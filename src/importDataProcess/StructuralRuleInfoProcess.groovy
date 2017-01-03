package importDataProcess

import groovy.json.JsonSlurper
import importDataInfo.ContainerInfo
import importDataInfo.StructuralRuleInfo

/**
 *
 * Created by csw on 2016/1/15.
 */
class StructuralRuleInfoProcess {

    //Json字符串解析编码
    public static List<ContainerInfo> getStructuralRuleInfo(String jsonStr) {

        boolean isError = false;
        List<StructuralRuleInfo> structuralRuleInfoList = new ArrayList<StructuralRuleInfo>();
        try{
            def root = new JsonSlurper().parseText(jsonStr)
            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List
            root.each {it->
                StructuralRuleInfo structuralRuleInfo = new StructuralRuleInfo()
                assert it instanceof Map
                structuralRuleInfo.bayNo = it.BAYNO;
                structuralRuleInfo.rowNo = it.ROWNO;
                structuralRuleInfo.vesselHatchId = it.VESSELHATCHID;
                structuralRuleInfo.highConNumber = it.HIGHCONNUMBER;
                structuralRuleInfo.tireNo = it.TIRENO;
                structuralRuleInfo.size20AllWeight = it.SIZE20ALLWEIGHT;
                structuralRuleInfo.size40AllWeight = it.SIZE40ALLWEIGHT;
                structuralRuleInfoList.add(structuralRuleInfo)
            }
        }
        catch (Exception e){
            System.out.println("在船舶规范信息解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("在船舶规范信息解析失败！")
            return null;
        }else {
            System.out.println("在船舶规范信息解析成功！")
            return structuralRuleInfoList;
        }
    }

}
