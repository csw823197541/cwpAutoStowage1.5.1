package importDataProcess;

import importDataInfo.CwpResultInfo;

import java.util.*;

/**
 * Created by csw on 2016/5/19.
 */
public class CwpResultInfoTransform {

    public static List<CwpResultInfo> getTransformResult(
            Long batchNum,
            List<CwpResultInfo> cwpResultInfoListIn) {
        List<CwpResultInfo> resultInfoList = new ArrayList<>();

        Map<String, Map<Integer, CwpResultInfo>> allMap = new HashMap<>();
        ExceptionData.exceptionMap.put(batchNum, "接口方法未执行。");
        boolean isRight = true;
        String info = "";
        try{
            for (CwpResultInfo cwpResultInfo:cwpResultInfoListIn){
                String bayId = cwpResultInfo.getHATCHBWID();
                String craneId = cwpResultInfo.getCRANEID();
                String ldFlag = cwpResultInfo.getLDULD();       //装卸标志
                String moveType = cwpResultInfo.getMOVETYPE();  //作业工艺

                String cKey = bayId + "." + craneId + "." + ldFlag;     //倍位号.桥机号

                //将数据按key为倍位号.桥机号，保存在Map里，其中value为以是开始时间为key的Map，目的是为了后面以时间顺序组成大作业块
                if(allMap.get(cKey)==null){
                    allMap.put(cKey,new HashMap<Integer, CwpResultInfo>());
                }
                int startSec = cwpResultInfo.getWORKINGSTARTTIME();
                allMap.get(cKey).put(startSec,cwpResultInfo);
            }

            //开始遍历倍位号.桥机号相同的Map
            for(Map<Integer, CwpResultInfo> valueMap : allMap.values()){
                List<Integer> timeList = new ArrayList(valueMap.keySet());
                Collections.sort(timeList);

                CwpResultInfo cwpResultInfoLast = null;   //上一个时间片块对象

                for(int i = 0 ; i < timeList.size() ; i++){     //开始遍历倍位号.桥机号相同的Map
                    int currentStartTime = timeList.get(i);
                    CwpResultInfo cwpResultInfoCurrent = valueMap.get(currentStartTime);
                    if(cwpResultInfoCurrent == null){
                        //为空，跳过
                    }
                    else {
                        if (cwpResultInfoLast == null){     //上一个时间片对象为空
                            try {      //把当前对象克隆给它
                                cwpResultInfoLast = (CwpResultInfo) cwpResultInfoCurrent.clone();
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        } else {    //上一个时间片对象不为空
                            //判断当前对象的开始时间是否与上一个对象的结束时间相同（由于cwp的输出有可能前者会小于后者），是否是连续的时间片
                            if(cwpResultInfoCurrent.getWORKINGSTARTTIME()-cwpResultInfoLast.getWORKINGENDTIME()<=1){
                                cwpResultInfoLast.setWORKINGENDTIME(cwpResultInfoCurrent.getWORKINGENDTIME());
                                cwpResultInfoLast.setMOVECOUNT(cwpResultInfoCurrent.getMOVECOUNT()+cwpResultInfoLast.getMOVECOUNT());
                            }
                            else{      //不是连续时间片
                                resultInfoList.add(cwpResultInfoLast);  //添加到结果List中
                                try {   //将当前对象克隆给上一个时间片对象
                                    cwpResultInfoLast = (CwpResultInfo)cwpResultInfoCurrent.clone();
                                } catch (CloneNotSupportedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                if(cwpResultInfoLast!=null){    //最后for循环结束，可能某个倍位.桥机，就只有一个块
                    resultInfoList.add(cwpResultInfoLast);
                }
            }

            resultInfoList = getCraneSeq(resultInfoList);   //对桥机进行编顺序号
            resultInfoList = getHatchSeq(resultInfoList);   //对舱进行编顺序号
        } catch (Exception e) {
            isRight = false;
            info += "接口方法发生传入的cwp数据有为null异常。";
        }
        if (isRight) {
            ExceptionData.exceptionMap.put(batchNum, "success! 生成cwp作业块接口方法没有发生异常。");
        } else {
            ExceptionData.exceptionMap.put(batchNum, "error! " + info);
        }

        return resultInfoList;
    }

    private static List<CwpResultInfo> getCraneSeq(List<CwpResultInfo> cwpResultInfoList) {
        List<CwpResultInfo> resultInfoList = new ArrayList<>();

        Map<String, Map<Integer, CwpResultInfo>> craneIdMap = new HashMap<>();

        //将数据按桥机保存在Map里
        for(CwpResultInfo cwpResultInfo : cwpResultInfoList) {
            String craneId = cwpResultInfo.getCRANEID();
            int startSec = cwpResultInfo.getWORKINGSTARTTIME();

            if(craneIdMap.get(craneId)==null){
                craneIdMap.put(craneId,new HashMap<Integer, CwpResultInfo>());
            }
            craneIdMap.get(craneId).put(startSec, cwpResultInfo);
        }

        for(Map<Integer, CwpResultInfo> valueMap : craneIdMap.values()) {
            List<Integer> timeList = new ArrayList(valueMap.keySet());
            Collections.sort(timeList);     //按时间进行排序
            Long seq = 1L;
            CwpResultInfo lastBlock = null; //上一个大块对象
            for(Integer tKey : timeList) {  //由于按时间排序了，所以直接编序号
                CwpResultInfo currentBlock = valueMap.get(tKey);    //当前大块对象

                //添加桥机作业顺序
                currentBlock.setCraneSeq(seq);
                if (lastBlock == null){     //上一个大块对象为空
                    try {      //将当前对象克隆给上一个大块对象
                        lastBlock = (CwpResultInfo) currentBlock.clone();
                        seq++;
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                } else {    //上一个时间片对象不为空
                    //判断当前对象的开始时间是否与上一个对象的结束时间相同（由于cwp的输出有可能前者会小于后者），
                    // 还有在同一个倍位上（桥机没有移动），
                    // 桥机装、卸的块分开
                    // 则桥机顺序号是相同的
                    if(currentBlock.getWORKINGSTARTTIME() - lastBlock.getWORKINGENDTIME() <= 1
                            && currentBlock.getHATCHBWID().equals(lastBlock.getHATCHBWID())
                            && currentBlock.getLDULD().equals(lastBlock.getLDULD())){
                        currentBlock.setCraneSeq(lastBlock.getCraneSeq());  //跟上一个大块的序号相同
                    }
                    else{      //不是连续时间片
                        try {   //将当前对象克隆给上一个大块对象
                            lastBlock = (CwpResultInfo) currentBlock.clone();
                            seq++;
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                currentBlock.setMOVETYPE(null);
                //添加作业绝对时间
                int workTimeMinute = currentBlock.getWorkTimeMinute();
                Date startTime = currentBlock.getCraneWorkStartTime();
                Date endTime = new Date(startTime.getTime() + workTimeMinute*60*1000);
                currentBlock.setWorkingEndTime(endTime);
                resultInfoList.add(currentBlock);
            }
        }

        return resultInfoList;
    }

    private static List<CwpResultInfo> getHatchSeq(List<CwpResultInfo> cwpResultInfoList) {
        List<CwpResultInfo> resultInfoList = new ArrayList<>();

        Map<String, Map<Integer, CwpResultInfo>> hatchIdMap = new HashMap<>();

        //将数据按舱保存在Map里
        for(CwpResultInfo cwpResultInfo : cwpResultInfoList) {
            String hatchId = cwpResultInfo.getHATCHID();
            int startSec = cwpResultInfo.getWORKINGSTARTTIME();

            if(hatchIdMap.get(hatchId)==null){
                hatchIdMap.put(hatchId, new HashMap<Integer, CwpResultInfo>());
            }
            hatchIdMap.get(hatchId).put(startSec, cwpResultInfo);
        }

        for(Map<Integer, CwpResultInfo> valueMap : hatchIdMap.values()) {
            List<Integer> timeList = new ArrayList(valueMap.keySet());
            Collections.sort(timeList);
            Long seq = 1L;
            CwpResultInfo lastBlock = null; //上一个大块对象
            for(int i = 0; i < timeList.size(); i++) {
                int tKey = timeList.get(i);
                CwpResultInfo currentBlock = valueMap.get(tKey);    //当前大块对象

                //添加舱作业顺序
                currentBlock.setHatchSeq(seq);
                if (lastBlock == null){     //上一个大块对象为空
                    try {      //将当前对象克隆给上一个大块对象
                        lastBlock = (CwpResultInfo) currentBlock.clone();
                        seq++;
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                } else {    //上一个时间片对象不为空
                    //判断当前对象的开始时间是否与上一个对象的结束时间相同（由于cwp的输出有可能前者会小于后者），还有在同一个倍位上（即桥机没有移动），
                    // 而且还是同一部桥机作业，还有装卸标识必须相同，则舱顺序号是相同的
                    if(currentBlock.getWORKINGSTARTTIME() - lastBlock.getWORKINGENDTIME() <= 1
                            && currentBlock.getHATCHBWID().equals(lastBlock.getHATCHBWID())
                            && currentBlock.getCRANEID().equals(lastBlock.getCRANEID())
                            && currentBlock.getLDULD().equals(lastBlock.getLDULD())){
                        currentBlock.setHatchSeq(lastBlock.getHatchSeq());  //跟上一个大块的序号相同
                    }
                    else{      //不是连续时间片
                        try {   //将当前对象克隆给上一个大块对象
                            lastBlock = (CwpResultInfo) currentBlock.clone();
                            seq++;
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                resultInfoList.add(currentBlock);
            }
        }

        return resultInfoList;
    }
}
