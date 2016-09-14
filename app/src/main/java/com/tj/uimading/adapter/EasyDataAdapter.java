package com.tj.uimading.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tj.uimading.FragMents.NewsListFragment;
import com.tj.uimading.entity.NewsEasyData;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class EasyDataAdapter extends FragmentPagerAdapter {

    private List<NewsEasyData.subData> titleList;

    public EasyDataAdapter(FragmentManager fm, List<NewsEasyData.subData> titleList) {
        super(fm);
        this.titleList = titleList;
    }

    public EasyDataAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList == null ? "NONE" : titleList.get(position).getTname();
    }


    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("tid", titleList.get(position).getTid());
        bundle.putString("tname", titleList.get(position).getTname());
        return NewsListFragment.getInstance(bundle);
    }


    @Override
    public int getCount() {
        return titleList.size();
    }

}
