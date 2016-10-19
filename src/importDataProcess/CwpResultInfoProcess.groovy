package importDataProcess

import groovy.json.JsonSlurper
import importDataInfo.CwpResultInfo
import importDataInfo.PreStowageData
import importDataInfo.VoyageInfo

import java.text.DecimalFormat

/**
 * Created by csw on 2016/1/16.
 */
class CwpResultInfoProcess {

    public static DecimalFormat df = new DecimalFormat("#.00");

    //Json字符串解析编码
    public static List<CwpResultInfo> getCwpResultInfo(String jsonStr, List<VoyageInfo> voyageInfoList) {

        boolean isError = false;
        List<CwpResultInfo> cwpResultInfoList = new ArrayList<CwpResultInfo>();

        try{
            Date voyageStartTime = voyageInfoList.get(0).getVOTPWKSTTM();
            long stLong = voyageStartTime.getTime();

            def root = new JsonSlurper().parseText(jsonStr)

            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List

            root.each { cwpResult ->
                CwpResultInfo cwpResultInfo = new CwpResultInfo()
                assert cwpResult instanceof Map
                cwpResultInfo.CRANEID = cwpResult.CRANEID
                cwpResultInfo.CranesPosition = Double.valueOf(df.format(cwpResult.CranesPosition))
                cwpResultInfo.HATCHBWID = cwpResult.HATCHBWID
                cwpResultInfo.HATCHID = cwpResult.HATCHID
                cwpResultInfo.MOVECOUNT = cwpResult.MOVECOUNT
                cwpResultInfo.QDC = cwpResult.QDC
                cwpResultInfo.StartMoveID = cwpResult.StartMoveID
                cwpResultInfo.VESSELID = cwpResult.VESSELD
                cwpResultInfo.WORKINGENDTIME = cwpResult.WORKINGENDTIME
                cwpResultInfo.REALWORKINGSTARTTIME = cwpResult.REALWORKINGSTARTTIME
                cwpResultInfo.WORKINGSTARTTIME = cwpResult.WORKINGSTARTTIME
                cwpResultInfo.endMoveID = cwpResult.EndMoveID
                cwpResultInfo.MOVETYPE = cwpResult.MOVETYPE
                cwpResultInfo.LDULD = cwpResult.mLD

                cwpResultInfo.workingStartTime = new Date(stLong + cwpResult.REALWORKINGSTARTTIME.intValue()*1000)
                cwpResultInfo.workingEndTime = new Date(stLong + cwpResult.WORKINGENDTIME*1000)
                cwpResultInfo.craneWorkStartTime = new Date(stLong + cwpResult.WORKINGSTARTTIME*1000)
                cwpResultInfoList.add(cwpResultInfo)
            }
        }catch (Exception e){
            System.out.println("cwp返回结果数据解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("cwp返回结果数据解析失败！")
            return null;
        }else {
            System.out.println("cwp返回结果数据解析成功！")
            return cwpResultInfoList
        }
    }

    public static List<CwpResultInfo> getCwpResultInfo(String jsonStr, List<VoyageInfo> voyageInfoList, List<PreStowageData> preStowageDataList) {

        boolean isError = false;
        List<CwpResultInfo> cwpResultInfoList = new ArrayList<CwpResultInfo>();

        try{

            //将预配信息进行处理，根据"舱号.moveOrder"得到作业工艺和装卸标志
            Map<String, PreStowageData> preStowageDataMap = new HashMap<>();
            for(PreStowageData preStowageData : preStowageDataList) {
                String hatchId = preStowageData.getVHTID();
                Integer moveOrder = preStowageData.getMOVEORDER();
                String hatchIdMoveOrder = hatchId + "." + moveOrder;
                preStowageDataMap.put(hatchIdMoveOrder, preStowageData);

            }

            Date voyageStartTime = voyageInfoList.get(0).getVOTPWKSTTM();
            long stLong = voyageStartTime.getTime();

            def root = new JsonSlurper().parseText(jsonStr)

            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List

            root.each { cwpResult ->
                CwpResultInfo cwpResultInfo = new CwpResultInfo()
                assert cwpResult instanceof Map
                cwpResultInfo.CRANEID = cwpResult.CRANEID
                cwpResultInfo.CranesPosition = Double.valueOf(df.format(cwpResult.CranesPosition))
                cwpResultInfo.HATCHBWID = cwpResult.HATCHBWID
                cwpResultInfo.HATCHID = cwpResult.HATCHID
                cwpResultInfo.MOVECOUNT = cwpResult.MOVECOUNT
                cwpResultInfo.QDC = 1
                cwpResultInfo.StartMoveID = cwpResult.MoveID
                cwpResultInfo.VESSELID = cwpResult.VESSELD
                cwpResultInfo.WORKINGENDTIME = cwpResult.WORKINGENDTIME
                cwpResultInfo.REALWORKINGSTARTTIME = cwpResult.REALWORKINGSTARTTIME
                cwpResultInfo.WORKINGSTARTTIME = cwpResult.WORKINGSTARTTIME
                cwpResultInfo.endMoveID = cwpResult.MoveID

                String hm = cwpResultInfo.HATCHID + "." + cwpResultInfo.startMoveID

                if (preStowageDataMap.get(hm) != null) {
                    cwpResultInfo.MOVETYPE = preStowageDataMap.get(hm).getWORKFLOW();
                    cwpResultInfo.LDULD = preStowageDataMap.get(hm).getLDULD();
                } else {
                    println "error hatchId.moveOrder: " + hm;
                }

//                cwpResultInfo.MOVETYPE = cwpResult.MOVETYPE
//                cwpResultInfo.LDULD = cwpResult.mLD

                cwpResultInfo.workingStartTime = new Date(stLong + cwpResultInfo.getREALWORKINGSTARTTIME()*1000)
                cwpResultInfo.workingEndTime = new Date(stLong + cwpResultInfo.getWORKINGENDTIME()*1000)
                cwpResultInfo.craneWorkStartTime = new Date(stLong + cwpResultInfo.getWORKINGSTARTTIME()*1000)
                cwpResultInfoList.add(cwpResultInfo)
            }


        }catch (Exception e){
            System.out.println("cwp返回结果数据解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("cwp返回结果数据解析失败！")
            return null;
        }else {
            System.out.println("cwp返回结果数据解析成功！")
            return cwpResultInfoList
        }
    }


}
