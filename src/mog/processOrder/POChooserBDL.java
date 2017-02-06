package mog.processOrder;

import mog.entity.*;
import mog.processType.WorkType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liuminhang on 2017/2/6.
 * 用于处理甲板下边装边卸
 */
public class POChooserBDL {
    //判断MOSlotBlock中所有箱子是否都被编序
    public boolean isAllMOSlotStackEmpty(MOSlotBlock moSlotBlock) {
        boolean result = false;

        for (MOSlotPosition moSlotPosition : moSlotBlock.getSlotPositions()) {
            MOSlot moSlot = moSlotBlock.getMOSlot(moSlotPosition);
            if (moSlot != null) {
                if (moSlot.getMoveType() != null) {
                    if (moSlot.getMoveOrderSeq() == -1) {
                        result = true;
                    }
                }
            }
        }

        return result;
    }

    public void processOrderBDL(MOSlotBlock moSlotBlockBD,MOSlotBlock moSlotBlockBL, WorkType[] workTypes,int rowEachTime){

        Map<Integer, MOSlotStack> bay01d = moSlotBlockBD.getBay01();
        Map<Integer, MOSlotStack> bay03d = moSlotBlockBD.getBay03();
        Map<Integer, MOSlotStack> bay01l = moSlotBlockBL.getBay03();
        Map<Integer, MOSlotStack> bay03l = moSlotBlockBL.getBay03();

        int n = 0;
        WW:while (isAllMOSlotStackEmpty(moSlotBlockBD)&&isAllMOSlotStackEmpty(moSlotBlockBL)) {
            //取作业工艺
            int i = 0;
            int dlRowCounts = 2;
            //从里到外，先做最多n排卸船，每做一个，从里至外判断整排是否有装船。有，则做一个装船。
            //当n排卸船完成之后，n变为2n,但不大于最大排数
            //边装边卸优于作业工艺切换

            //获取rowseq


            while (i < workTypes.length) {
                WorkType wt = workTypes[i];//当前作业工艺




            }

            n++;
            if (n > 10000) {
                System.out.println("Out of the endless loop (DL)");
                break WW;
            }
        }

    }


    private List<MOSlot> getBottomMOSlotList(Map<Integer, MOSlotStack> bay, MOSlotBlock moSlotBlock, boolean isBefore) {
        List<MOSlot> moSlotList = new ArrayList<>();

        List<Integer> rowSeqList;
//        if (isBefore) {//甲板下的遍历顺序
//            rowSeqList = moSlotBlock.getRowSeqListAsc();
//        } else {
//            rowSeqList = moSlotBlock.getRowSeqList();
//        }

        rowSeqList = moSlotBlock.getRowSeqList();
//        if ("JHYS2017".equals(ImportData.vesselId)) {
//            if (bay.get(1) != null) {
//                int bayInt = moSlotBlock.getSlotPositions().get(0).getBayInt();
//                if (bayInt > 10) {
//                    rowSeqList = moSlotBlock.getRowSeqList1();
//                }
//            }
//        }

        for (int j = 0; j <rowSeqList.size(); j++) {
            int row = rowSeqList.get(j);
            MOSlotStack moSlotStack = bay.get(row);
            if (moSlotStack != null) {
                MOSlot moSlotBottom = moSlotStack.getBottomMOSlot();
                if (moSlotBottom != null) {
                    moSlotList.add(moSlotBottom);
                }
            }
        }
        return moSlotList;
    }

    public List<MOSlot> getTopMOSlotList(Map<Integer, MOSlotStack> bay, MOSlotBlock moSlotBlock, boolean isBefore) {
        List<MOSlot> moSlotList = new ArrayList<>();

        List<Integer> rowSeqList;
//        if (isBefore == true) {//甲板下的遍历顺序
//            rowSeqList = moSlotBlock.getRowSeqListAsc();
//        } else {
//            rowSeqList = moSlotBlock.getRowSeqList();
//        }

        rowSeqList = moSlotBlock.getRowSeqList();
//        if ("JHYS2017".equals(ImportData.vesselId)) {
//            if (bay.get(1) != null) {
//                int bayInt = moSlotBlock.getSlotPositions().get(0).getBayInt();
//                if (bayInt > 10) {
//                    rowSeqList = moSlotBlock.getRowSeqList1();
//                }
//            }
//        }

        for (int j = 0; j < rowSeqList.size(); j++) {
            int row = rowSeqList.get(j);
            MOSlotStack moSlotStack = bay.get(row);
            if (moSlotStack != null) {
                MOSlot moSlotTop = moSlotStack.getTopMOSlot();
                if (moSlotTop != null) {
                    moSlotList.add(moSlotTop);
                }
            }
        }
        return moSlotList;
    }

