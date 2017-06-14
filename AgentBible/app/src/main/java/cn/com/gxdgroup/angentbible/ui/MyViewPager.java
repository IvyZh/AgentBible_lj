package cn.com.gxdgroup.angentbible.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Ivy on 2016/10/19.
 */

public class MyViewPager extends ViewPager {
    /**是否禁止左右滑动，true为禁止，false为不禁止*/
    private boolean noScroll = true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(ev);
    }


    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }
}
