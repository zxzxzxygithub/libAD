package co.allconnected.ad;

import java.util.List;

import co.allconnected.ad.bean.AdConfigBean;

/**
 * 广告配置管理类
 *
 * @author michael
 * @time 17/1/9 上午10:32
 */
public interface AdConfigManager {

    /**
     * 获取某个广告位的配置
     *
     * @param adPositionId 广告位Id，传空值取所有的广告位的配置，
     *                     不为空则取该广告位的配置
     */
    List<AdConfigBean> getAdConfigBean(String adPositionId);

}
