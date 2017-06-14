package cn.com.gxdgroup.angentbible.activities;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.ui.SearchHeadView;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/19.
 *
 * @description: 搜索界面（二手房）
 */

public class SearchActivity extends BaseActivity {

    @BindView(R.id.searchHeadView)
    SearchHeadView searchHeadView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_search);
    }

    @Override
    public void initView() {
        searchHeadView.setClickEventListener(new SearchHeadView.ClickEventListener() {
            @Override
            public void cancel() {
                finish();
            }

            @Override
            public void search(String key) {
                UIUtils.showToast("搜索: " + key);
            }
        });
    }


}
