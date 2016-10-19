package importDataProcess

import groovy.json.JsonBuilder
import importDataInfo.HatchInfo

import java.text.SimpleDateFormat

/**
 * Created by csw on 2016/1/18.
 */
class HatchInfoProcess {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    //生成船舱信息的json字符串，用于cwp算法
    public static String getHatchInfoJsonStr(List<HatchInfo> hatchInfos) {

        boolean isError = false;
        String result = null
        List<HatchInfo> hatchInfoList = hatchInfos

        if(hatchInfoList != null) {
            try{
                List<Map<String, Object>> list = new ArrayList<>()
                assert hatchInfoList instanceof List
                hatchInfoList.each {it->
                    Map<String, Object> map = new HashMap<String, Object>()
                    map.put("HORIZONTALSTARTPOSITION", it.HORIZONTALSTARTPOSITION)
                    map.put("ID", it.ID)
                    map.put("VESSELID", it.VESSELID)
                    map.put("LENGTH", it.LENGTH)
                    map.put("MOVECOUNT", it.MOVECOUNT)
                    map.put("NO", Integer.valueOf(it.NO))
                    map.put("SEQ", Integer.valueOf(it.SEQ))
                    def workingTimeRange = it.WORKINGTIMERANGES
                    assert workingTimeRange instanceof List
                    List<Map<String, Object>> listT = new ArrayList<>()
                    workingTimeRange.each {t->
                        Map<String, Object> mapT = new HashMap<String, Object>();
                        mapT.put("ID", t.ID)
                        mapT.put("WORKENDTIME", sdf.format(t.WORKENDTIME))
                        mapT.put("WORKSTARTTIME", sdf.format(t.WORKSTARTTIME))
                        listT.add(mapT)
                        map.put("WORKINGTIMERANGES", listT)
                    }
                    list.add(map)
                }
                def builder = new JsonBuilder(list)
                result = builder.toString()
                println result

            }catch (Exception e){
                System.out.println("生成船舱数据json格式时，发现数据异常！")
                isError = true;
                e.printStackTrace()
            }
        }else {
            System.out.println("没有船舱数据！")
        }
        if(isError) {
            System.out.println("生成船舱数据json格式失败！")
            return null;
        }else {
            System.out.println("生成船舱数据json格式成功！")
            return result
        }
    }
}
