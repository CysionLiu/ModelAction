package com.cysion.mvcation.base;

/**
 * Created by CysionLiu on 2017/4/7.
 * to get light and fluent data from net request
 */
public interface THttpListener {

    /**
     * only the target core data gotten is successful
     * @param obj
     * @param taskId
     */
    void onSuccess(Object obj, int taskId);

    /**
     * @param obj
     * @param taskId
     */
    void onFailure(Object obj, int taskId);
}
