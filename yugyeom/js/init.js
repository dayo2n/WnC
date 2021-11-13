//로그인상태인지 아닌지
const login_icon = document.querySelector("#login_icon");
const notification_icon = document.querySelector("#notification_icon");
const user_icon = document.querySelector("#user_icon");
const message_icon = document.querySelector("#message_icon");
if(JSON.parse(localStorage.getItem("isLogin")) === true){
  login_icon.classList.add("hidden");
  notification_icon.classList.remove("hidden");
  user_icon.classList.remove("hidden");
  message_icon.classList.remove("hidden");
}else{//로그인아닌상태
  notification_icon.classList.add("hidden");
  user_icon.classList.add("hidden");
  message_icon.classList.add("hidden");
}   



user_icon.addEventListener("click",e=> location.href="http://127.0.0.1:5500/yugyeom/user_profile.html");
login_icon.addEventListener("click", e =>location.href="http://127.0.0.1:5500/yugyeom/login.html");

const logo_img = document.querySelector("#logo");
const logo_text = document.querySelector("h1.logoBox");
logo_img.addEventListener("click", e => location.href="http://127.0.0.1:5500/yugyeom/home.html");
logo_text.addEventListener("click", e => location.href="http://127.0.0.1:5500/yugyeom/home.html");


$(document).ready(function(){
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