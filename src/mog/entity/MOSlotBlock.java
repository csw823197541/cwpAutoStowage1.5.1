package mog.entity;

import java.util.*;

/**
 * Created by liuminhang on 16/4/6.
 */
/*
表示整个箱块,例如某舱甲板上所有箱
 */
public class MOSlotBlock {

    private Map<Integer, MOSlotStack> bay01;    //01小贝,即第一个小贝，键值为排号
    private Map<Integer, MOSlotStack> bay03;    //03小贝,即第二个小贝

    private List<Integer> rowSeqList;           //排号顺序,默认偶数侧在前
    private List<Integer> rowSeqList1;           //排号顺序,默认奇数侧在前

    private List<Integer> rowSeqListAsc; //排号由小到大顺序

    private List<Integer> tierNoListAsc;       //层号列表,递增


    private List<MOSlotPosition> slotPositions; //位置列表,包含空舱位

    private int vMaxRowNo = -1, vMinRowNo = 9999;

    private int firstBay=-1,secondBay=-1;


    private MOSlotBlock() {
        bay01 = new HashMap<>();
        bay03 = new HashMap<>();
        rowSeqList = new ArrayList<>();
        rowSeqListAsc = new ArrayList<>();
        tierNoListAsc = new ArrayList<>();
        rowSeqList1 = new ArrayList<>();
    }

    //按照船舶结构构造空的Block
    public static MOSlotBlock buildEmptyMOSlotBlock(List<MOSlotPosition> moSlotPositionList) {
        MOSlotBlock moSlotBlock = new MOSlotBlock();
        moSlotBlock.slotPositions = moSlotPositionList;
        //遍历判断第一个贝位和第二个贝位
        Set<Integer> bayIntegerSet = new HashSet<>();
        for(int i = 0; i < moSlotPositionList.size(); i++){
            bayIntegerSet.add(moSlotPositionList.get(i).getBayInt());
        }
        List<Integer> bayIntegerList = new ArrayList<>(bayIntegerSet);
        Collections.sort(bayIntegerList);
        if(bayIntegerList.size()==1){
           moSlotBlock.firstBay = bayIntegerList.get(0);
        }
        else {
            moSlotBlock.firstBay = bayIntegerList.get(0);
            moSlotBlock.secondBay = bayIntegerList.get(bayIntegerList.size()-1);
        }


        for (int i = 0; i < moSlotPositionList.size(); i++) {
            MOSlotPosition moSlotPosition = moSlotPositionList.get(i);
            MOSlot moSlot = new MOSlot(moSlotPosition);
            //初始化空的MOSlot
            if (moSlotPosition.getBayInt() == moSlotBlock.firstBay) { //第一个小贝
                if (moSlotBlock.bay01.get(moSlotPosition.getRowInt()) != null) {
                    moSlotBlock.bay01.get(moSlotPosition.getRowInt()).putMOSlot(moSlotPosition.getTierInt(), moSlot);
                } else {
                    moSlotBlock.bay01.put(moSlotPosition.getRowInt(), new MOSlotStack());
                    moSlotBlock.bay01.get(moSlotPosition.getRowInt()).putMOSlot(moSlotPosition.getTierInt(), moSlot);
                }
            }
            if (moSlotPosition.getBayInt() == moSlotBlock.secondBay) { //第二个小贝
                if (moSlotBlock.bay03.get(moSlotPosition.getRowInt()) != null) {
                    moSlotBlock.bay03.get(moSlotPosition.getRowInt()).putMOSlot(moSlotPosition.getTierInt(), moSlot);
                } else {
                    moSlotBlock.bay03.put(moSlotPosition.getRowInt(), new MOSlotStack());
                    moSlotBlock.bay03.get(moSlotPosition.getRowInt()).putMOSlot(moSlotPosition.getTierInt(), moSlot);
                }
            }
            if (moSlotPosition.getBayInt() % 4 == 2 || moSlotPosition.getBayInt() % 4 == 0) { //第一和二个小贝
                if (moSlotBlock.bay01.get(moSlotPosition.getRowInt()) != null) {
                    moSlotBlock.bay01.get(moSlotPosition.getRowInt()).putMOSlot(moSlotPosition.getTierInt(), moSlot);
                } else {
                    moSlotBlock.bay01.put(moSlotPosition.getRowInt(), new MOSlotStack());
                    moSlotBlock.bay01.get(moSlotPosition.getRowInt()).putMOSlot(moSlotPosition.getTierInt(), moSlot);
                }
                if (moSlotBlock.bay03.get(moSlotPosition.getRowInt()) != null) {
                    moSlotBlock.bay03.get(moSlotPosition.getRowInt()).putMOSlot(moSlotPosition.getTierInt(), moSlot);
                } else {
                    moSlotBlock.bay03.put(moSlotPosition.getRowInt(), new MOSlotStack());
                    moSlotBlock.bay03.get(moSlotPosition.getRowInt()).putMOSlot(moSlotPosition.getTierInt(), moSlot);
                }
            }
            //处理最大排,最小排
            if (moSlotPosition.getRowInt() > moSlotBlock.vMaxRowNo) {
                moSlotBlock.vMaxRowNo = moSlotPosition.getRowInt();
            }
            if (moSlotPosition.getRowInt() < moSlotBlock.vMinRowNo) {
                moSlotBlock.vMinRowNo = moSlotPosition.getRowInt();
            }
            //添加层号
            if (!moSlotBlock.tierNoListAsc.contains(moSlotPosition.getTierInt())) {
                moSlotBlock.tierNoListAsc.add(moSlotPosition.getTierInt());
            }
        }

        //生成遍历顺序
        //甲板上,从左往右
        for (int i = moSlotBlock.vMaxRowNo % 2 == 0 ? moSlotBlock.vMaxRowNo : moSlotBlock.vMaxRowNo - 1; i >= moSlotBlock.vMinRowNo; i = i - 2) {//偶数侧
            moSlotBlock.rowSeqList.add(i);
            moSlotBlock.rowSeqListAsc.add(i);
        }
        for (int i = moSlotBlock.vMinRowNo % 2 == 0 ? moSlotBlock.vMinRowNo + 1 : moSlotBlock.vMinRowNo; i <= moSlotBlock.vMaxRowNo; i = i + 2) {//奇数侧
            moSlotBlock.rowSeqList.add(i);
            moSlotBlock.rowSeqListAsc.add(i);
        }

        for (int i = moSlotBlock.vMaxRowNo % 2 == 0 ? moSlotBlock.vMaxRowNo - 1 : moSlotBlock.vMaxRowNo; i >= moSlotBlock.vMinRowNo; i = i - 2) {//奇数侧
            moSlotBlock.rowSeqList1.add(i);
        }
        for (int i = moSlotBlock.vMinRowNo % 2 == 0 ? moSlotBlock.vMinRowNo : moSlotBlock.vMinRowNo + 1 ; i <= moSlotBlock.vMaxRowNo; i = i + 2) {//偶数侧
            moSlotBlock.rowSeqList1.add(i);
        }


        //生成遍历顺序，排号从小到大
        Collections.sort(moSlotBlock.rowSeqListAsc);

        //对层号进行排序
        Collections.sort(moSlotBlock.tierNoListAsc);

        return moSlotBlock;
    }

