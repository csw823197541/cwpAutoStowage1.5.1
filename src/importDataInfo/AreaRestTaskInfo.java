package importDataInfo;

/**
 * Created by csw on 2016/10/20 9:21.
 * Explain:
 */
public class AreaRestTaskInfo {

    private String areaNo; //箱区号
    private Integer restTaskNumber; //箱区还能作业多少任务数

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public Integer getRestTaskNumber() {
        return restTaskNumber;
    }

    public void setRestTaskNumber(Integer restTaskNumber) {
        this.restTaskNumber = restTaskNumber;
    }
}
