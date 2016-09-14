package com.tj.uimading;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.tj.uimading.FragMents.FavorFragment;
import com.tj.uimading.FragMents.HotFragment;
import com.tj.uimading.FragMents.LoginFragment;
import com.tj.uimading.FragMents.NewsFragment;
import com.tj.uimading.adapter.DataAdapter;
import com.tj.uimading.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, BaseFragment.OnFragmentInteractionListener {
    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.rg_four)
    RadioGroup rg_four;

    FavorFragment favor;
    HotFragment hot;
    LoginFragment login;
    NewsFragment news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        favor = new FavorFragment();
        hot = new HotFragment();
        login = new LoginFragment();
        news = NewsFragment.getInstance(getIntent().getExtras());

        addFragment(news);
        setListeners();
    }

    private void addFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, f, f.getClass().getSimpleName()).commit();
    }

    private void setListeners() {
        rg_four.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_home:
                showFragment(news);
                break;
            case R.id.rb_smartservice:
                showFragment(hot);
                break;
            case R.id.rb_gov:
                showFragment(favor);
                break;
            case R.id.rb_setting:
                showFragment(login);
                break;
        }
    }

    private void showFragment(Fragment f) {
        //        FragmentManager fm = getSupportFragmentManager();
        //        List<Fragment> list = fm.getFragments();
        //        Fragment tempfragment=null;
        //        if (list != null) {
        //            for (Fragment fragment:list) {
        //                if (fragment!=f){
        //                    fm.beginTransaction().hide(fragment).commit();
        //                }else{
        //                    tempfragment=fragment;
        //                }
        //            }
        //
        //            if (tempfragment==null){
        //                addFragment(f);
        //                tempfragment=f;
        //            }
        //
        //            fm.beginTransaction().show(tempfragment).commit();
        //        }

        Fragment[] fs = {news, favor, hot, login};

        FragmentManager fm = getSupportFragmentManager();

        //通过tag判断fragment是否已经加载过了，如果没有加载过就加载上。
        if (f != fm.findFragmentByTag(f.getClass().getSimpleName())) {
            addFragment(f);
        }

        FragmentTransaction tr = fm.beginTransaction();


        for (Fragment tf : fs) {
            tr.hide(tf);
        }
        tr.show(f).commit();

    }


    @Override
    public void onFragmentInteraction(int viewId, Bundle bundle) {
        switch (viewId) {
            case DataAdapter.RECYCLE_ITEM:
                Intent intent = new Intent(NewsActivity.this, BrowserActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
