package cn.com.gxdgroup.angentbible.holder.impl.chart;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.util.ArrayList;

import butterknife.BindView;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.ui.MyMarkerView;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/26.
 *
 * @description: 所有Chart的Holder
 */

public class CommonChartHolder extends BaseHolder {


    @BindView(R.id.tv_yaxis_left)
    TextView mTvYaxisLeft;
    @BindView(R.id.tv_yaxis_right)
    TextView mTvYaxisRight;
    @BindView(R.id.rl_yaxis_name)
    RelativeLayout mRlYaxisName;
    @BindView(R.id.chart)
    CombinedChart mChart;
    @BindView(R.id.tv_line1_legend)
    TextView mTvLine1Legend;
    @BindView(R.id.tv_line2_legend)
    TextView mTvLine2Legend;
    @BindView(R.id.tv_bar_legend)
    TextView mTvBarLegend;
    @BindView(R.id.ll_char_legend)
    LinearLayout mLlCharLegend;
    @BindView(R.id.tv_char_title)
    TextView mTvCharTitle;
    @BindView(R.id.tv_second_title)
    TextView mTvSecondTitle;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.tv_unit)
    TextView mTvUnit;
    @BindView(R.id.tv_rate)
    TextView mTvRate;
    @BindView(R.id.iv_rate_arrow)
    ImageView mIvRateArrow;
    @BindView(R.id.rl_rate)
    RelativeLayout mRlRate;
    @BindView(R.id.ll_char_title)
    LinearLayout mLlCharTitle;

    private ChartType mChartType = ChartType.BAR;//默认是柱状图

    public enum ChartType {
        BAR, LINE, LINE2, BAR_LINE, BAR_LINE2, LINE_TITLE
    }

    public CommonChartHolder(FragmentActivity activity, ChartType chartType) {
        super(activity);
        this.mChartType = chartType;
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_common_chart);
    }


    @Override
    public View getContentView() {

        //1.initChar

        int gridColorId = Color.rgb(239, 239, 239);//分割线的颜色
        int labelTextColor = Color.rgb(112, 112, 112);//x y周的label的颜色

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
        mChart.animateY(2000, Easing.EasingOption.EaseInSine); // 加载动画-图4
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        }); // draw bars behind lines
        MyMarkerView mv = new MyMarkerView(UIUtils.getContext(), R.layout.custom_marker_view);// set the marker to the chart
        mChart.setMarkerView(mv);

        //2.标注
        Legend l = mChart.getLegend();
        l.setEnabled(false);
        //l.setTextColor();
        //l.setWordWrapEnabled(true);
        //l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        //3.X轴
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

        //xAxis.setAvoidFirstLastClipping(true);//如果设置为true，则在绘制时会避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项。


        //4. Y轴 左边

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
        leftAxis.setLabelCount(6);
        //xAxis.setAvoidFirstLastClipping(true);//如果设置为true，则在绘制时会避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项。

        return super.getContentView();
    }

    @Override
    public void setData() {
        final String[] xAxisLabels = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};//x轴的内容

    }

    public void setData(final String[] xAxisLabels2) {
        final String[] xAxisLabels = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};//x轴的内容
        XAxis xAxis = mChart.getXAxis();
        xAxis.setLabelCount(xAxisLabels.length);
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


        CombinedData data = new CombinedData();

        int gridColorId = Color.rgb(239, 239, 239);//分割线的颜色
        int labelTextColor = Color.rgb(112, 112, 112);//x y周的label的颜色


        // 根据ChartType 来判断显示的表格类型
        if (mChartType == ChartType.BAR) {//柱状图-1
            mTvLine1Legend.setVisibility(View.GONE);
            mTvLine2Legend.setVisibility(View.GONE);

            data.setData(generateBarData(20, xAxisLabels.length));
        } else if (mChartType == ChartType.LINE) {//折线图-1
            mTvLine2Legend.setVisibility(View.GONE);
            mTvBarLegend.setVisibility(View.GONE);

            data.setData(generateLineData(R.color.common_blue, xAxisLabels.length));
        } else if (mChartType == ChartType.LINE_TITLE) {// 折线图+TITLE
            mTvLine2Legend.setVisibility(View.GONE);
            mTvBarLegend.setVisibility(View.GONE);
            mLlCharTitle.setVisibility(View.VISIBLE);

            YAxis right = mChart.getAxisRight();
            right.setEnabled(false);

            data.setData(generateLineData(R.color.common_blue, xAxisLabels.length));
        } else if (mChartType == ChartType.BAR_LINE) {//柱状-1 + 折线-1
            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setGranularity(1f);
            rightAxis.setGridColor(gridColorId);
            rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
            rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            rightAxis.setTextColor(labelTextColor);//设置轴标签的颜色。
            rightAxis.setLabelCount(5);
            rightAxis.setDrawAxisLine(false);// 设置为true，则绘制网格线。
//            rightAxis.setAxisMaxValue(100);
//            rightAxis.setAxisMinValue(0);
            mTvLine2Legend.setVisibility(View.GONE);

            data.setData(generateBarData(20, xAxisLabels.length));
            data.setData(generateLineData(R.color.common_blue, xAxisLabels.length));

        } else if (mChartType == ChartType.BAR_LINE2) {//柱状-1 + 折线-2
            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setGranularity(1f);
            rightAxis.setGridColor(gridColorId);
            rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
            rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            rightAxis.setTextColor(labelTextColor);//设置轴标签的颜色。
            rightAxis.setLabelCount(5);
            rightAxis.setDrawAxisLine(false);// 设置为true，则绘制网格线。
            rightAxis.setAxisMaxValue(100);
            rightAxis.setAxisMinValue(0);

            data.setData(generateBarData(20, xAxisLabels.length));
            data.setData(generateLineData(UIUtils.getColor(R.color.common_blue), xAxisLabels.length));
            data.setData(generateLineData(UIUtils.getColor(R.color.common_orange), xAxisLabels.length));
        }


        mChart.getXAxis().setAxisMaxValue(data.getXMax() + 0.25f);
        mChart.getXAxis().setAxisMinValue(0);
        mChart.getAxisRight().setAxisMaxValue(data.getXMax() + 0.25f);
        mChart.getAxisRight().setAxisMinValue(0);

        mChart.setData(data);
        mChart.invalidate();
    }


    private LineData generateLineData(int lineColorId, int dataNums) {
        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int index = 0; index < dataNums; index++)
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
        d.addDataSet(set);
        return d;
    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }


    private BarData generateBarData(int range, int dataNums) {
        int barColor = Color.rgb(214, 237, 255);
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        float start = 0f;
        for (int i = (int) start; i < dataNums; i++) {
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

    //------------------一些set方法----------------
    public CommonChartHolder setTvYaxisLeft(String msg) {
        mTvYaxisLeft.setText(msg);
        return this;
    }

    public CommonChartHolder setTvYaxisRight(String msg) {
        mTvYaxisRight.setText(msg);
        return this;
    }

    public CommonChartHolder setTvLine1Legend(String msg) {
        mTvLine1Legend.setText(msg);
        return this;
    }

    public CommonChartHolder setTvLine2Legend(String msg) {
        mTvLine2Legend.setText(msg);
        return this;
    }

    public CommonChartHolder setTvBarLegend(String msg) {
        mTvBarLegend.setText(msg);
        return this;
    }

    public CommonChartHolder setChartTitle(String msg) {
        mTvCharTitle.setText(msg);
        return this;
    }
}
