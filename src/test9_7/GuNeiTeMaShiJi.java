package test9_7;

import generateResult.*;
import importDataInfo.*;
import importDataProcess.*;
import utils.FileUtil;
import viewFrame.*;

import java.io.File;
import java.util.*;

/**
 * Created by csw on 2016/1/21.
 */
public class GuNeiTeMaShiJi {
    public static void main(String[] args) {

        String filePath = "9.7data/GuNeiTeMaShiJi/";

        String vo = FileUtil.readFileToString(new File(filePath + "Cwpvoyage.txt")).toString();

        String sh = FileUtil.readFileToString(new File(filePath + "vslstr.txt")).toString();

        String cr = FileUtil.readFileToString(new File(filePath + "crane.txt")).toString();

        String co = FileUtil.readFileToString(new File(filePath + "containers.txt")).toString();
//        String co = FileUtil.readFileToString(new File("toTempData/tempContainer.txt")).toString();

        String ca = FileUtil.readFileToString(new File(filePath + "area.txt")).toString();

        //航次
        List<VoyageInfo> voyageInfoList = VoyageInfoProcess.getVoyageInfo(vo);
        VoyageFrame voyageFrame = new VoyageFrame(voyageInfoList);
        voyageFrame.setVisible(true);

        //船舶结构
        List<VesselStructureInfo> vesselStructureInfoList = VesselStructureInfoProcess.getVesselStructureInfo(sh);
        ImportData.vesselStructureInfoList = vesselStructureInfoList;
        VesselStructureFrame vesselStructureFrame = new VesselStructureFrame(vesselStructureInfoList);
        vesselStructureFrame.setVisible(true);

        //测试产生查询倍位绝对坐标的方法
//        Map<String, Double> bayPositionMap = GenerateBayPositionQuery.getBayPositionMap(voyageInfoList, vesselStructureInfoList);

//        //桥机
        List<CraneInfo> craneInfoList = CraneInfoProcess.getCraneInfo(cr);
        CraneFrame craneFrame = new CraneFrame(craneInfoList);
        craneFrame.setVisible(true);

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
        System.out.println("提示信息：" + ExceptionProcess.getExceptionInfo(groupNo));
        GroupFrame groupFrame = new GroupFrame( groupInfoList);
        groupFrame.setVisible(true);

        //实配图
        String pr = FileUtil.readFileToString(new File(filePath + "cwpperstowage.txt")).toString();
        List<PreStowageData> preStowageDataList = PreStowageDataProcess.getPreStowageInfo(pr);
        //测试根据实配图生成预配图
        //将数据放在不同的舱位里
        List<String> VHTIDs = new ArrayList<>();//存放舱位ID
        for(PreStowageData preStowageData : preStowageDataList) {
            if(!VHTIDs.contains(preStowageData.getVHTID())) {
                VHTIDs.add(preStowageData.getVHTID());
            }
        }
        Collections.sort(VHTIDs);
        System.out.println( "舱位数：" + VHTIDs.size());
        Map<String, List<Integer>> workFlowMap = new HashMap<>();
        for (String str : VHTIDs) {
            workFlowMap.put(str, Arrays.asList(1));
        }
        Long moveOrderAndWorkFlowNo = 2L;
        List<PreStowageData> resultList = GenerateMoveOrder.getMoveOrderAndWorkFlow(moveOrderAndWorkFlowNo, preStowageDataList, vesselStructureInfoList, workFlowMap);
        System.out.println("提示信息：" + ExceptionProcess.getExceptionInfo(moveOrderAndWorkFlowNo));
        PreStowageDataFrame preStowageFrame2 = new PreStowageDataFrame(resultList);
        preStowageFrame2.setVisible(true);

        //调用cwp算法得到结果
        Long cwpNo = 3L;
        List<CwpResultInfo> cwpResultInfoList = GenerateCwpResult.getCwpResult(cwpNo, voyageInfoList, vesselStructureInfoList, craneInfoList, resultList);
        System.out.println("提示信息：" + ExceptionProcess.getExceptionInfo(cwpNo));

        //对cwp结果进行处理，将连续作业的cwp块放到一起，以及对作业于某个舱所有的桥机进行编顺序，和某桥机作业舱的顺序
        Long cwpNo1 = 31L;
        List<CwpResultInfo> cwpResultInfoTransformList =  CwpResultInfoTransform.getTransformResult(cwpNo1, cwpResultInfoList);
        System.out.println("提示信息：" + ExceptionProcess.getExceptionInfo(cwpNo1));
        CwpResultFrame cwpResultFrame = new CwpResultFrame(cwpResultInfoTransformList, craneInfoList, null);
        cwpResultFrame.setVisible(true);

        //目前现对cwp结果进行处理，得到每一个Move的输出对象，即对现在算法结果进行拆分
        Long cwpNo2 = 32L;
        List<CwpResultMoveInfo> cwpResultInfoToMoveList = CwpResultInfoToMove.getCwpMoveInfoResult(cwpNo2, cwpResultInfoList, preStowageDataList);
        System.out.println("提示信息：" + ExceptionProcess.getExceptionInfo(cwpNo2));
        CwpResultMoveInfoFrame cwpResultMoveInfoFrame = new CwpResultMoveInfoFrame(cwpResultInfoToMoveList);
        cwpResultMoveInfoFrame.setVisible(true);

        //测试自动配载算法
        Long autoStowNo = 4L;
        List<AutoStowResultInfo> autoStowInfoList = GenerateAutoStowResult.getAutoStowResult(autoStowNo, groupInfoList, containerInfoList, containerAreaInfoList, resultList, cwpResultInfoToMoveList);
        System.out.println("提示信息：" + ExceptionProcess.getExceptionInfo(autoStowNo));
        List<MoveInfo> moveInfoList = GenerateMoveInfoResult.getMoveInfoResult(voyageInfoList, resultList, cwpResultInfoToMoveList, autoStowInfoList);
        MoveFrame moveFrame = new MoveFrame(moveInfoList);
        moveFrame.setVisible(true);

//        //可视化显示配载结果
//        VesselImageFrame vesselImageFrame = new VesselImageFrame(vesselStructureInfoList, autoStowInfoList);
//        vesselImageFrame.setVisible(true);

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
