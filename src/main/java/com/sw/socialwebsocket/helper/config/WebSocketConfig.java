package com.sw.socialwebsocket.helper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.sw.socialwebsocket.handler.WebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	WebSocketHandler handler;
	
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(handler, "/webSocket").withSockJS();
	}

}