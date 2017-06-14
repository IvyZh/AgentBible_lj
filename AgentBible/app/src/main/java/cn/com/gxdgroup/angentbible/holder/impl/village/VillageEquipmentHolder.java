package cn.com.gxdgroup.angentbible.holder.impl.village;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/26.
 *
 * @description: 小区信息-配套设施
 */

public class VillageEquipmentHolder extends BaseHolder {
    public VillageEquipmentHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_village_info_equipment);
    }

    @Override
    public void setData() {

    }
}
