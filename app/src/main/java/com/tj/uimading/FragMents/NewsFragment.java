package com.tj.uimading.FragMents;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tj.uimading.R;
import com.tj.uimading.adapter.EasyDataAdapter;
import com.tj.uimading.base.BaseFragment;
import com.tj.uimading.entity.NewsEasyData;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/31.
 */
public class NewsFragment extends BaseFragment {

    @BindView(R.id.img_left)
    ImageView mImgLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.indicator)
    TabPageIndicator mIndicator;
    @BindView(R.id.pager)
    ViewPager mPager;

    EasyDataAdapter adapter;

    @Override
    protected int getRealLayout() {
        return R.layout.layout_news;
    }

    @Override
    protected String getRealUrl() {
        return null;
    }

    @Override
    protected void parseRealData(String result) {
        mTvTitle.setText(result);
    }

    @Override
    protected void initData() {
        showSuccessPager();
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<NewsEasyData.subData> list = (ArrayList<NewsEasyData.subData>) bundle.getSerializable("data");
            adapter = new EasyDataAdapter(getFragmentManager(), list);
            mPager.setAdapter(adapter);
        mIndicator.setViewPager(mPager);
        mIndicator.setVisibility(View.VISIBLE);
    }

    }



    //    @Override
    //    protected void initData() {
    //        Bundle bundle = getArguments();
    //        if (bundle != null) {
    //            ArrayList<NewsEasyData.subData> list = (ArrayList<NewsEasyData.subData>) bundle.getSerializable("data");
    //            adapter = new EasyDataAdapter(getFragmentManager(), list);
    //            mPager.setAdapter(adapter);
    //            mIndicator.setViewPager(mPager);
    //            mIndicator.setVisibility(View.VISIBLE);
    //        }
    //    }
    //
    //
    public static NewsFragment getInstance(Bundle bundle) {
        NewsFragment news = new NewsFragment();
        if (bundle != null) {
            news.setArguments(bundle);
        }
        return news;
    }
    //
    //    @Override
    //    public int getLayoutId() {
    //        return R.layout.layout_news;
    //    }
    //
    //
    //    @Override
    //    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //        // TODO: inflate a fragment view
    //        View rootView = super.onCreateView(inflater, container, savedInstanceState);
    //        ButterKnife.bind(this, rootView);
    //        return rootView;
    //    }
}
