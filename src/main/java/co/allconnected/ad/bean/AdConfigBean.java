package co.allconnected.ad.bean;

import java.util.List;

/**
 * 广告配置基类
 *
 * @author michael
 * @time 17/1/9 上午10:27
 */
public class AdConfigBean {

    private boolean enable;

    private List<AdBean> ad;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<AdBean> getAd() {
        return ad;
    }

    public void setAd(List<AdBean> ad) {
        this.ad = ad;
    }

    public static class AdBean {
        private int platformId;
        private String id;
        private String fresh_id;
        private int type;

        public int getPlatformId() {
            return platformId;
        }

        public void setPlatformId(int platformId) {
            this.platformId = platformId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFresh_id() {
            return fresh_id;
        }

        public void setFresh_id(String fresh_id) {
            this.fresh_id = fresh_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
