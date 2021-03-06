package cn.com.gxdgroup.angentbible.adapter;

/**
 * Created by Ivy on 2016/10/24.
 *
 * @description:
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.adapter.common.CommonAdapter;
import cn.com.gxdgroup.angentbible.adapter.common.ViewHolder;

/**
 * 父类别 适配器
 *
 * @author ansen
 * @create time 2015-09-25
 */
public class ChildrenCategoryAdapter extends CommonAdapter<String> {

    private int currentPos;

    public ChildrenCategoryAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, String item) {
        holder.setText(R.id.tv_selection, item);
        if (currentPos == mDatas.indexOf(item)) {
            holder.setTextColor(R.id.tv_selection, R.color.common_blue);
        } else {
            holder.setTextColor(R.id.tv_selection, R.color.text_grey);
        }

    }

    public void setDatas(List<String> data) {
        mDatas = data;
    }

    public void setSelectedPosition(int position) {
        currentPos = position;
    }
}