    //反转排遍历顺序


    //按位置填入Slot
    public void putMOSlot(MOSlotPosition moSlotPosition, MOSlot moSlot) {

        if (moSlotPosition.getBayInt() == firstBay) { //第一个小贝
            MOSlotStack moSlotStack = bay01.get(moSlotPosition.getRowInt());
            moSlotStack.putMOSlot(moSlotPosition.getTierInt(), moSlot);
            int topTierNo = moSlotStack.getTopTierNo();
            int bottomTierNo = moSlotStack.getBottomTierNo();
            moSlotStack.setTopTierNo(moSlotPosition.getTierInt() > topTierNo ? moSlotPosition.getTierInt() : topTierNo);
            moSlotStack.setBottomTierNo(moSlotPosition.getTierInt() < bottomTierNo ? moSlotPosition.getTierInt() : bottomTierNo);
        }
        if (moSlotPosition.getBayInt() == secondBay) { //第二个小贝
            MOSlotStack moSlotStack = bay03.get(moSlotPosition.getRowInt());
            moSlotStack.putMOSlot(moSlotPosition.getTierInt(), moSlot);
            int topTierNo = moSlotStack.getTopTierNo();
            int bottomTierNo = moSlotStack.getBottomTierNo();
            moSlotStack.setTopTierNo(moSlotPosition.getTierInt() > topTierNo ? moSlotPosition.getTierInt() : topTierNo);
            moSlotStack.setBottomTierNo(moSlotPosition.getTierInt() < bottomTierNo ? moSlotPosition.getTierInt() : bottomTierNo);
        }
        if (moSlotPosition.getBayInt() % 4 == 2 || moSlotPosition.getBayInt() % 4 == 0) { //第一和二个小贝
            MOSlotStack moSlotStack01 = bay01.get(moSlotPosition.getRowInt());
            moSlotStack01.putMOSlot(moSlotPosition.getTierInt(), moSlot);
            int topTierNo = moSlotStack01.getTopTierNo();
            int bottomTierNo = moSlotStack01.getBottomTierNo();
            moSlotStack01.setTopTierNo(moSlotPosition.getTierInt() > topTierNo ? moSlotPosition.getTierInt() : topTierNo);
            moSlotStack01.setBottomTierNo(moSlotPosition.getTierInt() < bottomTierNo ? moSlotPosition.getTierInt() : bottomTierNo);

            MOSlotStack moSlotStack03 = bay03.get(moSlotPosition.getRowInt());
            moSlotStack03.putMOSlot(moSlotPosition.getTierInt(), moSlot);
            topTierNo = moSlotStack03.getTopTierNo();
            bottomTierNo = moSlotStack03.getBottomTierNo();
            moSlotStack03.setTopTierNo(moSlotPosition.getTierInt() > topTierNo ? moSlotPosition.getTierInt() : topTierNo);
            moSlotStack03.setBottomTierNo(moSlotPosition.getTierInt() < bottomTierNo ? moSlotPosition.getTierInt() : bottomTierNo);
        }

    }


