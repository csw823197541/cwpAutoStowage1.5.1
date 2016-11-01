package viewFrame;

import importDataInfo.MoveInfo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by leko on 2016/1/22.
 */
public class MoveFrame1 extends JFrame {
    private JPanel panelCenter;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable tableWQL;

    private List<MoveInfo> moveInfoList;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MoveFrame1(List<MoveInfo> moveInfoList) {
        this.moveInfoList = moveInfoList;
        initComponents();
    }

    private void initComponents(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);//居中显示
        {
            this.panelCenter = new JPanel();
            this.panelCenter.setBorder(new TitledBorder(null, "装船指令", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
                    List<String> colList = new ArrayList<String>(Arrays.asList("gkey", "航次", "开始时间", "结束时间", "桥机号", "顺序号", "作业工艺", "装卸", "箱号", "尺寸", "状态", "上车次序", "场箱位", "船箱位"));
                    for (String col : colList) {
//                        System.out.println(col);
                        tableModel.addColumn(col);
                    }

                    //增加内容
                    System.out.print("生成内容");
                    for (MoveInfo moveInfo:this.moveInfoList){
                        Object[] rowData = new Object[14];
                        rowData[0] = moveInfo.getVpcCntrId();
                        rowData[1] = moveInfo.getVoyId();
                        rowData[2] = sdf.format(moveInfo.getWorkingStartTime());
                        rowData[3] = sdf.format(moveInfo.getWorkingEndTime());
                        rowData[4] = moveInfo.getCraneNo();
                        rowData[5] = moveInfo.getMoveNum();
                        rowData[6] = moveInfo.getWorkFlow();
                        rowData[7] = moveInfo.getMoveKind();
                        rowData[8] = moveInfo.getContainerId();
                        rowData[9] = moveInfo.getSize();
                        rowData[10] = moveInfo.getWorkStatus();
                        rowData[11] = moveInfo.getCarryOrder();
                        rowData[12] = moveInfo.getAreaPosition();
                        rowData[13] = moveInfo.getVesselPosition();
                        tableModel.addRow(rowData);
                    }
                    this.tableWQL.setModel(tableModel);

                    //针对特殊的列设置排序器
                    TableRowSorter tableRowSorter = new TableRowSorter(tableModel);

                    tableRowSorter.setComparator(tableWQL.getColumn("gkey").getModelIndex(), new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            //先以@为界分裂
                            String o1s = o1.split("@")[0];
                            String o2s = o2.split("@")[0];
                            String o1d = o1.split("@")[1];
                            String o2d = o2.split("@")[1];
//                            System.out.println(o1s + " " + o1d);
//                            System.out.println(o2s + " " + o2d);
                            if (o1s.equals(o2s)) {//前半截相同,后半截处理为Integer，比对
//                                System.out.println("前半截相同,后半截处理为Integer，比对");
                                int o1i = Integer.valueOf(o1d);
                                int o2i = Integer.valueOf(o2d);
                                return Integer.compare(o1i, o2i);//对比后半截
                            } else {
                                return Collator.getInstance().compare(o1s, o2s);//对比前半截
                            }
                        }
                    });

                    Comparator<Long> numberComparator = new Comparator<Long>() {
                        @Override
                        public int compare(Long o1, Long o2) {//
                            return o1.intValue() - o2.intValue();
                        }
                    };

                    tableRowSorter.setComparator(tableWQL.getColumn("顺序号").getModelIndex(), numberComparator);
//                    tableRowSorter.setComparator(tableWQL.getColumn("WORKINGSTARTTIME").getModelIndex(), numberComparator);

                    tableWQL.setRowSorter(tableRowSorter);
                    //设置排序属性
                    RowSorter.SortKey sortKey = new RowSorter.SortKey(tableWQL.getColumn("gkey").getModelIndex(),SortOrder.ASCENDING);
                    RowSorter.SortKey sortKey1 = new RowSorter.SortKey(tableWQL.getColumn("顺序号").getModelIndex(),SortOrder.ASCENDING);
//                    RowSorter.SortKey sortKey2 = new RowSorter.SortKey(tableWQL.getColumn("WORKINGSTARTTIME").getModelIndex(),SortOrder.ASCENDING);
                    List<RowSorter.SortKey> sortKeyList = new ArrayList<RowSorter.SortKey>();
                    sortKeyList.add(sortKey);
                    sortKeyList.add(sortKey1);
//                    sortKeyList.add(sortKey2);
                    tableRowSorter.setSortKeys(sortKeyList);
                    tableRowSorter.setSortsOnUpdates(true);
                }
            }
        }
    }
}
