package test1_4;

import generateResult.GenerateAutoStowResult1;
import generateResult.GenerateGroupResult;
import importDataInfo.*;
import importDataProcess.*;
import utils.FileUtil;
import viewFrame.ContainerFrame;
import viewFrame.CwpResultMoveInfoFrame;
import viewFrame.PreStowageDataFrame;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by csw on 2016/1/21.
 */
public class TestAutoStow2_6 {
    public static void main(String[] args) {

        String filePath = "2.6AutoStow/";

//        String vo = FileUtil.readFileToString(new File(filePath + "CwpVoyage.txt")).toString();

        String co = FileUtil.readFileToString(new File(filePath + "AutoStowContainers.txt")).toString();

        String ca = FileUtil.readFileToString(new File(filePath + "AutoArea.txt")).toString();

        //航次
//        List<VoyageInfo> voyageInfoList = VoyageInfoProcess.getVoyageInfo(vo);
//        VoyageFrame voyageFrame = new VoyageFrame(voyageInfoList);
//        voyageFrame.setVisible(true);

//        //在场箱
        List<ContainerInfo> containerInfoList = ContainerInfoProcess.getContainerInfo(co);
        ContainerFrame containerFrame = new ContainerFrame(containerInfoList);
        containerFrame.setVisible(true);

//        //箱区
        List<ContainerAreaInfo> containerAreaInfoList = ContainerAreaInfoProcess.getContainerAreaInfo(ca);
//        ContainerAreaFrame containerAreaFrame = new ContainerAreaFrame(containerAreaInfoList);
//        containerAreaFrame.setVisible(true);

//        //属性组
        Long groupNo = 1L;
        List<GroupInfo> groupInfoList = GenerateGroupResult.getGroupResult(groupNo, containerInfoList);
//        GroupFrame groupFrame = new GroupFrame( groupInfoList);
//        groupFrame.setVisible(true);

        //实配图
        String pr = FileUtil.readFileToString(new File(filePath + "AutoStowPerStowCntr.txt")).toString();
        List<PreStowageData> resultList = PreStowageDataProcess.getPreStowageInfo(pr);
        PreStowageDataFrame preStowageFrame = new PreStowageDataFrame(resultList);
        preStowageFrame.setVisible(true);

        //为了测试数据，从文件中读取cwp结果
        String cwpRe = FileUtil.readFileToString(new File(filePath + "AutoCwpRe.txt")).toString();
        List<CwpResultMoveInfo> cwpResultInfoToMoveList = CwpResultMoveInfoProcess.getCwpResultMoveInfoList(cwpRe);
        //cwpResultMoveInfoList = sortByStartTime(cwpResultMoveInfoList); //按时间排序
        CwpResultMoveInfoFrame cwpResultMoveInfoFrame1 = new CwpResultMoveInfoFrame(cwpResultInfoToMoveList);
        cwpResultMoveInfoFrame1.setVisible(true);

        //测试自动配载算法
        Long autoStowNo = 4L;
        List<AutoStowResultInfo1> autoStowInfoList = GenerateAutoStowResult1.getAutoStowResult(autoStowNo, groupInfoList, containerInfoList, containerAreaInfoList, resultList, cwpResultInfoToMoveList);
        System.out.println("提示信息：" + ExceptionProcess.getExceptionInfo(autoStowNo));

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
