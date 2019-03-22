/*
 * Copyright (C) 2013 Leszek Mzyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jjl.autocardbanner.widget.loopindicator;

import android.os.Parcelable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * A PagerAdapter wrapper responsible for providing a proper page to
 * LoopViewPager
 * 
 * This class shouldn't be used directly
 */
public class LoopPagerAdapterWrapper extends PagerAdapter {

    private PagerAdapter mAdapter;

    private SparseArray<ToDestroy> mToDestroy = new SparseArray<ToDestroy>();

    private boolean mBoundaryCaching;

    private int maxCount = Integer.MAX_VALUE;
//    private int maxCount = 9;

    void setBoundaryCaching(boolean flag) {
        mBoundaryCaching = flag;
    }

    public LoopPagerAdapterWrapper(PagerAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void notifyDataSetChanged() {
        mToDestroy = new SparseArray<ToDestroy>();
        super.notifyDataSetChanged();
    }

    /**
     * 返回表位置
     * @param position  真实位置
     * @return
     *//*
    int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = (position-banner1) % realCount;
        if (realPosition < 0)
            realPosition += realCount;

        return realPosition;
    }*/

    /**
     * @param //realPosition  表位置
     * @return 返回真实位置
     */
    /*public int toInnerPosition(int realPosition) {
        int position = (realPosition + banner1);
        return position;
    }*/

    /*private int getRealFirstPosition() {
        return banner1;
    }

    private int getRealLastPosition() {
        return getRealFirstPosition() + getRealCount() - banner1;
    }*/

   /* @Override
    public int getCount() {
    	if(mAdapter.getCount()<banner2)
    		return mAdapter.getCount();
        return mAdapter.getCount() + banner2;
    }*/

    @Override
    public int getCount()
    {
        return maxCount;
    }

    public int getRealCount() {
        return mAdapter.getCount();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        ViewPager viewPager = (ViewPager) container;
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = getFirstItemPosition();
        } else if (position == getCount() - 1) {
            position = getLastItemPosition();
        }
        viewPager.setCurrentItem(position, false);
        mAdapter.startUpdate(container);
    }

    private int getFirstItemPosition() {
        return maxCount / getRealCount() / 2 * getRealCount();
    }

    private int getLastItemPosition() {
        return maxCount / getRealCount() / 2 * getRealCount() - 1;
    }

    public PagerAdapter getRealAdapter() {
        return mAdapter;
    }

    public int getRealPosition(int position) {
        return position % getRealCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = (mAdapter instanceof FragmentPagerAdapter || mAdapter instanceof FragmentStatePagerAdapter)
                ? position
                : getRealPosition(position);

        if (mBoundaryCaching) {
            ToDestroy toDestroy = mToDestroy.get(realPosition);
            if (toDestroy != null) {
                mToDestroy.remove(realPosition);
                return toDestroy.object;
            }
        }
        return mAdapter.instantiateItem(container, realPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realFirst = getFirstItemPosition();
        int realLast = getLastItemPosition();
        int realPosition = (mAdapter instanceof FragmentPagerAdapter || mAdapter instanceof FragmentStatePagerAdapter)
                ? position
                : getRealPosition(position);

        if (mBoundaryCaching && (position == realFirst || position == realLast)) {
            mToDestroy.put(realPosition, new ToDestroy(container, realPosition,
                    object));
        } else {
            mAdapter.destroyItem(container, realPosition, object);
        }
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */

    @Override
    public void finishUpdate(ViewGroup container) {
        mAdapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return mAdapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        mAdapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return mAdapter.saveState();
    }

    /*@Override
    public void startUpdate(ViewGroup container) {
        mAdapter.startUpdate(container);
    }*/

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mAdapter.setPrimaryItem(container, position, object);
    }

    /*
     * End delegation
     */

    /**
     * Container class for caching the boundary views
     */
    static class ToDestroy {
        ViewGroup container;
        int position;
        Object object;

        public ToDestroy(ViewGroup container, int position, Object object) {
            this.container = container;
            this.position = position;
            this.object = object;
        }
    }

}