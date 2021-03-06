<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html lang="ko">
<head>
<title>스터디의 시작, 페이퍼민트</title>
<meta charset="utf-8">
<%@ include file="./commons/_favicon.jspf"%>
<link rel="stylesheet"
	href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css">
<link rel="stylesheet" href="/css/mainStyle.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
</head>
<style>
#editProfile-photoArea img.avatar{
  width:200px;
  height:200px;
  background-color:#558;
  border-radius:5px;
  overflow:hidden;
  margin-bottom:10px;
}
#editProfile-form p {
  margin-top:0px;
}
#editProfile-form label {
  display:block;
  font-weight:bold;
}
#editProfile-form input[type='text'], #editProfile-form input[type='password']{
  width:200px;
  padding:10px;
  font-size:15px;
  border-radius:4px;
  margin-bottom:4px;
}

#editProfile-form input[name='userPhoneNumber']{
  width:150px;
}
#editProfile-form span.info {
  display:block;
  font-size:12px;
  color:#9e9ea6;
}
#editProfile-form span.info strong{
  color:#ff5a5a
}

</style>
<body>
	<%@ include file="./commons/_topnav.jspf"%>
	<div class='content wrap' style='margin-top: 80px'>
		<h1><i class='fa fa-user'></i><span style='margin-left:10px;'>회원정보수정</span></h1>
		<div id='profile-panel' class='panel'>
			<table id='editProfile-panel-body' class='panel-body' style='width:100%'>
				<tr>
				<td valign=top id='editProfile-photoArea' style='width:200px; text-align:center;'>
					<img class='avatar' src="/img/avatar-default.png">
					<button class='btn btn-pm'>이미지 수정</button>
				</td>
				<td valign=top id='editProfile-profileArea' style='padding-left:25px;'>
					<form id='editProfile-form' method='post' action='/user/update'>
						<p>
							<label for="userName">First & Last Name</label>
							<input name="userName" type="text" placeholder="Your Name" required value="${sessionUserName}"></input>
							<span class="info"><strong>[필수사항]</strong>스터디메이트들과의 소통을 위한 이름을 입력하세요.</span>
						</p>
						<p>
							<label for="userNewPassword">Change Password</label>
							<input name="userNewPassword" type="password" placeholder=''></input>
							<span class="info">비밀번호를 변경하시려면 새로운 비밀번호를 입력하세요.</span>
						</p>
						<hr/>
						<p>
							<label for="userPassword">Password</label>
							<input name="userPassword" type="password" placeholder=''></input>
							<span class="info"><strong>[필수사항]</strong>정보변경을 완료하기 위해 비밀번호를 입력해주세요.</span>
						</p>
						<c:if test="${not empty message}">
							<span class="errorMessage"> ${message} <br /></span>
						</c:if>
						<button type="submit" class="btn btn-pm">수정</button>
					</form>
				</td>
				</tr>
			</table>
		</div>
	</div>
	<script>
		//TODO 개인정보를 Ajax로 요청하여 화면에 출력
		window.addEventListener('load', function() {
			var req = new XMLHttpRequest();
			var json = null;
			req.onreadystatechange = function() {
				if (req.readyState == 4) {
					if (req.status == 200) {
						json = JSON.parse(req.responseText);
					}
				}
			};
			//req.open('get', '/group/read', true);
			//req.send();
		}, false);
	</script>
</body>
</html>
