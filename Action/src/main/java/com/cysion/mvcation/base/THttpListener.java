package com.cysion.mvcation.base;

/**
 * Created by CysionLiu on 2017/4/7.
 * to get light and fluent data from net request
 */
public interface THttpListener {

    /**
     * 成功获得结果返回
     * @param obj
     * @param taskId
     */
    void onSuccess(Object obj, long taskId);

    /**
     * 结果获得失败。目前返回的obj是VolleyError
     * @param obj
     * @param taskId
     */
    void onFailure(Object obj, long taskId);
}
