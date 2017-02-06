package mog.processType;

import importDataInfo.PreStowageData;
import mog.entity.MOContainer;
import mog.entity.MOSlot;
import mog.entity.MOSlotBlock;
import mog.entity.MOSlotPosition;

import java.util.List;

/**
 * Created by csw on 2016/8/2 9:45.
 * Explain:
 */
public class PTProcess {

    public static MOSlotBlock PTChooserProcess(List<PreStowageData> preStowageDataList, MOSlotBlock moSlotBlock, List<IProcessType> PTSeq) {
        //将预配的数据初始化到block里面去
        for(PreStowageData preStowageData : preStowageDataList) {
            int bayInt = Integer.valueOf(preStowageData.getVBYBAYID());
            int rowInt = Integer.valueOf(preStowageData.getVRWROWNO());
            int tierInt = Integer.valueOf(preStowageData.getVTRTIERNO());
            MOSlotPosition moSlotPosition = new MOSlotPosition(bayInt, rowInt, tierInt);
            String containerNo = preStowageData.getContainerNum();
            String type = preStowageData.getCTYPECD();
            int weightKg = preStowageData.getWEIGHT() != null ? preStowageData.getWEIGHT() : 0;
            String eof = null;
            String size = preStowageData.getSIZE();
            MOContainer moContainer = new MOContainer(containerNo, type, weightKg, eof, size);
            MOSlot moSlot = new MOSlot(moSlotPosition);
            moSlotBlock.putMOSlot(moSlotPosition, moSlot);
            moSlotBlock.putMOContainer(moSlotPosition, moContainer);
        }
        
        //开始调用生成作业工艺的方法
        PTChooser ptChooser = new PTChooser();
        ptChooser.setMoSlotBlock(moSlotBlock);

//        List<IProcessType> PTSeq = new ArrayList<>();
//        PTSeq.add(new PT20Single());
//        PTSeq.add(new PT20Dual());
//        PTSeq.add(new PT40Single());
//        PTSeq.add(new PT40Dual());

        ptChooser.setPTSeq(PTSeq);

        moSlotBlock = ptChooser.choosePT();

        return moSlotBlock;
    }
}
