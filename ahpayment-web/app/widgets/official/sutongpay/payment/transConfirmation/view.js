module.exports = {
  init: function() {

    require("common/softKeyboard").keyBoardIdSet("dfbKey");
    
    require("common/softKeyboard").formIdSet("");

    function clickKeyboard(number){
         require("common/softKeyboard").clickKeyboard(number);
    }

    $("#paymentPwd").click(function(event) {
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
      require("common/softKeyboard").passwordIdSet('paymentPwd');
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


    $("input[name='paymentPwd']").keyup(function(){
      
      if($(this).val().length == 1){

        $("#paymentPwdMsg").html("");//清除提示
        
      }
    })


	
    $("#subButton").click(function(){
       require("common/softKeyboard").closeKeyboard_uncheck();
      require("common/extras/checkLogin").checkLogin();
    	 if ($("#paymentPwd").val().length == 0) {
        $("#paymentPwd").focus();
        $("#paymentPwdMsg").html("支付密码不能为空！");
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
        $("#transferPhoneMsg").html("");
        $("#transferAmountMsg").html("");
        $("#textfieldMsg").html("");

        return;
      }

     

      $.ajax({
        url: "/api/accountTransfer/transfer",
        type: "POST",
        data: {
          amount: $("#amount").val(),
          transferAmount: $("#transferAmount").val(),
          transPhone: $("#transferPhone").val(),
          field: $("#field").val(),
          pwd: $("#paymentPwd").val()
        },
        success: function(data) {
          if(data.data.status == "success"){
           window.location="/payment/transferOk"

          }else{
            $("#imgCodeFlag").val("false");
            flushCode();
            require("common/extras/popup").alert("", "error", data.data.msg, 5);

          }
        },
        error: function(data) {
          $("#imgCodeFlag").val("false");
          flushCode();
          alert("转账异常");
        }
      });

    });



  	  $("#retButton").click(function(){
        location.href="javascript:history.go(-1)";
      });
  }
}