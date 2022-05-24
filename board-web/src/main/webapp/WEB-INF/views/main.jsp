<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.tdp{
	padding: 5px;
	text-align: center;
}
</style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="container" style="margin-top: 30px;">
	<div class="jumbotron" style="height: 400px; background-image: url('/welcomeBanner.png'); background-size: cover;">
		<div style="margin-top: 60px; margin-left: 30px; float: left;">
		  <h1>MainPage to Spring</h1>
		  <p style="font-size: 16pt;">스프링으로 제작된 간단한 게시판 사이트입니다.</p>
		  <a class="btn btn-secondary btn-lg" href="/board/freeBoard" role="button">글 쓰러 가기</a>
		</div>
	</div>
</div>
<div class="container">
	<div style="float: left; width: 50%; height: 300px;">
		<div style="margin: auto; width: 97%; height: 100%; border: 1px solid black; float: left;">
			<table style="margin: auto; width: 100%">
				<tr style="height: 50px;">
					<th colspan="3" style="background-color: #eeeeee; text-align: center; font-size: 14pt;">오늘의 최다 추천 게시글</th>
				</tr>
				<tr style="background-color: #f8f8f8; height: 40px;">
					<th style="width: 70%; text-align: center;">
					제목
					</th>
					<th style="width: 15%; text-align: center;">
					조회
					</th>
					<th style="width: 15%; text-align: center;">
					추천
					</th>
				</tr>
				<c:forEach var="freeBoard" items="${recommendList}">
				<tr style="height: 40px;">
					<td class="tdp" style="text-align: left; padding-left: 10px;"><a class="a" href="/board/freeBoardView?boardNum=${freeBoard.boardNum }"><c:out value="${freeBoard.boardTitle}"></c:out></a></td>
					<td class="tdp">${freeBoard.readCount }</td>
					<td class="tdp">${freeBoard.recommend }</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<div style="float: left; width: 50%; height: 300px;">
		<div style="margin: auto; width: 97%; height: 100%; border: 1px solid black; float: right;">
			<table style="margin: auto; width: 100%">
				<tr style="height: 50px;">
					<th colspan="3" style="background-color: #eeeeee; text-align: center; font-size: 14pt;">자유게시판</th>
				</tr>
				<tr style="background-color: #f8f8f8; height: 40px;">
					<th style="width: 70%; text-align: center;">
					제목
					</th>
					<th style="width: 15%; text-align: center;">
					조회
					</th>
					<th style="width: 15%; text-align: center;">
					추천
					</th>
				</tr>
				<c:forEach var="freeBoard" items="${boardList}">
				<tr style="height: 40px;">
					<td class="tdp" style="text-align: left; padding-left: 10px;"><a class="a" href="/board/freeBoardView?boardNum=${freeBoard.boardNum }"><c:out value="${freeBoard.boardTitle}"></</c:out></a></td>
					<td class="tdp">${freeBoard.readCount }</td>
					<td class="tdp">${freeBoard.recommend }</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
</body>
</html>