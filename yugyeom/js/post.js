
$(document).ready(function () { 
    $.datepicker.setDefaults($.datepicker.regional['ko']);

    $( "#startDate" ).datepicker({ 
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
    dateFormat: "yy-mm-dd", minDate: 0, // 현재 이전 날짜는 선택 불가 
    onClose: function( selectedDate ) { //시작일(startDate) datepicker가 닫힐때 
        //종료일(endDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정 
        $("#endDate").datepicker( "option", "minDate", selectedDate ); 
    } 
}); 
    $( "#endDate" ).datepicker({ 
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
    dateFormat: "yy-mm-dd", minDate: 0, // 오늘 이전 날짜 선택 불가 
    onClose: function( selectedDate ) { // 종료일(endDate) datepicker가 닫힐때 
        // 시작일(startDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 시작일로 지정 
        $("#startDate").datepicker( "option", "maxDate", selectedDate ); 
        } 
    });

    $( "#periodDate" ).datepicker({ 
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
        monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
        dateFormat: "yy-mm-dd", minDate: 0, // 오늘 이전 날짜 선택 불가 
        onClose: function( selectedDate ) { // 종료일(endDate) datepicker가 닫힐때 
            // 시작일(startDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 시작일로 지정 
            $("#startDate").datepicker( "option", "maxDate", selectedDate ); 
            } 
        });
});

// 최대인원 수는 숫자로만 입력 가능
$('#maxTutee').on('keypress', function(){
    if((event.keyCode<48)||(event.keyCode>57)){
        event.returnValue=false;
    }
});

// ---------- for viewPost.html ----------

$(document).ready(function () { 
    // 테이블 셀 클릭시 해당 게시글을 조회하는 뷰로 이동하는 부분
    $("#btn-backToNoticeBoard").click(function(){
            $(location).attr('href', "home.html");
    });
    $("#btn-editPost").click(function(){
        $(location).attr('href', "postEditor.html");
});
});