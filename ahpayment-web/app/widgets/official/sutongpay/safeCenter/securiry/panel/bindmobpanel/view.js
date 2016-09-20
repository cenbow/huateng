module.exports = {
	init: function() {
    require("common/softKeyboard").keyBoardIdSet("dfbKey");
    
    require("common/softKeyboard").formIdSet("");

    function clickKeyboard(number){
     require("common/softKeyboard").clickKeyboard(number);
   }




   $("#payPasswd").click(function(event) {
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
          require("common/softKeyboard").passwordIdSet('payPasswd');
          require("common/softKeyboard").showKeyboard();
        }
      },error:function(){
      }
    })

  });


   $("#payPasswd3").click(function(event) {
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
          require("common/softKeyboard").passwordIdSet('payPasswd3');
          require("common/softKeyboard").showKeyboard();
        }
      },error:function(){
      }
    })

  });

   $("#jbs").click(function(){
    $("#shortMsg").attr("id","shortMsg1");
      $("#shortMsg3").attr("id","shortMsg");
      $("#uitext").attr("id","uitext1");
      $("#uitext3").attr("id","uitext");
      $("#verifyCode").attr("id","verifyCode1");
      $("#verifyCode3").attr("id","verifyCode");
      $("#verifyCodeMsg").attr("id","verifyCodeMsg1");
      $("#verifyCodeMsg3").attr("id","verifyCodeMsg");


      $("#verifyCode").val("");
$("#oldmb3").html($("#oldMobileNo").val());
      $("#cover").addClass('popup-disable-cover');
      $("#modal3").addClass('show');
      $("#cover").addClass('zheight');
      $("#modal3").removeClass('modalh');

   $("#modal2").css('display','none');
      $("#modal").css('display','none');

  });




   $("#libang").click(function(){
     $("#modal").css('display','block');
    $("#shortMsg").attr("id","shortMsg2");
      $("#shortMsg1").attr("id","shortMsg");
      $("#uitext").attr("id","uitext2");
      $("#uitext1").attr("id","uitext");
      $("#verifyCode").attr("id","verifyCode2");
      $("#verifyCode1").attr("id","verifyCode");
      $("#verifyCodeMsg").attr("id","verifyCodeMsg2");
      $("#verifyCodeMsg1").attr("id","verifyCodeMsg");


      

      $("#verifyCode").val("");

      $("#cover").addClass('popup-disable-cover');
      $("#modal").addClass('show');
      $("#cover").addClass('zheight');
      $("#modal").removeClass('modalh');
      $("#mobileNo").val("");

      $("#modal2").css('display','none');
      $("#modal3").css('display','none');

     

   

  });


   $("#close").click(function(){
    require("common/softKeyboard").closeKeyboard_uncheck();
      $("#cover").removeClass('popup-disable-cover');
      $("#modal").removeClass('show');
      $("#cover").removeClass('zheight');
      $("#modal").addClass('modalh');
      location.href="/safeCenter/home"
    })

   $("#close2").click(function(){
    require("common/softKeyboard").closeKeyboard_uncheck();
      $("#cover").removeClass('popup-disable-cover');
      $("#modal2").removeClass('show');
      $("#cover").removeClass('zheight');
      $("#modal2").addClass('modalh');
      location.href="/safeCenter/home"
    })

   $("#close3").click(function(){
    require("common/softKeyboard").closeKeyboard_uncheck();
      $("#cover").removeClass('popup-disable-cover');
      $("#modal3").removeClass('show');
      $("#cover").removeClass('zheight');
      $("#modal3").addClass('modalh');
      location.href="/safeCenter/home"
    })

   $("#xg").click(function(){
    $("#shortMsg").attr("id","shortMsg1");
      $("#shortMsg2").attr("id","shortMsg");
      $("#uitext").attr("id","uitext1");
      $("#uitext2").attr("id","uitext");
      $("#verifyCode").attr("id","verifyCode1");
      $("#verifyCode2").attr("id","verifyCode");
      $("#verifyCodeMsg").attr("id","verifyCodeMsg1");
      $("#verifyCodeMsg2").attr("id","verifyCodeMsg");


      $("#verifyCode").val("");

      $("#cover").addClass('popup-disable-cover');
      $("#modal2").addClass('show');
      $("#cover").addClass('zheight');
      $("#modal2").removeClass('modalh');

      $("#oldmb").html($("#oldMobileNo").val());
      $("#mobileNo2").val("");

      $("#modal").css('display','none');
      $("#modal3").css('display','none');

  });

   $("#resetButtn").click(function(){
    location.href="/safeCenter/home"
  }); 

   $("#resetButtn2").click(function(){
    location.href="/safeCenter/home"
  }); 

   function isNotTelPhone(unifyId) {
    return unifyId.match(/^(1[0-9][0-24-9]|15[0-24-9]|18[2-8])[0-9]{8}$/);
  }

  $("#mobileNo").blur(function() {
    var mobileNo=$("#mobileNo").val();

    if(mobileNo.length==""){
      $("#mobileNoMsg").html("请输入手机号");
      return;
    }

    if (!isNotTelPhone(mobileNo)) {
      $("#mobileNoMsg").html("手机号格式不正确");
      return;
    }
  })

  $("#mobileNo2").blur(function() {
    var mobileNo2=$("#mobileNo2").val();

    if(mobileNo2.length==""){
      $("#mobileNoMsg2").html("请输入手机号");
      return;
    }

    if (!isNotTelPhone(mobileNo2)) {
      $("#mobileNoMsg2").html("手机号格式不正确");
      return;
    }
  })



  $("#mobileNo").keyup(function() {
    if ($(this).val().length > 0) {
      $("#mobileNoMsg").html("");
      return;
    }
  })

  $("#mobileNo2").keyup(function() {
    if ($(this).val().length > 0) {
      $("#mobileNoMsg2").html("");
      return;
    }
  })




     //发送短信验证码   立即绑定
     $("#sendCode").click(function() {
      
      var mobileNo=$("#mobileNo").val();
      if(mobileNo=="" || mobileNo==null){
       $("#mobileNoMsg").html("请输入手机号");
       return;
     }

     require("common/send_sms").getParam("sendCode", "/api/mobileclient/testsend", $("#mobileNo").val(), "logined", "", "imgCodeFlag", "notice","checkType");

     require("common/send_sms").sendClickFun();
   });

     //发送短信验证码  修改绑定手机
     $("#sendCode2").click(function() {
      var oldMobileNo=$("#oldMobileNo").val();
      if(oldMobileNo=="" || oldMobileNo==null){

       return;
     }

     require("common/send_sms").getParam("sendCode2", "/api/mobileclient/testsend", oldMobileNo, "logined", "", "imgCodeFlag", "notice","checkType");

     require("common/send_sms").sendClickFun();
   });

     //发送短信验证码  手机解绑
     $("#sendCode3").click(function() {
      var oldMobileNo=$("#oldMobileNo").val();
      if(oldMobileNo=="" || oldMobileNo==null){

       return;
     }

     require("common/send_sms").getParam("sendCode3", "/api/mobileclient/testsend", oldMobileNo, "logined", "", "imgCodeFlag", "notice","checkType");

     require("common/send_sms").sendClickFun();
   });




     //绑定手机
     $("#submitButtn").click(function(){
      var mobileNo=$("#mobileNo").val();

      var verifyCode = $("#verifyCode").val();
      

      if(mobileNo==null || mobileNo==""){
        $("#mobileNoMsg").html("请输入手机号");
        return;
      }

      if (verifyCode == null || verifyCode == "") {
        $("#verifyCodeMsg").html("请输入短信验证码");
        return;
      }

      if (!isNotTelPhone(mobileNo)) {
        $("#mobileNoMsg").html("手机号格式不正确");
        return;
      }
//检验短信验证码
      $.ajax({
        url: '/api/passWordMgn/matchCodeOutCheckMobileNo',
        type: 'POST',
        async: false,
        data: {
          verifyCode: verifyCode,
          mobileNo: mobileNo
        },
        success: function(data) {
          if (data.data.status == 'suc') {
            $("#verifyCodeMsg").html("");
            isVerifyCode = 'Y';
          }else{
            $("#verifyCodeMsg").html(data.data.msg);
            $("#imgCodeFlag").val("false");
            require("common/send_sms").cleanImgCodeFun();
            isVerifyCode = 'N'
          }
        },
        error: function(data) {
          $("#verifyCodeMsg").html("短信验证码校验失败");
          $("#imgCodeFlag").val("false");
          require("common/send_sms").cleanImgCodeFun();
          isVerifyCode = 'N'
        }
      });
if (isVerifyCode == 'N') {
        return;
      }

      $.ajax({
        url: '/api/security/mobileNoBind',
        type: 'POST',
        async: false,
        data: {
          mobileNo: mobileNo,
          verifyCode : verifyCode
        },
        success: function(data) {

          if (data.data.status == 'success') {
           $.when(require("common/extras/popup").alert("", "success", "绑定成功", 5))　　
                  .done(function() {
                    location.href = "/safeCenter/home";
                  }).fail(function() {
                    location.href = "/safeCenter/home";
                  });
         }else{
          return require("common/extras/popup").alert("", "error", "手机绑定失败", 5);
        }
      },
      error: function(data) {
       return require("common/extras/popup").alert("", "error", "绑定失败", 5);
     }
   });

    });

