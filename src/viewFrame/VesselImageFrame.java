package viewFrame;

import importDataInfo.AutoStowResultInfo;
import importDataInfo.VesselStructureInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by ding on 2016/3/11 0011.
 */
public class VesselImageFrame extends JFrame {
    private JPanel topPanel;
    private JPanel contentPanel;
    private JPanel forScrollPanel;
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private int MaxTierUnderForDrawBoat = 0;//用来统计甲板下集装箱的最大层数以便于画船体
    private int MaxTierAboveForDrawString = 0;//用来统计甲板下集装箱的最大层数以便于写集装箱上方的文字
    private int bayCount;   //倍数
    int boxWidth = 30; //集装箱宽度固定值40
    int boxHeight =  15;   //集装箱高度就是15
    int gap = 15; //倍之间的间隔
    //船体四个点的坐标(程序中对四个点进行过更新)
     int[] SHIPX={20,150,1150,1280};
     int[] SHIPY={200,450,450,200};

    TreeSet<Integer> Bay = new TreeSet<>();  //统计倍数总数
    Map<Integer,Integer> MaxTierUnder = new HashMap<>(); //记录甲板下每个倍最高层《倍号，层数》
    Map<Integer,Integer> MaxTierAbove = new HashMap<>(); //记录甲板上最高层
    Map<Integer,Integer> evenNumberBay =  new HashMap<>();//专门用于记录偶数倍位的倍号和层号，用来重绘大尺寸集装箱


    private List<VesselStructureInfo> vesselStructureInfoList;
    private List<AutoStowResultInfo> autoStowInfoList;

   public VesselImageFrame(List<VesselStructureInfo> vesselStructureInfoList, List<AutoStowResultInfo> autoStowInfoList) {
       this.vesselStructureInfoList = vesselStructureInfoList;
       this.autoStowInfoList = autoStowInfoList;
       initComponents();
   }

    private void initComponents(){
        Container c = getContentPane();
//        c.setLayout(new BorderLayout(10,10));
        this.setTitle("侧视图");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1400,800);
        this.setLocationRelativeTo(null);

        //读取数据
        //java.util.List<VesselStructureInfo> vesselStructureInfos = importDataInfo.VesselStructureInfoList;
        Integer[] rowData = new Integer[3];
        for (VesselStructureInfo vesselStructureInfo : vesselStructureInfoList) {
            //                rowData[0] = vesselStructureInfo.getVHTID();      //舱次
            rowData[0] =Integer.valueOf(vesselStructureInfo.getVBYBAYID());     //倍
            rowData[1] =Integer.valueOf(vesselStructureInfo.getVTRTIERNO());    //层
            //                rowData[2] = vesselStructureInfo.getVRWROWNO();     //排

            Bay.add(rowData[0]); //统计倍数
            if(rowData[0]%2==0){//偶数大倍
                int lowerbay = rowData[0] - 1;//偶数大倍中的小奇数倍
                int higherbay = rowData[0] + 1;//偶数大倍中的大奇数倍
                if( rowData[1] < 80 &&  rowData[1] >= 0){
                    evenNumberBay.put(rowData[0],rowData[1]/2);

                    if(!MaxTierUnder.containsKey(lowerbay) || MaxTierUnder.get(lowerbay) < rowData[1]){
                        MaxTierUnder.put(lowerbay, rowData[1]/2);
                    }
                    if(!MaxTierUnder.containsKey(higherbay) || MaxTierUnder.get(higherbay) < rowData[1]){
                        MaxTierUnder.put(higherbay, rowData[1]/2);
                    }
                    if(MaxTierUnderForDrawBoat < rowData[1]/2) MaxTierUnderForDrawBoat = rowData[1]/2;

                }else if(rowData[1] > 80){
                    evenNumberBay.put(rowData[0],(rowData[1]-80)/2);

                    if(!MaxTierAbove.containsKey(lowerbay) ||MaxTierAbove.get(lowerbay) < rowData[1]){
                        MaxTierAbove.put(lowerbay, (rowData[1]-80)/2);
                    }
                    if(!MaxTierAbove.containsKey(higherbay) ||MaxTierAbove.get(higherbay) < rowData[1]){
                        MaxTierAbove.put(higherbay, (rowData[1]-80)/2);
                    }
                    if(MaxTierAboveForDrawString < (rowData[1]-80)/2) MaxTierAboveForDrawString = (rowData[1]-80)/2;
                }

            }else{//奇数倍
                if( rowData[1] < 80 &&  rowData[1] >= 0){
                    if(!MaxTierUnder.containsKey(rowData[0]) || MaxTierUnder.get(rowData[0]) < rowData[1]){
                        MaxTierUnder.put(rowData[0], rowData[1]/2);
                    }
                    if(MaxTierUnderForDrawBoat < rowData[1]/2) MaxTierUnderForDrawBoat = rowData[1]/2;

                }else if(rowData[1] > 80){
                    if(!MaxTierAbove.containsKey(rowData[0]) ||MaxTierAbove.get(rowData[0]) < rowData[1]){
                        MaxTierAbove.put(rowData[0], (rowData[1]-80)/2);
                    }
                    if(MaxTierAboveForDrawString < (rowData[1]-80)/2) MaxTierAboveForDrawString = (rowData[1]-80)/2;
                }
            }

        }
        bayCount = Bay.size();//倍数
        //更新船的四个点坐标，为了留有一定空间。进行+2，+3处理
        SHIPY[0] = SHIPY[1] - (MaxTierUnderForDrawBoat + 3) * 15;
        SHIPY[3] = SHIPY[2] - (MaxTierUnderForDrawBoat + 3) * 15;
        SHIPX[2] = SHIPX[1] + (bayCount+2)*boxWidth + gap*(bayCount/2);
        SHIPX[3] = SHIPX[2] +130;

