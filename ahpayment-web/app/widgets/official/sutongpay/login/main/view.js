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
    /*$("input[name='vcode']").keyup(function(){
      if($(this).val().length == 4){
        checkVcode();
      }else{
        $("#codeFlagSpan").attr("style", "display:none");
        $("#codeCheckFlag").val("false");
      }
    })*/
    $(function() {

      //账户登陆后获取账户余额
      if($("#balance").length>0){
        $.ajax({
          type:"GET",
          cache:false,
          url:"/api/account/balance",
          dataType:"json",
          success:function(data){
            $("#balance").text(data.data);
          }
        })
      }else{
        flushCode();
      }
    });

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
            $("#codeFlagSpan").attr("style", "display:show");
            $("#codeCheckFlag").val("true");
          } else {
            $("#codeFlag").attr("src", "/assets/sutongpay/er.png");
            $("#codeFlagSpan").attr("style", "display:show");
            $("#codeCheckFlag").val("false");
          }
        },
        error: function(data) {
          $("#codeFlag").attr("src", "/assets/sutongpay/er.png");
          $("#codeFlagSpan").attr("style", "display:block");
          $("#codeCheckFlag").val("false");
        }
      });
    }
    function isNotEmail(unifyId) {
        return unifyId.match(/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/);
      }

      function isNotTelPhone(unifyId) {
        return unifyId.match(/^(1[0-9][0-9]|15[0-9]|18[0-9])[0-9]{8}$/);
      }
    //用户登录，点击登录按钮事件
    $("#loginButtn").click(function() {
      //是否为正确的手机号码或邮箱
      if (!isNotTelPhone($("#userName").val())&&!isNotEmail($("#userName").val())) {
        alert("用户名格式错误，请输入正确的用户名");
        return;
      }
      //图片验证码验证结果
      /*if ($("#codeCheckFlag").val() != "true") {
        alert("请输入正确的验证码");
        return;
      }*/


      $("#loginButtn").val("正在登陆...");
      $("#loginButtn").css("background", "#dcdcdc");
      $("#loginButtn").css("border", "1px solid #dcdcdc");
      $("#loginButtn").attr("disabled","disabled")
      $.ajax({
        url: "/api/user/login",
        type: "POST",
        data: { 
          unifyId: $("#userName").val(),
          vcode: $("#vcode").val(),
          password: $("#passwd").val(),
          target: $("input[name='target']").val()
        },
        success: function(data) {
          if(data.data.status == "ok"){
            return window.location = data.data.target;
          }else{
            flushCode();
            alert(data.data.msg);
             $("#loginButtn").val("登陆");
             $("#loginButtn").css("background", "#f19a2b");
              $("#loginButtn").removeAttr("disabled")
              
          }
        },
        error: function(data) {
          flushCode();
          alert("登录失败，服务暂时不可用");
          $("#loginButtn").val("登陆");
          $("#loginButtn").css("background", "#f19a2b");
          $("#loginButtn").removeAttr("disabled")
              
        }
      })
    });


    //回车
document.onkeydown = function(e){
    var ev = document.all ? window.event : e;
    if(ev.keyCode==13) {

           $("#loginButtn").click();

     }
}
  }
}