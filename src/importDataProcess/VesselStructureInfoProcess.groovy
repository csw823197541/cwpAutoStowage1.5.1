package importDataProcess

import groovy.json.JsonSlurper
import importDataInfo.VesselStructureInfo

/**
 * Created by csw on 2016/1/16.
 */
class VesselStructureInfoProcess {

    //Json字符串解析编码
    public static List<VesselStructureInfo> getVesselStructureInfo(String jsonStr) {

        boolean isError = false;
        List<VesselStructureInfo> vesselStructureInfoList = new ArrayList<VesselStructureInfo>();

        try{
            def root = new JsonSlurper().parseText(jsonStr)

            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List

            root.each { vesselStructure ->
                VesselStructureInfo vesselStructureInfo = new VesselStructureInfo()
                assert vesselStructure instanceof Map
                vesselStructureInfo.VHTID = vesselStructure.VHTID
                vesselStructureInfo.LENGTH = Integer.valueOf(vesselStructure.LENGTH)
//                vesselStructureInfo.VHTPOSITION = vesselStructure.VHTPOSITION
                vesselStructureInfo.VBYBAYID = vesselStructure.VBYBAYID
//                vesselStructureInfo.VBYPOSITION = vesselStructure.VBYPOSITION
                vesselStructureInfo.VTRTIERNO = vesselStructure.VTRTIERNO
                vesselStructureInfo.VTRTIERSEQ = Integer.valueOf(vesselStructure.VTRTIERSEQ)
                vesselStructureInfo.VRWROWNO = vesselStructure.VRWROWNO
                vesselStructureInfo.VRWROWSEQ = Integer.valueOf(vesselStructure.VRWROWSEQ)
                vesselStructureInfo.CABLENGTH = Integer.valueOf(vesselStructure.CABLENGTH)
                vesselStructureInfo.CABPOSITION = Integer.valueOf(vesselStructure.CABPOSITION)
                vesselStructureInfo.weightGradeMin = vesselStructure.weightGradeMin
                vesselStructureInfo.weightGradeMax = vesselStructure.weightGradeMax
                vesselStructureInfoList.add(vesselStructureInfo)
            }
        }catch (Exception e){
            System.out.println("船舶结构数据解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("船舶结构数据解析失败！")
            return null;
        }else {
            System.out.println("船舶结构数据解析成功！")
            return vesselStructureInfoList
        }
    }

}
