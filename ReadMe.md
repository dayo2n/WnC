## <span style="color:orange">DATABASE</span>

##### 공통

```
CREATED_DATE  		=> 생성일
LAST_MODIFIED_DATE  => 마지막 수정일
```



- ##### ALARM(알림)

  ```
  ALARM_ID  	 		=> PK, 식별값
  
  ALARM_TYPE  		=> 알람의 타입(SEND_APPLY, APPROVED, REFUSE,COMPLETION)
  IS_READ  			=> 알람을 확인했는지 여부
  
  APPLICATNT_MEMBER_ID 
  => FK, XX학생이 강의를 요청했습니다에서, XX를 맡는 역할(nullabe)
  
  LESSON_ID  	=> FK	=> 관련된 강의(null 불가능)
  
  MEMBER_ID  => FK  => 누구에게 전달되는 알림인지 (null 불가능)
  
  ```

  

- ##### APPLIED_LESSON(신청한 과외(<span style="color:orange">가입이 수락되지 않은 상태</span>))

```
APPLIED_LESSON_ID  	=> PK 식별값

TEACHER_ID  	  => 신청한 과외를 담당하는 선생님의 id
TEACHER_NAME  	  => 신청한 과외를 담당하는 선생님의 이름
TITLE  	          => 신청한 과외의 제목
LESSON_ID  		 => FK, 신청한 과외의 id
STUDENT_ID  	=> FK, 신청한 학생의 id
```



- ##### EVALUATION(평가)

```
EVALUATION_ID  	=> PK, 식별값

CONTENT  	    => 선생님 평가의 내용(String)
STAR_POINT  	=> 별점(0~5점, double)
STUDENT_ID  	=> FK , 해당 평가를 작성한 학생
TEACHER_ID  	=> FK , 해당 평가의 대상 선생님
```



- ##### LESSON(과외(정확히는 과외 정보를 담은게시물))

```
DTYPE  		=> 개인과외인 경우 PERSONAL, 그룹과외인 경우 GROUP

LESSON_ID  	=> PK, 식별값

CONTENT  	      => 과외의 내용
IS_COMPLETED  	  => 모집완료된 과외인지 여부(true이면 모집완료)
MAX_STUDENT_COUNT => 모집학생 수, (개인과외일 경우 1, 그룹과외일 경우 2이상)
TITLE  	          => 과외의 제목(게시물 쫘르륵 나열할 때 게시물만 보임)
VIEWS  			  => 조회수
NOW_STUDENT_COUNT => 현재 모집한 학생 수(신청한 학생 수 아니라, 수락을 눌러 가입확인을 한 학생 수, 개인과외일 경우 항상 0)
START_PERIOD		=> (그룹과외일 경우) 과외 시작일
END_PERIOD  	     => (그룹과외일 경우) 과외 종료일(형식(2021-11-11))
TEACHER_ID        => FK, 과외를 담당하는 선생님의 id


```



- ##### MEMBER(회원)

```
DTYPE  	 => 학생이면 S, 선생님이면 T

MEMBER_ID  	=> PK, 식별값

ACTIVATED  	=> 계정 활성화 여부(시큐리티때메 어쩔 수 없이 만들어둠)
AGE  	   => 나이
IS_KAKAO_MEMBER  	=> 카카오톡으로 가입한 멤버인지
KAKAO_ID  			=> kakaoId, 카카오톡으로 로그인 시 사용
NAME  	            => 이름
PASSWORD  	        => 비밀번호(카카오톡 로그인 시, UUID로 랜덤하게 생성하여 인코딩)
PROFILE_IMG_PATH  	=> 프로필 사진을 업호드 한 주소
ROLE  	 			=> 역할(BAISC, ADMIN)
USERNAME  	        => 아이디
CAREER  	        => 선생님일 경우에만 사용, 경력
STAR_POINT         => 선생님일 경우에만 사용 ,별점

```



- ##### TAKING_LESSON(가입된 과외, 아직 모집완료가 되지 않았을 수도 있다.)

```
TAKING_LESSON_ID  	 => PK, 식별값
	
TEACHER_NAME  	      => 선생님 이름
TEACHER_ID            => 선생님 id
TITLE  				  => 제목
LESSON_ID  			  => FK, 과외의 id
STUDENT_ID  		  => FK, 학생의 id

```



- ##### UPLOAD_FILE(업로드한 파일, 첨부파일)(프사에 올린건 X)

```
UPLOAD_FILE_ID  	 => PK, 식별값
	
FILE_PATH  	          => 사진이 저장된 주소, 
ex) www.naver.com/imgage/ddhiu128ddqw.png

LESSON_ID   => FK, 파일이 저장된 게시물의 id
```







