module.exports = {
  init: function() {
	      //获取时间
    function get_time() {
      return new Date().getTime();
    }


    function isNotPwd(pwd) {
        return pwd.match(/^(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,20}$/);
      }

$("#setnewPasswd").blur(function() {
  var setnewPasswd=$("#setnewPasswd").val();
      if(!isNotPwd(setnewPasswd)){
       
          $("#newPwdMsg").html("请输入6到20位字母和数字组合");
          return;
        }else{
         
          $("#newPwdMsg").html("");
        }
      })

      $("#confirmPasswd").blur(function() {
        var setnewPasswd=$("#setnewPasswd").val();
      var confirmPasswd=$("#confirmPasswd").val();
      if(setnewPasswd!=confirmPasswd){
           $("#comPwdMsg").html("确认密码和新密码不一致");
          return;
        }
      if(!isNotPwd(confirmPasswd)){
       
          $("#comPwdMsg").html("请输入6到20位字母和数字组合");
          return;
        }else{
         
          $("#comPwdMsg").html("");
        }
      })
   
   
$("input[name='newPasswd']").keyup(function(){
      
      if($(this).val().length > 0){

        $("#newPwdMsg").html("");//清除提示
        
      }
    })

$("input[name='confirmPasswd']").keyup(function(){
      
      if($(this).val().length > 0){

        $("#comPwdMsg").html("");//清除提示
        
      }
    })


   
    //点击确定按钮事件
    $("#modifyBtn").click(function() {
      var setnewPasswd=$("#setnewPasswd").val();
      var confirmPasswd=$("#confirmPasswd").val();
        if(setnewPasswd=="" || setnewPasswd==null){
          $("#newPwdMsg").html("请输入新密码");
          return;
        }
        if(!isNotPwd(setnewPasswd)){
       
          $("#newPwdMsg").html("请输入6到20位字母和数字组合");
          return;
        }else{
         
          $("#newPwdMsg").html("");
        }

        if(confirmPasswd=="" || confirmPasswd==null){
          $("#comPwdMsg").html("请输入确认密码");
          return;
        }
        if(setnewPasswd!=confirmPasswd){
           $("#comPwdMsg").html("确认密码和新密码不一致");
          return;
        }
        if(!isNotPwd(confirmPasswd)){
       
          $("#comPwdMsg").html("请输入6到20位字母和数字组合");
          return;
        }else{
         
          $("#comPwdMsg").html("");
        }

        
      
      $.ajax({

        url: "/api/passWordMgn/re_loginPwd",
        type: "POST",
        data: {
          unifyId: $("#unifyId").val(),
          newPasswd: $("#setnewPasswd").val()
        },
        success: function(data) {
          if(data.data.status == "ok"){
                 location.href="/forget/success"
          }else{
            require("common/extras/popup").alert("", "success", data.data.msg, 5);

          }
        },
        error: function(data) {
         
          require("common/extras/popup").alert("", "error", "重置密码失败", 5);
        }
      })
    });

    $("#rn").click(function(){
      history.go(-1)

    });
     require("common/send_sms").gemCheCodParFun("vcode", "checkImg", "imgCodeFlag", "codeImg");
    require("common/send_sms").genImgCodeFun();
  }


}