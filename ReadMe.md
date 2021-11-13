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

<br/>

<br/>

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

<br/>

<br/>

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

<br/>

<br/>

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

<br/>

==============================================================================

<br/>

<br/>

==============================================================================

### 일반 로그인 

###  [<span style="color:pink">/login</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">JSON</span>]

##### 너네가 나한테 줄 데이터

> username
>
> password



#### 결과 데이터

> memberType = // STUDENT, TEACHER
>
> token = 로그인토큰
>
> id = 내 식별값
>
> myNoReadChatCount   => 내가 안 읽은 메세지 수, 0이면 다 읽은거
> myNoReadAlarm          => 내가 안 읽은 알람 수, 0이면 다 읽은거



<br/>

<br/>

### 카카오 로그인 

###  [<span style="color:pink">/login/kakao</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">JSON</span>]

##### 너네가 나한테 줄 데이터

> accessToken

#### 결과 데이터

> memberType = // STUDENT, TEACHER
>
> token = 로그인토큰
>
> id = 내 식별값
>
> myNoReadChatCount   => 내가 안 읽은 메세지 수, 0이면 다 읽은거
> myNoReadAlarm          => 내가 안 읽은 알람 수, 0이면 다 읽은거

<br/>

==============================================================================

<br/>

<br/>

==============================================================================

### 내 정보 보기 - 학생

###  [<span style="color:pink">/myInfo</span>] [<span style="color:blue">GET</span>] 

> ##### 필요한 데이터 없음

<br/>

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
> 		lessonId (Long 타입) : 강의의 id
> 	
> 		title  :강의의 제목
> 	
> 		createdDate (XXXX-XX-XX 형식),내가 강의에 가입된 시간
> 	
> 		teacherId => (Long타입) 선생님의 id(id로 식별가능,  버튼 클릭 시 해당 선생님의 정보를 조								회할 수 있게끔 구현해!! )
> 	
> 		teacherName => 선생님의 이름
>
> #### }
>
> 
>
> #### appliedLessonList(내가 신청한 강의 목록(아직 승인받지 않은 강의들)){
>
> 		lessonId (Long 타입) : 강의의 id
> 	
> 		title  :강의의 제목
> 	
> 		createdDate (XXXX-XX-XX 형식),내가 강의에 가입된 시간
> 	
> 		teacherId => (Long타입) 선생님의 id
> 	
> 		teacherName => 선생님의 이름
>
> #### }
>
> 
>
> #### evaluationList(내가 한 평가들 목록){
>
> 	evaluationId : 평가 id(식별값)
> 	content  : 평가내용
> 	starPoint : 별점
> 	teacherId : 평가한 선생님 id
> 	teacherName : 평가한 선생님 이름
>
> #### }

<br/>

<br/>

<br/>

### 내 정보 보기 - 선생

###  [<span style="color:pink">/myInfo</span>] [<span style="color:blue">GET</span>] 

> ##### 필요한 데이터 없음

<br/>

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
> 
>
> isBlack    => 블랙리스트면 true
>
> warningCount   => 받은 경고 수
>
> 
>
> #### lessonList(내가 올린 강의 목록, 여러개){
>
> 		lessonId (Long 타입) : 강의의 id
> 	
> 		title  :강의의 제목
> 	
> 		createdDate (XXXX-XX-XX 형식),내가 강의를 생성한 날짜
> 	
> 		isCompleted=> 모집완료 여부
> 	
> 		views =>조회수
> 	
> 		lessonType => 과외 타입 개인과외면 PERSONAL, 그룹과외면 GROUP
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
> 	evaluationId : 평가 id(식별값)
> 	
> 	content  : 평가내용
> 	starPoint : 별점
> 	studentName: 평가한 학생 이름. id는 신분 보호를 위해 비밀 + 만약 이름이 없다면 "귀욤둥이 								동훈이"로 통일
>
> #### }
>

<br/>

==============================================================================

<br/>

<br/>

### 내 정보 수정- 학생

###  [<span style="color:pink">/members/student</span>] [<span style="color:blue">PUT</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 너네가 나한테 줄 데이터

> name
> age
> profileImg => 이미지 파일로 줘!
> password
>
> ##### doProfileImgChange -> boolean형태, 프사 바꿀꺼면 true

