package generateResult;

import importDataInfo.AreaRestTaskInfo;
import importDataInfo.AreaToCraneInfo;
import importDataInfo.CraneWorkSTInfo;
import importDataInfo.MoveInfo;
import importDataProcess.ExceptionData;

import java.util.*;

/**
 * Created by csw on 2016/9/28 10:32.
 * Explain:
 */
public class
        GenerateInstruction {

    public static List<MoveInfo> getWorkInstruction(Long batchNum,
                                                    List<MoveInfo> moveInfoList,
                                                    Integer timeInterval,
                                                    List<CraneWorkSTInfo> craneWorkSTInfoList,
                                                    List<AreaRestTaskInfo> areaRestTaskInfoList,
                                                    List<AreaToCraneInfo> areaToCraneInfoList) {
        ExceptionData.exceptionMap.put(batchNum, "接口方法没有执行。");
        List<MoveInfo> resultList = new ArrayList<>();

        changeTimeToCraneWorkST(moveInfoList, craneWorkSTInfoList);

        sortByStartTime(moveInfoList); //按计划作业开始时间排序（升序）

        MoveInfo firstMove = findFirstMove(moveInfoList);
        if (firstMove != null) {
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
                    long endTime = moveInfo.getWorkingEndTime().getTime();
                    long workTime = endTime - startTime;
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
        } else {
            ExceptionData.exceptionMap.put(batchNum, "info! 当前没有可以发送的指令");
        }


        return resultList;
    }

    private static void changeTimeToCraneWorkST(List<MoveInfo> moveInfoList, List<CraneWorkSTInfo> craneWorkSTInfoList) {
        Map<String, CraneWorkSTInfo> craneWorkSTInfoMap = new HashMap<>();
        Map<String, List<MoveInfo>> moveInfoMap = new HashMap<>();
        for (CraneWorkSTInfo craneWorkSTInfo : craneWorkSTInfoList) {
            craneWorkSTInfoMap.put(craneWorkSTInfo.getCraneNo(), craneWorkSTInfo);
        }

        for (MoveInfo moveInfo : moveInfoList) {
            if (!moveInfoMap.containsKey(moveInfo.getCraneNo())) {
                moveInfoMap.put(moveInfo.getCraneNo(), new ArrayList<MoveInfo>());
                moveInfoMap.get(moveInfo.getCraneNo()).add(moveInfo);
            } else {
                moveInfoMap.get(moveInfo.getCraneNo()).add(moveInfo);
            }
        }

        Map<String, Long> firstSTMap = new HashMap<>();
        for (String craneNo : moveInfoMap.keySet()) {
            sortByStartTime(moveInfoMap.get(craneNo));//按开始时间升序
            MoveInfo firstMove = moveInfoMap.get(craneNo).get(0);//取最早时间指令
            firstSTMap.put(craneNo, firstMove.getWorkingStartTime().getTime());
        }

        for (MoveInfo moveInfo : moveInfoList) {
            String craneNo = moveInfo.getCraneNo();
            long startTime = moveInfo.getWorkingStartTime().getTime();
            long endTime = moveInfo.getWorkingEndTime().getTime();
            long workTime = endTime - startTime;
            if (craneWorkSTInfoMap.containsKey(craneNo)) {
                Date workST = craneWorkSTInfoMap.get(craneNo).getWorkStartTime();
                long workSTime = workST.getTime();
                long firstStartTime = firstSTMap.get(craneNo);
                long stNew = workSTime + startTime - firstStartTime;//新的开始时间
                long edNew = stNew + workTime;//新的结束时间
                moveInfo.setWorkingStartTime(new Date(stNew));
                moveInfo.setWorkingEndTime(new Date(edNew));
            }
        }
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
        String hatchId = moveInfo.getVesselPosition().substring(0, 1);
        String workFlow = moveInfo.getWorkFlow();
        String LD = moveInfo.getMoveKind();
        Date stTime = moveInfo.getWorkingStartTime();
        if ("L".equals(LD)) {
            if ("2".equals(workFlow) || "3".equals(workFlow)) {
                int containerNum = 0;
                for (MoveInfo moveInfo1 : moveInfoList) {
                    if (craneId.equals(moveInfo1.getCraneNo()) && moveNum.longValue() == moveInfo1.getMoveNum().longValue() && hatchId.equals(moveInfo1.getVesselPosition().substring(0, 1))) {
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
        MoveInfo firstMove = null;
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
