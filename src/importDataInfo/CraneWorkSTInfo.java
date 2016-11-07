package importDataInfo;

import java.util.Date;

/**
 * Created by csw on 2016/11/3 10:56.
 * Explain:
 */
public class CraneWorkSTInfo {

    private String craneNo; //桥机编号
    private Date workStartTime; //桥机作业开始时间

    public String getCraneNo() {
        return craneNo;
    }

    public void setCraneNo(String craneNo) {
        this.craneNo = craneNo;
    }

    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }
}
