package importDataInfo;

import java.util.Date;

/**
 * Created by leko on 2016/1/22.
 */
public class MoveInfo {

    private String vpcCntrId;                    //唯一编号
    private String craneNo;                 //桥机号
    private Integer moveNum;                     //桥机作业顺序号
    private String moveKind;                //装卸标志
    private String containerId;                  //箱Id号
    private String size;              //箱尺寸
    private String areaPosition;      //计划提箱位置, 场
    private String vesselPosition;       //计划放箱位置, 船箱位：倍.排.层

    private Date workingStartTime;    //cwp计划开始时间
    private Date workingEndTime;    //cwp计划结束时间

    private String workFlow;    //作业工艺
    private Long voyId; //航次Id，进口航次或出口航次

    private String workStatus; //指令状态
    private String workIsExchange; //指令是否可以交换
    private String workIsRepeal; //指令是否可以撤销

    private Integer carryOrder; //装上AGV的顺序

    private String DSTPORT; //目的港
    private Integer WEIGHT; //重量

    public String getDSTPORT() {
        return DSTPORT;
    }

    public void setDSTPORT(String DSTPORT) {
        this.DSTPORT = DSTPORT;
    }

    public Integer getWEIGHT() {
        return WEIGHT;
    }

    public void setWEIGHT(Integer WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    public String getVpcCntrId() {
        return vpcCntrId;
    }

    public void setVpcCntrId(String vpcCntrId) {
        this.vpcCntrId = vpcCntrId;
    }

    public String getCraneNo() {
        return craneNo;
    }

    public void setCraneNo(String craneNo) {
        this.craneNo = craneNo;
    }

    public Integer getMoveNum() {
        return moveNum;
    }

    public void setMoveNum(Integer moveNum) {
        this.moveNum = moveNum;
    }

    public String getMoveKind() {
        return moveKind;
    }

    public void setMoveKind(String moveKind) {
        this.moveKind = moveKind;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAreaPosition() {
        return areaPosition;
    }

    public void setAreaPosition(String areaPosition) {
        this.areaPosition = areaPosition;
    }

    public String getVesselPosition() {
        return vesselPosition;
    }

    public void setVesselPosition(String vesselPosition) {
        this.vesselPosition = vesselPosition;
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

    public String getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(String workFlow) {
        this.workFlow = workFlow;
    }

    public Long getVoyId() {
        return voyId;
    }

    public void setVoyId(Long voyId) {
        this.voyId = voyId;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getWorkIsExchange() {
        return workIsExchange;
    }

    public void setWorkIsExchange(String workIsExchange) {
        this.workIsExchange = workIsExchange;
    }

    public String getWorkIsRepeal() {
        return workIsRepeal;
    }

    public void setWorkIsRepeal(String workIsRepeal) {
        this.workIsRepeal = workIsRepeal;
    }

    public Integer getCarryOrder() {
        return carryOrder;
    }

    public void setCarryOrder(Integer carryOrder) {
        this.carryOrder = carryOrder;
    }
}
