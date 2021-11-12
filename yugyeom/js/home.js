var token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLshKDsg50xIiwiYXV0aCI6IlJPTEVfQkFTSUMiLCJleHAiOjE2MzY4MTYyMjd9.12vfH1Ciy09hF2cNgJJEyZnh_V-EoDtnVDWn3B5r8UBBsZtw0sGqUckD0uRR8CuupTTLzYTVgk4WGL6AWDgCsQ';
//날짜형식변환 yyyy-mm-dd

$(document).ready(function () {
//8080/lesson?teacherName=1

fetch("http://219.255.114.140:8090/lesson",{
        method: "GET",
        headers : {"Authorization" : `Bearer ${token}` }
        })
        .then(response => {
          return response.json();
        })
        .then(data => {
          console.log(data);
          console.log(data.simpleLectureDtoList.length);
          for(i=0;i < data.simpleLectureDtoList.length; i++){
            var singleData = data.simpleLectureDtoList[i];
          // completed: false
          // createdDate: "2021-11-12T20:38:59.1416"
          // endPeriod: null
          // id: 1
          // lessonType: "PERSONAL"
          // maxStudentCount: 1
          // nowStudentCount: 0
          // startPeriod: null
          // teacherName: "선생2"
          // title: "개인"
          // views: 0

          var completed = singleData.completed;
          var createdDate = singleData.createdDate;
          var endPeriod = singleData.endPeriod;
          var index = singleData.id;
          var lessonType = singleData.lessonType;
          var maxStudentCount = singleData.maxStudentCount;
          var nowStudentCount = singleData.nowStudentCount;
          var startPeriod = singleData.startPeriod;
          var teacherName = singleData.teacherName;
          var title = singleData.title;
          var views = singleData.views;
          $('#table>tbody').prepend('<tr><td>'+ index +'</td><td id="titleCell">' + title + '</td><td>'+'여기 수정'+'</td><td>'+teacherName+'</td><td>'+createdDate.slice(0,9)+'</td><td>'+views+'</td></tr>');
          }

          console.log("length : " + $('#table >tbody tr').length);
          // 테이블 셀 클릭시 해당 게시글을 조회하는 뷰로 이동하는 부분
          $("#table tr").click(function (e) {
            var editorType = "none";
            var postType = "viewPost";
            var rowIdx = e.target.closest("tr").rowIndex;
            var idx =  $(this).children().eq(0).text(); // 게시글의 id

            var url ="http://219.255.114.140:8090/lesson/"+idx;
            if(rowIdx !== 0){
                  $(location).attr(
                    "href",
                    "viewPost.html?editorType=" + editorType + "&postType=" + postType + "&postID=" + idx
                  ); // 경로 바꿔야함
            }
        });
    });

    $("#btn-createNewPost").click(function (e) {
      var editorType = "newEditor";
      var postType = "editPost";
      $(location).attr(
        "href",
        "postEditor.html?editorType=" + editorType + "&postType=" + postType
      );
    });

    $('.chatting_button').click(function(e){
      $(".message_modal").css("display", "none");
      $(".notification_modal").css("display", "none");
      $(".user_modal").css("display", "none");
      $(".chatting_modal").css("display", "block");


      $('#chattingTable>tbody').prepend('<tr><td></td><td>안녕하세요</td></tr>');
      $('#chattingTable>tbody').prepend('<tr><td>ㅎㅇㅎㅇ</td><td></td></tr>');
      $('#chattingTable>tbody').prepend('<tr><td></td><td>어쩌구저쩌구</td></tr>');

      $("#messageTable tr").click(function (e) {
        var rowIdx = e.target.closest("tr").rowIndex;

        $('.chatting_modal').css('display', 'block');
        $('.message_modal').css('display', 'none');
    });
  })

  /// 여기부터는 선생님 정보 조회
  fetch("http://219.255.114.140:8090/members/teachers",{
    method: "GET",
    headers : {"Authorization" : `Bearer ${token}` }
    })
    .then(response => {
      return response.json();
    })
    .then(data => {
      console.log(data);
      console.log(data.totalElementCount);
      // for(i=0;i < data.totalElementCount; i++){
      //   var singleData = data.simpleLectureDtoList[i];

      //   var completed = singleData.completed;
      //   var createdDate = singleData.createdDate;
      //   var endPeriod = singleData.endPeriod;
      //   var index = singleData.id;
      //   var lessonType = singleData.lessonType;
      //   var maxStudentCount = singleData.maxStudentCount;
      //   var nowStudentCount = singleData.nowStudentCount;
      //   var startPeriod = singleData.startPeriod;
      //   var teacherName = singleData.teacherName;
      //   var title = singleData.title;
      //   var views = singleData.views;
      //   $('#table>tbody').prepend('<li class="teacher_card"><img class="teacher_img" src="https://i.ytimg.com/vi/rLueTjLWVCc/maxresdefault.jpg"></img><div class="teacher_description"><div class="teacher_description_top"><div class="teacher_name">한석원선생님</div><div class="teacher_subject">수학</div></div><div class="teacher_description_mid"><div class="teacher_rating">⭐⭐⭐⭐⭐</div><input class="chatting_button" type="button" value="채팅하기"></div><div class="teacher_description_bottom"><div class="teacher_career">경력:</div> </div></div></li>');
      //   }

      //   console.log("length : " + $('#table >tbody tr').length);
      //   // 테이블 셀 클릭시 해당 게시글을 조회하는 뷰로 이동하는 부분
      //   $("#table tr").click(function (e) {
      //     var editorType = "none";
      //     var postType = "viewPost";
      //     var rowIdx = e.target.closest("tr").rowIndex;
      //     var idx =  $(this).children().eq(0).text(); // 게시글의 id

      //     var url ="http://219.255.114.140:8090/lesson/"+idx;
      //     if(rowIdx !== 0){
      //           $(location).attr(
      //             "href",
      //             "viewPost.html?editorType=" + editorType + "&postType=" + postType + "&postID=" + idx
      //           ); // 경로 바꿔야함
      //     }
      // });
  });

});


