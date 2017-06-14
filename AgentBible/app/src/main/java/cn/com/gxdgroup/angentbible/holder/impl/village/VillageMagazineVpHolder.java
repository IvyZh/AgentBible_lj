package cn.com.gxdgroup.angentbible.holder.impl.village;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/26.
 *
 * @description: 小区月报-浏览量
 */

public class VillageMagazineVpHolder extends BaseHolder {
    public VillageMagazineVpHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_magazine_deal);
    }

    @Override
    public void setData() {

    }
}
