module.exports = {
  init: function() {
        //获取时间
    function get_time() {
      return new Date().getTime();
    }

    function isNotEmail(unifyId) {
        return unifyId.match(/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/);
      }

      function isNotTelPhone(unifyId) {
        return unifyId.match(/^(1[0-9][0-9]|15[0-9]|18[0-9])[0-9]{8}$/);
      }

$("#unifyId").blur(function() {
        var uno=$("#unifyId").val();
        
        if(uno.length==""){
          $("#unifyIdMsg").html("请输入用户名");
          return;
        }

        if (!isNotTelPhone(uno) && !isNotEmail(uno)) {
          $("#unifyIdMsg").html("手机号或邮箱地址格式不正确");
          return;
        }else
        {
          $("#unifyIdMsg").html("");
          $.ajax({
            url: "/api/checkRegister",
            type: "GET",
            cache: false,
            data: {
              unifyId: uno
            },
            success: function(data) {
              if (data.data.status=="success") {
                
              } else {
                $("#unifyIdMsg").html("该用户不存在");
              }
            },
            error: function(data) {
            }
          });






        }
      })

      $("#unifyId").keyup(function() {
        if ($(this).val().length > 0) {
          $("#unifyIdMsg").html("");
          return;
        }
      })

    //获取图片验证码
    function flushCode() {
      var src = "/api/captcha/get?" + get_time();
      $("#codeImg").attr("src",src);
      $("#codeImg").show();
      $("#codeFlagSpan").hide();
      var obj = document.getElementById("vcode");
      obj.value = obj.defaultValue;
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

$("input[name='mobileNo']").keyup(function(){
      
      if($(this).val().length >0){

        $("#mobileNoMsg").html("");//清除提示
        
      }
    })

$("input[name='unifyId']").keyup(function(){
      
      if($(this).val().length >0){

        $("#unifyIdMsg").html("");//清除提示
        
      }
    })

$("input[name='verifyCode']").keyup(function(){
      
      if($(this).val().length >0){

        $("#verifyCodeMsg").html("");//清除提示
        
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

    //发送短信验证码
    $("#sendCode").click(function() {
       $("#checkCodMsg").html("");
      var imgCodeVal = $("#imgCodeFlag").val();
      var checkCodVal= $("#vcode").val();
      if(checkCodVal==""){
        $("#checkCodMsg").html("请输入验证码");
         return;
      }
      if (imgCodeVal != "true") {
        return false;
      }
      $("#verifyCodeMsg").html("");
       $.ajax({
        url: "/api/user/checkMobile",
        type: "POST",
    
        cache:false,
        data: {
          unifyId: $("#unifyId").val(),
          mobileNo: $("#mobileNo").val()

         },
        success: function(data) {
          $("#modifyBtn").removeClass('clickedClass');
          if (data.data.status == 'ok') {
           require("common/send_sms").getParam("sendCode", "/api/mobileclient/testsend", $("#mobileNo").val(), "logined", "", "imgCodeFlag", "notice","checkType");
           require("common/send_sms").sendClickFun();
          }else{
            $("#modifyBtn").removeClass('clickedClass');
            $("#verifyCodeMsg").html(data.data.msg);
            $("#codeFlagSpan").hide();
            require("common/send_sms").cleanImgCodeFun();
            isVerifyCode = 'N'
          }
        },
        error: function(data) {

            $("#modifyBtn").removeClass('clickedClass');
            $("#verifyCodeMsg").html("手机号校验失败");
            $("#codeFlagSpan").hide();
        }
      });
    });
   
    //点击确定按钮事件
    $("#modifyBtn").click(function() {
      

      var uno=$("#unifyId").val();
      var isUnifyId = 'Y';
        if ($("#unifyId").val().length == 0) {
        $("#unifyId").focus();
        $("#unifyIdMsg").html("用户名不能为空");
        return;

      }

        if (!isNotTelPhone(uno) && !isNotEmail(uno)) {
          $("#unifyIdMsg").html("手机号或邮箱地址格式不正确");
          return;
        }else
        {
          $("#unifyIdMsg").html("");
          $.ajax({
            url: "/api/checkRegister",
            type: "GET",
            cache: false,
            data: {
              unifyId: uno
            },
            success: function(data) {
              if (data.data.status=="success") {
                isUnifyId = 'Y';
              } else {
                $("#unifyIdMsg").html("该用户不存在");
                isUnifyId = 'N';
              }
            },
            error: function(data) {
              isUnifyId = 'N';
              return require("common/extras/popup").alert("", "error", "检验用户名异常", 5);
            }
          });






        }





      if ($("#mobileNo").val().length == 0) {
        $("#mobileNo").focus();
        $("#mobileNoMsg").html("手机号不能为空");
        return;

      }

        var imgCodeVal = $("#imgCodeFlag").val();
      if (imgCodeVal != "true") {
        if($("#vcode").val()==""){
           $("#checkCodMsg").html("请输入验证码");
           return;
         }else{
           $("#vcode").focus();
         }
        $("#comPwdMsg").html("");
        $("#newPwdMsg").html("");
        $("#mobileNoMsg").html("");
        return;
      }
      var verifyCode = $("#verifyCode").val();
      if (verifyCode == null || verifyCode == "") {
        $("#verifyCodeMsg").html("请输入短信验证码");
        $("#comPwdMsg").html("");
        $("#newPwdMsg").html("");
        $("#mobileNoMsg").html("");
        return;
      }

      $.ajax({
        url: "/api/passWordMgn/matchCode_other",
        type: "POST",
        async: false,
        data: {
          unifyId: $("#unifyId").val(),
          mobileNo: $("#mobileNo").val(),
          verifyCode: verifyCode
        },
        success: function(data) {
          $("#modifyBtn").removeClass('clickedClass');
          if (data.data.status == 'suc') {
            $("#verifyCodeMsg").html("");
            isVerifyCode = 'Y';
            location.href="/forget/setPwd?unifyId="+$("#unifyId").val();
          }else{
            $("#modifyBtn").removeClass('clickedClass');
            $("#verifyCodeMsg").html(data.data.msg);
            $("#codeFlagSpan").hide();
            require("common/send_sms").cleanImgCodeFun();
            isVerifyCode = 'N'
          }
        },
        error: function(data) {
            $("#modifyBtn").removeClass('clickedClass');
            $("#verifyCodeMsg").html("短信验证码校验失败");
            $("#codeFlagSpan").hide();
            require("common/send_sms").cleanImgCodeFun();
            isVerifyCode = 'N'
        }
      });
      if (isVerifyCode == 'N') {

        return;
      }

    });

    $("#rn").click(function(){
window.location="/login"

    });
     require("common/send_sms").gemCheCodParFun("vcode", "checkImg", "imgCodeFlag", "codeImg");
    require("common/send_sms").genImgCodeFun(); 
  }


}