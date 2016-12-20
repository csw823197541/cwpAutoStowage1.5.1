package generateResult;

import importDataInfo.PreStowageData;
import importDataInfo.VesselStructureInfo;
import importDataInfo.VoyageInfo;
import importDataProcess.ExceptionData;
import importDataProcess.ImportData;
import mog.entity.MOSlot;
import mog.entity.MOSlotBlock;
import mog.entity.MOSlotPosition;
import mog.processOrder.POChooser2;
import mog.processType.*;

import java.util.*;

/**
 * Created by csw on 2016/8/3 18:00.
 * Explain:
 */
public class GenerateMoveOrder {

    public static List<PreStowageData> getMoveOrderAndWorkFlow(Long batchNum,
                                                               List<VoyageInfo> voyageInfoList,
                                                               List<PreStowageData> preStowageDataList,
                                                               List<VesselStructureInfo> vesselStructureInfoList,
                                                               Map<String, List<Integer>> workFlowMap) {
        ExceptionData.exceptionMap.put(batchNum, "编MoveOrder和作业工艺方法没有执行。");
        List<PreStowageData> preStowageDataListResult = new ArrayList<>();

        List<PreStowageData> preStowageDataListNew = new ArrayList<>();
        for (PreStowageData preStowageData : preStowageDataList) {
            if ("N".equals(preStowageData.getTHROUGHFLAG()) || preStowageData.getTHROUGHFLAG() == null) {
                preStowageDataListNew.add(preStowageData);
            }
        }
        System.out.println("总共有多少个位置(过境箱除外)：" + preStowageDataListNew.size());
        //将数据放在不同的舱位里
        List<String> VHTIDs = new ArrayList<>();//存放舱位ID
        for (PreStowageData preStowageData : preStowageDataListNew) {
            if (!VHTIDs.contains(preStowageData.getVHTID())) {
                VHTIDs.add(preStowageData.getVHTID());
            }
        }
        Collections.sort(VHTIDs);
        System.out.println("舱位数：" + VHTIDs.size());

        Map<String, List<PreStowageData>> stringListMap1 = new HashMap<>();//放在不同的舱位的预配数据
        Map<String, List<VesselStructureInfo>> stringListMap2 = new HashMap<>();//放在不同的舱位的船舶结构数据
        for (String str : VHTIDs) {
            List<PreStowageData> dataList1 = new ArrayList<>();
            for (PreStowageData preStowageData : preStowageDataListNew) {
                if (str.equals(preStowageData.getVHTID())) {
                    dataList1.add(preStowageData);
                }
            }
            stringListMap1.put(str, dataList1);
            //不同舱位的船舶结构
            List<VesselStructureInfo> dataList2 = new ArrayList<>();
            for (VesselStructureInfo vesselStructureInfo : vesselStructureInfoList) {
                if (str.equals(vesselStructureInfo.getVHTID())) {
                    dataList2.add(vesselStructureInfo);
                }
            }
            stringListMap2.put(str, dataList2);
        }

        boolean isRight = true;
        String errorHatchStr = "";
        for (String str : VHTIDs) {//逐舱遍历
            try {
                List<PreStowageData> preStowageList = stringListMap1.get(str);

                //按装卸船、甲板上下分开
                List<PreStowageData> preStowageListAD = new ArrayList<>();
                List<PreStowageData> preStowageListBD = new ArrayList<>();
                List<PreStowageData> preStowageListAL = new ArrayList<>();
                List<PreStowageData> preStowageListBL = new ArrayList<>();
                for (PreStowageData preStowageData : preStowageList) {
                    if ("L".equals(preStowageData.getLDULD()) && 50 < Integer.valueOf(preStowageData.getVTRTIERNO())) {
                        preStowageListAL.add(preStowageData);
                    }
                    if ("L".equals(preStowageData.getLDULD()) && 50 > Integer.valueOf(preStowageData.getVTRTIERNO())) {
                        preStowageListBL.add(preStowageData);
                    }
                    if ("D".equals(preStowageData.getLDULD()) && 50 < Integer.valueOf(preStowageData.getVTRTIERNO())) {
                        preStowageListAD.add(preStowageData);
                    }
                    if ("D".equals(preStowageData.getLDULD()) && 50 > Integer.valueOf(preStowageData.getVTRTIERNO())) {
                        preStowageListBD.add(preStowageData);
                    }
                }

                //根据船舶结构初始化block
                List<VesselStructureInfo> vesselStructureList = stringListMap2.get(str);
                List<MOSlotPosition> moSlotPositionList = new ArrayList<>();
                for (VesselStructureInfo vesselStructureInfo : vesselStructureList) {
                    int bayInt = Integer.valueOf(vesselStructureInfo.getVBYBAYID());
                    int rowInt = Integer.valueOf(vesselStructureInfo.getVRWROWNO());
                    int tierInt = Integer.valueOf(vesselStructureInfo.getVTRTIERNO());
                    moSlotPositionList.add(new MOSlotPosition(bayInt, rowInt, tierInt));
                }

                MOSlotBlock initMOSlotBlockAD = MOSlotBlock.buildEmptyMOSlotBlock(moSlotPositionList);
                MOSlotBlock initMOSlotBlockBD = MOSlotBlock.buildEmptyMOSlotBlock(moSlotPositionList);
                MOSlotBlock initMOSlotBlockBL = MOSlotBlock.buildEmptyMOSlotBlock(moSlotPositionList);
                MOSlotBlock initMOSlotBlockAL = MOSlotBlock.buildEmptyMOSlotBlock(moSlotPositionList);

                //判断靠泊方向，true为正向（奇数排号靠近海侧）
                boolean isPositive = true;
                if ("L".equals(voyageInfoList.get(0).getAnchorDirection())) {
                    ImportData.isPositive = false;
                    isPositive = false;
                    Collections.reverse(initMOSlotBlockBL.getRowSeqList());
                    Collections.reverse(initMOSlotBlockAL.getRowSeqList());
                } else {
                    Collections.reverse(initMOSlotBlockAD.getRowSeqList());
                    Collections.reverse(initMOSlotBlockBD.getRowSeqList());
                }

                //指定舱的作业工艺------开始
                //获得该舱选择的作业工艺
                int type1_20_40 = 0, type2_20 = 0, type2_40 = 0;
                List<Integer> workFlowList = workFlowMap.get(str);
                List<IProcessType> PTSeq = new ArrayList<>();
                for (Integer type : workFlowList) {
                    if (type == 1) {
                        type1_20_40 = 1;
                    }
                    if (type == 2) {
                        type2_20 = 1;
                    }
                    if (type == 3) {
                        type2_40 = 1;
                    }
                }
                //得到对该舱编作业工艺的顺序
                WorkType[] workTypesD = null;
                WorkType[] workTypesL = null;
                if (type1_20_40 == 1 && type2_20 == 1 && type2_40 == 0) {
                    PTSeq.add(new PT20Single());
                    PTSeq.add(new PT40Single());
                    PTSeq.add(new PT20Dual());
                    workTypesD = new WorkType[]{new WorkType(1, "2"), new WorkType(1, "4"),
                            new WorkType(2, "2"), new WorkType(1, "4"), new WorkType(1, "2")};
                    workTypesL = new WorkType[]{new WorkType(1, "2"), new WorkType(2, "2"),
                            new WorkType(1, "4"), new WorkType(2, "2"), new WorkType(1, "2")};
                } else if (type1_20_40 == 1 && type2_20 == 1 && type2_40 == 1) {
                    PTSeq.add(new PT20Single());
                    PTSeq.add(new PT40Single());
                    PTSeq.add(new PT20Dual());
                    PTSeq.add(new PT40Dual());
                    workTypesD = new WorkType[]{new WorkType(1, "2"), new WorkType(1, "4"),
                            new WorkType(2, "4"), new WorkType(2, "2"), new WorkType(2, "4"),
                            new WorkType(1, "4"), new WorkType(1, "2")};
                    workTypesL = new WorkType[]{new WorkType(1, "2"), new WorkType(2, "2"),
                            new WorkType(1, "4"), new WorkType(2, "4"), new WorkType(1, "4"),
                            new WorkType(2, "2"), new WorkType(1, "2")};
                } else if (type1_20_40 == 1 && type2_20 == 0 && type2_40 == 0) {
                    PTSeq.add(new PT20Single());
                    PTSeq.add(new PT40Single());
                    workTypesD = new WorkType[]{new WorkType(1, "2"), new WorkType(1, "4"),
                            new WorkType(1, "4"), new WorkType(1, "2")};
                    workTypesL = new WorkType[]{new WorkType(1, "2"), new WorkType(1, "4"),
                            new WorkType(1, "4"), new WorkType(1, "2")};
                } else if (type1_20_40 == 1 && type2_20 == 0 && type2_40 == 1) {
                    PTSeq.add(new PT20Single());
                    PTSeq.add(new PT40Single());
                    PTSeq.add(new PT40Dual());
                    workTypesD = new WorkType[]{new WorkType(1, "2"), new WorkType(1, "4"),
                            new WorkType(2, "4"), new WorkType(1, "4"), new WorkType(1, "2")};
                    workTypesL = new WorkType[]{new WorkType(1, "2"), new WorkType(1, "4"),
                            new WorkType(2, "4"), new WorkType(1, "4"), new WorkType(1, "2")};
                } else { //没有指定作业工艺，按单吊、双箱吊、双吊具都有处理
                    PTSeq.add(new PT20Single());
                    PTSeq.add(new PT40Single());
                    PTSeq.add(new PT20Dual());
                    PTSeq.add(new PT40Dual());
                    workTypesD = new WorkType[]{new WorkType(1, "2"), new WorkType(1, "4"),
                            new WorkType(2, "4"), new WorkType(2, "2"), new WorkType(2, "4"),
                            new WorkType(1, "4"), new WorkType(1, "2")};
                    workTypesL = new WorkType[]{new WorkType(1, "2"), new WorkType(2, "2"),
                            new WorkType(1, "4"), new WorkType(2, "4"), new WorkType(1, "4"),
                            new WorkType(2, "2"), new WorkType(1, "2")};
                }
                //指定舱的作业工艺------结束

//                //判断靠泊方向，true为正向（奇数排号靠近海侧）
//                boolean isPositive = true;
//                if ("R".equals(voyageInfoList.get(0).getAnchorDirection())) {
//                    ImportData.isPositive = false;
//                    isPositive = false;
//                }

                //对甲板上卸船的block调用生成作业工艺的方法
                MOSlotBlock moSlotBlockAD = PTProcess.PTChooserProcess(preStowageListAD, initMOSlotBlockAD, PTSeq);
                //对甲板上卸船的block调用编MoveOrder的方法
                POChooser2 poChooser = new POChooser2();
                poChooser.processOrderAD(moSlotBlockAD, workTypesD, false);

                MOSlotBlock moSlotBlockBD = PTProcess.PTChooserProcess(preStowageListBD, initMOSlotBlockBD, PTSeq);
                poChooser.processOrderAD(moSlotBlockBD, workTypesD, true);

                MOSlotBlock moSlotBlockBL = PTProcess.PTChooserProcess(preStowageListBL, initMOSlotBlockBL, PTSeq);
                poChooser.processOrderBL(moSlotBlockBL, workTypesL, true);

                MOSlotBlock moSlotBlockAL = PTProcess.PTChooserProcess(preStowageListAL, initMOSlotBlockAL, PTSeq);
                poChooser.processOrderBL(moSlotBlockAL, workTypesL, false);

                //完成作业工艺和MoveOrder后,将数据进行保存
                for (PreStowageData preStowageData : preStowageList) {
                    int bayInt = Integer.valueOf(preStowageData.getVBYBAYID());
                    int rowInt = Integer.valueOf(preStowageData.getVRWROWNO());
                    int tierInt = Integer.valueOf(preStowageData.getVTRTIERNO());
                    MOSlotPosition moSlotPosition = new MOSlotPosition(bayInt, rowInt, tierInt);
                    if ("L".equals(preStowageData.getLDULD()) && 50 < Integer.valueOf(preStowageData.getVTRTIERNO())) {
                        MOSlot moSlot = moSlotBlockAL.getMOSlot(moSlotPosition);
                        preStowageData.setWORKFLOW(moSlot.getMoveType());
                        preStowageData.setMOVEORDER(moSlot.getMoveOrderSeq());
                    }
                    if ("L".equals(preStowageData.getLDULD()) && 50 > Integer.valueOf(preStowageData.getVTRTIERNO())) {
                        MOSlot moSlot = moSlotBlockBL.getMOSlot(moSlotPosition);
                        preStowageData.setWORKFLOW(moSlot.getMoveType());
                        preStowageData.setMOVEORDER(moSlot.getMoveOrderSeq());
                    }
                    if ("D".equals(preStowageData.getLDULD()) && 50 < Integer.valueOf(preStowageData.getVTRTIERNO())) {
                        MOSlot moSlot = moSlotBlockAD.getMOSlot(moSlotPosition);
                        preStowageData.setWORKFLOW(moSlot.getMoveType());
                        preStowageData.setMOVEORDER(moSlot.getMoveOrderSeq());
                    }
                    if ("D".equals(preStowageData.getLDULD()) && 50 > Integer.valueOf(preStowageData.getVTRTIERNO())) {
                        MOSlot moSlot = moSlotBlockBD.getMOSlot(moSlotPosition);
                        preStowageData.setWORKFLOW(moSlot.getMoveType());
                        preStowageData.setMOVEORDER(moSlot.getMoveOrderSeq());
                    }
                }
                preStowageDataListResult.addAll(preStowageList);
            } catch (Exception e) {
                isRight = false;
                errorHatchStr += str + ",";
            }
        }
        if (isRight) {
            ExceptionData.exceptionMap.put(batchNum, "success! 生成作业工艺和MoveOrder方法未发现数据异常。");
        } else {
            ExceptionData.exceptionMap.put(batchNum, "error! 舱号为：" + errorHatchStr + " 发现数据异常。");
        }

        return preStowageDataListResult;
    }
}
