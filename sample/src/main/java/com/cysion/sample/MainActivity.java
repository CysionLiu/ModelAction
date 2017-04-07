package com.cysion.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cysion.mvcation.MvcPointer;
import com.cysion.mvcation.base.DataState;
import com.cysion.mvcation.base.TActionListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements TActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MvcPointer.init(this, true, MvcPointer.PROXY.RETROFIT);
    }


    public void showGet(View view) {
        new GetAction(this).taskId(100).execute(DataState.NO_CACHE);
    }

    public void showPost(View view) {
        Map<String, String> params = new HashMap<>();
        params.put("istest", "0");
        params.put("articleType", "1003");
        params.put("date", "0330");
        params.put("page", "1");
        new PostAction(this).taskId(101).params(params).execute(DataState.CACHE_FIRST);

    }

    @Override
    public void onSuccess(Object obj, long taskId) {
        if (taskId == 100) {
            Log.e("flag--", "MainActivity--onSuccess--42--" + taskId);
        } else if (taskId==101) {
            Log.e("flag--", "MainActivity--onSuccess--44--" + taskId);
        }
        Toast.makeText(MainActivity.this, ((String)obj).substring(0,50), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure(Object obj, long taskId) {
        Log.e("flag--", "MainActivity--onFailure--94--" + taskId);
    }
}
