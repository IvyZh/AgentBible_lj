package cn.com.gxdgroup.angentbible.holder.impl.details;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description: 二手房详情里面的Holder
 */

public class HouseIntroHolder extends BaseHolder {
    @BindView(R.id.expand_text_view)
    ExpandableTextView mExpandTextView;

    public HouseIntroHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_house_intro);
    }

    @Override
    public void setData() {

    }

    @Override
    public void initView() {

        String str = "实地看房：2房1厅1卫1阳台，客厅朝南带大阳台， 厨卫房朝北，房间朝南，南北通透，全明户型。 地理位置：位于泰和西路/水产西路，宝山顾村核心地段，靠近1号线共富新村站！距离世纪联华大超市五分钟步行，外环高架车程五分钟。 小区配置：高端物业（银顶峰物业）、小区内小桥流水、有公园等多处景观、儿童游乐场，绿化率40%。 商圈配套：紧邻顾村商圈，靠近顾村公园，浦西大的湿地氧吧。对口宝山顾村小学及顾村中学。";
        mExpandTextView.setText(str);
    }
}