##### 참고 : 만약에 이름만 바꿀거면 나머지 필드는 그냥 채우지 말고 주면 됌!(괜히 원래거랑 똑같이 채우면 머리아프니까 변경사항 없는 부분은 빈칸으로!)

##### 참고 2: 카카오톡으로 가입한 사람은 비밀번호 변경 자체가 불가능해야 함. 판단은 내가 내정보보기에서 준 isKakaoMember 로 하셈!!!!

##### 참고 3: 이미지 파일 안 올리고 doProfileImgChange 을 true로 주면 기존의 이미지 파일을 삭제하고 기본 이미지로 변경할거임!

<br/>

<br/>

### 내 정보 수정- 선생

###  [<span style="color:pink">/members/teacher</span>] [<span style="color:blue">PUT</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 너네가 나한테 줄 데이터

> name
> age
> profileImg => 이미지 파일로 줘!
> password
>
> career
>
> ##### doProfileImgChange -> boolean형태, 프사 바꿀꺼면 true

<br/>

<br/>

<br/>

==============================================================================

<br/>

<br/>

### 회원 탈퇴 - 일반

###  [<span style="color:pink">/members</span>] [<span style="color:blue">DELETE</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 나한테 줄 데이터

> password

<br/>

<br/>

### 회원 탈퇴 - 카카오

###  [<span style="color:pink">/members/kakao</span>] [<span style="color:blue">DELETE</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 나한테 줄 데이터

> accessToken

<br/>

==============================================================================

<br/>

<br/>

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

<br/>

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
> 	id => 선생님 id(식별값으로, 클릭하면 선생님 볼 수 있도록 구현해!, 이 밑에서 소개할 선생님 1		명 정보 조회하기로 요청 보내면 돼!)
> 	name  => 선생님 이름
> 	age => 
> 	profileImgPath => 선생님 프사 주소
> 	career => 선생님 경력
> 	starPoint => 선생님 별점
>
> #### }

<br/>

==============================================================================

<br/>

<br/>

<br/>

### 선생님 1명 조회 

###  [<span style="color:pink">/members/teachers</span>/<span style="color:red">{teacherId}</span>] [<span style="color:blue">GET</span>]  

##### 예시 /members/teachers/123  => id가 123번인 선생님 조회

##### 언제 사용하냐? 엄청 많은데 몇개만 예시

- 학생인 경우 -> 내 정보 보기 -> 내가 수강한 강의 목록 보기 -> 여기서 강의들에 대해 teacherId를 주었으므로 이것을 가지고 클릭하면, 선생님의 정보를 볼 수 있게!
- 선생님들을 검색했을 때, 여러 선생님들의 목록 중, 아무 선생님이나 클릭하면 해당 선생님의 id를 가지고 여기로 요청 => 선생님 정보를 볼 수 있게!!!

등등

#### 결과 데이터

> totalElementCount => 총 평가 수
>
> 
>
> id => 선생님의 id (식별값)
> name
> age
> profileImgPath=> 선생님의 프사 주소
> career
> starPointAvg => 별점 평균
>
> 
>
> ##### evaluationDtos(평가 목록 여러개!){
>
> 	evaluationId => 평가의 식별값
> 	content =>  내용
> 	starPoint =>별점
> 	studentName => 평가한 학생 이름(학생이 회원탈퇴했다면 "귀욤둥이 동훈이"로 통일)
>
> ##### }













<br/>

=========================================================================

<br/>

<br/>

<br/>

=========================================================================

### 과외 게시물 등록

#### 블랙리스트이면 불가능 =>예외처리 해줘!

### [<span style="color:pink">/lesson</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 나에게 줄 데이터

>  title   게시물 제목
>
>  content   게시물 내용
>
>  #####  uploadFiles   게시물에 첨부할 파일, 여러개의 파일을 첨부할 수 있으며 각각의 파일은 크기가 1MB 이하, 첨부할 모든 파일의 크기는 5MB 이하여야 함
>
>  #####  lessonType => 개인과외면 PERSONAL, 그룹과외면 GROUP
>
>  #####  maxStudentCount => 개인과외일 경우 입력하지 마(사실 입력해도 에러는 안나게 처리했는데 그래도 그냥 프론트단에서 개인과외일 경우 선택지를 아예 없애버리면 더 이쁠 거 같아) 
>
>  #####  startPeriod =>    형식 XXXX-XX-XX ,마찬가지로 개인과외일 경우 입력 X
>
>  #####  endPeriod =>    형식 XXXX-XX-XX  ,마찬가지로 개인과외일 경우 입력 X

