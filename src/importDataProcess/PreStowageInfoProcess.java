package importDataProcess;

import importDataInfo.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by leko on 2016/1/22.
 */
public class PreStowageInfoProcess {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getContainerString(List<ContainerInfo> containerInfoList) {
        //在场箱信息字符串
        String container="";
        List<ContainerInfo> containerInfoList1;
        containerInfoList1 = containerInfoList;
        for (ContainerInfo containerInfo: containerInfoList1)
        {
            if("Y".equals(containerInfo.getIYCPLANFG())) {
                String temp="";
                temp+=containerInfo.getIYCCNTRNO()+",";
                temp+=containerInfo.getIYCCNTRAREAID()+",";
                temp+=containerInfo.getIYCVOYID()+",";
                temp+=containerInfo.getIYCCTYPECD()+",";
                temp+=containerInfo.getIYCCSZCSIZECD()+",";
                temp+=containerInfo.getIYCPORTCD()+",";
                temp+=containerInfo.getIYCWEIGHT()+",";
                temp+=containerInfo.getIYCDNGFG()+",";
                temp+=containerInfo.getIYCREFFG()+",";
                temp+=containerInfo.getIYCYLOCATION()+",";
                temp+=containerInfo.getIYCPLANFG()+",";
                temp+=containerInfo.getIYCRETIME()+"#";
                container+=temp;
            }
        }
        return container;
    }

    public static String getContainerAreaString(List<ContainerAreaInfo> containerAreaInfoList){
        //箱区信息字符串
        String containerArea="";
        List<ContainerAreaInfo> containerAreaInfoList1 = containerAreaInfoList;
        for (ContainerAreaInfo containerAreaInfo: containerAreaInfoList1)
        {
            String temp="";
            temp+=containerAreaInfo.getASCBOTTOMSPEED().toString()+",";
            temp+=containerAreaInfo.getASCTOPSPEED().toString()+",";
            temp+=containerAreaInfo.getID().toString()+",";
            temp+=containerAreaInfo.getLOCATIONLB()+",";
            temp+=containerAreaInfo.getLOCATIONLH()+",";
            temp+=containerAreaInfo.getLOCATIONRB()+",";
            temp+=containerAreaInfo.getLOCATIONRH()+",";
            temp+=containerAreaInfo.getVBYNUM().toString()+",";
            temp+=containerAreaInfo.getVTRNUM().toString()+",";
            temp+=containerAreaInfo.getVRWNUM().toString()+",";
            temp+=containerAreaInfo.getSCTYPE()+",";
            temp+=containerAreaInfo.getWORKEFFICIENCYB().toString()+",";
            String disP = containerAreaInfo.getDISPATCHEDWORK() == null ? "0" : containerAreaInfo.getDISPATCHEDWORK().toString();
            String preDisP = containerAreaInfo.getPREDISPATCHEDWORK() == null ? "0" : containerAreaInfo.getPREDISPATCHEDWORK().toString();
            temp+=disP+",";
            temp+=preDisP+",";
            temp+=containerAreaInfo.getWORKEFFICIENCYT().toString()+"#";
            containerArea+=temp;
        }
        return containerArea;
    }

    public static String getPreStowageString(List<GroupInfo> groupInfoList,List<PreStowageData> preStowageDataList){

        //预配信息字符串
        String preStowage="";
        List<PreStowageData> preStowageInfoList1 = preStowageDataList;
        for (PreStowageData preStowageData:preStowageInfoList1)
        {
            String temp="";
            temp+=preStowageData.getVHTID().toString()+",";
            temp+=preStowageData.getVBYBAYID().toString()+",";
            temp+=preStowageData.getVTRTIERNO().toString()+",";
            temp+=preStowageData.getVRWROWNO().toString()+",";
            temp+=preStowageData.getSIZE()+",";
            temp+=preStowageData.getDSTPORT()+",";
            temp+=preStowageData.getCTYPECD()+",";
            String weight = preStowageData.getWEIGHT() == null ? "" : preStowageData.getWEIGHT().toString();
            temp+=weight+",";
            temp+=preStowageData.getMOVEORDER().toString()+"#";
            preStowage+=temp;
        }
        return preStowage;
    }

//    public static String getCwpResultString(List<CwpResultInfo> cwpResultInfoList) {
//        //生成cwp输出结果
//        String cwpOutput="";
//        List<CwpResultInfo> cwpResultInfoList1 = cwpResultInfoList;
//        for (CwpResultInfo cwpResultInfo:cwpResultInfoList1)
//        {
//            String temp="";
//            temp+=cwpResultInfo.getCRANEID().toString()+",";
//            temp+=cwpResultInfo.getCranesPosition().toString()+",";
//            temp+=cwpResultInfo.getHATCHBWID().toString()+",";
//            temp+=cwpResultInfo.getHATCHID().toString()+",";
//            temp+=cwpResultInfo.getStartMoveID().toString()+",";
//            temp+=cwpResultInfo.getMOVECOUNT().toString()+",";
//            temp+=cwpResultInfo.getQDC().toString()+",";
//            temp+=cwpResultInfo.getVESSELID().toString()+",";
//            temp+=cwpResultInfo.getMOVETYPE().toString()+",";
//            temp+=cwpResultInfo.getLDULD()+",";
//            temp+=cwpResultInfo.getWORKINGENDTIME().toString()+",";
//            temp+=cwpResultInfo.getREALWORKINGSTARTTIME().toString()+"#";
//            cwpOutput+=temp;
//        }
//        return cwpOutput;
//    }

    public static String getCwpResultString(List<CwpResultMoveInfo> cwpResultMoveInfoList) {
        //生成cwp输出结果
        String cwpOutput="";
        List<CwpResultMoveInfo> cwpResultMoveInfoList1 = cwpResultMoveInfoList;
        for (CwpResultMoveInfo cwpResultMoveInfo : cwpResultMoveInfoList1)
        {
            String temp="";
            temp+=cwpResultMoveInfo.getCRANEID().toString()+",";
            temp+=cwpResultMoveInfo.getCranesPosition().toString()+",";
            temp+=cwpResultMoveInfo.getHATCHBWID().toString()+",";
            temp+=cwpResultMoveInfo.getHATCHID().toString()+",";
            temp+=cwpResultMoveInfo.getMoveOrder().toString()+",";  //moveOrder
            temp+=cwpResultMoveInfo.getVESSELID().toString()+",";
            temp+=cwpResultMoveInfo.getMOVETYPE().toString()+",";
            temp+=cwpResultMoveInfo.getLDULD()+",";
//            temp+=cwpResultMoveInfo.getWORKINGENDTIME().toString()+",";
//            temp+=cwpResultMoveInfo.getWORKINGSTARTTIME().toString()+"#";
            temp+=sdf.format(cwpResultMoveInfo.getWorkingEndTime())+",";
            temp+=sdf.format(cwpResultMoveInfo.getWorkingStartTime())+"#";
            cwpOutput+=temp;
        }
        return cwpOutput;
    }
}