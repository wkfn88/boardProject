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
<jsp:include page="header.jsp"></jsp:include>
<div class="container" style="margin-top: 40px;">
<h1>자유게시판</h1>
<hr>
<div>
<a href="/board/freeBoardWrite" style="width: 120px; margin-bottom: 10px; margin-top: 10px; font-size: 12pt;" class="btn btn-secondary float-right">글 쓰기</a>
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
		<td style="width: 8%"><a href="/board/freeBoardView?boardNum=${board.boardNum }" class="a"><c:out value="${board.boardNum }"/></a></td>
		<td style="width: 46%"><a href="/board/freeBoardView?boardNum=${board.boardNum }" class="a"><c:out value="${board.boardTitle }"/> [${board.comment}]</a></td>
		<td style="width: 15%"><c:out value="${board.memberId }"/></td>
		<td style="width: 15%">${board.timeMin}</td>
		<td style="width: 8%">${board.recommend }</td>
		<td style="width: 8%">${board.readCount}</td>
	</tr>
	</c:forEach>
</table>
<div style="text-align: center; margin-top: 20px;">
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
</div>
</div>
</body>
</html>