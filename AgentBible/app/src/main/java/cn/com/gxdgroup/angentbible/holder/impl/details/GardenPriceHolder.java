package cn.com.gxdgroup.angentbible.holder.impl.details;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description: 二手房详情里面的  价格走势Holder
 */

public class GardenPriceHolder extends BaseHolder {
    public GardenPriceHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_trend_chart);
    }

    @Override
    public void setData() {

    }
}
