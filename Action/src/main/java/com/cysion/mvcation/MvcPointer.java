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
    private static PROXY mPROXY;

    public enum PROXY {
        VOLLEY, RETROFIT
    }

    /**
     * Init.
     * @param aContext the a context
     */
    public static void init(Context aContext, boolean aIsDebug, PROXY aPROXY) {
        mContext = aContext.getApplicationContext();
        MvcAction.initAction(mContext, aIsDebug);
        mPROXY = aPROXY;
        if (mPROXY==null||aContext==null) {
            try {
                throw new Exception("they should be set properly");
            } catch (Exception aE) {
                aE.printStackTrace();
            }
        }
    }
    /**
     * 获得网络相关的代理者
     * @return 网络加载代理对象
     */
    public static HttpProxy getHttpProxy() {
        if (mPROXY == PROXY.VOLLEY) {
            return VolleyProxy.getInstance(mContext);
        }else if(mPROXY == PROXY.RETROFIT){
            return RetrofitProxy.getInstance(mContext);
        }
        return null;
    }
}
