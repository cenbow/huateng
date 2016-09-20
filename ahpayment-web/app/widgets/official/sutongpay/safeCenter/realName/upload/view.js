module.exports = {
	init: function() {
		$.ajax({
			url: '/api/security/cusInfo',
			type: 'GET',
			dataType: 'json'
		})
			.done(function(data) {
				if (data.data.status == "success") {
					$("#accountName").val(data.data.productNo);
					$("#realName").val(data.data.name);
					$("#idNo").val(data.data.idNo);
					$("#valid").val(data.data.valid);
					$("#address").val(data.data.address);
					if (data.data.nationflag == "false") {
						var natsel = "<select id='nation' name='nation' class='ui-lifepay-select01' >"
						var str = "";
						for (var i = 0; i < data.data.nationsize; i++) {
						    if(data.data.nationlist[i].codeValue=='cn'){
						        str += "<option value='" + data.data.nationlist[i].codeValue + "' selected>" + data.data.nationlist[i].codeDesc + "</option>"
						    }else{
						        str += "<option value='" + data.data.nationlist[i].codeValue + "'>" + data.data.nationlist[i].codeDesc + "</option>"
						    }
							
						}

						$("#nationdiv").html(natsel + str + "</select>");
					} else {
						$("#nation").val(data.data.nationCode);
						$("#nation1").html(data.data.nationDesc);
					}
					if (data.data.profesflag == "false") {
						var prosel = "<select id='profession' name='profession' class='ui-lifepay-select01' >"
						var str = "";
						for (var i = 0; i < data.data.professize; i++) {
							str += "<option value='" + data.data.profeslist[i].codeValue + "'>" + data.data.profeslist[i].codeDesc + "</option>"
						}

						$("#profdiv").html(prosel + str + "</select>");
					} else {
						$("#profession").val(data.data.profCode);
						$("#profession1").html(data.data.profDesc);
					}
				}else{
				    require("common/extras/popup").alert("", "error", data.data.msg, 5);
				}

			})
			.fail(function(data) {
				require("common/extras/popup").alert("", "error", "加载信息失败请稍后再试", 5);
			});

		$("#realForm").parsley();
		$("#idNo").focus(function(event) {
			$("#checkDate").html("");
		});

        $("#idNo").change(function(event) {
             $("#checkDate").html("");
        	var idNo=$("#idNo").val();
        	if(idNo.length==18){
        	   birthDateCheck();
        	}
        }); 
        $("#idNo").blur(function(event) {
            $("#checkDate").html("");
        	var idNo=$("#idNo").val();
        	if(idNo.length==18){
        	   birthDateCheck();
        	}
        });

        function birthDateCheck(){
          var idNo=$("#idNo").val();
          var dataStr=idNo.substring(6,14);
	      var data=dataStr.substring(0,4)+"/"+dataStr.substring(4,6)+"/"+dataStr.substring(6);
	      var idDate=new Date(data);
	      var currentDate=new Date();
	      if(idDate>currentDate){
	         $("#checkDate").html("身份证号码有误");
	         return false;
	      }
        }

		$("#nextStepButtn").click(function() {

			if ($("#realForm").parsley("isValid")) {
			    var idNo=$("#idNo").val();
			    var dateFlag="false";
			    if(idNo.length=='18'){
			      var dataStr=idNo.substring(6,14);
			      var data=dataStr.substring(0,4)+"/"+dataStr.substring(4,6)+"/"+dataStr.substring(6);
			      var idDate=new Date(data);
			      var currentDate=new Date();
			      if(idDate>currentDate){
			         var dateFlag="true";
			         $("#checkDate").html("身份证号码有误");
			         return;
			      }
			    }
			    if(dateFlag=="true"){
			       return;
			    }
				var imgPathVal = $("#imgPath").val();
				if (imgPathVal == "" || imgPathVal == null) {

					$("#idNoImgResult").html("请上传身份证正面图片");
					return;

				}
				$("#realForm").submit();
			} else {
				$("#realForm").parsley("validate");
			}
		});



		$("#img").live('change', function() {

			$.ajaxFileUpload({
				url: '/api/security/uploadImage', //需要链接到服务器地址
				secureuri: false,
				fileElementId: 'img', //文件选择框的id属性
				dataType: 'html', //服务器返回的格式，可以是json
				success: function(data) //相当于java中try语句块的用法
				{

				},
				error: function(data) {
					var restr = data.responseText;
					if (data.responseText.match('413') != null) {
						$("#imgDiv").html("");
						$("#imgPath").val("");
						return require("common/extras/popup").alert("", "error", "图片上传失败，图片大小不能超过3M", 5);
					}
					var str = restr.split("|");
					if (str[0].match('success') != null) {
						$("#imgDiv").html("<img id='bigImage' src='' style='width:300px;height:200px;'/>");
						document.getElementById("bigImage").src = str[1];
						$("#imgPath").val(str[1]);
					} else {
						$("#imgDiv").html("");
						$("#imgPath").val("");
						if (str[1] == 'logout') {
							return require("common/extras/popup").alert("", "error", "登录超时请重新登录。", 5);
						} else if (str[1] == 'errorimg') {
							return require("common/extras/popup").alert("", "error", "请上传.jpg格式图片", 5);
						} else if (str[1] == 'outsize') {
							return require("common/extras/popup").alert("", "error", "图片上传失败，图片大小不能超过3M", 5);
						} else {
							return require("common/extras/popup").alert("", "error", "图片上传失败。", 5);
						}

					}

				}
			});

		});

		//确保大于当天日期
		$("#valid").datepicker({
			changeMonth: true,
            changeYear: true,
			defaultDate: "+1w",
			numberOfMonths: 1,
			minDate: new Date(),
			onClose: function(selectedDate) {
				var date = $("#valid").val()
				date = date.replace(/-/g, '');
				$("#valid").val(date);
			}
		});


	}
};