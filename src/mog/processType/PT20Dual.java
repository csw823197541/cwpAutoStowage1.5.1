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
public class PT20Dual implements IProcessType {

    private String moveType = "2";  //20尺双箱吊

    @Override
    public Set<MOSlotPosition> canDo(MOSlot moSlot, MOSlotBlock moSlotBlock) {
        Set<MOSlotPosition> moSlotPositionSet = new HashSet<>();

        MOContainer moContainer = moSlot.getMoContainer();
        MOSlotPosition moSlotPosition = moSlot.getMoSlotPosition();
        if("20".equals(moContainer.size)) {
            MOSlot oppositeMOSlot = moSlotBlock.getOppositeMOSlot(moSlotPosition);
            if(oppositeMOSlot != null) {    //船舶结构里对面有slot
                if(oppositeMOSlot.getMoContainer() != null) {   //对面slot有箱子，取对面箱子的属性进行判断
                    MOContainer oppositeContainer = oppositeMOSlot.getMoContainer();
                    if("20".equals(oppositeContainer.size)) {   //对面是20尺的箱子，暂时判断为可以双箱吊
                        if (Math.abs(moContainer.weightKg - oppositeContainer.weightKg) <= 10000
                                && moContainer.type.equals(oppositeContainer.type)) {
                            moSlotPositionSet.add(moSlotPosition);
                            moSlotPositionSet.add(oppositeMOSlot.getMoSlotPosition());
                        }
//                        moSlotPositionSet.add(moSlotPosition);
//                        moSlotPositionSet.add(oppositeMOSlot.getMoSlotPosition());
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
