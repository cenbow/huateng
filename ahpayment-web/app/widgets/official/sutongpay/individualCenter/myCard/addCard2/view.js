module.exports = {
  init: function(){
    //判断用户是否登录
    require("common/extras/checkLogin").checkLogin();


    function isNotTelPhone(unifyId) {
        return unifyId.match(/^(1[0-9][0-24-9]|15[0-24-9]|18[2-8])[0-9]{8}$/);
      }

      function isNotCardNo(unifyId) {
        return unifyId.match(/^[0-9]{16}$/);
      }

      function isNotIdentityNo(identityNo) {
        return identityNo.match(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/);
      }

      $("#idNo").blur(function(){
      	var idNo=$("#idNo").val();

      	if (!isNotIdentityNo(idNo)&&idNo!=""&&idNo!=null) {
          $("#errMsg").html("身份证格式格式不正确");
          return;
        }else{
        	 $("#errMsg").html("");
        }
      })

      $("#idNo").keyup(function() {
        if ($(this).val().length > 0) {
          $("#errMsg").html("");
          return;
        }
      })

      $("#mobileNo").blur(function(){
      	var mobileNo=$("#mobileNo").val();

      	if (!isNotTelPhone(mobileNo)&&mobileNo!=""&&mobileNo!=null) {
          $("#errMsg").html("手机号格式不正确");
          return;
        }else{
        	 $("#errMsg").html("");
        }
      })

      $("#mobileNo").keyup(function() {
        if ($(this).val().length > 0) {
          $("#errMsg").html("");
          return;
        }
      })

      $("#cardNo").blur(function(){
      	var cardNo=$("#cardNo").val();

      	if (!isNotCardNo(cardNo)&&cardNo!=""&&cardNo!=null) {
          $("#errMsg").html("卡号格式不正确");
          return;
        }else{
        	 $("#errMsg").html("");
        }
      })

      $("#cardNo").keyup(function() {
        if ($(this).val().length > 0) {
          $("#errMsg").html("");
          return;
        }
      })


    $("#subBtn").click(function(){
    	var cardNo=$("#cardNo").val();
    	var idNo=$("#idNo").val();
    	var mobileNo=$("#mobileNo").val();
    	if (!isNotIdentityNo(idNo)) {
          $("#errMsg").html("身份证格式格式不正确");
          return;
        }

        if (!isNotTelPhone(mobileNo)) {
          $("#errMsg").html("手机号格式不正确");
          return;
        }

        if (!isNotCardNo(cardNo)) {
          $("#errMsg").html("卡号格式不正确");
          return;
        }
    	$.ajax({
        url: "/api/bankCard/bindBankCard",
        type: "POST",
        data: {
          cardNo: $("#cardNo").val(),
          name: $("#name").val(),
          idNo: $("#idNo").val(),
          mobileNo: $("#mobileNo").val(),
          bankCode: $("#bankCode").val(),
          cardType: $("#cardType").val()
        },
        success: function(data) {
          if(data.data.status == "success"){
           window.location="/myCard/addCard3"

          }else{
            require("common/extras/popup").alert("", "error", data.data.msg, 5);

          }
        },
        error: function(data) {
          require("common/extras/popup").alert("", "error", "银行卡绑定异常", 5);
        }
      });
    })


    }
}