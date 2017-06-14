package cn.com.gxdgroup.angentbible.holder.impl.home;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.ui.MyMarkerView;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/17.
 *
 * @description: 所有走势图的Holder
 */

public class TrendChartHolder extends BaseHolder {
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.chart)
    CombinedChart mChart;
    protected String[] xAxisLabels;//x轴的内容
    @BindView(R.id.tv_char_title)
    TextView mTvCharTitle;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_price_rate)
    TextView mTvPriceRate;
    @BindView(R.id.tv_house_num_rate)
    TextView mTvHouseNumRate;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_house_num)
    TextView mTvHouseNum;
    private int lineDataNum = 1;
    private int lineColors[] = {R.color.common_blue, R.color.common_green, R.color.common_orange};
    private ArrayList<String> options1Items = new ArrayList<>();
    private OptionsPickerView pvOptions;

    public TrendChartHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_trend_chart);
    }

    @Override
    public void initView() {

//        Typeface neue = UIUtils.getTypeFaceHelveticaNeue();
//        mTvHouseNum.setTypeface(neue);
//        mTvPrice.setTypeface(neue);
//        mTvHouseNumRate.setTypeface(neue);
//        mTvPriceRate.setTypeface(neue);


        //---------------------initChar--------------------------

        int gridColorId = Color.rgb(239, 239, 239);//分割线的颜色
        int labelTextColor = Color.rgb(112, 112, 112);//x y周的label的颜色
        xAxisLabels = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};//x轴的内容

        mChart.setDescription("");
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(true);//如果启用，chart 绘图区后面的背景矩形将绘制。
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setGridBackgroundColor(Color.WHITE);//设置网格背景应与绘制的颜色。
        mChart.setNoDataText("暂无数据");//设置当 chart 为空时显示的描述文字。
        mChart.setDrawBorders(false);//启用/禁用绘制图表边框（chart周围的线）。
        mChart.setBorderColor(Color.RED);//设置 chart 边框线的颜色。
        mChart.setBorderWidth(2);//设置 chart 边界线的宽度，单位 dp。
        mChart.setTouchEnabled(true); //启用/禁用与图表的所有可能的触摸交互。
        mChart.setDragEnabled(true);
        mChart.setDoubleTapToZoomEnabled(true);// 设置为false以禁止通过在其上双击缩放图表。
        mChart.setScaleYEnabled(false);//启用/禁用缩放在y轴。
        mChart.setPinchZoom(true);//: 如果设置为true，捏缩放功能。 如果false，x轴和y轴可分别放大。

        mChart.animateY(2000, Easing.EasingOption.EaseInSine); // 图4

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });


        MyMarkerView mv = new MyMarkerView(mActivity, R.layout.custom_marker_view);

        // set the marker to the chart
        mChart.setMarkerView(mv);

        Legend l = mChart.getLegend();
        l.setEnabled(false);//标注
//        l.setTextColor();
        l.setWordWrapEnabled(true);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);


        //----Y轴---
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setGranularity(1f);
        rightAxis.setGridColor(gridColorId);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setTextColor(labelTextColor);//设置轴标签的颜色。
        rightAxis.setLabelCount(4);
        rightAxis.setDrawAxisLine(false);// 设置为true，则绘制网格线。
        rightAxis.setAxisMaxValue(100);
//
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置XAxis出现的位置
        leftAxis.setDrawGridLines(true);//设置为true，则绘制网格线。（就是那些竖着的线
        leftAxis.setDrawAxisLine(true);//设置为true，则绘制该行旁边的轴线（axis-line）。
        leftAxis.setDrawLabels(true);//设置为true，则绘制轴的标签。
        leftAxis.setTextColor(labelTextColor);//设置轴标签的颜色。
        //xAxis.setTextSize(float size) : 设置轴标签的文字大小。
        leftAxis.setAxisMinValue(0f);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisLineColor(gridColorId);//设置轴线的轴的颜色。,使用这种引用不行：R.color.grid_color
        leftAxis.setGridColor(gridColorId);//设置该轴的网格线颜色。
        leftAxis.setLabelCount(4);
