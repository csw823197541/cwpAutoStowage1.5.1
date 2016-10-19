package importDataProcess

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import importDataInfo.CraneInfo
import importDataInfo.WorkingTimeRange

import java.text.SimpleDateFormat


/**
 * Created by csw on 2016/1/16.
 */
class CraneInfoProcess {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    //Json字符串解析编码
    public static List<CraneInfo> getCraneInfo(String jsonStr) {

        boolean isError = false;
        List<CraneInfo> craneInfoList = new ArrayList<CraneInfo>();
        try{
            def root = new JsonSlurper().parseText(jsonStr)

            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List

            root.each { crane ->
                CraneInfo craneInfo = new CraneInfo()
                assert crane instanceof Map
                craneInfo.CURRENTPOSITION = crane.CURRENTPOSITION
                craneInfo.DISCHARGEEFFICIENCY20 = crane.DISCHARGEEFFICIENCY20
                craneInfo.DISCHARGEEFFICIENCY40 = crane.DISCHARGEEFFICIENCY40
                craneInfo.DISCHARGEEFFICIENCYTWIN = crane.DISCHARGEEFFICIENCYTWIN
                craneInfo.DISCHARGEEFFICIENCYTDM = crane.DISCHARGEEFFICIENCYTDM
                craneInfo.ID = crane.ID
                craneInfo.LOADINGEFFICIENCY20 = crane.LOADINGEFFICIENCY20
                craneInfo.LOADINGEFFICIENCY40 = crane.LOADINGEFFICIENCY40
                craneInfo.LOADINGEFFICIENCYTWIN = crane.LOADINGEFFICIENCYTWIN
                craneInfo.LOADINGEFFICIENCYTDM = crane.LOADINGEFFICIENCYTDM
                craneInfo.MOVINGRANGEFROM = crane.MOVINGRANGEFROM
                craneInfo.MOVINGRANGETO = crane.MOVINGRANGETO
                craneInfo.NAME = crane.NAME
                craneInfo.SAFESPAN = crane.SAFESPAN
                craneInfo.SEQ = crane.SEQ
                craneInfo.SPEED = crane.SPEED
                craneInfo.WIDTH = crane.WIDTH

                def workingTime = crane.WORKINGTIMERANGES//解析工作时间区间
                assert workingTime instanceof List
                List<WorkingTimeRange> workingTimeRangeList = new ArrayList<WorkingTimeRange>()
                workingTime.each {time->
                    WorkingTimeRange workingTimeRange = new WorkingTimeRange()
                    assert time instanceof Map
                    workingTimeRange.ID = time.ID
                    if(time.WORKSTARTTIME)
                        workingTimeRange.WORKSTARTTIME = sdf.parse(new String(time.WORKSTARTTIME))
                    if(time.WORKENDTIME)
                        workingTimeRange.WORKENDTIME = sdf.parse(new String(time.WORKENDTIME))
                    workingTimeRangeList.add(workingTimeRange)
                }
                craneInfo.WORKINGTIMERANGES = workingTimeRangeList

                craneInfoList.add(craneInfo)
            }
        }catch (Exception e){
            System.out.println("桥机数据解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("桥机数据解析失败！")
            return null;
        }else {
            System.out.println("桥机数据解析成功！")
            return craneInfoList
        }
    }

    //解析生成桥机json格式字符串
    public static String getCraneInfoJsonStr(List<CraneInfo> craneInfos) {

        boolean isError = false;
        String result = null
        List<CraneInfo> craneInfoList = craneInfos;

        if(craneInfoList != null) {
            try{
                List<Map<String, Object>> list = new ArrayList<>()
                assert craneInfoList instanceof List
                craneInfoList.each {it->
                    Map<String, Object> map = new HashMap<String, Object>()
                    map.put("CURRENTPOSITION", it.CURRENTPOSITION)
                    map.put("DISCHARGEEFFICIENCY20", it.DISCHARGEEFFICIENCY20)
                    map.put("DISCHARGEEFFICIENCY40", it.DISCHARGEEFFICIENCY40)
                    map.put("DISCHARGEEFFICIENCYTWIN", it.DISCHARGEEFFICIENCYTWIN)
                    map.put("DISCHARGEEFFICIENCYTDM", it.DISCHARGEEFFICIENCYTWIN)
                    map.put("ID", it.ID)
                    map.put("LOADINGEFFICIENCY20", it.LOADINGEFFICIENCY20)
                    map.put("LOADINGEFFICIENCY40", it.LOADINGEFFICIENCY40)
                    map.put("LOADINGEFFICIENCYTWIN", it.LOADINGEFFICIENCYTWIN)
                    map.put("LOADINGEFFICIENCYTDM", it.LOADINGEFFICIENCYTWIN)
                    map.put("MOVINGRANGEFROM", it.MOVINGRANGEFROM)
                    map.put("MOVINGRANGETO", it.MOVINGRANGETO)
                    map.put("NAME", it.NAME)
                    map.put("SAFESPAN", it.SAFESPAN)
                    map.put("SEQ", it.SEQ)
                    map.put("SPEED", it.SPEED)
                    map.put("WIDTH", it.WIDTH)
                    def workingTimeRange = it.WORKINGTIMERANGES
                    assert workingTimeRange instanceof List
                    List<Map<String, Object>> listT = new ArrayList<>()
                    workingTimeRange.each {t->
                        Map<String, Object> mapT = new HashMap<String, Object>();
                        mapT.put("ID", t.ID)
                        mapT.put("WORKENDTIME", sdf.format(t.WORKENDTIME))
                        mapT.put("WORKSTARTTIME", sdf.format(t.WORKSTARTTIME))
//                        mapT.put("WORKENDTIME", "2015-06-02 00:59:00")
//                        mapT.put("WORKSTARTTIME", "2015-06-01 10:30:00")
                        listT.add(mapT)
                        map.put("WORKINGTIMERANGES", listT)
                    }
                    list.add(map)
                }
                def builder = new JsonBuilder(list)
                result = builder.toString()
                println result

            }catch (Exception e){
                System.out.println("生成桥机数据json格式时，发现数据异常！")
                isError = true;
                e.printStackTrace()
            }
        }else {
            System.out.println("没有桥机的数据！")
        }
        if(isError) {
            System.out.println("生成桥机数据json格式失败！")
            return null;
        }else {
            System.out.println("生成桥机数据json格式成功！")
            return result
        }
    }

}
