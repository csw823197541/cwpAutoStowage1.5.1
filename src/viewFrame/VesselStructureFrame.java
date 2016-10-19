package viewFrame;


import importDataInfo.VesselStructureInfo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by leko on 2016/1/19.
 */
public class VesselStructureFrame extends JFrame {
    private JPanel panelCenter;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable tableWQL;

    private List<VesselStructureInfo> vesselStructureInfoList;

    public VesselStructureFrame(List<VesselStructureInfo> vesselStructureInfos) {
        this.vesselStructureInfoList = vesselStructureInfos;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);//居中显示
        {
            this.panelCenter = new JPanel();
            this.panelCenter.setBorder(new TitledBorder(null, "船舶结构", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
                        ArrayList<String> colList = new ArrayList<String>(Arrays.asList("航位ID", "舱位长度", "舱起始相对位置", "倍位ID", "倍中心相对位置", "层号","排号","重量等级"));
                        for (String col : colList) {
//                            System.out.println(col);
                            tableModel.addColumn(col);
                        }

                        //增加内容
                        List<VesselStructureInfo> vesselStructureInfos = this.vesselStructureInfoList;
                        System.out.print("生成内容");
                        for (VesselStructureInfo vesselStructureInfo : vesselStructureInfos) {
                            Object[] rowData = new Object[9];
                            rowData[0] = vesselStructureInfo.getVHTID();
                            rowData[1] = vesselStructureInfo.getLENGTH();
                            rowData[2] = vesselStructureInfo.getVHTPOSITION();
                            rowData[3] = vesselStructureInfo.getVBYBAYID();
                            rowData[4] = vesselStructureInfo.getVBYPOSITION();
                            rowData[5] = vesselStructureInfo.getVTRTIERNO();
                            rowData[6] = vesselStructureInfo.getVRWROWNO();
                            rowData[7] = vesselStructureInfo.getWeightGradeMin();
                            rowData[8] = vesselStructureInfo.getWeightGradeMax();
                            tableModel.addRow(rowData);
                        }
                        this.tableWQL.setModel(tableModel);
                    }
                }
            }
        }
    }
}
