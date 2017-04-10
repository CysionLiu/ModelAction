package com.cysion.mvcation;

import android.content.Context;

import com.cysion.mvcation.base.Constant;
import com.cysion.mvcation.base.HttpProxy;
import com.cysion.mvcation.base.PreCall;
import com.cysion.mvcation.base.THttpListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.cysion.mvcation.MvcUtils.isGoodJson;

/**
 * Created by CysionLiu on 2017/4/7.
 */

public class RetrofitProxy implements HttpProxy {

    private Retrofit mRetrofit;

    private static volatile RetrofitProxy instance;
    private static Context mContext;
    private static OkHttpClient mClient;
    private static Map<String, Call<String>> mCallQueue;

    private RetrofitProxy() {
        mClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder().baseUrl("http://occupy").client(mClient).addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    public static synchronized RetrofitProxy getInstance(Context aContext) {
        if (instance == null) {
            mContext = aContext;
            mCallQueue = new HashMap<>();
            instance = new RetrofitProxy();
        }
        return instance;
    }

    @Override
    public void getData(String url, final THttpListener callBack, Map<String, String> params, Map<String, String> headers, final long taskId) {
        Call<String> call = mRetrofit.create(PreCall.class).getResult(url, headers);
        mCallQueue.put(taskId + "", call);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                mCallQueue.remove(taskId + "");
                if (!isGoodJson(response.body())) {
                    callBack.onFailure(Constant.WRONG_SERVER, taskId);
                    return;
                }

                callBack.onSuccess(response.body(), taskId);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBack.onFailure(t.getMessage(), taskId);
                mCallQueue.remove(taskId + "");
            }
        });
    }

    @Override
    public void postData(String url, final THttpListener callBack, Map<String, String> params, Map<String, String> headers, final long taskId) {
        Call<String> call = mRetrofit.create(PreCall.class).postResult(url, headers, params);
        mCallQueue.put(taskId + "", call);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                mCallQueue.remove(taskId + "");
                if (!isGoodJson(response.body())) {
                    callBack.onFailure(Constant.WRONG_SERVER, taskId);
                    return;
                }
                callBack.onSuccess(response.body(), taskId);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mCallQueue.remove(taskId + "");
                callBack.onFailure(t.getMessage(), taskId);
            }
        });

    }


    private void cancel(String tag) {
        Call<String> call = mCallQueue.get(tag);
        mCallQueue.remove(tag);
        call.cancel();
    }
    @Override
    public void cancelAll(String[] tags) {
        for (int i = 0; i < tags.length; i++) {
            cancel(tags[i]);
        }
    }
}
