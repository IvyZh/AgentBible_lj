package cn.com.gxdgroup.angentbible.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.constant.MyConstants;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/31.
 *
 * @description: 分享按钮
 */

public class SharePopupWindow extends PopupWindow {

    public SharePopupWindow(final Activity mContext, final Tencent mTencent) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.popup_share, null);
        this.setContentView(v);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        /* 设置背景显示 */
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        /* 设置触摸外面时消失 */
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */
        v.setFocusableInTouchMode(true);

        //设置点击事件

        v.findViewById(R.id.rl_weichat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //微信好友
                // 通过WXAPIFactory工厂，获取IWXAPI的实例
                IWXAPI api = WXAPIFactory.createWXAPI(mContext, MyConstants.APP_ID, false);
                api.registerApp(MyConstants.APP_ID);

                Bitmap bitmap = BitmapFactory.decodeResource(UIUtils.getResources(), R.drawable.launcher_logo);
//        WXImageObject imgObj = new WXImageObject(bitmap);

                WXWebpageObject webPage = new WXWebpageObject();
                webPage.webpageUrl = "http://www.baidu.com";

                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = webPage;
                msg.description = "这是网页描述";
                msg.title = "网页title";


                // 设置缩略图
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 60, 60, true);
                bitmap.recycle();
                msg.thumbData = bmpToByteArray(thumbBmp, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();

                req.transaction = buildTransaction("img");

                req.message = msg;

                req.scene = SendMessageToWX.Req.WXSceneSession;//分享到好友
//                req.scene = SendMessageToWX.Req.WXSceneTimeline;//分享到朋友圈

                api.sendReq(req);

            }
        });
        v.findViewById(R.id.rl_moment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //微信朋友圈
                // 通过WXAPIFactory工厂，获取IWXAPI的实例
                IWXAPI api = WXAPIFactory.createWXAPI(mContext, MyConstants.APP_ID, false);
                api.registerApp(MyConstants.APP_ID);

                Bitmap bitmap = BitmapFactory.decodeResource(UIUtils.getResources(), R.drawable.launcher_logo);
//        WXImageObject imgObj = new WXImageObject(bitmap);

                WXWebpageObject webPage = new WXWebpageObject();
                webPage.webpageUrl = "http://www.baidu.com";

                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = webPage;
                msg.description = "这是网页描述";
                msg.title = "网页title";


                // 设置缩略图
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 60, 60, true);
                bitmap.recycle();
                msg.thumbData = bmpToByteArray(thumbBmp, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();

                req.transaction = buildTransaction("img");

                req.message = msg;

//        req.scene = SendMessageToWX.Req.WXSceneSession;//分享到好友
                req.scene = SendMessageToWX.Req.WXSceneTimeline;//分享到朋友圈

                api.sendReq(req);

            }
        });
        v.findViewById(R.id.rl_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //QQ好友
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.163.com");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 123);
                mTencent.shareToQQ(mContext, params, new IUiListener() {

                    @Override
                    public void onComplete(Object o) {

                    }

                    @Override
                    public void onError(UiError error) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        v.findViewById(R.id.rl_qq_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //QQ空间

                final Bundle params = new Bundle();
                params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT + "");
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题123");//必填
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要233");//选填
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");//必填
                ArrayList<String> urls = new ArrayList<>();
//        urls.add("http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
                urls.add("http://img.educity.cn/img_21/359/2014062609/3225093910.png");

                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, urls);
                mTencent.shareToQzone(mContext, params, new IUiListener() {

                    @Override
                    public void onComplete(Object o) {

                    }

                    @Override
                    public void onError(UiError error) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        v.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消分享
                dismiss();
            }
        });
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);

        if (needRecycle) {

            bmp.recycle();

        }

        byte[] result = output.toByteArray();

        try {

            output.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return result;

    }
}
