let name_value;
let age_value;
let img_value;
let career_value;
let rating_value;
let isKakaoMember;
let count;
function init() {
  //강의 개수만큼 초기화
  //localStorage.getItem("isTeacher");
  fetch("http://219.255.114.140:8090/myInfo", {
    method: "GET",
    headers: {
        Authorization: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLtlZnsg50xIiwiYXV0aCI6IlJPTEVfQkFTSUMiLCJleHAiOjE2MzY4MTM2MDR9.Mrx1DJAL7Uou9jIMX0NsGL2A6U3CJ8O6mRHMR_0UpGYEHxG6_hFMBKZ5eaE9ZNWUAI23F01hp3m8O1FpP81BQQ`, //`Bearer ${JSON.parse(localStorage.getItem("token"))}`
    },
  })
    .then((response) => response.json())
    .then(
      (data) => (
        console.log(data),
        (name_value = data.name),
        (age_value = data.age),
        (img_value = data.profileImgPath),
        (isKakaoMember = data.isKaKaomember),
        (rating_value= data.starPoint),
        (career_value = data.career),
        getUserProfile(),
        count = 1,
        data.takingLessonList.forEach(element => {
          appendSubjectTable(element)//듣고있는 강의리스트
        }),
        (console.log(data.takingLessonList)),
        count = 1,
        data.takingLessonList.forEach(element =>{
          appendEvaluateTable(element)//평가테이블
        })
      )
    );
}
//init();

const info_name = document.querySelector("#info_name");
const info_age = document.querySelector("#info_age");
const info_career = document.querySelector("#info_career");
const info_img = document.querySelector("#info_img");
const info_rating = document.querySelector("#info_rating");

function getUserProfile() {
  info_name.innerHTML = name_value;
  info_age.innerHTML = age_value;
  info_career.innerHTML = career_value;
  info_rating.innerHTML = `별점: ${rating_value}`;
  info_img.src = img_value;
}

const applied_subject_tbody = document.querySelector(".applied_subject_tbody");

function appendSubjectTable(data){
    const tr = document.createElement("tr");
    const td1 = document.createElement("td")
    const td2 = document.createElement("td")
    const td3 = document.createElement("td")
    td2.setAttribute("id","teacher_name_cell");
    tr.setAttribute("id", data.teacherId)
    td1.innerText=count++//
    td2.innerText=data.teacherName//
    td3.innerText=data.title//DB
    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    applied_subject_tbody.appendChild(tr);
    tr.addEventListener("click",teacherIdSubmit);
}
function teacherIdSubmit(){
  getTeacherProfile(tr.id);
  location.href="http://127.0.0.1:5500/yugyeom/teacher_profile_view.html";
}

const evaluate_table_tbody = document.querySelector(".evaluate_table_tbody");

function appendEvaluateTable(data){
    const tr = document.createElement("tr");
    const td1 = document.createElement("td")
    const td2 = document.createElement("td")
    const td3 = document.createElement("td")
    const td4 = document.createElement("td");
    const td5 = document.createElement("td");
    const form = document.createElement("form");
    const td3_text = document.createElement("input");
    const td5_submit = document.createElement("input");
    td3_text.type = "text";
    td3_text.placeholder="평가내용"
    td3.appendChild(td3_text);
    td5_submit.type = "submit";
    td5.appendChild(td5_submit);
    form.classList.add("evaluate_form");
    td2.setAttribute("id","teacher_name_cell");
    form.setAttribute("id",data.teacherId);
    
    td1.innerText=`${count++}`//
    td2.innerText=`${data.teacherName}`//

    const select = document.createElement("select");
    td3.setAttribute("id","evaluate_content");
    select.setAttribute("id","evaluate_select");


    const option5 = document.createElement("option");
    const option4 = document.createElement("option");
    const option3 = document.createElement("option");
    const option2 = document.createElement("option");
    const option1 = document.createElement("option");
    const option0 = document.createElement("option");

    option5.value = "5";
    option4.value = "4";
    option3.value = "3";
    option2.value = "2";
    option1.value = "1";
    option0.value = "0";
    option5.innerText="5";
    option5.innerText="4";
    option5.innerText="3";
    option5.innerText="2";
    option5.innerText="1";
    option5.innerText="0";

    select.appendChild(option5);
    select.appendChild(option4);
    select.appendChild(option3);
    select.appendChild(option2);
    select.appendChild(option1);
    select.appendChild(option0);

    td4.appendChild(select)

    form.appendChild(td1);
    form.appendChild(td2);
    form.appendChild(td3);
    form.appendChild(td4);
    form.appendChild(td5);
    tr.appendChild(form);
    evaluate_table_tbody.appendChild(tr);
    console.log(tr);
    form.addEventListener("submit", postTeacherEvaluate);
}

function postTeacherEvaluate(event){
  event.preventDefault();
  const form= event.taget;
  const id = form.id;
  const input = document.querySelector(`#${id} #evaluate_content`);
  const select = docuemnt.querySelector(`#${id} #evaluate_select`);
  fetch(`http://219.255.114.140:8090/myInfo/evaluation/teachers/${id}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      content: input.value,
      starPoint: select.value,
      //access_token: authObj.access_token,
    }),
  })
    .then((response) => response.json())
    .then(
      (data) => (
        console.log(data))
    );
}
