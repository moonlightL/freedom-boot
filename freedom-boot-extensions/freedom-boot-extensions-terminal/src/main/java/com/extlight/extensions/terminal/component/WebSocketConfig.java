package com.extlight.extensions.terminal.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @Author MoonlightL
 * @Title: WebSocketConfig
 * @ProjectName: freedom-boot
 * @Description: websocket 配置
 * @DateTime: 2019/8/21 16:23
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketServer(), "/webSocketServer")
				.addInterceptors(handshakeInterceptor())
				.setAllowedOrigins("*");
	}

	@Bean
	public WebSocketHandler webSocketServer() {
		return new CustomWebSocketHandler();
	}

	@Bean
	public HandshakeInterceptor handshakeInterceptor(){
		return new CustomHandshakeInterceptor();
	}
}
