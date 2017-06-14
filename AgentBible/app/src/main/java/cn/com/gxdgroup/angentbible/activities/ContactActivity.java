package cn.com.gxdgroup.angentbible.activities;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.interfaces.AppTitleListener;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ivy on 2016/10/20.
 *
 * @description:
 */

public class ContactActivity extends BaseActivity {
    @BindView(R.id.iv_portrait)
    CircleImageView ivPortrait;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.switch_bt)
    Switch switchBt;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.titleView)
    AppTitleView titleView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_contact);
    }

    @Override
    public void initView() {
        titleView.setTitleMsg("经纪人").showMode(AppTitleView.MODE.TITLE, -1, this).setListener(new SimpleAppTitleListener(this) {
        });
    }


}
