package cn.com.gxdgroup.angentbible.holder.impl.village;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.activities.EquipmentActivity;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description:
 */

public class StaticMapHolder extends BaseHolder {


    @BindView(R.id.rl_around_equipment)
    RelativeLayout mRlAroundEquipment;
    @BindView(R.id.iv_static_map)
    ImageView mIvStaticMap;

    public StaticMapHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        View view = View.inflate(mActivity, R.layout.holder_static_map, null);
        return view;
    }


    @Override
    public void initView() {
        String url = "http://api.map.baidu.com/staticimage?width=360&height=294&center=121.433346,31.1882&zoom=15&markers=121.433346,31.1882&markerStyles=l,0";
        Glide.with(mActivity).load(url).into(mIvStaticMap);
    }


    @Override
    public void setData() {


    }

    @OnClick({R.id.rl_around_equipment, R.id.iv_static_map})
    public void onClick(View view) {
        mActivity.startActivity(new Intent(mActivity, EquipmentActivity.class));
    }
}
