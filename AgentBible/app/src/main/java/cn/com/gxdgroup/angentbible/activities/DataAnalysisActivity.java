package cn.com.gxdgroup.angentbible.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.adapter.common.DataRankAdapter;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.holder.impl.chart.CommonChartHolder;
import cn.com.gxdgroup.angentbible.holder.impl.home.TrendChartHolder;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;
import cn.com.gxdgroup.angentbible.ui.ObservableScrollView;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/22.
 *
 * @description: 具体的某一个数据分析 全市分析..人们区域 热门板块..端口...
 */

public class DataAnalysisActivity extends BaseActivity {


    @BindView(R.id.titleView)
    AppTitleView mTitleView;
    @BindView(R.id.tv_list_title)
    TextView mTvListTitle;
    @BindView(R.id.tab_month_year)
    LinearLayout mTabMonthYear;
    @BindView(R.id.rl_list_title)
    RelativeLayout mRlListTitle;
    @BindView(R.id.lv_data)
    ListView mLvData;
    @BindView(R.id.ll_tite_listview)
    LinearLayout mLlTiteListview;
    @BindView(R.id.fr_deal_analysis)
    FrameLayout mFrDealAnalysis;
    @BindView(R.id.fr_deal_volume_analysis)
    FrameLayout mFrDealVolumeAnalysis;
    @BindView(R.id.fr_quoted_analysis)
    FrameLayout mFrQuotedAnalysis;
    @BindView(R.id.fr_pv_analysis)
    FrameLayout mFrPvAnalysis;
    @BindView(R.id.sv_city_ana)
    ObservableScrollView mSvCityAna;
    @BindView(R.id.tv_city_ana)
    TextView mTvCityAna;
    @BindView(R.id.tv_hot_area)
    TextView mTvHotArea;
    @BindView(R.id.tv_hot_bankuai)
    TextView mTvHotBankuai;
    @BindView(R.id.tv_hot_garden)
    TextView mTvHotGarden;
    @BindView(R.id.ll_bottom_tab)
    LinearLayout mLlBottomTab;
    @BindView(R.id.cb_month)
    RadioButton mCbMonth;
    @BindView(R.id.cb_year)
    RadioButton mCbYear;
    @BindView(R.id.tv_rank_date)
    TextView mTvRankDate;
    private int mDataType;//数据分析的类型
    private boolean isAddCityView = false;

    public static void startActivity(Activity ba, int dataType) {
        Intent intent = new Intent(ba, DataAnalysisActivity.class);
        intent.putExtra("dataType", dataType);
        ba.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        mDataType = getIntent().getIntExtra("dataType", 0);
        setContentView(R.layout.activity_data_analysis);

    }