<br/>

=========================================================================

<br/>

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
> 	teacherId =>  선생님 id(식별값)
> 	
> 	name =>   선생님 이름
>
> ##### }    
>
> 
>
> #####  uploadFiles{
>
> 	filePath =  업로드한 파일의 경로
>
> ##### }
>

<br/>

=========================================================================

<br/>

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
> 	id                        과외 id
> 	
> 	title
> 	
> 	views          -> 조회수
> 	
> 	 teacherName => 작성자 이름
> 	
> 	createdDate => 작성일
> 	
> 	isCompleted    => 모집완료된 강의인지 여부(모집완료됐으면 true)
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
> 
>
> 

=========================================================================

<br/>

### 과외 수정 

### [<span style="color:pink">/lesson</span>/{lessonId}] [<span style="color:blue">PUT</span>]   [<span style="color:skyblue">multipart/form-data</span>]

#### 블랙리스트이면 불가능 =>예외처리 해줘!

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
>
> 
>
> ##### fileRemove => 파일을 지울거면 TRUE

<br/>

### 회원정보 수정과 동일하게, fileRemove 가 true일때 보내는 사진이 없으면 올려둔 파일 모두 삭제

=========================================================================

<br/>

<br/>

### 과외 삭제

### [<span style="color:pink">/lesson</span>/{lessonId}] [<span style="color:blue">DELETE</span>]  [<span style="color:skyblue">multipart/form-data]</span>

##### 보내줄 데이터

> password

<br/>

=========================================================================

<br/>

<br/>

### 과외 가입신청

### [<span style="color:pink">/lesson</span>/{lessonId}/apply] [<span style="color:blue">POST</span>]  

##### 보내줄 데이터 없음

##### 결과 데이터도 없음

##### 과외를 신청하면 해당 과외를 등록한 사람한테 알림이 가도록 구현함!

#### 모집 완료된 강의는 신청 못 하게 프론트에서 걸러줘!!!(백에서도 거르긴 했는데, 그건 진짜 답도없는 에러 막을라고 거른거! 앞에서 걸러주면 편함!)

<br/>

<br/>

### 과외 가입취소

### [<span style="color:pink">/lesson</span>/{lessonId}/cancel] [<span style="color:blue">POST</span>]  

##### 보내줄 데이터 없음

##### 결과 데이터도 없음

<br/>

#### 모집 완료된 강의는 가입취소 못 하게 막아줘!!!

<br/>

<br/>

<br/>



### 과외 가입 신청 수락

### [<span style="color:pink">/lesson/{lessonId}/accept/{alarmId}</span>] [<span style="color:blue">POST</span>]  

##### 보내줄 데이터 없음

##### 결과 데이터도 없음

##### 과외를 신청하면 해당 과외를 등록한 사람한테 알림이 가도록 구현함!

#### 모집 완료된 강의는 더이상 수락 못 받게 걸러줘!

<br/>

<br/>

### 과외 가입 신청 거절

### [<span style="color:pink">/lesson/{lessonId}/cancel/{alarmId}</span>] [<span style="color:blue">POST</span>]  

##### 보내줄 데이터 없음

##### 결과 데이터도 없음

<br/>

<br/>

### 과외 모집 완료(인원이 다 안 찼을 경우에, 선생님이 임의로 모집완료 버튼을 누른 경우)

#### (나머지 경우, 알아서 모집인원이 다 차면 모집완료 알림을 보내줌)

### [<span style="color:pink">/lesson/{lessonId}/complete</span>] [<span style="color:blue">POST</span>]  

##### 보내줄 데이터 없음

##### 결과 데이터도 없음

=========================================================================

<br/>

<br/>

<br/>





# 알람

#### 알람에서 신청한 학생과 강의에 대한 정보를 눌렀을 때  없어졌다고 뜨면, 아마 삭제되서 그럴거임!!

#### 적당한 예외페이지 만들자ㅣㅇ



### 나의 알람 목록 조회

### [<span style="color:pink">/myInfo/myAlarms</span>] [<span style="color:blue">GET</span>]  

##### 조회조건 :   readType :  [READ,  NOT_READ,  ALL] (읽은것만, 안읽은것만, 모두다)

