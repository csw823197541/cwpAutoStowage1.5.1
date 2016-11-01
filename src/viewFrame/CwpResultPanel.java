package viewFrame;

import importDataInfo.CraneInfo;
import importDataInfo.CwpResultInfo;
import importDataInfo.HatchInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by csw on 2016/3/13.
 */
public class CwpResultPanel extends JPanel{

    private List<CwpResultInfo> cwpResultInfoList;
    private List<CraneInfo> craneInfoList;
    private List<HatchInfo> hatchInfoList;

    public CwpResultPanel(List<CwpResultInfo> cwpResultInfoList, List<CraneInfo> craneInfoList, List<HatchInfo> hatchInfoList) {
        this.cwpResultInfoList = cwpResultInfoList;
        this.craneInfoList = craneInfoList;
        this.hatchInfoList = hatchInfoList;
        initComponents();
    }

    public int width  = 1400;
    public int height = 750;
    public int topMargin = 10;
    public int leftMargin = 50;
    public int length = 1300;//船总长
    public int hatchWidth = 50;//图中显示的舱宽度
    public int cwpBlock = 650;//cwp作业块总长度
    public int hatchLength = 25;//船舱长度
    public int hatchIn = 5;//舱间距

    public Font font = new Font("Courier New", Font.BOLD, 12);

