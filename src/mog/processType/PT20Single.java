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
public class PT20Single implements IProcessType {

    private String moveType = "1";  //20尺单吊具

    @Override
    public Set<MOSlotPosition> canDo(MOSlot moSlot, MOSlotBlock moSlotBlock) {
        Set<MOSlotPosition> moSlotPositionSet = new HashSet<>();

        MOContainer moContainer = moSlot.getMoContainer();
        MOSlotPosition moSlotPosition = moSlot.getMoSlotPosition();
        if("20".equals(moContainer.size)) {
//            MOSlot oppositeMOSlot = moSlotBlock.getOppositeMOSlot(moSlotPosition);
//            if(oppositeMOSlot != null) {//船舶结构里对面有slot
//                if(oppositeMOSlot.getMoContainer() != null) {   //对面slot有箱子，如果不能满足双箱吊的，就表示该slot只能单吊具
//                    MOContainer oppositeContainer = oppositeMOSlot.getMoContainer();
//                    if(!"20".equals(oppositeContainer.size)) {   //对面不是20尺的箱子，表示只能单吊
//                        moSlotPositionSet.add(moSlotPosition);
//                    }
//                } else {    //对面slot没有箱子，表示只能单吊具
//                    moSlotPositionSet.add(moSlotPosition);
//                }
//            } else {    //船舶结构里对面没有slot,表示只能单吊具
//                moSlotPositionSet.add(moSlotPosition);
//            }
            moSlotPositionSet.add(moSlotPosition);
        }

        return moSlotPositionSet;
    }

    @Override
    public String getMoveType() {
        return moveType;
    }
}
