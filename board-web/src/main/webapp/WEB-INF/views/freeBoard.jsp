<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function deleteChecker() {
	var checkbox = document.getElementsByName('deleteBox');
	var check_leng = checkbox.length;
	var checkCount = 0;
	
	for( i = 0; i < check_leng; i++ ) {
		if( checkbox[i].checked == true ) {
			checkCount++;
		}
	}
	
	if( checkCount == 0 ) {
		alert('삭제할 게시글을 선택해 주십시오.');	
		return false;
	}
	
	if ( confirm('게시글을 삭제합니까?') ) {
		return true
	}else {
		
	}
	
	return false;
}
</script>
</head>
<body>
<!-- 헤더부분 -->
<jsp:include page="header.jsp"></jsp:include>


<!-- 페이지 소개 및 공지사항 -->
<div class="container" style="margin-top: 40px;">
<div style="font-size: 30pt; margin-bottom: 5px;">자유게시판</div>
<div style="font-size: 14pt; width: 100%; height: 35px; background-color: #f3f6f4; border-radius: 10px; padding-left: 5px; padding-top: 2px;">
<i class="bi bi-megaphone-fill"></i> 공지사항 : <a href="/board/freeBoardView?boardNum=${notice.boardNum}" class="a"><c:out value="${notice.boardTitle}"/></a>
</div>


<!-- 검색 및 글쓰기 -->
<div style="margin-top: 30px;">
<form name="searchFrm" action="/board/search" method="get">
<select name="type" style="margin-top: 10px; height: 40px; width: 100px;">
	<option value="boardTitle">제목</option>
	<option value="memberId">작성자</option>
</select>
<input type="text" name="searchWord" style="height: 40px; width: 300px;">
<button class="btn btn-outline-secondary" style="margin-bottom: 5px;">검색 <i class="bi bi-search"></i></button>
<a href="/board/freeBoardWrite" style="width: 120px; margin-bottom: 10px; margin-top: 10px; font-size: 12pt;" class="btn btn-secondary float-right">글 쓰기</a>
</form>


<!-- 게시글 조회부분 -->
<form name="deleteFrm" action="/board/deleteAdmin" method="post">
<table style="width: 100%;">
	<tr style="text-align: center; background-color: #eeeeee; border: 1px solid #eeeeee; height: 40px;">
		<th style="width: 8%">번호</th>
		<th style="width: 46%">제목</th>
		<th style="width: 15%">작성자</th>
		<th style="width: 15%">작성일</th>
		<th style="width: 8%">추천수</th>
		<th style="width: 8%">조회수</th>
	</tr>
	<c:forEach var="board" items="${list }">
	<tr style="text-align: center; height: 40px; border: 1px solid #eeeeee;">
		<td style="width: 8%"><c:if test="${loginType == 'admin'}"><input type="checkbox" name="deleteBox" value="${board.boardNum }"> </c:if><a href="/board/freeBoardView?boardNum=${board.boardNum }" class="a"><c:out value="${board.boardNum }"/></a></td>
		<td style="width: 46%"><a href="/board/freeBoardView?boardNum=${board.boardNum }" class="a"><c:out value="${board.boardTitle }"/> [${board.comment}]</a></td>
		<td style="width: 15%"><c:out value="${board.memberId }"/></td>
		<td style="width: 15%">${board.timeMin}</td>
		<td style="width: 8%">${board.recommend }</td>
		<td style="width: 8%">${board.readCount}</td>
	</tr>
	</c:forEach>
</table>
<button class="btn btn-secondary float-left" style="margin-top: 10px; margin-bottom: 40px;" onclick="return deleteChecker()">게시글 삭제</button>
</form>


<!--  페이지네이션  -->
<c:if test="${requestPage == 'freeBoard'}">
	<div style="text-align: center; margin-top: 60px;">
		<c:if test="${pageStart != 1}">
			<a href="/board/freeBoard?pageNum=${pageStart-5}" class="btn btn-outline-secondary"><i class="bi bi-caret-left-fill"></i></a>
		</c:if>
		<c:forEach var="i" begin="${pageStart}" end="${pageStart + 4}">
			<c:if test="${ i <= pageCount }">
				<a href="/board/freeBoard?pageNum=${i }" class="btn btn-outline-secondary">${i}</a>
			</c:if>
		</c:forEach>
		<c:if test="${ pageCount >= pageStart+5 }">
			<a href="/board/freeBoard?pageNum=${pageStart+5 }" class="btn btn-outline-secondary"><i class="bi bi-caret-right-fill"></i></a>
		</c:if>
	</div>
</c:if>
<c:if test="${requestPage == 'search'}">
	<div style="text-align: center; margin-top: 60px; margin-bottom: 40px;">
		<c:if test="${pageStart != 1}">
			<a href="/board/search?pageNum=${pageStart-5}&type=${type}&searchWord=${searchWord}" class="btn btn-outline-secondary"><i class="bi bi-caret-left-fill"></i></a>
		</c:if>
		<c:forEach var="i" begin="${pageStart}" end="${pageStart + 4}">
			<c:if test="${ i <= pageCount }">
				<a href="/board/search?pageNum=${i }&type=${type}&searchWord=${searchWord}" class="btn btn-outline-secondary">${i}</a>
			</c:if>
		</c:forEach>
		<c:if test="${ pageCount >= pageStart+5 }">
			<a href="/board/search?pageNum=${pageStart+5 }&type=${type}&searchWord=${searchWord}" class="btn btn-outline-secondary"><i class="bi bi-caret-right-fill"></i></a>
		</c:if>
	</div>
</c:if>
</div>
</div>
</body>
</html>