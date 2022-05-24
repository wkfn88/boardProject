function searchBoard() {
	if( document.searchFrm.searchWord.value.length == 0 ) {
		location.href="/board/freeBoard";
	}else {
		var type = document.searchFrm.type.value;
		var searchWord = document.searchFrm.searchWord.value;
		location.href="/board/search?type="+type+"&searchWord="+searchWord;
	}
}