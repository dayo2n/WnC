const change_name_form = document.querySelector(".change_info_name");
const change_password_form = document.querySelector(".change_info_password");
const change_age_form = document.querySelector(".change_info_age");
const change_img = document.querySelector(".change_info_img");
const change_career_form = document.querySelector(".change_info_career");

function postChangeInfoName(event) {
  event.preventDefault();
  const selectBox = teacher_list_select.value;
  const searchValue = teacher_search.value;

  fetch(1, { //FormData로 보낼때 헤더설정 X
    method: "DELETE",
    headers: {},
    body: JSON.stringify({
      access_token: localStorage.getItem("token"),
    }),
  })
    .then((response) => response.json())
    .then((data) => console.log(data));
}
function postChangeInfoPassword(event) {}
function postChangeInfoAge(event) {}
function postChangeInfoImg(event) {}
function postChangeInfoCareer(event) {}

change_name_form.addEventListener("submit", postChangeInfoName);
change_password_form.addEventListener("submit", postChangeInfoPassword);
change_age_form.addEventListener("submit", postChangeInfoAge);
change_img.addEventListener("submit", postChangeInfoImg);
change_career_form.addEventListener("submit", postChangeInfoCareer);


const withdrawal = document.querySelector("#withdrawal");

function onWithdrawal(event){
  const password_form = document.createElement("form");
  const password_input = document.createElement("input");
  password_input.type="text";
  password_input.placeholder="비밀번호를 입력하시오"
  const password_submit = document.createElement("input");
  password_submit.type="submit";
  password_submit.value="확인";

  password_form.appendChild(password_input);
  password_form.appendChild(password_submit);
  withdrawal.parentElement.appendChild(password_form);
  password_form.classList.add("withdrawal_password_form");
  
  password_form.style.display="flex";
  withdrawal.style.display="none";

  const withdrawal_password_form = document.querySelector(".withdrawal_password_form");
  withdrawal_password_form.addEventListener("submit", postWithdrawal);
}

withdrawal.addEventListener("click", onWithdrawal);//카카오톡회원 탈퇴

function postWithdrawal(event){
  event.preventDefault();


    fetch(1, { //FormData로 보낼때 헤더설정 X
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          
          access_token: authObj.access_token,
        }),
      })
        .then((response) => response.json())
        .then((data) => console.log(data));

}