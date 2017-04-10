package com.cysion.mvcation.base;

/**
 * Created by CysionLiu on 2017/4/7.
 */
public interface TActionListener {

    /**
     * only the target core data gotten is successful
     * @param obj
     * @param taskId request id, to trace the procedure.
     */
    void onSuccess(Object obj, long taskId);

    /**
     * @param obj
     * @param taskId
     */
    void onFailure(Object obj, long taskId);
}
