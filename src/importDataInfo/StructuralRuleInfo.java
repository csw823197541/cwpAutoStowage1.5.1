package importDataInfo;

/**
 * Created by csw on 2016/11/2 13:51.
 * Explain:
 */
public class StructuralRuleInfo {

    private String vesselHatchId;//舱ID
    private String bayNo;//倍位号
    private String rowNo;//槽号(排号)
    private String tireNo;//层号
    private Integer size20AllWeight;//20尺单槽负荷重量
    private Integer size40AllWeight;//40或45尺单槽负荷重量
    private Integer highConNumber;//该槽可放的高箱数目

    public String getVesselHatchId() {
        return vesselHatchId;
    }

    public void setVesselHatchId(String vesselHatchId) {
        this.vesselHatchId = vesselHatchId;
    }

    public String getBayNo() {
        return bayNo;
    }

    public void setBayNo(String bayNo) {
        this.bayNo = bayNo;
    }

    public String getRowNo() {
        return rowNo;
    }

    public void setRowNo(String rowNo) {
        this.rowNo = rowNo;
    }

    public String getTireNo() {
        return tireNo;
    }

    public void setTireNo(String tireNo) {
        this.tireNo = tireNo;
    }

    public Integer getSize20AllWeight() {
        return size20AllWeight;
    }

    public void setSize20AllWeight(Integer size20AllWeight) {
        this.size20AllWeight = size20AllWeight;
    }

    public Integer getSize40AllWeight() {
        return size40AllWeight;
    }

    public void setSize40AllWeight(Integer size40AllWeight) {
        this.size40AllWeight = size40AllWeight;
    }

    public Integer getHighConNumber() {
        return highConNumber;
    }

    public void setHighConNumber(Integer highConNumber) {
        this.highConNumber = highConNumber;
    }
}