# <span style="color:orange">기능 설명</span>

### 회원 관련

##### 회원가입/로그인

- 일반 회원가입과 카카오톡 API를 사용하여 회원가입 & 로그인 할 수 있다.
- 회원 가입 시 학생으로 가입할지, 선생님으로 가입할지 선택할 수 있다.



##### 회원 정보 수정

- 회원 정보를 수정할 수 있으며, 이름, 나이, 프로필 이미지와, 비밀번호를 변경할 수 있다.(추가로 선생님은 경력도 변경이 가능하다)

##### 

##### 회원 탈퇴하기

- 비밀번호를 재입력하여, 본인확인을 한 후 탈퇴가 가능하다, 

- ##### <span style="color:red">카카오톡으로 회원가입한 경우, 탈퇴 시 자동으로 연동된 서비스에서도 삭제시키고 싶은데 아직 구현하지 못했다</span>



##### 내정보 보기

- 학생의 경우 나의 id(식별값), 이름, 나이, 프로필 사진, 내가 듣는 강의의 목록, 내가 신청한 강의의 목록,  내가 작성한 평가를 확인할 수 있다.
- 선생님의 경우 id, 이름. 나이, 프사, 경력, 별점과, 내가 올린 강의(강의구인게시물), 나에게 작성된 평가를 확인할 수 있다.



##### 선생님 정보 검색

- 검색조건 : 선생님 이름, 별점(해당 별점 이상인 선생님들만 검색)



=============================회원 관련 기능 종료 ==========================



### 과외 관련

##### 게시물 등록, 수정

- 선생님만 가능, 개인과외와 그룹과외로 설정해서 등록할 수 있다.
- 그룹과외 모집 게시물을 등록할 경우 모집 인원, 과외 기간 등을 설정할 수 있습니다.

- ##### <span style="color:red">과외 기간이 지금보다 과거로 설정되었을 경우 예외를 발생시켜야 한다.(아직 미구현)</span>

### 

##### 게시물 삭제

- ##### 모집이 완료되었거나, 그룹과외의 경우 현재 가입한 학생이 존재한다면 삭제가 불가능합니다



##### 게시물 조회

- ##### 게시물을 클릭하면 게시물의 내용을 볼 수 있습니다. 동시에 조회수가 올라갑니다.





##### 과외 신청&신청취소&수락&거절&모집완료

- 학생은 과외를 신청할 수 있습니다.
- 또한 신청한 과외를 취소할 수 있으나, 이미 모집완료된 상태라면 취소하지 못합니다.
- 가입을 수락할 수 있습니다. 그러나 모집완료된 경우 수락하지 못합니다.
- 선생님은 가입을 거절할 수 있습니다
- 선생님은 임의로 과외를 모집완료 할 수 있습니다.(인원수가 다 채워지지 않아도 모집완료가 가능합니다)



##### 게시물 검색

- 제목, 선생님의 이름, 내용, 과외의 타입, 모집인원으로 검색할 수 있습니다.

### 

===================과외 관련 종료========================



### 알람 기능

- 과외에 가입신청을 보내거나, 가입된 과외가 모집완료된 경우, 가입이 승인되었거나 거절된 경우 알림이 갑니다.
- 알림은 클릭해서 읽을 수 있고, 삭제할 수는 없습니다.

- 알람 버튼을 클릭하면 내 알람 정보들의 나옵니다







### 평가 기능

- 강의 평가하기 버튼을 누르면 내가 평가할 수 있는 선생님들의 목록이 주어집니다.
- 평가내용과 별점을 입력할 수 있습니다.



================================================================











### 

# <span style="color:orange">REST API 통신</span>

### 일반 회원가입 - 학생

###  [<span style="color:pink">/join/student</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 너네가 나한테 줄 데이터

> username
>
> password
>
> name
>
> age
>
> profileImg => FILE 형식(단 한장만 가능)







### 일반 회원가입 - 선생

###  [<span style="color:pink">/join/teacher</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 너네가 나한테 줄 데이터

> username
>
> password
>
> name
>
> age
>
> career => 선생님은 경력이 추가!
>
> profileImg => FILE 형식(단 한장만 가능)







### 카카오 회원가입 - 학생

###  [<span style="color:pink">/join/student/kakao</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 너네가 나한테 줄 데이터

> accessToken => 인가 토큰, 
>
> username
>
> name
>
> age
>
> profileImg => FILE 형식(단 한장만 가능)





### 카카오 회원가입 - 선생

###  [<span style="color:pink">/join/teacher/kakao</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 너네가 나한테 줄 데이터

