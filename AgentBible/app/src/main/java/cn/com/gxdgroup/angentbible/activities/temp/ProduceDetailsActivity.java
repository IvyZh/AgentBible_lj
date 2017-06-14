package cn.com.gxdgroup.angentbible.activities.temp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.bigkoo.pickerview.TimePickerView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.adapter.common.CommonAdapter;
import cn.com.gxdgroup.angentbible.adapter.common.ViewHolder;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.db.ProductEntity;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;
import cn.com.gxdgroup.angentbible.utils.DateUtils;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * 生产明细表
 */
public class ProduceDetailsActivity extends BaseActivity {

    @BindView(R.id.titleView)
    AppTitleView mTitleView;

    DataAdapter mListAdapter;
    @BindView(R.id.lv_data)
    ListView lvData;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_satrttime)
    TextView tvSatrttime;
    @BindView(R.id.tv_endtime)
    TextView tvEndtime;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    private ArrayList<ProductEntity> mListData;

    long startUnixTime, endUnixTime;

    public static void startActivity(Activity a) {
        Intent intent = new Intent(a, ProduceDetailsActivity.class);
        a.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_all_data_show);
    }

    @Override
    public void initView() {
        mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                .setTitleMsg("生产明细查询")
                .setListener(new SimpleAppTitleListener(ProduceDetailsActivity.this) {
                    @Override
                    public void onBack() {
                        finish();
                    }
                });


        mListData = new ArrayList<ProductEntity>();
        mListAdapter = new DataAdapter(this, mListData, R.layout.item_data_show);
        lvData.setAdapter(mListAdapter);


        String currentCalendar = getCurrentCalendar();
        tvSatrttime.setText(currentCalendar);
        tvEndtime.setText(currentCalendar);


        startUnixTime = DateUtils.string2UnixTime(DateUtils.date2String(new Date()) + " 00:00:00");
        endUnixTime = startUnixTime;
        L.v("--startUnixTime--" + startUnixTime);

    }


    @OnClick({R.id.tv_satrttime, R.id.tv_endtime, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_satrttime:
                pickTime(0);
                break;
            case R.id.tv_endtime:
                pickTime(1);
                break;
            case R.id.tv_search:
                search();
                break;
        }
    }

    private void search() {

        L.v("--search--" + startUnixTime + "-" + endUnixTime);

        if (endUnixTime < startUnixTime) {
            UIUtils.showToast("查询的截止日期不能大于起始日期");
            return;
        }
        Select select = new Select();
        List<ProductEntity> resultList = select.from(ProductEntity.class).where("dateUnixTime <= ?", endUnixTime).and("dateUnixTime >= ?", startUnixTime).execute();


        showThoseData(resultList);
        // 判断时候有数据
        if (resultList.size() > 0) {
            BigDecimal sum = new BigDecimal(0);
            for (int i = 0; i < resultList.size(); i++) {
                String p = resultList.get(i).getProductTotalPrice();
                sum = new BigDecimal(p).add(sum);
            }
            tvSum.setText(sum.stripTrailingZeros().toPlainString());
        } else {
            tvSum.setText("0");
        }
    }


    private String getCurrentCalendar() {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        DecimalFormat format = new DecimalFormat("00");
        String m2 = format.format(m);
        String d2 = format.format(d);
        String s = y + "年" + m2 + "月" + d2 + "日";
        return s;
    }

    /**
     * 让List列表显示给定的数据
     *
     * @param resultList
     */
    private void showThoseData(List<ProductEntity> resultList) {
        mListData.clear();
        mListData.addAll(resultList);
        mListAdapter.notifyDataSetChanged();
    }

    private void pickTime(final int flag) {
        boolean[] showType = {true, true, true, false, false, false};
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Calendar c = Calendar.getInstance();
                c.setTime(date);

                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH) + 1;
                int d = c.get(Calendar.DAY_OF_MONTH);

                DecimalFormat format = new DecimalFormat("00");
                String m2 = format.format(m);
                String d2 = format.format(d);
                String s = y + "年" + m2 + "月" + d2 + "日";


                if (flag == 0) {
//                    tvSatrttime.setText(DateUtils.date2StringFull(date));
                    tvSatrttime.setText(s);
                    startUnixTime = date.getTime() / 1000;
                } else {
//                    tvEndtime.setText(DateUtils.date2StringFull(date));
                    tvEndtime.setText(s);
                    endUnixTime = date.getTime() / 1000;
                }
            }
        })
                .setType(showType)
                .build();
        pvTime.setDate(Calendar.getInstance());
        //注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }


    class DataAdapter extends CommonAdapter<ProductEntity> {

        public DataAdapter(Context context, List<ProductEntity> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder holder, ProductEntity item) {
            holder.setText(R.id.tv_username, item.getProductUsername());
            holder.setText(R.id.tv_producedata, item.getProductDate());
            holder.setText(R.id.tv_total_price, item.getProductTotalPrice());
        }
    }

}
