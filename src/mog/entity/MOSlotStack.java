package mog.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuminhang on 16/4/6.
 */
/*
表示一剁箱位
 */
public class MOSlotStack {
    private int topTierNo;
    private int bottomTierNo;
    private Map<Integer, MOSlot> moSlotMap;//键值为层号


    MOSlotStack() {
        topTierNo = 0;
        bottomTierNo = 1000;
        moSlotMap = new HashMap<>();
    }

    public int getTopTierNo() {
        return topTierNo;
    }

    public void setTopTierNo(int topTierNo) {
        this.topTierNo = topTierNo;
    }

    public int getBottomTierNo() {
        return bottomTierNo;
    }

    public void setBottomTierNo(int bottomTierNo) {
        this.bottomTierNo = bottomTierNo;
    }

    //为空或已满
    public boolean isEmptyOrFull() {
        return topTierNo < bottomTierNo;
    }

    public void putMOSlot(int tierNo, MOSlot moSlot) {
        moSlotMap.put(tierNo, moSlot);
    }

    public MOSlot getMOSlot(int tierNo) {
        return moSlotMap.get(tierNo);
    }

    //栈顶减2
    public void topTierNoDownBy2() {
        this.setTopTierNo(topTierNo - 2);
    }

    //栈底加2
    public void bottomTierNoUpBy2() {
        this.setBottomTierNo(bottomTierNo + 2);
    }

    //获取顶层
    public MOSlot getTopMOSlot() {
        return moSlotMap.get(topTierNo);
    }

    //获取底层
    public MOSlot getBottomMOSlot() {
        return moSlotMap.get(bottomTierNo);
    }
}
