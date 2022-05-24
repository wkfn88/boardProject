function emptyCheck() {
	if( document.comFrm.memberId.value.length < 2 ) {
		alert('작성자를 두 글자 이상 입력해주세요.');
		return false;
	}else if( document.comFrm.commentPwd.value.length < 2 ) {
		alert('비밀번호를 두 글자 이상 입력해주세요.');
		return false;
	}else if( document.comFrm.commentContent.value.length == 0 ) {
		alert('내용을 입력해주세요.');
		return false;
	}
	
	return true;
}