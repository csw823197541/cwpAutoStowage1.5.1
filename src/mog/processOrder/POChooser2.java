package mog.processOrder;

import importDataProcess.ImportData;
import mog.entity.*;
import mog.processType.WorkType;

import java.util.*;

/**
 * Created by csw on 2016/8/2 9:31.
 * Explain: 编写作业序列
 */
public class POChooser2 {

    private Long seq;//作业顺序

    public POChooser2() {
        this.seq = 1L;
    }

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

    public List<MOSlot> getTopMOSlotList(Map<Integer, MOSlotStack> bay, MOSlotBlock moSlotBlock, boolean isBefore) {
        List<MOSlot> moSlotList = new ArrayList<>();

        List<Integer> rowSeqList;
        if (isBefore == true) {//甲板下的遍历顺序
            rowSeqList = moSlotBlock.getRowSeqListAsc();
        } else {
            rowSeqList = moSlotBlock.getRowSeqList();
        }

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

    //处理卸船编MoveOrder的过程
    public int processD(WorkType wt, Map<Integer, MOSlotStack> bay, MOSlotBlock moSlotBlock, boolean isBefore) {

        List<MOSlot> moSlotList = this.getTopMOSlotList(bay, moSlotBlock, isBefore);

        List<Integer> rowList = TraverseOrder.getDRowList(moSlotList);

        int count = 0;//对几个slot进行了编序号
        F:for (int j = 0; j < rowList.size(); j++) {
            int row = rowList.get(j);
            MOSlotStack moSlotStack = bay.get(row);
            MOSlot moSlotTop = moSlotStack.getTopMOSlot();
            if (moSlotTop.getMoveOrderSeq() == -1) {
                MOContainer moContainer = moSlotTop.getMoContainer();
                Set<MOSlotPosition> moSlotPositionSet = moSlotTop.getMoSlotPositionSet();
                if (moContainer != null && !moSlotPositionSet.isEmpty()) {
                    if (moContainer.size.startsWith(wt.size) && wt.n == moSlotPositionSet.size()) {
                        boolean isMOYes = true;//是否可以编序
                        for (MOSlotPosition moSlotPosition : moSlotPositionSet) {
                            //判断该位置上层是否有箱子没有编序,即上面有箱子还没有卸
                            MOSlot upSlot = moSlotBlock.getUpMOSlot(moSlotPosition);
                            if (upSlot != null) {
                                if (upSlot.getMoContainer() != null && upSlot.getMoveOrderSeq() == -1) {
                                    isMOYes = false;
                                    System.out.println("the up container is not null and none moveOrder: " + moSlotPosition.getBayInt() + "--" + moSlotPosition.getRowInt() + "--" + moSlotPosition.getTierInt());
                                }
                            }
                        }
                        if (isMOYes) {
                            //从moSlotPositionSet中先获取slot，然后编序编MoveOrder
                            for (MOSlotPosition moSlotPosition : moSlotPositionSet) {
                                MOSlot moSlot = moSlotBlock.getMOSlot(moSlotPosition);
                                moSlot.setMoveOrderSeq(seq);
                            }
                            seq++;
                            count++;
                            //编完序后，栈顶标记下移
                            moSlotStack.topTierNoDownBy2();
                            break F;
                        }
                    }
                }
            } else {//已经编了序号，下移栈顶
                moSlotStack.topTierNoDownBy2();
            }
        }

        return count;
    }

    private List<MOSlot> getBottomMOSlotList(Map<Integer, MOSlotStack> bay, MOSlotBlock moSlotBlock, boolean isBefore) {
        List<MOSlot> moSlotList = new ArrayList<>();

        List<Integer> rowSeqList;
        if (isBefore) {//甲板下的遍历顺序
            rowSeqList = moSlotBlock.getRowSeqListAsc();
        } else {
            rowSeqList = moSlotBlock.getRowSeqList();
        }

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

    //处理装船编moveOrder的过程
    private int processL(WorkType wt, Map<Integer, MOSlotStack> bay, MOSlotBlock moSlotBlock, boolean isBefore) {

        List<MOSlot> moSlotList = this.getBottomMOSlotList(bay, moSlotBlock, isBefore);

        List<Integer> rowList = TraverseOrder.getLRowList(moSlotList);

        int count = 0;//对几个slot进行了编序号
        F:for (int j = 0; j < rowList.size(); j++) {
            int row = rowList.get(j);
            MOSlotStack moSlotStack = bay.get(row);
            MOSlot moSlotBottom = moSlotStack.getBottomMOSlot();
            if (moSlotBottom.getMoveOrderSeq() == -1) {
                MOContainer moContainer = moSlotBottom.getMoContainer();
                Set<MOSlotPosition> moSlotPositionSet = moSlotBottom.getMoSlotPositionSet();
                if (moContainer != null && !moSlotPositionSet.isEmpty()) {
                    if (moContainer.size.startsWith(wt.size) && wt.n == moSlotPositionSet.size()) {
                        boolean isMOYes = true; //是否可以编序
                        for (MOSlotPosition moSlotPosition : moSlotPositionSet) {
                            //判断该位置底层是否有箱子没有编序,即下面有箱子还没有装
                            MOSlot underSlot = moSlotBlock.getUnderMOSlot(moSlotPosition);
                            if (underSlot != null) {
                                if (underSlot.getMoContainer() != null && underSlot.getMoveOrderSeq() == -1) {
                                    isMOYes = false;
                                    System.out.println("the under container is not null and none moveOrder: " + moSlotPosition.getBayInt() + "--" + moSlotPosition.getRowInt() + "--" + moSlotPosition.getTierInt());
                                }
                            }
                        }
                        if (isMOYes) {//下面没有未编序的箱子，表示当前箱子可以进行编序
                            //从moSlotPositionSet中先获取slot，然后编序编MoveOrder
                            for (MOSlotPosition moSlotPosition : moSlotPositionSet) {
                                MOSlot moSlot = moSlotBlock.getMOSlot(moSlotPosition);
                                moSlot.setMoveOrderSeq(seq);
                            }
                            seq++;
                            count++;
                            //编完序后，栈底标记上移
                            moSlotStack.bottomTierNoUpBy2();
                            break F;
                        }
                    }
                }
            } else {//已经编了序号，栈底标记上移
                moSlotStack.bottomTierNoUpBy2();
                count++;
                break F;
            }
        }

        return count;
    }

    public MOSlotBlock processOrderAD(MOSlotBlock moSlotBlock, WorkType[] workTypes, boolean isBefore) {

        Map<Integer, MOSlotStack> bay01 = moSlotBlock.getBay01();
        Map<Integer, MOSlotStack> bay03 = moSlotBlock.getBay03();

//        WorkType[] workTypes = new WorkType[]{new WorkType(1, "2"), new WorkType(1, "4"),
//                new WorkType(2, "4"), new WorkType(2, "2"), new WorkType(2, "4"), new WorkType(1, "4"), new WorkType(1, "2")};
//        WorkType[] workTypes = new WorkType[]{new WorkType(1, "2"), new WorkType(1, "4"),
//                new WorkType(2, "2"), new WorkType(1, "4"), new WorkType(1, "2")};

        int n = 0;
        WW:while (isAllMOSlotStackEmpty(moSlotBlock)) {
            //对栈顶元素进行编序
            int i = 0;
            while (i < workTypes.length) {
                WorkType wt = workTypes[i];
                if (isContinueSameTPTop(wt, moSlotBlock)) {
                    int count01 = -1, count03 = -1;
                    W:while (isContinueSameTPTopBay(wt, bay01)) {
                        count01= this.processD(wt, bay01, moSlotBlock, isBefore);
                        if (count01 == 0) {
                            break W;
                        }
                    }
                    W:while (isContinueSameTPTopBay(wt, bay03)) {
                        count03= this.processD(wt, bay03, moSlotBlock, isBefore);
                        if (count03 == 0) {
                            break W;
                        }
                    }
                    if (count01 == 0 || count03 == 0) {
                        i = 0;
                    } else {
                        if (!isContinueSameTPTop(wt, moSlotBlock)) {
                            i++;
                        }
                    }
                } else {
                    i++;
                }
            }

            n++;
            if (n > 10000) {
                System.out.println("Out of the endless loop (D)");
                break WW;
            }
        }

        return moSlotBlock;
    }

    private boolean isContinueSameTPTopBay(WorkType wt, Map<Integer, MOSlotStack> bay) {
        boolean result = false;

        for (MOSlotStack moSlotStack : bay.values()) {
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

    public MOSlotBlock processOrderBD(MOSlotBlock moSlotBlock) {

        return moSlotBlock;
    }

    public MOSlotBlock processOrderBL(MOSlotBlock moSlotBlock, WorkType[] workTypes, boolean isBefore) {

        Map<Integer, MOSlotStack> bay01 = moSlotBlock.getBay01();
        Map<Integer, MOSlotStack> bay03 = moSlotBlock.getBay03();

//        WorkType[] workTypes = new WorkType[]{new WorkType(1, "2"), new WorkType(2, "2"),
//                new WorkType(1, "4"), new WorkType(2, "4"), new WorkType(1, "4"), new WorkType(2, "2"), new WorkType(1, "2")};
//        WorkType[] workTypes = new WorkType[]{new WorkType(1, "2"), new WorkType(2, "2"),
//                new WorkType(1, "4"), new WorkType(2, "2"), new WorkType(1, "2")};

        int n = 0;
        WW:while (isAllMOSlotStackEmpty(moSlotBlock)) {
            int i = 0;
            while (i < workTypes.length) {
                WorkType wt = workTypes[i];
                if (isContinueSameTPBottom(wt, moSlotBlock)) {
                    int count01 = -1, count03 = -1;
                    W:while (isContinueSameTPBottomBay(wt, bay01)) {
                        count01= this.processL(wt, bay01, moSlotBlock, isBefore);
                        if (count01 == 0) {
                            break W;
                        }
                    }
                    W:while (isContinueSameTPBottomBay(wt, bay03)) {
                        count03= this.processL(wt, bay03, moSlotBlock, isBefore);
                        if (count03 == 0) {
                            break W;
                        }
                    }
                    if (count01 == 0 || count03 == 0) {
                        i = 0;
                    } else {
                        if (!isContinueSameTPBottom(wt, moSlotBlock)) {
                            i++;
                        }
                    }
                } else {
                    i++;
                }
            }

            n++;
            if (n > 10000) {
                System.out.println("Out of the endless loop (L)");
                break WW;
            }
        }

        return moSlotBlock;
    }

    private boolean isContinueSameTPBottomBay(WorkType wt, Map<Integer, MOSlotStack> bay) {
        boolean result = false;

        for (MOSlotStack moSlotStack : bay.values()) {
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
                }
            }
        }

        return result;
    }

    public MOSlotBlock processOrderAL(MOSlotBlock moSlotBlock) {

        return moSlotBlock;
    }
}
