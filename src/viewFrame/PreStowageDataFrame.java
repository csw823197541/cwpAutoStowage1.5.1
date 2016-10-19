package viewFrame;

import importDataInfo.PreStowageData;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by leko on 2016/1/21.
 */
public class PreStowageDataFrame extends JFrame{
    private JPanel panelCenter;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable tableWQL;

    private JLabel jlFilter;
    private JPanel jpFilter;
    private JTextField jfFilter;
    private JButton btn;

    private List<PreStowageData> preStowageInfoList;

    public PreStowageDataFrame(List<PreStowageData> preStowageInfoList) {
        this.preStowageInfoList = preStowageInfoList;
        initComponents();
    }
    private void initComponents(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);//居中显示
        {
            jlFilter = new JLabel("按舱号查询:");
            jfFilter = new JTextField(10);
            btn = new JButton("查询");

            jpFilter = new JPanel(new FlowLayout());
            jpFilter.add(jlFilter);
            jpFilter.add(jfFilter);
            jpFilter.add(btn);

            this.panelCenter = new JPanel();
            this.panelCenter.setBorder(new TitledBorder(null, "预配信息", TitledBorder.LEADING, TitledBorder.TOP, null, null));

            this.contentPane = new JPanel();
            this.contentPane.setLayout(new BorderLayout(0, 0));
            this.contentPane.add(jpFilter, BorderLayout.NORTH);
            this.contentPane.add(this.panelCenter, BorderLayout.CENTER);
            setContentPane(this.contentPane);
            this.panelCenter.setLayout(new BorderLayout(0, 0));
            {
                this.scrollPane = new JScrollPane();
                this.panelCenter.add(this.scrollPane,BorderLayout.CENTER);
                {
                    this.tableWQL = new JTable();
                    this.scrollPane.setViewportView(this.tableWQL);
                    final TableModel tableModel = new TableModel();
//                    DefaultTableModel tableModel = new DefaultTableModel();
                    //增加列名
                    ArrayList<String> colList = new ArrayList<String>(Arrays.asList("舱号","倍号", "层号", "排号", "箱号", "尺寸","箱型", "属性组","重量等级","MoveOrder", "装卸船标志", "作业工艺", "桥机号", "目的港", "过境箱标记"));
                    for (String col : colList) {
//                        System.out.println(col);
                        tableModel.addColumn(col);
                    }

                    //增加内容
                    Object[] rowData = new Object[17];
                    for (PreStowageData preStowageInfo:preStowageInfoList)
                    {
                        rowData[0] = preStowageInfo.getVHTID();
                        rowData[1] = preStowageInfo.getVBYBAYID();
                        rowData[2] = preStowageInfo.getVTRTIERNO();
                        rowData[3] = preStowageInfo.getVRWROWNO();
                        rowData[4] = preStowageInfo.getContainerNum();
                        rowData[5] = preStowageInfo.getSIZE();
                        rowData[6] = preStowageInfo.getCTYPECD();
                        rowData[7] = preStowageInfo.getGROUPID();
                        rowData[8] = preStowageInfo.getWEIGHT();
                        rowData[9] = preStowageInfo.getMOVEORDER();
                        rowData[10] = preStowageInfo.getLDULD();
                        rowData[11] = preStowageInfo.getWORKFLOW();
                        rowData[12] = preStowageInfo.getQCNO();
                        rowData[13] = preStowageInfo.getDSTPORT();
                        rowData[14] = preStowageInfo.getTHROUGHFLAG();
                        rowData[15] = preStowageInfo.getContainerNum();
                        rowData[16] = preStowageInfo.getContainerStatus();
                        //System.out.println(rowData[0]+" "+rowData[1]+" "+rowData[2]+" "+rowData[3]);
                        tableModel.addRow(rowData);
                    }
                    this.tableWQL.setModel(tableModel);

                    this.tableWQL.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
//                            System.out.println("Mouse Clicked");

                            super.mouseClicked(e);
                            if(e.getClickCount()>=2){
                                System.out.println("Mouse Double Clicked");

                                int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置
                                String clickedBayId = (String) tableModel.getValueAt(row,tableWQL.getColumnModel().getColumnIndex("倍号"));

                                String hatchId = (String) tableModel.getValueAt(row,tableWQL.getColumnModel().getColumnIndex("舱号"));
                                System.out.println("倍位号：" + clickedBayId);
                                //筛选该贝位的数据
                                List<PreStowageData> bayLoadStowageData = new ArrayList<PreStowageData>();
                                List<PreStowageData> bayDschStowageData = new ArrayList<PreStowageData>();
                                int bayInt = Integer.valueOf(clickedBayId);
                                int bayInt02 = 0;
                                if(bayInt % 4 == 1) {
                                    bayInt02 = bayInt + 1;
                                }
                                if(bayInt % 4 == 3) {
                                    bayInt02 = bayInt -1;
                                }
                                for(int i = 0;i<preStowageInfoList.size();i++){
                                    PreStowageData preStowageData = preStowageInfoList.get(i);
                                    if(bayInt == Integer.valueOf(preStowageData.getVBYBAYID()) || bayInt02 == Integer.valueOf(preStowageData.getVBYBAYID())){
                                        if(preStowageData.getLDULD().equals("L")){
                                            bayLoadStowageData.add(preStowageData);
                                        }
                                        if(preStowageData.getLDULD().equals("D")){
                                            bayDschStowageData.add(preStowageData);
                                        }
                                    }
                                }



                                VesselBayOrderFrame vesselBayDschOrderFrame = new VesselBayOrderFrame(bayDschStowageData);
                                vesselBayDschOrderFrame.setTitle(clickedBayId + "贝卸船");
                                vesselBayDschOrderFrame.setVisible(true);

                                VesselBayOrderFrame vesselBayLoadOrderFrame = new VesselBayOrderFrame(bayLoadStowageData);
                                vesselBayLoadOrderFrame.setTitle(clickedBayId + "贝装船");
                                vesselBayLoadOrderFrame.setBounds(vesselBayDschOrderFrame.getWidth(),0,vesselBayLoadOrderFrame.getWidth(),vesselBayLoadOrderFrame.getHeight());
                                vesselBayLoadOrderFrame.setVisible(true);
                            }
                        }
                    });


                }
            }

            final TableRowSorter<javax.swing.table.TableModel> sorter = new TableRowSorter<javax.swing.table.TableModel>(
                    tableWQL.getModel());
            tableWQL.setRowSorter(sorter);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = jfFilter.getText();
                    if(text.length() == 0){
                        sorter.setRowFilter(null);
                    }else {
                        sorter.setRowFilter(RowFilter.regexFilter("^"+text+"$", 0));//按表格第一例筛选
                    }
                }
            });
        }
    }
}
