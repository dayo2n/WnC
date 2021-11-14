const view_name = document.querySelector("#view_name");
const view_age = document.querySelector("#view_age");
const view_career = document.querySelector("#view_career");
const view_rating = document.querySelector("#view_rating");
const view_img = document.querySelector("#view_img");

const IMG_URL = "https://wnc-bucket.s3.ap-northeast-2.amazonaws.com/";

let view_name_value;
let view_age_value;
let view_img_value;
let view_career_value;
let view_rating_value;
let count=0;
function init() {
  const teacherID = JSON.parse(localStorage.getItem("teacher_profile_view_id"));
  console.log(teacherID);
  fetch(`https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/members/teachers/${teacherID}`, {//teacherID는 어떻게?
      method: "GET",
      headers: {
          Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`, //`Bearer ${JSON.parse(localStorage.getItem("token"))}`
      },
    })
      .then((response) => response.json())
      .then(
        (data) => (
          console.log(data),
          (view_name_value = data.name),
          (view_age_value = data.age),
          (view_img_value = data.profileImgPath),
          (view_career_value = data.career),
          (view_rating_value= data.starPointAvg),
          getTeacherProfileView(),
          count = 1,
          data.evaluationDtos.forEach(element => {
              appendEvaluatedTable(element)
          })
        )
      );
  }
  init();



function getTeacherProfileView(){
    view_name.innerHTML = view_name_value;
    view_age.innerHTML = view_age_value;
    view_career.innerHTML =view_career_value;
    console.log(view_rating_value);
    //view_rating.innerHTML = view_rating_value;
    view_img.src = `${IMG_URL}${view_img_value}`;
}

const evaluation_list_tbody = document.querySelector(".evaluation_table_tbody");

function appendEvaluatedTable(data){
    const tr = document.createElement("tr");

    const td1 = document.createElement("td");
    const td2 = document.createElement("td");
    const td3 = document.createElement("td");
    const td4 = document.createElement("td");

    td1.innerHTML = count++;
    td2.innerHTML = data.content;

    let starNum = "";
    for(let i = 0; i < data.starPoint; i++){
      starNum += "⭐";
     }


    td3.innerHTML = starNum;
    td4.innerHTML = data.studentName;
    
    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);

    evaluation_list_tbody.appendChild(tr);
}
