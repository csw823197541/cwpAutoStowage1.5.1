package viewFrame;

import importDataInfo.PreStowageData;
import importDataInfo.VesselStructureInfo;
import importDataProcess.ImportData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by liuminhang on 16/3/10.
 */
public class VesselBayOrderPanel extends JPanel {
    private List<PreStowageData> preStowageDataList;
    public int rect_length = 32;//边长
    public static int start_x = 400, start_y_b = 760, start_y_a = 400;
    public int size_width = 900;
    public int size_height = 800;
    public Font font = new Font("宋体",Font.BOLD,10);
    public Font font2 = new Font("宋体",Font.PLAIN,6);

    public VesselBayOrderPanel(List<PreStowageData> inPreStowageDataList) {
        preStowageDataList = inPreStowageDataList;
        initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(size_width,size_height));
        setSize(size_width, size_height);
        setOpaque(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        //绘制船舶结构

        g2d.setPaint(Color.gray);
//        g2d.drawLine(start_x, 0, start_x, size_height);//中心线
        g2d.drawLine(0, start_y_b, size_width, start_y_b);//舱底
        g2d.drawLine(0, start_y_a, size_width, start_y_a);//甲板
        for (int i = 0; i < ImportData.vesselStructureInfoList.size(); i++) {
            int vx = 0, vy = 0;
            VesselStructureInfo vesselStructureInfo = ImportData.vesselStructureInfoList.get(i);
            int bayInt = Integer.valueOf(vesselStructureInfo.getVBYBAYID());
            int rowInt = Integer.valueOf(vesselStructureInfo.getVRWROWNO());
            int tierInt = Integer.valueOf(vesselStructureInfo.getVTRTIERNO());

            if (rowInt % 2 == 1) {//奇数
                vx = (rowInt / 2) * rect_length + start_x;
            } else {
                vx = start_x - (rowInt / 2 + 1) * rect_length;
            }
            if (tierInt >= 50) {
                vy = start_y_a - ((tierInt - 75) / 2) * rect_length;
            } else {
                vy = start_y_b - (tierInt / 2 ) * rect_length;
            }

            g2d.drawRect(vx, vy, rect_length, rect_length);

            String tag2 = "" + rowInt + "," + tierInt;
//            g2d.setFont(font2);
            g2d.drawString(tag2, vx, vy + 18);
        }

        //绘制配载图
        for (int i = 0; i < preStowageDataList.size(); i++) {
            g2d.setPaint(Color.BLACK);
            PreStowageData preStowageData = preStowageDataList.get(i);
            int x = 0, y = 0;
            int bayInt = Integer.valueOf(preStowageData.getVBYBAYID());
            int rowInt = Integer.valueOf(preStowageData.getVRWROWNO());
            int tierInt = Integer.valueOf(preStowageData.getVTRTIERNO());

            if (bayInt > 0) {
//                System.out.println("Draw Start Pos:"+ x + "," + y);
                if (rowInt % 2 == 1) {//奇数
                    x = (rowInt / 2) * rect_length + start_x;
                } else {
                    x = start_x - (rowInt / 2 + 1) * rect_length;
                }
                if (tierInt >= 50) {
                    y = start_y_a - ((tierInt - 75) / 2) * rect_length;
                } else {
                    y = start_y_b - (tierInt / 2) * rect_length;
                }

                g2d.drawRect(x, y, rect_length, rect_length);
                String tag1 = preStowageData.getMOVEORDER().toString();
                g2d.setFont(font);
                if (bayInt % 4 == 1 || bayInt % 4 == 3) {
                    g2d.setPaint(Color.red);
                    g2d.drawString(tag1, x+10, y + 8);
                }
                if (bayInt % 4 == 2 || bayInt % 4 == 0) {
                    g2d.setPaint(Color.BLUE);
                    g2d.drawString(tag1, x+10, y + 8);
                }

//                System.out.println("Slot:"+ rowInt + "," + tierInt +"   Draw:[" + x + "," + y + "]");

            }


        }

    }
}