    //按位置填入箱
    public void putMOContainer(MOSlotPosition moSlotPosition, MOContainer moContainer) {

        if (moSlotPosition.getBayInt() == firstBay) { //第一个小贝
            bay01.get(moSlotPosition.getRowInt()).getMOSlot(moSlotPosition.getTierInt()).setMoContainer(moContainer);
        }
        if (moSlotPosition.getBayInt() == secondBay) { //第二个小贝
            bay03.get(moSlotPosition.getRowInt()).getMOSlot(moSlotPosition.getTierInt()).setMoContainer(moContainer);
        }
        if (moSlotPosition.getBayInt() % 4 == 2 || moSlotPosition.getBayInt() % 4 == 0) { //第一和二个小贝
            bay01.get(moSlotPosition.getRowInt()).getMOSlot(moSlotPosition.getTierInt()).setMoContainer(moContainer);
            bay03.get(moSlotPosition.getRowInt()).getMOSlot(moSlotPosition.getTierInt()).setMoContainer(moContainer);
        }

    }

    //按位置读取Slot
    public MOSlot getMOSlot(MOSlotPosition moSlotPosition) {

        MOSlot moSlot = null;
        if (moSlotPosition.getBayInt() == firstBay) { //第一个小贝
            if (bay01.get(moSlotPosition.getRowInt()) != null) {
                moSlot = bay01.get(moSlotPosition.getRowInt()).getMOSlot(moSlotPosition.getTierInt());
            }
        }
        if (moSlotPosition.getBayInt() == secondBay) { //第二个小贝
            if (bay03.get(moSlotPosition.getRowInt()) != null) {
                moSlot = bay03.get(moSlotPosition.getRowInt()).getMOSlot(moSlotPosition.getTierInt());
            }
        }
        if (moSlotPosition.getBayInt() % 4 == 2 || moSlotPosition.getBayInt() % 4 == 0) { //大贝,取第一个小贝
//            System.out.println(moSlotPosition.getBayInt() + "-" + moSlotPosition.getRowInt() + "-" + moSlotPosition.getTierInt());
            if (bay01.get(moSlotPosition.getRowInt()) != null) {
                moSlot = bay01.get(moSlotPosition.getRowInt()).getMOSlot(moSlotPosition.getTierInt());
            }
        }
        return moSlot;

    }

    //获取TierNo List,获取层号,递增
    public List<Integer> getTierNoListAsc() {
        return tierNoListAsc;
    }

    //获取舱位列表
    public List<MOSlotPosition> getSlotPositions() {
        return slotPositions;
    }

    //按排顺序获取某层的MOSlot,返回两个列表组成的数组,分别是第一小贝和第三小贝的List<MOSlot>
    public List<MOSlot> getMOSlotsByTierOn01Bay(int tierNo) {
        List<MOSlot> moSlotList = new ArrayList<>();
        for (int i = 0; i < rowSeqList.size(); i++) {
            if (bay01.get(rowSeqList.get(i)) != null) {
                moSlotList.add(bay01.get(rowSeqList.get(i)).getMOSlot(tierNo));
            }
        }
        return moSlotList;
    }

    public List<MOSlot> getMOSlotsByTierOn03Bay(int tierNo) {
        List<MOSlot> moSlotList = new ArrayList<>();
        for (int i = 0; i < rowSeqList.size(); i++) {
            if (bay03.get(rowSeqList.get(i)) != null) {
                moSlotList.add(bay03.get(rowSeqList.get(i)).getMOSlot(tierNo));
            }
        }
        return moSlotList;
    }

