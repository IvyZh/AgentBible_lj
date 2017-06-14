package cn.com.gxdgroup.angentbible.holder.impl;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/14.
 *
 * @description:
 */

public class ComingSoonHolder extends BaseHolder {
    public ComingSoonHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(cn.com.gxdgroup.angentbible.R.layout.layout_coming_soon_holder);
    }

    @Override
    public void setData() {

    }
}
