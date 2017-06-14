package cn.com.gxdgroup.angentbible.activities.temp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import cn.com.gxdgroup.angentbible.db.UserEntity;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;
import cn.com.gxdgroup.angentbible.utils.DateUtils;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * 每月生产情况
 */
public class MonthProduceActivity extends BaseActivity {

    @BindView(R.id.titleView)
    AppTitleView mTitleView;
    @BindView(R.id.spinner_name)
    Spinner spinnerName;
    ArrayAdapter mUserAdapter;
    DataAdapter mListAdapter;
    @BindView(R.id.lv_data)
    ListView lvData;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.ll_produce_month)
    LinearLayout llProduceMonth;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_satrttime_1)
    TextView tvSatrttime1;
    @BindView(R.id.tv_endtime_1)
    TextView tvEndtime1;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    private ArrayList<String> mUserData;
    private ArrayList<ProductEntity> mListData;
    private String searchUserName = "全部";


    long startUnixTime0, endUnixtTime0;//月份
    long startUnixTime1, endUnixtTime1;//范围

    int searchType = 0;//搜索类型 0：月份搜索 1：范围搜索

    public static void startActivity(Activity a) {
        Intent intent = new Intent(a, MonthProduceActivity.class);
        a.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_month_produce);
    }

    @Override
    public void initView() {
        mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                .setTitleMsg("月生产情况")
                .setListener(new SimpleAppTitleListener(MonthProduceActivity.this) {
                    @Override
                    public void onBack() {
                        finish();
                    }
                });


        mUserData = new ArrayList<>();
        mUserAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mUserData);
        mUserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerName.setAdapter(mUserAdapter);
        spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchUserName = mUserData.get(position);
                if (searchType == 0)
                    handleSearch0TimeAndSearch();
                else
                    handleSearch1TimeAndSearch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mListData = new ArrayList<ProductEntity>();
        mListAdapter = new DataAdapter(this, mListData, R.layout.item_data_show);
        lvData.setAdapter(mListAdapter);


        // 给tvMonth tvStarTime tvEndTime 赋值
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);

        DecimalFormat format = new DecimalFormat("00");
        String m2 = format.format(m);
        String d2 = format.format(d);
        String s = y + "-" + m2;
        tvMonth.setText(s);
        tvEndtime1.setText(s);
        tvSatrttime1.setText(s);


        handleSearch0Time();//给startUnixTime0，endUnixTime0赋值
        handleSearch1Time();//startUnixTime1，endUnixtTime1赋值


        handleSearch0TimeAndSearch();//第一次进来默认加载显示按月份搜索的数据

    }

    /**
     * 处理月份搜索
     */
    private void handleSearch0Time() {
        String[] split = tvMonth.getText().toString().trim().split("-");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.valueOf(split[0]));
        c.set(Calendar.MONTH, Integer.valueOf(split[1]) - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        L.v("--" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
        startUnixTime0 = getCurrentUnixTime(c);

        String[] split2 = tvMonth.getText().toString().trim().split("-");
        c.set(Calendar.YEAR, Integer.valueOf(split2[0]));
        c.set(Calendar.MONTH, Integer.valueOf(split2[1]) - 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        L.v("--" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
        endUnixtTime0 = getCurrentUnixTime(c);

    }

    private void handleSearch1Time() {
        String[] split = tvSatrttime1.getText().toString().trim().split("-");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.valueOf(split[0]));
        c.set(Calendar.MONTH, Integer.valueOf(split[1]) - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        L.v("--" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
        startUnixTime1 = getCurrentUnixTime(c);


        String[] split2 = tvEndtime1.getText().toString().trim().split("-");
        c.set(Calendar.YEAR, Integer.valueOf(split2[0]));
        c.set(Calendar.MONTH, Integer.valueOf(split2[1]) - 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        L.v("--" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
        endUnixtTime1 = getCurrentUnixTime(c);

    }


    @Override
    protected void loadData() {
        super.loadData();
        queryAllPeople();
    }

    private void queryAllPeople() {

        // 查询所有用户给Spnaner赋值
        Select select = new Select();
        List<UserEntity> list = select.from(UserEntity.class).execute();
        if (list.size() > 0) {
            for (UserEntity u : list) {
                L.v("query people:" + u.toString());
                mUserData.add(u.getUsername());
            }
            mUserData.add(0, "全部");
            mUserAdapter.notifyDataSetChanged();
        }

        L.v("--------------------------------------------");
        Select select2 = new Select();
        List<ProductEntity> list2 = select2.from(ProductEntity.class).execute();

        for (ProductEntity p : list2) {
            L.v("query produce:" + p.toString());
        }
    }


    /**
     * 选择生产日期（月份）
     */
    private void pickSingleMonth() {
        boolean[] showType = {true, true, false, false, false, false};
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
                String s = y + "-" + m2;
                tvMonth.setText(s);

                handleSearch0TimeAndSearch();

            }
        })
                .setType(showType)
                .build();
        pvTime.setDate(Calendar.getInstance());
        pvTime.show();
    }


    @OnClick({R.id.ll_produce_month, R.id.tv_search, R.id.tv_satrttime_1, R.id.tv_endtime_1, R.id.tv_search0})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_produce_month://选择月份
                pickSingleMonth();
                break;
            case R.id.tv_search0://搜索月份
                handleSearch0TimeAndSearch();
                break;
            case R.id.tv_satrttime_1://选择范围月份
                pickMonth(0);
                break;
            case R.id.tv_endtime_1://选择范围月份
                pickMonth(1);
                break;
            case R.id.tv_search://搜索范围
                handleSearch1TimeAndSearch();
                break;
        }
    }

    // 范围搜索
    private void handleSearch1TimeAndSearch() {
        searchType = 1;
        handleSearch1Time();
        search(startUnixTime1, endUnixtTime1);

    }

    //月份搜索
    private void handleSearch0TimeAndSearch() {
        searchType = 0;
        handleSearch0Time();
        search(startUnixTime0, endUnixtTime0);
    }

    private void search(long startTime, long endTime) {

        if (startTime == 0 || endTime == 0 || searchUserName == null) {
            return;
        }

        if (startTime > endTime) {
            UIUtils.showToast("查询的截止日期不能大于起始日期");
            return;
        }

        List<ProductEntity> resultList;
        Select select = new Select();
        if (TextUtils.equals("全部", searchUserName)) {
            resultList = select.from(ProductEntity.class).where("dateUnixTime <= ?", endTime).and("dateUnixTime >= ?", startTime).execute();
        } else {// 搜索特定的人的生产量
            resultList = select.from(ProductEntity.class).where("dateUnixTime <= ?", endTime).and("dateUnixTime >= ?", startTime).and("productUsername = ?", searchUserName).execute();
        }

        if (resultList == null) {
            UIUtils.showToast("没有记录");
            return;
        }

        if (resultList.size() > 0) {
            ArrayList<ProductEntity> dataList = new ArrayList<>();
            for (int i = 0; i < resultList.size(); i++) {
                boolean hasThisData = false;
                ProductEntity r = resultList.get(i);//全部结果
                L.v("--全部结果--" + r.toString());
                for (int j = 0; j < dataList.size(); j++) {
                    ProductEntity d = dataList.get(j);
                    if (TextUtils.equals(d.getProductUsername(), r.getProductUsername())) {
                        hasThisData = true;
                        L.v("--已经存了--" + d.getProductUsername());
                        BigDecimal add = new BigDecimal(r.getProductTotalPrice()).add(new BigDecimal(d.getProductTotalPrice()));
                        d.setProductTotalPrice(add.stripTrailingZeros().toPlainString());
                    }

                }

                if (!hasThisData) {
                    dataList.add(r);
                }
            }


            mListData.clear();
            mListData.addAll(dataList);
            mListAdapter.notifyDataSetChanged();

            //计算合计
            BigDecimal sumBig = new BigDecimal("0");
            for (ProductEntity p : mListData) {
                sumBig = sumBig.add(new BigDecimal(p.getProductTotalPrice()));
            }
            tvSum.setText(sumBig.stripTrailingZeros().toPlainString());
            if (mListData.size() == 0)
                UIUtils.showToast("没有记录");
        } else {//没有数据
            mListData.clear();
            mListAdapter.notifyDataSetChanged();
            tvSum.setText("0");
            UIUtils.showToast("没有记录");
        }
    }


    private long getCurrentUnixTime(Calendar c) {
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        DecimalFormat df = new DecimalFormat("00");
        String m2 = df.format(m);
        String d2 = df.format(d);

        return DateUtils.string2UnixTime(y + "-" + m2 + "-" + d2 + " 00:00:00");
    }

    private void pickMonth(final int flag) {
        boolean[] showType = {true, true, false, false, false, false};
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
                String s = y + "-" + m2;
                if (flag == 0) {
                    tvSatrttime1.setText(s);
                    c.set(Calendar.DAY_OF_MONTH, 1);

                } else {
                    tvEndtime1.setText(s);
                    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                }


            }
        })
                .setType(showType)
                .build();
        pvTime.setDate(Calendar.getInstance());
        pvTime.show();
    }


    class DataAdapter extends CommonAdapter<ProductEntity> {

        public DataAdapter(Context context, List<ProductEntity> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder holder, ProductEntity item) {
            holder.setText(R.id.tv_username, item.getProductUsername());
            String sDate = "";
            if (searchType == 0) {
                sDate = tvMonth.getText().toString().trim();
            } else {
                sDate = tvSatrttime1.getText().toString().trim() + " 至 " + tvEndtime1.getText().toString().trim();
            }

            holder.setText(R.id.tv_producedata, sDate);
            holder.setText(R.id.tv_total_price, item.getProductTotalPrice());
        }
    }

}
