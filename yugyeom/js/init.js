const login_icon = document.querySelector("#login_icon");
const notification_icon = document.querySelector("#notification_icon");
const user_icon = document.querySelector("#user_icon");
if(JSON.parse(localStorage.getItem("isLogin")) === true){
  login_icon.classList.add("hidden");
  notification_icon.classList.remove("hidden");
    user_icon.classList.remove("hidden");
}else{//로그인아닌상태
    notification_icon.classList.add("hidden");
    user_icon.classList.add("hidden");
}   