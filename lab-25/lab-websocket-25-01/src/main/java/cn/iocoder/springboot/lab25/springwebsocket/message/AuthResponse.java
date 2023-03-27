package cn.iocoder.springboot.lab25.springwebsocket.message;

/**
 * 用户认证响应
 * 虽然说，WebSocket 协议是基于 Message 模型，进行交互。但是，这并不意味着它的操作，不需要响应结果。例如说，用户认证请求，是需要用户认证响应的。所以，我们创建 AuthResponse 类，作为用户认证响应。
 */
public class AuthResponse implements Message {

    public static final String TYPE = "AUTH_RESPONSE";

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;

    public Integer getCode() {
        return code;
    }

    public AuthResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AuthResponse setMessage(String message) {
        this.message = message;
        return this;
    }

}
