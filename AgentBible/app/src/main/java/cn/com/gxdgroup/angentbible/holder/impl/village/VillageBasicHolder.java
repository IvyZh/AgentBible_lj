package cn.com.gxdgroup.angentbible.holder.impl.village;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.activities.VillageBasicInfoActivity;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description: 小区信息-基本信息Holder(不是信息+简介+配套这个界面)
 */

public class VillageBasicHolder extends BaseHolder {


    @BindView(R.id.rl_village_basic)
    RelativeLayout mRlVillageBasic;

    public VillageBasicHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_village_basic);
    }

    @Override
    public void setData() {

    }


    @OnClick(R.id.rl_village_basic)
    public void onClick() {
        mActivity.startActivity(new Intent(mActivity, VillageBasicInfoActivity.class));
    }
}
