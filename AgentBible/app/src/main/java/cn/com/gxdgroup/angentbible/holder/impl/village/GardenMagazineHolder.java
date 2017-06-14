package cn.com.gxdgroup.angentbible.holder.impl.village;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.activities.VillageMagazineActivity;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description: 小区月报
 */

public class GardenMagazineHolder extends BaseHolder {
    @BindView(R.id.rl_village_magazine)
    RelativeLayout mRlVillageMagazine;

    public GardenMagazineHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_garden_magazine);
    }

    @Override
    public void setData() {

    }

    @OnClick(R.id.rl_village_magazine)
    public void onClick() {
        mActivity.startActivity(new Intent(mActivity, VillageMagazineActivity.class));
    }
}
