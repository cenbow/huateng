module.exports = {
  init: function() {
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


//账号检查
function isNotTelPhone(transPhone) {
  return transPhone.match(/^(1[0-9][0-24-9]|15[0-24-9]|18[2-8])[0-9]{8}$/);
}

function isNotEmail(unifyId) {
        return unifyId.match(/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/);
      }

$("#transPhone").blur(function(){
  if((!isNotTelPhone($("#transPhone").val()) && !isNotEmail($("#transPhone").val()))&& $("#transPhone").val().length!=0){
   $("#transferPhoneMsg").html("账号格式不对");
    return;
  }else{
    $("#transferPhoneMsg").html("");
    return;
  }

})




//转账金额检查
function isNotAmount(transAmount) {
  return transAmount.match(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/);
}

$("#transferAmount").blur(function(){
  if(!isNotAmount($("#transferAmount").val())&& $("#transferAmount").val().length!=0){
   $("#transferAmountMsg").html("转账金额格式不对");
    return;
  }else{
    $("#transferAmountMsg").html("");
    return;
  }

})




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

    $("input[name='transPhone']").keyup(function(){
      
      if($(this).val().length == 1){

        $("#transferPhoneMsg").html("");//清除提示
        
      }
    })

    $("input[name='transferAmount']").keyup(function(){
      
      if($(this).val().length == 1){

        $("#transferAmountMsg").html("");//清除提示
        
      }
    })


	
    $("#nextButton").click(function(){
      require("common/extras/checkLogin").checkLogin();
    	 if ($("#transPhone").val().length == 0) {
        $("#transPhone").focus();
        $("#transferPhoneMsg").html("收款人账号不能为空！");
        $("#transferAmountMsg").html("");
        $("#textfieldMsg").html("");
        return;

      }

      $("#transPhone").blur(function(){
  if((!isNotTelPhone($("#transPhone").val()) && !isNotEmail($("#transPhone").val()))&& $("#transPhone").val().length!=0){
   $("#transferPhoneMsg").html("账号格式不对");
    return;
  }else{
    $("#transferPhoneMsg").html("");
    return;
  }

})

      if ($("#transferAmount").val().length == 0) {
        $("#transferAmount").focus();
        $("#transferPhoneMsg").html("");
        $("#transferAmountMsg").html("转账金额不能为空！");
        $("#textfieldMsg").html("");
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

      var ckbox = $("#ckbox")
          if(!ckbox.is(":checked")){
            require("common/extras/popup").alert("", "error", "请阅读并同意《速通支付缴费协议》", 5);
            return;
          }



      $.ajax({
        url: "/api/accountTransfer/transferCheck",
        type: "POST",
        data: {
          amount: $("#amount").val(),
          transferAmount: $("#transferAmount").val(),
          transPhone: $("#transPhone").val(),
          field:$("#field").val()
        },
        success: function(data) {
          if(data.data.status == "success"){
           
            $("#formNumber").submit();
           
          }else{
            $("#imgCodeFlag").val("false");
            flushCode();
            require("common/extras/popup").alert("", "error", data.data.msg, 5);


          }
        },
        error: function(data) {
          $("#imgCodeFlag").val("false");
          flushCode();
         require("common/extras/popup").alert("", "error", "转账检查异常", 5);
        }
      });

    });



  	  
  }
}