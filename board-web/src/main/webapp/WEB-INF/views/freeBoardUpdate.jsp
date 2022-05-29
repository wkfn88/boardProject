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
<form action="/board/updateBoard" method="post" name="frm">
<input type="hidden" name="boardNum" value="${board.boardNum }">
<div class="container" style="margin-top: 40px;">
	<table style="width: 100%;">
		<tr style="background-color: #eeeeee; text-align: center; font-size: 14pt;">
			<td colspan=3>
				게시판 수정하기
			</td>
		</tr>
		<tr style="background-color: #f6f6f6;">
			<td style="padding: 10px; width: 100%;"><input type="text" class="form-control" name="boardTitle" placeholder="글 제목" value="${board.boardTitle }"></td>
		</tr>
	</table>
	<div style="margin-top: 20px;">
	<textarea id="summernote" name="boardContent">${board.boardContent}</textarea>
	    <script>
	      $('#summernote').summernote({
	        placeholder: '게시글을 입력해 주세요',
	        tabsize: 2,
	        height: 400,
	        toolbar: [
	          ['style', ['style']],
	          ['font', ['bold', 'underline', 'clear']],
	          ['fontname', ['fontname']],
	          ['fontsize', ['fontsize']],
	          ['color', ['color']],
	          ['para', ['ul', 'ol', 'paragraph']],
	          ['table', ['table']],
	          ['insert', ['link', 'picture', 'video']],
	        ],
	        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
			fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
	      });
	    </script>
	</div>
	<div style="margin-top: 10px; text-align: right;">
		<input type="submit" value="게시물 등록" class="btn btn-secondary" style="margin-right: 10px;" onclick="return emptyCheck()">
		<input type="button" value="나가기" class="btn btn-secondary" onclick="location.href='/board/freeBoardView?boardNum=${board.boardNum}'">
	</div>
</div>
</form>
</body>
</html>