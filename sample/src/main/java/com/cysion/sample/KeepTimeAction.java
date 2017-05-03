package com.cysion.sample;

import com.cysion.mvcation.base.TActionListener;

/**
 * Created by xianshang.liu on 2017/5/3.
 */

public class KeepTimeAction extends BaseAction {
    public static final int GET_TIME = 301;
    public KeepTimeAction(TActionListener aListener) {
        super(aListener);
    }

    @Override
    protected String getUrl(int aTaskId) {
        return Urls.GET_TIME;
    }

    @Override
    protected Method getHttpMethod(int aTaskId) {
        return Method.Method_GET;
    }

    @Override
    protected boolean getTargetDataFromJson(String aResult, int aTaskId) {
        if (aResult.length() > 100) {
            listener.onSuccess(aResult.substring(0, 99), aTaskId);
            return true;
        } else if (aResult.length() > 30) {
            listener.onSuccess(aResult.substring(0, 29), aTaskId);
            return true;
        }
        return false;
    }

    @Override
    protected int getKeepTime() {
        return 30;
    }
}
