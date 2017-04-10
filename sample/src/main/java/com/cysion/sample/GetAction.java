package com.cysion.sample;

import android.text.TextUtils;
import android.util.Log;

import com.cysion.mvcation.base.TActionListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xianshang.liu on 2017/4/7.
 */

public class GetAction extends BaseAction {
    public GetAction(TActionListener aListener) {
        super(aListener);
    }

    @Override
    protected String getUrl() {
        return Urls.BASE + Urls.FIND_COL;
    }

    @Override
    protected int getHttpMethod() {
        return Method_GET;
    }

    @Override
    protected boolean getTargetDataFromJson(String aResult, long aTaskId) {
        try {
            JSONObject jsonObject = new JSONObject(aResult);
            String temp = (String) jsonObject.opt("product");
            if (!TextUtils.isEmpty(temp)) {
                TestBean bean = new Gson().fromJson(aResult, TestBean.class);
                Log.e("flag--", "GetAction--getTargetDataFromJson--38--" + bean.getWebUrl());
                listener.onSuccess(aResult, aTaskId);
                return true;
            }
        } catch (JSONException aE) {
            aE.printStackTrace();
        }
        return false;
    }
}
