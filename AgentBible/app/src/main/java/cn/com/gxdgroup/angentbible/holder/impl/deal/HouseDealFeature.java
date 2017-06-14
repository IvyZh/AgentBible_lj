package cn.com.gxdgroup.angentbible.holder.impl.deal;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/26.
 *
 * @description:
 */

public class HouseDealFeature extends BaseHolder {
    public HouseDealFeature(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_house_deal_feature);
    }

    @Override
    public void setData() {

    }
}
