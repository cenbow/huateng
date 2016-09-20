module.exports = {
  init: function() {

    require("common/softKeyboard").keyBoardIdSet("dfbKey");
    
    require("common/softKeyboard").formIdSet("payPwdModify");

    function clickKeyboard(number){
        $("#comPwdMsg").html("");
        $("#newPwdMsg").html("");
        $("#oldPwdMsg").html("");
     require("common/softKeyboard").clickKeyboard(number);

   }

   $("#oldPasswd").click(function(event) {

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
          require("common/softKeyboard").passwordIdSet('oldPasswd');
          require("common/softKeyboard").showKeyboard();
        }
      },error:function(){
      }
    })
    
  });




   $("#oldPasswd").blur(function(){
    var oldPasswd=$("#oldPasswd").val();
    if(oldPasswd.length == "" || oldPasswd.length == null){

    }else{
      $("#oldPwdMsg").html("");
      return;
    }

   });


   $("#newPasswd").blur(function(){
    var newPasswd=$("#newPasswd").val();
    if(newPasswd.length == "" || newPasswd.length == null){

    }else{
      $("#newPwdMsg").html("");
      return;
    }

   });


   $("#confirmPasswd").blur(function(){
    var confirmPasswd=$("#confirmPasswd").val();
    var newPasswd=$("#newPasswd").val();
    if(confirmPasswd.length == "" || confirmPasswd.length == null){

    }else{
      $("#comPwdMsg").html("");
    }

    if(newPasswd!=confirmPasswd){
 $("#comPwdMsg").html("确认密码和新密码不一致");
      return;
    }else{
      $("#comPwdMsg").html("");
    }

   });



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


	      //获取时间
        function get_time() {
          return new Date().getTime();
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


    $("input[name='verifyCode']").keyup(function(){
      
      if($(this).val().length == 1){

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
      require("common/softKeyboard").closeKeyboard_uncheck();
      var mobileNo;
      $("#checkCodMsg").html("");
      var isBindmobile='y';
      //检查是否绑定手机
      $.ajax({
        url: '/api/security/isModifyOrResetPwd',
        type: 'GET',
        data: {way: '0'},
        async: false,
        cache:false
      })
     .done(function(data) {
         if(data.data.status=="success"){
        mobileNo=data.data.msg;
         }else{
          isBindmobile='n';
          return require("common/extras/popup").alert("", "error", "您的账号未绑定手机", 5);
            
         }
     })
     .fail(function(data) {
    isBindmobile='n';
            return;
     });

     if(isBindmobile=='n'){
      return;
     }
      
      var imgCodeVal = $("#imgCodeFlag").val();
      var checkCodVal= $("#vcode").val();
      if(checkCodVal==""){
        $("#checkCodMsg").html("请输入验证码");
        return;
      }
      if (imgCodeVal != "true") {
        return false;

      }

      
      require("common/send_sms").getParam("sendCode", "/api/mobileclient/testsend", mobileNo, "logined", "", "imgCodeFlag", "notice","checkType");

      require("common/send_sms").sendClickFun();
    });
    
    //点击确定按钮事件
    $("#modifyBtn").click(function() {
      require("common/softKeyboard").closeKeyboard_uncheck();
      var isBindmobile='y';
      //检查是否绑定手机
      $.ajax({
        url: '/api/security/isModifyOrResetPwd',
        type: 'GET',
        data: {way: '0'},
        async: false,
        cache:false
      })
     .done(function(data) {
         if(data.data.status=="success"){

        
             
         }else{
          isBindmobile='n';
          return require("common/extras/popup").alert("", "error", "您的账号未绑定手机", 5);
               return;
         }
     })
     .fail(function(data) {
    isBindmobile='n';
            return;
     });

     if(isBindmobile=='n'){
      return;
     }
     if ($("#oldPasswd").val().length == 0) {
      $("#oldPasswd").focus();
      $("#oldPwdMsg").html("原密码不能为空");
      $("#newPwdMsg").html("");
      $("#comPwdMsg").html("");
      return;

    }
    if ($("#oldPasswd").val().length != 6) {
      $("#oldPasswd").focus();
      $("#oldPwdMsg").html("请输入6位原密码");
      $("#comPwdMsg").html("");
      $("#newPwdMsg").html("");
      return;

    }

    if ($("#newPasswd").val().length == 0) {
      $("#newPasswd").focus();
      $("#newPwdMsg").html("新密码不能为空");
      $("#oldPwdMsg").html("");
      $("#comPwdMsg").html("");
      return;
    }
    if ($("#newPasswd").val().length != 6) {
      $("#newPasswd").focus();
      $("#newPwdMsg").html("请输入6位新密码");
      $("#oldPwdMsg").html("");
      $("#comPwdMsg").html("");
      return;

    }

    if ($("#confirmPasswd").val().length == 0) {
      $("#confirmPasswd").focus();
      $("#comPwdMsg").html("确认密码不能为空");
      $("#newPwdMsg").html("");
      $("#oldPwdMsg").html("");
      return;
    }

    if ($("#confirmPasswd").val().length != 6) {
      $("#confirmPasswd").focus();
      $("#comPwdMsg").html("请输入6位确认密码");
      $("#newPwdMsg").html("");
      $("#oldPwdMsg").html("");
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
     $("#oldPwdMsg").html("");
     return;
   }
   var verifyCode = $("#verifyCode").val();
   if (verifyCode == null || verifyCode == "") {
    $("#verifyCodeMsg").html("请输入短信验证码");
    $("#comPwdMsg").html("");
    $("#newPwdMsg").html("");
    $("#oldPwdMsg").html("");
    return;
  }



  $.ajax({
    url: '/api/passWordMgn/matchCode',
    type: 'POST',
    async: false,
    data: {
      verifyCode: verifyCode
    },
    success: function(data) {
      $("#modifyBtn").removeClass('clickedClass');
      if (data.data.status == 'suc') {
        $("#verifyCodeMsg").html("");
        $("#comPwdMsg").html("");
        $("#newPwdMsg").html("");
        isVerifyCode = 'Y';
      }else{
        $("#modifyBtn").removeClass('clickedClass');
        $("#verifyCodeMsg").html(data.data.msg);
        $("#comPwdMsg").html("");
        $("#newPwdMsg").html("");
        $("#codeFlagSpan").hide();
        $("#imgCodeFlag").val("false");
        require("common/send_sms").cleanImgCodeFun();
        isVerifyCode = 'N'
      }
    },
    error: function(data) {
      $("#modifyBtn").removeClass('clickedClass');
      $("#verifyCodeMsg").html("短信验证码校验失败");
      $("#comPwdMsg").html("");
      $("#newPwdMsg").html("");
      
      $("#codeFlagSpan").hide();
      $("#imgCodeFlag").val("false");
      require("common/send_sms").cleanImgCodeFun();
      isVerifyCode = 'N'
    }
  });
if (isVerifyCode == 'N') {
  return;
}

if($("#oldPasswd").val() == $("#newPasswd").val()){
  $("#newPwdMsg").html("新密码和原密码不能相同");
return;
}

if($("#confirmPasswd").val() != $("#newPasswd").val()){
  $("#comPwdMsg").html("密码不一致");
return;
}

$.ajax({
  url: "/api/passWordMgn/modify",
  type: "POST",
  data: {
    unifyId: $("#unifyId").val(),
    oldLoginPasswd: $("#oldPasswd").val(),
    newLoginPasswd: $("#newPasswd").val()
  },
  success: function(data) {
    if(data.data.status == "ok"){
     
     location.href="/safeCenter/paypwd/ok"
   }else{
    flushCode();
    return require("common/extras/popup").alert("", "error", data.data.msg, 5);
  }
},
error: function(data) {
  flushCode();
  return require("common/extras/popup").alert("", "error", "修改密码失败", 5);
}
})
});

$("#rn").click(function(){
  window.location="/safeCenter/home"

});
require("common/send_sms").gemCheCodParFun("vcode", "checkImg", "imgCodeFlag", "codeImg");
require("common/send_sms").genImgCodeFun();
}


}