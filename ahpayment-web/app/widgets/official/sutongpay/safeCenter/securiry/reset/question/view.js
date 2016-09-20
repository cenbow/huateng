module.exports = {
  init: function() {

    require("common/softKeyboard").keyBoardIdSet("dfbKey");
    
    require("common/softKeyboard").formIdSet("rstpwdByQForm");


    function clickKeyboard(number){
         require("common/softKeyboard").clickKeyboard(number);
    }




    //获取图片验证码
    function flushCode() {
      var src = "/api/captcha/get?" + get_time();
      $("#codeImg").attr("src",src);
      $("#codeImg").show();
      $("#codeFlagSpan").hide();
      var obj = document.getElementById("vcode");
      obj.value = obj.defaultValue;
      obj.style.color = '#666';
    }
    //验证码输入事件
    $("input[name='vcode']").keyup(function(){
      $("#checkCodMsg").html("");//清除提示
      if($(this).val().length == 4){

        checkVcode();
        
      }else{
        $("#codeFlagSpan").attr("style", "display:none");
        $("#imgCodeFlag").val("false");
      }
    })



    
    flushCode();

    //刷新图片验证码
    $("a[name='flushCode']").click(function() {
      flushCode();
    });
    $("img[name='codeImg']").click(function() {
      flushCode();
    });
      //验证图片验证码
    function checkVcode() {
      $.ajax({
        url: "/api/captcha/match",
        type: "GET",
        cache:false,
        data: {
          actualToken: $("#vcode").val()
        },
        success: function(data) {
          if (data.data) {
            $("#codeFlag").attr("src", "/assets/sutongpay/se.png");
            $("#codeFlagSpan").attr("style", "display:show;margin-left:-17px;");
            $("#imgCodeFlag").val("true");
          } else {
            $("#codeFlag").attr("src", "/assets/sutongpay/er.png");
            $("#codeFlagSpan").attr("style", "display:show;margin-left:-17px;");
            $("#imgCodeFlag").val("false");
          }
        },
        error: function(data) {
          $("#codeFlag").attr("src", "/assets/sutongpay/er.png");
          $("#codeFlagSpan").attr("style", "display:block;margin-left:-17px;");
          $("#imgCodeFlag").val("false");
        }
      });
    }


    $("#newPasswd").click(function(event) {
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
      require("common/softKeyboard").passwordIdSet('newPasswd');
      require("common/softKeyboard").showKeyboard();
          }
        },error:function(){
        }
      })

      
    });

    $("#confirmPasswd").click(function(event) {
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
      require("common/softKeyboard").passwordIdSet('confirmPasswd');
      require("common/softKeyboard").showKeyboard();
          }
        },error:function(){
        }
      })
      
    });

    function get_time() {
      return new Date().getTime();
    }
    $("#verifyCode").keyup(function() {
      $("#verifyCodeMsg").html("");
    });

    $("#newPasswd").keyup(function() {
      $("#newPwdMsg").html("");
    });


    $("#confirmPasswd").keyup(function() {
      $("#comPwdMsg").html("");
    });

    //初始化表单验证
    $("#rstpwdByQForm").parsley();

    $("#submitButtn").click(function() {
      require("common/softKeyboard").closeKeyboard_uncheck();

      var newPasswd=$("#newPasswd").val();
      var confirmPasswd=$("#confirmPasswd").val();
      checkVcode();
      if($("#imgCodeFlag").val()=='false'){
         $("#codeFlag").attr("src", "/assets/sutongpay/er.png");
            $("#codeFlagSpan").attr("style", "display:show;margin-left:-17px;");
            $("#imgCodeFlag").val("false");
            return;
      }

      if (newPasswd.length == 0) {
        $("#newPasswd").focus();
        $("#newPwdMsg").html("新密码不能为空");
        $("#comPwdMsg").html("");
        $("#verifyCodeMsg").html("");
        $("#errAnswer").html("");
        return;
      }
      if (newPasswd.length != 6) {
        $("#newPasswd").focus();
        $("#newPwdMsg").html("请输入6位新密码");
        $("#comPwdMsg").html("");
        $("#verifyCodeMsg").html("");
        $("#errAnswer").html("");
        return;
      }

      if (confirmPasswd.length == 0) {
        $("#confirmPasswd").focus();
        $("#comPwdMsg").html("确认密码不能为空");
        $("#newPwdMsg").html("");
        $("#verifyCodeMsg").html("");
        $("#errAnswer").html("");
        return;
      }

      if (confirmPasswd.length != 6) {
        $("#confirmPasswd").focus();
        $("#comPwdMsg").html("请输入6位确认密码");
        $("#newPwdMsg").html("");
        $("#verifyCodeMsg").html("");
        $("#errAnswer").html("");
        return;
      }


      if ($("#submitButtn").hasClass('clickedClass')) {
        return;
      }
      $("#submitButtn").addClass('clickedClass');


      if ($("#rstpwdByQForm").parsley("isValid")) {

        var newPwd = newPasswd;
        //
 
        var newPwd2 = confirmPasswd;
        $("#submitButtn").addClass('clickedClass');
        //校验新密码和重复密码
        $.ajax({
          url: "/api/security/confirmPassword",
          type: "POST",
          async: false,
          data: {
            newPayPwd: newPwd,
            newPayPwd2: newPwd2,
            pwdKey: "keyNewPassword",
            pwd2Key: "keyNewPassword2"
          },
          success: function(data) {
            if (data.toString() == "success") {
              var question = $("#question").val();
              var answer = $("#answer").val();
              $.ajax({
                url: "/api/security/resetPayPwdByQuestion",
                type: "POST",
                data: {
                  question: question,
                  answer: answer,
                  payPwd: newPwd
                },
                success: function(data) {
                  $("#submitButtn").removeClass('clickedClass');
                  if (data.data.status == "success") {
                    location.href = "/safeCenter/paypwd/reset/ok";
                  } else {
                    var retStr = data.data.msg.split("|");
                    if (retStr[0] == '700005') {
                      $("#errAnswer").html("密码保护答案不正确");
                    } else {
                      require("common/extras/popup").alert("", "error", "密码重置失败:"+retStr[1], 5);
                      $("#errAnswer").html("");
                    }
                    return;
                  }
                },
                error: function(data) {
                  $("#submitButtn").removeClass('clickedClass');
                  require("common/extras/popup").alert("", "error", "密码重置失败", 5);
                  $("#errAnswer").html("");
                  return;
                }
              });
            } else {
              $("#confirmPasswd").focus();
              $("#comPwdMsg").html("两次输入新密码不一致");
              $("#newPwdMsg").html("");
              $("#verifyCodeMsg").html("");
              $("#errAnswer").html("");
              $("#msgNote").css("display", "none");
            }
          },
          error: function() {
            $("#submitButtn").removeClass('clickedClass');
            require("common/extras/popup").alert("", "error", "密码重置异常", 5);
            $("#errAnswer").html("");
            return;
          }
        });
      } else {
        $("#rstpwdByQForm").parsley("validate")
        $("#errAnswer").html("");
      }
    });

    $.ajax({
        type: "GET",
        cache: false,
        url: "/api/getQuestion",
        dataType: "json",
        success: function(data) {
          $.each(data.data, function() {
            $("#question").append("<option value=" + this.codeValue + ">" + this.codeDesc + "</option>");
          })
        },error:function(){

        }
      })

  }
};