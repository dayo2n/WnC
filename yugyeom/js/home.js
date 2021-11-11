$(document).ready(function () { 
    // 테이블 셀 클릭시 해당 게시글을 조회하는 뷰로 이동하는 부분
    $("#notice_board_title tr").click(function(e){
        var rowIdx = e.target.closest("tr").rowIndex;
        if(rowIdx !== 0){ // th가 인덱스 0이므로 게시글 인덱스는 1부터 시작
            $(location).attr('href', "viewPost.html"); // 경로 바꿔야함
        }
    });
});