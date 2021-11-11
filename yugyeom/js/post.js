
// edior 타입은 두 개. 글 수정용 에디터 : "editEditor", 새 글 작성용 데이터 : "newEditor"
// ---------- for postEditor.html ----------

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
    
    $('#lessonType').change(function(){
        if($(this).val()==='PERSONAL'){
            $('tbody tr').eq(2).remove();
        }else{
            $('tbody tr').eq(1).after('<tr><td>모집인원</td><td><input type="text" placeholder="최대인원을 입력하세요." class="writeComponent" id="maxStudentCount" maxlength=20 style="width:100%"></td></tr>');
                    // 최대인원 수는 숫자로만 입력 가능
            $('#maxStudentCount').on('keypress', function(){
                if((event.keyCode<48)||(event.keyCode>57)){
                    event.returnValue=false;
                }
            });

        }
    });

    $("#btn-backToNoticeBoard").click(function(){
        // viewPost /  editPost 모드 구분
        data = location.href.split("?")[1];
        types = data.split("&");
        editorType = types[0].split("=")[1];
        postType = types[1].split("=")[1];
        console.log(editorType + " " +postType);

        if(postType==="editPost"){
            var flag = confirm('작성한 내용은 저장되지 않습니다. 글 작성을 취소하시겠습니까?');
            console.log(flag);
            if(flag){
                $(location).attr('href', "home.html");
            }
        }else{ // postType === "viewPost"
            $(location).attr('href', "home.html");
        }
    });

    // "글 등록" 버튼 클릭 액션
    $("#btn-postNewNotice").click(function(){
        data = location.href.split("?")[1];
        types = data.split("&");
        editorType = types[0].split("=")[1];
        postType = types[1].split("=")[1];
        console.log(editorType + " " +postType);

        if(postType === "editPost"){ // 에디터에서 
            if(editorType === "newEditor"){  // 새 글작성 모드 
                // 새 데이터 추가 코드 구현 
            }else{ // editorType === "editEditor", 기존 글 수정 모드

            }
        }
    });
});



// ---------- for viewPost.html ----------

$(document).ready(function () { 

    // 글 수정 클릭시
    $("#btn-editPost").click(function(){
        var editorType = "editEditor";
        var postType = "editPost";
        // 기존에 작성된 내용을 불러와야함
        $(location).attr('href', "postEditor.html?editorType=" +  editorType + "&postType=" + postType);
    });
});