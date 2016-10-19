package importDataInfo;

/**
 * 船舶结构
 * Created by csw on 2016/1/16.
 */
public class VesselStructureInfo {

    private String VHTID;//舱次ID
    private Integer LENGTH;//舱位长度
    private Double VHTPOSITION; //舱开始相对于船头位置
    private String VBYBAYID;//倍位ID
    private Double VBYPOSITION;//倍位中心相对于船头位置
    private String VTRTIERNO;//层号
    private Integer VTRTIERSEQ;//层序号
    private String VRWROWNO;//排号
    private Integer VRWROWSEQ;//排序号
    private String groupId;//属性组
    private Integer CABPOSITION;//驾驶室位置信息
    private Integer CABLENGTH;//驾驶室长度

    private Integer weightGradeMin;      //重量等级最小值
    private Integer weightGradeMax;      //重量等级最大值

    public Integer getWeightGradeMin() {
        return weightGradeMin;
    }

    public void setWeightGradeMin(Integer weightGradeMin) {
        this.weightGradeMin = weightGradeMin;
    }

    public Integer getWeightGradeMax() {
        return weightGradeMax;
    }

    public void setWeightGradeMax(Integer weightGradeMax) {
        this.weightGradeMax = weightGradeMax;
    }

    public String getVHTID() {
        return VHTID;
    }

    public void setVHTID(String VHTID) {
        this.VHTID = VHTID;
    }

    public Integer getLENGTH() {
        return LENGTH;
    }

    public void setLENGTH(Integer LENGTH) {
        this.LENGTH = LENGTH;
    }

    public Double getVHTPOSITION() {
        return VHTPOSITION;
    }

    public void setVHTPOSITION(Double VHTPOSITION) {
        this.VHTPOSITION = VHTPOSITION;
    }

    public String getVBYBAYID() {
        return VBYBAYID;
    }

    public void setVBYBAYID(String VBYBAYID) {
        this.VBYBAYID = VBYBAYID;
    }

    public Double getVBYPOSITION() {
        return VBYPOSITION;
    }

    public void setVBYPOSITION(Double VBYPOSITION) {
        this.VBYPOSITION = VBYPOSITION;
    }

    public String getVTRTIERNO() {
        return VTRTIERNO;
    }

    public void setVTRTIERNO(String VTRTIERNO) {
        this.VTRTIERNO = VTRTIERNO;
    }

    public Integer getVTRTIERSEQ() {
        return VTRTIERSEQ;
    }

    public void setVTRTIERSEQ(Integer VTRTIERSEQ) {
        this.VTRTIERSEQ = VTRTIERSEQ;
    }

    public String getVRWROWNO() {
        return VRWROWNO;
    }

    public void setVRWROWNO(String VRWROWNO) {
        this.VRWROWNO = VRWROWNO;
    }

    public Integer getVRWROWSEQ() {
        return VRWROWSEQ;
    }

    public void setVRWROWSEQ(Integer VRWROWSEQ) {
        this.VRWROWSEQ = VRWROWSEQ;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getCABPOSITION() {
        return CABPOSITION;
    }

    public void setCABPOSITION(Integer CABPOSITION) {
        this.CABPOSITION = CABPOSITION;
    }

    public Integer getCABLENGTH() {
        return CABLENGTH;
    }

    public void setCABLENGTH(Integer CABLENGTH) {
        this.CABLENGTH = CABLENGTH;
    }
}
