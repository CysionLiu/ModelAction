package com.cysion.sample;

import com.cysion.mvcation.base.TActionListener;

import static com.cysion.mvcation.base.MvcAction.Method.Method_POST;

/**
 * Created by xianshang.liu on 2017/4/7.
 */

public class PostAction extends BaseAction {
    public PostAction(TActionListener aListener) {
        super(aListener);
    }

    @Override
    protected String getUrl(int aTaskId) {
        return Urls.SELF_BASE+Urls.SELF_ARTICLE;
    }

    @Override
    protected Method getHttpMethod(int aTaskId) {
        return Method_POST;
    }

    @Override
    protected boolean getTargetDataFromJson(String aResult, int aTaskId) {
        listener.onSuccess(aResult,aTaskId);
        return true;
    }

    @Override
    protected int getKeepTime() {
        return 10;
    }
}
