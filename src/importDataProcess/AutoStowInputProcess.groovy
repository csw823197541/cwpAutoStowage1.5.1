package importDataProcess

import groovy.json.JsonBuilder
import importDataInfo.ContainerAreaInfo
import importDataInfo.ContainerInfo
import importDataInfo.CwpResultMoveInfo
import importDataInfo.PreStowageData
import importDataInfo.StructuralRuleInfo

import java.text.SimpleDateFormat

/**
 * Created by csw on 2016/7/5 17:51.
 * explain：自动配载输入信息处理，包括在场箱、箱区、预配、cwp结果
 */
class AutoStowInputProcess {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    /**
     * 生成在场箱信息json字符串
     * @param containerInfoList
     * @return
     */
    public static String getContainerJsonStr(Long batchNum, List<ContainerInfo> containerInfoList) {

        boolean isError = false;
        String result = null

        if(containerInfoList != null) {
            try{
                List<Map<String, Object>> list = new ArrayList<>()
                assert containerInfoList instanceof List
                containerInfoList.each {it->
                    if(it.IYCPLANFG.equals("Y")) {
                        Map<String, Object> map = new HashMap<String, Object>()
                        map.put("IYCCNTRNO", it.containerId)
                        map.put("IYCCNTRAREAID", it.IYCCNTRAREAID)
                        map.put("IYCVOYID", it.IYCVOYID)
                        map.put("IYCCTYPECD", it.IYCCTYPECD)
                        map.put("IYCCSZCSIZECD", it.IYCCSZCSIZECD)
                        map.put("IYCPORTCD", it.IYCPORTCD)
                        map.put("IYCWEIGHT", it.IYCWEIGHT)
                        map.put("IYCDNGFG", it.IYCDNGFG)
                        map.put("IYCREFFG", it.IYCREFFG)
                        map.put("IYCYLOCATION", it.IYCYLOCATION)
                        map.put("IYCPLANFG", it.IYCPLANFG)
                        map.put("IYCRETIME", it.IYCRETIME)
                        list.add(map)
                    }
                }
                def builder = new JsonBuilder(list)
                result = builder.toString()
                println result

            }catch (Exception e){
                System.out.println("生成在场箱信息json格式时，发现数据异常！")
                isError = true;
                e.printStackTrace()
            }
        }else {
            System.out.println("没有在场箱信息！")
        }
        if(isError) {
            System.out.println("生成在场箱信息json格式失败！")
            ExceptionData.exceptionMap.put(batchNum, "生成在场箱信息json格式时，发现数据异常！")
            return null;
        }else {
            System.out.println("生成在场箱信息json格式成功！")
            return result
        }
    }

    /**
     * 生成箱区信息json格式字符串
     * @param containerAreaInfoList
     * @return
     */
    public static String getContainerAreaJsonStr(Long batchNum, List<ContainerAreaInfo> containerAreaInfoList) {

        boolean isError = false;
        String result = null

        if(containerAreaInfoList != null) {
            try{
                List<Map<String, Object>> list = new ArrayList<>()
                assert containerAreaInfoList instanceof List
                containerAreaInfoList.each {it->
                    Map<String, Object> map = new HashMap<String, Object>()
                    map.put("ASCBOTTOMSPEED", it.ASCBOTTOMSPEED)
                    map.put("ASCTOPSPEED", it.ASCTOPSPEED)
                    map.put("ID", it.ID)
                    map.put("LOCATIONLB", it.LOCATIONLB)
                    map.put("LOCATIONLH", it.LOCATIONLH)
                    map.put("LOCATIONRB", it.LOCATIONRB)
                    map.put("LOCATIONRH", it.LOCATIONRH)
                    map.put("VBYNUM", it.VBYNUM)
                    map.put("VRWNUM", it.VRWNUM)
                    map.put("VTRNUM", it.VTRNUM)
                    map.put("SCTYPE", it.SCTYPE)
                    map.put("WORKEFFICIENCYB", it.WORKEFFICIENCYB)
                    map.put("WORKEFFICIENCYT", it.WORKEFFICIENCYT)
//                    String disP = it.DISPATCHEDWORK == null ? "0" : it.DISPATCHEDWORK.toString();
//                    String preDisP = it.PREDISPATCHEDWORK == null ? "0" : it.PREDISPATCHEDWORK.toString();
                    map.put("DISPATCHEDWORK", it.DISPATCHEDWORK)
                    map.put("PREDISPATCHEDWORK", it.PREDISPATCHEDWORK)
                    list.add(map)
                }
                def builder = new JsonBuilder(list)
                result = builder.toString()
                println result

            }catch (Exception e){
                System.out.println("生成箱区信息json格式时，发现数据异常！")
                isError = true;
                e.printStackTrace()
            }
        }else {
            System.out.println("没有箱区信息！")
        }
        if(isError) {
            System.out.println("生成箱区信息json格式失败！")
            ExceptionData.exceptionMap.put(batchNum, "生成箱区信息json格式时，发现数据异常！")
            return null;
        }else {
            System.out.println("生成箱区信息json格式成功！")
            return result
        }
    }

