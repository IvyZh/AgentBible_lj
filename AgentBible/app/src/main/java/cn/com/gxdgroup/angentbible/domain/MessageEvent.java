package cn.com.gxdgroup.angentbible.domain;

/**
 * Created by Ivy on 2016/10/28.
 *
 * @description:
 */

public class MessageEvent {
    private int msgType;//0 :QQ登陆 1： 微信登陆
    private Object msg;

    public MessageEvent(int msgType, Object msg) {
        this.msgType = msgType;
        this.msg = msg;
    }

    public int getMsgType() {
        return msgType;
    }


    public Object getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "msgType=" + msgType +
                ", msg=" + msg +
                '}';
    }
}
