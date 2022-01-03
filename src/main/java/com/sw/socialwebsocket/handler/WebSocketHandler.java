package com.sw.socialwebsocket.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.socialwebsocket.controller.WebSocketController;
import com.sw.socialwebsocket.service.MemberService;
import com.sw.socialwebsocket.service.MemberServiceImpl;
import com.sw.socialwebsocket.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	// 세션 리스트
	private List<Map<String, Object>> sessionList = new ArrayList<Map<String, Object>>();
	private MemberVO member = new MemberVO();
	private String lastEnterUserName = null;
	private String bangID;
	
	@Autowired
	MemberService memberservice;

	public void loginMember(MemberVO membervo) {
		member = membervo;
	}
	
	// 클라이언트가 서버로 메세지 전송 처리
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		
		// JSON --> Map으로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> mapReceive = objectMapper.readValue(message.getPayload(), Map.class);

		switch (mapReceive.get("cmd")) {

		// CLIENT 입장
		case "CMD_ENTER":
			// 세션 리스트에 저장
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bang_id", mapReceive.get("bang_id"));
			map.put("session", session);
			map.put("sessionID", session.getId());
			map.put("nickName", member.getNickName());
			sessionList.add(map);

			lastEnterUserName = member.getNickName();
			if(lastEnterUserName.isEmpty()||lastEnterUserName==null) {
				break;
			}
			bangID = (String) mapReceive.get("bang_id");
			for (int i = 0; i < sessionList.size(); i++) {
				WebSocketSession sess = (WebSocketSession) sessionList.get(i).get("session");
				
				Map<String, String> mapToSend = new HashMap<String, String>();
				mapToSend.put("bang_id", bangID);
				mapToSend.put("cmd", "CMD_ENTER");
				mapToSend.put("msg", lastEnterUserName + "님이 입장 했습니다.");
				
				String jsonStr = objectMapper.writeValueAsString(mapToSend);
				sess.sendMessage(new TextMessage(jsonStr));
			}
			break;

		// CLIENT 메세지
		case "CMD_MSG_SEND":
			// 같은 채팅방에 메세지 전송
			String sendUser="";
			for (int i = 0; i < sessionList.size(); i++) {
				Map<String, Object> sendMap = sessionList.get(i);
				String bang_id = (String) sendMap.get("bang_id");
				String nickName = (String) sendMap.get("nickName");
				WebSocketSession sess = (WebSocketSession) sendMap.get("session");

				if (session.equals(sess)) {
					sendUser = (String) sendMap.get("nickName");
					break;
				}
			}
			for (int i = 0; i < sessionList.size(); i++) {
				WebSocketSession sess = (WebSocketSession) sessionList.get(i).get("session");
				
				Map<String, String> mapToSend = new HashMap<String, String>();
				mapToSend.put("bang_id", bangID);
				mapToSend.put("cmd", "CMD_MSG_SEND");
				mapToSend.put("msg", sendUser + " : " + mapReceive.get("msg"));
				
				String jsonStr = objectMapper.writeValueAsString(mapToSend);
				sess.sendMessage(new TextMessage(jsonStr));
			}
			break;
		}
	}

	// 클라이언트가 연결을 끊음 처리
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		super.afterConnectionClosed(session, status);

		// JSON -> Map 변환
		ObjectMapper objectMapper = new ObjectMapper();
		String now_bang_id = "";

		String disconnectUser = null;
		// 사용자 세션을 리스트에서 제거
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> map = sessionList.get(i);
			String bang_id = (String) map.get("bang_id");
			String nickName = (String) map.get("nickName");
			WebSocketSession sess = (WebSocketSession) map.get("session");

			if (session.equals(sess)) {
				now_bang_id = bang_id;
				disconnectUser = nickName;
				sessionList.remove(map);
				break;
			}
		}
		// 같은 채팅방에 퇴장 메세지 전송
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> mapSessionList = sessionList.get(i);
			WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");

			if (bangID.equals(now_bang_id)) {
				Map<String, String> mapToSend = new HashMap<String, String>();
				mapToSend.put("bang_id", bangID);
				mapToSend.put("cmd", "CMD_EXIT");
				mapToSend.put("msg", disconnectUser + "님이 퇴장 했습니다.");

				String jsonStr = objectMapper.writeValueAsString(mapToSend);
				sess.sendMessage(new TextMessage(jsonStr));
			}
		}
	}
}