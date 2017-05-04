package com.cysion.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cysion.mvcation.MvcPointer;
import com.cysion.mvcation.base.DataState;
import com.cysion.mvcation.base.TActionListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements TActionListener {

    private BaseAction mGetAction;
    private BaseAction mPostAction;
    private BaseAction mKeepAction;
    private TextView mTextShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextShow = (TextView) findViewById(R.id.text_show);
        MvcPointer.init(this, true, VolleyProxy.getInstance(this));
        mGetAction = new GetAction(this);
        mPostAction = new PostAction(this);
        mKeepAction = new KeepTimeAction(this);
    }

    public void getWithPath(View view) {
        mTextShow.setText("loading...");
        mGetAction.taskId(GetAction.ZHIHU_DATE).path("20170503").execute(DataState.NO_CACHE);
    }

    public void noCache(View view) {
        mTextShow.setText("loading...");
        mGetAction.taskId(GetAction.ZHIHU_NEW).execute(DataState.NO_CACHE);
    }

    public void cacheFirstPost(View view) {
        mTextShow.setText("loading...");
        Map<String, String> param = new HashMap<>();
        param.put("lastId", "23411");
        mPostAction.taskId(PostAction.POST01).params(param).execute(DataState.CACHE_FIRST);
    }

    public void netFirst(View view) {
        mTextShow.setText("loading...");
        Map<String, String> param = new HashMap<>();
        param.put("lastId", "23411");
        param.put("tagId", "23");
        mPostAction.taskId(PostAction.POST02).params(param).execute(DataState.NET_FIRST);
    }

    public void keepTime(View view) {
        mTextShow.setText("loading...");
        mKeepAction.taskId(KeepTimeAction.GET_TIME).path("sd").execute(DataState.CACHE_FIRST);
    }

    public void getWithParams(View view) {
        mTextShow.setText("loading...");
        Map<String, String> params = new HashMap<>();
        params.put("key", "12831296b9670");
        params.put("city", "朝阳");
        params.put("province", "北京");
        mGetAction.taskId(GetAction.WEATHER_ID).params(params).execute(DataState.NET_FIRST);
    }

    @Override
    public void onSuccess(Object obj, int taskId) {
        String result = (String) obj;
        switch (taskId) {
            case GetAction.ZHIHU_NEW:
                mTextShow.setText("noCache---" + result);
                break;
            case GetAction.ZHIHU_DATE:
                mTextShow.setText("path---" + result);
                break;
            case GetAction.WEATHER_ID:
                mTextShow.setText("params---" + result);
                break;
            case PostAction.POST01:
                mTextShow.setText("cache first---" + result);
                break;
            case PostAction.POST02:
                mTextShow.setText("net first---" + result);
                break;
            case KeepTimeAction.GET_TIME:
                mTextShow.setText("keep_time---" + result);
                break;
        }
    }

    @Override
    public void onFailure(Object obj, int taskId) {
        mTextShow.setText("failure");
    }

}
