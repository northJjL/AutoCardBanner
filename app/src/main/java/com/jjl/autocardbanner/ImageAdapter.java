package com.jjl.autocardbanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**  轮播图 */
class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private int count = 100;
    private Queue<View> views = new LinkedList<>();
    private final List<Integer> data = new ArrayList<>();


    public ImageAdapter(Context context ,List<Integer> lists) {
        this.mContext = context;
        if (lists != null) {
            data.addAll(lists);
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        CardView cardView = (CardView) views.poll();
        ImageView mage ;
        if (cardView == null) {
            cardView = new CardView(mContext);
            mage = new ImageView(mContext);
            mage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            cardView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            cardView.setRadius(20f);
            mage.setId(count++);
            mage.setId(count);
            mage.setScaleType(ImageView.ScaleType.FIT_XY);
            cardView.addView(mage);
        }else{
            if(cardView.getChildCount() != 1){
                mage = new ImageView(mContext);
                mage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mage.setId(count);
                mage.setId(count++);
                mage.setScaleType(ImageView.ScaleType.FIT_XY);
                cardView.addView(mage);
            }else{
                mage = (ImageView) cardView.getChildAt(0);
            }
        }
//        mage.setImageResource(data.get(position));
        Glide.with(mContext).load(data.get(position)).into(mage);
        container.addView(cardView);
        return cardView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        CardView cardView = (CardView) object;
        views.add(cardView);
        container.removeView(cardView);
    }
}
