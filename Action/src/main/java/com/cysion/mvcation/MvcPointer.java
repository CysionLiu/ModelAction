package com.cysion.mvcation;

import android.content.Context;

import com.cysion.mvcation.base.HttpProxy;
import com.cysion.mvcation.base.MvcAction;

/**
 * Created by CysionLiu on 2017/4/7.
 * the entrance
 */
public class MvcPointer {
    private static Context mContext;
    private static HttpProxy mHttpProxy;

    /**
     * Init.
     *
     * @param aContext the a context
     */
    public static void init(Context aContext, boolean aIsDebug, HttpProxy aHttpProxy) {
        if (aContext == null) {
            try {
                throw new Exception("aContext should not be null");
            } catch (Exception aE) {
                aE.printStackTrace();
            } finally {
                return;
            }
        }
        mContext = aContext.getApplicationContext();
        MvcAction.initAction(mContext, aIsDebug);
        mHttpProxy = aHttpProxy;
        if (mHttpProxy == null) {
            mHttpProxy = VolleyProxy.getInstance(mContext);
        }
    }

    public static HttpProxy getHttpProxy() {
        return mHttpProxy;
    }

    public static void clearCache(Context aContext) {
        CacheProxy.get(aContext).clear();
    }

    public static void cancelTask(String[] tags) {
        getHttpProxy().cancelAll(tags);
    }
}
