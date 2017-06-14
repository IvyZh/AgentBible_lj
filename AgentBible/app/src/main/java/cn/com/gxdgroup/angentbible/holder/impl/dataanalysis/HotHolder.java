package cn.com.gxdgroup.angentbible.holder.impl.dataanalysis;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.activities.DataAnalysisActivity;
import cn.com.gxdgroup.angentbible.activities.temp.DataInputActivity;
import cn.com.gxdgroup.angentbible.activities.temp.PeopleManagerActivity;
import cn.com.gxdgroup.angentbible.activities.temp.StatisticsMenuActivity;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/20.
 *
 * @description: 热门
 */

public class HotHolder extends BaseHolder {
    @BindView(R.id.tv_char_title)
    TextView mTvCharTitle;
    @BindView(R.id.iv_hot_city_ana)
    ImageView mIvHotCityAna;
    @BindView(R.id.rl_hot_city_ana)
    RelativeLayout mRlHotCityAna;
    @BindView(R.id.iv_area)
    ImageView mIvArea;
    @BindView(R.id.rl_hot_area)
    RelativeLayout mRlHotArea;
    @BindView(R.id.iv_keyuan)
    ImageView mIvKeyuan;
    @BindView(R.id.rl_bankuai)
    RelativeLayout mRlBankuai;
    @BindView(R.id.iv_second_hand_house)
    ImageView mIvSecondHandHouse;
    @BindView(R.id.tv_second_hand_house)
    TextView mTvSecondHandHouse;
    @BindView(R.id.rl_hot_garden)
    RelativeLayout mRlHotGarden;

    public HotHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_hot);
    }

    @Override
    public void setData() {

    }

    @OnClick({R.id.rl_hot_city_ana, R.id.rl_hot_area, R.id.rl_bankuai, R.id.rl_hot_garden, R.id.rl_people_manager, R.id.rl_data_ana, R.id.rl_data_show})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_hot_city_ana:
                DataAnalysisActivity.startActivity(mActivity, 0);
                break;
            case R.id.rl_hot_area:
                DataAnalysisActivity.startActivity(mActivity, 1);
                break;
            case R.id.rl_bankuai:
                DataAnalysisActivity.startActivity(mActivity, 2);
                break;
            case R.id.rl_hot_garden:
                DataAnalysisActivity.startActivity(mActivity, 3);
                break;
            case R.id.rl_people_manager://人员管理
                PeopleManagerActivity.startActivity(mActivity);
                break;
            case R.id.rl_data_ana://数据录入
                DataInputActivity.startActivity(mActivity);
                break;
            case R.id.rl_data_show://数据统计
                StatisticsMenuActivity.startActivity(mActivity);
                break;
        }
    }
}
