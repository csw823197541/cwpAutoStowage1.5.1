package viewFrame;

import importDataInfo.CraneInfo;
import importDataInfo.CwpResultInfo;
import importDataInfo.HatchInfo;
import importDataInfo.VesselStructureInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by csw on 2016/3/13.
 */
public class CwpResultFrame extends JFrame{

    private List<CwpResultInfo> cwpResultInfoList;
    private List<CraneInfo> craneInfoList;
    private List<VesselStructureInfo> vesselStructureInfoList;

    public CwpResultFrame(List<CwpResultInfo> cwpResultInfoList, List<CraneInfo> craneInfoList, List<VesselStructureInfo> vesselStructureInfoList) {
        this.cwpResultInfoList = cwpResultInfoList;
        this.craneInfoList = craneInfoList;
        this.vesselStructureInfoList = vesselStructureInfoList;
        initComponents();
    }

    private void initComponents() {
        this.setTitle("cwp计划结果图");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1000, 600);
        this.setResizable(true);
//        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);// 居中显示
        this.getContentPane().setLayout(new BorderLayout());
        CwpResultPanel1 cwpResultPanel = new CwpResultPanel1(cwpResultInfoList, craneInfoList, vesselStructureInfoList);
        JScrollPane scrollPane = new JScrollPane(cwpResultPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

//    public static void main(String[] args) {
//        String str = FileUtil.readFileToString(new File("toCwpData/cwpBlock11.txt")).toString();
//        String str1 = FileUtil.readFileToString(new File("toCwpData/crane11.txt")).toString();
//        String str2 = FileUtil.readFileToString(new File("toCwpData/hatch11.txt")).toString();
//        List<CwpResultInfo> cwpResultInfoList1 = CwpResultInfoProcess.getCwpResultInfo(str);
//        List<CraneInfo> craneInfoList1 = CraneInfoProcess.getCraneInfo(str1);
//        new CwpResultFrame(cwpResultInfoList1, craneInfoList1, null).setVisible(true);
//    }
}