        //横向滚动条
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //顶部Panel
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        label = new JLabel("请输入（或点击）选择要查询的倍数");
        label.setFont(new Font("",0,20));

        textField = new JTextField(10);
        textField.setPreferredSize(new Dimension(10,28));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("",0,20));
        textField.setForeground(Color.BLUE);

        //单击按钮
        button = new JButton("确定");
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!textField.getText().matches("[0-9]+") || Integer.valueOf(textField.getText()) % 2 == 0){//判断输入是否合法。为奇数整数
                        JFrame dialogBackground = new JFrame();
                        dialogBackground.setSize(300,300);
                        dialogBackground.setTitle("输入错误");
                        dialogBackground.add(new JLabel("         输入错误"));
                        dialogBackground.setLocationRelativeTo(null);
                        dialogBackground.setVisible(true);
                    }else {
                        int bayID = Integer.valueOf(textField.getText());//获取倍数
                        ContainerImageFrame containerImageFrame = new ContainerImageFrame(vesselStructureInfoList,autoStowInfoList,bayID); //集装箱倍位概图界面
                        containerImageFrame.setVisible(true);
                    }
                }
            });
        }


        //添加顶部panel
        topPanel.add(label);
        topPanel.add(textField);
        topPanel.add(button);

        //画船和集装箱
        contentPanel = new JPanel();
