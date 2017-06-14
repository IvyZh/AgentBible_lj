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
 * @description: 补充说明的Holder
 */

public class RemarkHolder extends BaseHolder {

    public RemarkHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_remark);
    }

    @Override
    public void setData() {
        //TODO 在给remark赋值的时候，获取可以通过服务器返回的type字段来判断是租房进来的还是购房客户进来的。不过其实还好，json的key应该都是remarks
    }


}
