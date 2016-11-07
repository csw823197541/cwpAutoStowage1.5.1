package test;

import generateResult.GenerateCwpResult;
import generateResult.GenerateInstruction;
import generateResult.GenerateMoveOrder;
import importDataInfo.*;
import importDataProcess.*;
import utils.FileUtil;
import viewFrame.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by csw on 2016/1/21.
 */
public class TestWorkInstruction {
    public static void main(String[] args) throws Exception{

        String filePath = "10.18data/";

        String wi = FileUtil.readFileToString(new File(filePath + "CWPWI.txt")).toString();

        List<MoveInfo> moveInfoList = MoveInfoProcess.getMoveInfoList(wi);
        MoveFrame1 moveFrame = new MoveFrame1(moveInfoList);
        moveFrame.setVisible(true);

        String timeStr1 = "2016-11-03 00:00:00";
        String timeStr2 = "2016-11-03 01:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<CraneWorkSTInfo> craneWorkSTInfoList = new ArrayList<>();
        CraneWorkSTInfo craneWorkSTInfo1 = new CraneWorkSTInfo();
        craneWorkSTInfo1.setCraneNo("103");
        craneWorkSTInfo1.setWorkStartTime(sdf.parse(timeStr1));
        CraneWorkSTInfo craneWorkSTInfo2 = new CraneWorkSTInfo();
        craneWorkSTInfo2.setCraneNo("102");
        craneWorkSTInfo2.setWorkStartTime(sdf.parse(timeStr2));
        craneWorkSTInfoList.add(craneWorkSTInfo1);
        craneWorkSTInfoList.add(craneWorkSTInfo2);

        Long workNo = 5L;
        List<MoveInfo> instructionList = GenerateInstruction.getWorkInstruction(workNo, moveInfoList, 30, craneWorkSTInfoList, null, null);
        System.out.println("提示信息：" + ExceptionProcess.getExceptionInfo(workNo));
        MoveFrame1 moveFrame1 = new MoveFrame1(instructionList);
        moveFrame1.setVisible(true);

    }
}