    @Override
    public void initView() {


        mTitleView.setListener(new SimpleAppTitleListener(this) {
            @Override
            public void onTabLeft() {
                loadData();
                mCbMonth.setChecked(true);
            }

            @Override
            public void onTabRight() {
                loadData();
                mCbMonth.setChecked(true);
            }
        });

        mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                .setTitleMsg("全市分析");

        if (mDataType <= 3) {
            mLlBottomTab.setVisibility(View.VISIBLE);
            mSvCityAna.setVisibility(View.VISIBLE);
            mLlTiteListview.setVisibility(View.GONE);
        } else {
            mLlBottomTab.setVisibility(View.GONE);
        }


        if (mDataType == 0) {
            //全市分析
            if (!isAddCityView) {
                prepareAddCityView();
            }


        } else if (mDataType >= 1 && mDataType <= 3) {//热门区域 热门板块 热门小区
            mSvCityAna.setVisibility(View.GONE);
            mLlTiteListview.setVisibility(View.VISIBLE);
            mTitleView.showMode(AppTitleView.MODE.TAB, -1, null)
                    .setTabLeftMsg("成交套数").setTabRightMsg("成交总价");
        } else if (mDataType == 4) {//发布量
            mSvCityAna.setVisibility(View.GONE);
            mLlTiteListview.setVisibility(View.VISIBLE);
            mTitleView.showMode(AppTitleView.MODE.TAB, -1, null)
                    .setTabLeftMsg("二手房").setTabRightMsg("租房");
        } else if (mDataType == 5) {//网站经纪人数
            mSvCityAna.setVisibility(View.GONE);
            mLlTiteListview.setVisibility(View.VISIBLE);
            mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                    .setTitleMsg("网站经纪人数");
        } else if (mDataType == 6) {// 签约量
            mSvCityAna.setVisibility(View.GONE);
            mLlTiteListview.setVisibility(View.VISIBLE);
            mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                    .setTitleMsg("签约量");
        } else if (mDataType == 7) {//端口数
            mSvCityAna.setVisibility(View.GONE);
            mLlTiteListview.setVisibility(View.VISIBLE);
            mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                    .setTitleMsg("端口数");
        }


        mSvCityAna.setScrollListener(new ObservableScrollView.ScrollListener() {
            @Override
            public void scrollOritention(int oritention) {
                L.v("scroll--" + oritention);
                if (oritention == 16) {//显示
                    mLlBottomTab.setVisibility(View.VISIBLE);
                } else {//隐藏
                    mLlBottomTab.setVisibility(View.GONE);
                }
            }
        });

        mLvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {

                if (mDataType != 3) {
                    FrameLayout frChart = (FrameLayout) view1.findViewById(R.id.fr_chart);
                    int visibility = frChart.getVisibility();
                    if (visibility == View.GONE) {
                        CommonChartHolder chartHolder;
                        if (frChart.getChildCount() == 0) {
                            chartHolder = new CommonChartHolder(null, CommonChartHolder.ChartType.BAR_LINE);
                            frChart.addView(chartHolder.getContentView());
                            frChart.setTag(chartHolder);
                        } else {
                            chartHolder = (CommonChartHolder) frChart.getTag();
                        }

                        chartHolder.setData(null);
                        frChart.setVisibility(View.VISIBLE);
                    } else {
                        frChart.setVisibility(View.GONE);
                    }
                } else {
                    View chartContainer = view1.findViewById(R.id.ll_charts_container);
                    int visibility = chartContainer.getVisibility();
                    if (visibility == View.GONE) {
                        FrameLayout frChart1 = (FrameLayout) view1.findViewById(R.id.fr_chart1);
                        FrameLayout frChart2 = (FrameLayout) view1.findViewById(R.id.fr_chart2);
                        CommonChartHolder chartHolder1, chartHolder2;
                        if (frChart1.getChildCount() == 0) {
                            chartHolder1 = new CommonChartHolder(null, CommonChartHolder.ChartType.BAR_LINE);
                            chartHolder2 = new CommonChartHolder(null, CommonChartHolder.ChartType.LINE);
                            frChart1.addView(chartHolder1.getContentView());
                            frChart2.addView(chartHolder2.getContentView());

                            frChart1.setTag(chartHolder1);
                            frChart2.setTag(chartHolder2);
                        } else {
                            chartHolder1 = (CommonChartHolder) frChart1.getTag();
                            chartHolder2 = (CommonChartHolder) frChart2.getTag();
                        }

                        chartHolder1.setData(null);
                        chartHolder2.setData(null);

                        chartContainer.setVisibility(View.VISIBLE);
                    } else {
                        chartContainer.setVisibility(View.GONE);
                    }
                }
            }
        });


        // 给月和年的CheckBox设置点击事件

        mCbMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean b) {
                if (b) {
                    mTvListTitle.setText("月排名");
                    mTvRankDate.setText("2016-08");
                } else {
                    mTvListTitle.setText("年排名");
                    mTvRankDate.setText("2016");
                }

                loadData();
            }
        });

        int blueColor = UIUtils.getColor(R.color.common_blue);
        switch (mDataType) {
            case 0:
                mTvCityAna.setTextColor(blueColor);
                break;
            case 1:
                mTvHotArea.setTextColor(blueColor);
                break;
            case 2:
                mTvHotBankuai.setTextColor(blueColor);
                break;
            case 3:
                mTvHotGarden.setTextColor(blueColor);
                break;
        }

    }

    private void prepareAddCityView() {
        isAddCityView = true;
        TrendChartHolder dealHolder = new TrendChartHolder(this);//成交分析
        dealHolder.setTitle("成交分析");
        mFrDealAnalysis.addView(dealHolder.getContentView());
        dealHolder.setData();

        CommonChartHolder dealVolumenHolder = new CommonChartHolder(this, CommonChartHolder.ChartType.LINE_TITLE);//成交总金额分析
        dealVolumenHolder.setChartTitle("成交总金额分析");
        mFrDealVolumeAnalysis.addView(dealVolumenHolder.getContentView());
        dealVolumenHolder.setData(null);


        CommonChartHolder quotedHolder = new CommonChartHolder(this, CommonChartHolder.ChartType.LINE_TITLE);//挂牌分析
        quotedHolder.setChartTitle("挂牌分析");
        mFrQuotedAnalysis.addView(quotedHolder.getContentView());
        quotedHolder.setData(null);


        CommonChartHolder pvHolder = new CommonChartHolder(this, CommonChartHolder.ChartType.LINE_TITLE);//浏览量分析
        pvHolder.setChartTitle("浏览量分析");
        mFrPvAnalysis.addView(pvHolder.getContentView());
        pvHolder.setData(null);
    }


    @OnClick({R.id.tv_city_ana, R.id.tv_hot_area, R.id.tv_hot_bankuai, R.id.tv_hot_garden})
    public void onClick(View view) {
        int blueColor = UIUtils.getColor(R.color.common_blue);
        int whiteColor = UIUtils.getColor(R.color.white);

        mTvCityAna.setTextColor(whiteColor);
        mTvHotArea.setTextColor(whiteColor);
        mTvHotBankuai.setTextColor(whiteColor);
        mTvHotGarden.setTextColor(whiteColor);


        switch (view.getId()) {
            case R.id.tv_city_ana:
                if (!isAddCityView) {
                    prepareAddCityView();
                }
                mSvCityAna.setVisibility(View.VISIBLE);
                mDataType = 0;
                mLlTiteListview.setVisibility(View.GONE);
                mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                        .setTitleMsg("全市分析");
                mTvCityAna.setTextColor(blueColor);
                break;
            case R.id.tv_hot_area:
                mSvCityAna.setVisibility(View.GONE);
                mDataType = 1;
                mLlTiteListview.setVisibility(View.VISIBLE);
                mTitleView.showMode(AppTitleView.MODE.TAB, -1, null)
                        .setTabLeftMsg("成交套数").setTabRightMsg("成交总价");
                mTvHotArea.setTextColor(blueColor);
                break;
            case R.id.tv_hot_bankuai:
                mSvCityAna.setVisibility(View.GONE);
                mDataType = 2;
                mLlTiteListview.setVisibility(View.VISIBLE);
                mTitleView.showMode(AppTitleView.MODE.TAB, -1, null)
                        .setTabLeftMsg("成交套数").setTabRightMsg("成交总价");
                mTvHotBankuai.setTextColor(blueColor);
                break;
            case R.id.tv_hot_garden:
                mDataType = 3;
                mSvCityAna.setVisibility(View.GONE);
                mLlTiteListview.setVisibility(View.VISIBLE);
                mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                        .setTitleMsg("热门小区");
                mTvHotGarden.setTextColor(blueColor);
                break;
        }

        loadData();
    }


    @Override
    protected void loadData() {

        // 加载数据这块先暂时写在这个地方 后期再优化

        ArrayList<String> data = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            data.add(i + "");
        }

        if (mDataType == 3) {//热门小区单独处理
            DataRankAdapter adapter = new DataRankAdapter(this, data, R.layout.item_hot_village_rank);
            mLvData.setAdapter(adapter);
        } else {
            DataRankAdapter adapter = new DataRankAdapter(this, data, R.layout.item_rank);
            mLvData.setAdapter(adapter);
        }


    }
}
