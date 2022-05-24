<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/regist.js">
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container" style="margin: auto; width: 500px; margin-top: 40px; background-color: #f2f4f5; padding: 30px;">
	<form action="/member/registEvent" method="post" name="registFrm">
		<h2 style="text-align: center;">회원가입 페이지</h2>
		<hr>
		<div class="mb-3">
			<label for="formGroupExampleInput" class="form-label">아이디</label> 
			<input type="text" name="memberId" class="form-control" id="formGroupExampleInput" placeholder="아이디를 입력하세요." value="${mVo.memberId }">
			<input type="submit" name="action" value="아이디 중복 확인" class="btn btn-secondary" style="margin-top: 10px;" onclick="return idCheck()"> ${message }
		</div>
		<hr>
		<div class="mb-3">
			<label for="formGroupExampleInput2" class="form-label">이메일</label> 
			<input type="text" name="memberEmail"class="form-control" id="formGroupExampleInput2" placeholder="이메일을 입력하세요." value="${mVo.memberEmail }">
		</div>
		<hr>
		<div class="mb-3">
			<label for="formGroupExampleInput2" class="form-label">비밀번호</label> 
			<input type="password" name="memberPwd" class="form-control" id="formGroupExampleInput2" placeholder="비밀번호를 입력하세요." onkeyup="pwdCheck()">
		</div>
		<div class="mb-3">
			<label for="formGroupExampleInput2" class="form-label">비밀번호 확인</label> 
			<input type="password" name="memberPwdCheck" class="form-control" id="formGroupExampleInput2" placeholder="비밀번호를 다시 입력하세요." onkeyup="pwdCheck()">
			<div id="pwdDiv"></div>
		</div>
		<hr>
		<div class="mb-3" style="text-align: center;">
			<input type="submit" name="action" value="회원가입" class="btn btn-secondary" style="margin-top: 10px; width: 100px;" onclick="return emptyCheck()">
			<input type="button" value="나가기" class="btn btn-secondary" style="margin-top: 10px; width: 100px;" onclick="location.href='/index'">
		</div>
		</form>
	</div>
</body>
</html>