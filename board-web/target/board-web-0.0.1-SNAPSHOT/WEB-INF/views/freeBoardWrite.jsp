<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
tr {
	height: 40px;
}
</style>
<script type="text/javascript" src="/freeWrite.js">
</script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<form action="/board/freeBoardWriteEvent" method="post" name="frm">
<div class="container" style="margin-top: 40px;">
	<table style="width: 100%;">
		<tr style="background-color: #eeeeee; text-align: center; font-size: 14pt;">
			<td colspan=3>
				게시판 글쓰기
			</td>
		</tr>
		<c:if test="${loginUser == null }">
		<tr style="background-color: #f6f6f6;">
			<td style="padding: 10px; width: 60%;"><input type="text" class="form-control" name="boardTitle" placeholder="글 제목"></td>
			<td style="padding: 10px; width: 20%;"><input type="text" class="form-control" name="memberId" placeholder="작성자"></td>
			<td style="padding: 10px; width: 20%;"><input type="text" class="form-control" name="boardPwd" placeholder="비밀번호"></td>
		</tr>
		</c:if>
		<c:if test="${loginUser != null }">
		<tr style="background-color: #f6f6f6;">
			<td style="padding: 10px; width: 100%;">
				<input type="text" class="form-control" name="boardTitle" placeholder="글 제목">
				<input type="hidden" name="memberId" value="${loginUser}">
			</td>
		</tr>
		</c:if>
	</table>
	<div style="margin-top: 20px;">
	<textarea id="summernote" name="boardContent"></textarea>
	    <script>
	      $('#summernote').summernote({
	        placeholder: '게시글을 입력해 주세요',
	        tabsize: 2,
	        height: 400,
	        toolbar: [
	          ['style', ['style']],
	          ['font', ['bold', 'underline', 'clear']],
	          ['color', ['color']],
	          ['para', ['ul', 'ol', 'paragraph']],
	          ['table', ['table']],
	          ['insert', ['link', 'picture', 'video']],
	        ]
	      });
	    </script>
	</div>
	<div style="margin-top: 10px; text-align: right;">
		<input type="submit" value="게시물 등록" class="btn btn-secondary" style="margin-right: 10px;" onclick="return emptyCheck()">
		<input type="button" value="나가기" class="btn btn-secondary" onclick="location.href='/board/freeBoard'">
	</div>
</div>
</form>
</body>
</html>