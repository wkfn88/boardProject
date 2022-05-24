<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:choose>
	<c:when test="${result == 'recommend'}">
		<script type="text/javascript">
			alert('이미 추천하셨습니다.');
			history.back();
		</script>
	</c:when>
	<c:when test="${result == 'notLogin'}">
		<script type="text/javascript">
			alert('로그인이 필요합니다.');
			history.back();
		</script>
	</c:when>
	<c:when test="${result == 'RegistFail'}">
		<script type="text/javascript">
			alert('아이디가 중복됩니다.');
			history.back();
		</script>
	</c:when>
	<c:when test="${result == 'RegistSuccess' }">
		<script type="text/javascript">
			alert('회원가입을 축하합니다.');
			location.href='/';
		</script>
	</c:when>
	<c:when test="${result == 'memberCheckFail' }">
		<script type="text/javascript">
			alert('일치하는 계정이 없습니다.');
			history.back();
		</script>
	</c:when>
	<c:when test="${result == 'comDelSuccess' }">
		<script type="text/javascript">
			alert('댓글을 삭제했습니다.');
			location.href="/board/freeBoardView?boardNum="+${boardNum}
		</script>
	</c:when>
	<c:when test="${result == 'boardDelSuccess' }">
		<script type="text/javascript">
			alert('게시글을 삭제했습니다.');
			location.href="/board/freeBoard";
		</script>
	</c:when>
	<c:when test="${result == 'delFail' }">
		<script type="text/javascript">
			alert('비밀번호가 일치하지 않습니다.');
			history.back();
		</script>
	</c:when>
	<c:when test="${result == 'boardNumError' }">
		<script type="text/javascript">
			alert('존재하지 않는 페이지입니다.');
			history.back();
		</script>
	</c:when>
	
</c:choose>
</body>
</html>