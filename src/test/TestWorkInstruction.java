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

        String curTimeStr = "2016-08-10 18:39:47";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curTime = sdf.parse(curTimeStr);
        Long workNo = 5L;
        List<MoveInfo> instructionList = GenerateInstruction.getWorkInstruction(workNo, moveInfoList, 30, new HashMap<String, Integer>());
        System.out.println("提示信息：" + ExceptionProcess.getExceptionInfo(workNo));
        MoveFrame1 moveFrame1 = new MoveFrame1(instructionList);
        moveFrame1.setVisible(true);

    }
}
