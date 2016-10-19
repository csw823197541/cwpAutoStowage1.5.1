package mog.processType;

import mog.entity.MOSlot;
import mog.entity.MOSlotBlock;
import mog.entity.MOSlotPosition;

import java.util.Set;

/**
 * Created by liuminhang on 16/4/11.
 */
public interface IProcessType {

    Set<MOSlotPosition> canDo(MOSlot moSlot, MOSlotBlock moSlotBlock);

    String getMoveType();
}