##### 기본 사이즈 20, 기본 페이지 0(<span style="color:red">무조건 첫번째 페이지는 1이 아니라 0이다</span>)

#### 요청 => readType=위 3개 조건중 하나

##### 결과 데이터

> 
>
> totalPageNum 총 페이지 수
> currentPageNum 현재 페이지 번호
> totalElementCount 전체 알람 개수
> currentPageElementCount  현재 페이지 알람 개수
>
> 
>
> #### simpleAlarmList(목록 -> 여러개){
>
> 		id		=>  알람 id
> 	
> 		alarmType  	=>  알람타입 (SEND_APPLY, APPROVED, REFUSED, COMPLETION)
> 	
> 		isRead	=>  읽은 알람이면 ture,아니면 false
> 	
> 		createdDate	=>  알람이 온 날짜
>
> 
>
> ##### 				lessonId  => 강의 id
>
> ##### 		<span style="color:red">		title      => 강의 제목</span>
>
> ##### 				teacherName  => 선생님 이름
>
> #### 		
>
> ##### 				applicantStudentId=> 강의를 신청한 학생 id
>
> ##### 				<span style="color:red">applicantStudentName=> 강의를 신청한 학생 이름</span>
>
> #### 	}

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

<br/>

<br/>

### 알람 1개 읽기

### [<span style="color:pink">/myInfo/myAlarms/{alarmId}</span>] [<span style="color:blue">GET</span>]  

##### 결과 데이터

> ##### 	
>
> ##### id		=>  알람 id
>
> ##### alarmType  	=>  알람타입 (SEND_APPLY, APPROVED, REFUSED, COMPLETION)
>
> ##### createdDate	=>  알람이 온 날짜
>
> 
>
> ##### 		lessonId  => 강의 id
>
> ##### 		<span style="color:red">title      => 강의 제목</span>
>
> ##### 		teacherName  => 선생님 이름
>
> #### 	
>
> ##### 		applicantStudentId => 신청한 학생 id
>
> ##### 		<span style="color:red">applicantStudentName=> 신청한 학생 이름</span>
>
> #### 	
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

<br/>

<br/>







# 평가

<br/>

## 선생님은 선생님 평가 불가능!!!!

<br/>

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
> 	id    => 선생님 id
> 	
> 	name   => 선생님 이름
> 	
> 	age  
> 	
> 	profileImgPath    =>선생님 프사 url
> 	
> 	career
> 	
> 	starPoint
>
> #### }

<br/>

<br/>

### 선생님 평가하기

### [<span style="color:pink">/myInfo/evaluation/teachers/{teacherId}</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">JSON</span>]



#### 보내줘야 할 데이터

> content - 평가내용
>
> ##### starPoint - 별점 (1~5점 사이, 소수점 1자리까지 허용(두자리 못오게 막아줘!))

<br/>

<br/>













# 신고

### 선생님 신고하기

##### (신고할 때는 아무 조건 없이 신고 가능하나 선생님은 신고하지 못한다. 즉 오직 학생만 신고 가능!)

#### [<span style="color:pink">/reports/teachers/{teacherId}</span>] [<span style="color:blue">POST</span>]  [<span style="color:skyblue">multipart/form-data</span>]

##### 전달해야 할 데이터

> content => 신고내용









### 신고 목록 보기

### [<span style="color:pink">/reports</span>] [<span style="color:blue">GET</span>]  

#### (어드민만 가능!)

#### 결과 데이터

> 
>
> ##### totalReportCount  =>  신고의 개수
>
> 
>
> ####  reportDtos(신고 정보) {
>
> 	 reportId   신고 id(식별값)
> 	
> 	 content     신고내용
> 	
> 	createDate 신고날짜
>
> ​	createDate 신고날짜
>
> 
> 	writerId     신고자 id  
> 	
> 	 writerName   신고자 이름,  
> 	
> 	 writerUsername   신고자 username
>
> 
>
> 	 targetTeacherId    신고당한 선생님 id
> 	
> 	 targetTeacherName  신고당한 선생님 이름
> 	
> 	 targetTeacherUsername   신고당한 선생님 username
>
> 
>
> 	 isSolved     해결된 신고인가? 
>
> 
>
> 	 solverAdminId    해결한 관리자의 id
> 	
> 	solverAdminName   해결한 관리자의 이름
>
> #### }





### 신고 내용 보기(1개)

