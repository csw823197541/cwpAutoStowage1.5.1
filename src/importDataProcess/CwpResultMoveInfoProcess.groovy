package importDataProcess

import groovy.json.JsonSlurper
import importDataInfo.CwpResultMoveInfo

import java.text.SimpleDateFormat

/**
 * Created by csw on 2016/6/20 19:29.
 * explain：
 */
class CwpResultMoveInfoProcess {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    public static List<CwpResultMoveInfo> getCwpResultMoveInfoList(String jsonStr) {

        boolean isError = false;
        List<CwpResultMoveInfo> resultMoveInfoList = new ArrayList<>()

        try{
            def root = new JsonSlurper().parseText(jsonStr)

            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List

            root.each { it->
                CwpResultMoveInfo cwpResultMoveInfo = new CwpResultMoveInfo()
                assert it instanceof Map
                cwpResultMoveInfo.LDULD = it.LDULD
                cwpResultMoveInfo.CRANEID = it.CRANEID
                cwpResultMoveInfo.cranesPosition = it.CRANESPOSITION
                cwpResultMoveInfo.HATCHBWID = it.HATCHBWID
                cwpResultMoveInfo.HATCHID = it.HATCHID
                cwpResultMoveInfo.moveOrder = it.MOVEORDER
                cwpResultMoveInfo.MOVETYPE = it.MOVETYPE
                cwpResultMoveInfo.VESSELID = it.VESSELID
                cwpResultMoveInfo.workingEndTime = sdf.parse(it.WORKINGENDTIME)
                cwpResultMoveInfo.workingStartTime = sdf.parse(it.WORKINGSTARTTIME)

                cwpResultMoveInfo.size = it.Size
                cwpResultMoveInfo.vesselPosition = it.VESSELPOSITION

                resultMoveInfoList.add(cwpResultMoveInfo)
            }
        }catch (Exception e) {
            System.out.println("cwp返回结果数据解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("cwp返回结果数据解析失败！")
            return null;
        }else {
            System.out.println("cwp返回结果数据解析成功！")
            return resultMoveInfoList
        }
    }
}
