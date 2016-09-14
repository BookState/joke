package com.tj.uimading.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tj.uimading.R;
import com.tj.uimading.entity.Data;
import com.tj.uimading.utils.XImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/9/3.
 */
public class ADSAdapter extends PagerAdapter {
    private List<Data.ADS> ads;

    public ADSAdapter(List<Data.ADS> ads) {
        this.ads = ads;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(container.getContext(), R.layout.layout_item_one_head, null);
        ImageView img_head = (ImageView) view.findViewById(R.id.img_head);
        TextView tv_headtitle = (TextView) view.findViewById(R.id.tv_headtitle);
        XImageUtil.display(img_head, ads.get(position%ads.size()).imgsrc);
        tv_headtitle.setText(ads.get(position%ads.size()).title);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
//        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
