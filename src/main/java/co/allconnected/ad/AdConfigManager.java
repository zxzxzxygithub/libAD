package co.allconnected.ad;

/**
 * 广告配置管理类
 * @author michael
 * @time 17/1/9 上午10:32
 */
public interface AdConfigManager<T> {

    /**
     * 获取某个广告位的配置
     */
    T getAdConfigBean(T t);

    /**
     * 保存所有广告位的配置
     */
    void saveAdConfigBeanes();

    /**
     * 获取在线参数
     */
    void getAdOnlineConfig();

}
