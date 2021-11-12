const join_username = document.querySelector("#join_username");
const join_password = document.querySelector("#join_password");
const join_name = document.querySelector("#join_name");
const join_age = document.querySelector("#join_age");
const join_img = document.querySelector("#join_img");
const join_career = document.querySelector("#join_career");
const join_form = document.querySelector(".join_form");

join_form.addEventListener("submit", postJoin);

function postJoin(event){
    event.preventDefault();

    const formdata = new FormData();
    formdata.append("username",join_username.value);
    formdata.append("password", join_password.value);
    formdata.append("name", join_name.value);
    formdata.append("age", join_age.value);
    formdata.append("career", join_career.value);
    console.log(join_img.files[0]);
    if(join_img.files[0] !== undefined){formdata.append("profileImg", null);}
    
    fetch("http://219.255.114.140:8090/join/teacher", { //FormData로 보낼때 헤더설정 X
        method: "POST",
        body: formdata
      })
        .then((response) => response.json())
        .then((data) => console.log(data));
}//에러메세지



function usernameOverlapCheck(){//아이디중복확인
    const username = join_username.value;
    fetch("http://219.255.114.140:8090/", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        username: username,
      }),
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



