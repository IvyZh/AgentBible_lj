package cn.com.gxdgroup.angentbible.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.gxdgroup.angentbible.R;

/**
 * Created by Ivy on 2016/10/19.
 *
 * @description:
 */

public class TitleView extends RelativeLayout {

    private ImageView ivBack, ivOperation;
    private TextView tvTitle, tvOperation;

    public TitleView(Context context) {
        super(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);

    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.title_view, this);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivOperation = (ImageView) findViewById(R.id.iv_operation);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvOperation = (TextView) findViewById(R.id.tv_operation);
    }

    public void setTitle(String msg) {
        tvTitle.setText(msg);
    }

    public void setRightButtonListener(OnClickListener listener) {
        ivOperation.setOnClickListener(listener);
    }

    public void setLeftButtonListener(OnClickListener listener) {
        ivBack.setOnClickListener(listener);
    }

    public void setLeftButtonVisibility(int visibility) {
        ivBack.setVisibility(visibility);
    }

    public void setRightButtonVisibility(int visibility) {
        ivOperation.setVisibility(visibility);
    }


    // visiable
    public void showRightText(boolean b) {
        tvOperation.setVisibility(b ? VISIBLE : GONE);
    }

    public void showRightImageView(boolean b) {
        ivOperation.setVisibility(b ? VISIBLE : GONE);
    }

    // text
    public void setRightText(String msg) {
        tvOperation.setText(msg);
    }

    // listener
    public TitleView setRightTextListener(OnClickListener listener) {
        tvOperation.setOnClickListener(listener);
        return this;
    }

}
