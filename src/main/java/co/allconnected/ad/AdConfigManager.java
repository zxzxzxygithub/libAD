package co.allconnected.ad;

import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.allconnected.ad.bean.AdConfigBean;

/**
 * 广告配置实现类
 *
 * @author michael
 * @time 17/1/10 下午2:55
 */
public class AdConfigManager {

    /**
     * 广告平台-facebook
     */
    public static final int PLATFORM_ID_FACEBOOK = 1;
    /**
     * 广告平台-百度
     */
    public static final int PLATFORM_ID_DU = 2;
    /**
     * 广告平台-admob
     */
    public static final int PLATFORM_ID_ADMOB = 3;
    /**
     * 广告平台-admob聚合
     */
    public static final int PLATFORM_ID_ADMOB_INTEGRATION = 4;
    /**
     * 广告平台-MOBVISTA
     */
    public static final int PLATFORM_ID_MOBVISTA = 5;

    /**
     * 广告平台-facebook
     */
    public static final String PLATFORM_FACEBOOK = "facebook";
    /**
     * 广告平台-百度
     */
    public static final String PLATFORM_DU = "du";
    /**
     * 广告平台-admob
     */
    public static final String PLATFORM_ADMOB = "admob";
    /**
     * 广告平台-admob聚合
     */
    public static final String PLATFORM_ADMOB_INTEGRATION = "admob_integration";
    /**
     * 广告平台-MOBVISTA
     */
    public static final String PLATFORM_MOBVISTA = "mobvista";

    /**
     * 广告类型-全屏
     */
    public static final int TYPE_ID_FULLSCREEN = 1;
    /**
     * 广告类型-原生
     */
    public static final int TYPE_ID_NATIVE = 2;
    /**
     * 广告类型-appwall
     */
    public static final int TYPE_ID_APPWALL = 3;

    /**
     * 广告类型-全屏
     */
    public static final String TYPE_FULLSCREEN = "fullscreen";
    /**
     * 广告类型-原生
     */
    public static final String TYPE_NATIVE = "native";
    /**
     * 广告类型-appwall
     */
    public static final String TYPE_APPWALL = "appwall";


    private static FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static long mCacheExpiration;

    /**
     * 初始化
     *
     * @param defaultConfigFileId 默认配置的文件id，在xml文件夹下
     * @param cacheExpiration     缓存失效时间，单位秒
     * @author michael
     * @time 17/1/10 下午3:38
     */
    public static void init(int defaultConfigFileId, long cacheExpiration) {
        // set cacheExpiration
        mCacheExpiration = cacheExpiration;

        // Get Remote Config instance.
        // [START get_remote_config_instance]
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // [END get_remote_config_instance]

        // Set default Remote Config values. In general you should have in app defaults for all
        // values that you may configure using Remote Config later on. The idea is that you
        // use the in app defaults and when you need to adjust those defaults, you set an updated
        // value in the App Manager console. Then the next time you application fetches from the
        // server, the updated value will be used. You can set defaults via an xml file like done
        // here or you can set defaults inline by using one of the other setDefaults methods.S
        // [START set_default_values]
        mFirebaseRemoteConfig.setDefaults(defaultConfigFileId);
        // [END set_default_values]
    }

    /**
     * 拉取广告位的配置
     *
     * @param listener 拉取成功失败的回调
     */
    public static void fetchAdConfig(OnCompleteListener<Void> listener) {

        mFirebaseRemoteConfig.fetch(mCacheExpiration)
                .addOnCompleteListener(listener);

    }

    /**
     * 在拉取成功的回调里面调用该方法获取某个广告位的配置
     * 适用于不使用默认配置
     *
     * @author michael
     * @time 17/1/10 下午4:04
     */
    public static String getAdConfigStr(String adPositionId) {
        mFirebaseRemoteConfig.activateFetched();
        String configStr = mFirebaseRemoteConfig.getString(adPositionId);
        return configStr;
    }

    /**
     * 在拉取成功的回调里面调用该方法获取某个广告位的配置实体类
     *
     * @author michael
     * @time 17/1/10 下午4:04
     */
    public static AdConfigBean getAdConfigBean(String adPositionId) {
        mFirebaseRemoteConfig.activateFetched();
        String configStr = mFirebaseRemoteConfig.getString(adPositionId);
        if (!TextUtils.isEmpty(configStr)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(configStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonObject != null) {
                AdConfigBean adConfigBean = new AdConfigBean();
                try {
                    adConfigBean.setEnable(jsonObject.getBoolean("enable"));
                    JSONArray adJsonArray = jsonObject.getJSONArray("ad");
                    if (adJsonArray != null && adJsonArray.length() > 0) {
                        List<AdConfigBean.AdBean> adBeanList = new ArrayList<>();
                        AdConfigBean.AdBean adBean = null;
                        for (int i = 0; i < adJsonArray.length(); i++) {
                            JSONObject adJsonobject = adJsonArray.getJSONObject(i);
                            if (adJsonobject != null) {
                                adBean = new AdConfigBean.AdBean();
                                adBean.setFresh_id(adJsonobject.optString("fresh_id"));
                                adBean.setId(adJsonobject.optString("id"));
                                adBean.setPlatformId(adJsonobject.optInt("platformId"));
                                adBean.setType(adJsonobject.optInt("type"));
                                adBeanList.add(adBean);
                            }

                        }
                        adConfigBean.setAd(adBeanList);
                        return adConfigBean;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
