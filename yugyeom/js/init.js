//로그인상태인지 아닌지
const login_icon = document.querySelector("#login_icon");
const notification_icon = document.querySelector("#notification_icon");
const user_icon = document.querySelector("#user_icon");
const message_icon = document.querySelector("#message_icon");
if (JSON.parse(localStorage.getItem("isLogin")) === true) {
  login_icon.classList.add("hidden");
  notification_icon.classList.remove("hidden");
  user_icon.classList.remove("hidden");
  message_icon.classList.remove("hidden");
} else {
  //로그인아닌상태
  logout_icon.classList.add("hidden");
  notification_icon.classList.add("hidden");
  user_icon.classList.add("hidden");
  message_icon.classList.add("hidden");
}



user_icon.addEventListener(
  "click",
  (e) => (location.href = "http://127.0.0.1:5500/yugyeom/user_profile.html")
);
login_icon.addEventListener(
  "click",
  (e) => (location.href = "http://127.0.0.1:5500/yugyeom/login.html")
);
logout_icon.addEventListener(
  "click",
  (e) => (location.href = "http://127.0.0.1:5500/yugyeom/login.html",
  localStorage.clear())
);

const logo_img = document.querySelector("#logo");
const logo_text = document.querySelector("h1.logoBox");
logo_img.addEventListener(
  "click",
  (e) => (location.href = "http://127.0.0.1:5500/yugyeom/home.html")
);
logo_text.addEventListener(
  "click",
  (e) => (location.href = "http://127.0.0.1:5500/yugyeom/home.html")
);

$(document).ready(function () {
  // for notification modal
  $("#notification_icon").click(function () {
    $(".notification_modal").css("display", "block");
    $(".user_modal").css("display", "none");
    $(".message_modal").css("display", "none");
  });
  $(".notification_modal_close_area").click(function () {
    $(".notification_modal").css("display", "none");
  });

  // for user modal
  $("#user_icon").click(function (e) {
    $(".user_modal").css("display", "block");
    $(".notification_modal").css("display", "none");
    $(".message_modal").css("display", "none");
  });

  $(".user_modal_close_area").click(function () {
    $(".user_modal").css("display", "none");
  });

  // for user modal
  $("#message_icon").click(function (e) {
    $(".message_modal").css("display", "block");
    $(".notification_modal").css("display", "none");
    $(".user_modal").css("display", "none");
  });

  $(".message_modal_close_area").click(function () {
    $(".message_modal").css("display", "none");
  });

  $(".chatting_modal_close_area").click(function () {
    $(".chatting_modal").css("display", "none");
  });
});

const alarm_select = document.querySelector("#alarm_select");

const alarm_form = document.querySelector(".alarm_form");
alarm_form.addEventListener("submit", onAlarmList);

notification_icon.addEventListener("click", onAlarmList);

