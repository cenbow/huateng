module.exports = {
  init: function() {

    require("common/softKeyboard").keyBoardIdSet("dfbKey");
    
    require("common/softKeyboard").formIdSet("");

    function clickKeyboard(number){
         require("common/softKeyboard").clickKeyboard(number);
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

    $("#verifyCode").focus(function() {
      $("#verifyCodeMsg").html("");
      $(this).val("");
    });

    $("#submitButtn").click(function() {
      require("common/softKeyboard").closeKeyboard_uncheck();
      var vcode = $("#vcode").val();
      var verifyCode = $("#verifyCode").val();
      var newPasswd=$("#newPasswd").val();
      
      var confirmPasswd=$("#confirmPasswd").val();

      checkVcode();
      if($("#imgCodeFlag").val()=='false'){
         $("#codeFlag").attr("src", "/assets/sutongpay/er.png");
            $("#codeFlagSpan").attr("style", "display:show;margin-left:-17px;");
            $("#imgCodeFlag").val("false");
            return;
      }
      if (verifyCode == null || verifyCode == "") {
        $("#verifyCodeMsg").html("请输入短信验证码");
        $("#comPwdMsg").html("");
        $("#newPwdMsg").html("");
        $("#oldPwdMsg").html("");
        $("#errAnswer").html("");
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

      var isVerifyCode = 'N';
      if ($("#submitButtn").hasClass('clickedClass')) {
        return;
      }
      $("#submitButtn").addClass('clickedClass');
      $.ajax({
        url: '/api/passWordMgn/matchCode',
        type: 'POST',
        async: false,
        data: {
          verifyCode: verifyCode
        },
        success: function(data) {
          $("#submitButtn").removeClass('clickedClass');
          if (data.data.status == 'suc') {
            $("#verifyCodeMsg").html("");
            $("#comPwdMsg").html("");
            $("#newPwdMsg").html("");
            isVerifyCode = 'Y';
          }else{
             $("#verifyCodeMsg").html(data.data.msg);
          $("#comPwdMsg").html("");
          $("#newPwdMsg").html("");
          isVerifyCode = 'N'
          }
        },
        error: function(data) {
          flushCode();
          $("#submitButtn").removeClass('clickedClass');
          $("#verifyCodeMsg").html("短信验证码校验失败");
          $("#comPwdMsg").html("");
          $("#newPwdMsg").html("");
          isVerifyCode = 'N'
        }
      });
      if (isVerifyCode == 'N') {
        return;
      }


             var newPwd = newPasswd;
 
        var newPwd2 = confirmPasswd;
      $("#submitButtn").addClass('clickedClass');
      //校验新密码和重复密码
      $.ajax({
        url: "/api/security/confirmPassword",
        type: "POST",
        data: {
          newPayPwd: newPwd,
          newPayPwd2: newPwd2,
          pwdKey: "keyNewPassword",
          pwd2Key: "keyNewPassword2"
        },
        success: function(data) {
          if (data.toString() == "success") {
            var verifyCode = $("#verifyCode").val();
            $.ajax({
              url: "/api/security/resetPayPwdBySms",
              type: "POST",
              data: {
                newPwd: newPwd,
                checkCode: vcode
              },
              success: function(data) {
                $("#submitButtn").removeClass('clickedClass');
                if (data.data.status == 'success') {
                  location.href = "/safeCenter/paypwd/reset/ok";
                  return;
                }else{
                  return require("common/extras/popup").alert("", "error", data.data.msg, 5);
                }
              },
              error: function(data) {
                flushCode();
                $("#submitButtn").removeClass('clickedClass');
                return require("common/extras/popup").alert("", "error", "支付密码重置异常", 5);
              }
            });
          } else {
            flushCode();
            $("#submitButtn").removeClass('clickedClass');

            $("#confirmPasswd").focus();
            $("#comPwdMsg").html("两次输入的密码不一致");
            $("#newPwdMsg").html("");
            $("#verifyCodeMsg").html("");
            return;
          }
        },
        error: function() {
          flushCode();
          $("#submitButtn").removeClass('clickedClass');
          return require("common/extras/popup").alert("", "error", "支付密码验证异常。", 5);
      
        }
      });
    });
    $("#sendCode").click(function(event) {
      require("common/softKeyboard").closeKeyboard_uncheck();
      require("common/send_sms").getParam("sendCode", "/api/mobileclient/testsend", $("#phoneNo").val(), "logined", "", "flag", "notice");

      require("common/send_sms").sendClickFun();
    });
  }
};