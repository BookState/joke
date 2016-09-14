package com.tj.uimading.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.tj.uimading.R;
import com.tj.uimading.app.MyXutilsApp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/9/5.
 */
public abstract class BaseLoadingPager extends FrameLayout {
    private static final int LAYOUT_LOADING = 1;
    private static final int LAYOUT_SUCCESS = 2;
    private static final int LAYOUT_NODATA = 3;
    private static final int LAYOUT_NOWIFI = 4;

    private int currentState = 1;

    private View view_loading;
    private View view_success;
    private View view_nodata;
    private View view_nowifi;
    private String url;

    public BaseLoadingPager(Context context) {
        super(context);
        initializeView();
    }

    private void initializeView() {

        if (view_loading == null) {
            view_loading = View.inflate(getContext(), R.layout.item_loading, null);
            addView(view_loading);
        }

        if (view_nodata == null) {
            view_nodata = View.inflate(getContext(), R.layout.item_nodata, null);
            addView(view_nodata);
        }

        if (view_nowifi == null) {
            view_nowifi = View.inflate(getContext(), R.layout.item_nowifi, null);
            addView(view_nowifi);
        }

        showPager();
    }


    private void showPager() {
        MyXutilsApp.handler.post(new Runnable() {
            @Override
            public void run() {
                view_loading.setVisibility(currentState == LAYOUT_LOADING ? View.VISIBLE : View.INVISIBLE);
                view_nodata.setVisibility(currentState == LAYOUT_NODATA ? View.VISIBLE : View.INVISIBLE);
                view_nowifi.setVisibility(currentState == LAYOUT_NOWIFI ? View.VISIBLE : View.INVISIBLE);

                if (view_success == null) {
                    view_success = View.inflate(getContext(), getSuccessLayout(), null);
                    addView(view_success);

                    bindSuccessview(view_success);
                }

                view_success.setVisibility(currentState == LAYOUT_SUCCESS ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    public void netWorking() {
        url = getUrl();
        if (url == null) {
            currentState = LAYOUT_SUCCESS;
            showPager();
        } else {
            x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (TextUtils.isEmpty(result)) {
                        currentState = LAYOUT_NODATA;
                    } else {
                        currentState = LAYOUT_SUCCESS;
                        parseData(result);
                    }
                    showPager();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    currentState = LAYOUT_NOWIFI;
                    showPager();

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }

    }

    protected void setUrl(String url) {
        this.url = url;
    }

    protected abstract void parseData(String result);

    protected abstract String getUrl();

    protected abstract int getSuccessLayout();

    protected abstract void bindSuccessview(View view_success);
}
