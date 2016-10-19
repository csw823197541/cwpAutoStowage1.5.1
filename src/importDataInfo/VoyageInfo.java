package importDataInfo;

import java.util.Date;

/**
 * 航次信息类
 * Created by csw on 2016/1/16.
 */
public class VoyageInfo {

    private Integer VOTVOYID;//航次ID
    private String VESSELID;//船舶ID
    private Date VOTPWKSTTM;//计划开工时间
    private Date VOTPWKENTM;//计划完工时间
    private Integer STARTPOSITION;//船头停泊位置
    private Integer ENDPOSITION;//船尾停泊位置

    public Integer getVOTVOYID() {
        return VOTVOYID;
    }

    public void setVOTVOYID(Integer VOTVOYID) {
        this.VOTVOYID = VOTVOYID;
    }

    public String getVESSELID() {
        return VESSELID;
    }

    public void setVESSELID(String VESSELID) {
        this.VESSELID = VESSELID;
    }

    public Date getVOTPWKSTTM() {
        return VOTPWKSTTM;
    }

    public void setVOTPWKSTTM(Date VOTPWKSTTM) {
        this.VOTPWKSTTM = VOTPWKSTTM;
    }

    public Date getVOTPWKENTM() {
        return VOTPWKENTM;
    }

    public void setVOTPWKENTM(Date VOTPWKENTM) {
        this.VOTPWKENTM = VOTPWKENTM;
    }

    public Integer getSTARTPOSITION() {
        return STARTPOSITION;
    }

    public void setSTARTPOSITION(Integer STARTPOSITION) {
        this.STARTPOSITION = STARTPOSITION;
    }

    public Integer getENDPOSITION() {
        return ENDPOSITION;
    }

    public void setENDPOSITION(Integer ENDPOSITION) {
        this.ENDPOSITION = ENDPOSITION;
    }
}
