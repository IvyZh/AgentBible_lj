package cn.com.gxdgroup.angentbible.listener;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/28.
 *
 * @description:
 */

public class QQLoginUiListener implements IUiListener {
    @Override
    public void onComplete(Object response) {
        //V2.0版本，参数类型由JSONObject 改成了Object,具体类型参考api文档
        if (null == response) {
            UIUtils.showToast("返回为空,登录失败");
            return;
        }
        JSONObject jsonResponse = (JSONObject) response;
        if (null != jsonResponse && jsonResponse.length() == 0) {
            UIUtils.showToast("返回为空,登录失败");
            return;
        }
        doComplete((JSONObject) response);
    }

    protected void doComplete(JSONObject values) {

    }

    @Override
    public void onError(UiError e) {
        L.v("onError:", "code:" + e.errorCode + ", msg:" + e.errorMessage + ", detail:" + e.errorDetail);
        UIUtils.showToast("登录失败:" + e.errorCode);
    }

    @Override
    public void onCancel() {
        UIUtils.showToast("用户取消授权登陆");
    }
}
