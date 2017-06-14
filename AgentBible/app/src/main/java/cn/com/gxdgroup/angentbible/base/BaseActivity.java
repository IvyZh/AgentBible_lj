package cn.com.gxdgroup.angentbible.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.ui.SystemBarTintManager;
import cn.com.gxdgroup.angentbible.utils.AppManager;


/**
 * Created by Ivy on 2016/10/7.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        AppManager.getAppManager().addActivity(this);// 添加Activity到堆栈

        setContentView();//需要设置setContentView
        ButterKnife.bind(this);//绑定ButterKnife
        initView();//初始化View
        initActionBar(R.color.common_blue);
        initListener();//监听
        loadData();//加载数据
    }

    public void initActionBar(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorId);// 通知栏所需颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * setContentView
     */
    protected abstract void setContentView();

    /**
     * 初始化View中控件
     */
    public abstract void initView();

    /**
     * 给控件添加点击监听事件(选填，一般也可以使用ButterKnife)
     */
    protected void initListener() {
    }

    /**
     * 加载数据的网络操作(选填)
     */
    protected void loadData() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }
}
