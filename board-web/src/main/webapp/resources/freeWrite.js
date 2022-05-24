function emptyCheck() {
	if( document.frm.boardTitle.value.length < 2 ) {
		alert('제목을 두 글자 이상 적어주세요.');
		return false;
	}else if( document.frm.memberId.value.length < 2 ) {
		alert('작성자를 두 글자 이상 적어주세요.');
		return false;
	}else if( document.frm.boardPwd.value.length < 2 ) {
		alert('비밀번호를 두 글자 이상 적어주세요.')
		return false;
	}else if( document.frm.boardContent.value.length == 0 ) {
		alert('게시글을 적어주세요.');
		return false;
	}
	return true;
}