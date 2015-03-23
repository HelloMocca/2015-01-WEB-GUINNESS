<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<title>스터디의 시작, 기네스</title>
<meta charset="utf-8">
<link rel="stylesheet"
	href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css">
<link rel="stylesheet" href="/css/mainStyle.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
</head>
<body>
	<%@ include file="/commons/_topnav.jspf"%>
	<div class='content wrap' style='margin-top: 100px'>

		<ul id='group-container' class='group-list'>
			<li id='create-new'>새 스터디 그룹 생성...</li>
		</ul>
	</div>

	<!-- 그룹생성을 위한 Modal -->
	<div id='black-cover' style="display: none;">
		<div id='createGroup-container'>
			<div id='createGroup-header'>
				<div id='createGroup-title'>새 스터디 그룹 생성</div>
				<div id='createGroup-close'>
					<i class='fa fa-remove'></i>
				</div>
			</div>
			<div id='createGroup-body'>
				<form name="user" method="post" action="/group/create">
					<c:if test="${not empty errorMessage}">
						<div>
							<label>${errorMessage}</label>
						</div>
					</c:if>
					<div>
						그룹이름 <input type="text" name="groupName">
					</div>
					<div>
						<input type="radio" name="isPublic" value="private" checked>비공개
						<input type="radio" name="isPublic" value="public">공개
					</div>
					<br />
					<button id='createGroup-submit' class='btn'>생성</button>
				</form>
			</div>
		</div>
	</div>
	<script>
		function validator() {
			var errorMessage = '${errorMessage}';
			console.log(errorMessage);
			if( errorMessage != null){
				console.log("1"+errorMessage);
				errorMessage = null;
				return false;
			}
			return true;
		}
		window.addEventListener('load', function() {
			var req = new XMLHttpRequest();
			var json = null;
			req.onreadystatechange = function() {
				if (req.readyState == 4) {
					if (req.status == 200) {
						json = JSON.parse(req.responseText);
						createGroup(json);
					}
				}
			};
			req.open('get', '/group/read', true);
			req.send();
			
			var errorMessage = '${errorMessage}';

<<<<<<< HEAD
			if(errorMessage !== '') {
				var blkcvr = document.getElementById('black-cover');
				blkcvr.style.display = "block";
			}
			
			var el = document.getElementById('create-new');
			el.addEventListener('mouseup', showModal, false);
			el = document.getElementById('createGroup-close');
			el.addEventListener('mouseup', showModal, false);
		}, false);
=======
  function createGroup(json){
    var el = document.getElementById('group-container');
    var obj = null;
    for(var i = 0; i < json.length; i++){
        obj = json[i];
        var newEl = document.createElement("a");
        newEl.setAttribute("href","/g/"+obj.groupId);
        newEl.innerHTML = "<li>"+obj.groupName+"</li>";
        el.appendChild(newEl);
    }
  }
  
  function showModal(e) {
	var blkcvr = document.getElementById('black-cover');
	if (blkcvr.style.display == "none") {
		blkcvr.style.display = "block";
	} else {
		blkcvr.style.display = "none";
	}
  }
>>>>>>> branch 'master' of https://github.com/NHNNEXT/2015-01-WEB-GUINNESS.git

		function createGroup(json) {
			var el = document.getElementById('group-container');
			var obj = null;
			for (var i = 0; i < json.length; i++) {
				obj = json[i];
				var newEl = document.createElement("a");
				newEl.setAttribute("href", "/b/" + obj.groupId);
				newEl.innerHTML = "<li>" + obj.groupName + "</li>";
				el.appendChild(newEl);
			}
		}

		function showModal(e) {
			var blkcvr = document.getElementById('black-cover');
			if (blkcvr.style.display == "none") {
				blkcvr.style.display = "block";
			} else {
				blkcvr.style.display = "none";
			}
		}
	</script>
</body>
</html>
