package importDataInfo;

/**
 * Created by leko on 2016/1/17.
 */
public class PreStowageData {
    private String VHTID;          //舱位ID
    private String VBYBAYID;      //倍位ID
    private String VTRTIERNO;     //层号
    private String VRWROWNO;      //排号
    private String SIZE;            //箱尺寸
    private String CTYPECD;        //箱型
    private String  GROUPID;       //属性组
    private Integer WEIGHT;         //重量等级
    private Integer MOVEORDER;    //move顺序
    private String LDULD;    //装卸船标志
    private String WORKFLOW;    //作业工艺
    private String QCNO;     //桥机号
    private String DSTPORT;  //目的港
    private String THROUGHFLAG;  //过境箱标记

    private String containerNum;    //箱号
    private String containerStatus;     //箱子状态

    private Integer lockWorkFlow; //指定该预配位作业工艺

    private Integer stowageLabel; //是否配载标记

    public String getContainerNum() {
        return containerNum;
    }

    public void setContainerNum(String containerNum) {
        this.containerNum = containerNum;
    }

    public String getContainerStatus() {
        return containerStatus;
    }

    public void setContainerStatus(String containerStatus) {
        this.containerStatus = containerStatus;
    }

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

    public String getVTRTIERNO() {
        return VTRTIERNO;
    }

    public void setVTRTIERNO(String VTRTIERNO) {
        this.VTRTIERNO = VTRTIERNO;
    }

    public String getVRWROWNO() {
        return VRWROWNO;
    }

    public void setVRWROWNO(String VRWROWNO) {
        this.VRWROWNO = VRWROWNO;
    }

    public String getSIZE() {
        return SIZE;
    }

    public void setSIZE(String SIZE) {
        this.SIZE = SIZE;
    }

    public String getCTYPECD() {
        return CTYPECD;
    }

    public void setCTYPECD(String CTYPECD) {
        this.CTYPECD = CTYPECD;
    }

    public String getGROUPID() {
        return GROUPID;
    }

    public void setGROUPID(String GROUPID) {
        this.GROUPID = GROUPID;
    }

    public Integer getWEIGHT() {
        return WEIGHT;
    }

    public void setWEIGHT(Integer WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    public Integer getMOVEORDER() {
        return MOVEORDER;
    }

    public void setMOVEORDER(Integer MOVEORDER) {
        this.MOVEORDER = MOVEORDER;
    }

    public String getLDULD() {
        return LDULD;
    }

    public void setLDULD(String LDULD) {
        this.LDULD = LDULD;
    }

    public String getWORKFLOW() {
        return WORKFLOW;
    }

    public void setWORKFLOW(String WORKFLOW) {
        this.WORKFLOW = WORKFLOW;
    }

    public String getQCNO() {
        return QCNO;
    }

    public void setQCNO(String QCNO) {
        this.QCNO = QCNO;
    }

    public String getDSTPORT() {
        return DSTPORT;
    }

    public void setDSTPORT(String DSTPORT) {
        this.DSTPORT = DSTPORT;
    }

    public String getTHROUGHFLAG() {
        return THROUGHFLAG;
    }

    public void setTHROUGHFLAG(String THROUGHFLAG) {
        this.THROUGHFLAG = THROUGHFLAG;
    }
}
