package generateResult;

import importDataInfo.ContainerInfo;
import importDataInfo.GroupInfo;
import importDataProcess.ExceptionData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by leko on 2016/1/21.
 */
public class GenerateGroupResult {

    //生成属性组
    public static List<GroupInfo> getGroupResult(Long batchNum,
                                                 List<ContainerInfo> containerInfoList){

        List<GroupInfo> groupInfoList = new ArrayList<GroupInfo>();

        Set<String> cPortSet = new HashSet<String>();   //包含港口类型
        Set<String> cTypeSet = new HashSet<String>();  //包含箱型类型
        Set<String> cSizeSet = new HashSet<String>();   //包含尺寸类型

        ExceptionData.exceptionMap.put(batchNum, "接口方法未执行。");
        boolean isRight = true;
        String info = "";
        try{
            System.out.println("开始生成分组属性");
            for (ContainerInfo containerInfo : containerInfoList) {
                String port = containerInfo.getIYCPORTCD();
                cPortSet.add(port);                                      //统计港口类型
                String type = containerInfo.getIYCCTYPECD();
                cTypeSet.add(type);                                      //统计箱型类型
                String size = containerInfo.getIYCCSZCSIZECD();
                cSizeSet.add(size);                                      //统计尺寸类型
            }

            Integer groupNum=1;
            GroupInfo groupInfo;
            for (String port: cPortSet){
                for (String type:cTypeSet){
                    for (String size: cSizeSet) {
                        String groupID = "G" + groupNum.toString();
                        groupNum++;
                        //向groupInfoList增加数据
                        groupInfo = new GroupInfo();
                        groupInfo.setGroupID(groupID);
                        groupInfo.setPort(port);
                        groupInfo.setSize(size);
                        groupInfo.setType(type);
                        groupInfoList.add(groupInfo);
                    }
                }
            }
        } catch (Exception e) {
            isRight = false;
            info += "接口方法发生在场箱数据为null异常。";
        }
        if (isRight) {
            ExceptionData.exceptionMap.put(batchNum, "success! 接口方法没有发生异常。");
        } else {
            ExceptionData.exceptionMap.put(batchNum, "error! " + info);
        }

        return groupInfoList;
    }
}
