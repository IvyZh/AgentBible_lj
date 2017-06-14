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
import cn.com.gxdgroup.angentbible.constant.MenuType;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description: 收藏列表界面（包括房源+客源）
 */

public class CollectionActivity extends BaseActivity {


    @BindView(R.id.lv_house_res)
    ListView lvHouseRes;
    @BindView(R.id.app_title)
    AppTitleView mAppTitle;
    int adapterMenuType; //需要依据mMenuType进行转换

    private int mMenuType = MenuType.SENCOND_HAND;//0-二手房，1-租房，2-客源，3-最新成交

    public static void startActivity(Activity ba, int menuType) {
        Intent intent = new Intent(ba, CollectionActivity.class);
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

        mAppTitle.showMode(AppTitleView.MODE.TAB, -1, this);//mMenuType 传-1是为了隐藏selection

        if (mMenuType == MenuType.COLL_HOUSE) {//房源
            mAppTitle.setTabMsg("二手房", "租房");
        } else if (mMenuType == MenuType.COLL_KE) {//客源
            mAppTitle.setTabMsg("购房客户", "租房客户");
        }


        mAppTitle.setListener(new SimpleAppTitleListener(CollectionActivity.this) {
            @Override
            public void OnrlSearch() {
                startActivity(new Intent(CollectionActivity.this, SearchActivity.class));
            }

            @Override
            public void onTabLeft() {
                super.onTabLeft();
                defaultConvert();
                loadData();
            }

            @Override
            public void onTabRight() {
                super.onTabRight();
                //mMenuType--> mMenuType 的转换
                if (mMenuType == MenuType.COLL_HOUSE) {//房源
                    adapterMenuType = 1;//对应-->1：首页-租房Menu
                } else if (mMenuType == MenuType.COLL_KE) {//客源
                    adapterMenuType = 2;  //注意TAB的区分可以不在这个地方设置，但是为了界面加载布局先加载好，似乎又得需要传入一个客源的tab值（购房客户+租房客户）。以便下一个Activity预加载好布局
                }
                loadData();
            }
        });

        defaultConvert();
    }

    /**
     * 刚进页面的默认转换 mMenuType-->adapterMenuType
     */
    private void defaultConvert() {
        //mMenuType--> mMenuType 的转换
        if (mMenuType == MenuType.COLL_HOUSE) {//房源
            adapterMenuType = 0;//对应-->0：首页-二手房Menu
        } else if (mMenuType == MenuType.COLL_KE) {//客源
            adapterMenuType = 2;  //注意TAB的区分可以不在这个地方设置，但是为了界面加载布局先加载好，似乎又得需要传入一个客源的tab值（购房客户+租房客户）。以便下一个Activity预加载好布局
        }
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


        HouseInfoAdapter adapter = new HouseInfoAdapter(this, data, R.layout.item_house_info, adapterMenuType);
        lvHouseRes.setAdapter(adapter);
        lvHouseRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
                HouseDetailsActivity.startActivity(mContext, adapterMenuType);
            }
        });

    }


}
