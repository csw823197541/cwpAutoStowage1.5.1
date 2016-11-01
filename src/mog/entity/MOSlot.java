package mog.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by liuminhang on 16/4/12.
 */
/*
基本的slot对象,即为基本的船舶结构.放有MOContainer,PositionSet等对象
 */
public class MOSlot {


    private MOSlotPosition moSlotPosition;
    private MOContainer moContainer;
    private Set<MOSlotPosition> moSlotPositionSet;
    private Long moveOrderSeq;   //作业序列
    private String moveType;    //作业工艺

    public MOSlot(){}

    public MOSlot(MOSlotPosition moSlotPosition) {
        this.moSlotPosition = moSlotPosition;
        moSlotPositionSet = new HashSet<>();
        moveOrderSeq = -1L;
    }

    public MOContainer getMoContainer() {
        return moContainer;
    }

    public void setMoContainer(MOContainer moContainer) {
        this.moContainer = moContainer;
    }

    public Set<MOSlotPosition> getMoSlotPositionSet() {
        return moSlotPositionSet;
    }

    public void setMoSlotPositionSet(Set<MOSlotPosition> moSlotPositionSet) {
        this.moSlotPositionSet = moSlotPositionSet;
    }

    public MOSlotPosition getMoSlotPosition() {
        return moSlotPosition;
    }

    public Long getMoveOrderSeq() {
        return moveOrderSeq;
    }

    public void setMoveOrderSeq(Long moveOrderSeq) {
        this.moveOrderSeq = moveOrderSeq;
    }

    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }
}
