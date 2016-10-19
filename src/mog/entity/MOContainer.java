package mog.entity;

/**
 * Created by liuminhang on 16/4/6.
 */
/*
集装箱对象
 */
public class MOContainer {

    public String containerNo;       //箱号,可空
    public String type;     //箱型,决定了高度,以及其他一些属性
    public int weightKg;    //重量,kg
    public String eof;      //empty or full,箱空重
    public String size;     //箱尺寸

    public MOContainer() {
    }

    public MOContainer(String containerNo, String type, int weightKg, String eof, String size) {
        this.containerNo = containerNo;
        this.type = type;
        this.weightKg = weightKg;
        this.eof = eof;
        this.size = size;
    }
}
