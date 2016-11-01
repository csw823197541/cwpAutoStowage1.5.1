package test;

import generateResult.GenerateAutoStowResult;
import generateResult.GenerateGroupResult;
import generateResult.GenerateMoveInfoResult;
import importDataInfo.*;
import importDataProcess.ContainerAreaInfoProcess;
import importDataProcess.ContainerInfoProcess;
import importDataProcess.CwpResultMoveInfoProcess;
import importDataProcess.PreStowageDataProcess;
import utils.FileUtil;
import viewFrame.*;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by csw on 2016/1/21.
 */
public class TestAutoStow {
    public static void main(String[] args) {

        String filePath = "10.25.1data/";

        String co = FileUtil.readFileToString(new File(filePath + "containers.txt")).toString();

        String ca = FileUtil.readFileToString(new File(filePath + "area.txt")).toString();

//        //在场箱
        List<ContainerInfo> containerInfoList = ContainerInfoProcess.getContainerInfo(co);
        ContainerFrame containerFrame = new ContainerFrame(containerInfoList);
        containerFrame.setVisible(true);

//        //箱区
        List<ContainerAreaInfo> containerAreaInfoList = ContainerAreaInfoProcess.getContainerAreaInfo(ca);
        ContainerAreaFrame containerAreaFrame = new ContainerAreaFrame(containerAreaInfoList);
        containerAreaFrame.setVisible(true);

//        //属性组
        Long groupNo = 1L;
        List<GroupInfo> groupInfoList = GenerateGroupResult.getGroupResult(groupNo, containerInfoList);
        GroupFrame groupFrame = new GroupFrame( groupInfoList);
        groupFrame.setVisible(true);

        //实配图
        String pr = FileUtil.readFileToString(new File(filePath + "perstowandstow.txt")).toString();
        List<PreStowageData> resultList = PreStowageDataProcess.getPreStowageInfo(pr);
        PreStowageDataFrame preStowageFrame = new PreStowageDataFrame(resultList);
        preStowageFrame.setVisible(true);
        //测试根据实配图生成预配图
//        List<PreStowageData> resultList = GeneratePreStowageFromKnowStowage6.getPreStowageResult(preStowageDataList);
//        System.out.println(resultList.size());
//        PreStowageDataFrame preStowageFrame2 = new PreStowageDataFrame(resultList);
//        preStowageFrame2.setVisible(true);

        //调用cwp算法得到结果
//        List<CwpResultInfo> cwpResultInfoList = GenerateCwpResult.getCwpResult(voyageInfoList, vesselStructureInfoList, craneInfoList, resultList);

        //对cwp结果进行处理，将连续作业的cwp块放到一起，以及对作业于某个舱所有的桥机进行编顺序，和某桥机作业舱的顺序
//        List<CwpResultInfo> cwpResultInfoTransformList =  CwpResultInfoTransform.getTransformResult(cwpResultInfoList);
//        CwpResultFrame cwpResultFrame = new CwpResultFrame(cwpResultInfoTransformList, craneInfoList, null);
//        cwpResultFrame.setVisible(true);

        //目前现对cwp结果进行处理，得到每一个Move的输出对象，即对现在算法结果进行拆分
//        List<CwpResultMoveInfo> cwpResultInfoToMoveList = CwpResultInfoToMove.getCwpMoveInfoResult(cwpResultInfoList, preStowageDataList);
//        //cwpResultInfoToMoveList = sortByStartTime(cwpResultInfoToMoveList); //按时间排序
//        CwpResultMoveInfoFrame cwpResultMoveInfoFrame = new CwpResultMoveInfoFrame(cwpResultInfoToMoveList);
//        cwpResultMoveInfoFrame.setVisible(true);

        //为了测试数据，从文件中读取cwp结果
        String cwpRe = FileUtil.readFileToString(new File(filePath + "cwpRe.txt")).toString();
        List<CwpResultMoveInfo> cwpResultInfoToMoveList = CwpResultMoveInfoProcess.getCwpResultMoveInfoList(cwpRe);
        //cwpResultMoveInfoList = sortByStartTime(cwpResultMoveInfoList); //按时间排序
        CwpResultMoveInfoFrame cwpResultMoveInfoFrame1 = new CwpResultMoveInfoFrame(cwpResultInfoToMoveList);
        cwpResultMoveInfoFrame1.setVisible(true);

        //测试自动配载算法
        Long autoStowNo = 2L;
//        List<AutoStowResultInfo> autoStowInfoList = GenerateAutoStowResult.getAutoStowResult(autoStowNo, groupInfoList, containerInfoList, containerAreaInfoList, resultList, cwpResultInfoToMoveList);

//        List<MoveInfo> moveInfoList = GenerateMoveInfoResult.getMoveInfoResult(voyageInfoList, resultList, cwpResultInfoToMoveList, autoStowInfoList);
//        MoveFrame moveFrame = new MoveFrame(moveInfoList);
//        moveFrame.setVisible(true);

    }

    private static List<CwpResultMoveInfo> sortByStartTime(List<CwpResultMoveInfo> valueList) {

        Collections.sort(valueList, new Comparator<CwpResultMoveInfo>() {
            @Override
            public int compare(CwpResultMoveInfo o1, CwpResultMoveInfo o2) {
                return o1.getWorkingStartTime().compareTo(o2.getWorkingStartTime());
            }
        });

        return valueList;
    }

}
