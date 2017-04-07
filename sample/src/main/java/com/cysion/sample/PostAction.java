package com.cysion.sample;

import com.cysion.mvcation.base.TActionListener;

/**
 * Created by xianshang.liu on 2017/4/7.
 */

public class PostAction extends BaseAction {
    public PostAction(TActionListener aListener) {
        super(aListener);
    }

    @Override
    protected String getUrl() {
        return Urls.SELF_BASE+Urls.SELF_ARTICLE;
    }

    @Override
    protected int getHttpMethod() {
        return Method_POST;
    }

    @Override
    protected boolean getTargetDataFromJson(String aResult, long aTaskId) {
        listener.onSuccess(aResult,aTaskId);
        return true;
    }
}
