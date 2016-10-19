package importDataInfo;

import java.util.Date;

/**
 * Created by csw on 2016/1/20.
 */
public class CwpResultMoveInfo {

    private String CRANEID;//桥机ID

    private String HATCHID;//舱ID
    private String vesselPosition;   //船箱位：倍.排.层

    private String VESSELID;//船舶ID
    private Integer WORKINGENDTIME;//结束时间
    private Integer WORKINGSTARTTIME;//起始时间

    private Integer moveOrder;  //作业顺序
    private String MOVETYPE;//作业工艺
    private String LDULD;    //装卸船标志


    private Date workingStartTime;     //开始作业时间
    private Date workingEndTime;        //结束作业时间
    private Integer moveWorkTime;   //每一个move的作业时间(单位是：秒)

    private String size;    //尺寸

    private Double CranesPosition;//桥机当前位置
    private String HATCHBWID;//倍位ID

    public Double getCranesPosition() {
        return CranesPosition;
    }

    public void setCranesPosition(Double cranesPosition) {
        CranesPosition = cranesPosition;
    }

    public String getHATCHBWID() {
        return HATCHBWID;
    }

    public void setHATCHBWID(String HATCHBWID) {
        this.HATCHBWID = HATCHBWID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getMoveOrder() {
        return moveOrder;
    }

    public void setMoveOrder(Integer moveOrder) {
        this.moveOrder = moveOrder;
    }

    public String getVesselPosition() {
        return vesselPosition;
    }

    public void setVesselPosition(String vesselPosition) {
        this.vesselPosition = vesselPosition;
    }

    public Integer getMoveWorkTime() {
        return moveWorkTime;
    }

    public void setMoveWorkTime(Integer moveWorkTime) {
        this.moveWorkTime = moveWorkTime;
    }

    public Date getWorkingStartTime() {
        return workingStartTime;
    }

    public void setWorkingStartTime(Date workingStartTime) {
        this.workingStartTime = workingStartTime;
    }

    public Date getWorkingEndTime() {
        return workingEndTime;
    }

    public void setWorkingEndTime(Date workingEndTime) {
        this.workingEndTime = workingEndTime;
    }

    public String getMOVETYPE() {
        return MOVETYPE;
    }

    public void setMOVETYPE(String MOVETYPE) {
        this.MOVETYPE = MOVETYPE;
    }

    public String getCRANEID() {
        return CRANEID;
    }

    public void setCRANEID(String CRANEID) {
        this.CRANEID = CRANEID;
    }

    public String getHATCHID() {
        return HATCHID;
    }

    public void setHATCHID(String HATCHID) {
        this.HATCHID = HATCHID;
    }

    public String getVESSELID() {
        return VESSELID;
    }

    public void setVESSELID(String VESSELID) {
        this.VESSELID = VESSELID;
    }

    public Integer getWORKINGENDTIME() {
        return WORKINGENDTIME;
    }

    public void setWORKINGENDTIME(Integer WORKINGENDTIME) {
        this.WORKINGENDTIME = WORKINGENDTIME;
    }

    public Integer getWORKINGSTARTTIME() {
        return WORKINGSTARTTIME;
    }

    public void setWORKINGSTARTTIME(Integer WORKINGSTARTTIME) {
        this.WORKINGSTARTTIME = WORKINGSTARTTIME;
    }

    public String getLDULD() {
        return LDULD;
    }

    public void setLDULD(String LDULD) {
        this.LDULD = LDULD;
    }

}
