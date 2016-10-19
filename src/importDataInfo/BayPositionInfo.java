package importDataInfo;

/**
 * Created by leko on 2016/1/18.
 */
public class BayPositionInfo {
    private String VHTID;         //舱位ID
    private String VBYBAYID;      //倍位ID
    private Double VBYPOSITION;   //倍位中心相对船头距离

    public String getVHTID() {
        return VHTID;
    }

    public void setVHTID(String VHTID) {
        this.VHTID = VHTID;
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
}
