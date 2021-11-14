Kakao.init('9599c940fdbe3a4732ac990120dca478');
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
        location.href="http://127.0.0.1:5500/yugyeom/kakao_join.html";
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





const LOGIN_URL = "http://219.255.114.140:8090/admin/login";
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
    .then(response => {
      console.log(response);
      if(response.status !== 401){
        return response.json();
      }else{
        alert("관리자 권한이 없습니다.");
      }
    })
    .then(
      (data) => {
        localStorage.setItem("token", JSON.stringify(data.token));
        location.href="http://127.0.0.1:5500/yugyeom/admin.html";

      }
       
    )
}

login_form.addEventListener("submit", onLoginSubmit);