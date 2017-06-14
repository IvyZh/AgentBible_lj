package cn.com.gxdgroup.angentbible.activities.temp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;

/**
 * 数据统计类型菜单页面
 */
public class StatisticsMenuActivity extends BaseActivity {

    @BindView(R.id.titleView)
    AppTitleView mTitleView;
    @BindView(R.id.bt_produce_month)
    Button btProduceMonth;
    @BindView(R.id.bt_produce_details)
    Button btProduceDetails;


    public static void startActivity(Activity a) {
        Intent intent = new Intent(a, StatisticsMenuActivity.class);
        a.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_statistics_menu);
    }

    @Override
    public void initView() {
        mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                .setTitleMsg("数据统计")
                .setListener(new SimpleAppTitleListener(StatisticsMenuActivity.this) {
                    @Override
                    public void onBack() {
                        finish();
                    }
                });


    }


    @OnClick({R.id.bt_produce_month, R.id.bt_produce_details})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_produce_month:
                MonthProduceActivity.startActivity(this);
                break;
            case R.id.bt_produce_details:
                ProduceDetailsActivity.startActivity(this);
                break;
        }
    }
}
