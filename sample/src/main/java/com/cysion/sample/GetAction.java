package com.cysion.sample;

import com.cysion.mvcation.base.TActionListener;

import static com.cysion.mvcation.base.MvcAction.Method.Method_GET;

/**
 * Created by xianshang.liu on 2017/4/7.
 */

public class GetAction extends BaseAction {
    public static final int ZHIHU_NEW = 315;
    public static final int ZHIHU_DATE = 991;
    public static final int ZHIHU_ID = 175;


    public GetAction(TActionListener aListener) {
        super(aListener);
    }

    @Override
    protected String getUrl(int aTaskId) {
        switch (aTaskId) {
            case ZHIHU_NEW:
                return Urls.BASE_ZHIHU_URL + Urls.ZHIHU_LATEST;
            case ZHIHU_DATE:
                return Urls.BASE_ZHIHU_URL + Urls.ZHIHU_DATE;
            case ZHIHU_ID:
                return Urls.BASE_ZHIHU_URL + Urls.ZHIHU_ID;
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
                handleZhihuNEW(aResult,aTaskId);
                break;
            case ZHIHU_DATE:
                handleZhihuDate(aResult,aTaskId);
                break;
            case ZHIHU_ID:
                handleZhihuID(aResult,aTaskId);
                break;
        }
        return false;
    }

    private void handleZhihuID(String aResult, int aTaskId) {

    }

    private void handleZhihuDate(String aResult, int aTaskId) {
    }

    private void handleZhihuNEW(String aResult, int aTaskId) {

    }
}
