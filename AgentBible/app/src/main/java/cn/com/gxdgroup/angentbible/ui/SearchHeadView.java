package cn.com.gxdgroup.angentbible.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.utils.L;

/**
 * Created by Ivy on 2016/10/21.
 */

public class SearchHeadView extends RelativeLayout {
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;

    private ClickEventListener clickEventListener;

    public SearchHeadView(Context context) {
        this(context, null);
    }

    public SearchHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.head_search_view2, this);
        ButterKnife.bind(this);


        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.length() == 0) {
                    ivClear.setVisibility(GONE);
                } else {
                    ivClear.setVisibility(VISIBLE);
                }

            }
        });

        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == 0 && keyEvent != null) {
                    String key = etContent.getText().toString().trim();
                    if (clickEventListener != null) {
                        clickEventListener.search(key);
                    }
                }

                return false;
            }
        });

    }


    @OnClick({R.id.tv_cancle, R.id.iv_clear, R.id.rl_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_search:
                etContent.setFocusable(true);
                etContent.requestFocus();
                break;
            case R.id.tv_cancle:
                if (clickEventListener != null) {
                    clickEventListener.cancel();
                }
                break;
            case R.id.iv_clear:
                etContent.getText().clear();
                break;
        }
    }

    public interface ClickEventListener {
        void cancel();
        void search(String key);
    }

    public void setClickEventListener(ClickEventListener clickEventListener) {
        this.clickEventListener = clickEventListener;
    }
}
