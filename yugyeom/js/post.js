
var token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLshKDsg50xIiwiYXV0aCI6IlJPTEVfQkFTSUMiLCJleHAiOjE2MzY4MTYyMjd9.12vfH1Ciy09hF2cNgJJEyZnh_V-EoDtnVDWn3B5r8UBBsZtw0sGqUckD0uRR8CuupTTLzYTVgk4WGL6AWDgCsQ';
// edior 타입은 두 개. 글 수정용 에디터 : "editEditor", 새 글 작성용 데이터 : "newEditor"
// ---------- for postEditor.html ----------

$(document).ready(function () {

    // viewPost /  editPost 모드 구분
    var data = location.href.split("?")[1];
    var types = data.split("&");
    var editorType = types[0].split("=")[1]; // none newEditor editEditor
    var postType = types[1].split("=")[1]; // editPost viewPost
    console.log(editorType + " " +postType);


        // 해당 게시글에 대한 기존 내용이 불러져와야함
    if(editorType ===  "editEditor" && postType === "editPost"){

        var postIdx = types[2].split("=")[1];

        fetch("http://219.255.114.140:8090/lesson/"+postIdx,{
            method: "GET",
            headers : {"Authorization" : `Bearer ${token}` }
            })
            .then(response => {
              return response.json();
            })
            .then(data => {
                console.log(data);
                console.log(data.lessonType);
                $('#lessonType').attr('disabled', 'true'); // true
                $('#title').val(data.title);
                if(data.lessonType === "PERSONAL"){
                }else{
                    $('#lessonType').val("GROUP").prop('selected', true); // true
                    $('tbody tr').eq(1).after('<tr><td>모집인원</td><td><input type="text" placeholder="최대인원을 입력하세요." class="writeComponent" id="maxStudentCount" maxlength=20 style="width:100%"></td></tr>');
                    // 최대인원 수는 숫자로만 입력 가능
                    $('tbody tr').eq(2).after('<tr><td>과외기간</td><td><input type=" text" class="writeComponent" id="startDate" placeholder="시작일자 yyyy-mm-dd"> ~<input type="text" class="writeComponent" id="endDate" placeholder="종료일자 yyyy-mm-dd"></td></tr>');
                    $('#maxStudentCount').val(data.maxStudentCount);
                    $('#startDate').val(data.startPeriod.slice(0,10));
                    $('#endDate').val(data.endPeriod.slice(0,10));
                }
                $('#content').val(data.content);


                $.datepicker.setDefaults($.datepicker.regional['ko']);

                $( "#startDate" ).datepicker({ 
                dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
                monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
                monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
                dateFormat: "yy-mm-dd", minDate: 1, // 현재 이전 날짜는 선택 불가 
                onClose: function( selectedDate ) { //시작일(startDate) datepicker가 닫힐때 
                    //종료일(endDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정 
                    $("#endDate").datepicker( "option", "minDate", selectedDate ); 
                    } 
                }); 
                $( "#endDate" ).datepicker({ 
                dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
                monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
                monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
                dateFormat: "yy-mm-dd", minDate: 1, // 오늘 이전 날짜 선택 불가 
                onClose: function( selectedDate ) { // 종료일(endDate) datepicker가 닫힐때 
                    // 시작일(startDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 시작일로 지정 
                    $("#startDate").datepicker( "option", "maxDate", selectedDate ); 
                    } 
                });
            });
    }

    if(postType === "viewPost"){
        var tr = $('#table tr');
        var td = tr.children();
        var postIdx = types[2].split("=")[1];
        fetch("http://219.255.114.140:8090/lesson/"+postIdx,{
            method: "GET",
            headers : {"Authorization" : `Bearer ${token}` }
            })
            .then(response => {
              return response.json();
            })
            .then(data => {
                console.log(data);
                console.log(data.lessonType);
                $('#postViewTable tr:eq(0) th:eq(0)').text(data.title);
                $("#postViewTable tr:eq(1) td:eq(1)").text(data.lessonType);
                $('#postViewTable tr:eq(1) td:eq(3)').text(data.views);
                if(data.lessonType === "PERSONAL"){
                    $('#postViewTable tr:eq(2) td:eq(1)').text(1);
                    $('#postViewTable tr:eq(3) td:eq(1)').text("개인과외는 개인 조정");
                }else{
                    $('#postViewTable tr:eq(2) td:eq(1)').text(data.maxStudentCount);
                    $('#postViewTable tr:eq(3) td:eq(1)').text(data.startPeriod.slice(0, 10) + " ~ " + data.endPeriod.slice(0,10));
                }
                $('#postViewTable tr:eq(4) td:eq(0)').text(data.content);

                    // 글 수정 클릭시
                $("#btn-editPost").click(function(){
                    var editorType = "editEditor";
                    var postType = "editPost";
                    $(location).attr('href', "postEditor.html?editorType=" +  editorType + "&postType=" + postType + '&postID=' + postIdx);
                });

                // 가입 신청 시
                $("#btn-register").click(function(){
                    fetch("http://219.255.114.140:8090/lesson/"+postIdx+"/apply",{
                        method: "POST",
                        headers : {"Authorization" : `Bearer ${token}` }
                    })
                });
            });
    }
    
    $('#lessonType').change(function(){
        if($(this).val()==='PERSONAL'){
            $('tbody tr').eq(2).remove();
            $('tbody tr').eq(3).remove();
        }else{
            $('tbody tr').eq(1).after('<tr><td>모집인원</td><td><input type="text" placeholder="최대인원을 입력하세요." class="writeComponent" id="maxStudentCount" maxlength=20 style="width:100%"></td></tr>');
                    // 최대인원 수는 숫자로만 입력 가능
            $('tbody tr').eq(2).after('<tr><td>과외기간</td><td><input type=" text" class="writeComponent" id="startDate" placeholder="시작일자 yyyy-mm-dd"> ~<input type="text" class="writeComponent" id="endDate" placeholder="종료일자 yyyy-mm-dd"></td></tr>');
            $('#maxStudentCount').on('keypress', function(){
                if((event.keyCode<48)||(event.keyCode>57)){
                    event.returnValue=false;
                }
            });

            $.datepicker.setDefaults($.datepicker.regional['ko']);

            $( "#startDate" ).datepicker({ 
            dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
            monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
            monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
            dateFormat: "yy-mm-dd", minDate: 1, // 현재 이전 날짜는 선택 불가 
            onClose: function( selectedDate ) { //시작일(startDate) datepicker가 닫힐때 
                //종료일(endDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정 
                $("#endDate").datepicker( "option", "minDate", selectedDate ); 
                } 
            }); 
            $( "#endDate" ).datepicker({ 
            dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
            monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
            monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
            dateFormat: "yy-mm-dd", minDate: 1, // 오늘 이전 날짜 선택 불가 
            onClose: function( selectedDate ) { // 종료일(endDate) datepicker가 닫힐때 
                // 시작일(startDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 시작일로 지정 
                $("#startDate").datepicker( "option", "maxDate", selectedDate ); 
                } 
            });

        }
    });

    $("#btn-backToNoticeBoard").click(function(){

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
        event.preventDefault();


        // 입력안한 부분있는지 확인하는 과정 구현 && 날짜 형식 확인도
        var flag = true;
        var alertNotice = "";
        if($('#title').val() === "" || $('#content').val() === ""){
            flag = false;
            alertNotice = "작성하지 않은 부분이 있습니다.";
        }

        var dateCheck = RegExp(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);

        if(flag){
            if(postType === "editPost"){ // 에디터에서 
                var lessonType = $('#lessonType option:selected').val();
                var formData = new FormData();
            
                if(editorType === "newEditor"){  // 새 글작성 모드 
                    // 새 데이터 추가 코드 구현 
                    if(lessonType === "PERSONAL"){

                        formData.append('lessonType', lessonType)
                        formData.append('title', $('#title').val());
                        formData.append('content', $('#content').val());

                        fetch("http://219.255.114.140:8090/lesson",{
                            method: "POST",
                            headers :{
                                // 'Content-Type': 'multipart/form-data',
                                'Authorization' : `Bearer ${token}`,
                            },
                            body: formData,
                        })
                        // .then((response) => response.json())
                        //                             .then(
                        //                               (data) => (
                        //                                 console.log((token = data)),
                        //                                 localStorage.setItem("token", JSON.stringify(data.token))
                        //                               ));

                    }else{ // lessonType === "GROUP"
                        if($('#maxStudentCount').val() === ""){
                            alert("작성하지 않은 부분이 있습니다.");
                            if(!dateCheck.test($('#startDate').val()) || !dateCheck.test($('#endDate').val()) ){
                                flag = false;
                                alertNotice += "\n날짜 형식이 잘못되었습니다. (yyyy-mm-dd)";
                            }
                        }else{
                            formData.append('lessonType', lessonType)
                            formData.append('title', $('#title').val());
                            formData.append('maxStudentCount', $('#maxStudentCount').val());
                            formData.append('startPeriod', $('#startDate').val()); // 과외 시작일자
                            formData.append('endPeriod', $('#endDate').val()); // 과외 종료일자
                            formData.append('content', $('#content').val());

                            fetch("http://219.255.114.140:8090/lesson",{
                            method: "POST",
                            headers :{
                                // 'Content-Type': 'multipart/form-data',
                                'Authorization' : `Bearer ${token}`,
                            },
                            body: formData,
                        });
                        }
                    }
                }else{//editorType=="editEditor"
                    if(lessonType==="PERSONAL"){

                        var postIdx = types[2].split("=")[1];
                        formData.append('title', $('#title').val());
                        formData.append('content', $('#content').val());
                        formData.append('lessonType', lessonType);
                        
                        var url = "http://219.255.114.140:8090/lesson/" + postIdx;
                        fetch(url,{
                            method: "PUT",
                            headers :{
                                // 'Content-Type': 'multipart/form-data',
                                'Authorization' : `Bearer ${token}`,
                            },
                            body: formData,
                        })
                        // .then((response) => response.json())
                        // .then((data) => (console.log(data)));
                    }
                    else{
                        var postIdx = types[2].split("=")[1];   
                        console.log('잘들ㅇ어왔나?');
                        formData.append('title', $('#title').val());
                        formData.append('content', $('#content').val());
                        formData.append('lessonType', lessonType);
                        formData.append('maxStudentCount', $('#maxStudentCount').val());
                        formData.append('startPeriod', $('#startDate').val()); // 과외 시작일자
                        formData.append('endPeriod', $('#endDate').val()); // 과외 종료일자
                        
                        var url = "http://219.255.114.140:8090/lesson/" + postIdx;
                        fetch(url,{
                            method: "PUT",
                            headers :{
                                // 'Content-Type': 'multipart/form-data',
                                'Authorization' : `Bearer ${token}`,
                            },
                            body: formData,
                        })
                    }
                }
            }

            // $(location).attr('href', "home.html"); response 확인하고 보내야함

        }else{// 작성하지않은 부분이 있으면 :: if(!flag)
            alert(alertNotice);
        }
    });

    $('#btn-register').click(function(){
        // 이미 신청한 강의인지 확인하는 과정 필요
        $.ajax({
            type:"POST",
            url:"",
            data:{
                // 현재 로그인 중인 회원 정보
            }, dataType:"json",
            success:function(json){
                if(json){
                    alert("신청 성공");
                }else{
                    alert("오류");
                }
                return;
            }
        });
    });
});