> accessToken => 인가 토큰, 
>
> username
>
> name
>
> age
>
> career => 선생님은 경력이 추가!
>
> profileImg => FILE 형식(단 한장만 가능)

==============================================================================





==============================================================================

### 일반 로그인 

###  [<span style="color:pink">/login</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">JSON</span>]

##### 너네가 나한테 줄 데이터

> username
>
> password



### 카카오 로그인 

###  [<span style="color:pink">/login/kakao</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">JSON</span>]

##### 너네가 나한테 줄 데이터

> accessToken

==============================================================================





==============================================================================

### 내 정보 보기 - 학생

###  [<span style="color:pink">/myInfo</span>] [<span style="color:blue">GET</span>] 

> ##### 필요한 데이터 없음

#### 반환타입

> id => 식별값, 내 id
>
> name
>
> age
>
> profileImgPath => 프로필 사진이 저장된 경로 ex) www.naver.com/image/myprofile.jpg
>
> isKakaoMember => 카카오톡으로 가입한 사람이면 true
>
> #### takingLessonList(내가 듣는 강의 목록, 여러개가 나옴){
>
> ​		lessonId (Long 타입) : 강의의 id
>
> ​		title  :강의의 제목
>
> ​		createdDate (XXXX-XX-XX 형식),내가 강의에 가입된 시간
>
> ​		teacherId => (Long타입) 선생님의 id(id로 식별가능,  버튼 클릭 시 해당 선생님의 정보를 조								회할 수 있게끔 구현해!! )
>
> ​		teacherName => 선생님의 이름
>
> #### }
>
> 
>
> #### appliedLessonList(내가 신청한 강의 목록(아직 승인받지 않은 강의들)){
>
> ​		lessonId (Long 타입) : 강의의 id
>
> ​		title  :강의의 제목
>
> ​		createdDate (XXXX-XX-XX 형식),내가 강의에 가입된 시간
>
> ​		teacherId => (Long타입) 선생님의 id
>
> ​		teacherName => 선생님의 이름
>
> #### }
>
> 
>
> #### evaluationList(내가 한 평가들 목록){
>
> ​	evaluationId : 평가 id(식별값)
> ​	content  : 평가내용
> ​	starPoint : 별점
> ​	teacherId : 평가한 선생님 id
> ​	teacherName : 평가한 선생님 이름
>
> #### }





### 내 정보 보기 - 선생

###  [<span style="color:pink">/myInfo</span>] [<span style="color:blue">GET</span>] 

> ##### 필요한 데이터 없음

#### 반환타입

> id => 식별값, 내 id
>
> name
>
> age
>
> profileImgPath => 프로필 사진이 저장된 경로 ex) www.naver.com/image/myprofile.jpg
>
> career
>
> starPoint  별정
>
> isKakaoMember => 카카오톡으로 가입한 사람이면 true
>
> #### lessonList(내가 올린 강의 목록){
>
> ​		lessonId (Long 타입) : 강의의 id
>
> ​		title  :강의의 제목
>
> ​		createdDate (XXXX-XX-XX 형식),내가 강의를 생성한 날짜
>
> ​		isCompleted=> 모집완료 여부
>
> ​		views =>조회수
>
> ​		lessonType => 과외 타입 개인과외면 PERSONAL, 그룹과외면 GROUP
>
> #####  <span style="color:red">maxStudentCount</span>=>최대 인원 수, (개인과외일 경우 표시하지 말아줘!!!, 알아서 처리 부탁)
>
> #####  <span style="color:red">nowStudentCount</span>=>현 인원 수, (개인과외일 경우 표시하지 말아줘!!!, 알아서 처리 부탁)
>
> #### }
>
> 
>
> #### evaluationList(나에 대한 평가 목록){
>
> ​	evaluationId : 평가 id(식별값)
>
> ​	content  : 평가내용
> ​	starPoint : 별점
> ​	studentName: 평가한 학생 이름. id는 
>
> #### }
>
> 
>
> 



==============================================================================

### 내 정보 수정- 학생

###  [<span style="color:pink">/members/student</span>] [<span style="color:blue">PUT</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 너네가 나한테 줄 데이터

> name
> age
> profileImg => 이미지 파일로 줘!
> password

#### 참고 : 만약에 이름만 바꿀거면 나머지 필드는 그냥 채우지 말고 주면 됌!(괜히 원래거랑 똑같이 채우면 머리아프니까 변경사항 없는 부분은 빈칸으로!)

#### 참고 2: 카카오톡으로 가입한 사람은 비밀번호 변경 자체가 불가능해야 함. 판단은 내가 내정보보기에서 준 isKakaoMember 로 하셈!!!!





