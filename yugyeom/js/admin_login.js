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