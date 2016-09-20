module.exports = {
	init: function(){

		$("#mxselect").focus(function(){
			$("#mxselected").css("display","block");
		})
		$("#mxselect").blur(function(){
			$("#mxselected").css("display","none");
		});


		$("#mxselect").click(function(){
			$("#mxselected").css("display","block");
		})

		$("#lm").click(function(){
			$("#mxselected").css("display","none");
			$("#valu").html($("#lm").text());
			
		})

		$("#lwm").click(function(){
			$("#mxselected").css("display","none");
			$("#valu").html($("#lwm").text());
			
			
		})

		$("#introduct").click(function(){
				$("#subinfor").css("display","block");
			})



		function isNotTelPhone(unifyId) {
        return unifyId.match(/^(1[0-9][0-24-9]|15[0-24-9]|18[2-8])[0-9]{8}$/);
      }

      $("#homeTelephone").blur(function(){
      	var hno=$("#homeTelephone").val();

      	if (!isNotTelPhone(hno)&&hno!=""&&hno!=null) {
          $("#homeTelephoneMsg").html("手机号格式不正确");
          return;
        }else{
        	 $("#homeTelephoneMsg").html("");
        }
      })

      $("#homeTelephone").keyup(function() {
        if ($(this).val().length > 0) {
          $("#homeTelephoneMsg").html("");
          return;
        }
      })

      $("#officeTelephone").blur(function(){
      	var hno=$("#officeTelephone").val();

      	if (!isNotTelPhone(hno)&&hno!=""&&hno!=null) {
          $("#officeTelephoneMsg").html("手机号格式不正确");
          return;
        }else{
        	 $("#officeTelephoneMsg").html("");
        }
      })

      $("#officeTelephone").keyup(function() {
        if ($(this).val().length > 0) {
          $("#officeTelephoneMsg").html("");
          return;
        }
      })

      $("#otherTelephone").blur(function(){
      	var ono=$("#otherTelephone").val();

      	if (!isNotTelPhone(ono)&&ono!=""&&ono!=null) {
          $("#otherTelephoneMsg").html("手机号格式不正确");
          return;
        }else{
        	 $("#otherTelephoneMsg").html("");
        }
      })

      $("#otherTelephone").keyup(function() {
        if ($(this).val().length > 0) {
          $("#otherTelephoneMsg").html("");
          return;
        }
      })
       


			$("#subBtn").click(function(){
      require("common/extras/checkLogin").checkLogin();


	var hno=$("#homeTelephone").val();
	var ofno=$("#officeTelephone").val();
	var ono=$("#otherTelephone").val();
	if (!isNotTelPhone(ono)&&ono!=""&&ono!=null) {
          $("#otherTelephoneMsg").html("手机号格式不正确");
          return;
        }

        if (!isNotTelPhone(ofno)&&ofno!=""&&ofno!=null) {
          $("#officeTelephoneMsg").html("手机号格式不正确");
          return;
        }

        if (!isNotTelPhone(hno)&&hno!=""&&hno!=null) {
          $("#homeTelephoneMsg").html("手机号格式不正确");
          return;
        }


        var gender=$("#valu").text();
        if(gender=="男"){
        	gender="1";
        }

        else if(gender=="女"){
		gender="2";
        }else{
        	gender="1";
        }

      $.ajax({
        url: '/api/user/modifyCustomerInfo',
        type: 'POST',
        cache:false,
        data: {
          gender: gender,
          homeTelephone : $("#homeTelephone").val(),
          officeTelephone : $("#officeTelephone").val(),
          otherTelephone : $("#otherTelephone").val(),
          contactAddress : $("#contactAddress").val()
        },
        success:function(data){
        if(data.data.status=="success"){
        	window.location.reload();
        }else{
        	require("common/extras/popup").alert("", "error", "个人信息修改失败", 5);
        }
        },
       error:function(){
       	require("common/extras/popup").alert("", "error", "个人信息修改失败", 5);
       }
      })
    });


		

	}}