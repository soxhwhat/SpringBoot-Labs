package cn.iocoder.springboot.lab25.springwebsocket.message;

/**
 * 基础消息体
 * 在 HTTP 协议中，是基于 Request/Response 请求响应的同步模型，进行交互。在 Websocket 协议中，是基于 Message 消息的异步模型，进行交互。这一点，是很大的不同的，等会看到具体的消息类，感受会更明显。
 *
 * 因为 WebSocket 协议，不像 HTTP 协议有 URI 可以区分不同的 API 请求操作，所以我们需要在 WebSocket 的 Message 里，增加能够标识消息类型，这里我们采用 type 字段。所以在这个示例中，我们采用的 Message 采用 JSON 格式编码，格式如下：
 *
 * {
 *     type: "", // 消息类型
 *     body: {} // 消息体
 * }
 * type 字段，消息类型。通过该字段，我们知道使用哪个 MessageHandler 消息处理器。关于 MessageHandler ，我们在 「2.6 消息处理器」 中，详细解析。
 * body 字段，消息体。不同的消息类型，会有不同的消息体。
 * Message 采用 JSON 格式编码，主要考虑便捷性，胖友实际项目下，也可以考虑 Protobuf 等更加高效且节省流量的编码格式。
 */
public interface Message {
}
