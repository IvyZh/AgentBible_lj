package cn.com.gxdgroup.angentbible.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import com.bigkoo.alertview.AlertView;

import butterknife.BindView;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.constant.MenuType;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.holder.impl.deal.HouseDealFeature;
import cn.com.gxdgroup.angentbible.holder.impl.details.BannerHolder;
import cn.com.gxdgroup.angentbible.holder.impl.details.HouseDealInDetailsHolder;
import cn.com.gxdgroup.angentbible.holder.impl.details.HouseFeatureHolder;
import cn.com.gxdgroup.angentbible.holder.impl.details.HouseIntroHolder;
import cn.com.gxdgroup.angentbible.holder.impl.details.RemarkHolder;
import cn.com.gxdgroup.angentbible.holder.impl.details.SecondHandHouseInDetailsHolder;
import cn.com.gxdgroup.angentbible.holder.impl.details.ZuFangInDetailsHolder;
import cn.com.gxdgroup.angentbible.holder.impl.home.TrendChartHolder;
import cn.com.gxdgroup.angentbible.ui.TitleView;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description: 二手房详情界面+租房（mMenuType）
 */

public class HouseDetailsActivity extends BaseActivity {
    @BindView(R.id.fr_banner)
    FrameLayout mFrBanner;
    @BindView(R.id.fr_house_intro)
    FrameLayout mFrHouseIntro;
    @BindView(R.id.fr_garden_price)
    FrameLayout mFrGardenPrice;
    @BindView(R.id.fr_second_house)
    FrameLayout mFrSecondHouse;
    @BindView(R.id.fr_zufang)
    FrameLayout mFrZufang;
    @BindView(R.id.fr_house_deal)
    FrameLayout mFrHouseDeal;
    @BindView(R.id.fr_house_feature)
    FrameLayout mFrHouseFeature;
    @BindView(R.id.titleView)
    TitleView mTitleView;
    @BindView(R.id.fr_remark)
    FrameLayout mFrRemark;

    private BaseHolder featureHolder, introHolder, bannerHolder, trendChartHolder, zuFangInDetailsHolder, secondHandHouseInDetailsHolder, dealInDetailsHolder, remarkHolder;

    private int mMenuType;

    public static void startActivity(Activity ba, int menuType) {
        Intent intent = new Intent(ba, HouseDetailsActivity.class);
        intent.putExtra("menuType", menuType);
        ba.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_house_details);
    }

    @Override
    public void initView() {
        mMenuType = getIntent().getIntExtra("menuType", 0);


        mTitleView.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTitleView.setTitle("湖滨半岛公馆");

        mTitleView.showRightImageView(true);
        mTitleView.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertView mAlertView = new AlertView("标题", "内容", "取消", new String[]{"确定"}, null, mContext, AlertView.Style.Alert, null).setCancelable(true);
                mAlertView.show();
            }
        });


        // 初始化Holder内容

        if (mMenuType != MenuType.KE_BUY || mMenuType != MenuType.KE_RENT) {
            bannerHolder = new BannerHolder(this);
            mFrBanner.addView(bannerHolder.getContentView());//轮播
        }


        //TODO to(客源notes)在给remark赋值的时候，获取可以通过服务器返回的type字段来判断是租房进来的还是购房客户进来的。不过其实还好，json的key应该都是remarks，只不过特征的Holder需要做个处理

        if (mMenuType == MenuType.RECENT_DEAL) {
            HouseDealFeature dealFeature = new HouseDealFeature(this);
            mFrHouseFeature.addView(dealFeature.getContentView());
        } else {
            featureHolder = new HouseFeatureHolder(this, mMenuType);
            mFrHouseFeature.addView(featureHolder.getContentView());//房源特征 TODO 最新交易记录（和客源，客源只有补充说明和简介）有点大不同 只有（banner+feature+小区价格走势）
        }

        if (mMenuType != MenuType.RECENT_DEAL && (mMenuType != MenuType.KE_BUY || mMenuType != MenuType.KE_RENT)) {// 客源也没有这个holder
            introHolder = new HouseIntroHolder(this);
            mFrHouseIntro.addView(introHolder.getContentView());//房源介绍
        }


        if (mMenuType == MenuType.SENCOND_HAND) {
            trendChartHolder = new TrendChartHolder(this);
            zuFangInDetailsHolder = new ZuFangInDetailsHolder(this);
            secondHandHouseInDetailsHolder = new SecondHandHouseInDetailsHolder(this);
            dealInDetailsHolder = new HouseDealInDetailsHolder(this);

            mFrGardenPrice.addView(trendChartHolder.getContentView());//小区价格走势
            mFrZufang.addView(zuFangInDetailsHolder.getContentView());//小区租房
            mFrSecondHouse.addView(secondHandHouseInDetailsHolder.getContentView());//小区二手房
            mFrHouseDeal.addView(dealInDetailsHolder.getContentView());//小区成交记录
        } else if (mMenuType == MenuType.RECENT_DEAL) {
            trendChartHolder = new TrendChartHolder(this);
            mFrGardenPrice.addView(trendChartHolder.getContentView());//小区价格走势

            dealInDetailsHolder = new HouseDealInDetailsHolder(this);
            mFrHouseDeal.addView(dealInDetailsHolder.getContentView());//小区成交记录
        }


        if (mMenuType == MenuType.KE_BUY || mMenuType == MenuType.KE_RENT) {//加一个补充说明的Holde
            remarkHolder = new RemarkHolder(this);
            mFrRemark.addView(remarkHolder.getContentView());
        }


        // 为了保证间距，需要判断framlayout有没有子孩子，没有的话gone掉

        frVisiable(mFrBanner).frVisiable(mFrGardenPrice).frVisiable(mFrHouseDeal).frVisiable(mFrHouseFeature)
                .frVisiable(mFrHouseIntro).frVisiable(mFrRemark).frVisiable(mFrSecondHouse).frVisiable(mFrZufang);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        if (mMenuType == MenuType.SENCOND_HAND || mMenuType == MenuType.RECENT_DEAL) {
//            featureHolder.setData();
            ((TrendChartHolder) trendChartHolder).setLineDataNum(2);
            ((TrendChartHolder) trendChartHolder).setTitle("小区价格走势");
            ((TrendChartHolder) trendChartHolder).setLocationVisibility(false);
            trendChartHolder.setData();
        }
    }


    public void contact(View v) {
        startActivity(new Intent(this, ContactActivity.class));
    }


    private HouseDetailsActivity frVisiable(FrameLayout fr) {
        fr.setVisibility(fr.getChildCount() == 0 ? View.GONE : View.VISIBLE);
        return this;
    }
}
