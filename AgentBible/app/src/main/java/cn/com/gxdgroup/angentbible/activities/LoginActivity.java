package cn.com.gxdgroup.angentbible.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.constant.MyConstants;
import cn.com.gxdgroup.angentbible.domain.MessageEvent;
import cn.com.gxdgroup.angentbible.listener.GetQQUserInfoUiListener;
import cn.com.gxdgroup.angentbible.listener.MyTextWatcher;
import cn.com.gxdgroup.angentbible.listener.QQLoginUiListener;
import cn.com.gxdgroup.angentbible.net.retrofit2.Retrofit2Utils;
import cn.com.gxdgroup.angentbible.net.api.ServiceApi;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ivy on 2016/10/24.
 *
 * @description: 登录界面
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.rl_username)
    RelativeLayout mRlUsername;
    @BindView(R.id.view_divide)
    View mViewDivide;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.view_divide2)
    View mViewDivide2;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.tv_one_label)
    TextView mTvOneLabel;
    @BindView(R.id.ll_one)
    LinearLayout mLlOne;
    @BindView(R.id.iv_wechat)
    ImageView mIvWechat;
    @BindView(R.id.iv_qq)
    ImageView mIvQq;
    @BindView(R.id.bt_get_code)
    Button mBtGetCode;
    @BindView(R.id.tv_user_handbook)
    TextView mTvUserHandbook;
    // QQ
    private Tencent mTencent;
    private IUiListener qqLoginListener;
    private static boolean isServerSideLogin = false;
    private UserInfo mInfo;

    //WeiXin
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        //注册事件
        EventBus.getDefault().register(this);

        //QQ初始化
        mTencent = Tencent.createInstance("1105165474", this.getApplicationContext());

        qqLoginListener = new QQLoginUiListener() {
            @Override
            protected void doComplete(JSONObject values) {
                initOpenidAndToken(values);
                updateUserInfo();
            }
        };

        // 微信初始化

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, MyConstants.APP_ID, false);
        api.registerApp(MyConstants.APP_ID);

        // 控件初始化
        mEtUsername.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                mIvDelete.setVisibility(sequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void initActionBar(int colorId) {
        super.initActionBar(R.color.login_title);
    }

    @OnClick({R.id.iv_close, R.id.iv_delete, R.id.bt_login, R.id.iv_wechat, R.id.iv_qq, R.id.tv_user_handbook, R.id.bt_get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_delete:
                mEtUsername.setText("");
                break;
            case R.id.bt_login:
                normalLogin();
                break;
            case R.id.iv_wechat:
                doWeChat();
                break;
            case R.id.iv_qq:
                doQQLogin();
                break;
            case R.id.tv_user_handbook:
                UIUtils.showToast("进入用户服务协议界面");
                break;
            case R.id.bt_get_code:
                UIUtils.showToast("获取验证码");
                getVerifyCode();
                break;
        }
    }

    private void normalLogin() {
        ServiceApi api = Retrofit2Utils.getServiceApi();
        JSONObject object = new JSONObject();
        try {
            object.put("username", mEtUsername.getText().toString());
            object.put("accountcate", "0");
            object.put("code", mEtPwd.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        api.login(object).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject jsonObject = response.body();
                L.v("success:" + jsonObject.toString());
                //{"code":"SUCCESS","message":null,"data":{"Auth-Token":"e1cefc1e225ea8ab81340b64375827eb"}}


            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });

    }

    private void getVerifyCode() {
        ServiceApi api = Retrofit2Utils.getServiceApi();
        JSONObject object = new JSONObject();
        try {
            object.put("phone", "13811111111");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<JSONObject> call = api.sendMsg(object);

        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject jsonObject = response.body();
                L.v("success:" + jsonObject.toString());
                String code = "";
                try {
                    code = jsonObject.getString("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (TextUtils.equals("SUCCESS", code)) {
                    UIUtils.showToast("获取验证码成功");
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                L.v("onFailure:" + t.getMessage());
            }
        });
    }

    //##############################################################################
    //################                                              ################
    //################                                              ################
    //################                 以下是WeChat登陆的逻辑         ################
    //################                                              ################
    //################                                              ################
    //##############################################################################
    private void doWeChat() {

        //Step 1. 请求code
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";

        if (!api.isWXAppInstalled()) {
            UIUtils.showToast("请先安装微信客户端");
            return;
        }
        if (!api.isWXAppSupportAPI()) {
            UIUtils.showToast("请先更新微信客户端");
            return;
        }
        //调用该方法拉起微信的登录界面
        MyConstants.isWechatLogin = true;
        api.sendReq(req);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(MessageEvent event) {
        L.v("LoginActivity,helloEventBus:" + event);
        if (event.getMsgType() == 1) {//微信登陆
            finish();
        }
    }

    //##############################################################################
    //################                                              ################
    //################                                              ################
    //################                 以上是WeChat登陆的逻辑         ################
    //################                                              ################
    //################                                              ################
    //##############################################################################


    //##############################################################################
    //################                                              ################
    //################                                              ################
    //################                 以下是QQ登陆的逻辑             ################
    //################                                              ################
    //################                                              ################
    //##############################################################################

    private void doQQLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", qqLoginListener);
            isServerSideLogin = false;
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", qqLoginListener);
                isServerSideLogin = false;
                return;
            }
            mTencent.logout(this);
            isServerSideLogin = false;
            updateUserInfo();
        }
    }


    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.v("-->onActivityResult " + requestCode + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, qqLoginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(new GetQQUserInfoUiListener() {
                @Override
                public void onComplete(Object response) {
                    MessageEvent event = new MessageEvent(0, response);
                    EventBus.getDefault().post(event);
                }
            });

            UIUtils.showToast("登陆成功");
            finish();
        }
    }


    //##############################################################################
    //################                                              ################
    //################                                              ################
    //################                 以上是QQ登陆的逻辑             ################
    //################                                              ################
    //################                                              ################
    //##############################################################################

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
