package cn.com.gxdgroup.angentbible.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.activities.CollectionActivity;
import cn.com.gxdgroup.angentbible.activities.LoginActivity;
import cn.com.gxdgroup.angentbible.base.BaseFragment;
import cn.com.gxdgroup.angentbible.constant.MenuType;
import cn.com.gxdgroup.angentbible.constant.MyConstants;
import cn.com.gxdgroup.angentbible.domain.MoviesBean;
import cn.com.gxdgroup.angentbible.net.retrofit2.HttpMethods;
import cn.com.gxdgroup.angentbible.ui.SharePopupWindow;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;
import rx.Subscriber;


/**
 * Created by Ivy on 2016/10/14.
 *
 * @description:
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.iv_portrait)
    ImageView mIvPortrait;
    @BindView(R.id.iv_scfy)
    ImageView mIvScfy;
    @BindView(R.id.rl_collection_house)
    RelativeLayout mRlCollectionHouse;
    @BindView(R.id.iv_scky)
    ImageView mIvScky;
    @BindView(R.id.rl_collection_people)
    RelativeLayout mRlCollectionPeople;
    @BindView(R.id.iv_reflact)
    ImageView mIvReflact;
    @BindView(R.id.rl_feedback)
    RelativeLayout mRlFeedback;
    @BindView(R.id.iv_invite)
    ImageView mIvInvite;
    @BindView(R.id.rl_invite)
    RelativeLayout mRlInvite;
    @BindView(R.id.iv_update)
    ImageView mIvUpdate;
    @BindView(R.id.rl_update)
    RelativeLayout mRlUpdate;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;
    @BindView(R.id.rl_setting)
    RelativeLayout mRlSetting;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.me_root_view)
    View mRootView;
    private Tencent mTencent;

    @Override
    public View setContentView(LayoutInflater inflater) {
        return UIUtils.inflate(R.layout.fragment_me);
    }

    @Override
    protected void initView(View view) {
        mTencent = Tencent.createInstance("1105165474", mActivity);
    }

    @Override
    public void loadData() {
        L.v("MeFragment load data...");
    }


    @OnClick({R.id.iv_portrait, R.id.rl_collection_house, R.id.rl_collection_people, R.id.rl_feedback, R.id.rl_invite, R.id.rl_update, R.id.rl_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_collection_house:
                CollectionActivity.startActivity(mActivity, MenuType.COLL_HOUSE);
                break;
            case R.id.rl_collection_people:
                CollectionActivity.startActivity(mActivity, MenuType.COLL_KE);
                break;
            case R.id.rl_feedback:
                break;
            case R.id.rl_invite:
                SharePopupWindow sharePopupWindow = new SharePopupWindow(mActivity, mTencent);
                sharePopupWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);
                break;
            case R.id.rl_update:
                break;
            case R.id.rl_setting:
                //getMovies();
                break;
            case R.id.iv_portrait:
                startActivity(new Intent(mActivity, LoginActivity.class));
                break;
        }
    }

    private void getMovies() {
        Subscriber<MoviesBean> subscriber = new Subscriber<MoviesBean>() {

            @Override
            public void onCompleted() {
                UIUtils.showToast("电影加载完成");
            }

            @Override
            public void onError(Throwable e) {
                UIUtils.showToast("电影加载失败" + e.getMessage());
            }

            @Override
            public void onNext(MoviesBean bean) {
                mTvNickname.setText(bean.toString());
            }
        };


        HttpMethods.getInstance().getTopMovie(subscriber, 0, 10);
    }


    /**
     * 读取QQ的信息设置头像和昵称
     *
     * @param info
     */
    public void setAvatar(Object info, int type) {
        JSONObject json = (JSONObject) info;
        try {
            String nickname = "", headimgurl = "";
            if (type == 0) {
                headimgurl = json.getString("figureurl_qq_2");
                nickname = json.getString("nickname");
            } else if (type == 1) {
                headimgurl = json.getString("headimgurl");
                nickname = json.getString("nickname");
            }

            Glide.with(mActivity).load(headimgurl).into(mIvPortrait);
            mTvNickname.setText(nickname);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