//        contentPanel.setLayout(null);
        DrawContainers drawContainers = new DrawContainers();   //画集装箱
        DrawShip drawShip = new DrawShip();  //画船
        drawContainers.addMouseListener(drawContainers);//添加鼠标点击监控
        contentPanel.add(drawShip);
        contentPanel.add(drawContainers);
        contentPanel.setPreferredSize(new Dimension((int)((SHIPX[2] - SHIPX[1])*1.5),650));//设置内容panel的大小，从而让滚动条生效
        scrollPane.setViewportView(contentPanel);

        //添加到滚动条panel中
        forScrollPanel = new JPanel();
        forScrollPanel.setLayout(new BorderLayout(10,10));
        forScrollPanel.add("North",topPanel);
        forScrollPanel.add("Center",scrollPane);

        c.add(forScrollPanel);
    }

    //画船体
     class DrawShip extends JPanel{
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D drawShip;
            drawShip=(Graphics2D)g;
            drawShip.setColor(Color.BLUE); //设置弧形的颜色为黑色
            drawShip.setStroke(new BasicStroke(5)); //船的粗细

            drawShip.drawLine(SHIPX[0],SHIPY[0],SHIPX[3],SHIPY[3]);//画甲板线
            drawShip.drawLine(SHIPX[1],SHIPY[1],SHIPX[2],SHIPY[2]);//画船底线
            QuadCurve2D curveLeft = new QuadCurve2D.Double(SHIPX[0],SHIPY[0],SHIPX[1]+20,SHIPY[1]-100,SHIPX[1],SHIPY[1]);//画船的左侧线条
            QuadCurve2D curveRight = new QuadCurve2D.Double(SHIPX[2],SHIPY[2],SHIPX[2]-20,SHIPY[2]-100,SHIPX[3],SHIPY[3]);//画船的右侧线条
            drawShip.draw(curveLeft);
            drawShip.draw(curveRight);

            drawShip.setFont(new Font("",Font.BOLD,30));
            drawShip.drawString("船头",SHIPX[0],SHIPY[1]);
            this.setOpaque(false);  //透明
            this.setBounds(100,100,2000,1500);
        }
    }

    //画集装箱
     class DrawContainers extends JPanel implements MouseListener{
        Rectangle2D[] rectangle2D = new Rectangle2D[(Bay.last()+1)/2]; //建立一个数组。用于响应鼠标点击事件
        public   void  paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D drawboxes;
            drawboxes = (Graphics2D)g;
            drawboxes.setColor(Color.BLACK);
            drawboxes.setBackground(null);
            drawboxes.setStroke(new BasicStroke(2));//集装箱粗细
            drawboxes.setFont(new Font("",Font.BOLD,15));
            this.setBounds(100,100,2000,1500);
            this.setOpaque(false);

            //画集装箱，添加文字
            for(int i = 1 ; i <= Bay.last();i++) {

                //添加倍的文字
                if(i%4==1){
                    drawboxes.drawString(i+"B",SHIPX[1]+gap+ boxWidth * (i/2) + (i/4)*gap,SHIPY[1] + 20);
                    drawboxes.drawString(i+"A",SHIPX[1]+gap+ boxWidth * (i/2) + (i/4)*gap,SHIPY[1]-(MaxTierUnderForDrawBoat + 3)*boxHeight - (MaxTierAboveForDrawString + 3) * boxHeight);
//                    drawboxes.drawString(i*2+1+"B",SHIPX[1]+ boxWidth * (i+1) + (i/2)*gap,SHIPY[1] + 20);
//                    drawboxes.drawString(i*2+1+"A",SHIPX[1]+ boxWidth * (i+1) + (i/2)*gap,SHIPY[1]-(MaxTierUnderForDrawBoat + 3)*boxHeight - (MaxTierAboveForDrawString + 3) * boxHeight);

                }
                //画集装箱
                if(MaxTierUnder.containsKey(i)){
                    for(int j=0;j<MaxTierUnder.get(i);j++){
                        Rectangle2D rectangle2D = new Rectangle2D.Double(SHIPX[1]+gap+ boxWidth * (i/2) + (i/4)*gap,SHIPY[1] - boxHeight*(j+1)-5,boxWidth,boxHeight);
                        drawboxes.draw(rectangle2D);
                    }
                }
                if(MaxTierAbove.containsKey(i)) {
                    for (int j = 0; j < MaxTierAbove.get(i); j++) {
                        Rectangle2D rectangle2D = new Rectangle2D.Double(SHIPX[1]+gap + boxWidth * (i/2) + (i / 4) * gap, SHIPY[0] - boxHeight * (j + 1) - 5, boxWidth, boxHeight);
                        drawboxes.draw(rectangle2D);
                    }
                }
            }

            //画偶数倍,******************目前假设13一定是一个大倍，不会出现35是一个大倍******************
            for(int key :evenNumberBay.keySet()){
                Rectangle2D rectangle2D = new Rectangle2D.Double(SHIPX[1]+gap+ boxWidth * ((key-1)/2) + ((key-1)/4)*gap,SHIPY[1] - boxHeight*(evenNumberBay.get(key))-5,2*boxWidth,boxHeight);
                drawboxes.setColor(DrawContainers.this.getBackground());
                drawboxes.fill(rectangle2D);
                drawboxes.setColor(Color.black);
                rectangle2D = new Rectangle2D.Double(SHIPX[1]+gap+ boxWidth * ((key-1)/2) + ((key-1)/4)*gap,SHIPY[1] - boxHeight*(evenNumberBay.get(key))-5,2*boxWidth,boxHeight);
                drawboxes.draw(rectangle2D);

            }


            //画用于单击选择的透明倍位矩形，矩形的范围略大于集装箱
            for(int i = 1 ; i <= Bay.last();i+=2) {
                //画一个透明的倍位框，用来单击选择倍位
                int yStart = SHIPY[1]-(MaxTierUnderForDrawBoat + 3)*boxHeight - (MaxTierAboveForDrawString + 3) * boxHeight - 10;//透明倍位的纵坐标起点。略高于集装箱
                 rectangle2D[(i-1)/2] = new Rectangle2D.Double(SHIPX[1]+gap+ boxWidth * (i/2) + (i/4)*gap,yStart,boxWidth,SHIPY[1] - yStart + 20);//画斗透明倍位
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f); //最后一个参数代表不透明度 即 当 最后一个参数为 0.1f 时 画笔的透明度就为90%
                drawboxes.setComposite(ac);
                drawboxes.fill( rectangle2D[(i-1)/2]);//填充透明的倍位
            }
        }

        //判断点击坐标在哪个倍位
        public void mouseClicked(MouseEvent e){
            if(e.getClickCount()==1){
                for(int i=1;i<=Bay.last();i+=2){
                    if(rectangle2D[(i-1)/2].contains(e.getX(),e.getY())){
                        textField.setText(i+"");
                        textField.setVisible(true);
                    }
                }
            }
            if(e.getClickCount()==2){
                for(int i=1;i<=Bay.last();i+=2){
                    if(rectangle2D[(i-1)/2].contains(e.getX(),e.getY())){
                        textField.setText(i+"");
                        textField.setVisible(true);
                    }
                }
                ContainerImageFrame containerImageFrame = new ContainerImageFrame(vesselStructureInfoList,autoStowInfoList,Integer.valueOf(textField.getText())); //集装箱倍位概图界面
                containerImageFrame.setVisible(true);
            }
        }
        public void mouseReleased(MouseEvent e){

        }
        public void mouseEntered(MouseEvent e){

        }
        public void mousePressed(MouseEvent e){

        }
        public void mouseExited(MouseEvent e){

        }
    }

}
