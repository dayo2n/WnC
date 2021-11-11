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