package mog.processType;

import mog.entity.MOContainer;
import mog.entity.MOSlot;
import mog.entity.MOSlotBlock;
import mog.entity.MOSlotPosition;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by liuminhang on 16/4/11.
 */
public class PT40Dual implements IProcessType {

    private String moveType = "3";

    @Override
    public Set<MOSlotPosition> canDo(MOSlot moSlot, MOSlotBlock moSlotBlock) {
        Set<MOSlotPosition> moSlotPositionSet = new HashSet<>();

        MOContainer moContainer = moSlot.getMoContainer();
        MOSlotPosition moSlotPosition = moSlot.getMoSlotPosition();
        if(moContainer != null && moSlotPosition != null) {
            if(moContainer.size.startsWith("4")) {
                MOSlot nextMOSlot = moSlotBlock.getNextMOSlot(moSlotPosition);
                if(nextMOSlot != null) {    //船舶结构里后面slot存在
                    if(nextMOSlot.getMoContainer() != null) {   //后面slot有箱子，如果不能满足双吊具的，就表示该slot只能单吊具
                        MOContainer nextContainer = nextMOSlot.getMoContainer();
                        if(nextContainer.size.startsWith("4")) {   //后面是40尺或45尺的箱子，暂时表示可以做双吊具
                            if (Math.abs(moContainer.weightKg - nextContainer.weightKg) <= 10000
                                    && moContainer.type.equals(nextContainer.type)) {
                                moSlotPositionSet.add(moSlotPosition);
                                moSlotPositionSet.add(nextMOSlot.getMoSlotPosition());
                            }
//                            moSlotPositionSet.add(moSlotPosition);
//                            moSlotPositionSet.add(nextMOSlot.getMoSlotPosition());
                        }
                    }
                }
            }
        }

        return moSlotPositionSet;
    }

    @Override
    public String getMoveType() {
        return moveType;
    }
}
