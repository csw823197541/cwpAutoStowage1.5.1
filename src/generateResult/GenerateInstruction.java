package generateResult;

import importDataInfo.MoveInfo;
import importDataProcess.ExceptionData;

import java.util.*;

/**
 * Created by csw on 2016/9/28 10:32.
 * Explain:
 */
public class GenerateInstruction {

    public static List<MoveInfo> getWorkInstruction(Long batchNum,
                                                    List<MoveInfo> moveInfoList,
                                                    Date curInstructionTime,
                                                    Integer timeInterval) {
        ExceptionData.exceptionMap.put(batchNum, "接口方法没有执行。");
        List<MoveInfo> resultList = new ArrayList<>();

        sortByStartTime(moveInfoList); //按计划作业开始时间排序（升序）

        MoveInfo firstMove = findFirstMove(curInstructionTime, moveInfoList);
        long firstST = firstMove.getWorkingStartTime().getTime();

        if (timeInterval == null) { //间隔时间，默认是30分钟
            timeInterval = 30;
        }

        boolean isRight = true;
        String info = "";
        for (MoveInfo moveInfo : moveInfoList) {
            String status = moveInfo.getWorkStatus();
            if ("R".equals(status)) {
                long startTime = moveInfo.getWorkingStartTime().getTime();
                if (startTime >= firstST && startTime <= firstST + timeInterval * 60 * 1000) {
                    if (!isUnderEmpty(moveInfo, moveInfoList) && isWorkFlowOk(moveInfo, moveInfoList)) {
                        resultList.add(moveInfo);
                    } else {
                        isRight = false;
                        info += "指令编号为：" + moveInfo.getVpcCntrId() + "不可作业；";
                    }
                }
            }
        }
        if (isRight) {
            ExceptionData.exceptionMap.put(batchNum, "success! 成功返回可作业的所有指令。");
        } else {
            ExceptionData.exceptionMap.put(batchNum, "error! " + info);
        }

        return resultList;
    }



    private static boolean isUnderEmpty(MoveInfo moveInfo, List<MoveInfo> moveInfoList) {
        boolean isUnderEmpty = false;
        String craneId = moveInfo.getCraneNo();
        Integer moveNum = moveInfo.getMoveNum();
        for (MoveInfo moveInfo1 : moveInfoList) {
            if (craneId.equals(moveInfo1.getCraneNo())) {
                if (moveInfo1.getMoveNum() < moveNum) {
                    if ("?".equals(moveInfo1.getContainerId()) || "".equals(moveInfo1.getWorkStatus())) {
                        isUnderEmpty = true;
                    }
                }
            }
        }
        return isUnderEmpty;
    }

    private static boolean isWorkFlowOk(MoveInfo moveInfo, List<MoveInfo> moveInfoList) {
        boolean isWorkFlowOk = true;
        String craneId = moveInfo.getCraneNo();
        Integer moveNum = moveInfo.getMoveNum();
        String workFlow = moveInfo.getWorkFlow();
        String LD = moveInfo.getMoveKind();
        if ("L".equals(LD)) {
            if ("2".equals(workFlow) || "3".equals(workFlow)) {
                int containerNum = 0;
                for (MoveInfo moveInfo1 : moveInfoList) {
                    if (craneId.equals(moveInfo1.getCraneNo()) && moveNum == moveInfo1.getMoveNum()) {
                        if (!"?".equals(moveInfo1.getContainerId())) {
                            containerNum++;
                        }
                    }
                }
                if (containerNum != 2) {
                    isWorkFlowOk = false;
                }
            }
        }
        return isWorkFlowOk;
    }

    private static MoveInfo findFirstMove(Date curInstructionTime, List<MoveInfo> moveInfoList) {
        MoveInfo firstMove = new MoveInfo();
        for (MoveInfo moveInfo : moveInfoList) {
            if (curInstructionTime.compareTo(moveInfo.getWorkingStartTime()) == -1) {
                firstMove = moveInfo;
                break;
            }
        }
        return firstMove;
    }

    private static List<MoveInfo> sortByStartTime(List<MoveInfo> moveInfoList) {
        Collections.sort(moveInfoList, new Comparator<MoveInfo>() {
            @Override
            public int compare(MoveInfo o1, MoveInfo o2) {
                return o1.getWorkingStartTime().compareTo(o2.getWorkingStartTime());
            }
        });
        return moveInfoList;
    }


}