    private void initComponents() {
        this.setPreferredSize(new Dimension(width,height));
        this.setSize(width, height);
        this.setOpaque(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

//        g2d.setPaint(Color.white);
//        g2d.fillRect(0, 0, width, height);

        g2d.setPaint(Color.DARK_GRAY);
        g2d.drawRect(leftMargin, topMargin, length, hatchWidth);//画出船的模型

        //画宽实线,最左边的Y轴时间刻度线
        g2d.setPaint(Color.BLACK);
        g2d.drawRect(leftMargin, topMargin, 2, cwpBlock + topMargin);
        g2d.fillRect(leftMargin, topMargin, 2, cwpBlock + topMargin);

        //画出船舱
        //将每个倍的位置存起来，以便查找画作业块
        Map<Integer, Integer> bayQuery = new HashMap<>();
        int start = leftMargin;
        for(int i = 0; i < 23; i++) {
            int X1 = start + hatchIn + 2;
            bayQuery.put((i+1)*4-3, X1);//左边小倍
            int X3 = start + hatchIn + hatchLength +2;
            bayQuery.put((i+1)*4-1, X3);//右边小倍
            int X2 = start + hatchIn + hatchLength/2;
            bayQuery.put((i+1)*4-2, X2);//中间大倍
            //画实线,船舱左边缘线
            g2d.setPaint(Color.BLACK);
            g2d.drawLine(start+hatchIn, topMargin, start+hatchIn, cwpBlock + topMargin);
            g2d.drawString((i+1)*4-3 + "", X1+3, topMargin+hatchWidth/2);
            //画虚线,小倍间隔线
            g2d.setPaint(Color.LIGHT_GRAY);
            g2d.drawLine(start+hatchIn+hatchLength, topMargin, start+hatchIn+hatchLength, cwpBlock + topMargin);
            //画实线,船舱右边缘线
            g2d.setPaint(Color.BLACK);
            g2d.drawLine(start+hatchIn+2*hatchLength, topMargin, start+hatchIn+2*hatchLength, cwpBlock + topMargin);
            g2d.drawString((i+1)*4-1 + "", X3+3, topMargin+hatchWidth/2);
            start = start+hatchIn+2*hatchLength;
        }

        //设置桥机颜色
        Color[] colors = new Color[]{new Color(0xCD00CD), new Color(0x1EC6CD), new Color(0xFF0325), new Color(0x9F79EE),
                new Color(0x21FE06), new Color(0xFFFF22), new Color(0xFF00FF), new Color(0x9AA309), new Color(0x120DFF),
                new Color(0x8B0000), new Color(0x00FFFF), new Color(0x87CEFA), new Color(0xEE0000), new Color(0x000077),
                new Color(0x22B522), new Color(0x3D3D3D), new Color(0x050505)};//16部桥机的颜色
        Map<String, Color> craneQuery = new HashMap<>();
        int k = 0;
        for(CraneInfo craneInfo : craneInfoList) {
            craneQuery.put(craneInfo.getID(), colors[k++]);
        }

        //根据cwpResult数据,画作业块
        //得到时间的最大值
        int maxEndTime = 0;
        for(CwpResultInfo cwpResultInfo : cwpResultInfoList) {
            if(maxEndTime < cwpResultInfo.getWORKINGENDTIME())
                maxEndTime = cwpResultInfo.getWORKINGENDTIME();
        }
        //将时间化成标准时间刻度
        g2d.drawString("总时间:", 5, (float) (hatchWidth/2.5));
        g2d.drawString(secToTime(maxEndTime), 5, topMargin + hatchWidth/2);//将总时间画出
        int timeStep = maxEndTime%1800 == 0 ? maxEndTime/1800 : maxEndTime/1800 + 1;//30分钟一个刻度
        for(int j = 0; j <= timeStep; j++) {//画时间刻度
            String tStr = secToTime(j*1800);
            g2d.drawString(tStr, 5, j*(cwpBlock-hatchWidth)/timeStep + topMargin + hatchWidth + 5);
            g2d.drawLine(leftMargin-5, j*(cwpBlock-hatchWidth)/timeStep + topMargin + hatchWidth, leftMargin, j*(cwpBlock-hatchWidth)/timeStep + topMargin + hatchWidth);
        }
        //画作业块
        Map<String, Long> countQuery = new HashMap<>();//每个倍位的moveCount数统计
        Map<String, List<String>> blockCountQuery = new HashMap<>();//每个作业块的moveCount数统计
        List<String> strList;
        String strNew;
        for(CwpResultInfo cwpResultInfo : cwpResultInfoList) {
            String craneId = cwpResultInfo.getCRANEID();//得到桥机号
            int bayId = Integer.valueOf(cwpResultInfo.getHATCHBWID());//得到倍位号
            int startTime = cwpResultInfo.getWORKINGSTARTTIME();
            int endTime = cwpResultInfo.getWORKINGENDTIME();
            Long moveCount = cwpResultInfo.getMOVECOUNT();
            if(countQuery.get(bayId+"") != null) {
                countQuery.put(bayId+"", countQuery.get(bayId + "") + moveCount);
            } else {
                countQuery.put(bayId + "", moveCount);
            }
            String key = bayId+" "+craneId;
            strList = new ArrayList<>();
            List<String> startTimeEndTimeMoveCount = blockCountQuery.get(key);//开始时间、结束时间、数量
            if(startTimeEndTimeMoveCount != null) {//已经存在倍位、桥机相同的作业快
                boolean flag = false;
                for(String str : startTimeEndTimeMoveCount) {
                    if((Integer.valueOf(str.split(" ")[1]) == startTime) || (Integer.valueOf(str.split(" ")[1]) == startTime+1)) {//结束时间与开始时间相同，是同一个作业块的
                        Long count = Integer.valueOf(str.split(" ")[2])+moveCount;
                        strNew = str.split(" ")[0]+" "+endTime+" "+count;
                        strList.add(strNew);
                        flag = true;
                    }
                }
                if(!flag) {
                    strNew = startTime+" "+endTime+" "+moveCount;
                    strList.add(strNew);
//                    key = key + " " + 1;
                }
                blockCountQuery.put(key, strList);
            } else {
                strNew = startTime+" "+endTime+" "+moveCount;
                strList.add(strNew);
                blockCountQuery.put(key, strList);
            }
            int x = bayQuery.get(bayId);
            int y = topMargin + hatchWidth + startTime*(cwpBlock - hatchWidth - topMargin)/maxEndTime;
            int w = bayId%2 == 0 ? hatchLength : hatchLength-4;//作业块宽度
            int h = (endTime - startTime)*(cwpBlock - hatchWidth - topMargin)/maxEndTime;//作业块长度
            g2d.setPaint(craneQuery.get(craneId));
            g2d.drawRect(x, y, w, h);
            g2d.fillRect(x, y, w, h);
        }
        //遍历Map，画出每个倍位的moveCount数
        if(countQuery != null) {
            for(Map.Entry<String, Long> entry : countQuery.entrySet()) {
                int x = bayQuery.get(Integer.valueOf(entry.getKey()));
                g2d.setPaint(Color.red);
                if(Integer.valueOf(entry.getKey())%2 == 0) {
                    g2d.drawString(entry.getValue()+"", x+2, 20);
                }else{
                    g2d.drawString(entry.getValue()+"", x+2, 50);
                }
            }
        }
        //遍历Map, 画出每个作业块的moveCount数
        if(blockCountQuery != null) {
            for(Map.Entry<String, List<String>> entry : blockCountQuery.entrySet()) {
                int x = bayQuery.get(Integer.valueOf(entry.getKey().split(" ")[0]));
                List<String> stringList = entry.getValue();
                for(String str : stringList) {
                    int startTime = Integer.valueOf(str.split(" ")[0]);
                    int endTime = Integer.valueOf(str.split(" ")[1]);
                    String moveCount = str.split(" ")[2];
                    g2d.setPaint(Color.BLACK);
                    int y = topMargin + hatchWidth + startTime*(cwpBlock - hatchWidth - topMargin)/maxEndTime;
                    int h = (endTime - startTime)*(cwpBlock - hatchWidth - topMargin)/maxEndTime;//作业块长度
                    y = y + h/2;
                    g2d.drawString(moveCount, x+2, y);
                }

            }
        }
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            hour = minute / 60;
            minute = minute % 60;
            timeStr = unitFormat(hour) + ":" + unitFormat(minute);
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        return i >= 0 && i < 10 ? "0" + Integer.toString(i) : "" + i;
    }
}