//        xAxis.setAvoidFirstLastClipping(true);//如果设置为true，则在绘制时会避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项。


        //----X轴---
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置XAxis出现的位置
        xAxis.setDrawGridLines(false);//设置为true，则绘制网格线。（就是那些竖着的线
        xAxis.setDrawAxisLine(true);//设置为true，则绘制该行旁边的轴线（axis-line）。
        xAxis.setDrawLabels(true);//设置为true，则绘制轴的标签。
        xAxis.setTextColor(labelTextColor);//设置轴标签的颜色。
        //xAxis.setTextSize(float size) : 设置轴标签的文字大小。
        //xAxis.setAxisMinValue(0f);
        xAxis.setGranularity(1f);
        xAxis.setAxisLineColor(gridColorId);//设置轴线的轴的颜色。,使用这种引用不行：R.color.grid_color
        xAxis.setGridColor(gridColorId);//设置该轴的网格线颜色。
        xAxis.setLabelCount(xAxisLabels.length);
//        xAxis.setAvoidFirstLastClipping(true);//如果设置为true，则在绘制时会避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项。

        xAxis.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisLabels[(int) value % xAxisLabels.length];
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
    }


    /**
     * 设置折线图的数据
     */
    private LineData generateLineData() {

        LineData d = new LineData();
        for (int i = 0; i < lineDataNum; i++) {
            int lineColorId = UIUtils.getColor(lineColors[i % lineColors.length]);
            d.addDataSet(prepareLineData(lineColorId));
        }


        return d;
    }

    private ILineDataSet prepareLineData(int lineColorId) {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int index = 0; index < xAxisLabels.length; index++)
            entries.add(new Entry(index + 0.25f, getRandom(15, 5)));

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(lineColorId);
        set.setLineWidth(1.5f);
        set.setCircleColor(lineColorId);//周围的颜色
        set.setCircleColorHole(lineColorId);//里面想小圆球颜色
        set.setCircleRadius(3f);
        set.setFillColor(lineColorId);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);//折线(LINEAR) 曲滑折线(CUBIC_BEZIER)

        set.setDrawValues(false);// 启用/禁用 绘制所有 DataSets 数据对象包含的数据的值文本。
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        set.setHighLightColor(Color.TRANSPARENT);// 设置点击某个点时，横竖两条线的颜色

        return set;
    }


    /**
     * 设置柱状图的数据
     *
     * @return
     */
    private BarData generateBarData2() {

        int range = 20;
        int barColor = Color.rgb(214, 237, 255);
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        float start = 0f;
        for (int i = (int) start; i < 12; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(i + 0.25f, val));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Bar 1");
        set1.setColor(barColor);
        set1.setDrawValues(false);
        set1.setValueTextColor(barColor);
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.8f);
        return data;
    }


    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    @Override
    public void setData() {
        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateBarData2());
        //data.setValueTypeface(mTfLight);//设置该 DataSet 的文本的字体（Typeface）。

        mChart.getXAxis().setAxisMaxValue(data.getXMax() + 0.25f);
        mChart.getXAxis().setAxisMinValue(0);

        mChart.setData(data);
        mChart.invalidate();


        // 给成交量 成交rate 平均价 平均价rata赋值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(60000, 86512);
        valueAnimator.setDuration(800);
        //
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int money = (int) animation.getAnimatedValue();
                mTvPrice.setText(money + "");
                mTvHouseNum.setText((money / 12) + "");
            }
        });
        valueAnimator.start();

    }

    public void setLineDataNum(int lineDataNum) {
        this.lineDataNum = lineDataNum;
    }

    public void setTitle(String title) {
        mTvCharTitle.setText(title);
    }

    public void setLocationVisibility(boolean isShow) {
        mIvArrow.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mTvLocation.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.tv_location)
    public void onClick() {

        if (pvOptions == null) {
            final String[] names = {"浦东", "闵行", "徐汇", "长宁", "普陀", "静安", "卢湾", "黄浦", "闸北", "虹口", "杨浦", "宝山", "嘉定", "青浦", "松江", "金山", "奉贤", "南汇", "崇明"};
            //选项选择器
//            pvOptions = new OptionsPickerView(mActivity);
            pvOptions = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    mTvLocation.setText(names[options1] + "区");
                }
            }).setCyclic(false, false, false)
                    .build();

            for (int i = 0; i < names.length; i++) {
                options1Items.add(names[i]);
            }
            pvOptions.setPicker(options1Items);

        }

        pvOptions.show();

    }
}
