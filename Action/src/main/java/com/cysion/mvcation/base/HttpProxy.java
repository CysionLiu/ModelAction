package com.cysion.mvcation.base;

import java.util.Map;

/**
 * Created by CysionLiu on 2017/4/7.
 */

public interface HttpProxy {

    void getData(String url, final THttpListener callBack, final Map<String, String> paraMap,final Map<String,
            String> headers, final long taskId);

    void postData(String url, final THttpListener callBack,
                  final Map<String, String> paraMap, final Map<String,
            String> headers, final long taskId);

    void cancelAll(String[] tag);
}
