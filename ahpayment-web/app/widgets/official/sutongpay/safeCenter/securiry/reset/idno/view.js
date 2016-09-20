module.exports = {
  init: function() {
    $("#resetPwdByIdno").parsley();
    $("#verifyCode").focus(function() {
      $("#verifyCodeMsg").html("");
      $(this).val("");
    });
    $("#checkCode").focus(function() {
      $("#checkCodMsg").html("");
    });

    function get_time() {
      return new Date().getTime();
    }

    $("#submitButtn").click(function() {
      if ($("#resetPwdByIdno").parsley("isValid")) {

        var imgCodeVal = $("#imgCodeFlag").val();
        if (imgCodeVal != "true") {
          if($("#checkCode").val()==""){
            $("#checkCodMsg").html("请输入校验码");
            return;
          }else{
            $("#checkCode").focus();
          }
          $("#comPwdMsg").html("");
          $("#newPwdMsg").html("");
          return;
        }

        var verifyCode = $("#verifyCode").val();
        if (verifyCode == null || verifyCode == "") {
          $("#verifyCodeMsg").html("请输入短信验证码");
          $("#comPwdMsg").html("");
          $("#newPwdMsg").html("");
          return;
        }
        var isVerifyCode = 'N';
        if ($("#submitButtn").hasClass('clickedClass')) {
          return;
        }
        $("#submitButtn").addClass('clickedClass');
        
        $.ajax({
          url: '/api/security/matchCode',
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

        $.ajax({
          url: "/api/security/getRandomString?key=keyNewPassword&" + get_time(),
          type: "GET",
          async: false,
          success: function(srand_num) {
            pgeditor1.pwdSetSk(srand_num);
          }
        });
        var newPwd = pgeditor1.pwdResult();
        //
        $.ajax({
          url: "/api/security/getRandomString?key=keyNewPassword2&" + get_time(),
          type: "GET",
          async: false,
          success: function(srand_num) {
            pgeditor2.pwdSetSk(srand_num);
          }
        });
        var newPwd2 = pgeditor2.pwdResult();
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
              var mobileNo = $("#mobileNo").val();
              var idNo = $("#idNo").val();
              var checkCode = $("#checkCode").val();
              $.ajax({
                url: "/api/security/resetPayPwdByIdNo",
                type: "POST",
                data: {
                  mobileId: mobileId,
                  idNo: idNo,
                  newPwd: newPwd,
                  checkCode: checkCode
                },
                success: function(data) {
                  $("#submitButtn").removeClass('clickedClass');
                  if (data.data.status == "success") {
                    location.href = "/mypay/security/paypwd/reset/ok";
                    return;
                  }else{
                     require("common/send_sms").cleanImgCodeFun();
                  $("#verifyCode").val("");
                  $("#msgNote").css("display", "block");
                  $("#errorMsg").html("支付密码重置失败，" + data.data.msg);
                  return;
                  }
                },
                error: function(data) {
                  $("#submitButtn").removeClass('clickedClass');
                  require("common/send_sms").cleanImgCodeFun();
                  $("#verifyCode").val("");
                  $("#msgNote").css("display", "block");
                  $("#errorMsg").html("支付密码重置失败");
                  return;
                }
              });
            } else {
              $("#submitButtn").removeClass('clickedClass');
              require("common/send_sms").cleanImgCodeFun();
              $("#_ocx_password2").focus();
              $("#comPwdMsg").html("两次输入新密码不一致");
              $("#newPwdMsg").html("");
              $("#verifyCodeMsg").html("");
              $("#msgNote").css("display", "none");
              return;
            }
          },
          error: function(data) {
            $("#submitButtn").removeClass('clickedClass');
            require("common/send_sms").cleanImgCodeFun();
            $("#verifyCode").val("");
            $("#msgNote").css("display", "block");
            $("#errorMsg").html("支付密码验证失败。");
            return;
          }
        });
      } else {
        $("#resetPwdByIdno").parsley("validate")
      }
    });
    $(".js-security-input1").replaceWith(pgeditor1.innerGenerate());
    $(".js-security-input2").replaceWith(pgeditor2.innerGenerate());
    pgeditor1.pgInitialize();
    pgeditor2.pgInitialize();

    require("common/send_sms").gemCheCodParFun("checkCode", "checkImg", "imgCodeFlag", "imageCode");
    require("common/send_sms").genImgCodeFun();


    $("#changeCode").click(function(event) {

      require("common/send_sms").cleanImgCodeFun();
    });
    $("#imageCode").click(function(event) {

      require("common/send_sms").cleanImgCodeFun();
    });


    $("#checkCode").keyup(function() {
      $("#imgCodeFlag").val("false");

      require("common/send_sms").checkCodeFun();

    });


    $("#sendCode").click(function() {
      var imgCodeVal = $("#imgCodeFlag").val();
      if($("#checkCode").val()==""){
        $("#checkCodMsg").html("请输入校验码");
        return;
      }
      if (imgCodeVal != "true") {
        return false;

      }

      require("common/send_sms").getParam("sendCode", "/api/mobileclient/testsend", "", "logined", "", "imgCodeFlag", "notice","checkType");

      require("common/send_sms").sendClickFun();

    });
  }
};