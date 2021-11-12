$(document).ready(function(){
      // for notification modal
  $("#notification_icon").click(function () {
    $(".notification_modal").css("display", "block");
    $(".user_modal").css("display", "none");
    $(".message_modal").css("display", "none");
  });
  $(".notification_modal_close_area").click(function () {
    $(".notification_modal").css("display", "none");
  });

  // for user modal
  $("#user_icon").click(function (e) {
    $(".user_modal").css("display", "block");
    $(".notification_modal").css("display", "none");
    $(".message_modal").css("display", "none");
  });

  $(".user_modal_close_area").click(function () {
    $(".user_modal").css("display", "none");
  });

  // for user modal
  $("#message_icon").click(function (e) {
    $(".message_modal").css("display", "block");
    $(".notification_modal").css("display", "none");
    $(".user_modal").css("display", "none");
  });

  $(".message_modal_close_area").click(function () {
    $(".message_modal").css("display", "none");
  });

  $(".chatting_modal_close_area").click(function () {
    $(".chatting_modal").css("display", "none");
  });
});