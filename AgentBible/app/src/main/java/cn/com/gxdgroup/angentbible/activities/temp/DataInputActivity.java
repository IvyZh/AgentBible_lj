package cn.com.gxdgroup.angentbible.activities.temp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.db.ProductEntity;
import cn.com.gxdgroup.angentbible.db.UserEntity;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;
import cn.com.gxdgroup.angentbible.utils.DateUtils;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.TimeUtils;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * 数据录入页面
 */
public class DataInputActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.titleView)
    AppTitleView mTitleView;
    @BindView(R.id.spinner)
    Spinner spinner;

    ArrayAdapter mUserAdapter;

    List<String> mUserData;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.et_nums)
    EditText etNums;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.bt_commit)
    Button btCommit;

    String inputDate;//信息录入时间

    String selectProUserName;

    public static void startActivity(Activity a) {
        Intent intent = new Intent(a, DataInputActivity.class);
        a.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_data_input);
    }

    @Override
    public void initView() {
        mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                .setTitleMsg("数据录入")
                .setListener(new SimpleAppTitleListener(DataInputActivity.this) {
                    @Override
                    public void onBack() {
                        finish();
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
                selectProUserName = mUserData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // 设置默认显示日期
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));//获取东八区时间
        int year = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        String currentDay = calendar.get(Calendar.DAY_OF_MONTH) + "";// 3
        DecimalFormat df = new DecimalFormat("00");
        String month2 = df.format(currentMonth);
        String day2 = df.format(Integer.valueOf(currentDay));
        inputDate = year + "-" + month2 + "-" + day2;
        tvDate.setText(inputDate);


        // 监听EditText输入

        etNums.addTextChangedListener(this);
        etPrice.addTextChangedListener(this);
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
    }


    @OnClick({R.id.ll_date, R.id.bt_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_date:
                selectDate();
                break;
            case R.id.bt_commit:
                submit();
                break;
        }
    }

    private void submit() {
        // 判断数据是否填写完整
        if (TextUtils.isEmpty(selectProUserName)) {
            UIUtils.showToast("请选择人员");
            return;
        }


        if (TextUtils.isEmpty(etNums.getText().toString().trim())) {
            UIUtils.showToast("请输入产量");
            return;
        }

        if (TextUtils.isEmpty(etPrice.getText().toString().trim())) {
            UIUtils.showToast("请输入单价");
            return;
        }

        String produceDate = tvDate.getText().toString().trim();

        Select select = new Select();
        List<ProductEntity> list = select.from(ProductEntity.class).where("productUsername = ?", selectProUserName).and("productDate = ?", produceDate).execute();

        if (list.size() == 0) {
            ProductEntity entity = new ProductEntity();
            entity.setProductUsername(selectProUserName);
            entity.setProductDate(produceDate);
            entity.setProductNums(etNums.getText().toString().trim());
            entity.setProductPrice(etPrice.getText().toString().trim());
            entity.setProductTotalPrice(tvAllPrice.getText().toString().trim());

            String t = tvDate.getText().toString().trim();
            String[] split = t.split("-");
            entity.setProductYear(split[0]);
            entity.setProductMonth(split[1]);
            entity.setProductDay(split[2]);

            entity.setDateUnixTime(DateUtils.string2UnixTime(t + " 00:00:00"));

            entity.save();
            UIUtils.showToast("存储成功");
        } else {
            Update update = new Update(ProductEntity.class);
            String num = etNums.getText().toString().trim();
            String price = etPrice.getText().toString().trim();
            String total = tvAllPrice.getText().toString().trim();

            update.set("productNums='" + num + "'").where("productUsername = '" + selectProUserName + "' and productDate = '" + produceDate + "'").execute();
            update.set("productPrice='" + price + "'").where("productUsername = '" + selectProUserName + "' and productDate = '" + produceDate + "'").execute();
            update.set("productTotalPrice='" + total + "'").where("productUsername = '" + selectProUserName + "' and productDate = '" + produceDate + "'").execute();

            UIUtils.showToast("修改成功");
        }


    }

    /**
     * 选择日期
     */
    private void selectDate() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String m = TimeUtils.get2FormatDate(i1 + 1);
                String d = TimeUtils.get2FormatDate(i2);
                inputDate = i + "-" + m + "-" + d;
                tvDate.setText(inputDate);
            }
        }, TimeUtils.getYear(), TimeUtils.getMonth() - 1, TimeUtils.getDay()).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String nums = etNums.getText().toString().trim();
        String price = etPrice.getText().toString().trim();

        if (!TextUtils.isEmpty(nums) && !TextUtils.isEmpty(price)) {
            try {
                BigDecimal numBig = new BigDecimal(nums);
                BigDecimal priceBig = new BigDecimal(price);
                BigDecimal totalPrice = numBig.multiply(priceBig);
                tvAllPrice.setText(totalPrice.stripTrailingZeros().toPlainString());
            } catch (Exception e) {
                tvAllPrice.setText("");
                e.printStackTrace();
            }
        } else {
            tvAllPrice.setText("");
        }

    }
}
