package importDataInfo;

/**
 * Created by leko on 2016/1/15.
 */
public class ContainerInfo {
    private String containerId;                 //唯一箱id
    private String IYCCNTRNO;                    //箱号
    private String IYCCNTRAREAID;             //箱所在箱区号
    private Long IYCVOYID;                     //航次ID

    private String IYCCTYPECD;                   //箱型
    private String IYCCSZCSIZECD;              //尺寸
    private String IYCPORTCD;                    //目的港

    private Integer IYCWEIGHT;                     //箱重

    private String IYCDNGFG;                      //是否危险品(Y,N)
    private String IYCREFFG;                      //是否冷藏(Y,N)
    private String IYCYLOCATION;                 //场箱位
    private String IYCPLANFG;                    //是否可配载
    private String IYCRETIME;                    //当前作业该箱所需的倒箱时间

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getIYCCNTRNO() {
        return IYCCNTRNO;
    }

    public void setIYCCNTRNO(String IYCCNTRNO) {
        this.IYCCNTRNO = IYCCNTRNO;
    }

    public String getIYCCNTRAREAID() {
        return IYCCNTRAREAID;
    }

    public void setIYCCNTRAREAID(String IYCCNTRAREAID) {
        this.IYCCNTRAREAID = IYCCNTRAREAID;
    }

    public Long getIYCVOYID() {
        return IYCVOYID;
    }

    public void setIYCVOYID(Long IYCVOYID) {
        this.IYCVOYID = IYCVOYID;
    }

    public String getIYCCTYPECD() {
        return IYCCTYPECD;
    }

    public void setIYCCTYPECD(String IYCCTYPECD) {
        this.IYCCTYPECD = IYCCTYPECD;
    }

    public String getIYCCSZCSIZECD() {
        return IYCCSZCSIZECD;
    }

    public void setIYCCSZCSIZECD(String IYCCSZCSIZECD) {
        this.IYCCSZCSIZECD = IYCCSZCSIZECD;
    }

    public String getIYCPORTCD() {
        return IYCPORTCD;
    }

    public void setIYCPORTCD(String IYCPORTCD) {
        this.IYCPORTCD = IYCPORTCD;
    }

    public Integer getIYCWEIGHT() {
        return IYCWEIGHT;
    }

    public void setIYCWEIGHT(Integer IYCWEIGHT) {
        this.IYCWEIGHT = IYCWEIGHT;
    }

    public String getIYCDNGFG() {
        return IYCDNGFG;
    }

    public void setIYCDNGFG(String IYCDNGFG) {
        this.IYCDNGFG = IYCDNGFG;
    }

    public String getIYCREFFG() {
        return IYCREFFG;
    }

    public void setIYCREFFG(String IYCREFFG) {
        this.IYCREFFG = IYCREFFG;
    }

    public String getIYCYLOCATION() {
        return IYCYLOCATION;
    }

    public void setIYCYLOCATION(String IYCYLOCATION) {
        this.IYCYLOCATION = IYCYLOCATION;
    }

    public String getIYCPLANFG() {
        return IYCPLANFG;
    }

    public void setIYCPLANFG(String IYCPLANFG) {
        this.IYCPLANFG = IYCPLANFG;
    }

    public String getIYCRETIME() {
        return IYCRETIME;
    }

    public void setIYCRETIME(String IYCRETIME) {
        this.IYCRETIME = IYCRETIME;
    }
}
