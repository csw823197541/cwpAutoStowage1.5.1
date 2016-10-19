package viewFrame;

import importDataInfo.VoyageInfo;

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
public class VoyageFrame extends JFrame {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private JPanel panelCenter;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable tableWQL;

    private List<VoyageInfo> voyageInfoList;

    public VoyageFrame(List<VoyageInfo> voyageInfoList) {
        this.voyageInfoList = voyageInfoList;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 100);
        setLocationRelativeTo(null);//居中显示
        {
            this.panelCenter = new JPanel();
            this.panelCenter.setBorder(new TitledBorder(null, "航次信息", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
                        ArrayList<String> colList = new ArrayList<String>(Arrays.asList("航次", "船舶", "开工时间", "完工时间", "船头位置", "船尾位置"));
                        for (String col : colList) {
//                            System.out.println(col);
                            tableModel.addColumn(col);
                        }

                        //增加内容
                        List<VoyageInfo> voyageInfoList = this.voyageInfoList;
                        System.out.print("生成内容");
                        for (VoyageInfo voyageInfo : voyageInfoList) {
                            Object[] rowData = new Object[6];
                            rowData[0] = voyageInfo.getVOTVOYID();
                            rowData[1] = voyageInfo.getVESSELID();
                            rowData[2] = sdf.format(voyageInfo.getVOTPWKSTTM());
                            rowData[3] = sdf.format(voyageInfo.getVOTPWKENTM());
                            rowData[4] = voyageInfo.getSTARTPOSITION();
                            rowData[5] = voyageInfo.getENDPOSITION();
                            tableModel.addRow(rowData);
                        }
                        this.tableWQL.setModel(tableModel);
                    }
                }
            }
        }
    }
}
