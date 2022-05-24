<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<!--  bootstrap -->
<script src="/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

<!-- textEditor -->
<link href="/editor/summernote-lite.min.css" rel="stylesheet">
<script src="/editor/summernote-lite.min.js"></script>

<!-- custom css -->
<link rel="stylesheet" href="/custom.css">

<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<div class="container" style="text-align: center; height: 100px;">
	<a href="/index"><img src="/title.png" style="height: 100%"></a>
</div>
<nav class="navbar navbar-light" style="background-color: #c2dce4;">
  <div class="container-fluid" style="width: 55%;">
  	<div>
  		<a class="navbar-brand" href="/index" style="margin-right: 30px;">메인페이지</a>
  		<a class="navbar-brand" href="/board/freeBoard" style="margin-right: 30px;">자유게시판</a>
  		<a class="navbar-brand" href="#" style="margin-right: 30px;">토론게시판</a>
  	</div>
  	<form action="/member/loginEvent" method="post">
  	<div class="nav-item dropdown">
  		<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: black; font-size: 14pt;">
           <c:if test="${loginUser == null }">로그인</c:if>
           <c:if test="${loginUser != null }">내 정보</c:if>
        </a>
        <c:if test="${loginUser == null }">
        	<ul class="dropdown-menu" aria-labelledby="navbarDropdown" style="text-align: center; width: 200px;">
	            <li style="padding: 5px;"><input type="text" name="userId" placeholder="아이디 입력" style="width: 100%;" class="form-control"></li>
	            <li style="padding: 5px;"><input type="password" name="userPwd" placeholder="비밀번호 입력" style="width: 100%;" class="form-control"></li>
	            <li style="padding: 5px;"><input type="submit" value="로그인" style="width: 100%;" class="btn btn-secondary"></li>
	            <li><hr class="dropdown-divider"></li>
	            <li><a class="dropdown-item" href="/member/registPage">회원가입</a></li>
         	</ul>
        </c:if>
        <c:if test="${loginUser != null }">
        	<ul class="dropdown-menu" aria-labelledby="navbarDropdown" style="text-align: center; width: 200px;">
	            <li style="padding: 5px;">사용자 : ${loginUser}</li>
	            <li style="padding: 5px;"><input type="button" value="마이페이지" style="width: 90%;" class="btn btn-secondary"></li>
	            <li><hr class="dropdown-divider"></li>
	            <li><a class="dropdown-item" href="/member/logout">로그아웃</a></li>
         	</ul>
        </c:if>
  	</div>
  	</form>
  </div>
</nav>
</body>
</html>