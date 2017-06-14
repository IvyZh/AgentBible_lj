package cn.com.gxdgroup.angentbible.holder.impl.details;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.activities.PhotoViewerActivity;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description: 二手房详情里面的Holder
 */

public class BannerHolder extends BaseHolder {
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;

    public BannerHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_details_banner);
    }

    @Override
    public void setData() {

    }

    @OnClick(R.id.iv_photo)
    public void onClick() {
        mActivity.startActivity(new Intent(mActivity, PhotoViewerActivity.class));
    }
}
