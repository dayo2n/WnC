
const change_name_form = document.querySelector(".change_info_name");
const change_password_form = document.querySelector(".change_info_password");
const change_age_form = document.querySelector(".change_info_age");
const change_img_form = document.querySelector(".change_info_img");
const change_career_form = document.querySelector(".change_info_career");

const change_name = document.querySelector("#name");
const current_password = document.querySelector("#current_password");
const new_password = document.querySelector("#new_password");
const check_new_password = document.querySelector("#check_new_password");
const change_age = document.querySelector("#age");
const change_img = document.querySelector("#info_img");
const change_career = document.querySelector("#career");


function postChangeInfoName(event) {
  event.preventDefault();

  const formData = new FormData();
  formData.append("name",change_name.value);



  if (JSON.parse(localStorage.getItem("memberType")) === "STUDENT") {
    fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members/student", {
      //선생일때
      //중복검사 후 보내기
      method: "PUT",
      headers: {
        Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
      },
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => console.log(data));
  } else {
    fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members/teacher", {
      //선생일때
      //중복검사 후 보내기
      method: "PUT",
      headers: {
        Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
      },
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => console.log(data));
  }
}

function postChangeInfoPassword(event) {
  event.preventDefault();

  const formData = new FormData();

  formData.append("password",check_new_password.value);
  formData.append("oldPassword",current_password.value);

  if(JSON.parse(localStorage.getItem("memberType")) === "STUDENT"){
    if (new_password.value === check_new_password.value) {

      fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members/student", {
        //선생일때
        //중복검사 후 보내기
        method: "PUT",
        headers: {
          Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
        },
        body: formData,
      })
        .then((response) => response.json())
        .then((data) => console.log(data));
    }
  }else{
    if (new_password.value === check_new_password.value) {

  
      fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members/teacher", {
        //선생일때
        //중복검사 후 보내기
        method: "PUT",
        headers: {
          Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
        },
        body: formData,
      })
        .then((response) => response.json())
        .then((data) => console.log(data));
    }
  }

  
}

function postChangeInfoAge(event) {
  event.preventDefault();
  const formData = new FormData();
  formData.append("age",change_age.value);
  if (JSON.parse(localStorage.getItem("memberType")) === "STUDENT") {
    fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members/student", {
      //학생일때
      method: "PUT",
      headers: {
        Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
      },
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => console.log(data));
  } else {
    fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members/teacher", {
      //선생일때
      method: "PUT",
      headers: {
        Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
      },
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => console.log(data));
  }
}
function postChangeInfoImg(event) {
  event.preventDefault();

  const formData = new FormData();
  formData.append("profileImg",change_img.files[0]);

  if (JSON.parse(localStorage.getItem("memberType")) === "STUDENT") {
    fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members/student", {
      //학생일때
      headers: {
        Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
      },
      method: "PUT",
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => console.log(data));
  } else {
    fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members/teacher", {
      headers: {
        Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
      },
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => console.log(data));
  }
}
function postChangeInfoCareer(event) {
  event.preventDefault();

  const formData = new FormData();
  formData.append("career",change_career.value);

  fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members/teacher", {
    //선생일때
    method: "PUT",
    headers: {
      Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
    },
    body: formData,
  })
    .then((response) => {response.json()
      if(response.status<300 && response.status>=200){
        alert("정보가 변경되었습니다.");
      }else{
        alert("에러");
      }})

    .then((data) => console.log(data));
}

change_name_form.addEventListener("submit", postChangeInfoName);
change_password_form.addEventListener("submit", postChangeInfoPassword);
change_age_form.addEventListener("submit", postChangeInfoAge);
change_img_form.addEventListener("change", postChangeInfoImg);
change_career_form.addEventListener("submit", postChangeInfoCareer);

const withdrawal = document.querySelector("#withdrawal");

function onWithdrawal(event) {
  const password_form = document.createElement("form");
  const password_input = document.createElement("input");
  password_input.type = "text";
  password_input.placeholder = "비밀번호를 입력하시오";
  const password_submit = document.createElement("input");
  password_submit.type = "submit";
  password_submit.value = "확인";

  password_form.appendChild(password_input);
  password_form.appendChild(password_submit);
  withdrawal.parentElement.appendChild(password_form);
  password_form.classList.add("withdrawal_password_form");

  password_form.style.display = "flex";
  withdrawal.style.display = "none";

  const withdrawal_password_form = document.querySelector(
    ".withdrawal_password_form"
  );
  withdrawal_password_form.addEventListener("submit", postWithdrawal);
}

withdrawal.addEventListener("click", onWithdrawal); //카카오톡회원 탈퇴

function postWithdrawal(event) {
  event.preventDefault();

  const formData = new FormData();
  formData.append("password",current_password.value);

  fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members", {
    //FormData로 보낼때 헤더설정 X
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
    },
    body: formData,
  })
    .then((response) => {
      if(response.status<300 && response.status>=200){
        alert("회원탈퇴되셨습니다");
        location.href = `${URL_ROUTE}home.html`
      }else{
        alert("비밀번호가 일치하지 않습니다.");
        return response.json();
      }
    })
    .then((data) => console.log(data));
}
