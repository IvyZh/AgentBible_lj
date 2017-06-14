package cn.com.gxdgroup.angentbible.holder.impl.details;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description: 二手房详情里面的Holder
 */

public class ZuFangInDetailsHolder extends BaseHolder {
    public ZuFangInDetailsHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_zufang_in_details);
    }

    @Override
    public void setData() {

    }
}
