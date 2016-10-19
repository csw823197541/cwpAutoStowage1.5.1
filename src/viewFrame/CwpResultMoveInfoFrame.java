package viewFrame;


import importDataInfo.CwpResultMoveInfo;

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
public class CwpResultMoveInfoFrame extends JFrame {
    private JPanel panelCenter;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable tableWQL;

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<CwpResultMoveInfo> cwpResultMoveInfoList;

    public CwpResultMoveInfoFrame(List<CwpResultMoveInfo> cwpResultMoveInfoList) {
        this.cwpResultMoveInfoList = cwpResultMoveInfoList;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);//居中显示
        {
            this.panelCenter = new JPanel();
            this.panelCenter.setBorder(new TitledBorder(null, "数据库cwp结果", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
                        ArrayList<String> colList = new ArrayList<String>(Arrays.asList("舱号", "倍位号", "桥机号", "桥机当前位置", "船舶代码", "moveOrder", "moveType", "装卸标志", "开始时间", "结束时间"));
                        for (String col : colList) {
//                            System.out.println(col);
                            tableModel.addColumn(col);
                        }

                        //增加内容
                        List<CwpResultMoveInfo> cwpResultMoveInfoList = this.cwpResultMoveInfoList;
                        System.out.print("生成内容");
                        for (CwpResultMoveInfo cwpResultMoveInfo : cwpResultMoveInfoList) {
                            Object[] rowData = new Object[10];
                            rowData[0] = cwpResultMoveInfo.getHATCHID();
                            rowData[1] = cwpResultMoveInfo.getHATCHBWID();
                            rowData[2] = cwpResultMoveInfo.getCRANEID();
                            rowData[3] = cwpResultMoveInfo.getCranesPosition();
                            rowData[4] = cwpResultMoveInfo.getVESSELID();
                            rowData[5] = cwpResultMoveInfo.getMoveOrder();
                            rowData[6] = cwpResultMoveInfo.getMOVETYPE();
                            rowData[7] = cwpResultMoveInfo.getLDULD();
                            rowData[8] = sdf.format(cwpResultMoveInfo.getWorkingStartTime());
                            rowData[9] = sdf.format(cwpResultMoveInfo.getWorkingEndTime());
                            tableModel.addRow(rowData);
                        }
                        this.tableWQL.setModel(tableModel);
                    }
                }
            }
        }
    }
}
