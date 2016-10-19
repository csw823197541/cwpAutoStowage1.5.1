package viewFrame;

import importDataInfo.GroupInfo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by leko on 2016/1/21.
 */
public class GroupFrame extends JFrame {
    private JPanel panelCenter;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable tableWQL;

    private List<GroupInfo> groupInfoList;


    public GroupFrame(List<GroupInfo> groupInfoList) {
        this.groupInfoList = groupInfoList;
        initComponents();
    }

    private void initComponents(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);//居中显示
        {
            this.panelCenter = new JPanel();
            this.panelCenter.setBorder(new TitledBorder(null, "属性组", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            this.contentPane = new JPanel();
            this.contentPane.setLayout(new BorderLayout(0, 0));
            this.contentPane.add(this.panelCenter, BorderLayout.CENTER);
            setContentPane(this.contentPane);
            this.panelCenter.setLayout(new BorderLayout(0, 0));
            {
                this.scrollPane = new JScrollPane();
                this.panelCenter.add(this.scrollPane,BorderLayout.CENTER);
                {
                    this.tableWQL = new JTable();
                    this.scrollPane.setViewportView(this.tableWQL);
                    DefaultTableModel tableModel = new DefaultTableModel();

                    //增加列名
                    ArrayList<String> colList = new ArrayList<String>(Arrays.asList("属性组","港口", "箱型", "尺寸"));
                    for (String col : colList) {
//                        System.out.println(col);
                        tableModel.addColumn(col);
                    }

                    //增加内容
                    System.out.print("生成内容");
                    for (GroupInfo groupInfo:this.groupInfoList){
                        Object[] rowData = new Object[4];
                        rowData[0] = groupInfo.getGroupID();
                        rowData[1] = groupInfo.getPort();
                        rowData[2] = groupInfo.getType();
                        rowData[3] = groupInfo.getSize();
                        tableModel.addRow(rowData);

                    }
                    this.tableWQL.setModel(tableModel);
                }
            }
        }
    }
}
