package com.tj.uimading.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tj.uimading.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluateActivity extends AppCompatActivity {
    @BindView(R.id.wv_evaluate)
    WebView mWvEvaluate;
    /*
评论：
http://comment.3g.163.com/news_guonei8_bbs/C0GUQUDN0001544E.html
                      "boardid": "news2_bbs", "docid": "C0F63MGE00015C18",
"http://comment.3g.163.com/"+boardid+"/"+docid+".html"
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        String docid = getIntent().getStringExtra("docid");
        String boardid = getIntent().getStringExtra("boardid");
//        Toast.makeText(this,"docid:"+docid+"boardid:"+boardid,Toast.LENGTH_LONG).show();
        String url = "http://comment.3g.163.com/" + boardid +"/"+ docid + ".html";
//        String url="http://comment.3g.163.com/news_guonei8_bbs/C0GUQUDN0001544E.html";
        mWvEvaluate.loadUrl(url);

        mWvEvaluate.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //                return super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;
            }
        });
    }
}
