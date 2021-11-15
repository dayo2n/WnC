
# 제 1회 어드민 X 프로브 WnC 프로그래밍 대회

WnC : Web & Cloud 프로그래밍 대회, 웹 서비스를 개발하고 클라우드☁️에 배포해보자 !

[요구사항 알아보기](https://roan-firewall-154.notion.site/1-x-WnC-d27d58216eb84b39b023a5dcd1bd94fb)

[결과물 구경하러가기](https://rladbrua0207.github.io/WnC/home)



# 🍯 꿀단지

- Frontend
  - 🐝  [김유겸](https://github.com/rladbrua0207)

  - 🐝  [문다연](https://github.com/dayo2n)

- Backend
  - 🐝  [신동훈](https://github.com/Shindonghun1)


# 주의사항

처음에는 화면에 데이터 뜨지않거나 데이터가 불러와지지않거나, 기능이 작동하지 않을 수 있습니다. 약 2~3분정도 기다려도 화면이 로딩되지 않거나 서버 오류 발생🚨🚨🚨🚨🚨 시 ➡️ [다음 페이지](https://cors-anywhere.herokuapp.com/corsdemo) ⬅️를 방문해서 액세스 요청 버튼을 눌러주세요.

접속량이 많으면 서버가 지연될 수 있습니다.

기능 확인을 위한 계정
  - 어드민 계정 (Only) : admin 1234
  - 차단당한 계정 : kyuni kyuni -> 테스트 가능
  - 학생 / 선생님 계정 : 회원가입, 로그인 후 테스트 가능합니다.

# 구현 조건사항 (구현 ☑️, 미구현 ⛔️, 추가구현 ✅)

```

회원가입 / 로그인 ☑️

  - 회원 분류는 선생님, 학생, 관리자가 있습니다. ☑️

  - 선생님은 경력을 입력할 수 있습니다. ex) OO대학교 졸업, 과외 경력 O년, OO대회 수상 등 ☑️

  - ##### [추가] 소셜 로그인 : 카카오, 구글, 깃허브 등 계정으로 소셜로그인을 할 수 있습니다. ⛔️ (백엔드 카카오만 구현, 프론트X) 



회원 정보 수정 / 탈퇴 ☑️

  - 선생님은 과외 학생 모집 게시물을 등록/수정/삭제할 수 있습니다. ☑️

  - 과외는 1대1 과외, 그룹 과외가 있습니다. ☑️

  - 그룹과외 모집 게시물을 등록할 경우 모집 인원, 과외 기간 등을 설정할 수 있습니다. ☑️

  - 제목, 선생님, 모집 인원 등으로 게시물을 검색할 수 있습니다. -> ☑️ 그룹 유형(일대일, 그룹) & 제목 | 선생님 | 내용 조합으로 검색 가능

  - 선생님은 등록한 게시물을 모집이 완료되었다면 모집 완료 상태로 변경할 수 있습니다. ✅



선생님 목록 보기 및 검색 

  ⇒ 별점순(오름차,내림차) ☑️, 선생님 이름 검색 ☑️, 학생들이 많이 수강한 순 ⛔️ 등으로 볼 수 있고 검색도 가능합니다.

  - [추가] 선생님과 채팅기능 : 선생님 목록을 보다가 과외를 받고싶은 선생님이 있으면 채팅을 할 수 있습니다. ⛔️

  - 선생님 / 과외 리스트는 로그인하지 않아도 볼 수 있으나, 상세 조회시 로그인이 필요합니다. ✅



모집 완료 알림 기능 ☑️

  - 학생 모집이 완료되었으면 선생님 및 신청 학생들에게 알려줍니다. ☑️
  
  - 학생이 선생님에게 가입 신청을 보내면 선생님의 알림창에 뜨고, 수락/거절을 할 수 있습니다. 선생님의 수락/거절에 대한 결과가 학생의 알림창에 띄워집니다. ✅
  
  - 아직 읽음처리하지않은 알림은 읽지 않음으로 표시됩니다. 알림을 보고 ✔️ 버튼을 눌러 읽음처리할 수 있습니다. ✅
  
  - 모집 완료되면 모집 상태가 완료로 자동으로 변경됩니다.  ✅



선생님의 수업에 참가하는 학생은 선생님에 대한 평가 작성 및 별점을 줄 수 있으며 신고하기 기능도 있습니다. ☑️
(원래는 과외 기간이 끝난 후 가능해야하지만, 기능 확인을 위해 참여하기만 하면 평가 가능하도록 구현해주시면 됩니다.) 
  
  - 선생님 / 학생은 로그인된 유저정보를 확인할 수 있습니다.✅
      
      - 학생 : 현재 수강 중인 강의 리스트, 강의 평가, 수락 대기 중인 강의 목록
      - 선생님 : 현재 강의 중인 목록, 학생들에게 받은 강의 평가 결과



관리자 페이지 ☑️ 
  로그인 페이지에서 헤더의 관리자 아이콘을 눌러 관리자 로그인 페이지로 이동합니다.
  일정 건수 이상의 신고를 받은 선생님은 관리자가 검토 후 블랙리스트에 등록할 수 있습니다. 
  블랙리스트에 등록된 선생님은 학생 모집 게시물을 올릴 수 없습니다. ☑️

  - 신고 건수 기준은 자유롭게 정하시면 됩니다. ☑️ -> 신고 건수는 3건으로 지정했습니다. 블랙리스트에 등록되면 서비스를 아예 이용할 수 없습니다.
   
  - 학생은 선생님을 신고할 수 있습니다. (신고사유와 함께) 신고가 3건 이상 접수된 선생님은 자동으로 차단되어 블랙리스트에 등록됩니다. ✅

  - 관리자는 관리자 페이지에서 접수된 신고 목록을 보고 아직 처리하지않은 신고의 경우 다음 세가지 방법으로 처리할 수 있습니다. (무시, 경고, 차단) ✅
 
    무시 : 별다른 조치없이 신고가 처리됨. 경고 : 경고 횟수가 누적됩니다. 차단 : 해당 회원은 차단되어 서비스를 이용할 수 없습니다. 


```


# 📸 Screenshot

메인 홈화면

	- 로그인 상태라면 로그인 버튼 없이 EXIT 버튼 Only
  
  <img width="700" alt="home" src="https://user-images.githubusercontent.com/57654681/141720774-58cda932-8247-4bd0-869c-5131fe02b74b.png">

회원가입 화면

	- 모든 공란 입력 필수

![join](https://user-images.githubusercontent.com/57654681/141724057-6392ddec-c9ec-4240-a7b8-a344c4deb854.png){: width="600px"}

로그인 화면

	- 회원가입 필수
  
<img width="700" alt="login" src="https://user-images.githubusercontent.com/57654681/141721999-17cad069-9a1a-4cf2-ac7e-2a3820062850.png">

이하 항목은 회원 로그인이 없으면 제대로 이용할 수 없는 기능입니다.

그룹 과외 게시물 등록

	- 파일 첨부 필수X
  
	- 나머지 공란 필수 기입
  
<img width="600" alt="newEditorGroup" src="https://user-images.githubusercontent.com/57654681/141722009-e2788c3a-7494-4e07-ab47-b0ee7979ecb3.png">

과외 게시물 상세조회

	- 학생은 아래 신청하기 버튼으로 신청가능
  
	- 게시자는 글 수정버튼으로 게시글 수정 가능, 로그인 유저와 게시자 아이디가 다르면 불가
  
	- 게시자는 글 삭제 가능, 로그인 유저와 게시자의 아이디가 다르면 불가, 확인 비밀번호 입력시 틀리면 삭제 불가
  
	- 과외 가입자가 모집인원을 충족하면 자동 모집완료로 바뀌며 신청하기가 비활성화
  
<img width="700" alt="viewPost" src="https://user-images.githubusercontent.com/57654681/141722022-e1e60060-c37f-4708-bf26-eb53c41b9c90.png">

알람기능
	- 학생이 과외 가입 신청시 해당 선생님에게 알람 전송
  
	- 선생님이 가입 수락시 가입과 동시에 학생에게 알람 전송(거절 시 거절 알람 전송)
  
	- 선생님의 과외의 모집인원이 충족되어 완료되면 모집 완료 알림을 해당 선생님과 수강 학생에게 모두 전송
  
	- 읽은 알림/안읽은 알림/전체 구분하여 확인 가능 (안읽은 알림에서 ✔️표시를 눌러 읽음표시로 구분)
  
<img width="700" alt="teacherNotification" src="https://user-images.githubusercontent.com/57654681/141722100-51261703-4cd6-4b7b-89a5-a6f831163c06.png">
<img width="700" alt="studentNotification" src="https://user-images.githubusercontent.com/57654681/141722103-9eb4f194-90fa-4664-bdbc-a560634c7e10.png">
<img width="300" alt="no-readNotication" src="https://user-images.githubusercontent.com/57654681/141722229-4a1905b7-a6ff-4916-a34e-8d7df3d27dcc.png">
<img width="300" alt="readNotification" src="https://user-images.githubusercontent.com/57654681/141722232-0a890926-9e30-4e5e-83f6-8afad61707e2.png">

평가 기능 & 유저페이지

	- 상단의 유저 아이콘을 눌러 회원정보 창으로 이동 가능
	
	- 간단한 회원정보와 함께 수강 목록, 평가 창이 있어 수강 중인 과외 선생님에 대한 평가 가능
	
	- 학생이 평가하면 학생들의 평가의 평균으로 홈 화면에서도 선생님에 대한 별점이 업데이트됨
	
	- 회원 정보 수정 가능
	
<img width="700" alt="studentUserInfo" src="https://user-images.githubusercontent.com/57654681/141731527-35785948-e16d-44df-b3b2-99c54aa52c4b.png">

<img width="700" alt="evaluate" src="https://user-images.githubusercontent.com/57654681/141731514-2a2d7add-0f83-4d18-ba34-867debb45b8f.png">

<img width="700" alt="addOnEvaluateList" src="https://user-images.githubusercontent.com/57654681/141731502-f81fc9b6-05ae-4619-9d6c-17ea102d6349.png">

<img width="300" alt="settingStarPoint" src="https://user-images.githubusercontent.com/57654681/141731609-a6b9a1e6-99e5-478d-b641-5cd0fae5d187.png">
	

선생님 신고

	- 메인 홈화면의 선생님 리스트나 선생님을 검색하여 신고 가능
  
	- 신고가 3번 이상 접수된 선생님은 자동 블랙리스트에 등록
  
	- 블랙리스트 등재 시 서비스 이용 일절 불가
  
<img width="700" alt="report" src="https://user-images.githubusercontent.com/57654681/141722495-bc3ff0d6-a480-47dc-b370-b52149c4da1a.png">


어드민 로그인

	- 기본 로그인 화면에서 상단 배너의 관리자 버튼을 눌러 관리자 로그인 페이지로 이동
  
	- id: admin
  
	- pw: 1234
  
<img width="700" alt="loginAdmin" src="https://user-images.githubusercontent.com/57654681/141722512-e947639a-a835-4897-86c9-a3d36628a71f.png">

관리자 페이지 (신고접수 목록, 블랙리스트)

	- 다음 조치로 처리 가능 : 
  
		- 무시 : 별다른 조치없이 신고가 처리됨.
    
		- 경고 : 경고 횟수가 누적됩니다.
    
		- 차단 : 해당 회원은 차단되어 서비스를 이용할 수 없습니다. 
    
	- 처리가 완료된 신고는 처리 완료, 아직 접수상태인 신고는 처리할 수 있도록 함
  
<img width="700" alt="reportAdmin" src="https://user-images.githubusercontent.com/57654681/141722805-5726178b-bad0-4b20-ade0-fc3c17af019d.png">

<img width="700" alt="repotAdmin2" src="https://user-images.githubusercontent.com/57654681/141722809-42eac7e7-2214-4274-b365-d2c903a92b9c.png">

<img width="700" alt="reportBlack" src="https://user-images.githubusercontent.com/57654681/141726980-b33cdb9d-60f9-4d6a-992f-6b461d6cc670.png">
