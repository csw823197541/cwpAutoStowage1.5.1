package importDataInfo;

import java.util.List;

/**
 * Created by csw on 2016/1/14.
 */
public class CraneInfo {

    private Integer CURRENTPOSITION;//当前位置
    private Integer DISCHARGEEFFICIENCY20;//卸船20英尺箱效率
    private Integer DISCHARGEEFFICIENCY40;//卸船40英尺箱效率
    private Integer DISCHARGEEFFICIENCYTWIN;//卸船双吊具效率
    private Integer DISCHARGEEFFICIENCYTDM;//卸船双箱吊效率
    private String ID;//桥吊ID
    private Integer LOADINGEFFICIENCY20;//装船20英尺箱效率
    private Integer LOADINGEFFICIENCY40;//装船40英尺箱效率
    private Integer LOADINGEFFICIENCYTWIN;//装船双吊具效率
    private Integer LOADINGEFFICIENCYTDM; //装船双箱吊效率
    private Integer MOVINGRANGEFROM;//移动范围起始点
    private Integer MOVINGRANGETO;//移动范围终止点
    private String NAME;//桥吊编号
    private Integer SAFESPAN;//安全距离
    private Integer SEQ;//桥吊序列号
    private Integer SPEED;//移动速度
    private Integer WIDTH;//桥吊宽度
    private List<WorkingTimeRange> WORKINGTIMERANGES;//工作时间

    public Integer getDISCHARGEEFFICIENCYTDM() {
        return DISCHARGEEFFICIENCYTDM;
    }

    public void setDISCHARGEEFFICIENCYTDM(Integer DISCHARGEEFFICIENCYTDM) {
        this.DISCHARGEEFFICIENCYTDM = DISCHARGEEFFICIENCYTDM;
    }

    public Integer getLOADINGEFFICIENCYTDM() {
        return LOADINGEFFICIENCYTDM;
    }

    public void setLOADINGEFFICIENCYTDM(Integer LOADINGEFFICIENCYTDM) {
        this.LOADINGEFFICIENCYTDM = LOADINGEFFICIENCYTDM;
    }

    public Integer getCURRENTPOSITION() {
        return CURRENTPOSITION;
    }

    public void setCURRENTPOSITION(Integer CURRENTPOSITION) {
        this.CURRENTPOSITION = CURRENTPOSITION;
    }

    public Integer getDISCHARGEEFFICIENCY20() {
        return DISCHARGEEFFICIENCY20;
    }

    public void setDISCHARGEEFFICIENCY20(Integer DISCHARGEEFFICIENCY20) {
        this.DISCHARGEEFFICIENCY20 = DISCHARGEEFFICIENCY20;
    }

    public Integer getDISCHARGEEFFICIENCY40() {
        return DISCHARGEEFFICIENCY40;
    }

    public void setDISCHARGEEFFICIENCY40(Integer DISCHARGEEFFICIENCY40) {
        this.DISCHARGEEFFICIENCY40 = DISCHARGEEFFICIENCY40;
    }

    public Integer getDISCHARGEEFFICIENCYTWIN() {
        return DISCHARGEEFFICIENCYTWIN;
    }

    public void setDISCHARGEEFFICIENCYTWIN(Integer DISCHARGEEFFICIENCYTWIN) {
        this.DISCHARGEEFFICIENCYTWIN = DISCHARGEEFFICIENCYTWIN;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getLOADINGEFFICIENCY20() {
        return LOADINGEFFICIENCY20;
    }

    public void setLOADINGEFFICIENCY20(Integer LOADINGEFFICIENCY20) {
        this.LOADINGEFFICIENCY20 = LOADINGEFFICIENCY20;
    }

    public Integer getLOADINGEFFICIENCY40() {
        return LOADINGEFFICIENCY40;
    }

    public void setLOADINGEFFICIENCY40(Integer LOADINGEFFICIENCY40) {
        this.LOADINGEFFICIENCY40 = LOADINGEFFICIENCY40;
    }

    public Integer getLOADINGEFFICIENCYTWIN() {
        return LOADINGEFFICIENCYTWIN;
    }

    public void setLOADINGEFFICIENCYTWIN(Integer LOADINGEFFICIENCYTWIN) {
        this.LOADINGEFFICIENCYTWIN = LOADINGEFFICIENCYTWIN;
    }

    public Integer getMOVINGRANGEFROM() {
        return MOVINGRANGEFROM;
    }

    public void setMOVINGRANGEFROM(Integer MOVINGRANGEFROM) {
        this.MOVINGRANGEFROM = MOVINGRANGEFROM;
    }

    public Integer getMOVINGRANGETO() {
        return MOVINGRANGETO;
    }

    public void setMOVINGRANGETO(Integer MOVINGRANGETO) {
        this.MOVINGRANGETO = MOVINGRANGETO;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Integer getSAFESPAN() {
        return SAFESPAN;
    }

    public void setSAFESPAN(Integer SAFESPAN) {
        this.SAFESPAN = SAFESPAN;
    }

    public Integer getSEQ() {
        return SEQ;
    }

    public void setSEQ(Integer SEQ) {
        this.SEQ = SEQ;
    }

    public Integer getSPEED() {
        return SPEED;
    }

    public void setSPEED(Integer SPEED) {
        this.SPEED = SPEED;
    }

    public Integer getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(Integer WIDTH) {
        this.WIDTH = WIDTH;
    }

    public List<WorkingTimeRange> getWORKINGTIMERANGES() {
        return WORKINGTIMERANGES;
    }

    public void setWORKINGTIMERANGES(List<WorkingTimeRange> WORKINGTIMERANGES) {
        this.WORKINGTIMERANGES = WORKINGTIMERANGES;
    }
}
