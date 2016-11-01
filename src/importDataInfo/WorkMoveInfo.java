package importDataInfo;

/**
 * Created by leko on 2016/1/18.
 */
public class WorkMoveInfo {
    private Long CWPWORKMOVENUM;     //Move的顺序
    private String DECK;                //D甲板下H甲板上
    private Integer GLOBALPRIORITY;     //全为2
    private String HATCH;               //舱
    private Double HORIZONTALPOSITION;  //水平交互点
    private String LD;                  //D卸船L装船
    private String MOVETYPE;            //作业工艺（暂时写尺寸）

    public Long getCWPWORKMOVENUM() {
        return CWPWORKMOVENUM;
    }

    public void setCWPWORKMOVENUM(Long CWPWORKMOVENUM) {
        this.CWPWORKMOVENUM = CWPWORKMOVENUM;
    }

    public String getDECK() {
        return DECK;
    }

    public void setDECK(String DECK) {
        this.DECK = DECK;
    }

    public Integer getGLOBALPRIORITY() {
        return GLOBALPRIORITY;
    }

    public void setGLOBALPRIORITY(Integer GLOBALPRIORITY) {
        this.GLOBALPRIORITY = GLOBALPRIORITY;
    }

    public String getHATCH() {
        return HATCH;
    }

    public void setHATCH(String HATCH) {
        this.HATCH = HATCH;
    }

    public Double getHORIZONTALPOSITION() {
        return HORIZONTALPOSITION;
    }

    public void setHORIZONTALPOSITION(Double HORIZONTALPOSITION) {
        this.HORIZONTALPOSITION = HORIZONTALPOSITION;
    }

    public String getLD() {
        return LD;
    }

    public void setLD(String LD) {
        this.LD = LD;
    }

    public String getMOVETYPE() {
        return MOVETYPE;
    }

    public void setMOVETYPE(String MOVETYPE) {
        this.MOVETYPE = MOVETYPE;
    }
}