//로그인버튼
const login_button = document.querySelector("#login_icon");
// login_button.addEventListener("click", e => console.log(1),window.location.href="http://127.0.0.1:5500/yugyeom/login.html");


const teacher_list_select = document.querySelector("#teacher_list_select");
const teacher_search = document.querySelector("#teacher_search");

function getTeacherList(event) {
  event.preventDefault();
  const selectBox = teacher_list_select.value;
  const searchValue = teacher_search.value;

  fetch(LOGIN_URL, {
    //수정
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      selectBox: selectBox,
      searchValue: searchValue,
    }),
  })
    .then((response) => response.json())
    .then((data) => console.log(data));
}

// teacher_list_search_form.addEventListener("submit", getTeacherList2);

const teacher_list = document.querySelector(".teacher_list");
function getTeacherList2(event) {
  // getTeacherList에 넣을거
  //받아올 데이터: 사진, 선생님이름, 담당과목, 별점, 채팅하기??, 경력
  event.preventDefault();
  console.log(1);

  const card = document.createElement("li");
  card.classList.add("teacher_card");

  const img = document.createElement("img");
  img.classList.add("teacher_img");
  img.src = "https://i.ytimg.com/vi/rLueTjLWVCc/maxresdefault.jpg"; //database에서 가져오기

  const description = document.createElement("div");
  description.classList.add("teacher_description");
  //
  const description_top = document.createElement("div");
  description_top.classList.add("teacher_description_top");

  const name = document.createElement("div");
  name.classList.add("teacher_name");
  const subject = document.createElement("subject");
  subject.classList.add("teacher_subject");
  //
  const description_mid = document.createElement("div");
  description_mid.classList.add("teacher_description_mid");

  const rating = document.createElement("div");
  rating.classList.add("teacher_rating");
  const input_chatting_button = document.createElement("input");
  input_chatting_button.type = "button";
  input_chatting_button.value = "채팅하기";
  input_chatting_button.classList.add("chatting_button");
  //
  const description_bottom = document.createElement("div");
  description_bottom.classList.add("teacher_description_bottom");

  const career = document.createElement("div");
  career.classList.add("teacher_career");

  //
  description_top.appendChild(name);
  description_top.appendChild(subject);

  description_mid.appendChild(rating);
  description_mid.appendChild(input_chatting_button);

  description_bottom.appendChild(career);

  description.appendChild(description_top);
  description.appendChild(description_mid);
  description.appendChild(description_bottom);

  card.appendChild(img);
  card.appendChild(description);

  name.innerText = "DB";
  subject.innerText = "DB";
  rating.innerText = "DB"; //데이터베이스에서 별 개수 받은 후 별그림으로
  career.innerText = "DB";

  teacher_list.appendChild(card);
}

