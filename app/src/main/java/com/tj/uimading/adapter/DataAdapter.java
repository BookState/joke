package com.tj.uimading.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tj.uimading.R;
import com.tj.uimading.entity.Data;
import com.tj.uimading.utils.XImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/2.
 */
public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Data> list;
    private Context context;
    private onItemClickListener mOnItemClickListener;
    private final int VIEW_TYPE_INFO = 0;
    private final int VIEW_TYPE_IMG = 1;
    private final int VIEW_TYPE_HEAD = 2;
    private final int VIEW_TYPE_VP = 3;
    private final int VIEW_TYPE_FOOT = 4;


    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0; //默认状态，提示上拉加载
    //正在加载...
    public static final int ISLOADING = 1;//正在加载
    public static final int NO_MORE_DATA = 2;// 没有更多数据了
    //上拉加载的显示状态，初始为0
    private int load_more_status;
    public static final int RECYCLE_ITEM = 0;

    public DataAdapter(List<Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public DataAdapter(Context context) {
        this.context = context;
    }

    public List<Data> getList() {
        return list;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }

    //添加一个增加集合的方法。
    public void addList(List<Data> l) {
        if (list == null) {
            setList(l);
            return;
        }
        if (l == null)
            return;

        list.addAll(l);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_INFO:
                View view = View.inflate(context, R.layout.layout_item_info_img, null);
                return new InfoImgHolder(view);
            case VIEW_TYPE_IMG:
                view = View.inflate(context, R.layout.layout_item_three_img, null);
                return new ThreeImgHolder(view);
            case VIEW_TYPE_HEAD:
                view = View.inflate(context, R.layout.layout_item_one_head, null);
                return new OneHeadHolder(view);
            case VIEW_TYPE_VP:
                view = View.inflate(context, R.layout.layout_item_vp, null);
                return new VPHolder(view);
            case VIEW_TYPE_FOOT:
                view = View.inflate(context, R.layout.layout_item_foot, null);
                return new FootHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            if (list.get(position).ads == null) {
                return VIEW_TYPE_HEAD;
            } else {
                return VIEW_TYPE_VP;
            }
        } else if (position > 0 && position < getItemCount() - 1) {
            if (list.get(position).imgextra == null) {
                return VIEW_TYPE_INFO;
            } else {
                return VIEW_TYPE_IMG;
            }
        } else {
            return VIEW_TYPE_FOOT;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof InfoImgHolder) {
            XImageUtil.display(((InfoImgHolder) holder).mIvImg, list.get(position).imgsrc);
            ((InfoImgHolder) holder).mTvInfotitle.setText(list.get(position).title);
            ((InfoImgHolder) holder).mTvDigest.setText(list.get(position).digest);
            ((InfoImgHolder) holder).mTvFollow.setText("共有" + list.get(position).replyCount + "人跟帖");
        } else if (holder instanceof ThreeImgHolder) {
            ((ThreeImgHolder) holder).mTvTitle.setText(list.get(position).title);
            XImageUtil.display(((ThreeImgHolder) holder).mIvLeft, list.get(position).imgsrc);
            XImageUtil.display(((ThreeImgHolder) holder).mIvMiddle, list.get(position).imgextra.get(0).imgsrc);
            XImageUtil.display(((ThreeImgHolder) holder).mIvRight, list.get(position).imgextra.get(1).imgsrc);
            ((ThreeImgHolder) holder).mTvCome.setText("共有" + list.get(position).replyCount + "人跟帖");
        } else if (holder instanceof OneHeadHolder) {
            XImageUtil.display(((OneHeadHolder) holder).mImgHead, list.get(position).imgsrc);
            ((OneHeadHolder) holder).mTvHeadtitle.setText(list.get(position).title);
        } else if (holder instanceof VPHolder) {
            ADSAdapter adapter = new ADSAdapter(list.get(position).ads);
            ((VPHolder) holder).viewpager.setAdapter(adapter);
            ((VPHolder) holder).viewpager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % list.get(position).ads.size()));
        } else{
            FootHolder h = (FootHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    h.mFootPb.setVisibility(View.GONE);
                    h.mTvLoading.setText("上拉松手加载数据....");
                    break;
                case ISLOADING:
                    h.mFootPb.setVisibility(View.VISIBLE);
                    h.mTvLoading.setText("数据加载中....");
                    break;
                case NO_MORE_DATA:
                    h.mFootPb.setVisibility(View.GONE);
                    h.mTvLoading.setText("没有更多数据了!");
                    break;
            }
        }

        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.itemClick(RECYCLE_ITEM,position);
                }
            });
        }
    }

    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() + 1;
    }

    public static class InfoImgHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_infotitle)
        TextView mTvInfotitle;
        @BindView(R.id.tv_digest)
        TextView mTvDigest;
        @BindView(R.id.tv_follow)
        TextView mTvFollow;

        public InfoImgHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ThreeImgHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.iv_left)
        ImageView mIvLeft;
        @BindView(R.id.iv_middle)
        ImageView mIvMiddle;
        @BindView(R.id.iv_right)
        ImageView mIvRight;
        @BindView(R.id.tv_come)
        TextView mTvCome;

        public ThreeImgHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class OneHeadHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_head)
        ImageView mImgHead;
        @BindView(R.id.tv_headtitle)
        TextView mTvHeadtitle;

        public OneHeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class VPHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewpager)
        ViewPager viewpager;

        public VPHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    public class FootHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.foot_pb)
        ProgressBar mFootPb;
        @BindView(R.id.tv_loading)
        TextView mTvLoading;

        public FootHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



    public interface onItemClickListener{
        void itemClick(int viewId,int position);
    }

    public onItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}