$("#submitButtn2").click(function(){
var mobileNo2 = $("#mobileNo2").val();

  var verifyCode = $("#verifyCode").val();

  var payPasswd = $("#payPasswd").val();

  
if (verifyCode == null || verifyCode == "") {
    $("#verifyCodeMsg").html("请输入短信验证码"); 
    return;
  }

  if(mobileNo2==null || mobileNo2==""){
    $("#mobileNoMsg2").html("请输入手机号");
    return;
  }

  if (!isNotTelPhone(mobileNo2)) {
    $("#mobileNoMsg2").html("手机号格式不正确");
    return;
  }

  

  if (payPasswd==null || payPasswd=="") {
    $("#payPwdMsg").html("请输入支付密码");
    return;
  }

      //检验短信验证码
      $.ajax({
        url: '/api/passWordMgn/matchCode',
        type: 'POST',
        async: false,
        data: {
          verifyCode: verifyCode
        },
        success: function(data) {
          if (data.data.status == 'suc') {
            $("#verifyCodeMsg").html("");
            isVerifyCode = 'Y';
          }else{
            $("#verifyCodeMsg").html(data.data.msg);
            $("#imgCodeFlag").val("false");
            require("common/send_sms").cleanImgCodeFun();
            isVerifyCode = 'N'
          }
        },
        error: function(data) {
          $("#verifyCodeMsg").html("短信验证码校验失败");
          $("#imgCodeFlag").val("false");
          require("common/send_sms").cleanImgCodeFun();
          isVerifyCode = 'N'
        }
      });
      if (isVerifyCode == 'N') {
        return;
      }



      $.ajax({
        url: '/api/security/modifyMobileNoBind',
        type: 'POST',
        async: false,
        data: {
          mobileNo: mobileNo2,
          payPwd : payPasswd
        },
        success: function(data) {

          if (data.data.status == 'success') {
           $.when(require("common/extras/popup").alert("", "success", "绑定手机修改成功", 5))　　
                  .done(function() {
                    location.href = "/safeCenter/home";
                  }).fail(function() {
                    location.href = "/safeCenter/home";
                  });

         }else{
          return require("common/extras/popup").alert("", "error", data.data.msg, 5);
        }
      },
      error: function(data) {
       return require("common/extras/popup").alert("", "error", "绑定手机修改失败", 5);
     }
   });
})






