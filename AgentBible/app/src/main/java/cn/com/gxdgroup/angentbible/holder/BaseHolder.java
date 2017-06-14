package cn.com.gxdgroup.angentbible.holder;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Ivy on 2016/10/14.
 *
 * @description:
 */

public abstract class BaseHolder {

    private View contentView;
    protected FragmentActivity mActivity;
    protected boolean isInitViewDown = false;// 是否初始化完毕

    public BaseHolder(FragmentActivity activity) {
        mActivity = activity;
        contentView = setContentView();
        ButterKnife.bind(this, contentView);
        initView();
        isInitViewDown = true;
    }

    //把当前的view返回给父类
    public View getContentView() {
        return contentView;
    }

    //设置根视图
    public abstract View setContentView();

    //执行一些初始化的操作，非必须，所以空实现了，需要的话重写即可
    public void initView() {
    }

    /**
     * 给holder设置数据，子类需要覆盖
     */
    public abstract void setData();
}
