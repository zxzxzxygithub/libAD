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

    private static FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static long mCacheExpiration;

    /**
     * 初始化
     *
     * @param defaultConfigFileId
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