### 내 정보 수정- 선생

###  [<span style="color:pink">/members/teacher</span>] [<span style="color:blue">PUT</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 너네가 나한테 줄 데이터

> name
> age
> profileImg => 이미지 파일로 줘!
> password
>
> career



### 참고 : 사진을 입력 안 하면 기존 사진이 유지된다!

##### 그럼 사진을 지우고 싶을때는 어떻게 하냐?

##### 나도 모름... 어카지... 일단 천천히 생각해보자

### <span style="color:red">(일단 지금 생각으론, doProfileImgChange => true일 경우 프로필 이미지가 없으면 기본 이미지로 변경하는 그런 느낌의 코드를 짜고싶은데 아직 미구현!)</span>

==============================================================================



### 회원 탈퇴 - 일반

###  [<span style="color:pink">/members</span>] [<span style="color:blue">DELETE</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 나한테 줄 데이터

> password



### 회원 탈퇴 - 카카오

###  [<span style="color:pink">/members/kakao</span>] [<span style="color:blue">DELETE</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 나한테 줄 데이터

> accessToken



==============================================================================



### 

### 선생님 목록 조회

###  [<span style="color:pink">/members/teachers</span>] [<span style="color:blue">GET</span>]  

##### 검색 조건: teacherName, starPoint

##### 기본 사이즈 12, 기본 페이지 0(<span style="color:red">무조건 첫번째 페이지는 1이 아니라 0이다</span>)

##### 결과들은 별점이 높은 순 (우선순위1)+최근에 가입한 순(우선순위2) 로 정렬되어 보여짐

##### 예시

> 선생님 목록을 조회할건데, 검색 조건은 선생님의 이름과 별점이고, 12개씩 나눴을때  0번째 페이지에 해당하는 선생님들을 보여줘
>
> /members/teachers?**teacherName=선생님이름&starPoint=3**
>
> (위의 예시는 기본 페이지가 0, 기본 사이즈가 12이이므로 생략 가능)

##### 예시 2

> 선생님 목록을 조회할건데, 검색 조건은 선생님의 이름이고, 8개씩 나눴을때  1번째 페이지에 해당하는 선생님들을 보여줘
>
> /members/teachers?**teacherName=선생님이름&size=8&page=1**

##### 예시 3

> 어우 맨 처음 선생님 검색하는 화면에 들어갔을때는 아무런 검색 조건 없이 12개씩 나눠서, 0페이지에 있는 선생님들을 보여줄거야. 
>
> 이때 기본 검색 조건에 따라 별점이 제일 높은 선생님부터 보여줄거고, 그 다음에 최근에 가입한 선생님 순으로 보여줄거야!
>
> /members/teachers

##### 예시 4

> 나는 별점이 낮은 선생님부터 보고싶어!
>
> 또는 나는 가입한지 가장 오래된 선생님부터 보고싶어!
>
> /members/teachers&sort=starPoint,ASC
>
> /members/teachers&sort=createdDate,ASC
>
> 
>
> 나는 별점은 낮고 가입한지 가장 오래된 선생님부터 보고싶어!
>
> /members/teachers&sort=createdDate,ASC&sort=starPoint,ASC
>
> ### (이거 될지 안될지 모르겠음, 일단 해보고 안되면 같이 고쳐보장 ㅎㅎ)

#### 결과 데이터

> totalPageNum = 전체 페이지 수
>
> currentPageNum = 현재 페이지 번호
>
> totalElementCount = 검색 결과 조회된 전체 선생님 수
>
> currentPageElementCount = 현재 페이지에 존재하는 선생님의 수
>
>  
>
> ####  teacherProfiles(선생님 정보 리스트){
>
> ​	id => 선생님 id(식별값으로, 클릭하면 선생님 볼 수 있도록 구현해!, 이 밑에서 소개할 선생님 1		명 정보 조회하기로 요청 보내면 돼!)
> ​	name  => 선생님 이름
> ​	age => 
> ​	profileImgPath => 선생님 프사 주소
> ​	career => 선생님 경력
> ​	starPoint => 선생님 별점
>
> #### }



==============================================================================



### 선생님 1명 조회

###  [<span style="color:pink">/members/teachers</span>/<span style="color:red">{teacherId}</span>] [<span style="color:blue">GET</span>]  

##### 예시 /members/teachers/123  => id가 123번인 선생님 조회

##### 언제 사용하냐? 엄청 많은데 몇개만 예시

