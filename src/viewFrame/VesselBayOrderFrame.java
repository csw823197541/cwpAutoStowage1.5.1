package viewFrame;

import importDataInfo.PreStowageData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by liuminhang on 16/3/10.
 */
public class VesselBayOrderFrame extends JFrame{
    private List<PreStowageData> preStowageDataList;


    public VesselBayOrderFrame(List<PreStowageData> inPreStowageDataList){
        preStowageDataList = inPreStowageDataList;
        initComponents();
    }
    private void initComponents(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        VesselBayOrderPanel panel = new VesselBayOrderPanel(preStowageDataList);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        setSize(900,700);
    }


}
