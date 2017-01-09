package co.allconnected.ad;

import co.allconnected.ad.bean.AdConfigBean;

/**
 * 广告配置管理类
 * @author michael
 * @time 17/1/9 上午10:32
 */
public interface AdConfigManager{

    /**
     * 获取某个广告位的配置
     * @param adPositionId  广告位id
     */
    AdConfigBean getAdConfigBean(String adPositionId);


    /**
     * 保存所有广告位的配置
     */
    void saveAdConfigBeanes();


    /**
     * 获取在线参数
     * @param adPositionId 为空的话则表示获取所有的广告位的配置，
     *                     不为空则表示获取某个具体的广告为的配置
     */
    String getAdOnlineConfig(String adPositionId);




}
