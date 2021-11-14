$(document).ready(function(){

    const IMG_URL = "https://wnc-bucket.s3.ap-northeast-2.amazonaws.com/";

    fetch("http://219.255.114.140:8090/reports",{
        method: "GET",
        header : {"Authorization" : `Bearer ${JSON.parse(localStorage.getItem("token"))}` }
    })
    .then(response => {
        return response.json();
      })
    .then(data => {
        console.log(data);


//     reportId   신고 id(식별값)
//  ​	 content     신고내용
//   	writerId     신고자 id  
//  ​	 writerName   신고자 이름,  
//  ​	 writerUsername   신고자 username
//  ​	 targetTeacherId    신고당한 선생님 id
//   ​	 targetTeacherName  신고당한 선생님 이름
//  ​	 targetTeacherUsername   신고당한 선생님 username
//  ​	 isSolved     해결된 신고인가? 
//  ​	 solverAdminId    해결한 관리자의 id
// ​	 solverAdminName   해결한 관리자의 이름

{/* <i class="fas fa-check"></i> */}
        for(i=0; i<parseInt(data.totalRepotCount); i++){
            var reportID = data.reportDtos.reportID;
            var content = data.reportDtos.content;
            var writerId = data.reportDtos.writerId;
            var writerName = data.reportDtos.writerName;
            var writerUsername = data.reportDtos.writerUsername;
            var targetTeacherId = data.reportDtos.targetTeacherId;
            var targetTeacherName = data.reportDtos.targetTeacherName;
            var targetTeacherUsername = data.reportDtos.targetTeacherUsername;
            var isSolved = data.reportDtos.isSolved;
            var solverAdminId = data.reportDtos.solverAdminId;
            var solverAdminName = data.reportDtos.solverAdminName;
        }
      })
});