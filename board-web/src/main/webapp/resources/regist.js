function pwdCheck() {
	var pwd1 = document.registFrm.memberPwd.value;
	var pwd2 = document.registFrm.memberPwdCheck.value;
	
	if( pwd1.length == 0 || pwd2.length == 0 ) {
		document.getElementById('pwdDiv').innerHTML = '비밀번호가 일치하지 않습니다.';
	} else if( pwd1 != pwd2 ) {
		document.getElementById('pwdDiv').innerHTML = '비밀번호가 일치하지 않습니다.';
	}else {
		document.getElementById('pwdDiv').innerHTML = '비밀번호가 일치합니다.';
	}
}

function idCheck() {
	if( document.registFrm.memberId.value.length == 0 ) {
		alert('아이디를 입력해주세요.');
		return false;
	}
	return true;
}

function emptyCheck() {
	if( document.registFrm.memberId.value.length == 0 ) {
		alert('아이디를 입력해주세요.');
		return false;
	}else if( document.registFrm.memberEmail.value.length == 0 ) {
		alert('이메일을 입력해주세요.');
		return false;
	}else if( document.registFrm.memberPwd.value.length == 0 ) {
		alert('비밀번호를 입력해주세요.');
		return false;
	}else if( document.registFrm.memberPwdCheck.value.length == 0 ) {
		alert('비밀번호를 입력해주세요.');
		return false;
	}else if( document.registFrm.memberPwd.value != document.registFrm.memberPwdCheck.value ) {
		alert('비밀번호가 일치하지 않습니다.');
		return false;
	}
	
	
	var id = document.registFrm.memberId.value;
	for(var i = 0; i < id.length; i++ ) {
		if( id.charAt(i) < '0' ) {
			alert("특수문자는 입력할 수 없습니다.");
			return false;
		}else if( id.charAt(i) > '9' && id.charAt(i) < 'A' ) {
			alert("특수문자는 입력할 수 없습니다.");
			return false;
		}
	}
	return true;
}