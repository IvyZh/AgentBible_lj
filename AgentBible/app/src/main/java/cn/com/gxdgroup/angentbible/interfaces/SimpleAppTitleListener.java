package cn.com.gxdgroup.angentbible.interfaces;

import android.content.Context;

import cn.com.gxdgroup.angentbible.base.BaseActivity;

/**
 * Created by Ivy on 2016/10/22.
 *
 * @description:
 */

public class SimpleAppTitleListener implements AppTitleListener {
    private BaseActivity mContext;//用于finishActivity

    public SimpleAppTitleListener(BaseActivity context) {
        mContext = context;
    }

    @Override
    public void onBack() {
        mContext.finish();
    }

    @Override
    public void onTabRight() {

    }

    @Override
    public void onTabLeft() {

    }

    @Override
    public void OnivRight() {

    }

    @Override
    public void OntvRight() {

    }

    @Override
    public void OnrlSearch() {

    }
}
