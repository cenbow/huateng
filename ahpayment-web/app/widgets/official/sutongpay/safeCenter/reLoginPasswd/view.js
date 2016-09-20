module.exports = {
	init: function() {


		$("#changePwdForm").parsley();
		function isNotPwd(pwd) {
        return pwd.match(/^(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,20}$/);
        }

        $("#oldLoginPasswd").blur(function() {
        var oldLoginPasswd=$("#oldLoginPasswd").val();
        
        if(oldLoginPasswd.length==0){
          $("#oldPwdResult").html("请输入原密码");
          return;
        }

        if(!isNotPwd(oldLoginPasswd)){
          $("#oldPwdResult").html("请输入6到20位字母和数字组合");
          return;
        }else{
          $("#oldPwdResult").html("");
        }
      })

        $("#oldLoginPasswd").keyup(function() {
        if ($(this).val().length > 0) {
          $("#oldPwdResult").html("");
          return;
        }
      })

        $("#newLoginPasswd").blur(function() {
        var newLoginPasswd=$("#newLoginPasswd").val();
        
        if(newLoginPasswd.length==0){
          $("#newPwdResult").html("请输入新密码");
          return;
        }

        if(!isNotPwd(newLoginPasswd)){
          $("#newPwdResult").html("请输入6到20位字母和数字组合");
          return;
        }else{
          $("#newPwdResult").html("");
        }
      })

        $("#newLoginPasswd").keyup(function() {
        if ($(this).val().length > 0) {
          $("#newPwdResult").html("");
          return;
        }
      })


         $("#confirmLoginPasswd").blur(function() {
        var confirmLoginPasswd=$("#confirmLoginPasswd").val();
        var newLoginPasswd=$("#newLoginPasswd").val();

        if(confirmLoginPasswd.length==0){
          $("#comPwdResult").html("请输入确认密码");
          return;
        }

        if(confirmLoginPasswd!=newLoginPasswd){
          $("#comPwdResult").html("新密码两次输入不一致");
          return;
        }
        $("#comPwdResult").html("");
      })

        $("#confirmLoginPasswd").keyup(function() {
        if ($(this).val().length > 0) {
          $("#comPwdResult").html("");
          return;
        }
      })

		$("#submitButtn").click(function() {
			require("common/softKeyboard").closeKeyboard_uncheck();
      var oldLoginPasswd=$("#oldLoginPasswd").val();
        
        if(oldLoginPasswd.length==0){
          $("#oldPwdResult").html("请输入原密码");
          return;
        }

        if(!isNotPwd(oldLoginPasswd)){
          $("#oldPwdResult").html("请输入6到20位字母和数字组合");
          return;
        }else{
          $("#oldPwdResult").html("");
        }
      var newLoginPasswd=$("#newLoginPasswd").val();
        
        if(newLoginPasswd.length==0){
          $("#newPwdResult").html("请输入新密码");
          return;
        }

        if(!isNotPwd(newLoginPasswd)){
          $("#newPwdResult").html("请输入6到20位字母和数字组合");
          return;
        }else{
          $("#newPwdResult").html("");
        }

      var confirmLoginPasswd=$("#confirmLoginPasswd").val();
        var newLoginPasswd=$("#newLoginPasswd").val();

        if(confirmLoginPasswd.length==0){
          $("#comPwdResult").html("请输入确认密码");
          return;
        }

        if(confirmLoginPasswd!=newLoginPasswd){
          $("#comPwdResult").html("新密码两次输入不一致");
          return;
        }

		    if($("#submitButtn").hasClass('clicked')){
		       return;
		    }
			if ($("#changePwdForm").parsley("isValid")) {
				if ($("#oldLoginPasswd").val() == $("#newLoginPasswd").val()) {
					
					return require("common/extras/popup").alert("", "error", "新密码不能和原密码相同", 5);
				}
			
				$.ajax({
					url: "/api/security/reSetLoginPasswd",
					type: "POST",
					// async: false,
					data: {
						oldLoginPasswd: $("#oldLoginPasswd").val(),
						newLoginPasswd: $("#newLoginPasswd").val(),
						confirmLoginPasswd: $("#confirmLoginPasswd").val()
						
					},
					success: function(data) {
						if (data.data.status == 'success') {
						    $("#submitButtn").addClass('clicked');
							location.href="/safeCenter/loginPwd/ok";
							
						} else {
							return require("common/extras/popup").alert("", "error", "密码修改失败，" + data.data.msg, 5);
						}
					},
					error: function(data) {
						return require("common/extras/popup").alert("", "error", "密码修改失败", 5);
					}
				});
			} else {
				$("#changePwdForm").parsley("validate");
			}
		});

		$("#resetButtn").click(function() {
			$("#oldLoginPasswd").val("");
			$("#newLoginPasswd").val("");
			$("#confirmLoginPasswd").val("");
			window.location="/safeCenter/home"
		})
	}
};