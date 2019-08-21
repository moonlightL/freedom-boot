package com.extlight.extensions.terminal.component;

import com.extlight.extensions.terminal.model.SshClient;
import com.extlight.extensions.terminal.web.config.SshConfig;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private SshConfig sshConfig;

	private SshClient sshClient;

	private static final String EXIT = "exit";

	/**
	 * 建立连接
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);

		this.sshClient = new SshClient(this.sshConfig.getHostname(), this.sshConfig.getUsername(), this.sshConfig.getPassword(), session);
		this.sshClient.connect();
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
				this.sshClient.sendCommand(new String(msg.asBytes(), "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.sendMessage(new TextMessage("An error occured, websocket is closed."));
			session.close();
		}
	}

}
