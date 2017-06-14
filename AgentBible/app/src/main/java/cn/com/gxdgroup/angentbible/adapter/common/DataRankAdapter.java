package cn.com.gxdgroup.angentbible.adapter.common;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;
import java.util.Random;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.impl.chart.CommonChartHolder;

/**
 * Created by Ivy on 2016/10/24.
 *
 * @description:
 */

public class DataRankAdapter extends CommonAdapter<String> {
    public DataRankAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(final ViewHolder holder, String item) {
        holder.setText(R.id.tv_rank_number, item);
        holder.setText(R.id.tv_number, new Random().nextInt(1000) + "套");
//        holder.setOnClickListener(R.id.iv_arrow, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int visibility = holder.getView(R.id.fr_chart).getVisibility();
//                if (visibility == View.GONE) {
//                    holder.getView(R.id.fr_chart).setVisibility(View.VISIBLE);
//                    FrameLayout frChart = holder.getView(R.id.fr_chart);
//                    CommonChartHolder chartHolder = new CommonChartHolder(null, CommonChartHolder.ChartType.BAR_LINE);
//                    frChart.removeAllViews();
//                    frChart.addView(chartHolder.getContentView());
//                    chartHolder.setData(null);
//                } else {
//                    holder.getView(R.id.fr_chart).setVisibility(View.GONE);
//                }
//            }
//        });
    }
}