### [<span style="color:pink">/reports/{reportId}</span>] [<span style="color:blue">GET</span>]  

#### (어드민만 가능!)

#### 결과 데이터

> 	 	 reportId   신고 id(식별값)
> 	 	
> 	 	 content     신고내용
> 	 	
> 	 	createDate 신고날짜
> 	 	
> 	 	writerId     신고자 id  
> 	 	
> 	 	 writerName   신고자 이름,  
> 	 	
> 	 	 writerUsername   신고자 아이디
>
> 	 
>
> 	 	 targetTeacherId    신고당한 선생님 id
> 	 	
> 	 	 targetTeacherName  신고당한 선생님 이름
> 	 	
> 	 	 targetTeacherUsername   신고당한 선생님 username
>
> 	 
>
> 	 	 isSolved     해결된 신고인가? true, false
> 	 	
> 	 	 solverAdminId    해결한 관리자의 id
> 	 	
> 	 	solverAdminName   해결한 관리자의 이름





### 신고내용 처리 

### (신고 내용으로 들어갔을때 처리하는 버튼 3개를 만들어서, 처리하는 그런 느낌!)

### 1. 무시하기

### 2. 경고 주기(3회 중첩 시 자동으로 블랙리스트)

### 3. 바로 블랙리스트에 추가(3회 경고를 안받았지만 죄질이 너무 악하여 바로 블랙리스트 쳐넣기)





### 1 신고 무시하기

### [<span style="color:pink">/reports/{reportsId}/ignore</span>] [<span style="color:blue">POST</span>]  

#### (어드민만 가능!)





### 2 경고 주기

### [<span style="color:pink">/reports/{reportsId}/warn</span>] [<span style="color:blue">POST</span>]  

#### (어드민만 가능!)





### 3 블랙리스트 만들기

### [<span style="color:pink">/reports/{reportsId}/black</span>] [<span style="color:blue">POST</span>]  

#### (어드민만 가능!)





### 추가 - 블랙리스트에서 원상복구시키기

### [<span style="color:pink">/reports/white/{teacherId}</span>] [<span style="color:blue">POST</span>]  

#### (어드민만 가능!)





### 블랙리스트 목록 조회

### [<span style="color:pink">/reports/blacklist</span>] [<span style="color:blue">GET</span>]  

#### (어드민만 가능!)

#### 결과 데이터

> 리스트의 형태로
>
> id    => 블랙리스트 id
>
> username   => 블랙리스트 username
>
> name   => 블랙리스트 이름











# 채팅

### 선생님의 정보를 검색 -> 채팅하기 버튼을 누를 경우

### 혹은 내 채팅 목록-> 채팅방으로 입장한 경우

### [<span style="color:pink">/chat/list/{receiverId}</span>] [<span style="color:blue">GET</span>]  

#### 이곳에서 receiverId에 선생님의 id값을 입력 -> 채팅 시작 or 이미 한 채팅이 있으면 그 채팅 목록을 보여줌

#### 반환 데이터

##### 하나의 메세지당 아래 형식으로, 메세지를 주고받은 개수만큼 list로 나옴!, 만약 채팅을 한 적이 없다면 아무것도 안나옴

> id      => 메세지의 id
> studentName  => 학생의 이름
> studentId       => 학생의 아이디
> teacherName    -> 선생님의 이름
> teacherId           -> 선생님의 id
> writeTime        -> 메세지 쓴 시간
> content             -> 메세지 내용
>
> isStudentRead -> 학생이 읽었는지 여부
> isTeacherRead ->  선생님이 읽었는지 여부

##### 내가 학생인지 선생인지는 알고있으니까, 내가 학생이라면 선생님의 데이터를 반대로 두고, 선생님이 읽은게 false라면 안읽음으로 처리 !!

### 메세지 전송하기

### [<span style="color:pink">/chat/insert</span>] [<span style="color:blue">POST</span>]   [ JSON ]

##### => 그럼 내가 알아서  채팅 목록 가져와서  이 위에 채팅 목록 불러와서 너한테 줄거임

#### 반환 데이터

### 메세지 전송하기

### [<span style="color:pink">/chat/insert</span>] [<span style="color:blue">POST</span>]   [ JSON ]

##### 줄 데이터

> content => 입력할 내용
> receiverId   => 상대 id


##### => 그럼 내가 알아서  채팅 목록 가져와서  이 위에 채팅 목록 불러와서 너한테 줄거임

