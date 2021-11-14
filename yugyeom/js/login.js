$(document).ready(function(){
  $('#admin_icon').click(function(){
    $(location).attr('href', "login_admin.html");
  })
})

Kakao.init('');
console.log(Kakao.isInitialized());

function loginWithKakao() {//카카오톡으로 로그인
  Kakao.Auth.login({
    success: function(authObj) {
     localStorage.setItem("access_token",JSON.stringify(authObj.access_token));
      fetch("http://219.255.114.140:8090/login/kakao", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", //token.token
    },
    body: JSON.stringify({
      accessToken : `${authObj.access_token}`
      //access_token: authObj.access_token,
    }),
  }).then((response) => {
      console.log(`response.status ${response.status}`);
      if (response.status >= 300 || response.status < 200) {
        //location.href="http://127.0.0.1:5500/yugyeom/kakao_join.html";
        return response.json().then(err =>console.log(err.errorMessage));
      }
      // location.href="http://127.0.0.1:5500/index.html";
      return response.json();
    })
    .then((data) => {console.log(data)},
    localStorage.setItem("isKakao","true"));

    },
    fail: function(err) {
      console.log(err);
    },
  })
}





const LOGIN_URL = "http://219.255.114.140:8090/login";
const login_username = document.querySelector("#login_username");
const login_password = document.querySelector("#login_password");
const login_form = document.querySelector(".login_form");

function onLoginSubmit(event) {
  event.preventDefault();
  const username = login_username.value;
  const password = login_password.value;

  fetch(LOGIN_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      username: username,
      password: password,
      //access_token: authObj.access_token,
    }),
  })
    .then((response) => response.json())
    .then(
      (data) => {
        console.log((data));
        localStorage.setItem("token", JSON.stringify(data.token));
        localStorage.setItem("isKakao",data.Kakao);
        localStorage.setItem("memberType", JSON.stringify(data.memberType));
        localStorage.setItem("isLogin",JSON.stringify(data.login));
        localStorage.setItem("myNoReadChatCount", JSON.stringify(data.myNoReadChatCount));
        localStorage.setItem("myNoReadAlarm", JSON.stringify(data.myNoReadAlarm));
        localStorage.setItem("id", JSON.stringify(data.id));
        localStorage.setItem("isBlack",JSON.stringify(data.black));
        console.log(localStorage.getItem("isLogin"));
        if (JSON.parse(localStorage.getItem("isLogin") === "true")) {
          location.href="http://127.0.0.1:5500/yugyeom/home.html";
        }else{
          alert(data.message);
          localStorage.clear();
        }
      } 
    );
}

login_form.addEventListener("submit", onLoginSubmit);