function onAlarmList(event) {
  event.preventDefault();
  while (alarm_list_container.hasChildNodes()) {
    alarm_list_container.removeChild(alarm_list_container.firstChild);
  }
  console.log("셀렉트 벨류", alarm_select.value);
  fetch("http://219.255.114.140:8090/myInfo/myAlarms", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`, //`Bearer ${JSON.parse(localStorage.getItem("token"))}`
    },
  })
    .then((response) => response.json())
    .then((data) =>
      data.simpleAlarmList.forEach((element) => {
        getAlarmList(element);
      })
    );
}

const alarm_list_container = document.querySelector(".alarm_list_container");
function getAlarmList(data) {
  if (alarm_select.value === "unread" && data.read === false) {
    getAlarmIsreadList(data);
  } else if (alarm_select.value === "read" && data.read === true) {
    getAlarmIsreadList(data);
  } else if (alarm_select.value === "all") {
    getAlarmIsreadList(data);
  }
  function getAlarmIsreadList(data) {
    if (data.alarmType === "SEND_APPLY") {
      ///
      //선생님한테만 전달, ?학생이 ?과외에 가입신청 보냈습니다.
      //수락하시겠습니까? 예 버튼, 아니오버튼 안눌렀으면 읽지않은알림상태
      const li = document.createElement("li");
      const span = document.createElement("span");
      const input1 = document.createElement("input");
      const input2 = document.createElement("input");
      input1.value = "수락";
      input2.value = "거절";
      input1.type = "button";
      input2.type = "button";
      span.innerText = `${data.applicantStudentName}학생이 ${data.title}과외에 가입신청을 보냈습니다.`;

      li.appendChild(span);
      li.appendChild(input1);
      li.appendChild(input2);

      input1.id = data.id; //알람ID를 inputid에 저장
      input2.id = data.id;

      if (data.read === true) {
        input1.classList.add("hidden");
        input2.classList.add("hidden");
      }

      input1.addEventListener(
        "click",
        (e) => (
          getApproveLesson(e),
          input1.classList.add("hidden"),
          input2.classList.add("hidden")
        )
      ); //예를 눌렀을때
      input2.addEventListener(
        "click",
        (e) => (
          getRefuseLesson(e),
          input1.classList.add("hidden"),
          input2.classList.add("hidden")
        )
      ); //아니오를 눌렀을시

      alarm_list_container.appendChild(li);
    } else if (data.alarmType === "APPROVED") {
      //학생에게만 전달, ?님이 수업하시는 ?과외에 가입되셨습니다.
      const li = document.createElement("li");
      const span = document.createElement("span");
      const checkButton = document.createElement("span");
      checkButton.innerText = "✔";
      checkButton.classList.add("check_button");
      span.innerText = `${data.teacherName}님이 수업하시는 ${data.title}과외에 가입되셨습니다.`;
      li.appendChild(span);
      li.appendChild(checkButton);
      if (data.read === true) {
        checkButton.classList.add("hidden");
      }

      checkButton.id = data.id;
      checkButton.addEventListener(
        "click",
        (e) => (
          getApproveLesson(e),
          console.log(data.read),
          checkButton.classList.add("hidden")
        )
      ); //체크했을때 read(true)

      alarm_list_container.appendChild(li);
    } else if (data.alarmType === "REFUSED") {
      //학생에게만 전달, ??님이 수업하시는 ?과외에 가입이 거절되었습니다.
      const li = document.createElement("li");
      const span = document.createElement("span");
      const checkButton = document.createElement("span");

      checkButton.innerText = "✔";
      checkButton.classList.add("check_button");
      checkButton.id = data.id;

      span.innerText = `${data.teacherName}님이 수업하시는 ${data.title}과외에 가입이 거절되었습니다.`;

      li.appendChild(span);
      li.appendChild(checkButton);
      if (data.read === true) {
        checkButton.classList.add("hidden");
      }

      checkButton.addEventListener(
        "click",
        (e) => (getApproveLesson(e), checkButton.classList.add("hidden"))
      ); //체크했을때 read(true)

      alarm_list_container.appendChild(li);
    } else if (data.alarmType === "COMPLETION") {
      //교수님과 학생모두 전달 ?님이 수업하시는? 과외의 모집이 완료되었습니다.
      const li = document.createElement("li");
      const span = document.createElement("span");
      const checkButton = document.createElement("span");

      checkButton.innerText = "✔";
      checkButton.classList.add("check_button");
      checkButton.id = data.id;
      span.innerText = `${data.teacherName}님이 수업하시는 ${data.title}과외의 모집이 완료되었습니다.`;

      li.appendChild(span);
      li.appendChild(checkButton);
      if (data.read === true) {
        checkButton.classList.add("hidden");
      }
      checkButton.addEventListener(
        "click",
        (e) => (
          getApproveLesson(e),
          console.log(data.read),
          checkButton.classList.add("hidden")
        )
      ); //체크했을때 read(true)

      alarm_list_container.appendChild(li);
    }
  }
}

function getApproveLesson(event) {//강의열람
  //강의수락
  const alarm_id = event.target.id;
  fetch(`http://219.255.114.140:8090/myInfo/myAlarms/${alarm_id}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`, //`Bearer ${JSON.parse(localStorage.getItem("token"))}`
    },
  })
    .then((response) =>  response.json())
    .then((data) => {
      if(event.target.innerText ==="✔"){
          console.log(event.target.innerText);
      }else{
      (postApproveLesson(data)),console.log(event.target.innerText);}
    });
}

function postApproveLesson(data) {
  //강의수락요쳥보내기

  fetch(
    `http://219.255.114.140:8090/lesson/${data.lessonId}/accept/${data.id}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
      },
      body: JSON.stringify({}),
    }
  )
    .then((response) => response.json())
    .then((data) => {
      //console.log(data);
    });
}

function getRefuseLesson(event) {
  const alarm_id = event.target.id;
  fetch(`http://219.255.114.140:8090/myInfo/myAlarms/${alarm_id}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`, //`Bearer ${JSON.parse(localStorage.getItem("token"))}`
    },
  })
    .then((response) => response.json())
    .then((data) => postRepuseLesson(data));
}

function postRepuseLesson(data) {
  //강의거절 요청보내기
  fetch(
    `http://219.255.114.140:8090/lesson/${data.lessonId}/cancel/${data.id}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
      },
      body: JSON.stringify({}),
    }
  )
    .then((response) => response.json())
    .then((data) => {
      console.log("강의거절요청", data);
    });
}
