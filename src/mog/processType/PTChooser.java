package mog.processType;

import mog.entity.MOSlot;
import mog.entity.MOSlotBlock;
import mog.entity.MOSlotPosition;

import java.util.List;
import java.util.Set;

/**
 * Created by liuminhang on 16/4/11.
 */
public class PTChooser {

    private MOSlotBlock moSlotBlock;

    private List<IProcessType> PTSeq;

    public PTChooser() {

    }

    public MOSlotBlock choosePT() { //选择作业工艺

        //Block层遍历,按层顺序
        for (int i = 0; i < moSlotBlock.getTierNoListAsc().size(); i++) {
            int tierNo = moSlotBlock.getTierNoListAsc().get(i); //获取层号
            //这层按顺序遍历
            List<MOSlot> moSlotList01Bay = moSlotBlock.getMOSlotsByTierOn01Bay(tierNo);
            //按序判断作业工艺
            for(MOSlot moSlot : moSlotList01Bay) {
                if(moSlot != null) {
                    if(moSlot.getMoContainer() != null && moSlot.getMoSlotPositionSet().isEmpty()) {
                        PT:for (IProcessType iProcessType : PTSeq) {
                            Set<MOSlotPosition> moSlotPositionSet = iProcessType.canDo(moSlot, moSlotBlock);
                            if(!moSlotPositionSet.isEmpty()) { //给定作业工艺、同一作业工艺的slot互存位置信息
                                String moveType = iProcessType.getMoveType();   //当前处理方法的作业工艺
                                moSlot.setMoSlotPositionSet(moSlotPositionSet); //当前slot保存位置信息
                                moSlot.setMoveType(moveType);
                                //遍历与当前slot同一个作业工艺的所有slot，将位置信息保存
                                for(MOSlotPosition moSlotPosition : moSlotPositionSet) {
                                    MOSlot sameMoveTypeMoSlot = moSlotBlock.getMOSlot(moSlotPosition);
                                    sameMoveTypeMoSlot.setMoSlotPositionSet(moSlotPositionSet);
                                    sameMoveTypeMoSlot.setMoveType(moveType);
                                }
//                                break PT;
                            }
                        }
                    }
                }
            }

            List<MOSlot> moSlotList03Bay = moSlotBlock.getMOSlotsByTierOn03Bay(tierNo);
            //按序判断作业工艺
            for(MOSlot moSlot : moSlotList03Bay) {
                if(moSlot != null) {
                    if(moSlot.getMoContainer() != null && moSlot.getMoSlotPositionSet().isEmpty()) {
                        PT:for (IProcessType iProcessType : PTSeq) {
                            Set<MOSlotPosition> moSlotPositionSet = iProcessType.canDo(moSlot, moSlotBlock);
                            if(moSlotPositionSet != null) { //给定作业工艺、同一作业工艺的slot互存位置信息
                                String moveType = iProcessType.getMoveType();   //当前处理方法的作业工艺
                                moSlot.setMoSlotPositionSet(moSlotPositionSet); //当前slot保存位置信息
                                moSlot.setMoveType(moveType);
                                //遍历与当前slot同一个作业工艺的所有slot，将位置信息保存
                                for(MOSlotPosition moSlotPosition : moSlotPositionSet) {
                                    MOSlot sameMoveTypeMoSlot = moSlotBlock.getMOSlot(moSlotPosition);
                                    sameMoveTypeMoSlot.setMoSlotPositionSet(moSlotPositionSet);
                                    sameMoveTypeMoSlot.setMoveType(moveType);
                                }
                                break PT;
                            }
                        }
                    }
                }
            }
        }

        return moSlotBlock;

    }

    public MOSlotBlock getMoSlotBlock() {
        return moSlotBlock;
    }

    public void setMoSlotBlock(MOSlotBlock moSlotBlock) {
        this.moSlotBlock = moSlotBlock;
    }

    public List<IProcessType> getPTSeq() {
        return PTSeq;
    }

    public void setPTSeq(List<IProcessType> PTSeq) {
        this.PTSeq = PTSeq;
    }
}
