package importDataProcess;

import importDataInfo.CwpResultInfo;
import importDataInfo.CwpResultMoveInfo;
import importDataInfo.PreStowageData;

import java.util.*;

/**
 * Created by csw on 2016/5/27 12:28.
 * explain：
 */
public class CwpResultInfoToMove {

    public static List<CwpResultMoveInfo> getCwpMoveInfoResult(
            Long batchNum,
            List<CwpResultInfo> cwpResultInfoListIn,
            List<PreStowageData> preStowageDataList) {
        List<CwpResultMoveInfo> resultInfoList = new ArrayList<>();
        ExceptionData.exceptionMap.put(batchNum, "接口方法未执行。");
        boolean isRight = true;
        String info = "";
        try{
            //将预配信息进行处理，根据船箱位得到卸船的箱号信息
            Map<String, PreStowageData> preStowageDataMapD = new HashMap<>();
            Map<String, PreStowageData> preStowageDataMapL = new HashMap<>();
            for(PreStowageData preStowageData : preStowageDataList) {
                String bayId = preStowageData.getVBYBAYID();    //倍号
                String rowId = preStowageData.getVRWROWNO();    //排号
                String tieId = preStowageData.getVTRTIERNO();   //层号
                String vp = bayId + rowId + tieId;
                if("D".equals(preStowageData.getLDULD())) {
                    if(!preStowageDataMapD.containsKey(vp)) {
                        preStowageDataMapD.put(vp, preStowageData);
                    }
                } else if("L".equals(preStowageData.getLDULD())) {
                    if(!preStowageDataMapL.containsKey(vp)) {
                        preStowageDataMapL.put(vp, preStowageData);
                    }
                }
            }

            //舱.作业序列.作业工艺确定具体位置
            Map<String,List<String>> moveOrderRecords = ImportData.moveOrderRecords;

            for(CwpResultInfo cwpResultInfo : cwpResultInfoListIn) {

                int moveCount = cwpResultInfo.getMOVECOUNT();
                if (moveCount != 0) {
                    int startTime = cwpResultInfo.getREALWORKINGSTARTTIME();
                    int endTime = cwpResultInfo.getWORKINGENDTIME();
                    Date startTimeDate = cwpResultInfo.getWorkingStartTime();
                    int singleMoveWorkTime = (endTime-startTime)/moveCount; //做一个move的时间

                    String craneId = cwpResultInfo.getCRANEID();
                    String hatchId = cwpResultInfo.getHATCHID();
                    String vesselId = cwpResultInfo.getVESSELID();
                    String moveType = cwpResultInfo.getMOVETYPE();
                    String LD = cwpResultInfo.getLDULD();
                    Double cranePosition = cwpResultInfo.getCranesPosition();

                    int startMoveOrder = cwpResultInfo.getStartMoveID();

                    for(int i = 0; i < moveCount; i++) {   //有多少个move，就拆成几个对象

                        int moveOrder = startMoveOrder + i;
                        //舱.作业序列.作业工艺为关键字,查找船箱位
                        String key = hatchId + "." + moveOrder + "." + moveType;
                        List<String> vesselPosition = moveOrderRecords.get(key);
                        if(vesselPosition != null) {
                            for(String vesselPositionStr : vesselPosition) {    //有多少个船箱位，结果就有多少个对象
                                CwpResultMoveInfo cwpResultMoveInfo = new CwpResultMoveInfo();

                                cwpResultMoveInfo.setMoveOrder(moveOrder);     //作业顺序
                                cwpResultMoveInfo.setWorkingStartTime(new Date(startTimeDate.getTime() + (i)*singleMoveWorkTime*1000));
                                cwpResultMoveInfo.setWorkingEndTime(new Date(startTimeDate.getTime() + (i+1)*singleMoveWorkTime*1000));
                                cwpResultMoveInfo.setWORKINGSTARTTIME(startTime + (i)*singleMoveWorkTime);
                                cwpResultMoveInfo.setWORKINGENDTIME(startTime + (i+1)*singleMoveWorkTime);
                                cwpResultMoveInfo.setMoveWorkTime(singleMoveWorkTime);

                                cwpResultMoveInfo.setCRANEID(craneId);
                                cwpResultMoveInfo.setHATCHID(hatchId);
                                cwpResultMoveInfo.setVESSELID(vesselId);
                                cwpResultMoveInfo.setMOVETYPE(moveType);
                                cwpResultMoveInfo.setLDULD(LD);

                                String[] vps = vesselPositionStr.split("\\.");
                                String vp = vps[1] + "" + vps[3] + "" + vps[2];
                                cwpResultMoveInfo.setVesselPosition(vp);

                                if("L".equals(LD)) {
                                    PreStowageData preStowageData = preStowageDataMapL.get(vp);
                                    cwpResultMoveInfo.setSize(preStowageData.getSIZE());
                                    String bayId = preStowageData.getVBYBAYID();
                                    cwpResultMoveInfo.setHATCHBWID(bayId);
                                } else {
                                    PreStowageData preStowageData = preStowageDataMapD.get(vp);
                                    cwpResultMoveInfo.setSize(preStowageData.getSIZE());
                                    String bayId = preStowageData.getVBYBAYID();
                                    cwpResultMoveInfo.setHATCHBWID(bayId);
                                }
                                cwpResultMoveInfo.setCranesPosition(cranePosition);

                                resultInfoList.add(cwpResultMoveInfo);
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            isRight = false;
            info += "接口方法发生cwp结果或者预配数据为null异常。";
        }
        if (isRight) {
            ExceptionData.exceptionMap.put(batchNum, "success! 生成cwp单箱数据接口方法没有发生异常。");
        } else {
            ExceptionData.exceptionMap.put(batchNum, "error! " + info);
        }

        return resultInfoList;

    }
}
