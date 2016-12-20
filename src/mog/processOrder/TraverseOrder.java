package mog.processOrder;

import mog.entity.MOSlot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by csw on 2016/8/23 14:19.
 * Explain:
 */
public class TraverseOrder {

    public static List<Integer> getDRowList(List<MOSlot> moSlotList) {
        List<Integer> rowList = new ArrayList<>();
        if (moSlotList != null) {
            moSlotList = sortByTierDESC(moSlotList);
            for (MOSlot moSlot : moSlotList) {
                rowList.add(moSlot.getMoSlotPosition().getRowInt());
            }
        }
        return rowList;
    }

    public static List<Integer> getLRowList(List<MOSlot> moSlotList) {
        List<Integer> rowList = new ArrayList<>();
        if (moSlotList != null) {
            moSlotList = sortByTierASC(moSlotList);
            for (MOSlot moSlot : moSlotList) {
                rowList.add(moSlot.getMoSlotPosition().getRowInt());
            }
        }
        return rowList;
    }

    private static List<MOSlot> sortByTierASC(List<MOSlot> moSlotList) {
        Collections.sort(moSlotList, new Comparator<MOSlot>() {
            @Override
            public int compare(MOSlot o1, MOSlot o2) {
                return o1.getMoSlotPosition().getTierInt() < o2.getMoSlotPosition().getTierInt() ? -1 : (o1.getMoSlotPosition().getTierInt() == o2.getMoSlotPosition().getTierInt() ? 0 : 1);
            }
        });

        return moSlotList;
    }

    private static List<MOSlot> sortByTierDESC(List<MOSlot> moSlotList) {
        Collections.sort(moSlotList, new Comparator<MOSlot>() {
            @Override
            public int compare(MOSlot o1, MOSlot o2) {
                return o1.getMoSlotPosition().getTierInt() > o2.getMoSlotPosition().getTierInt() ? -1 : (o1.getMoSlotPosition().getTierInt() == o2.getMoSlotPosition().getTierInt() ? 0 : 1);
            }
        });

        return moSlotList;
    }

}
