package cn.com.gxdgroup.angentbible.activities.temp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.adapter.common.CommonAdapter;
import cn.com.gxdgroup.angentbible.adapter.common.ViewHolder;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.db.ProductEntity;
import cn.com.gxdgroup.angentbible.db.UserEntity;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;
import cn.com.gxdgroup.angentbible.utils.L;

/**
 * 每月生产情况
 */
public class MonthProduceActivity2 extends BaseActivity {

    @BindView(R.id.titleView)
    AppTitleView mTitleView;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.spinner2)
    Spinner spinnerMonth;

    ArrayAdapter mUserAdapter, mMonthAdapter;
    DataAdapter mListAdapter;
    @BindView(R.id.lv_data)
    ListView lvData;
    @BindView(R.id.tv_summarize)
    TextView tvSummarize;
    private ArrayList<String> mUserData, mMonthData;
    private ArrayList<ProductEntity> mListData;
    String searchUserName, searchMonth;

    public static void startActivity(Activity a) {
        Intent intent = new Intent(a, MonthProduceActivity2.class);
        a.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_data_show);
    }

    @Override
    public void initView() {
        mTitleView.showMode(AppTitleView.MODE.TITLE_R_IV, -1, null)
                .setTitleMsg("月生产情况")
                .setVisible(true, mTitleView.getTvTitle())
                .setListener(new SimpleAppTitleListener(MonthProduceActivity2.this) {
                    @Override
                    public void onBack() {
                        finish();
                    }

                    @Override
                    public void OnivRight() {
                        ProduceDetailsActivity.startActivity(MonthProduceActivity2.this);
                    }
                });


        //适配器
        mUserData = new ArrayList<>();
        mUserAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mUserData);
        //设置样式
        mUserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(mUserAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchUserName = mUserData.get(position);

                search(searchUserName, searchMonth);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mMonthData = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            mMonthData.add("" + i);
        }
        mMonthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mMonthData);
        //设置样式
        mMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinnerMonth.setAdapter(mMonthAdapter);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchMonth = mMonthData.get(position);
                search(searchUserName, searchMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mListData = new ArrayList<ProductEntity>();

        // mListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mListData);


        mListAdapter = new DataAdapter(this, mListData, R.layout.item_data_show);
        lvData.setAdapter(mListAdapter);

    }

    private void search(String searchUserName, String searchMonth) {

        L.v("searchUserName:" + searchUserName + "," + searchMonth);

        if (!TextUtils.isEmpty(searchMonth) && !TextUtils.isEmpty(searchUserName)) {
            DecimalFormat df = new DecimalFormat("00");
            searchMonth = df.format(Integer.valueOf(searchMonth));
            L.v("searchUserName format:" + searchUserName + "," + searchMonth);

            Select select = new Select();
            List<ProductEntity> list;

            list = select.from(ProductEntity.class).where("productUsername = ?", searchUserName).and("productMonth = ?", searchMonth).execute();


            mListData.clear();
            for (ProductEntity p : list) {
//                mListData.add(p.getProductUsername() + "," + p.getProductMonth() + "," + p.getProductTotalPrice());
                if (Double.valueOf(p.getProductTotalPrice()) > 0)
                    mListData.add(p);
            }

            mListAdapter.notifyDataSetChanged();

            // 判断时候有数据
            if (mListData.size() > 0) {
                BigDecimal sum = new BigDecimal(0);
                for (int i = 0; i < mListData.size(); i++) {
                    String p = mListData.get(i).getProductTotalPrice();
                    sum = new BigDecimal(p).add(sum);
                }

                String s = searchUserName + "在" + searchMonth + "月份生产总值是 " + sum.stripTrailingZeros().toPlainString();
                tvSummarize.setText(s);
            } else {
                tvSummarize.setText(searchUserName + "在" + searchMonth + "月份生产总值是 0");
            }
        }
    }


    @Override
    protected void loadData() {
        super.loadData();
        queryAllPeople();
    }

    private void queryAllPeople() {
        Select select = new Select();
        List<UserEntity> list = select.from(UserEntity.class).execute();
        if (list.size() > 0) {
            for (UserEntity u : list) {
                L.v("query all:" + u.toString());
                mUserData.add(u.getUsername());
                mUserAdapter.notifyDataSetChanged();
            }
        }

        L.v("--------------------------------------------");

        Select select2 = new Select();
        List<ProductEntity> list2 = select2.from(ProductEntity.class).execute();

        for (ProductEntity p : list2) {
            L.v("query produce:" + p.toString());
        }
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
