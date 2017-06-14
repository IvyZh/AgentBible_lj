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
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/20.
 *
 * @description: 公司
 */

public class CompanyHolder extends BaseHolder {
    @BindView(R.id.tv_char_title)
    TextView mTvCharTitle;
    @BindView(R.id.iv_hot_city_ana)
    ImageView mIvHotCityAna;
    @BindView(R.id.rl_hot_sign_num)
    RelativeLayout mRlHotSignNum;
    @BindView(R.id.iv_area)
    ImageView mIvArea;
    @BindView(R.id.rl_port_num)
    RelativeLayout mRlPortNum;

    public CompanyHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_company);
    }

    @Override
    public void setData() {

    }

    @OnClick({R.id.rl_hot_sign_num, R.id.rl_port_num})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_hot_sign_num:
                DataAnalysisActivity.startActivity(mActivity,6);
                break;
            case R.id.rl_port_num:
                DataAnalysisActivity.startActivity(mActivity,7);
                break;
        }
    }
}
