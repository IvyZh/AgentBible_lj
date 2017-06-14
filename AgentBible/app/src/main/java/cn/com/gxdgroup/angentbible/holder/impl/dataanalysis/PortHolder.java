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
 * @description: 端口
 */

public class PortHolder extends BaseHolder {
    @BindView(R.id.tv_char_title)
    TextView mTvCharTitle;
    @BindView(R.id.iv_hot_city_ana)
    ImageView mIvHotCityAna;
    @BindView(R.id.rl_hot_public_num)
    RelativeLayout mRlHotPublicNum;
    @BindView(R.id.iv_area)
    ImageView mIvArea;
    @BindView(R.id.rl_agent_num)
    RelativeLayout mRlAgentNum;

    public PortHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_port);
    }

    @Override
    public void setData() {

    }

    @OnClick({R.id.rl_hot_public_num, R.id.rl_agent_num})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_hot_public_num:
                DataAnalysisActivity.startActivity(mActivity, 4);
                break;
            case R.id.rl_agent_num:
                DataAnalysisActivity.startActivity(mActivity, 5);
                break;
        }
    }
}
