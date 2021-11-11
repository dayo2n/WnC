const teacher_list_search_form = document.querySelector(
  ".teacher_list_search_form"
);
const teacher_list_select = document.querySelector("#teacher_list_select");
const teacher_search = document.querySelector("#teacher_search");

function getTeacherList(event) {
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

teacher_list_search_form.addEventListener("submit", getTeacherList2);

const teacher_list = document.querySelector(".teacher_list");
function getTeacherList2(event) {
  // getTeacherList에 넣을거
  //받아올 데이터: 사진, 선생님이름, 담당과목, 별점, 채팅하기??, 경력
  event.preventDefault();
  console.log(1);

  const card = document.createElement("li");
  card.classList.add("teacher_card");

  const img = document.createElement("img");
  img.classList.add("teacher_img");
  img.src = "https://i.ytimg.com/vi/rLueTjLWVCc/maxresdefault.jpg"; //database에서 가져오기

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
  input_chatting_button.value = "채팅하기";
  input_chatting_button.classList.add("chatting_button");
  //
  const description_bottom = document.createElement("div");
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
  career.innerText = "DB";

  teacher_list.appendChild(card);
}

function transformPrev(event) {
  const slidePrev = event.target;
  const slideNext = slidePrev.nextElementSibling;

  const classList = slidePrev.parentElement.parentElement.nextElementSibling;
  let activeLi = classList.getAttribute("data-position");
  const liList = classList.getElementsByTagName("li");

  // 하나의 카드라도 오른쪽으로 이동했다면, 왼쪽으로 갈 수 있음
  if (Number(activeLi) < 0) {
    activeLi = Number(activeLi) + 270;

    // 오른쪽에 있던 카드가 왼쪽  갔다면, 다시 왼쪽으로 갈 수 있으므로 next 버튼 활성화
    slideNext.style.color = "#2f3059";
    slideNext.classList.add("slide_next_hover");
    slideNext.addEventListener("click", transformNext);

    // 맨 왼쪽에 현재 보이는 카드가 맨 첫번째 카드면 왼쪽으로 갈 수 없으므로 prev 비활성화
    if (Number(activeLi) === 0) {
      slidePrev.style.color = "#cfd8dc";
      slidePrev.classList.remove("slide_prev_hover");
      slidePrev.removeEventListener("click", transformPrev);
    }
  }

  classList.style.transition = "transform 1s";
  classList.style.transform = "translateX(" + String(activeLi) + "px)";
  classList.setAttribute("data-position", activeLi);
}

function transformNext(event) {
  const slideNext = event.target;
  const slidePrev = slideNext.previousElementSibling;

  // ul 태그 선택
  const classList = slideNext.parentElement.parentElement.nextElementSibling;
  let activeLi = classList.getAttribute("data-position");
  const liList = classList.getElementsByTagName("li");

  /* classList.clientWidth 는 ul 태그의 실질적인 너비
   * liList.length * 270 에서 270 은 각 li 요소의 실질 너비(margin 포함)
   * activeLi 는 data-position 에 있는 현재 위치
   * 즉, liList.length * 270 + Number(activeLi) 는 현재 위치부터 오른쪽으로 나열되야 하는 나머지 카드들의 너비
   */

  /* classList.clientWidth < (liList.length * 270 + Number(activeLi)) 의미는
   * 오른쪽으로 나열될 카드들이 넘친 상태이므로, 왼쪽으로 이동이 가능함
   */

  if (classList.clientWidth < liList.length * 270 + Number(activeLi)) {
    // 위치를 오른쪽으로 270 이동 (-260px)
    activeLi = Number(activeLi) - 270;

    /* 위치를 오른쪽으로 270 이동 (-260px)
     * 해당 위치는 변경된 activeLi 값이 적용된 liList.length * 270 + Number(activeLi) 값임
     * 이 값보다, classList.clientWidth (ul 태그의 너비)가 크다는 것은
     * 넘치는 li 가 없다는 뜻으로, prev 버튼은 활성화 X
     */
    if (classList.clientWidth > liList.length * 270 + Number(activeLi)) {
      slideNext.style.color = "#cfd8dc";
      slideNext.classList.remove("slide_next_hover");
      slideNext.removeEventListener("click", transformNext);
    }

    slidePrev.style.color = "#2f3059";
    slidePrev.classList.add("slide_prev_hover");
    slidePrev.addEventListener("click", transformPrev);
  }

  classList.style.transition = "transform 1s";
  classList.style.transform = "translateX(" + String(activeLi) + "px)";
  classList.setAttribute("data-position", activeLi);
}


// ul 태그 선택
const slideNext_btn = document.querySelector(".slide_next");
const slidePrev_btn = slideNext_btn.previousElementSibling;
let classList = slideNext_btn.parentElement.parentElement.nextElementSibling;
let liList = classList.getElementsByTagName("li");

// 카드가 ul 태그 너비보다 넘치면, 왼쪽(prev) 버튼은 활성화하고, 오른쪽(next)는 현재 맨 첫카드 위치이므로 비활성화
if (classList.clientWidth < liList.length * 270) {
  slideNext_btn.classList.add("slide_next_hover");
  slideNext_btn.classList.remove("slide_next");
  slidePrev_btn.classList.add("slide_next");
  slideNext_btn.classList.add("slide_prev");// 스타일 변경 안돼서 클래스로 바꿈
  slideNext_btn.addEventListener("click", transformNext);
} else {
  /* 카드가 ul 태그 너비보다 넘치지 않으면, prev, next 버튼 불필요하므로, 아예 삭제함
      태그 삭제시, 부모 요소에서 removeChild 를 통해 삭제
        1. 먼저 부모 요소를 찾아서
        2. 부모 요소의 자식 요소로 있는 pev 와 next 요소를 삭제함
      */
  const arrowContainer = slideNext_btn.parentElement;
  arrowContainer.removeChild(slidePrev_btn.nextElementSibling);
  arrowContainer.removeChild(slidePrev_btn);
}
