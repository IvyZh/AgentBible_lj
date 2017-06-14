package cn.com.gxdgroup.angentbible.holder.impl.home;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.activities.HouseResourcesListActivity;
import cn.com.gxdgroup.angentbible.constant.MenuType;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/17.
 *
 * @description: 首页四大模块的Holder
 */

public class HomeMenuHolder extends BaseHolder {
    public HomeMenuHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_home_menu);
    }

    @Override
    public void setData() {

    }

    @OnClick({R.id.rl_second_hand_house, R.id.rl_tenement, R.id.rl_keyuan, R.id.rl_deal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_second_hand_house:
                HouseResourcesListActivity.startActivity(mActivity, MenuType.SENCOND_HAND);
                break;
            case R.id.rl_tenement:
                HouseResourcesListActivity.startActivity(mActivity, MenuType.RENT_HOUSE);
                break;
            case R.id.rl_keyuan:
                HouseResourcesListActivity.startActivity(mActivity, MenuType.KE_BUY);
                break;
            case R.id.rl_deal:
                HouseResourcesListActivity.startActivity(mActivity, MenuType.RECENT_DEAL);
                break;
        }
    }
}
