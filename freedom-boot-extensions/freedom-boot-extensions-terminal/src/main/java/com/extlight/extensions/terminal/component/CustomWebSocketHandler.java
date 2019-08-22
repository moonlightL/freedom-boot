package com.extlight.extensions.terminal.component;

import com.extlight.common.utils.AesUtil;
import com.extlight.extensions.terminal.model.SshClient;
import com.extlight.extensions.terminal.model.SshConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Arrays;

/**
 * @Author MoonlightL
 * @Title: CustomWebSocketHandler
 * @ProjectName: freedom-boot
 * @Description: websocket 处理器
 * @DateTime: 2019/8/21 16:24
 */
@Slf4j
public class CustomWebSocketHandler extends TextWebSocketHandler {

	private static final String EXIT = "exit";

	private static final String CHARSET = "UTF-8";

	private SshClient sshClient;

	/**
	 * 建立连接
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);

		String hostname = (String) session.getAttributes().get("hostname");
		String username = (String) session.getAttributes().get("username");
		String encrypt = (String) session.getAttributes().get("password");
		String password = AesUtil.decrypt(encrypt);

		this.sshClient = new SshClient(new SshConfig(hostname, username, password), session);
		boolean isSuccess = this.sshClient.connect();
		if (!isSuccess) {
			session.sendMessage(new TextMessage(new String("连接服务器失败，请核对连接信息")));
			session.close();
		}
	}

	/**
	 * 断开连接
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);

		if (this.sshClient != null) {
			this.sshClient.disconnect();
		}
	}

	/**
	 * 处理消息
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		super.handleMessage(session, message);

		try {
			//当客户端不为空的情况
			if (this.sshClient != null) {
				TextMessage msg = (TextMessage) message;
				if (Arrays.equals(EXIT.getBytes(), msg.asBytes())) {

					if (this.sshClient != null) {
						this.sshClient.disconnect();
					}

					session.close();
					return;
				}

				// 前端传递过来的命令，发送到目标服务器上
				this.sshClient.sendCommand(new String(msg.asBytes(), CHARSET));
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.sendMessage(new TextMessage(new String("WebSocket 发生异常，中断连接")));
			session.close();
		}
	}

}
