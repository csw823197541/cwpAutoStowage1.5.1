package importDataInfo;

/**
 * Created by leko on 2016/1/21.
 */
public class GroupInfo {

    private String groupID;   //属性组
    private String port;      //港口
    private String type;      //箱型
    private String size;      //尺寸

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
