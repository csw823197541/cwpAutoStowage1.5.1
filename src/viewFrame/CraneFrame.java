package viewFrame;

import importDataInfo.CraneInfo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by leko on 2016/1/19.
 */
public class CraneFrame extends JFrame {
    private JPanel panelCenter;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable tableWQL;

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<CraneInfo> craneInfoList;


    public CraneFrame(List<CraneInfo> craneInfoList) {
        this.craneInfoList = craneInfoList;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);//居中显示
        {
            this.panelCenter = new JPanel();
            this.panelCenter.setBorder(new TitledBorder(null, "桥机", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            this.contentPane = new JPanel();
            this.contentPane.setLayout(new BorderLayout(0, 0));
            this.contentPane.add(this.panelCenter, BorderLayout.CENTER);
            setContentPane(this.contentPane);
            this.panelCenter.setLayout(new BorderLayout(0, 0));
            {
                {
                    this.scrollPane = new JScrollPane();
                    this.panelCenter.add(this.scrollPane, BorderLayout.CENTER);
                    {
                        this.tableWQL = new JTable();
                        this.scrollPane.setViewportView(this.tableWQL);
                        DefaultTableModel tableModel = new DefaultTableModel();

                        //增加列名
                        ArrayList<String> colList = new ArrayList<String>(Arrays.asList("桥吊ID","当前位置", "卸20尺箱效率", "卸40尺箱效率", "卸船双吊具效率", "装20尺箱效率", "装40尺箱效率","装船双吊具效率","移动范围起始","移动范围终止","桥吊编号","安全距离","桥吊序列号","移动速度","桥吊宽度","工作时间"));
                        for (String col : colList) {
//                            System.out.println(col);
                            tableModel.addColumn(col);
                        }

                        //增加内容
                        List<CraneInfo> craneInfoList = this.craneInfoList;
                        System.out.print("生成内容");
                        for (CraneInfo craneInfo : craneInfoList) {
                            Object[] rowData = new Object[16];
                            rowData[0] = craneInfo.getID();
                            rowData[1] = craneInfo.getCURRENTPOSITION();
                            rowData[2] = craneInfo.getDISCHARGEEFFICIENCY20();
                            rowData[3] = craneInfo.getDISCHARGEEFFICIENCY40();
                            rowData[4] = craneInfo.getDISCHARGEEFFICIENCYTWIN();
                            rowData[5] = craneInfo.getLOADINGEFFICIENCY20();
                            rowData[6] = craneInfo.getLOADINGEFFICIENCY40();
                            rowData[7] = craneInfo.getLOADINGEFFICIENCYTWIN();
                            rowData[8] = craneInfo.getMOVINGRANGEFROM();
                            rowData[9] = craneInfo.getMOVINGRANGETO();
                            rowData[10] = craneInfo.getNAME();
                            rowData[11] = craneInfo.getSAFESPAN();
                            rowData[12] = craneInfo.getSEQ();
                            rowData[13] = craneInfo.getSPEED();
                            rowData[14] = craneInfo.getWIDTH();
                            rowData[15] = sdf.format(craneInfo.getWORKINGTIMERANGES().get(0).getWORKSTARTTIME())+"-"+sdf.format(craneInfo.getWORKINGTIMERANGES().get(0).getWORKENDTIME());
                            tableModel.addRow(rowData);
                        }
                        this.tableWQL.setModel(tableModel);
                    }
                }
            }
        }
    }
}
