function emptyCheck() {
	if( document.comFrm.memberId.value.length < 2 ) {
		alert('작성자를 두 글자 이상 입력해주세요.');
		return false;
	}
	
	if( document.comFrm.commentPwd.value.length < 2 ) {
		alert('비밀번호를 두 글자 이상 입력해주세요.');
		return false;
	}
	
	if( document.comFrm.commentContent.value.length == 0 ) {
		alert('내용을 입력해주세요.');
		return false;
	}
	
	return true;
}

function emptyCheck2() {
	if( document.comFrm.commentContent.value.length == 0 ) {
		alert('내용을 입력해주세요.');
		return false;
	}
	return true;
}

function setModalBoard(type) {
	document.deleteFrm.modalType.value = type;
}


function setModalComment(commentNum, type) {
	document.deleteFrm.commentNum.value = commentNum;
	document.deleteFrm.modalType.value = type;
}

function deleteBoard(boardNum, memberId) {
	if(confirm('삭제하시겠습니까?')) {
		location.href="/board/deleteBoard?boardNum="+boardNum+"&memberId="+memberId;
	}else {
		
	}
}

function deleteComment(boardNum, commentNum, memberId) {
	if(confirm('삭제하시겠습니까?')) {
		location.href="/board/deleteComment?boardNum="+boardNum+"&commentNum="+commentNum+"&memberId="+memberId;
	}else {
		
	}
}
