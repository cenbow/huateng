module.exports = {
  init: function() {

    require("common/softKeyboard").keyBoardIdSet("dfbKey");
    
    require("common/softKeyboard").formIdSet("rstQuestionForm");

    function clickKeyboard(number){
         require("common/softKeyboard").clickKeyboard(number);
    }

    $("#payPwd").click(function(event) {
      $.ajax({
        type: "POST",
        cache: false,
        url: "/api/softKeyBoard/getRdmPwd",
        dataType: "json",
        success: function(data) {
          if(data.data.status == 'ok'){
            for(var i=0;i<10;i++){
              $("area:eq("+i+")").attr('href','javascript:clickKeyboard('+data.data.pwd.substr(i,1)+')' );
            }
            
      require("common/softKeyboard").closeKeyboard_uncheck();
      require("common/softKeyboard").passwordIdSet('payPwd');
      require("common/softKeyboard").showKeyboard();
          }
        },error:function(){
        }
      })
      
    });




    function get_time() {
      return new Date().getTime();
    }
    $(function() {
      $.ajax({
        type: "GET",
        cache: false,
        url: "/api/getQuestion",
        dataType: "json",
        success: function(data) {
          $.each(data.data, function() {
            $("#newQuestion").append("<option value=" + this.codeValue + ">" + this.codeDesc + "</option>");
          })
        }
      })
    });

    $("#submitButtn").click(function() {
       require("common/softKeyboard").closeKeyboard_uncheck();
      if ($("#rstQuestionForm").parsley("isValid")) {
        if ($("#payPwd").val().length == 0) {
          $("#payPwd").focus();
          $("#pwdMsg").html("支付密码不能为空");
          return;
        }
        if ($("#payPwd").val().length != 6) {
          $("#payPwd").focus();
          $("#pwdMsg").html("请输入6位支付密码");
          return;
        }
        


        $.ajax({
              url: "/api/security/resetQuestionByPayPwd",
              type: "POST",
              data: {
                newQuestion: $("#newQuestion").val(),
                newQuestionAnswer: $("#newQuestionAnswer").val(),
                payPwd: $("#payPwd").val()
              },
              success: function(data) {
                if (data.data.status == 'success') {
                  location.href = "/safeCenter/question/ok";
                  return;
                } else {
                  $("#pwdMsg").html("");
                  $("#msgNote").css("display", "block");
                  $("#pwdMsg").html("密码修改失败，" + data.data.msg);
                  return;
                }
              },
              error: function(data) {
                $("#pwdMsg").html("");
                $("#msgNote").css("display", "block");
                $("#pwdMsg").html("密码修改失败");
                return;
              }
            });
      } else {
        $("#rstQuestionForm").parsley("validate")
      }
    });

$("#resetButtn").click(function() {
      window.location="/safeCenter/home"
    })



  }
};