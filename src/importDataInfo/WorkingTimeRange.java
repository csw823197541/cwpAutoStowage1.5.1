package importDataInfo;

import java.util.Date;

/**
 * Created by csw on 2016/1/14.
 */
public class WorkingTimeRange {

    private String ID;
    private Date WORKENDTIME;
    private Date WORKSTARTTIME;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getWORKENDTIME() {
        return WORKENDTIME;
    }

    public void setWORKENDTIME(Date WORKENDTIME) {
        this.WORKENDTIME = WORKENDTIME;
    }

    public Date getWORKSTARTTIME() {
        return WORKSTARTTIME;
    }

    public void setWORKSTARTTIME(Date WORKSTARTTIME) {
        this.WORKSTARTTIME = WORKSTARTTIME;
    }
}
