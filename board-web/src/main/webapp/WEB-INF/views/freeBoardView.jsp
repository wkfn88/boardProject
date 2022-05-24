<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.lineUp {
	border-top: 1px solid #D1D1D1;
}

.lineDown {
	border-bottom: 1px solid #D1D1D1;
}

.tdp {
	padding: 5px;
}

</style>
<script type="text/javascript" src="/comment.js">
</script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<!-- 글 제목  -->
<div class="container" style="margin-top: 50px;">
	<table style="width: 100%;">
		<tr class="lineUp">
			<td style="padding-bottom: 10px; padding-top:5px; font-size: 14pt;"><c:out value="${board.boardTitle }"></c:out></td>
		</tr>
		<tr class="lineDown">
			<td style="padding-bottom: 5px;">
				<c:out value="${board.memberId}"></c:out> | ${board.boardDate}
			</td>
			<td style="padding-bottom: 5px; text-align: right;">
				조회수 : ${board.readCount}
			</td>
		</tr>
	</table>
	
	
<!-- 글 본문 -->	
	<div style="text-align: right; margin-top: 10px;">
		<input type="button" value="목록" class="btn btn-outline-secondary" onclick="location.href='/board/freeBoard'">
		<c:if test="${board.anonymous == 0}">
		<input type="button" value="수정" class="btn btn-outline-secondary"  data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="setModalBoard('update')">
		<input type="button" value="삭제" class="btn btn-outline-secondary"  data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="setModalBoard('delete')">
		</c:if>
		<c:if test="${board.anonymous == 1}">
			<c:if test="${board.memberId == loginUser }">
				<input type="button" value="수정" class="btn btn-outline-secondary" onclick="location.href='/board/freeBoardUpdate?boardNum=${board.boardNum}&memberId=${board.memberId}'">
				<input type="button" value="삭제" class="btn btn-outline-secondary" onclick="deleteBoard(${board.boardNum}, '${board.memberId}')">
			</c:if>
		</c:if>
	</div>
	<div style="padding-top: 50px; padding-bottom: 50px; font-family: initial;">
	${board.boardContent}
	</div>
	<div style="text-align: center;">
		<a href="/board/recommend?boardNum=${board.boardNum}" class="btn btn-secondary" style="font-size: 16pt;"><i class="bi bi-hand-thumbs-up"> </i>추천 : ${board.recommend}</a>
	</div>
	

<!-- 댓글 조회 부분  -->
	<table style="width: 100%; margin-top: 20px;">
		<c:if test="${commentList == null }">
			<tr class="lineUp lineDown" style="background-color: #F9F9F9;">
				<td class="tdp" style="width: 100%; text-align: center;">작성된 댓글이 없습니다.</td>
			</tr>
		</c:if>
		<c:if test="${commentList != null }">
			<c:forEach var="comment" items="${commentList}">
				<tr class="lineUp lineDown" style="background-color: #F9F9F9;">
					<td class="tdp" style="width: 20%;"><c:out value="${comment.memberId}"></c:out></td>
					<td class="tdp" style="width: 58%;"><c:out value="${comment.commentContent}"></c:out></td>
					<c:if test="${comment.anonymous == 0}">
						<td class="tdp" style="width: 22%; text-align: right;">${comment.commentDate} | <a href="#" class="a" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="setModalComment(${comment.commentNum}, 'comDelete')">삭제</a></td>
					</c:if>
					<c:if test="${comment.anonymous == 1}">
						<c:if test="${comment.memberId == loginUser }">
							<td class="tdp" style="width: 22%; text-align: right;">${comment.commentDate} | <a href="#" class="a" onclick="deleteComment(${board.boardNum}, ${comment.commentNum}, '${comment.memberId}')">삭제</a></td>
						</c:if>
						<c:if test="${comment.memberId != loginUser }">
							<td class="tdp" style="width: 22%; text-align: right;">${comment.commentDate}</td>
						</c:if>
					</c:if>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	
	
<!-- 댓글 작성 부분  -->
	<form action="/board/comment" method="post" name="comFrm">
	<c:if test="${loginUser == null }">
		<input type="hidden" name="anonymous" value="0">
	</c:if>
	<c:if test="${loginUser != null }">
		<input type="hidden" name="anonymous" value="1">
	</c:if>
	<input type="hidden" name="boardNum" value="${board.boardNum}">
	<c:if test="${loginUser == null }">
		<table style="border: 1px solid #D1D1D1; width: 100%; margin-top: 30px;">
			<tr>
				<td class="tdp" style="width: 15%;">
					<input type="text" name="memberId" class="form-control" placeholder="작성자">
				</td>
				<td class="tdp" style="width: 85%;" rowspan="3">
					<textarea class="form-control" style="width: 100%;" rows="5" name="commentContent"></textarea>
				</td>
			</tr>
			<tr>
				<td class="tdp" style="width: 15%;">
					<input type="password" name="commentPwd" class="form-control" placeholder="비밀번호">
				</td>
			</tr>
			<tr>
				<td class="tdp" style="width: 15%;">
					<input type="submit" onclick="return emptyCheck()" value="댓글등록" class="btn btn-secondary" style="width: 100%;">
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${loginUser != null }">
		<table style="border: 1px solid #D1D1D1; width: 100%; margin-top: 30px;">
			<tr>
				<td class="tdp" style="width: 15%;">
					<input type="text" name="memberId" class="form-control" value="${loginUser }" readonly="readonly">
				</td>
				<td class="tdp" style="width: 85%;" rowspan="2">
					<textarea class="form-control" style="width: 100%;" rows="3" name="commentContent"></textarea>
				</td>
			</tr>
			<tr>
				<td class="tdp" style="width: 15%;">
					<input type="submit" onclick="return emptyCheck2()" value="댓글등록" class="btn btn-secondary" style="width: 100%;">
				</td>
			</tr>
		</table>
	</c:if>
	</form>
</div>


<!-- 모달 -->
<form action="/board/modal" method="post" name="deleteFrm">
<input type="hidden" name="boardNum" value="${board.boardNum }">
<input type="hidden" name="modalType">
<input type="hidden" name="commentNum">
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel">비밀번호 확인</h5>
      </div>
      <div class="modal-body">
       <input type="password" class="form-control" placeholder="비밀번호를 입력해주세요" name="pwd">
      </div>
      <div class="modal-footer">
        <input type="submit" class="btn btn-secondary" style="width: 100px;" value="확인"></input>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="width: 100px;">취소</button>
      </div>
    </div>
  </div>
</div>
</form>
</body>
</html>