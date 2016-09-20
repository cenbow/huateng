module.exports = {
	init: function(){



//获取时间
        function get_time() {
          return new Date().getTime();
        }
 flushCode();
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



    $("#payPasswd").click(function(){
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
          $("#dfbKey").removeAttr("style");
          $("#dfbKey").addClass('unbindkeytl');
        }
      },error:function(){
      }
    })
          

    
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




		$("#jb").click(function(){
			if ($("#payPasswd").val().length != 6) {
      $("#payPasswd").focus();
      $("#payPwdMsg").html("请输入6位支付密码");
      return;

    	}

    		checkVcode();
    		if($("#imgCodeFlag").val()=='false'){
    			return;
    		}



    		$.ajax({
          url: "/api/bankCard/unBindBankCard",
          type: "POST",
          cache:false,
          data: {
          	bankCardNo : $("#bankCardNo").val(),
            payPwd: $("#payPasswd").val()
          },
          success: function(data) {
            if (data.data.status=="success") {
              $.when(require("common/extras/popup").alert("", "success", "银行卡解绑成功", 5))　　
                  .done(function() {
                    location.href = "/individualCenter";
                  }).fail(function() {
                    location.href = "/individualCenter";
                  });
            } else {
              require("common/extras/popup").alert("", "error", data.data.msg, 5);
            }
          },
          error: function(data) {
            require("common/extras/popup").alert("", "error", "银行卡解绑异常", 5);
          }
        });


			$("#cover").removeClass('popup-disable-cover');
			$("#modal").removeClass('show');
			$("#cover").removeClass('zheight');
			$("#modal").addClass('modalh');
		})

		$("#close").click(function(){
			$("#cover").removeClass('popup-disable-cover');
			$("#modal").removeClass('show');
			$("#cover").removeClass('zheight');
			$("#modal").addClass('modalh');


		})

		require("common/send_sms").genImgCodeFun();
	}
}