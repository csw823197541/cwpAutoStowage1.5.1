package importDataInfo;

/**
 * 在场箱区信息
 * Created by csw on 2016/1/17.
 */
public class ContainerAreaInfo {

    private Double ASCBOTTOMSPEED;//靠近海侧的吊机移动速度
    private Double ASCTOPSPEED;//靠近陆侧的吊机移动速度
    private String ID;//箱区ID
    private String LOCATIONLB;//箱区左下角坐标
    private String LOCATIONLH;//箱区左上角坐标
    private String LOCATIONRB;//箱区右下角坐标
    private String LOCATIONRH;//箱区右上角坐标
    private Integer VBYNUM;//箱区倍位数
    private Integer VRWNUM;//排的数量
    private Integer VTRNUM;//层的数量
    private String SCTYPE;//箱区吊机的类型
    private Integer WORKEFFICIENCYB;//靠近海侧的吊机的工作效率
    private Integer WORKEFFICIENCYT;//靠近陆侧的吊机的工作效率
    private Integer DISPATCHEDWORK;//该箱区已指派的任务数量，可为空
    private Integer PREDISPATCHEDWORK;//该箱区预估的任务, 可为空

    public Integer getDISPATCHEDWORK() {
        return DISPATCHEDWORK;
    }

    public void setDISPATCHEDWORK(Integer DISPATCHEDWORK) {
        this.DISPATCHEDWORK = DISPATCHEDWORK;
    }

    public Integer getPREDISPATCHEDWORK() {
        return PREDISPATCHEDWORK;
    }

    public void setPREDISPATCHEDWORK(Integer PREDISPATCHEDWORK) {
        this.PREDISPATCHEDWORK = PREDISPATCHEDWORK;
    }

    public Double getASCBOTTOMSPEED() {
        return ASCBOTTOMSPEED;
    }

    public void setASCBOTTOMSPEED(Double ASCBOTTOMSPEED) {
        this.ASCBOTTOMSPEED = ASCBOTTOMSPEED;
    }

    public Double getASCTOPSPEED() {
        return ASCTOPSPEED;
    }

    public void setASCTOPSPEED(Double ASCTOPSPEED) {
        this.ASCTOPSPEED = ASCTOPSPEED;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLOCATIONLB() {
        return LOCATIONLB;
    }

    public void setLOCATIONLB(String LOCATIONLB) {
        this.LOCATIONLB = LOCATIONLB;
    }

    public String getLOCATIONLH() {
        return LOCATIONLH;
    }

    public void setLOCATIONLH(String LOCATIONLH) {
        this.LOCATIONLH = LOCATIONLH;
    }

    public String getLOCATIONRB() {
        return LOCATIONRB;
    }

    public void setLOCATIONRB(String LOCATIONRB) {
        this.LOCATIONRB = LOCATIONRB;
    }

    public String getLOCATIONRH() {
        return LOCATIONRH;
    }

    public void setLOCATIONRH(String LOCATIONRH) {
        this.LOCATIONRH = LOCATIONRH;
    }

    public Integer getVBYNUM() {
        return VBYNUM;
    }

    public void setVBYNUM(Integer VBYNUM) {
        this.VBYNUM = VBYNUM;
    }

    public Integer getVRWNUM() {
        return VRWNUM;
    }

    public void setVRWNUM(Integer VRWNUM) {
        this.VRWNUM = VRWNUM;
    }

    public Integer getVTRNUM() {
        return VTRNUM;
    }

    public void setVTRNUM(Integer VTRNUM) {
        this.VTRNUM = VTRNUM;
    }

    public String getSCTYPE() {
        return SCTYPE;
    }

    public void setSCTYPE(String SCTYPE) {
        this.SCTYPE = SCTYPE;
    }

    public Integer getWORKEFFICIENCYB() {
        return WORKEFFICIENCYB;
    }

    public void setWORKEFFICIENCYB(Integer WORKEFFICIENCYB) {
        this.WORKEFFICIENCYB = WORKEFFICIENCYB;
    }

    public Integer getWORKEFFICIENCYT() {
        return WORKEFFICIENCYT;
    }

    public void setWORKEFFICIENCYT(Integer WORKEFFICIENCYT) {
        this.WORKEFFICIENCYT = WORKEFFICIENCYT;
    }
}
