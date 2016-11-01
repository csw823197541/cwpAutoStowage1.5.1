package generateResult;

import importDataInfo.MoveInfo;
import importDataProcess.ExceptionData;

import java.util.*;

/**
 * Created by csw on 2016/9/28 10:32.
 * Explain:
 */
public class GenerateInstruction {

    private static Map<String, MoveInfo> moveInfoMapD = new HashMap<>();
    private static Map<String, MoveInfo> moveInfoMapL = new HashMap<>();

    public static List<MoveInfo> getWorkInstruction(Long batchNum,
                                                    List<MoveInfo> moveInfoList,
                                                    Integer timeInterval,
                                                    Map<String, Integer> areaRestTask) {
        ExceptionData.exceptionMap.put(batchNum, "接口方法没有执行。");
        List<MoveInfo> resultList = new ArrayList<>();

        sortByStartTime(moveInfoList); //按计划作业开始时间排序（升序）

        //根据船箱位可以得到指令对象
//        Map<String, MoveInfo> moveInfoMapD = new HashMap<>();
//        Map<String, MoveInfo> moveInfoMapL = new HashMap<>();
        for (MoveInfo moveInfo : moveInfoList) {
            if ("D".equals(moveInfo.getMoveKind())) {
                moveInfoMapD.put(moveInfo.getVesselPosition(), moveInfo);
            }
            if ("L".equals(moveInfo.getMoveKind())) {
                moveInfoMapL.put(moveInfo.getVesselPosition(), moveInfo);
            }
        }

        MoveInfo firstMove = findFirstMove(moveInfoList);
        long firstST = firstMove.getWorkingStartTime().getTime();

        if (timeInterval == null) { //间隔时间，默认是30分钟
            timeInterval = 30;
        }

        boolean isRight = true;
        String info = "";
        for (MoveInfo moveInfo : moveInfoList) {
            String status = moveInfo.getWorkStatus();
            if ("Y".equals(status) || "S".equals(status) || "P".equals(status)) {
                long startTime = moveInfo.getWorkingStartTime().getTime();
                if (startTime >= firstST && startTime <= firstST + timeInterval * 60 * 1000) {
                    if (!isUnderEmpty(moveInfo, moveInfoList) && isWorkFlowOk(moveInfo, moveInfoList)) {
                        resultList.add(moveInfo);
                    } else {
                        isRight = false;
                        info += "指令编号为：" + moveInfo.getVpcCntrId() + " 不可作业；";
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
        Long moveNum = moveInfo.getMoveNum();
        String vesselPosition = moveInfo.getVesselPosition();
        for (MoveInfo moveInfo1 : moveInfoList) {
            if (craneId.equals(moveInfo1.getCraneNo())) {
                String vesselPosition1 = moveInfo.getVesselPosition();

            }
        }
        return isUnderEmpty;
    }

    private static boolean isWorkFlowOk(MoveInfo moveInfo, List<MoveInfo> moveInfoList) {
        boolean isWorkFlowOk = true;
        String craneId = moveInfo.getCraneNo();
        Long moveNum = moveInfo.getMoveNum();
        String workFlow = moveInfo.getWorkFlow();
        String LD = moveInfo.getMoveKind();
        if ("L".equals(LD)) {
            if ("2".equals(workFlow) || "3".equals(workFlow)) {
                int containerNum = 0;
                for (MoveInfo moveInfo1 : moveInfoList) {
                    if (craneId.equals(moveInfo1.getCraneNo()) && moveNum.longValue() == moveInfo1.getMoveNum().longValue()) {
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

    private static MoveInfo findFirstMove(List<MoveInfo> moveInfoList) {
        MoveInfo firstMove = new MoveInfo();
        for (MoveInfo moveInfo : moveInfoList) {
            if ("Y".equals(moveInfo.getWorkStatus())
                    || "S".equals(moveInfo.getWorkStatus())
                    || "P".equals(moveInfo.getWorkStatus()) ) {
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
