package com.cysion.mvcation;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cysion.mvcation.base.Constant;
import com.cysion.mvcation.base.HttpProxy;
import com.cysion.mvcation.base.THttpListener;

import java.util.Map;

import static com.cysion.mvcation.MvcUtils.isGoodJson;

/**
 * Created by CysionLiu on 2017/4/7..
 * 频繁网络交互的代理者，目前基于volley
 * 要结合结合{@link THttpListener}使用
 * get和post方法，还包括加入header的情况
 */
public class VolleyProxy implements HttpProxy {

    private static RequestQueue queue;
    private static VolleyProxy instance = new VolleyProxy();

    private VolleyProxy() {

    }

    public static synchronized VolleyProxy getInstance(Context aContext) {
        if (queue == null) {
            queue = Volley.newRequestQueue(aContext);
        }
        return instance;
    }

    //单例模式创建请求队列，context应传进程的context
    public static synchronized RequestQueue getQueue(Context context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    //取消特定tag的请求
    public void cancel(Object tag) {
        queue.cancelAll(tag);
    }

    public void cancelAll(Object[] tags) {
        for (int i = 0; i < tags.length; i++) {
            cancel(tags[i]);
        }
    }

    @Override
    public void getData(String url, final THttpListener callBack, final Map<String, String> params, final Map<String, String> headers, final long taskId) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                if (!isGoodJson(response)) {
                    callBack.onFailure(Constant.WRONG_SERVER, taskId);
                    return;
                }
                callBack.onSuccess(response, taskId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error, taskId);
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        stringRequest.setTag(taskId);
        queue.add(stringRequest);
    }

    @Override
    public void postData(String url, final THttpListener callBack, final Map<String, String> paraMap, final Map<String, String> headers, final long taskId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response
                .Listener<String>() {
            @Override
            public void onResponse(final String response) {
                if (!isGoodJson(response)) {
                    callBack.onFailure(Constant.WRONG_SERVER, taskId);
                    return;
                }
                callBack.onSuccess(response, taskId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error, taskId);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paraMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        stringRequest.setTag(taskId);
        queue.add(stringRequest);
    }
}
