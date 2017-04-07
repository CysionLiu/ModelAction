package com.cysion.sample;

import com.cysion.mvcation.base.TActionListener;

/**
 * Created by xianshang.liu on 2017/4/7.
 */

public class GetAction extends BaseAction {
    public GetAction(TActionListener aListener) {
        super(aListener);
    }

    @Override
    protected String getUrl() {
        return Urls.BASE+Urls.FIND_COL;
    }

    @Override
    protected int getHttpMethod() {
        return Method_GET;
    }

    @Override
    protected boolean getTargetDataFromJson(String aResult, long aTaskId) {
        listener.onSuccess(aResult,aTaskId);
        return true;
    }
}
