package generateResult

import autoStow.CallAutoStow
import groovy.json.JsonSlurper
import importDataInfo.*
import importDataProcess.AutoStowInputProcess
import importDataProcess.ExceptionData
import utils.FileUtil

/**
 * Created by leko on 2016/1/22.
 */
public class GenerateAutoStowResult1 {

    //调用自动配载
    public
    static List<AutoStowResultInfo1> getAutoStowResult(Long batchNum,
                                                      List<GroupInfo> groupInfoList,
                                                      List<ContainerInfo> containerInfoList,
                                                      List<ContainerAreaInfo> containerAreaInfoList,
                                                      List<PreStowageData> preStowageDataList,
                                                      List<CwpResultMoveInfo> cwpResultMoveInfoList) {
        ExceptionData.exceptionMap.put(batchNum, "接口没有执行。");
        List<AutoStowResultInfo1> autoStowResultInfoList = new ArrayList<AutoStowResultInfo1>();

        //处理在场箱信息
        String containerStr = AutoStowInputProcess.getContainerJsonStr(batchNum, containerInfoList);

        //处理箱区信息
        String containerAreaStr = AutoStowInputProcess.getContainerAreaJsonStr(batchNum, containerAreaInfoList);

        //处理预配信息
        String preStowageStr = AutoStowInputProcess.getPreStowageJsonStr(batchNum, preStowageDataList);

        try {//将自动配载要用的结果写在文件里，让算法去读这个文件
            FileUtil.writeToFile("toAutoStowJsonData/Container.txt", containerStr);
            FileUtil.writeToFile("toAutoStowJsonData/PreStowage.txt", preStowageStr);
            FileUtil.writeToFile("toAutoStowJsonData/ContainerArea.txt", containerAreaStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //处理cwp输出信息
        String cwpResultStr = AutoStowInputProcess.getCwpResultJsonStr(batchNum, cwpResultMoveInfoList);

        try {//将自动配载要用的结果写在文件里
            FileUtil.writeToFile("toAutoStowJsonData/CwpOutput.txt", cwpResultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String autoStowStr = null;
        if (containerStr != null && containerAreaStr != null && preStowageStr != null && cwpResultStr != null) {
            //调用自动配载算法
            autoStowStr = CallAutoStow.autoStow(containerStr, containerAreaStr, preStowageStr, cwpResultStr);

            try {//将自动配载要用的结果写在文件里
                FileUtil.writeToFile("toAutoStowJsonData/autoStowResult.txt", autoStowStr);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("自动配载算法返回的结果：" + autoStowStr);
            if (autoStowStr != null) {
                if(!autoStowStr.startsWith("loadDataError")) {
                    autoStowResultInfoList = getAutoStowResult(batchNum, autoStowStr, preStowageDataList, containerInfoList);
                    ExceptionData.exceptionMap.put(batchNum, "success! 自动配载算法成功返回结果。")
                } else {
                    ExceptionData.exceptionMap.put(batchNum, "error! 算法检查输入属性组数据时，发现如下异常：" + autoStowStr);
                }
            } else {
                ExceptionData.exceptionMap.put(batchNum, "error! 自动配载算法发现未知异常，没有返回结果。")
                System.out.println("自动配载算法没有返回结果！");
            }
        } else {
            ExceptionData.exceptionMap.put(batchNum, "error! 自动配载算法需要的4个参数信息中有空的，不能调用算法。")
            System.out.println("自动配载算法需要的4个参数信息中有空的，不能调用算法！");
        }
        return autoStowResultInfoList;
    }


    public
    static List<AutoStowResultInfo1> getAutoStowResult(Long batchNum, String autoStowStr, List<PreStowageData> preStowageDataList, List<ContainerInfo> containerInfoList) {

        List<AutoStowResultInfo1> autoStowResultInfoList = new ArrayList<>();
        boolean isError = false;

        try {

            Map<String, ContainerInfo> containerInfoMap = new HashMap<>();
            for (ContainerInfo containerInfo : containerInfoList) {
                containerInfoMap.put(containerInfo.getContainerId(), containerInfo);
            }

            //将预配信息进行处理，根据船箱位得到卸船的箱号信息
            Map<String, PreStowageData> preStowageDataMapL = new HashMap<>();
            for (PreStowageData preStowageData : preStowageDataList) {
                String bayId = preStowageData.getVBYBAYID();    //倍号
                String rowId = preStowageData.getVRWROWNO();    //排号
                String tieId = preStowageData.getVTRTIERNO();   //层号
                String vp = bayId + rowId + tieId;
                if ("L".equals(preStowageData.getLDULD())) {
                    if (!preStowageDataMapL.containsKey(vp)) {
                        preStowageDataMapL.put(vp, preStowageData);
                    }
                }
            }

            def root = new JsonSlurper().parseText(autoStowStr);
            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List

            root.each { autoStowResult ->
                AutoStowResultInfo1 autoStowResultInfo = new AutoStowResultInfo1();
                assert autoStowResult instanceof Map
                String[] vesselLoc = autoStowResult.vesselLoc.split("%");
                String containerID = autoStowResult.containerID;
                String yardLoc = autoStowResult.yardLoc;
                String group = autoStowResult.Xpro;
                Integer stowWeight = Integer.valueOf(autoStowResult.weight);
                String weightLevel = autoStowResult.WeightLevel;
                String unStowedReason = yardLoc;//场箱位记录未配载的原因
                String vesselPosition = vesselLoc[1] + "" + vesselLoc[2] + "" + vesselLoc[3];//key,船上的位置：倍排层
                String size = "";
                Integer preWeight = null;
                if (preStowageDataMapL.get(vesselPosition) != null) {
                    size = preStowageDataMapL.get(vesselPosition).getSIZE();
                    preWeight = preStowageDataMapL.get(vesselPosition).getWEIGHT();
                }
                String containerNum = null;
                Long voyId = null;
                if (containerID == null) {
                    containerID = "?";
                    yardLoc = "?";
                } else {
                    containerNum = containerInfoMap.get(containerID).getIYCCNTRNO();
                    voyId = containerInfoMap.get(containerID).getIYCVOYID();
                }
                if(!yardLoc.startsWith("unStowed")) {
//                    yardLoc = yardLoc.split("%")[0] + "" + yardLoc.split("%")[1] + "" + yardLoc.split("%")[2] + "" + yardLoc.split("%")[3];
                yardLoc = yardLoc.replace("%", "");
                }
                autoStowResultInfo.setAreaPosition(yardLoc);
                autoStowResultInfo.setSize(size);
                autoStowResultInfo.setContainerId(containerID);
                autoStowResultInfo.setContainerNum(containerNum)
                autoStowResultInfo.setVesselPosition(vesselPosition);
                autoStowResultInfo.setVoyId(voyId);
                autoStowResultInfo.setUnStowedReason(unStowedReason);
                autoStowResultInfo.setGroup(group);
                autoStowResultInfo.setPreWeight(preWeight);
                autoStowResultInfo.setStowWeight(stowWeight);
                autoStowResultInfo.setWeightLevel(weightLevel);
                autoStowResultInfoList.add(autoStowResultInfo);
            }
        } catch (Exception e) {
            System.out.println("自动配载返回结果数据解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if (isError) {
            System.out.println("自动配载返回结果数据解析失败！")
            ExceptionData.exceptionMap.put(batchNum, "自动配载返回结果数据解析时，发现json数据异常！")
            return null;
        } else {
            System.out.println("自动配载返回结果数据解析成功！")
            return autoStowResultInfoList
        }
    }
}