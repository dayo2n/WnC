Kakao.init('9599c940fdbe3a4732ac990120dca478');
console.log(Kakao.isInitialized());

function loginWithKakao() {//카카오톡으로 로그인
  Kakao.Auth.login({
    success: function(authObj) {
     
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
        // window.location.href="http://127.0.0.1:5500/index.html";
        return response.json().then(err =>console.log(err.errorMessage));
      }
      // window.location.href="http://127.0.0.1:5500/index.html";
      return response.json();
    })
    .then((data) => {console.log(data);});
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
  const password = login_password.value;//login_username.name 테스트

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
      (data) => (
        console.log((token = data)),
        localStorage.setItem("token", JSON.stringify(data.token))
      )
    );
}

login_form.addEventListener("submit", onLoginSubmit);