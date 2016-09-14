package com.tj.uimading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.tj.uimading.entity.NewsEasyData;
import com.tj.uimading.utils.IgnoreUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, NewsActivity.class);

 /*       Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        }, 4000);*/

        getNewsEasyDataList();

    }

    private void getNewsEasyDataList() {

        String uri = "http://c.m.163.com/nc/topicset/android/subscribe/manage/listspecial.html";
        RequestParams entity = new RequestParams(uri);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                NewsEasyData newsEasyData = gson.fromJson(result, NewsEasyData.class);
                ignore(newsEasyData);
                intent.putExtra("data", newsEasyData.gettList());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                startActivity(intent);
                finish();
            }
        });


        //        private void ignore(NewsEasyData newsEasyData) {
        //            List<newsEasyData.TList> tobeDeleted = new ArrayList<>();
        //            for (int i = 0; i < IgnoreTypes.TYPES.length; i++) {
        //                for (int j = 0; j <netEaseType.gettList().size(); j++) {
        //                    if (IgnoreTypes.TYPES[i].equals(netEaseType.gettList().get(j).getTname())) {
        //                        tobeDeleted.add(netEaseType.gettList().get(j));
        //                    }
        //                }
        //
        //            }
        //            netEaseType.gettList().removeAll(tobeDeleted);
        //        }
    }

    private void ignore(NewsEasyData newsEasyData) {
        List<NewsEasyData.subData> tobeDeleted = new ArrayList<>();
        for (int i = 0; i < IgnoreUtil.TYPE.length; i++) {
            for (int j = 0; j < newsEasyData.gettList().size(); j++) {
                if (IgnoreUtil.TYPE[i].equals(newsEasyData.gettList().get(j).getTname())) {
                    tobeDeleted.add(newsEasyData.gettList().get(j));
                }
            }

        }
        newsEasyData.gettList().removeAll(tobeDeleted);
    }
}

