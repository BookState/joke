package com.tj.uimading.FragMents;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.tj.uimading.R;
import com.tj.uimading.adapter.DataAdapter;
import com.tj.uimading.base.BaseFragment;
import com.tj.uimading.base.RecycleViewDivider;
import com.tj.uimading.entity.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/31.
 */
public class NewsListFragment extends BaseFragment {
    //    @BindView(R.id.textView)
    //    TextView mTextView;
    @BindView(R.id.rcv_content)
    RecyclerView rcv_content;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiper;

    private boolean isPrepared;//加载是否准备好
    private boolean isVisible;//是否可见
    private boolean isCompleted;//是否已经加载完成。
    private String tid;
    private DataAdapter adapter;
    private LinearLayoutManager lilamanager;

    @Override
    protected int getRealLayout() {
        return R.layout.item_fnewlist;
    }

    @Override
    protected String getRealUrl() {
        String url = "http://c.m.163.com/nc/article/list/" + tid + "/0-20.html";
        return url;
    }

    Data tempdata;

    @Override
    protected void parseRealData(String result) {

        List<Data> dlist = new ArrayList<Data>();
        try {
            JSONArray jarray = new JSONObject(result).getJSONArray(tid);
            for (int i = 0; i < jarray.length(); i++) {
                Data data = new Gson().fromJson(jarray.getString(i), Data.class);
                if (i == 0) {
                    tempdata = data;
                }
                dlist.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.addList(dlist);
        adapter.notifyDataSetChanged();
        //        adapter = new DataAdapter(dlist, getContext());
        //        rcv_content.setAdapter(adapter);
        //        rcv_content.setLayoutManager(lilamanager);
        //数据加载完成就设置为true.
        isCompleted = true;
    }

    @Override
    protected void initData() {
        isPrepared = true;
        lilamanager = new LinearLayoutManager(getContext());
        final Bundle bundle = getArguments();
        adapter = new DataAdapter(getContext());
        rcv_content.setAdapter(adapter);
        rcv_content.setLayoutManager(lilamanager);
        rcv_content.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1, Color.RED));
        adapter.setOnItemClickListener(new DataAdapter.onItemClickListener() {
            @Override
            public void itemClick(int viewId, int position) {
                if (viewId == DataAdapter.RECYCLE_ITEM) {
                    String url = adapter.getList().get(position).url;
                    String docid = adapter.getList().get(position).docid;
                    String boardid = adapter.getList().get(position).boardid;
                    String postid = adapter.getList().get(position).postid;
                    if (url != null) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("url", url);
                        bundle1.putString("docid", docid);
                        bundle1.putString("boardid", boardid);
                        bundle1.putString("postid", postid);
                        mListener.onFragmentInteraction(viewId, bundle1);
                    }
                }
            }
        });
        if (bundle != null) {
            tid = bundle.getString("tid");
        }


        //上拉刷新
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //              if (swiper.isRefreshing()){
                //
                //              }
                swiper.setRefreshing(false);
            }
        });

        rcv_content.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!swiper.isRefreshing()) {
                    int lastVisibleItem = lilamanager.findLastVisibleItemPosition();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                        //调用Adapter里的changeMoreStatus方法来改变加载脚View的显示状态为：正在加载...
                        adapter.changeMoreStatus(DataAdapter.ISLOADING);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                //当加载完数据后，再恢复加载脚View的显示状态为：上拉加载更多
                                adapter.changeMoreStatus(DataAdapter.NO_MORE_DATA);
                            }
                        }, 3000);
                    }
                }
            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//frahment从不可见到完全可见的时候，会调用该方法
        super.setUserVisibleHint(isVisibleToUser);
        // Log.d("newListFragment:", "setUserVisibleHint: ");
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    //
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || isCompleted)
            return;

        showSuccessPager();

    }

    //
    protected void onInvisible() {

    }
    //
    //懒加载的方法,在这个方法里面我们为Fragment的各个组件去添加数据

    protected void onVisible() {
        lazyLoad();
    }

    //
    //    @Override
    //    protected void initData() {
    //        isPrepared = true;
    //        //根据传进来的tid去获取json数据
    //        lazyLoad();
    //
    //    }
    //
    //    private void getInData(final String tid) {
    //        //http://c.m.163.com/nc/article/list/T1348649145984/0-20.html
    //        //http://c.m.163.com/nc/article/list/T1348648756099/0-20.html
    //        String url = "http://c.m.163.com/nc/article/list/" + tid + "/0-20.html";
    //        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
    //            @Override
    //            public void onSuccess(String result) {
    //                List<Data> dlist = new ArrayList<Data>();
    //                try {
    //                    JSONArray jarray = new JSONObject(result).getJSONArray(tid);
    //                    for (int i = 0; i < jarray.length(); i++) {
    //                        Data data = new Gson().fromJson(jarray.getString(i), Data.class);
    //                        dlist.add(data);
    //                    }
    //                } catch (JSONException e) {
    //                    e.printStackTrace();
    //                }
    //
    //
    //                //String tname = getArguments().getString("tname");
    //                //mTextView.setText(tname);
    //                DataAdapter adapter = new DataAdapter(dlist, getContext());
    //                rcv_content.setAdapter(adapter);
    //                rcv_content.setLayoutManager(new LinearLayoutManager(getContext()));
    //                //数据加载完成就设置为true.
    //                isCompleted = true;
    //            }
    //
    //            @Override
    //            public void onError(Throwable ex, boolean isOnCallback) {
    //
    //            }
    //
    //            @Override
    //            public void onCancelled(CancelledException cex) {
    //
    //            }
    //
    //            @Override
    //            public void onFinished() {
    //
    //            }
    //        });
    //    }
    //
    public static NewsListFragment getInstance(Bundle bundle) {
        NewsListFragment newslist = new NewsListFragment();
        if (bundle != null) {
            newslist.setArguments(bundle);
        }
        return newslist;
    }
    //
    //
    //    @Override
    //    public int getLayoutId() {
    //        return R.layout.item_fnewlist;
    //    }


}
