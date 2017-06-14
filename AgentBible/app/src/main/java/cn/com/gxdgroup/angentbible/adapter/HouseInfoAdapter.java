package cn.com.gxdgroup.angentbible.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.adapter.common.CommonAdapter;
import cn.com.gxdgroup.angentbible.adapter.common.ViewHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/18.
 *
 * @description:
 */

public class HouseInfoAdapter extends CommonAdapter<String> {

    private ArrayList<String> list = new ArrayList<>();
    private int mMenuType;


    public HouseInfoAdapter(Context context, List<String> mDatas, int itemLayoutId, int menuType) {
        super(context, mDatas, itemLayoutId);
        mMenuType = menuType;

        list.add("http://cdn1.dooioo.com/gimage10/M00/4F/BF/ChYCZ1crapaAItWeAAAphhDd-DE261.jpg");
        list.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160902/ee555266-74b0-47b2-903f-ebe1b09b37d0.jpg_600x450.jpg");
        list.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160519/657e62ef-ea3d-48df-b7cd-a7e917cea088.jpg_600x450.jpg");
        list.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160519/449997ea-502a-468f-b1e2-c760cf1d1c9f.jpg_600x450.jpg");
        list.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160906/1e3a7037-c14b-483c-9946-0013aa28f4b0.jpg_600x450.jpg");
        list.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160916/096818ff-dc56-4827-9caa-955bfdee03f2.jpg_600x450.jpg");
        list.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160914/b0eadb9f-7a49-409f-bab1-3a3fa3f5d446.jpg_600x450.jpg");


    }

    @Override
    public void convert(ViewHolder holder, String item) {

        String s = "" + ((new Random().nextInt(1000) % (1000 - 100 + 1) + 100) + "万");
//
        holder.setText(R.id.tv_total_price, s);

        holder.setImageByUrl(R.id.iv_cover, list.get(new Random().nextInt(list.size())));

        if (mMenuType == 1 || mMenuType == 2) {
            holder.setVisible(false, R.id.tv_unit_price);
        }
        if (mMenuType == 2) {
            holder.setVisible(View.GONE, R.id.iv_cover);
        }

    }
}
