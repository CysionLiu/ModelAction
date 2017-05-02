package com.cysion.mvcation.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.cysion.mvcation.CacheProxy;
import com.cysion.mvcation.MvcUtils;
import com.cysion.mvcation.MvcPointer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.cysion.mvcation.base.MvcAction.Method.Method_GET;
import static com.cysion.mvcation.base.MvcAction.Method.Method_POST;

/**
 * Created by CysionLiu on 2017/4/7.
 */
public abstract class MvcAction {
    private static final String TAG = "MvcAction";
    protected static Context mContext;
    private static boolean DEBUG = false;
    private String url;
    protected DataState mActionCode;
    protected Method mReqMethod;
    private int taskId = -1;
    protected Map<String, String> params;
    protected TActionListener listener;
    private String key;
    protected static Map<String, String> mHeader;
    private String mPath;

    public enum Method {Method_GET, Method_POST}

    public static void initAction(Context aContext, boolean isDebug) {
        mContext = aContext.getApplicationContext();
        if (isDebug) {
            setDebug();
        }
    }

    public MvcAction(TActionListener aListener) {
        if (mContext != null) {
            listener = aListener;
            mActionCode = DataState.NO_CACHE;
        } else {
            try {
                throw new Exception("should invoke initAction firstly");
            } catch (Exception aE) {
                aE.printStackTrace();
            }
        }
    }

    public MvcAction params(Map<String, String> aParams) {
        params = aParams;
        return this;
    }

    public MvcAction taskId(int id) {
        taskId = id;
        return this;
    }

    /*suffix path of url */
    public MvcAction path(String path) {
        mPath = path;
        return this;
    }

    public void execute(DataState actionCode) {

        mActionCode = actionCode;
        url = getUrl(taskId);
        mReqMethod = getHttpMethod(taskId);
        if (!TextUtils.isEmpty(mPath)) {
            url += mPath;
            mPath = null;
        }
        checkUrlAndId();
        checkActionCode();
        getKey();
        if (!MvcUtils.isNetAvailable(mContext)) {
            whenNoNet();
            return;
        }
        if (mActionCode == DataState.CACHE_FIRST) {
            if (!isCacheValid(taskId)) {
                byHttp();
            }
        } else {
            byHttp();

        }
    }

    protected int getKeepTime() {
        return Integer.MAX_VALUE;
    }

    protected Map<String, String> getHeader() {
        if (mHeader == null) {
            mHeader = new HashMap<>();
        }
        return mHeader;
    }

    //override by children when params are fix ;
    protected Map<String, String> fixedParams() {
        return new HashMap<>();
    }

    protected void whenNoNet() {
        logv("no-network");
        listener.onFailure(Constant.NO_NET, taskId);
        switch (mActionCode) {
            case CACHE_FIRST:
            case NET_FIRST:
                isCacheValid(taskId);
                break;
            default:
                break;
        }
    }

    protected HttpProxy getHttpProxy() {
        return MvcPointer.getHttpProxy();
    }

    protected void byHttp() {
        if (mReqMethod == Method_GET) {
            if (params.size() > 0) {
                url = getAppearUrl(params);
            }
            getHttpProxy().getData(url, callBack, getParams(), getHeader(), taskId);
        } else if (mReqMethod == Method_POST) {
            Log.e("flag--", "MvcAction--byHttp--209--" + url);
            getHttpProxy().postData(url, callBack, getParams(), getHeader(), taskId);
        }
        Map<String, String> tempHeader = getHeader();
        for (Map.Entry entry : tempHeader.entrySet()) {
            Object key = entry.getKey();
            logv("header--" + key + ":" + entry.getValue());
        }
        taskId = -1;
        url = getUrl(taskId);
        params = null;
        logv("clear the field of action");
    }

