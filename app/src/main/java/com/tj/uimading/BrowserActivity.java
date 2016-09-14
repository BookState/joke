package com.tj.uimading;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tj.uimading.entity.NewsBodyData;
import com.tj.uimading.utils.CollectActivity;
import com.tj.uimading.utils.EvaluateActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BrowserActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.img_left)
    ImageView mImgLeft;
    @BindView(R.id.right_arrow)
    ImageView mRightArrow;


    private PopupWindow po;
    private String docid;
    private String boardid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);

        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        boardid = getIntent().getStringExtra("boardid");

        //"http://c.m.163.com/" + "nc/article/" + docid + "/full.html";
        docid = getIntent().getStringExtra("docid");

        //        Toast.makeText(BrowserActivity.this, "docid:" + docid+"boardid:"+boardid, Toast.LENGTH_SHORT).show();
        String url = "http://c.m.163.com/" + "nc/article/" + docid + "/full.html";
        //        String url = getIntent().getStringExtra("url");
        mWebview.loadUrl(url);

        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //                return super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset();
            }
        });


        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    String string = new JSONObject(result).getString(docid);
                    NewsBodyData newsBodyData = new Gson().fromJson(string, NewsBodyData.class);
                    String before = "<img src=\"";
                    String after = "\"/> </img>";

                    for (NewsBodyData.Img img : newsBodyData.img) {
                        newsBodyData.body = newsBodyData.body.replace(img.ref, before + img.src + after);
                    }
                    mWebview.loadDataWithBaseURL(null, newsBodyData.body, "text.html", "utf-8", null);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //    @OnClick(R.id.right_arrow)
    //    public void onClick() {
    //
    //
    //        View view = View.inflate(this, R.layout.clickafter, null);
    //        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
    //        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
    //
    //        View btn_f = view.findViewById(R.id.btn_f);
    //        View btn_p = view.findViewById(R.id.btn_p);
    //        View btn_s = view.findViewById(R.id.btn_s);
    //
    //        btn_f.setOnClickListener(OnClickListener);
    //        btn_p.setOnClickListener(OnClickListener);
    //        btn_s.setOnClickListener(OnClickListener);
    //
    //        if (po == null) {
    //            po = new PopupWindow(view, width, height, true);
    //
    //            po.setContentView(view);
    //            po.setTouchable(true);
    //            po.setBackgroundDrawable(new BitmapDrawable());
    //        }
    //        po.showAsDropDown(findViewById(R.id.right_arrow));
    //
    //    }

    @OnClick({R.id.img_left, R.id.right_arrow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.right_arrow:
                view = View.inflate(this, R.layout.clickafter, null);
                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;

                View btn_f = view.findViewById(R.id.btn_f);
                View btn_p = view.findViewById(R.id.btn_p);
                View btn_s = view.findViewById(R.id.btn_s);

                btn_f.setOnClickListener(OnClickListener);
                btn_p.setOnClickListener(OnClickListener);
                btn_s.setOnClickListener(OnClickListener);

                if (po == null) {
                    po = new PopupWindow(view, width, height, true);

                    po.setContentView(view);
                    po.setTouchable(true);
                    po.setBackgroundDrawable(new BitmapDrawable());
                }
                po.showAsDropDown(findViewById(R.id.right_arrow));
                break;
        }
    }

    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_f:
                    Intent intent1 = new Intent(Intent.ACTION_SEND);
                    intent1.setType("image/*");
                    intent1.putExtra(Intent.EXTRA_SUBJECT, "分享");
                    intent1.putExtra(Intent.EXTRA_TEXT, "我将美好的分享给你。");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent1, getTitle()));
                    //Toast.makeText(BrowserActivity.this, "分享", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_p:
                    Intent intent = new Intent(BrowserActivity.this, EvaluateActivity.class);
                    intent.putExtra("docid", docid);
                    intent.putExtra("boardid", boardid);
                    startActivity(intent);
                    //                  Toast.makeText(BrowserActivity.this, "评价", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_s:
                    intent1 = new Intent(BrowserActivity.this, CollectActivity.class);
                    startActivity(intent1);

                    //Toast.makeText(BrowserActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                    break;
            }
            po.dismiss();
        }
    };

    private void imgReset() {
        mWebview.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%';   " +
                "}" +
                "})()");
    }

}
