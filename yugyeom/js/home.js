const teacher_list_search_form = document.querySelector(
  ".teacher_list_search_form"
);
const teacher_list_select = document.querySelector("#teacher_list_select");
const teacher_search = document.querySelector("#teacher_search");

function getTeacherList(event) {
  event.preventDefault();
  const selectBox = teacher_list_select.value;
  const searchValue = teacher_search.value;
  
  fetch(LOGIN_URL, {//수정
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      selectBox:selectBox,
      searchValue:searchValue
    }),
  })
  .then((response) => response.json())
  .then((data) => (console.log(data))
  );
}

teacher_list_search_form.addEventListener("submit", getTeacherList2);


const teacher_list = document.querySelector(".teacher_list");
function getTeacherList2(event){ // getTeacherList에 넣을거
  //받아올 데이터: 사진, 선생님이름, 담당과목, 별점, 채팅하기??, 경력
  event.preventDefault();
  console.log(1);

  const card = document.createElement("li");
  card.classList.add("teacher_card");

  const img = document.createElement("img");
  img.classList.add("teacher_img");
  img.src="https://i.ytimg.com/vi/rLueTjLWVCc/maxresdefault.jpg";//database에서 가져오기

  const description = document.createElement("div");
  description.classList.add("teacher_description");
//
  const description_top = document.createElement("div");
  description_top.classList.add("teacher_description_top");

  const name = document.createElement("div");
  name.classList.add("teacher_name");
  const subject = document.createElement("subject");
  subject.classList.add("teacher_subject");
//
  const description_mid = document.createElement("div");
  description_mid.classList.add("teacher_description_mid");

  const rating = document.createElement("div");
  rating.classList.add("teacher_rating");
  const input_chatting_button = document.createElement("input");
  input_chatting_button.type = "button";
  input_chatting_button.value="채팅하기";
  input_chatting_button.classList.add("chatting_button");
//
  const description_bottom = document.createElement("div")
  description_bottom.classList.add("teacher_description_bottom");

  const career = document.createElement("div");
  career.classList.add("teacher_career");

  //
  description_top.appendChild(name);
  description_top.appendChild(subject);

  description_mid.appendChild(rating);
  description_mid.appendChild(input_chatting_button);

  description_bottom.appendChild(career);

  description.appendChild(description_top);
  description.appendChild(description_mid);
  description.appendChild(description_bottom);

  card.appendChild(img);
  card.appendChild(description);

  name.innerText = "DB";
  subject.innerText = "DB";
  rating.innerText = "DB"; //데이터베이스에서 별 개수 받은 후 별그림으로
  career.innerText ="DB";


  teacher_list.appendChild(card);

}