- 학생인 경우 -> 내 정보 보기 -> 내가 수강한 강의 목록 보기 -> 여기서 강의들에 대해 teacherId를 주었으므로 이것을 가지고 클릭하면, 선생님의 정보를 볼 수 있게!
- 선생님들을 검색했을 때, 여러 선생님들의 목록 중, 아무 선생님이나 클릭하면 해당 선생님의 id를 가지고 여기로 요청 => 선생님 정보를 볼 수 있게!!!

등등

#### 결과 데이터

> teacherId => 선생님의 id (식별값)
> teacherName 
> age
> profileImgPath=> 선생님의 프사 주소
> career
> starPoint



=========================================================================











=========================================================================

### 과외 게시물 등록

### [<span style="color:pink">/lesson</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 나에게 줄 데이터

>  title   게시물 제목
>
>  content   게시물 내용
>
> #####  uploadFiles   게시물에 첨부할 파일, 여러개의 파일을 첨부할 수 있으며 각각의 파일은 크기가 1MB 이하, 첨부할 모든 파일의 크기는 5MB 이하여야 함
>
> #####  lessonType => 개인과외면 PERSONAL, 그룹과외면 GROUP
>
> #####  maxStudentCount => 개인과외일 경우 입력하지 마(사실 입력해도 에러는 안나게 처리했는데 그래도 그냥 프론트단에서 개인과외일 경우 선택지를 아예 없애버리면 더 이쁠 거 같아) 
>
> #####  startPeriod =>    형식 XXXX-XX-XX ,마찬가지로 개인과외일 경우 입력 X
>
> #####  endPeriod =>    형식 XXXX-XX-XX  ,마찬가지로 개인과외일 경우 입력 X



=========================================================================

### 과외 1개 조회

### [<span style="color:pink">/lesson</span>/{lessonId}] [<span style="color:blue">GET</span>] 

##### 나에게 줄 데이터 없음

##### 결과 데이터

> id                        과외 id
>
> title
>
> content           ->내용
>
> views          -> 조회수
>
> createdDate => 작성일
>
> isCompleted    => 모집완료된 강의인지 여부(모집완료됐으면 true)
>
> 
>
> ##### lessonType       타입-> PERSONAL, GROUP 이제 알제?
>
> ##### maxStudentCount   => 개인과외일 경우 아마 0으로 갈건데, 어차피 개인과외는 최대 인원 1명이니까  그냥 개인과외면 이거 무시하셈
>
> ##### nowStudentCount => 똑같이 개인과외면 무시
>
> ##### startPeriod   => 똑같이 개인과외면 무시
>
> ##### endPeriod   => 똑같이 개인과외면 무시
>
> 
>
> ##### teacher     {
>
> ​	teacherId =>  선생님 id(식별값)
>
> ​	name =>   선생님 이름
>
> ##### }    
>
> 
>
> #####  uploadFiles{
>
> ​	filePath =  업로드한 파일의 경로
>
> ##### }
>
>  

=========================================================================

### 과외 목록 검색

### [<span style="color:pink">/lesson</span>] [<span style="color:blue">GET</span>] 

##### 검색 조건: title, teacherName, content, lessonType(그룹과외인지(GROUP), 개인과외인지(PERSONAL), 전체 다인지(ALL))

##### minStudentCount(모집인원 최소) ~ maxStudentCount(모집인원 최대) 

##### 기본 사이즈 12, 기본 페이지 0(<span style="color:red">무조건 첫번째 페이지는 1이 아니라 0이다</span>)

##### 최근에 생성된 순서로 정렬되어 보여짐

#### 결과 데이터

> totalPageNum 총 페이지 수
> currentPageNum 현재 페이지 번호
> totalElementCount 전체 과외 개수
> currentPageElementCount  현재 페이지 과외 개수
>
> 
>
> #### simpleLectureDtoList{
>
> ​	id                        과외 id
>
> ​	title
>
> ​	views          -> 조회수
>
> ​	 teacherName => 작성자 이름
>
> ​	createdDate => 작성일
>
> ​	isCompleted    => 모집완료된 강의인지 여부(모집완료됐으면 true)
>
> ##### 	lessonType       타입-> PERSONAL, GROUP 
>
> ##### 	maxStudentCount   => 개인과외일 경우 아마 0으로 갈건데, 어차피 개인과외는 최대 인원 1		명이니까  그냥 개인과외면 이거 무시하셈
>
> ##### 	nowStudentCount => 똑같이 개인과외면 무시
>
> ##### 	startPeriod   => 똑같이 개인과외면 무시
>
> ##### 	endPeriod   => 똑같이 개인과외면 무시
>
> #### }
>
> ##### 
>
>  

=========================================================================