    /**
     * 生成预配信息json字符串
     * @param preStowageDataList
     * @return
     */
    public static String getPreStowageJsonStr(Long batchNum, List<PreStowageData> preStowageDataList) {

        boolean isError = false;
        String result = null

        if(preStowageDataList != null) {
            try{
                List<Map<String, Object>> list = new ArrayList<>()
                assert preStowageDataList instanceof List
                preStowageDataList.each {it->
                    Map<String, Object> map = new HashMap<String, Object>()
                    map.put("VHTID", it.VHTID)
                    map.put("VBYBAYID", it.VBYBAYID)
                    map.put("VTRTIERNO", it.VTRTIERNO)
                    map.put("VRWROWNO", it.VRWROWNO)
                    map.put("SIZE", it.SIZE)
                    map.put("DSTPORT", it.DSTPORT)
                    map.put("CTYPECD", it.CTYPECD)
                    map.put("WEIGHT", it.WEIGHT)
                    map.put("MOVEORDER", it.MOVEORDER)
                    list.add(map)
                }
                def builder = new JsonBuilder(list)
                result = builder.toString()
                println result

            }catch (Exception e){
                System.out.println("生成预配信息json格式时，发现数据异常！")
                isError = true;
                e.printStackTrace()
            }
        }else {
            System.out.println("没有预配信息！")
        }
        if(isError) {
            System.out.println("生成预配信息json格式失败！")
            ExceptionData.exceptionMap.put(batchNum, "生成预配信息json格式时，发现数据异常！")
            return null;
        }else {
            System.out.println("生成预配信息json格式成功！")
            return result
        }
    }

    /**
     * 生成cwp结果信息json字符串
     * @param cwpResultMoveInfoList
     * @return
     */
    public static String getCwpResultJsonStr(Long batchNum, List<CwpResultMoveInfo> cwpResultMoveInfoList) {

        boolean isError = false;
        String result = null

        if(cwpResultMoveInfoList != null) {
            try{
                List<Map<String, Object>> list = new ArrayList<>()
                assert cwpResultMoveInfoList instanceof List
                cwpResultMoveInfoList.each {it->
                    Map<String, Object> map = new HashMap<String, Object>()
                    map.put("CRANEID", it.CRANEID)
                    map.put("CranesPosition", it.CranesPosition)
                    map.put("HATCHBWID", it.HATCHBWID)
                    map.put("HATCHID", it.HATCHID)
                    map.put("moveOrder", it.moveOrder)
                    map.put("VESSELID", it.VESSELID)
                    map.put("MOVETYPE", it.MOVETYPE)
                    map.put("LDULD", it.LDULD)
                    map.put("workingStartTime", sdf.format(it.workingStartTime))
                    map.put("workingEndTime", sdf.format(it.workingEndTime))
                    list.add(map)
                }
                def builder = new JsonBuilder(list)
                result = builder.toString()
                println result

            }catch (Exception e){
                System.out.println("生成cwp结果信息json格式时，发现数据异常！")
                isError = true;
                e.printStackTrace()
            }
        }else {
            System.out.println("没有cwp结果信息！")
        }
        if(isError) {
            System.out.println("生成cwp结果信息json格式失败！")
            ExceptionData.exceptionMap.put(batchNum, "生成cwp结果信息json格式时，发现数据异常！")
            return null;
        }else {
            System.out.println("生成cwp结果信息json格式成功！")
            return result
        }
    }

    public static String getStructuralRuleStr(long batchNum, List<StructuralRuleInfo> structuralRuleInfoList) {
        boolean isError = false;
        String result = null

        if(structuralRuleInfoList != null) {
            try{
                List<Map<String, Object>> list = new ArrayList<>()
                assert structuralRuleInfoList instanceof List
                structuralRuleInfoList.each {it->
                    Map<String, Object> map = new HashMap<String, Object>()
                    map.put("vesselHatchId", it.vesselHatchId)
                    map.put("bayNo", it.bayNo)
                    map.put("rowNo", it.rowNo)
                    map.put("tireNo", it.tireNo)
                    map.put("size20AllWeight", it.size20AllWeight)
                    map.put("size40AllWeight", it.size40AllWeight)
                    map.put("highConNumber", it.highConNumber)
                    list.add(map)
                }
                def builder = new JsonBuilder(list)
                result = builder.toString()
                println result

            }catch (Exception e){
                System.out.println("生成船舶规范信息json格式时，发现数据异常！")
                isError = true;
                e.printStackTrace()
            }
        }else {
            System.out.println("没有cwp结果信息！")
        }
        if(isError) {
            System.out.println("生成船舶规范信息json格式失败！")
            ExceptionData.exceptionMap.put(batchNum, "生成船舶规范信息json格式时，发现数据异常！")
            return null;
        }else {
            System.out.println("生成船舶规范信息json格式成功！")
            return result
        }
    }
}
