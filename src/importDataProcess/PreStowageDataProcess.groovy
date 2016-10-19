package importDataProcess

import groovy.json.JsonSlurper
import importDataInfo.PreStowageData

/**
 * 解析预配信息
 * Created by csw on 2016/1/15.
 */
class PreStowageDataProcess {

    //Json字符串解析编码
    public static List<PreStowageData> getPreStowageInfo(String jsonStr) {

        boolean isError = false;
        List<PreStowageData> preStowageDataList = new ArrayList<PreStowageData>();
        try{
            def root = new JsonSlurper().parseText(jsonStr)
            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List
            root.each {preStowage->
                PreStowageData preStowageData = new PreStowageData()
                assert preStowage instanceof Map
                preStowageData.VHTID = preStowage.VHTID
                preStowageData.VBYBAYID = preStowage.VBYBAYID
                preStowageData.VTRTIERNO = preStowage.VTRTIERNO
                preStowageData.VRWROWNO = preStowage.VRWROWNO
                preStowageData.SIZE = preStowage.SIZE
                preStowageData.CTYPECD = preStowage.CTYPECD
                preStowageData.GROUPID = ""
                preStowageData.WEIGHT = preStowage.WEIGHT
                preStowageData.MOVEORDER = preStowage.MOVEORDER
                preStowageData.LDULD = preStowage.LDULD
                preStowageData.WORKFLOW = preStowage.WORKFLOW
                preStowageData.QCNO = preStowage.QCNO
                preStowageData.DSTPORT = preStowage.DSTPORT
                preStowageData.THROUGHFLAG = preStowage.THROUGHFLAG

                preStowageData.containerNum = preStowage.CONTAINERNUM
                preStowageData.containerStatus = preStowage.CONTAINERSTATUS
                preStowageDataList.add(preStowageData)
            }
        }
        catch (Exception e){
            System.out.println("在预配信息解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("在预配信息解析失败！")
            return null;
        }else {
            System.out.println("在预配信息解析成功！")
            return preStowageDataList
        }
    }

}