### 과외 수정 

### [<span style="color:pink">/lesson</span>/{lessonId}] [<span style="color:blue">PUT</span>]   [<span style="color:skyblue">multipart/form-data</span>]

##### 보내줄 데이터

> title 제목
>
> content 내용
>
> ##### uploadFiles  게시물에 첨부할 파일, 여러개의 파일을 첨부할 수 있으며 각각의 파일은 크기가 1MB 이하, 첨부할 모든 파일의 크기는 5MB 이하여야 함
>
> ##### lessonType => 그룹과외인데 PERSONAL로 보내거나 그 반대 => 예외 발생시킬거임!, 그냥 그룹과외 수정하면 lessonType =GROUP 주고, 개인과외 수정하면 lessonType = PERSONAL 주셈!
>
> ##### maxStudentCount => 개인과외일 경우 보내지 망!
>
> ##### startPeriod => 개인과외일 경우 보내지 망!
>
> ##### endPeriod => 개인과외일 경우 보내지 망!



### 회원정보 수정과 동일하게, 첨부파일 삭제 미구현!

=========================================================================





### 과외 삭제

### [<span style="color:pink">/lesson</span>/{lessonId}] [<span style="color:blue">DELETE</span>]   [<span style="color:skyblue">multipart/form-data]

##### 보내줄 데이터

> password





=========================================================================



### 과외 가입신청

### [<span style="color:pink">/lesson</span>/{lessonId}/apply] [<span style="color:blue">POST</span>]  

##### 보내줄 데이터 없음

##### 결과 데이터도 없음

##### 과외를 신청하면 해당 과외를 등록한 사람한테 알림이 가도록 구현함!

#### 모집 완료된 강의는 신청 못 하게 프론트에서 걸러줘!!!(백에서도 거르긴 했는데, 그건 진짜 답도없는 에러 막을라고 거른거! 앞에서 걸러주면 편함!)





### 과외 가입취소

### [<span style="color:pink">/lesson</span>/{lessonId}/cancel] [<span style="color:blue">POST</span>]  

##### 보내줄 데이터 없음

##### 결과 데이터도 없음



#### 모집 완료된 강의는 가입취소 못 하게 막아줘!!!





### 과외 가입 신청 수락

### [<span style="color:pink">/lesson/{lessonId}/accept/{alarmId}</span>] [<span style="color:blue">POST</span>]  

##### 보내줄 데이터 없음

##### 결과 데이터도 없음

##### 과외를 신청하면 해당 과외를 등록한 사람한테 알림이 가도록 구현함!

#### 모집 완료된 강의는 더이상 수락 못 받게 걸러줘!





### 과외 가입 신청 거절

### [<span style="color:pink">/lesson/{lessonId}/cancel/{alarmId}</span>] [<span style="color:blue">POST</span>]  

##### 보내줄 데이터 없음

##### 결과 데이터도 없음





### 과외 모집 완료(인원이 다 안 찼을 경우에, 선생님이 임의로 모집완료 버튼을 누른 경우)

#### (나머지 경우, 알아서 모집인원이 다 차면 모집완료 알림을 보내줌)

### [<span style="color:pink">/lesson/{lessonId}/complete</span>] [<span style="color:blue">POST</span>]  

##### 보내줄 데이터 없음

##### 결과 데이터도 없음

=========================================================================







### 나의 알람 목록 조회

### [<span style="color:pink">/myInfo/myAlarms</span>] [<span style="color:blue">GET</span>]  

##### 조회조건 : READ, NOT_READ, ALL(읽은것만, 안읽은것만, 모두다)

##### 기본 사이즈 12, 기본 페이지 0(<span style="color:red">무조건 첫번째 페이지는 1이 아니라 0이다</span>)

#### 요청 => readType=위 3개 조건중 하나

##### 결과 데이터

> totalPageNum 총 페이지 수
> currentPageNum 현재 페이지 번호
> totalElementCount 전체 알람 개수
> currentPageElementCount  현재 페이지 알람 개수
>
> 
>
> #### simpleAlarmList(목록 -> 여러개){
>
> ​	id		=>  알람 id
>
> ​	alarmType  	=>  알람타입 (SEND_APPLY, APPROVED, REFUSED, COMPLETION)
>
> ​	isRead	=>  읽은 알람이면 ture
>
> ​	createdDate	=>  알람이 온 날짜
>
> 
>
> #### 	lessonDto (어떤 강의에 대한 알람인지, 1개의 알림당 1개){
>
> ##### 		lessonId  => 강의 id
>
> ##### 		<span style="color:red">title      => 강의 제목</span>
>
> ##### 		teacherName  => 선생님 이름
>
> #### 	}
>
> 
>
> #### 	applicantMemberDto (강의를 신청한 학생 정보, 알람 종류에 따라 없을	수도 있음, 1개의 알림당 1개 혹은 0개)){
>
> ##### 		applicantStudentId => 신청한 학생 id
>
> ##### 		<span style="color:red">studentName => 신청한 학생 이름</span>
>
> #### 	}
>
> #### }

