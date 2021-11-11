const teacher_list_search_form = document.querySelector(
  ".teacher_list_search_form"
);
const teacher_list_select = document.querySelector("#teacher_list_select");
const teacher_search = document.querySelector("#teacher_search");

teacher_list_search_form.addEventListener("submit", getTeacherList);

function getTeacherList(event) {
  event.preventDefault();

  const username = signin_userNameInput.value;
  const password = signin_passwordInput.value;
  fetch(LOGIN_URL, {
    method: "GET",
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
