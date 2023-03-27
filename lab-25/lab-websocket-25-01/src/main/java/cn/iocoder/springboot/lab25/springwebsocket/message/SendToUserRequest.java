package cn.iocoder.springboot.lab25.springwebsocket.message;

/**
 * 发送消息给一个用户的 Message
 * 在服务端接收到发送消息的请求，需要转发消息给对应的人。所以，创建 SendToUserRequest 类，发送消息给一个用户的 Message 。
 * 相比 SendToOneRequest 来说，少一个 toUser 字段。因为，我们可以通过 WebSocket 连接，已经知道发送给谁了。
 */
public class SendToUserRequest implements Message {

    public static final String TYPE = "SEND_TO_USER_REQUEST";

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;

    public String getMsgId() {
        return msgId;
    }

    public SendToUserRequest setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SendToUserRequest setContent(String content) {
        this.content = content;
        return this;
    }

}
