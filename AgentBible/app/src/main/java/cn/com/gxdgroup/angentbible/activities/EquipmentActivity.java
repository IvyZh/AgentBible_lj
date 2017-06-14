package cn.com.gxdgroup.angentbible.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.mapapi.map.TextureMapView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.domain.MessageEvent;
import cn.com.gxdgroup.angentbible.holder.impl.village.AroundEquipmentHolder;
import cn.com.gxdgroup.angentbible.holder.impl.village.EquipmentMapDetialsHolder;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;
import cn.com.gxdgroup.angentbible.ui.TitleView;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/20.
 *
 * @description: 周边配套界面
 */

public class EquipmentActivity extends BaseActivity {
    @BindView(R.id.titleView)
    AppTitleView mTitleView;
    @BindView(R.id.fr_map)
    FrameLayout mFrMap;
    private TextureMapView mMapView;
    private boolean isMap = true;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_equipment);
    }

    @Override
    public void initView() {
        //注册事件
//        EventBus.getDefault().register(this);

        final AroundEquipmentHolder equipmentHolder = new AroundEquipmentHolder(this);
        final EquipmentMapDetialsHolder detialsHolder = new EquipmentMapDetialsHolder(this);

        equipmentHolder.showMapTitleBar(false);


        mFrMap.addView(equipmentHolder.getContentView());
        mFrMap.addView(detialsHolder.getContentView());

        detialsHolder.getContentView().setVisibility(View.GONE);


        mTitleView.showMode(AppTitleView.MODE.TITLE_R_TV, -1, this)
                .setRightMsg("详情")
                .setTitleMsg("周边配套")
                .setListener(new SimpleAppTitleListener(this) {
                    @Override
                    public void OntvRight() {
                        if (isMap) {
                            equipmentHolder.getContentView().setVisibility(View.GONE);
                            detialsHolder.getContentView().setVisibility(View.VISIBLE);
                            detialsHolder.setData();
                        } else {
                            detialsHolder.getContentView().setVisibility(View.GONE);
                            equipmentHolder.getContentView().setVisibility(View.VISIBLE);
                        }
                        isMap = !isMap;
                    }
                });


        mMapView = equipmentHolder.getMapView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
