package cn.com.gxdgroup.angentbible.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.base.BaseFragment;
import cn.com.gxdgroup.angentbible.domain.MessageEvent;
import cn.com.gxdgroup.angentbible.fragments.DataAnalysisFragments;
import cn.com.gxdgroup.angentbible.fragments.HomeFragment;
import cn.com.gxdgroup.angentbible.fragments.MeFragment;
import cn.com.gxdgroup.angentbible.ui.MyViewPager;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;


public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_main)
    MyViewPager mVpMain;
    @BindView(R.id.iv_main)
    ImageView mIvMain;
    @BindView(R.id.tv_main)
    TextView mTvMain;
    @BindView(R.id.rl_main)
    RelativeLayout mRlMain;
    @BindView(R.id.iv_data)
    ImageView mIvData;
    @BindView(R.id.tv_data)
    TextView mTvData;
    @BindView(R.id.rl_data)
    RelativeLayout mRlData;
    @BindView(R.id.iv_me)
    ImageView mIvMe;
    @BindView(R.id.tv_me)
    TextView mTvMe;
    @BindView(R.id.rl_me)
    RelativeLayout mRlMe;
    @BindView(R.id.ll_bottom)
    View mBottomView;

    private MeFragment meFragment;

    private ArrayList<BaseFragment> fragments;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        //注册事件..
        EventBus.getDefault().register(this);

        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new DataAnalysisFragments());
        meFragment = new MeFragment();
        fragments.add(meFragment);

        mVpMain.setNoScroll(false);
        mVpMain.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
        mVpMain.setOffscreenPageLimit(2);//就可以让ViewPager多缓存一个页面，这样上面的问题就得到了解决。

        mVpMain.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                selectPosAndLoadData(position);
            }
        });

//        mVpMain.setCurrentItem(1);//TOD 最后可以去掉
        selectPosAndLoadData(0);// TODO 0

//        NetUtils.getInstance().getBooks("1", new Action1() {
//            @Override
//            public void call(Object o) {
//
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(MessageEvent event) {
        L.v("MainActivity,helloEventBus:" + event);
        if (event.getMsgType() == 0) {// QQ登陆
            meFragment.setAvatar(event.getMsg(), 0);
        } else if (event.getMsgType() == 1) {//微信登陆
            meFragment.setAvatar(event.getMsg(), 1);
        }
    }


    @OnClick({R.id.rl_main, R.id.rl_data, R.id.rl_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_main:
                mVpMain.setCurrentItem(0);
                break;
            case R.id.rl_data:
                mVpMain.setCurrentItem(1);
                break;
            case R.id.rl_me:
                mVpMain.setCurrentItem(2);
                break;
        }
    }

    private void selectPosAndLoadData(int pos) {
        // 还原所有状态-颜色
        mTvMain.setTextColor(UIUtils.getColor(R.color.bottom_text_nor));
        mTvData.setTextColor(UIUtils.getColor(R.color.bottom_text_nor));
        mTvMe.setTextColor(UIUtils.getColor(R.color.bottom_text_nor));
        // 还原所有状态-资源
        mIvMain.setImageResource(R.drawable.tab_home);
        mIvData.setImageResource(R.drawable.data);
        mIvMe.setImageResource(R.drawable.my);

        //设置新数据
        switch (pos) {
            case 0:
                mTvMain.setTextColor(UIUtils.getColor(R.color.bottom_text_pre));
                mIvMain.setImageResource(R.drawable.tab_home_press);
                break;
            case 1:
                mTvData.setTextColor(UIUtils.getColor(R.color.bottom_text_pre));
                mIvData.setImageResource(R.drawable.data_press);
                break;
            case 2:
                mTvMe.setTextColor(UIUtils.getColor(R.color.bottom_text_pre));
                mIvMe.setImageResource(R.drawable.my_press);
                break;
        }
        //对fragment loadData
        L.v("selectPosAndLoadData:" + pos);

        fragments.get(pos).loadData();
    }


    class FragmentAdapter extends FragmentPagerAdapter {

        private ArrayList<BaseFragment> fragments;

        public FragmentAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
