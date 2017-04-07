package com.cysion.mvcation.base;

/**
 * Created by CysionLiu on 2017/4/7.
 */
public interface TActionListener {

    /**
     * 成功获得结果返回，注意，此结果代表本次请求最希望的数据，即目标数据
     * @param obj
     * @param taskId
     */
    void onSuccess(Object obj, long taskId);

    /**
     * 结果获得失败。可以为缓存带有的结果码
     * @param obj
     * @param taskId
     */
    void onFailure(Object obj, long taskId);
}
