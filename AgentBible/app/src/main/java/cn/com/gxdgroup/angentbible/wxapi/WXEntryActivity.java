package cn.com.gxdgroup.angentbible.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.com.gxdgroup.angentbible.constant.MyConstants;
import cn.com.gxdgroup.angentbible.domain.MessageEvent;
import cn.com.gxdgroup.angentbible.net.client.OkHttp3Utils;
import cn.com.gxdgroup.angentbible.utils.AppManager;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;
import okhttp3.Request;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private Bundle bundle;
    //这个实体类是我自定义的实体类，用来保存第三方的数据的实体类
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(WXEntryActivity.this);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, MyConstants.APP_ID, false);
        api.registerApp(MyConstants.APP_ID);

        api.handleIntent(getIntent(), WXEntryActivity.this);  //必须调用此句话
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        api.handleIntent(intent, WXEntryActivity.this);//必须调用此句话
    }

    @Override
    public void onReq(BaseReq req) {
        System.out.println();
    }


    @Override
    public void onResp(BaseResp arg0) {


        bundle = getIntent().getExtras();
        SendAuth.Resp resp = new SendAuth.Resp(bundle);
        //获取到code之后，需要调用接口获取到access_token

        L.v("--resp code--" + resp.code);
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            if (MyConstants.isWechatLogin) {
                getToken(resp.code);
                MyConstants.isWechatLogin = false;
            } else {
                WXEntryActivity.this.finish();
                UIUtils.showToast("分享成功");
            }

        } else {
            WXEntryActivity.this.finish();
        }

    }

    /**
     * Title: onResp
     * <p>
     * API：https://open.weixin.qq.com/ cgi- bin/showdocument ?action=dir_list&t=resource/res_list&verify=1&id=open1419317853 &lang=zh_CN
     * Description:在此处得到Code之后调用https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     * 获取到token和openID。之后再调用https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID 获取用户个人信息
     */


    //这个方法会取得accesstoken  和openID
    private void getToken(String code) {
        L.v("--getToken--code:" + code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + MyConstants.APP_ID + "&secret=" + MyConstants.APP_SECRET + "&code=" + code + "&grant_type=authorization_code";


        Request request = new Request.Builder()
                .url(url)
                .build();

        okhttp3.Call call = OkHttp3Utils.getOkHttpClient().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                L.v("onFailure:" + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                //{"access_token":"IrTEMgQvnIfnze-H5m5X_--Hclj5IPhSdLHHTInGuViTtN-or-w_SjiuqCnpTjxu5XFY2o5cAtAKNEksmHFvb2Nye7sjZSEX8oayqXHCj1o","expires_in":7200,"refresh_token":"Unwlt5Z4wboXi4yxH30-Dnh1U2m30-wkD7LpJ4ZxncRS9CZuMahy3qp13SoxKYyfEMhlQX-oQI4AqG_E0aFV05uRPuNXhdgMrfU--2NCzSU","openid":"oXNk_xAr2pZDaIyx5yGbStXJs6Ic","scope":"snsapi_userinfo","unionid":"oqj3_wrY5UI_TjdJU2wD36UEF9Qc"}
                String result = response.body().string();

                L.v("success:" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    String token = object.getString("access_token");
                    String openid = object.getString("openid");
                    getUserInfo(token, openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //获取到token和openID之后，调用此接口得到身份信息
    private void getUserInfo(String token, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid;

        Request request = new Request.Builder()
                .url(url)
                .build();

        okhttp3.Call call = OkHttp3Utils.getOkHttpClient().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                L.v("onFailure:" + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                final String result = response.body().string();
                L.v("success:" + result);

                UIUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MessageEvent event = new MessageEvent(1, object);
                        EventBus.getDefault().post(event);
                        UIUtils.showToast("登陆成功");
                        finish();
                    }
                });
            }
        });

    }
}
