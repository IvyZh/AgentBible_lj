package cn.com.gxdgroup.angentbible.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseFragment;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.holder.impl.home.HomeCarouselHolder;
import cn.com.gxdgroup.angentbible.holder.impl.home.TrendChartHolder;
import cn.com.gxdgroup.angentbible.holder.impl.home.HomeMenuHolder;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/14.
 *
 * @description:
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.fr_imgs_ad)
    FrameLayout mFrImgsAd;
    @BindView(R.id.fr_menu)
    FrameLayout mFrMenu;
    @BindView(R.id.fr_market)
    FrameLayout mFrMarket;
    BaseHolder carouselHolder, menuHolder;
    TrendChartHolder homeMarketHolder;

    @Override
    public void loadData() {
        L.v("HomeFragment load data...");

//        homeMarketHolder.setData();

        UIUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (homeMarketHolder != null) {
                    homeMarketHolder.setData();//有可能null TODO
                }
            }
        }, 1000);
    }

    @Override
    public View setContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    protected void initView(View view) {
        homeMarketHolder = new TrendChartHolder(mActivity);
        carouselHolder = new HomeCarouselHolder(mActivity);
        menuHolder = new HomeMenuHolder(mActivity);

        mFrImgsAd.addView(carouselHolder.getContentView());
        mFrMenu.addView(menuHolder.getContentView());
        mFrMarket.addView(homeMarketHolder.getContentView());

        homeMarketHolder.setLocationVisibility(true);
    }

}
