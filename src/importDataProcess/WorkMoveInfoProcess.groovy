package importDataProcess

import groovy.json.JsonBuilder
import importDataInfo.WorkMoveInfo

/**
 * Created by csw on 2016/1/18.
 */
class WorkMoveInfoProcess {

    //生成舱内作业关信息的json字符串，用于cwp算法
    public static String getWorkMoveInfoJsonStr(List<WorkMoveInfo> workMoveInfos) {

        boolean isError = false;
        String result = null
        List<WorkMoveInfo> workMoveInfoList = workMoveInfos

        if(workMoveInfoList != null) {
            try{
                List<Map<String, Object>> list = new ArrayList<>()
                assert workMoveInfoList instanceof List
                workMoveInfoList.each {it->
                    Map<String, Object> map = new HashMap<String, Object>()
                    map.put("CWPWORKMOVENUM", it.CWPWORKMOVENUM)
                    map.put("DECK", it.DECK)
                    map.put("GLOBALPRIORITY", it.GLOBALPRIORITY)
                    map.put("HATCHID", it.HATCH)
                    map.put("HORIZONTALPOSITION", it.HORIZONTALPOSITION)
                    map.put("LD", it.LD)
                    map.put("MOVETYPE", it.MOVETYPE)
                    list.add(map)
                }
                def builder = new JsonBuilder(list)
                result = builder.toString()
                println result

            }catch (Exception e){
                System.out.println("生成舱内作业关信息json格式时，发现数据异常！")
                isError = true;
                e.printStackTrace()
            }
        }else {
            System.out.println("没有舱内作业关信息！")
        }
        if(isError) {
            System.out.println("生成舱内作业关信息json格式失败！")
            return null;
        }else {
            System.out.println("生成舱内作业关信息json格式成功！")
            return result
        }
    }
}
