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
<div class="container">
<h1 style="text-align: center; margin-top: 20px;"> 등록된 공지 사항</h1>
<a href="/board/noticeWrite" style="width: 120px; margin-bottom: 10px; margin-top: 10px; font-size: 12pt;" class="btn btn-secondary float-right">글 쓰기</a>
<table style="width: 100%;">
	<tr style="text-align: center; background-color: #eeeeee; border: 1px solid #eeeeee; height: 40px;">
		<th style="width: 10%">번호</th>
		<th style="width: 90%">요약</th>
	</tr>
	<c:forEach var="board" items="${list }">
	<tr style="text-align: center; height: 40px; border: 1px solid #eeeeee;">
		<td style="width: 10%"><a href="/board/freeBoardView?boardNum=${board.boardNum }" class="a"><c:out value="${board.boardNum }"/></a></td>
		<td style="width: 90%"><a href="/board/freeBoardView?boardNum=${board.boardNum }" class="a"><c:out value="${board.boardTitle }"/></a></td>
	</tr>
	</c:forEach>
</table>
</div>
</body>
</html>