function transformPrev(event) {
  const slidePrev = event.target;
  const slideNext = slidePrev.nextElementSibling;

  const classList = slidePrev.parentElement.parentElement.nextElementSibling;
  let activeLi = classList.getAttribute("data-position");
  const liList = classList.getElementsByTagName("li");

  // 하나의 카드라도 오른쪽으로 이동했다면, 왼쪽으로 갈 수 있음
  if (Number(activeLi) < 0) {
    activeLi = Number(activeLi) + 270;

    // 오른쪽에 있던 카드가 왼쪽  갔다면, 다시 왼쪽으로 갈 수 있으므로 next 버튼 활성화
    slideNext.style.color = "#2f3059";
    slideNext.classList.add("slide_next_hover");
    slideNext.addEventListener("click", transformNext);

    // 맨 왼쪽에 현재 보이는 카드가 맨 첫번째 카드면 왼쪽으로 갈 수 없으므로 prev 비활성화
    if (Number(activeLi) === 0) {
      slidePrev.style.color = "#cfd8dc";
      slidePrev.classList.remove("slide_prev_hover");
      slidePrev.removeEventListener("click", transformPrev);
    }
  }

  classList.style.transition = "transform 1s";
  classList.style.transform = "translateX(" + String(activeLi) + "px)";
  classList.setAttribute("data-position", activeLi);
}

function transformNext(event) {
  const slideNext = event.target;
  const slidePrev = slideNext.previousElementSibling;

  // ul 태그 선택
  const classList = slideNext.parentElement.parentElement.nextElementSibling;
  let activeLi = classList.getAttribute("data-position");
  const liList = classList.getElementsByTagName("li");

  /* classList.clientWidth 는 ul 태그의 실질적인 너비
   * liList.length * 270 에서 270 은 각 li 요소의 실질 너비(margin 포함)
   * activeLi 는 data-position 에 있는 현재 위치
   * 즉, liList.length * 270 + Number(activeLi) 는 현재 위치부터 오른쪽으로 나열되야 하는 나머지 카드들의 너비
   */

  /* classList.clientWidth < (liList.length * 270 + Number(activeLi)) 의미는
   * 오른쪽으로 나열될 카드들이 넘친 상태이므로, 왼쪽으로 이동이 가능함
   */

  if (classList.clientWidth < liList.length * 270 + Number(activeLi)) {
    // 위치를 오른쪽으로 270 이동 (-260px)
    activeLi = Number(activeLi) - 270;

    /* 위치를 오른쪽으로 270 이동 (-260px)
     * 해당 위치는 변경된 activeLi 값이 적용된 liList.length * 270 + Number(activeLi) 값임
     * 이 값보다, classList.clientWidth (ul 태그의 너비)가 크다는 것은
     * 넘치는 li 가 없다는 뜻으로, prev 버튼은 활성화 X
     */
    if (classList.clientWidth > liList.length * 270 + Number(activeLi)) {
      slideNext.style.color = "#cfd8dc";
      slideNext.classList.remove("slide_next_hover");
      slideNext.removeEventListener("click", transformNext);
    }

    slidePrev.style.color = "#2f3059";
    slidePrev.classList.add("slide_prev_hover");
    slidePrev.addEventListener("click", transformPrev);
  }

  classList.style.transition = "transform 1s";
  classList.style.transform = "translateX(" + String(activeLi) + "px)";
  classList.setAttribute("data-position", activeLi);
}

// ul 태그 선택
const slideNext_btn = document.querySelector(".slide_next");
const slidePrev_btn = slideNext_btn.previousElementSibling;
let classList = slideNext_btn.parentElement.parentElement.nextElementSibling;
let liList = classList.getElementsByTagName("li");

// 카드가 ul 태그 너비보다 넘치면, 왼쪽(prev) 버튼은 활성화하고, 오른쪽(next)는 현재 맨 첫카드 위치이므로 비활성화
if (classList.clientWidth < liList.length * 270) {
  slideNext_btn.classList.add("slide_next_hover");
  slideNext_btn.classList.remove("slide_next");
  slidePrev_btn.classList.add("slide_next");
  slideNext_btn.classList.add("slide_prev"); // 스타일 변경 안돼서 클래스로 바꿈
  slideNext_btn.addEventListener("click", transformNext);
} else {
  /* 카드가 ul 태그 너비보다 넘치지 않으면, prev, next 버튼 불필요하므로, 아예 삭제함
      태그 삭제시, 부모 요소에서 removeChild 를 통해 삭제
        1. 먼저 부모 요소를 찾아서
        2. 부모 요소의 자식 요소로 있는 pev 와 next 요소를 삭제함
      */
  const arrowContainer = slideNext_btn.parentElement;
  arrowContainer.removeChild(slidePrev_btn.nextElementSibling);
  arrowContainer.removeChild(slidePrev_btn);
}