    //判断栈顶有没有该作业工艺的slot
    public boolean isContinueSameTPTop(WorkType wt, MOSlotBlock moSlotBlock) {
        boolean result = false;

        Map<Integer, MOSlotStack> bay01 = moSlotBlock.getBay01();
        Map<Integer, MOSlotStack> bay03 = moSlotBlock.getBay03();

        for (MOSlotStack moSlotStack : bay01.values()) {
            MOSlot moSlot = moSlotStack.getTopMOSlot();
            if (moSlot != null) {
                if (moSlot.getMoveOrderSeq() == -1) {//没有编过MoveOrder
                    MOContainer moContainer = moSlot.getMoContainer();
                    Set<MOSlotPosition> moSlotPositionSet = moSlot.getMoSlotPositionSet();
                    if (moContainer != null && !moSlotPositionSet.isEmpty()) {
                        if (moContainer.size.startsWith(wt.size) && wt.n == moSlotPositionSet.size()) {
                            result = true;
                        }
                    } else {
                        moSlotStack.topTierNoDownBy2();
                    }
                } else {
                    moSlotStack.topTierNoDownBy2();
                }
            }
        }

        for (MOSlotStack moSlotStack : bay03.values()) {
            MOSlot moSlot = moSlotStack.getTopMOSlot();
            if (moSlot != null) {
                if (moSlot.getMoveOrderSeq() == -1) {//没有编过MoveOrder
                    MOContainer moContainer = moSlot.getMoContainer();
                    Set<MOSlotPosition> moSlotPositionSet = moSlot.getMoSlotPositionSet();
                    if (moContainer != null && !moSlotPositionSet.isEmpty()) {
                        if (moContainer.size.startsWith(wt.size) && wt.n == moSlotPositionSet.size()) {
                            result = true;
                        }
                    } else {
                        moSlotStack.topTierNoDownBy2();
                    }
                } else {
                    moSlotStack.topTierNoDownBy2();
                }
            }
        }

        return result;
    }

    //判断栈底有没有该作业工艺的slot
    public boolean isContinueSameTPBottom(WorkType wt, MOSlotBlock moSlotBlock) {
        boolean result = false;

        Map<Integer, MOSlotStack> bay01 = moSlotBlock.getBay01();
        Map<Integer, MOSlotStack> bay03 = moSlotBlock.getBay03();

        for (MOSlotStack moSlotStack : bay01.values()) {
            MOSlot moSlot = moSlotStack.getBottomMOSlot();
            if (moSlot != null) {
                if (moSlot.getMoveOrderSeq() == -1) {//没有编过MoveOrder
                    MOContainer moContainer = moSlot.getMoContainer();
                    Set<MOSlotPosition> moSlotPositionSet = moSlot.getMoSlotPositionSet();
                    if (moContainer != null && !moSlotPositionSet.isEmpty()) {
                        if (moContainer.size.startsWith(wt.size) && wt.n == moSlotPositionSet.size()) {
                            result = true;
                        }
                    } else {
                        moSlotStack.bottomTierNoUpBy2();
                    }
                } else {
                    moSlotStack.bottomTierNoUpBy2();
                }
            }
        }

        for (MOSlotStack moSlotStack : bay03.values()) {
            MOSlot moSlot = moSlotStack.getBottomMOSlot();
            if (moSlot != null) {
                if (moSlot.getMoveOrderSeq() == -1) {//没有编过MoveOrder
                    MOContainer moContainer = moSlot.getMoContainer();
                    Set<MOSlotPosition> moSlotPositionSet = moSlot.getMoSlotPositionSet();
                    if (moContainer != null && !moSlotPositionSet.isEmpty()) {
                        if (moContainer.size.startsWith(wt.size) && wt.n == moSlotPositionSet.size()) {
                            result = true;
                        }
                    } else {
                        moSlotStack.bottomTierNoUpBy2();
                    }
                } else {
                    moSlotStack.bottomTierNoUpBy2();
                }
            }
        }

        return result;
    }
}
