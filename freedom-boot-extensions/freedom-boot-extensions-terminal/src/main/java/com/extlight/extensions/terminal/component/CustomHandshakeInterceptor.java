package com.extlight.extensions.terminal.component;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author MoonlightL
 * @Title: CustomHandshakeInterceptor
 * @ProjectName: freedom-boot
 * @Description: WebSocket 拦截器
 * @DateTime: 2019/8/22 12:24
 */
public class CustomHandshakeInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
		HttpServletRequest servletRequest = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
		attributes.put("hostname", servletRequest.getParameter("hostname"));
		attributes.put("username", servletRequest.getParameter("username"));
		attributes.put("password", servletRequest.getParameter("password"));
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, @Nullable Exception e) {

	}
}
