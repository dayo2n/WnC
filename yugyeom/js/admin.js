
$(document).ready(function () {
  const IMG_URL = "https://wnc-bucket.s3.ap-northeast-2.amazonaws.com/";

  var adminFetch = function () {
    $("#reportList>tbody").empty();

    fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/reports", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
      },
    })
      .then((response) => {
        if (response.status < 200 || response.status >= 300) {
          response.json().then(err => alert(err.message));
          location.href=`${URL_ROUTE}login.html`;
        } else {
          console.log(2, response);
          return response.json();
        }
      })
      .then((data) => {
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

        for (i = 0; i < parseInt(data.totalReportCount); i++) {
          console.log(i);
          var reportID = data.reportDtos[i].reportId;
          var content = data.reportDtos[i].content;
          var writerId = data.reportDtos[i].writerId;
          var writerName = data.reportDtos[i].writerName;
          var writerUsername = data.reportDtos[i].writerUsername;
          var targetTeacherId = data.reportDtos[i].targetTeacherId;
          var targetTeacherName = data.reportDtos[i].targetTeacherName;
          var targetTeacherUsername = data.reportDtos[i].targetTeacherUsername;
          var isSolved = data.reportDtos[i].solved;
          var solverAdminId = data.reportDtos[i].solverAdminId;
          var solverAdminName = data.reportDtos[i].solverAdminName;

          console.log("solve" + isSolved);
          if (isSolved) {
            $("#reportList>tbody").prepend(
              "<tr><td>" +
                reportID +
                "</td><td>" +
                targetTeacherId +
                " : " +
                targetTeacherUsername +
                "</td><td>" +
                writerId +
                " : " +
                writerUsername +
                "</td><td>" +
                content +
                '</td> <td><i class="fas fa-check" style="color: green;"></i></td><td>' +
                solverAdminId +
                " : " +
                solverAdminName +
                "</td></tr>"
            );
          } else {
            // 아직 해결되지않은 신고이면
            $("#reportList>tbody").prepend(
              "<tr><td>" +
                reportID +
                "</td><td>" +
                targetTeacherId +
                " : " +
                targetTeacherUsername +
                "</td><td>" +
                writerId +
                " : " +
                writerUsername +
                "</td><td>" +
                content +
                '</td> <td><select name="" id="blackOption"><option value="ignore" id="ignore">무시</option><option value="warn" id="warn">경고</option><option value="black" id="black">차단</option></select></td><td><input type="button" value="처리하기" id="btn-confirm"></td></tr>'
            );
          }
        }

        $("#btn-confirm").click(function () {
          if ($("#blackOption option:selected").attr("id") === "ignore") {
            fetch(
              "https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/reports/" + reportID + "/ignore",
              {
                method: "POST",
                headers: {
                  Authorization: `Bearer ${JSON.parse(
                    localStorage.getItem("token")
                  )}`,
                },
              }
            ).then((response) => {
              console.log(response);
              if (response.status === 200) {
                blackFetch();
                adminFetch();
              } else if (response.status === 404) {
                alert("이미 차단된 회원입니다.");
              }
            });
          } else if ($("#blackOption option:selected").attr("id") === "warn") {
            fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/reports/" + reportID + "/warn", {
              method: "POST",
              headers: {
                Authorization: `Bearer ${JSON.parse(
                  localStorage.getItem("token")
                )}`,
              },
            }).then((response) => {
              console.log(response);
              if (response.status === 200) {
                blackFetch();
                adminFetch();
              } else if (response.status === 404) {
                alert("이미 차단된 회원입니다.");
              }
            });
          } else if ($("#blackOption option:selected").attr("id") === "black") {
            fetch(
              "https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/reports/" + reportID + "/black",
              {
                method: "POST",
                headers: {
                  Authorization: `Bearer ${JSON.parse(
                    localStorage.getItem("token")
                  )}`,
                },
              }
            ).then((response) => {
              console.log(response);
              if (response.status === 200) {
                blackFetch();
                adminFetch();
              } else if (response.status === 404) {
                alert("이미 차단된 회원입니다.");
              }
            });
          }
        });
      });
  };

  adminFetch();

  var blackFetch = function () {
    $("#blackList>tbody").empty();
    fetch("https://cors-anywhere.herokuapp.com/http://219.255.114.140:8090/reports/blacklist", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${JSON.parse(localStorage.getItem("token"))}`,
      },
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        console.log("black" + data);

        // 리스트의 형태로
        // id    => 블랙리스트 id
        // username   => 블랙리스트 username
        // name   => 블랙리스트 이름
        for (i = 0; i < data.length; i++) {
          console.log(data[i]);
          var id = data[i].id;
          var username = data[i].username;
          var name = data[i].name;

          $("#blackList>tbody").prepend(
            "<tr><td>" +
              id +
              "</td><td>" +
              username +
              "</td><td>" +
              name +
              "</td></tr>"
          );
        }
      });
  };
  blackFetch();
});
