<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="/summernote/summernote.min.css" rel="stylesheet">
<script src="/summernote/summernote.min.js"></script>
</head>
<body>
<form action="/testWrite" method="post" name="frm">
<input type="hidden" name="status" value="1">
<c:if test="${loginUser == null }">
<input type="hidden" name="anonymous" value="0">
</c:if>
<c:if test="${loginUser != null }">
<input type="hidden" name="anonymous" value="1">
</c:if>
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
			<td style="padding: 10px; width: 20%;"><input type="password" class="form-control" name="boardPwd" placeholder="비밀번호"></td>
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
			height: 300,                 // 에디터 높이
			minHeight: null,             // 최소 높이
			maxHeight: null,             // 최대 높이
			focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
			lang: "ko-KR",					// 한글 설정
			placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정
			callbacks: {	//여기 부분이 이미지를 첨부하는 부분
				onImageUpload : function(files) {
					uploadSummernoteImageFile(files[0],this);
				},
				onPaste: function (e) {
					var clipboardData = e.originalEvent.clipboardData;
					if (clipboardData && clipboardData.items && clipboardData.items.length) {
						var item = clipboardData.items[0];
						if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
							e.preventDefault();
						}
					}
				}
			}
});
    
    

/**
* 이미지 파일 업로드
*/
function uploadSummernoteImageFile(file, editor) {
	data = new FormData();
	data.append("file", file);
	$.ajax({
		data : data,
		type : "POST",
		url : "/uploadSummernoteImageFile",
		contentType : false,
		processData : false,
		success : function(data) {
        	//항상 업로드된 파일의 url이 있어야 한다.
        	var x = document.createElement("IMG");
			$(editor).summernote('insertImage', data.url, data.filename);
        	alert(data.url);
        	alert(data.filename);
		}
	});
}
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