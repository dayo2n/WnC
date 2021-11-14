
const change_name_form = document.querySelector(".change_info_name");
const change_password_form = document.querySelector(".change_info_password");
const change_age_form = document.querySelector(".change_info_age");
const change_img = document.querySelector(".change_info_img");
const change_career_form = document.querySelector(".change_info_career");

function postChangeInfoName(event) {
  event.preventDefault();
  const selectBox = teacher_list_select.value;
  const searchValue = teacher_search.value;

  fetch(LOGIN_URL, {
    //수정
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      selectBox: selectBox,
      searchValue: searchValue,
    }),
  })
    .then((response) => response.json())
    .then((data) => console.log(data));
}

function postChangeInfoAge(event) {}
function postChangeInfoImg(event) {}
function postChangeInfoCareer(event) {}

change_name_form.addEventListener("submit", postChangeInfoName);
change_age_form.addEventListener("submit", postChangeInfoAge);
change_img.addEventListener("submit", postChangeInfoImg);
change_career_form.addEventListener("submit", postChangeInfoCareer);

const withdrawal = document.querySelector("#withdrawal");

function onWithdrawal(event){
  const password_form = document.createElement("form");
  const password_submit = document.createElement("input");
  password_submit.type="submit";
  password_submit.value="확인";

  password_form.appendChild(password_submit);
  withdrawal.parentElement.appendChild(password_form);
  password_form.classList.add("withdrawal_password_form");
  
  password_form.style.display="flex";
  withdrawal.style.display="none";

  const withdrawal_password_form = document.querySelector(".withdrawal_password_form");
  withdrawal_password_form.addEventListener("submit", postWithdrawal);
}

withdrawal.addEventListener("click", onWithdrawal);

function postWithdrawal(event){//회원탈퇴 구현 카카오톡 회원이면 
  event.preventDefault();

}