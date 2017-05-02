package com.cysion.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cysion.mvcation.MvcPointer;
import com.cysion.mvcation.RetrofitProxy;
import com.cysion.mvcation.base.TActionListener;

public class MainActivity extends AppCompatActivity implements TActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MvcPointer.init(this, true, RetrofitProxy.getInstance(this));
    }

    public void getWithPath(View view) {
        Log.e("flag--", "getWithPath(MainActivity.java:23)-->>");
    }

    public void noCache(View view) {
        Log.e("flag--", "noCache(MainActivity.java:27)-->>");
    }

    public void cacheFirstPost(View view) {
        Log.e("flag--", "cacheFirstPost(MainActivity.java:31)-->>");
    }

    public void netFirst(View view) {
        Log.e("flag--", "netFirst(MainActivity.java:35)-->>");
    }

    public void keepTime(View view) {
        Log.e("flag--", "keepTime(MainActivity.java:39)-->>");
    }

    public void getWithParams(View view) {
        Log.e("flag--", "getWithParams(MainActivity.java:43)-->>");
    }

    @Override
    public void onSuccess(Object obj, int taskId) {

    }

    @Override
    public void onFailure(Object obj, int taskId) {
    }

}