### <span style="color:red">중요</span>

#### 알람 타입에 따른 알림 메세지 형식

- ##### SEND_APPLY =>lessonDto , applicantMemberDto 가 모두 존재

##### => 선생님께만 전달되며, 형식은 

##### [<span style="color:red">studentName</span> 학생이 <span style="color:red">title</span> 과외에 가입신청을 보냈습니다.]



- ##### APPROVED

##### => 학생에게만 전달되며, 형식은 

##### [teacherName님이 수업하시는 <span style="color:red">title</span> 과외에 가입되셨습니다.]



- ##### REFUSED

##### => 학생에게만 전달되며, 형식은 

##### [teacherName님이 수업하시는 <span style="color:red">title</span> 과외에 가입이 거절되었습니다.]



- ##### COMPLETION

##### => 교수님과 학생모두 전달 가능하며, 전달되며, 형식은 

##### [teacherName님이 수업하시는<span style="color:red"> title</span> 과외의 모집이 완료되었습니다.]

=========================================================================





### 알람 1개 읽기

### [<span style="color:pink">/myInfo/myAlarms/{alarmId}</span>] [<span style="color:blue">GET</span>]  

##### 결과 데이터

> ​	id		=>  알람 id
>
> ​	alarmType  	=>  알람타입 (SEND_APPLY, APPROVED, REFUSED, COMPLETION)
>
> ​	createdDate	=>  알람이 온 날짜
>
> 
>
> #### 	lessonDto (어떤 강의에 대한 알람인지, 1개의 알림당 1개){
>
> ##### 		lessonId  => 강의 id
>
> ##### 		<span style="color:red">title      => 강의 제목</span>
>
> ##### 		teacherName  => 선생님 이름
>
> #### 	}
>
> 
>
> #### 	applicantMemberDto (강의를 신청한 학생 정보)){
>
> ##### 		applicantStudentId => 신청한 학생 id
>
> ##### 		<span style="color:red">studentName => 신청한 학생 이름</span>
>
> #### 	}
>
> #### }

### <span style="color:red">중요</span>

#### 알람 타입에 따른 알림 메세지 형식

- ##### SEND_APPLY =>lessonDto , applicantMemberDto 가 모두 존재

##### => 선생님께만 전달되며, 형식은 

##### [<span style="color:red">studentName</span> 학생이 <span style="color:red">title</span> 과외에 가입신청을 보냈습니다. 

##### 수락하시겠습니까? (예, 아니오)

##### <span style="color:orange">중요</span>

- ##### 예를 눌렀을 시 -> 강의 수락 요청 [<span style="color:pink">/lesson/{lessonId}/accept/{alarmId}</span>] [<span style="color:blue">POST</span>]  

- ##### 아니오 눌렀을 시 -> [<span style="color:pink">/lesson/{lessonId}/cancel/{alarmId}</span>] [<span style="color:blue">POST</span>]  

##### 만약 누르지 않았다면 이 알림은 읽지 않은 상태로 냅둘거임!

##### ]



- ##### APPROVED

##### => 학생에게만 전달되며, 형식은 

##### [teacherName님이 수업하시는 <span style="color:red">title</span> 과외에 가입되셨습니다.]



- ##### REFUSED

##### => 학생에게만 전달되며, 형식은 

##### [teacherName님이 수업하시는 <span style="color:red">title</span> 과외에 가입이 거절되었습니다.]



- ##### COMPLETION

##### => 교수님과 학생모두 전달 가능하며, 전달되며, 형식은 

##### [teacherName님이 수업하시는<span style="color:red"> title</span> 과외의 모집이 완료되었습니다.]

=========================================================================











## 선생님은 선생님 평가 불가능!!!!



### 평가 가능한 선생님 목록 조회하기(내정보 -> 평가하기 등 처럼 구현)

### [<span style="color:pink">/myInfo/evaluation/teachers</span>] [<span style="color:blue">GET</span>]

#### 결과 데이터  