#### 반환 데이터

### 하나의 메세지당 아래 형식으로, 메세지를 주고받은 개수만큼 list로 나옴!

##### , 만약 채팅을 한 적이 없다면 아무것도 안나옴 

##### => 바로바로 채팅창 업데이트

> id      => 메세지의 id
> studentName  => 학생의 이름
> studentId       => 학생의 아이디
> teacherName    -> 선생님의 이름
> teacherId           -> 선생님의 id
> writeTime        -> 메세지 쓴 시간
> content             -> 메세지 내용





### 나의 채팅방 목록 보기

### [<span style="color:pink">/chat/list</span>] [<span style="color:blue"> GET</span>]  

##### 전송할 데이터 없음

##### 결과 데이터 => 내가 선생인 경우

##### 아래 데이터가 list로 여러개 보내짐()

> studentId    => 대상 학생 id
> studentName
> noReadMessage : 안 읽은 메세지 수
>
> #### messageList{
>
> 	content : 내용
>
> #### }

##### 결과 데이터 => 내가 학생

##### 아래 데이터가 list로 여러개 보내짐()

> 
>
> teacherId => 대상 선생 id
> teacherName
> noReadMessage : 안 읽은 메세지 수
>
> #### messageList{
>
> 	content : 내용
>
> #### }



### 나의 채팅방 목록 보기

### [<span style="color:pink">/chat/list</span>] [<span style="color:blue"> GET</span>]  

##### 전송할 데이터 없음

##### 결과 데이터 => 내가 선생인 경우

##### 아래 데이터가 list로 여러개 보내짐()

> studentId    => 대상 학생 id
> studentName
> noReadMessage : 안 읽은 메세지 수
>
> #### messageList{
>
> ​	content : 내용
>
> #### }

##### 결과 데이터 => 내가 학생

##### 아래 데이터가 list로 여러개 보내짐()

> 
>
> teacherId => 대상 선생 id
> teacherName
> noReadMessage : 안 읽은 메세지 수
>
> #### messageList{
>
> ​	content : 내용
>
> #### }







# 조건

- ##### 회원가입/로그인 (완료)

  - ##### 회원 분류는 선생님, 학생, 관리자가 있습니다.(관리자 미구현)

  - ##### 선생님은 경력을 입력할 수 있습니다.(구현)

    - ex) OO대학교 졸업, 과외 경력 O년, OO대회 수상 등

  - ##### [추가] 소셜 로그인 : 카카오, 구글, 깃허브 등 계정으로 소셜로그인을 할 수 있습니다.(카카오만 구현)



- ##### 회원 정보 수정/탈퇴 (완료)



- 선생님은 과외 학생 모집 게시물을 등록/수정/삭제할 수 있습니다.

  - ##### 과외는 1대1 과외, 그룹 과외가 있습니다.(구현)

  - ##### 그룹과외 모집 게시물을 등록할 경우 모집 인원, 과외 기간 등을 설정할 수 있습니다.(구현)

  - ##### 제목, 선생님, 모집 인원 등으로 게시물을 검색할 수 있습니다.(구현)





- ##### 선생님 목록 보기 및 검색 ⇒ 별점이 높은순, 학생들이 많이 수강한 순 등으로 볼 수 있고 검색도 가능합니다.(구현)

  - [추가] 선생님과 채팅기능 : 선생님 목록을 보다가 과외를 받고싶은 선생님이 있으면 채팅을 할 수 있습니다.





- ##### 모집 완료 알림 기능(완료)

  - ##### 학생 모집이 완료되었으면 선생님 및 신청 학생들에게 알려줍니다.(구현)



- ##### 선생님의 수업에 참가하는 학생은 선생님에 대한 평가 작성 및 별점을 줄 수 있으며 신고하기 기능도 있습니다. (원래는 과외 기간이 끝난 후 가능해야하지만, 기능 확인을 위해 참여하기만 하면 평가 가능하도록 구현해주시면 됩니다.)  (완료)



- ##### 관리자 페이지 : *일정 건수 이상의 신고를 받은 선생님은 관리자가 검토 후 블랙리스트에 등록할 수 있습니다. 블랙리스트에 등록된 선생님은 학생 모집 게시물을 올릴 수 없습니다.(완료)

  - *신고 건수 기준은 자유롭게 정하시면 됩니다.

 