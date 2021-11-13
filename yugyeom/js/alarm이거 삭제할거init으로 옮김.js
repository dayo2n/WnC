function onAlarmList() {
  fetch("http://219.255.114.140:8090/myInfo/myAlarms", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`, //`Bearer ${JSON.parse(localStorage.getItem("token"))}`
    },
  })
    .then((response) => response.json())
    .then(
      (data) => (
        console.log(data),
        data.simpleAlarmList.forEach((element) => {
          getAlarmList(element);
        })
      )
    );
}

const alarm_list_container = document.querySelector(".alarm_list_container");
function getAlarmList(data) {
  if (data.alarmType === "SEND_APPLY") {
    ///
    //선생님한테만 전달, ?학생이 ?과외에 가입신청 보냈습니다.
    //수락하시겠습니까? 예 버튼, 아니오버튼 안눌렀으면 읽지않은알림상태
    const li = document.createElement("li");
    const span = document.createElement("span");
    const input1 = document.createElement("input");
    const input2 = document.createElement("input");
    input1.value = "예";
    input2.value = "아니오";
    input1.type = "button";
    input2.type = "button";
    span.innerText = `${data.applicantStudentName}학생이 ${data.title}과외에 가입신청을 보냈습니다.`;

    li.appendChild(span);
    li.appendChild(input1);
    li.appendChild(input2);

    input1.id = data.id; //알람ID를 inputid에 저장
    input2.id = data.id;

    input1.addEventListener(
      "click",
      getApproveLesson,
      (e) => (data.isRead = true),
      input1.classList.add("hidden"),
      input2.classList.add("hidden")
    ); //예를 눌렀을때
    input2.addEventListener(
      "click",
      getRefuseLesson,
      (e) => (data.isRead = true),
      input1.classList.add("hidden"),
      input2.classList.add("hidden")
    ); //아니오를 눌렀을시

    alarm_list_container.appendChild(li);
  } else if (data.alarmType === "APPROVED") {
    //학생에게만 전달, ?님이 수업하시는 ?과외에 가입되셨습니다.
    const li = document.createElement("li");
    const span = document.createElement("span");
    const checkButton = document.createElement("span");
    checkButton.innerText = "✔";
    span.innerText = `${data.teacherName}님이 수업하시는 ${data.title}과외에 가입되셨습니다.`;

    li.appendChild(span);
    li.appendChild(checkButton);

    checkButton.addEventListener(
      "click",
      (e) => (data.isRead = true),
      checkButton.classList.add("hidden")
    ); //체크했을때 isRead(true)

    alarm_list_container.appendChild(li);
  } else if (data.alarmType === "REFUSED") {
    //학생에게만 전달, ??님이 수업하시는 ?과외에 가입이 거절되었습니다.
    const li = document.createElement("li");
    const span = document.createElement("span");
    const checkButton = document.createElement("span");

    checkButton.innerText = "✔";
    span.innerText = `${data.teacherName}님이 수업하시는 ${data.title}과외에 가입이 거절되었습니다.`;

    li.appendChild(span);
    li.appendChild(checkButton);

    checkButton.addEventListener(
      "click",
      (e) => (data.isRead = true),
      checkButton.classList.add("hidden")
    ); //체크했을때 isRead(true)

    alarm_list_container.appendChild(li);
  } else if (data.alarmType === "COMPLETION") {
    //교수님과 학생모두 전달 ?님이 수업하시는? 과외의 모집이 완료되었습니다.
    const li = document.createElement("li");
    const span = document.createElement("span");
    const checkButton = document.createElement("span");

    checkButton.innerText = "✔";
    span.innerText = `${data.teacherName}님이 수업하시는 ${data.title}과외의 모집이 완료되었습니다.`;

    li.appendChild(span);
    li.appendChild(checkButton);

    checkButton.addEventListener(
      "click",
      (e) => (data.isRead = true),
      checkButton.classList.add("hidden")
    ); //체크했을때 isRead(true)

    alarm_list_container.appendChild(li);
  }
}

function getApproveLesson(event) {
  //강의수락
  const alarm_id = event.target.id;

  fetch(`http://219.255.114.140:8090/myInfo/myAlarms/${alarm_id}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`, //`Bearer ${JSON.parse(localStorage.getItem("token"))}`
    },
  })
    .then((response) => response.json())
    .then((data) => (console.log(data), postApproveLesson(data)));
}

function postApproveLesson(data) {
  //강의수락요쳥보내기

  fetch(
    `http://219.255.114.140:8090/lesson/${data.lessonId}/accept/${data.id}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({}),
    }
  )
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
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
    .then((data) => (console.log(data), postRepuseLesson(data)));
}

function postRepuseLesson(data) {//강의거절 요청보내기
    fetch(
      `http://219.255.114.140:8090/lesson/${data.lessonId}/cancel/${data.id}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({}),
      }
    )
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
      });
  }
  