$("#submitButtn3").click(function(){

  var verifyCode = $("#verifyCode").val();

  var payPasswd = $("#payPasswd3").val();

  
if (verifyCode == null || verifyCode == "") {
    $("#verifyCodeMsg").html("请输入短信验证码"); 
    return;
  }


  

  if (payPasswd==null || payPasswd=="") {
    $("#payPwdMsg").html("请输入支付密码");
    return;
  }

      //检验短信验证码
      $.ajax({
        url: '/api/passWordMgn/matchCode',
        type: 'POST',
        async: false,
        data: {
          verifyCode: verifyCode
        },
        success: function(data) {
          if (data.data.status == 'suc') {
            $("#verifyCodeMsg").html("");
            isVerifyCode = 'Y';
          }else{
            $("#verifyCodeMsg").html(data.data.msg);
            $("#imgCodeFlag").val("false");
            require("common/send_sms").cleanImgCodeFun();
            isVerifyCode = 'N'
          }
        },
        error: function(data) {
          $("#verifyCodeMsg").html("短信验证码校验失败");
          $("#imgCodeFlag").val("false");
          require("common/send_sms").cleanImgCodeFun();
          isVerifyCode = 'N'
        }
      });
      if (isVerifyCode == 'N') {
        return;
      }



      $.ajax({
        url: '/api/security/unMobileNoBind',
        type: 'POST',
        async: false,
        data: {
          payPwd : payPasswd
        },
        success: function(data) {

          if (data.data.status == 'success') {
           $.when(require("common/extras/popup").alert("", "success", "手机解绑成功", 5))　　
                  .done(function() {
                    location.href = "/safeCenter/home";
                  }).fail(function() {
                    location.href = "/safeCenter/home";
                  });

         }else{
          return require("common/extras/popup").alert("", "error", data.data.msg, 5);
        }
      },
      error: function(data) {
       return require("common/extras/popup").alert("", "error", "手机解绑失败", 5);
     }
   });
})





}
}