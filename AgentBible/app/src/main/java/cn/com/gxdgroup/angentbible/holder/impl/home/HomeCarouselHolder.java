package cn.com.gxdgroup.angentbible.holder.impl.home;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/17.
 *
 * @description: 首页图片的轮播Holder
 */

public class HomeCarouselHolder extends BaseHolder {
    public HomeCarouselHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_home_carousel);
    }

    @Override
    public void setData() {

    }
}
