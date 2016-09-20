module.exports = {
  init: function() {
 require("common/extras/checkLogin").checkLogin();
$.ajax({
      type: "POST",
      cache: false,
      url: "/api/security/securityCenterGrade",
      success: function(data) {
        if(data.data.status == 'success'){
          if(data.data.msg=="1"){
          	$("#scGrade").css('width','284');
          	$("#scGradeT").html("");
          	$("#scGradeT").text("中");

          }else if(data.data.msg=="2"){
          	$("#scGrade").css('width','214');
          	$("#scGradeT").html("");
          	$("#scGradeT").text("低");
          }
          
        }
        $("#sc").css('display','block');
          $("#sc2").css('display','block');
      },error:function(){
      	$("#sc2").css('display','block');
      }
    })
}
}