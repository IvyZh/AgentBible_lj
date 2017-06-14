package cn.com.gxdgroup.angentbible.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.holder.impl.village.BasicInfoHolder;
import cn.com.gxdgroup.angentbible.holder.impl.village.VillageBasicHolder;
import cn.com.gxdgroup.angentbible.holder.impl.village.VillageEquipmentHolder;
import cn.com.gxdgroup.angentbible.holder.impl.village.VillageIntroduceHolder;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;

/**
 * Created by Ivy on 2016/10/26.
 *
 * @description: 小区基本信息+配套设施+小区简介
 */

public class VillageBasicInfoActivity extends BaseActivity {
    @BindView(R.id.titleView)
    AppTitleView mTitleView;
    @BindView(R.id.fr_village_basic)
    FrameLayout mFrVillageBasic;
    @BindView(R.id.fr_village_equipment)
    FrameLayout mFrVillageEquipment;
    @BindView(R.id.fr_village_introduce)
    FrameLayout mFrVillageIntroduce;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_village_basic);
    }

    @Override
    public void initView() {
        mTitleView.showMode(AppTitleView.MODE.TITLE, -1, this)
                .setTitleMsg("小区信息")
                .setListener(new SimpleAppTitleListener(this));


        BasicInfoHolder basicHolder = new BasicInfoHolder(this);
        VillageEquipmentHolder equipmentHolder = new VillageEquipmentHolder(this);
        VillageIntroduceHolder introduceHolder = new VillageIntroduceHolder(this);

        mFrVillageBasic.addView(basicHolder.getContentView());
        mFrVillageEquipment.addView(equipmentHolder.getContentView());
        mFrVillageIntroduce.addView(introduceHolder.getContentView());
    }


}
