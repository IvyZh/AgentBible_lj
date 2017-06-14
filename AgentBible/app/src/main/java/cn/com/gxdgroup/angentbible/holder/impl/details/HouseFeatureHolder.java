package cn.com.gxdgroup.angentbible.holder.impl.details;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.activities.HouseDetailsActivity;
import cn.com.gxdgroup.angentbible.activities.VillageInfoActivity;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description: 二手房详情里面的Holder
 */

public class HouseFeatureHolder extends BaseHolder {

    private final int mMenuType;
    @BindView(R.id.tv_publish_time)
    TextView mTvPublishTime;
    @BindView(R.id.tv_house_price)
    TextView mTvHousePrice;
    @BindView(R.id.tv_house_layout)
    TextView mTvHouseLayout;
    @BindView(R.id.tv_house_area)
    TextView mTvHouseArea;
    @BindView(R.id.tv_unit_price)
    TextView mTvUnitPrice;
    @BindView(R.id.tv_house_fitment)
    TextView mTvHouseFitment;
    @BindView(R.id.tv_house_old)
    TextView mTvHouseOld;
    @BindView(R.id.tv_house_floor)
    TextView mTvHouseFloor;
    @BindView(R.id.tv_house_orientation)
    TextView mTvHouseOrientation;
    @BindView(R.id.tv_house_location)
    TextView mTvHouseLocation;
    @BindView(R.id.tv_garden_name_label)
    TextView mTvGardenNameLabel;
    @BindView(R.id.tv_garden_name)
    TextView mTvGardenName;
    @BindView(R.id.rl_garden_name)
    RelativeLayout mRlGardenName;
    @BindView(R.id.tv_price_type)
    TextView mTvPriceType;
    @BindView(R.id.tv_price_way)
    TextView mTvPriceWay;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.tv_area_status)
    TextView mTvAreaStatus;


    public HouseFeatureHolder(HouseDetailsActivity activity, int menuType) {
        super(activity);
        this.mMenuType = menuType;
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_house_feature);
    }

    /**
     * 同样的原因，需要根据传入的mMenuType来修改布局之后才能返回。{思路，是否可以依据此解决先前页面未加载完成，设置数据为null的情况，PS考虑使用RxJava Retrofit }
     *
     * @return
     */
    @Override
    public View getContentView() {
        if (mMenuType == 1) {//租房
            mTvPriceType.setText("租金:");
            mTvPriceWay.setText("方式：");
            mTvAreaStatus.setText("现状:");
        }


        return super.getContentView();
    }

    @Override
    public void setData() {
        mTvGardenName.setText("湖滨半岛公馆" + new Random().nextInt(99));
    }

    @OnClick(R.id.rl_garden_name)
    public void onClick() {
        mActivity.startActivity(new Intent(mActivity, VillageInfoActivity.class));
    }

}
