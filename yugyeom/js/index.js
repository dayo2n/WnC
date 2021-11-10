const nameInput = document.querySelector(".signup_form #name");
const signup_userNameInput = document.querySelector(".signup_form #username");
const signup_passwordInput = document.querySelector(".signup_form #password");
const ageInput = document.querySelector("#age");
const signin_userNameInput = document.querySelector(".signin_form #username");
const signin_passwordInput = document.querySelector(".signin_form #password");
const signup_form = document.querySelector(".signup_form");
const signin_form = document.querySelector(".signin_form");
const LOGIN_URL = "http://219.255.114.140:8090//authenticate";
const SIGNUP_URL = "http://219.255.114.140:8090/join/teacher";
const token_button = document.querySelector("#token");
let token;
//Authorization: `Bearer ${token}`,

function onSignInSubmit(event) {
  event.preventDefault();
  const username = signin_userNameInput.value;
  const password = signin_passwordInput.value;
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

function onSignUpSubmit(event) {
  event.preventDefault();
  const name = nameInput.value;
  const username = signup_userNameInput.value;
  const password = signup_passwordInput.value;
  const age = ageInput.value;

  fetch(SIGNUP_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      name: name,
      username: username,
      password: password,
      age: age,
      role: "BASIC",
      //access_token: authObj.access_token,
    }),
  })
    .then((response) => response.json())
    .then((data) => console.log(data));
}
signin_form.addEventListener("submit", onSignInSubmit);
signup_form.addEventListener("submit", onSignUpSubmit);

token_button.addEventListener("click", onTokenSubmit);

function onTokenSubmit(event) {
  event.preventDefault();
  fetch("http://219.255.114.140:8090/api/user", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`, //token.token
    },
  })
    .then((response) => {
      console.log(`response.status ${response.status}`);
      if (response.status >= 300 || response.status < 200) {
        return response.json().then(err =>console.log(err.message));
      }
      return response.json();
    })
    .then((data) => {
      console.log(data);
    }); //console.log(data.status)
}

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




// //SDK를 초기화 합니다. 사용할 앱의 JavaScript 키를 설정해 주세요.
// Kakao.init("9599c940fdbe3a4732ac990120dca478");
// const cName = "JSESSIONID";

// // SDK 초기화 여부를 판단합니다.
// console.log(Kakao.isInitialized());
// Kakao.Auth.createLoginButton({
//   container: "#kakao-login-btn",
//   success: function (authObj) {
//     console.log(authObj.access_token);

//     Kakao.API.request({
//       url: "/v2/user/me",
//       success: function (res) {
//         console.log((JSON.stringify(res)));
//       },
//       fail: function (error) {
//         alert(
//           "login success, but failed to request user information: " +
//             JSON.stringify(error)
//         );
//       },
//     });
//   },
//   fail: function (err) {
//     alert("failed to login: " + JSON.stringify(err));
//   },
// });
