# <span style="color:orange">회원가입  </span>

#### 형식 multipart/form-data 로 동일



### 일반 폼 - 학생  [ /join/student] [POST]

- username  -> 아이디
- password  -> 비번
- name -> 이름
-  age;  -> 나이
- profileImg; -> 프로필 사진(이미지 파일)





### 일반 폼 - 선생  [ /join/teacher] [POST]

- username  -> 아이디
- password  -> 비번
- name -> 이름
- age;  -> 나이
- profileImg; -> 프로필 사진(이미지 파일)
- career -> 경력 





### 카카오 폼 - 학생 [ /join/student/kakao] [POST]

- username  -> 아이디
- name -> 이름
- age;  -> 나이
- profileImg; -> 프로필 사진(이미지 파일)



### 카카오 폼 - 선생 [ /join/teacher/kakao] [POST]

- username  -> 아이디
- name -> 이름
- age;  -> 나이
- profileImg; -> 프로필 사진(이미지 파일)
- career -> 경력 







# <span style="color:orange">로그인 시</span>

#### 형식 JOSN 으로 동일



### 일반 계정 로그인 [/login] [POST]

- ##### username

- ##### password 



### 카카오 계정 로그인 [/login/kakao] [POST]

- ##### accessToken





# <span style="color:orange">회원 정보 수정/ 탈퇴 시</span>

#### 형식 multipart/form-data



### 학생 정보 수정 [/members/student] [PUT]

- name : 변경할 이름 (없으면 안넣어도 됨, 아래도 다 동일)
- age : 나이
- profileImg : 변경할 프사
- password : 비밀번호 변경



### 선생 정보 수정 [/members/teacher] [PUT]

- name : 변경할 이름 (없으면 안넣어도 됨, 아래도 다 동일)
- age : 나이
- profileImg : 변경할 프사
- password : 비밀번호 변경
- career : 경력



### 회원 탈퇴 [/members] [DELETE]

- password : 비밀번호 확인용으로









# <span style="color:orange">과외</span>

### 과외 등록 [/lesson] [POST]

#### 형식 multipart/form-data

- title : 제목
- content : 내용
- uploadFiles : 첨부파일
- lessonType : 등록하는 강의가 개인과외라면 PERSONAL, 그룹과외라면 GROUP 
- maxStudentCount : 그룹과외일 경우 최대 학생 수
- period : 그룹과외일 경우 모집기간(형식 2019-11-12) , 모집기간이 지금보다 과거로 설정 못하게 처리해야 함

#### 주의 : 과외는 오직 선생님만 올릴 수 있음





### 과외 정보 조회 [/lesson/{과외id}] [GET]

전달할 데이터 없음

#### 결과 :

- id - 과외 식별값

- title - 제목

- content - 내용

- views - 조회수

- #### teacher 

  - {name : 선생님 이름

     username : 선생님 username}

- recommendCount

- isCompleted

- #### uploadFiles

  - { filePath : 파일 경로

      filePath 

    ​	....

    }

- lessonType = 개인과외라면 PERSONAL, 그룹과외라면 GROUP 

- maxStudentCount = 그룹과외인 경우 최대 학생 수

- nowStudentCount = 그룹과외인 경우 현재 모집된 학생 수

- period = 그룹과외인 경우 만료기간

  



### 과외 수정 [/lesson/{과외id}] [PUT]

#### 형식 multipart/form-data

- title : 제목
- content : 내용
- uploadFiles : 첨부파일
- lessonType : 수정하는 강의가 개인과외라면 PERSONAL, 그룹과외라면 GROUP 
- maxStudentCount : 그룹과외일 경우 최대 학생 수
- period : 그룹과외일 경우 모집기간

#### 주의 : 과외는 오직 선생님만 올릴 수 있음

##### 



### 과외 삭제 [/lesson/{과외id}] [DELETE]

- password : 인증용





### 과외 검색 [/lesson/쿼리 파라미터 형식으로] [DELETE]

- page = 0 (page번째 페이지를 보여줌, **0부터 시작**)

- ##### size= X(한 페이지에 주어지는 과외들의 개수)(설정하지 않으면 기본값 20개, 원하는 기본값 말하면 바꿔줄 수 있으니까 말하셈!!)

- titile : 제목으로 검색

- teacherName: 선생님 이름으로 검색

- content : 내용으로 검색

- lessonType : ALL 이면 과외 종류 상관없이, PERSONAL이면 개인과외, GROUP이면 그룹과외

- maxStudentCount : 그룹과외시 최대 학생 수, 이 수보다 작은 과외들만 출력



> 예시 /lesson/page=1&size=10&title=키키
>
> => 제목에 키키가 들어간 과외들을 가져오는데, 과외들을 한 페이지에 10개씩 보여줄 때 1페이지에 존재하는 과외들을 가져옴

##### [참고]추가해야 할 거, 학생의 수를 사이값으로 입력받아 그 사이에 있는 과외들 출력

- ex 1, 5를 입력받으면 모집 학생 수가 1명이상 5명이하인 과외 가져오기

#### 반환타입  

- totalPageNum : 총 페이지 수

- currentPageNum : 현재 페이지 번호

- totalElementCount : 검색결과 나온 총 과외의 개수

- currentPageElementCount : 해당 페이지에 존재하는 과외의 개수

- simpleLectureDtoList {

  - id : 과외 id, 클릭 시 /lesson/id 이런식으로 이동하기 위해 사용
  - title : 과외 제목
  - views : 조회수
  - teacherName : 선생님 이름
  - recommendCount : 추천 수
  - lessonType: PERSONAL이면 개인과외, GROUP이면 그룹과외
  - maxStudentCount : 개인과외시 1 , 그룹과외시 최대 모집 인원
  - nowStudentCount : 그룹과외시 현재 모집된 인원
  - period : 그룹과외 시 모집기간
  - isCompleted : 모집완료된 과외인가? 맞다면 true, 아니라면 false

  }









### 동훈이 메모

#### 페이징 : 

(나만의 메모 : findAll(pagealbe) => 이런식으로 바로 사용가능!!!)

```
@PageableDefault(sort = { "id" }, direction = Direction.DESC, size = 2)
```

```
회원가입 시 프로필사진 올리는거 구현해야 함

MemberService의 getMyInfo리턴을 object로 해도 될까

중복 로그인 방지

카카오톡에서 탈퇴 시 알아서 탈퇴

properties파일 git ignore
```

