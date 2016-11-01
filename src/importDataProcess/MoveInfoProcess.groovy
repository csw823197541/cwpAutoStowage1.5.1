package importDataProcess

import groovy.json.JsonSlurper
import importDataInfo.MoveInfo
import importDataInfo.VesselStructureInfo

import java.text.SimpleDateFormat

/**
 * Created by csw on 2016/10/19 15:59.
 * Explain: 
 */
public class MoveInfoProcess {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static List<MoveInfo> getMoveInfoList(String jsonStr) {
        boolean isError = false;
        List<MoveInfo> moveInfoList = new ArrayList<MoveInfo>();

        try{
            def root = new JsonSlurper().parseText(jsonStr)

            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List

            root.each { it ->
                MoveInfo moveInfo = new MoveInfo()
                assert it instanceof Map
                moveInfo.vpcCntrId = it.CRANENO + "@" + it.MOVENUM
                moveInfo.craneNo = it.CRANENO
                moveInfo.moveNum = it.MOVENUM
                moveInfo.moveKind = it.MOVEKIND
                moveInfo.containerId = it.CONTAINERID
                moveInfo.size = it.SIZE
                moveInfo.areaPosition = it.AREAPOSITION
                moveInfo.vesselPosition = it.VESSELPOSITION
                moveInfo.workingStartTime = sdf.parse(String.valueOf(it.WORKINGSTARTTIME))
                moveInfo.workingEndTime = sdf.parse(String.valueOf(it.WORKINGENDTIME))
                moveInfo.workFlow = it.WORKFLOW
                moveInfo.voyId = it.VOYID
                moveInfo.workStatus = it.WORKSTATUS
                moveInfo.workIsExchange = it.WORKISEXCHANGE
                moveInfo.workIsRepeal = it.WORKISREPEAL
                moveInfo.carryOrder = it.CARRYORDER
                moveInfoList.add(moveInfo)
            }
        }catch (Exception e){
            System.out.println("指令数据解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("指令数据解析失败！")
            return null;
        }else {
//            System.out.println("指令数据解析成功！")
            println "指令数据解析成功！";
            return moveInfoList
        }
    }
}
