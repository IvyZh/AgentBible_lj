package cn.com.gxdgroup.angentbible.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.adapter.HouseInfoAdapter;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;
import cn.com.gxdgroup.angentbible.utils.SharedPreUtils;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description: 房源列表界面
 */

public class HouseResourcesListActivity extends BaseActivity {

    @BindView(R.id.lv_house_res)
    ListView lvHouseRes;
    @BindView(R.id.app_title)
    AppTitleView mAppTitle;

    /**
     * -1 ：去掉AppTitleView下面的SelectionHolder
     * 0：首页-二手房Menu
     * 1：首页-租房Menu
     * 2：首页-客源Menu(购房)
     * 3：首页-客源Menu(租房)
     * 4：首页-最新成交Menu
     * 5：我-收藏房源-Item
     * 6：我-收藏客源-Item
     */
    private int mMenuType = 0;

    public static void startActivity(Activity ba, int menuType) {
        Intent intent = new Intent(ba, HouseResourcesListActivity.class);
        intent.putExtra("mMenuType", menuType);
        ba.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_house_res_list);
    }

    @Override
    public void initView() {

        mMenuType = getIntent().getIntExtra("mMenuType", 0);

        if (mMenuType == 2) {// 客源单独处理+要显示TAB
            mAppTitle.showMode(AppTitleView.MODE.TAB, mMenuType, this);
        } else {
            mAppTitle.showMode(AppTitleView.MODE.SEARCH, mMenuType, this);
        }

        mAppTitle.setListener(new SimpleAppTitleListener(mContext) {
            @Override
            public void OnrlSearch() {
                startActivity(new Intent(HouseResourcesListActivity.this, SearchActivity.class));
            }

            @Override
            public void onTabLeft() {
                super.onTabLeft();
            }

            @Override
            public void onTabRight() {
                super.onTabRight();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("zhang san " + i);
        }

        HouseInfoAdapter adapter = new HouseInfoAdapter(this, data, R.layout.item_house_info, mMenuType);

        lvHouseRes.setAdapter(adapter);
        lvHouseRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
//                startActivity(new Intent(mContext, HouseDetailsActivity.class));
                HouseDetailsActivity.startActivity(mContext, mMenuType);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //需要清空SharePre里面的内容
        SharedPreUtils.clear();
    }
}
