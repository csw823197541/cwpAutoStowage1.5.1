package generateResult;

import importDataInfo.VesselStructureInfo;
import importDataInfo.VoyageInfo;
import importDataProcess.ExceptionData;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by csw on 2016/6/3 14:59.
 * explain：
 */
public class GenerateBayPositionQuery {

    public static DecimalFormat df = new DecimalFormat("#.00");

    public static Map<String, Double> getBayPositionMap(
            Long batchNum,
            List<VoyageInfo> voyageInfoList,
            List<VesselStructureInfo> vesselStructureInfoList) {
        Map<String, Double> bayPositionMap = new HashMap<>();

        ExceptionData.exceptionMap.put(batchNum, "接口方法未执行。");
        boolean isRight = true;
        String info = "";
        try{
            List<String> hatchIdList = new ArrayList<>();   //该船舶结构有多少个舱
            List<String> bayWeiIdList = new ArrayList<>();  //有多少个倍位
            for (VesselStructureInfo vesselStructureInfo : vesselStructureInfoList) {
                if(!hatchIdList.contains(vesselStructureInfo.getVHTID()))
                    hatchIdList.add(vesselStructureInfo.getVHTID());
                if(!bayWeiIdList.contains(vesselStructureInfo.getVBYBAYID()))
                    bayWeiIdList.add(vesselStructureInfo.getVBYBAYID());
            }//统计倍舱位数和倍位数

            //统计每个舱有多少个倍
            Map<String, Set<String>> hatchBayWeiMap = new HashMap<>();
            for(String hatchId : hatchIdList) {
                Set<String> bayWeiSet = new TreeSet<>();
                for (VesselStructureInfo vesselStructureInfo : vesselStructureInfoList) {
                    if(hatchId.equals(vesselStructureInfo.getVHTID())) {
                        bayWeiSet.add(vesselStructureInfo.getVBYBAYID());
                    }
                }
                hatchBayWeiMap.put(hatchId, bayWeiSet);
            }

            //计算舱绝对位置坐标
            Map<String, Double> hatchPositionMap = new HashMap<>();
            int i = 0;
            int length = vesselStructureInfoList.get(0).getLENGTH();    //舱长度
            int cabL = vesselStructureInfoList.get(0).getCABLENGTH();   //驾驶室长度
            int cabPosition = vesselStructureInfoList.get(0).getCABPOSITION();  //驾驶室在哪个倍位号后面
            String cabBayWei = String.format("%02d", cabPosition);
            String cabHatchId = null;   //驾驶室在哪个舱
            Collections.sort(hatchIdList);
            for(int j = 0; j < hatchIdList.size(); j++) {//查找到驾驶室在哪个舱
                List<String> bayWeiList = new ArrayList<>(hatchBayWeiMap.get(hatchIdList.get(j)));
                if(bayWeiList.contains(cabBayWei)) {//取后面一个舱号
                    cabHatchId = hatchIdList.get(j+1);
                }
                if(bayWeiList.size() == 2) {
                    if(cabPosition == (Integer.valueOf(bayWeiList.get(0)) +
                            Integer.valueOf(bayWeiList.get(1)))/2) {
                        cabHatchId = hatchIdList.get(j+1);
                    }
                }
            }
            int startPosition = voyageInfoList.get(0).getSTARTPOSITION();//船头开始位置
            Double cjj = 1.0;   //舱间距1米
            Double cabLength = 0.0;
            for(String hatchId : hatchIdList) {
                if(hatchId.equals(cabHatchId)) {//当前舱前面有驾驶室
                    cabLength = cabL + cjj;
                }
                hatchPositionMap.put(hatchId, Double.valueOf(df.format(startPosition + cabLength + i*(length + cjj))));
                i++;
            }

            //计算倍位的中心绝对位置坐标
            for(String hatchId : hatchIdList) {
                Set<String> bayWeiSet = hatchBayWeiMap.get(hatchId);
                Double hatchPosition = hatchPositionMap.get(hatchId);   //舱的位置
                if(bayWeiSet.size() == 2) {//两个倍位
                    Double position1 = hatchPosition + Double.valueOf(length)/4;    //左边小贝
                    Double position2 = hatchPosition + 3*Double.valueOf(length)/4;  //右边小贝
                    Double position3 = hatchPosition + Double.valueOf(length)/2;    //大倍
                    List<String> bayWeiList = new ArrayList<>(bayWeiSet);
                    String bayWei1 = bayWeiList.get(0);
                    String bayWei2 = bayWeiList.get(1);
                    bayPositionMap.put(bayWei1, Double.valueOf(df.format(position1)));
                    bayPositionMap.put(bayWei2, Double.valueOf(df.format(position2)));
                    bayWei1 = bayWei1.startsWith("0") ? bayWei1.replace("0", "") : bayWei1;
                    bayWei2 = bayWei2.startsWith("0") ? bayWei2.replace("0", "") : bayWei2;
                    Integer intBayWei3 = (Integer.valueOf(bayWei1) + Integer.valueOf(bayWei2))/2;
                    String bayWei3 = String.format("%02d", intBayWei3);
                    bayPositionMap.put(bayWei3, Double.valueOf(df.format(position3)));
                }
                if(bayWeiSet.size() == 1) {//一个倍位
                    Double position = hatchPosition + Double.valueOf(length)/2;
                    List<String> bayWeiList = new ArrayList<>(bayWeiSet);
                    String bayWei = bayWeiList.get(0);
                    bayPositionMap.put(bayWei, Double.valueOf(df.format(position)));
                }
            }
        } catch (Exception e) {
            isRight = false;
            info += "接口方法发生航次或船舶结构数据为null异常。";
        }
        if (isRight) {
            ExceptionData.exceptionMap.put(batchNum, "success! 接口方法没有发生异常。");
        } else {
            ExceptionData.exceptionMap.put(batchNum, "error! " + info);
        }

        return bayPositionMap;
    }
}
