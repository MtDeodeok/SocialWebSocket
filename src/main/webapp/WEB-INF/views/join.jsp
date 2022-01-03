<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form id='joiner' action="joiner" method='post'>
		<input type='text' style='display: none' readonly id='profile' name='profile' value=${sessionScope.loginMember.getProfile()}>
		<input type='text' readonly id='email' name='email' value=${sessionScope.loginMember.getEmail()}></br>
		<input type='text' readonly id='provider' name='provider' value=${sessionScope.loginMember.getProvider()}></br>
		<input type='text' readonly id='name' name='name' value=${sessionScope.loginMember.getName()}></br>
		<input type='text' id='nickName' name='nickName' value="닉네임을 입력하세요">
		<div>
			<c:choose>
				<c:when test="${sessionScope.state eq '신규'}">
					<button type='submit'>채팅방 가입</button>
				</c:when>
				<c:otherwise>
					<button type='submit'>채팅방 입장</button>
				</c:otherwise>
			</c:choose>
			<button type='button' onclick="location.href='login.jsp'">나가기</button>
		</div>
	</form>
</body>
</html>