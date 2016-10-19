package importDataInfo;

/**
 * Created by leko on 2016/1/22.
 */
public class AutoStowResultInfo {

    private String containerId; //箱Id
    private String containerNum; //箱号
    private String vesselPosition;   //船上位置
    private String areaPosition;     //箱区位置
    private String size;        //尺寸
    private Long voyId;    //艘次信息
    private String unStowedReason;  //未配载原因

    private String weightLevel; //重量等级

    public String getWeightLevel() {
        return weightLevel;
    }

    public void setWeightLevel(String weightLevel) {
        this.weightLevel = weightLevel;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getContainerNum() {
        return containerNum;
    }

    public void setContainerNum(String containerNum) {
        this.containerNum = containerNum;
    }

    public String getUnStowedReason() {
        return unStowedReason;
    }

    public void setUnStowedReason(String unStowedReason) {
        this.unStowedReason = unStowedReason;
    }

    public Long getVoyId() {
        return voyId;
    }

    public void setVoyId(Long voyId) {
        this.voyId = voyId;
    }

    public String getVesselPosition() {
        return vesselPosition;
    }

    public void setVesselPosition(String vesselPosition) {
        this.vesselPosition = vesselPosition;
    }

    public String getAreaPosition() {
        return areaPosition;
    }

    public void setAreaPosition(String areaPosition) {
        this.areaPosition = areaPosition;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