    //根据位置得到对面位置的slot
    public MOSlot getOppositeMOSlot(MOSlotPosition moSlotPosition) {
        int bayInt = moSlotPosition.getBayInt();
        int rowInt = moSlotPosition.getRowInt();
        int tierInt = moSlotPosition.getTierInt();
        //取对面slot的贝位号
        if (bayInt == firstBay) {
            bayInt = bayInt + 2;
        } else if (bayInt == secondBay) {
            bayInt = bayInt - 2;
        }
        MOSlotPosition oppositePosition = new MOSlotPosition(bayInt, rowInt, tierInt);
        MOSlot moSlot = this.getMOSlot(oppositePosition);
        return moSlot;
    }

    //根据位置得到后面位置的一个slot
    public MOSlot getNextMOSlot(MOSlotPosition moSlotPosition) {
        int bayInt = moSlotPosition.getBayInt();
        int rowInt = moSlotPosition.getRowInt();
        int tierInt = moSlotPosition.getTierInt();
        //后面slot的
        //System.out.println(moSlotPosition.getBayInt() + "-" + moSlotPosition.getRowInt() + "-" + moSlotPosition.getTierInt());
        MOSlot moSlot;
        if (rowInt == rowSeqList.get(rowSeqList.size() - 1)) {
            moSlot = null;
        } else {
            int nextRowInt = 9999;
            for (int i = 0; i < rowSeqList.size() - 1; i++) {
                if (rowInt == rowSeqList.get(i)) {
                    nextRowInt = rowSeqList.get(i + 1);
                }
            }
            MOSlotPosition nextPosition = new MOSlotPosition(bayInt, nextRowInt, tierInt);
//            System.out.println("next: " + nextPosition.getBayInt() + "-" + nextPosition.getRowInt() + "-" + nextPosition.getTierInt());
            moSlot = this.getMOSlot(nextPosition);
        }
        return moSlot;
    }

    //得到两个贝位的MOSlotStack的Map
    public Map<Integer, MOSlotStack> getBay01() {
        return bay01;
    }

    public Map<Integer, MOSlotStack> getBay03() {
        return bay03;
    }

    public List<Integer> getRowSeqList() {
        return rowSeqList;
    }

    public List<Integer> getRowSeqList1() {
        return rowSeqList1;
    }

    public List<Integer> getRowSeqListAsc() {
        return rowSeqListAsc;
    }

    //根据MOSlotPosition得到对于的stack
    public MOSlotStack getMOSlotStack(MOSlotPosition moSlotPosition) {

        int bayInt = moSlotPosition.getBayInt();
        int rowInt = moSlotPosition.getRowInt();
        if(bayInt % 4 == 1) {

        }
        return null;
    }

    //得到该位置底层的slot
    public MOSlot getUnderMOSlot(MOSlotPosition moSlotPosition) {
        MOSlot moSlot = new MOSlot();
        int bayInt = moSlotPosition.getBayInt();
        int rowInt = moSlotPosition.getRowInt();
        int tierInt = moSlotPosition.getTierInt();

        if (tierInt > 0) {
            tierInt = tierInt - 2;
            if (bayInt % 4 == 2 || bayInt % 4 == 0) {
                MOSlotPosition underPosition = new MOSlotPosition(bayInt, rowInt, tierInt);
                moSlot = this.getMOSlot(underPosition);
                if (moSlot == null) {
                    bayInt = bayInt + 1;
                    MOSlotPosition underPosition03 = new MOSlotPosition(bayInt, rowInt, tierInt);
                    moSlot = this.getMOSlot(underPosition03);
                }
            } else {
                MOSlotPosition underPosition = new MOSlotPosition(bayInt, rowInt, tierInt);
                moSlot = this.getMOSlot(underPosition);
            }
        }

        return moSlot;
    }

    //得到该位置上层的slot
    public MOSlot getUpMOSlot(MOSlotPosition moSlotPosition) {
        MOSlot moSlot = new MOSlot();
        int bayInt = moSlotPosition.getBayInt();
        int rowInt = moSlotPosition.getRowInt();
        int tierInt = moSlotPosition.getTierInt();

        if(this.tierNoListAsc.size() > 0) {
            if (tierInt < this.tierNoListAsc.get(tierNoListAsc.size()-1)) {
                tierInt = tierInt + 2;
                if (bayInt % 4 == 2 || bayInt % 4 == 0) {
                    MOSlotPosition upPosition = new MOSlotPosition(bayInt, rowInt, tierInt);
                    moSlot = this.getMOSlot(upPosition);
                    if (moSlot == null) {
                        bayInt = bayInt + 1;
                        MOSlotPosition upPosition03 = new MOSlotPosition(bayInt, rowInt, tierInt);
                        moSlot = this.getMOSlot(upPosition03);
                    }
                } else {
                    MOSlotPosition upPosition = new MOSlotPosition(bayInt, rowInt, tierInt);
                    moSlot = this.getMOSlot(upPosition);
                }
            }
        }
        return moSlot;
    }
}
