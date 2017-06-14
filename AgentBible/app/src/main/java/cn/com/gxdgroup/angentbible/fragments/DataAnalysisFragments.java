package cn.com.gxdgroup.angentbible.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseFragment;
import cn.com.gxdgroup.angentbible.holder.impl.dataanalysis.CompanyHolder;
import cn.com.gxdgroup.angentbible.holder.impl.dataanalysis.HotHolder;
import cn.com.gxdgroup.angentbible.holder.impl.dataanalysis.PortHolder;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;
import cn.com.gxdgroup.angentbible.utils.UIUtils;


/**
 * Created by Ivy on 2016/10/14.
 *
 * @description:
 */

public class DataAnalysisFragments extends BaseFragment {


    @BindView(R.id.fr_hot)
    FrameLayout mFrHot;
    @BindView(R.id.fr_port)
    FrameLayout mFrPort;
    @BindView(R.id.fr_company)
    FrameLayout mFrCompany;
    @BindView(R.id.app_title)
    AppTitleView mAppTitle;

    @Override
    public View setContentView(LayoutInflater inflater) {
        return UIUtils.inflate(R.layout.fragment_data_analysis);
    }

    @Override
    protected void initView(View view) {

        mAppTitle.showMode(AppTitleView.MODE.DATA_ANALYSIS, -1, null);

        mFrHot.addView(new HotHolder(mActivity).getContentView());
        mFrPort.addView(new PortHolder(mActivity).getContentView());
        mFrCompany.addView(new CompanyHolder(mActivity).getContentView());
    }

    @Override
    public void loadData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
