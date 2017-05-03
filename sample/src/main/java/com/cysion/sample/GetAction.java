package com.cysion.sample;

import com.cysion.mvcation.base.TActionListener;

import static com.cysion.mvcation.base.MvcAction.Method.Method_GET;

/**
 * Created by xianshang.liu on 2017/4/7.
 */

public class GetAction extends BaseAction {
    public static final int ZHIHU_NEW = 100;
    public static final int ZHIHU_DATE = 101;
    public static final int WEATHER_ID = 102;


    public GetAction(TActionListener aListener) {
        super(aListener);
    }

    @Override
    protected String getUrl(int aTaskId) {
        switch (aTaskId) {
            case ZHIHU_NEW:
                return Urls.ZHIHU_LATEST;
            case ZHIHU_DATE:
                return Urls.ZHIHU_DATE;
            case WEATHER_ID:
                return Urls.WEATHER_ID;
        }
        return null;
    }

    @Override
    protected Method getHttpMethod(int aTaskId) {
        return Method_GET;
    }

    @Override
    protected boolean getTargetDataFromJson(String aResult, int aTaskId) {
        switch (aTaskId) {
            case ZHIHU_NEW:
                return handleZhihuNEW(aResult, aTaskId);
            case ZHIHU_DATE:
                return handleZhihuDate(aResult, aTaskId);
            case WEATHER_ID:
                return handleZhihuID(aResult, aTaskId);
        }
        return false;
    }

    private boolean handleZhihuID(String aResult, int aTaskId) {
        if (aResult.length() > 100) {
            listener.onSuccess(aResult.substring(0, 99), aTaskId);
            return true;
        }
        return false;
    }

    private boolean handleZhihuDate(String aResult, int aTaskId) {
        if (aResult.length() > 100) {
            listener.onSuccess(aResult.substring(0, 99), aTaskId);
            return true;
        }
        return false;
    }

    private boolean handleZhihuNEW(String aResult, int aTaskId) {
        if (aResult.length() > 100) {
            listener.onSuccess(aResult.substring(0, 99), aTaskId);
            return true;
        }
        return false;
    }
}
