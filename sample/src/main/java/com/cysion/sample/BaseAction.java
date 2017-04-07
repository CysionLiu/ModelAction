package com.cysion.sample;

import com.cysion.mvcation.base.MvcAction;
import com.cysion.mvcation.base.TActionListener;

import java.util.Map;

/**
 * Created by xianshang.liu on 2017/4/7.
 */

public abstract class BaseAction extends MvcAction {


    public BaseAction(TActionListener aListener) {
        super(aListener);
    }

    @Override
    protected Map<String, String> getHeader() {
        return super.getHeader();
    }
}
