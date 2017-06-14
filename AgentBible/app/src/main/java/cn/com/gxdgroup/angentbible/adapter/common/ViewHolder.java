package cn.com.gxdgroup.angentbible.adapter.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.com.gxdgroup.angentbible.base.MyApplication;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/12.
 *
 * @description:
 */

public class ViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        //setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {

        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }


    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }


    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean check) {
        CheckBox view = getView(viewId);
        view.setChecked(check);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageByUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        Glide.with(MyApplication.getContext()).load(url).into(view);
        return this;
    }


    /**
     * 为RatingBar设置内容
     *
     * @param viewId
     * @param max
     * @param progress
     * @return
     */
    public ViewHolder setRatingBar(int viewId, int max, int progress) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * 给控件设置点击事件
     */
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        View view = getView(viewId);
        if (view instanceof CheckBox) {
            ((CheckBox) view).setOnCheckedChangeListener(listener);
        } else {
            throw new IllegalArgumentException("不能给非CheckBox控件设置点击事件");
        }
        return this;
    }


    public ViewHolder setVisible(boolean show, int... viewIds) {
        for (int id : viewIds) {
            getView(id).setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
        return this;
    }

    public ViewHolder setVisible(int visivle, int... viewIds) {
        for (int id : viewIds) {
            getView(id).setVisibility(visivle);
        }
        return this;
    }


    public ViewHolder setTextColor(int id, int colorId) {
        TextView v = getView(id);
        v.setTextColor(UIUtils.getColor(colorId));
        return this;
    }
}
