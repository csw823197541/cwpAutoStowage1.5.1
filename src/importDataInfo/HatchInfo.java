package importDataInfo;

import java.util.List;

/**
 * Created by leko on 2016/1/18.
 */
public class HatchInfo {
    private Double HORIZONTALSTARTPOSITION;        //水平起始交换点
    private String ID;                                 //船舱ID
    private String VESSELID;                          //船舶ID
    private Integer LENGTH;                           //舱位长度
    private Integer MOVECOUNT;                        //MOVE数量
    private String NO;                                 //船舱编号
    private String SEQ;                                //序列
    private List<WorkingTimeRange> WORKINGTIMERANGES;//工作时间

    public Double getHORIZONTALSTARTPOSITION() {
        return HORIZONTALSTARTPOSITION;
    }

    public void setHORIZONTALSTARTPOSITION(Double HORIZONTALSTARTPOSITION) {
        this.HORIZONTALSTARTPOSITION = HORIZONTALSTARTPOSITION;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getVESSELID() {
        return VESSELID;
    }

    public void setVESSELID(String VESSELID) {
        this.VESSELID = VESSELID;
    }

    public Integer getLENGTH() {
        return LENGTH;
    }

    public void setLENGTH(Integer LENGTH) {
        this.LENGTH = LENGTH;
    }

    public Integer getMOVECOUNT() {
        return MOVECOUNT;
    }

    public void setMOVECOUNT(Integer MOVECOUNT) {
        this.MOVECOUNT = MOVECOUNT;
    }

    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getSEQ() {
        return SEQ;
    }

    public void setSEQ(String SEQ) {
        this.SEQ = SEQ;
    }

    public List<WorkingTimeRange> getWORKINGTIMERANGES() {
        return WORKINGTIMERANGES;
    }

    public void setWORKINGTIMERANGES(List<WorkingTimeRange> WORKINGTIMERANGES) {
        this.WORKINGTIMERANGES = WORKINGTIMERANGES;
    }
}
