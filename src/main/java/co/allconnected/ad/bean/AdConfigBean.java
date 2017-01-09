package co.allconnected.ad.bean;

import java.util.ArrayList;

/**
 * 广告配置基类
 *
 * @author michael
 * @time 17/1/9 上午10:27
 */
public class AdConfigBean {

    /**
     * 广告位id
     */
    private String positionId;

    /**
     * 广告id
     */
    private Object id;
    /**
     * 要不要展示广告
     */
    private boolean enable;
    /**
     * 广告平台,按优先级排序
     */
    private ArrayList<String> platformList;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public ArrayList<String> getPlatformList() {
        return platformList;
    }

    public void setPlatformList(ArrayList<String> platformList) {
        this.platformList = platformList;
    }

    @Override
    public String toString() {
        return "AdConfigBean{" +
                "positionId=" + positionId +
                ", id=" + id +
                ", enable=" + enable +
                ", platformList=" + platformList +
                '}';
    }
}
