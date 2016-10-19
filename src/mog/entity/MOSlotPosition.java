package mog.entity;

/**
 * Created by liuminhang on 16/4/8.
 */
/*
用于标记舱的位置,由贝位bay,排row,层tier组成,例如 050682,060784
String类型由5.6.82表示
 */
public class MOSlotPosition {

    //数字类型表示
    private int bayInt;
    private int rowInt;
    private int tierInt;

    public MOSlotPosition(int inBayInt, int inRowInt, int inTierInt) {
        this.bayInt = inBayInt;
        this.rowInt = inRowInt;
        this.tierInt = inTierInt;
    }

    //
    public MOSlotPosition(String inPosStrByDot) throws Exception {
        String[] poss = inPosStrByDot.split("\\.");
        bayInt = Integer.parseInt(poss[0]);
        rowInt = Integer.parseInt(poss[1]);
        tierInt = Integer.parseInt(poss[2]);

    }

    public String getPosStrByDot() {
        return "" + bayInt + "." + rowInt + "." + tierInt;
    }

    public int getBayInt() {
        return bayInt;
    }

    public int getRowInt() {
        return rowInt;
    }

    public int getTierInt() {
        return tierInt;
    }
}