> 
>
> ##### totalElementCount -> 선생님 수, (이전과는 다르게 총 수밖에 없는데, 이건 페이징이 너무 힘들어서 포기,, 어차피 수업 그렇게 많이 안 들을거니까 이렇게 하자 ㅎㅎ)
>
>  
>
> #### evaluationTeacherDtoList (평가 가능한 선생님 정보){
>
> ​	id    => 선생님 id
>
> ​	name   => 선생님 이름
>
> ​	age  
>
> ​	profileImgPath    =>선생님 프사 url
>
> ​	career
>
> ​	starPoint
>
> #### }







### 선생님 평가하기

### [<span style="color:pink">/myInfo/evaluation/teachers/{teacherId}</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">JSON</span>]



#### 보내줘야 할 데이터

> content - 평가내용
>
> starPoint - 별점 (1~5점 사이, 소수점 1자리까지 허용)



#### 

















# 미구현 목록

- ##### <span style="color:red">과외 기간이 지금보다 과거로 설정되었을 경우 예외를 발생시켜야 한다.(아직 미구현)</span>

- ##### 그룹과외일 경우 최대 모집인원 최소 2명 이상, 예외처리!

- ##### 관리자 기능 구현

- ##### 선생님 신고 기능

- ##### 블랙리스트 기능

- ##### 채팅 기능





- ##### 카카오톡으로 회원가입한 경우, 탈퇴 시 자동으로 연동된 서비스에서도 삭제시키고 싶은데 아직 구현하지 못했다 => [일단은 구현, 확인해봐야 알아]









# 나혼자 볼거임

- ##### 왜 TAKING_LESSON에서 Teacher는 엔티티로 사용하지 않았지?

- ![image-20211111221744194](https://raw.githubusercontent.com/ShinDongHun1/image_repo/main/img/image-20211111221744194.png)

UploadFile의 해당 부분 때문에, DB가 이상하게 생성되서 지웠다. 

UploadFile은 Lesson에 JoinColumn을 사용하여, 연관관계의 주인이 되게 했고, 영속성 전이와 고아 객체 제거 기능을 사용하여 관리한다. 따라서 없어도 될것같은데, 혹시 오류가 발생하지는 않는지 확인해보기.

##### 

# 주의

- 과외를 생성, 수정할 때, 모집기간을 변경한다면, String으로 받아와서

  ```java
  LocalDate.parse(period, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
  ```

  해당 로직을 통해 LocalDateTime으로 전환해야 함.





# 조건

- ##### 회원가입/로그인 (완료)

  - ##### 회원 분류는 선생님, 학생, 관리자가 있습니다.(관리자 미구현)

  - ##### 선생님은 경력을 입력할 수 있습니다.(구현)

    - ex) OO대학교 졸업, 과외 경력 O년, OO대회 수상 등

  - ##### [추가] 소셜 로그인 : 카카오, 구글, 깃허브 등 계정으로 소셜로그인을 할 수 있습니다.(카카오만 구현)



- ##### 회원 정보 수정/탈퇴 (수정 완료, 탈퇴 미구현)



- 선생님은 과외 학생 모집 게시물을 등록/수정/삭제할 수 있습니다.

  - ##### 과외는 1대1 과외, 그룹 과외가 있습니다.(구현)

  - ##### 그룹과외 모집 게시물을 등록할 경우 모집 인원, 과외 기간 등을 설정할 수 있습니다.(구현)

  - ##### 제목, 선생님, 모집 인원 등으로 게시물을 검색할 수 있습니다.(구현)



- ##### 선생님 목록 보기 및 검색 ⇒ 별점이 높은순, 학생들이 많이 수강한 순 등으로 볼 수 있고 검색도 가능합니다.(검색 가능, 별점이 높은 순으로 미구현)

  - [추가] 선생님과 채팅기능 : 선생님 목록을 보다가 과외를 받고싶은 선생님이 있으면 채팅을 할 수 있습니다.





- ##### 모집 완료 알림 기능

  - ##### 학생 모집이 완료되었으면 선생님 및 신청 학생들에게 알려줍니다.(구현)



- ##### 선생님의 수업에 참가하는 학생은 선생님에 대한 평가 작성 및 별점을 줄 수 있으며 신고하기 기능도 있습니다. (원래는 과외 기간이 끝난 후 가능해야하지만, 기능 확인을 위해 참여하기만 하면 평가 가능하도록 구현해주시면 됩니다.)  (평가 작성 구현, 신고 기능 미구현)



- ##### 관리자 페이지 : *일정 건수 이상의 신고를 받은 선생님은 관리자가 검토 후 블랙리스트에 등록할 수 있습니다. 블랙리스트에 등록된 선생님은 학생 모집 게시물을 올릴 수 없습니다.(미구현)

  - *신고 건수 기준은 자유롭게 정하시면 됩니다.