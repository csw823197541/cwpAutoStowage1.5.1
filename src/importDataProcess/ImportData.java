package importDataProcess;

import importDataInfo.PreStowageData;
import importDataInfo.VesselStructureInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by leko on 2016/1/21.
 */
public class ImportData {

    public static Map<String, List<String>> moveOrderRecords;      //舱.作业序列.作业工艺确定具体位置

    public static List<Integer> movecounts;     //每个舱move数

    public static Map<String, Integer> moveCountQuery;//根据舱ID查找该舱的moveCount数

    public static Map<String, Double> bayPositionMap;//倍位中心绝对位置

    public static Map<String,String[]> autoStowResult; //自动配载结果,key:船箱位；value：0放箱区位置，1放箱号，2放尺寸


    public static List<VesselStructureInfo> vesselStructureInfoList; //船舶结构

    public static Map<String, List<PreStowageData>> preStowageDataMap;  //预配位对应的箱子

}
