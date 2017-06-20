package org.apache.shiro.session.mgt;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Function: 自定义 session
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/19 18:55 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
@Setter
@Getter
public class OnlineSession extends SimpleSession {

    public static enum OnlineStatus {
        on_line("在线"), hidden("隐身"), force_logout("强制退出");
        private final String info;

        OnlineStatus(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * bitIndexCounter 此属性为父类default
     */
    private static final int USER_AGENT_BIT_MASK = 1 << bitIndexCounter++;
    private static final int STATUS_BIT_MASK = 1 << bitIndexCounter++;

    /**
     * 用户浏览器类型
     */
    private String userAgent;

    /**
     * 在线状态
     */
    private OnlineStatus status = OnlineStatus.on_line;

    /**
     * 用户登录时系统IP
     */
    private String systemHost;

    public OnlineSession() {
        super();
    }

    public OnlineSession(String host) {
        super(host);
    }

    /**
     * 属性是否改变-优化session数据同步
     */
    private transient boolean attributeChange = false;

    @Override
    public void setAttribute(Object key, Object value) {
        super.setAttribute(key, value);
    }

    @Override
    public Object removeAttribute(Object key) {
        return super.removeAttribute(key);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        short alteredFieldsBitMask = getAlteredFieldsBitMask();
        out.writeShort(alteredFieldsBitMask);
        if (null != userAgent) {
            out.writeObject(userAgent);
        }
        if (null != status) {
            out.writeObject(status);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        short bitMask = in.readShort();
        if (isFieldPresent(bitMask,USER_AGENT_BIT_MASK)){
            this.userAgent = (String)in.readObject();
        }
        if (isFieldPresent(bitMask,STATUS_BIT_MASK)){
            this.status = (OnlineStatus)in.readObject();
        }
    }

    private short getAlteredFieldsBitMask(){
        int bitMask = 0;
        bitMask = userAgent != null ? bitMask | USER_AGENT_BIT_MASK : bitMask;
        bitMask = status != null ? bitMask | STATUS_BIT_MASK : bitMask;
        return (short)bitMask;
    }

    private static boolean isFieldPresent(short bitMask,int fieldBitMask){
        return (bitMask & fieldBitMask) != 0;
    }

}
