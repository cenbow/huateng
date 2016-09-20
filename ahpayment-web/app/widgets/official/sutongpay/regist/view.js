module.exports = {
  init: function() {
    var inden='y';
      //获取时间
      function get_time() {
        return new Date().getTime();
      }
        //获取图片验证码
        function flushCode() {
          var src = "/api/captcha/get?" + get_time();
          $("#codeImg").attr("src", src);
          $("#codeImg").show();
          $("#codeFlagSpan").hide();
          var obj = document.getElementById("vcode");
          obj.value = obj.defaultValue;
          obj.style.color = '#666';
        }
        //验证码输入事件
        $("input[name='vcode']").keyup(function() {
          if ($(this).val().length == 4) {
            checkVcode();
          } else {
            $("#codeFlagSpan").attr("style", "display:none");
            $("#codeCheckFlag").val("false");
          }
        })

        $(function() {
          flushCode();
        });



      //获取密保问题
      $(function() {
        $.ajax({
          type: "GET",
          cache: false,
          url: "/api/getQuestion",
          dataType: "json",
          success: function(data) {
            $.each(data.data, function() {
              $("#question").append("<option value=" + this.codeValue + ">" + this.codeDesc + "</option>");
            })
          }
        })
      });




      function isNotEmail(unifyId) {
        return unifyId.match(/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/);
      }

      function isNotTelPhone(unifyId) {
        return unifyId.match(/^(1[0-9][0-9]|15[0-9]|18[0-9])[0-9]{8}$/);
      }

      function isNotPwd(pwd) {
        return pwd.match(/^(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,20}$/);
      }

      $("#loginPassword").blur(function() {
        var loginPwd=$("#loginPassword").val();
        
        if(loginPwd.length==""){
          $("#loginPasswordResult").html("请输入登录密码");
          inden='n'
          return;
        }

        if(!isNotPwd(loginPwd)){
          inden='n'
          $("#loginPasswordResult").html("请输入6到20位字母和数字组合");
          return;
        }else{
          inden='y'
          $("#loginPasswordResult").html("");
        }
      })

      $("#r_loginPassword").blur(function() {
        var rLoginPwd=$("#r_loginPassword").val();
        var loginPwd=$("#loginPassword").val();
        
        if(rLoginPwd.length==""){
          $("#r_loginPasswordResult").html("请输入确认登录密码");
          inden='n'
          return;
        }   

        if(loginPwd!=rLoginPwd){
          $("#r_loginPasswordResult").html("密码不一样");
          return;
        }

        if(!isNotPwd(rLoginPwd)){
          inden='n'
          $("#r_loginPasswordResult").html("请输入6到20位字母和数字组合");
          return;
        }else{
          inden='y'
          $("#r_loginPasswordResult").html("");
        }
      })

      $("#unifyId").blur(function() {
        var uno=$("#unifyId").val();
        
        if(uno.length==""){
          inden='n'
          $("#unifyIdMsg").html("请输入账号");
          return;
        }

        if (!isNotTelPhone(uno) && !isNotEmail(uno)) {
          inden='n'
          $("#unifyIdMsg").html("手机号或邮箱地址格式不正确");
          return;
        }else
        {
          inden='y'
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
                inden='n'
                $("#unifyIdMsg").html("该账号已被注册");
              } else {
                inden='y'
              }
            },
            error: function(data) {
              inden='n'
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



      function isNotIdentityNo(identityNo) {
        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
        return reg.test(identityNo);
      }


      $("#identityNo").blur(function() {
        var identityNo=$("#identityNo").val();
        if ($("#identity").val() == 1) {
          if(identityNo.length==""){
            inden='n'
            $("#identityNoMsg").html("请输入证件号码");
            return;
          }

          if (!isNotIdentityNo(identityNo)) {
            inden='n'
            $("#identityNoMsg").html("身份证号格式不正确");
            return;
          }else{
            inden='y'
            $("#identityNoMsg").html("");
          }
        }
      })


      $("#answer").blur(function(){
       var answer=$("#answer").val();
       if(answer==""){
        $("#answerMsg").html("请输入密保答案");
        inden='n'
      }
    });

      $("#answer").keyup(function() {
        if ($(this).val().length > 0) {
          $("#answerMsg").html("");
          return;
        }
      })



      $("#userName").blur(function(){
       var userName=$("#userName").val();
       if(userName==""){
        $("#userNameMsg").html("请输入联系人姓名");
        inden='n'
      }
    });

      $("#userName").keyup(function() {
        if ($(this).val().length > 0) {
          $("#userNameMsg").html("");
          return;
        }
      })


        //刷新图片验证码
        $("a[name='flushCode']").click(function() {
          flushCode();
        }) 
        $("img[name='codeImg']").click(function() {
          flushCode();
        })
        //验证图片验证码
        function checkVcode() {
          $.ajax({
            url: "/api/captcha/match",
            type: "GET",
            cache: false,
            data: {
              actualToken: $("#vcode").val()
            },
            success: function(data) {
              if (data.data) {
                inden='y'
                $("#codeFlag").attr("src", "/assets/sutongpay/se.png");
                $("#codeFlagSpan").attr("style", "display:show");
                $("#codeCheckFlag").val("true");
              } else {
                inden='n'
                $("#codeFlag").attr("src", "/assets/sutongpay/er.png");
                $("#codeFlagSpan").attr("style", "display:show");
                $("#codeCheckFlag").val("false");
              }
            },
            error: function(data) {
              inden='n'
              $("#codeFlag").attr("src", "/assets/sutongpay/er.png");
              $("#codeFlagSpan").attr("style", "display:block");
              $("#codeCheckFlag").val("false");
            }
          });
        }



        $("#submitButtn").click(function() {

          var loginPwd=$("#loginPassword").val();
          var uno=$("#unifyId").val();
          var identityNo=$("#identityNo").val();
          var answer=$("#answer").val();

          //再次校验
          if(answer==""){
            $("#answerMsg").html("请输入密保答案");
            inden='n'
          }

          if(answer==""){
            $("#userName").html("请输入联系人姓名");
            inden='n'
          }




          if(!isNotPwd(loginPwd)){
            if(loginPwd.length==""){
              $("#loginPasswordResult").html("请输入登录密码");

            }else{

              $("#loginPasswordResult").html("请输入6到20位字母和数字组合");
            }
            inden='n'
          }


          



          if (!isNotTelPhone(uno) && !isNotEmail(uno)) {
           if(uno.length==""){

            $("#unifyIdMsg").html("请输入账号");

          }else{

            $("#unifyIdMsg").html("手机号或邮箱地址格式不正确");
          }
          inden='n'
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
                inden='n'
                $("#unifyIdMsg").html("该账号已被注册");
              }
            },
            error: function(data) {
              inden='n'
            }
          });
        }




        
        if ($("#identity").val() == 1) {


          if (!isNotIdentityNo(identityNo)) {
            if(identityNo.length==""){
              $("#identityNoMsg").html("请输入证件号码");

            }else{

              $("#identityNoMsg").html("身份证号格式不正确");
            }
            inden='n'
          }
        }


        if (inden=='y') {
          if ($("#loginPassword").val() != $("#r_loginPassword").val()) {
            flushCode();
             $("#r_loginPasswordResult").html("密码不一样");
          }

          $.ajax({
            url: "/api/register",
            type: "POST",
              // async: false,
              data: {
                unifyId: $("#unifyId").val(),
                loginPassword: $("#loginPassword").val(),
                r_loginPassword: $("#r_loginPassword").val(),
                userName: $("#userName").val(),
                gender: $("#gender").val(),
                identity: $("#identity").val(),
                identityNo: $("#identityNo").val(),
                question: $("#question").val(),
                answer: $("#answer").val(),
                vcode: $("#vcode").val()
              },
              success: function(data) {
                if (data.data.status == 'success') {
                  $("#submitButtn").addClass('clicked');
                  $.when(require("common/extras/popup").alert("", "success", "注册成功。", 5))　　
                  .done(function() {
                    location.href = "/login";
                  }).fail(function() {
                    location.href = "/login";
                  });

                } else {
                  flushCode();
                  return require("common/extras/popup").alert("", "error", "注册失败，" + data.data.msg, 5);
                }
              },
              error: function(data) {
                flushCode();
                return require("common/extras/popup").alert("", "error", "注册失败", 5);
              }
            });
} else {
  return require("common/extras/popup").alert("", "error", "信息填写有误", 5);
  inden='n'
}
});


$("#retButtn").click(function(){
  location.href="/login";
});





}
}