    protected THttpListener callBack = new THttpListener() {
        @Override
        public void onSuccess(Object obj, int aTaskId) {
            String result = (String) obj;
            if (handleDataFromNet(result, aTaskId)) {
                writeToCache(result);
            } else {
                if (mActionCode == DataState.NET_FIRST && isCacheValid(aTaskId)) {
                } else {
                    listener.onFailure(Constant.NO_TARGET_DATA, aTaskId);
                }
            }
        }

        @Override
        public void onFailure(Object obj, int taskId) {
            if (Constant.WRONG_SERVER.equals(obj)) {
                listener.onFailure(Constant.WRONG_SERVER, taskId);
            } else {
                listener.onFailure(Constant.UNKNOWN_ERROR, taskId);
            }
        }
    };

    protected abstract String getUrl(int aTaskId);

    protected abstract Method getHttpMethod(int aTaskId);

    protected abstract boolean getTargetDataFromJson(String aResult, int aTaskId);

    protected boolean handleDataFromNet(String aResult, int aTaskId) {
        logv("from network");
        return getTargetDataFromJson(aResult, aTaskId);
    }

    protected boolean handleDataFromCache(String aResult, int aTaskId) {
        logv("from cache");
        return getTargetDataFromJson(aResult, aTaskId);
    }

    protected void loadMoreCache(String aShouldCache) {
    }

    protected void addHeadCache(String aShouldCache) {
        CacheProxy cacheObj = CacheProxy.get(mContext);
        cacheObj.remove(key);
        cacheObj.put(key, aShouldCache, getKeepTime());
    }

    private void getKey() {
        params = getParams();
        String buffer = getAppearUrl(params);
        logv("method--" + getHttpMethod(taskId) + "--url---" + buffer);
        try {
            key = MvcUtils.MD5encrypt(buffer.toString(), "utf-8");
        } catch (Exception aE) {
            aE.printStackTrace();
        }
    }

    private void checkUrlAndId() {
        if (TextUtils.isEmpty(url) || taskId == -1) {
            try {
                throw new Exception("should give url a value or give taskId a value");
            } catch (Exception aE) {
                aE.printStackTrace();
            }
        }
    }

    private void checkActionCode() {
        DataState[] values = DataState.values();
        if (!Arrays.asList(values).contains(mActionCode)) {
            try {
                throw new Exception("invalid actionCode");
            } catch (Exception aE) {
                aE.printStackTrace();
            }
        }
    }

    private Map<String, String> getParams() {
        if (params != null) {
            return params;
        }
        Map<String, String> temp = fixedParams();
        return temp;
    }

    private String getAppearUrl(Map<String, String> aParams) {
        String temp = url + "?";
        Set<String> strings = aParams.keySet();
        for (String aKey : strings) {
            temp = temp + aKey + "=" + aParams.get(aKey) + "&";
        }
        temp = temp.substring(0, temp.length() - 1);
        return temp;
    }

    private void logv(String lable) {
        if (DEBUG) {
            Log.d(TAG, lable);
        }
    }

    private String readCache(long aTaskId) {
        if (mActionCode == DataState.NO_CACHE) {
            return null;
        }
        CacheProxy cacheObj = CacheProxy.get(mContext);
        String fromCache = cacheObj.getAsString(key);
        return fromCache;

    }

    private static void setDebug() {
        DEBUG = true;
    }

    private void writeToCache(String shouldCache) {
        if (mActionCode == DataState.NO_CACHE) {
            return;
        }
        if (TextUtils.isEmpty(shouldCache)) {
            return;
        }
        CacheProxy cacheObj = CacheProxy.get(mContext);
        switch (mActionCode) {
            case CACHE_FIRST:
            case NET_FIRST:
                cacheObj.remove(key);
                cacheObj.put(key, shouldCache, getKeepTime());
                break;
            case HEAD_REFRESH:
                addHeadCache(shouldCache);
                break;
            case LOAD_MORE:
                loadMoreCache(shouldCache);
                break;
        }
    }

    private boolean isCacheValid(int aTaskId) {
        String fromCache = readCache(aTaskId);
        if (!TextUtils.isEmpty(fromCache)) {
            return handleDataFromCache(fromCache, aTaskId);
        }
        return false;
    }
}
