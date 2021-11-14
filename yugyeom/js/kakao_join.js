const kakao_join_form = document.querySelector(".kakao_join_form");
kakao_join_form.addEventListener("submit", postKakaoJoin);
const kakao_join_student = document.querySelector("#kakao_join_student");
const kakao_join_teacher = document.querySelector("#kakao_join_teacher");
const kakao_join_username = document.querySelector("#kakao_join_username");
const kakao_join_name = document.querySelector("#kakao_join_name");
const kakao_join_age = document.querySelector("#kakao_join_age");
const kakao_join_img = document.querySelector("#kakao_join_img");
const kakao_join_career = document.querySelector("#kakao_join_career");
const kakao_career_box =document.querySelector(".kakao_career_box");

kakao_join_student.addEventListener("click", e => kakao_career_box.classList.add("hidden"));
kakao_join_teacher.addEventListener("click", e=> kakao_career_box.classList.remove("hidden"));

function postKakaoJoin(event) {
  event.preventDefault();
  const formData = new FormData();
  formData.append("username", kakao_join_username.value);
  formData.append("name", kakao_join_name.value);
  formData.append("age", kakao_join_age.value);
  formData.append("career",kakao_join_career.value);
  formData.append("profileImg",kakao_join_img.files[0]);
  formData.append("accessToken",JSON.parse(localStorage.getItem("access_token")));

  console.dir(kakao_join_student.checked);
  if(kakao_join_student.checked){
    fetch("http://219.255.114.140:8090/join/student/kakao", {
        //선생님 카카오
    
        method: "POST",
        headers: {
          Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
        },
        body: formData,
      })
        .then((response) => response.json())
        .then((data) => console.log(data));
  }else{
    fetch("http://219.255.114.140:8090/join/teacher/kakao", {
        //선생님 카카오
    
        method: "POST",
        headers: {
          Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
        },
        body: formData,
      })
        .then((response) => response.json())
        .then((data) => console.log(data));
  }
  

  
  
}
