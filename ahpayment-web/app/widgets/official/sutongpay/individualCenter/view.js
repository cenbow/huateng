module.exports = {
  init: function(){
    //判断用户是否登录
    require("common/extras/checkLogin").checkLogin();

    $("#recharge").click(function(){
      location.href="/api/webGatePay/recharge";
    })

    $("#laddCard").click(function(){
      location.href="/myCard/addCard";
    })
    //非实名不能提现
    $("#cashaway").click(function(){
      require("common/extras/checkLogin").checkLogin();
      $.ajax({
        url: '/api/user/realName',
        type: 'GET',
        dataType:"json",
        cache:false,
        success:function(data){
          if(data.data.msg!="4" && data.data.msg!="1"){
            require("common/extras/popup").alert("", "error", "非实名用户不能提现", 5);
            return;
          }else{
            location.href="cashaway";
          }
        },
        error:function(){

       }
      })
    });    
    
    //非实名不能转账
    $("#transfer").click(function(){
      require("common/extras/checkLogin").checkLogin();
      $.ajax({
        url: '/api/user/realName',
        type: 'GET',
        dataType:"json",
        cache:false,
        success:function(data){
          if(data.data.msg!="4" && data.data.msg!="1"){
            require("common/extras/popup").alert("", "error", "非实名用户不能转账", 5);
            return;
          }else{
            location.href="/payment/transfer";
          }
        },
       error:function(){

       }
      })
    });

    $.ajax({
        type:"GET",
        url:"/api/mypay/summary/detail",
        dataType:"json",
        cache:false,
        success:function(data){
          //$("#balance").text(data.data.balance);
          if(data.data.realName!="4"){
            var realNameInfo
            if(data.data.realName=="1"){
              realNameInfo = "初级实名认证"
            }else{
              realNameInfo = "非实名"
            }          
            $("#authRealName").append(realNameInfo + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='ui-txt-a ui-txt-wh' href='/api/security/result'>申请高级实名认证</a>");
          }else{
          $("#authRealName").append("高级实名认证");
          }
          $("#bankNum").text(data.data.bankNum);
        },
        error:function(){
          $("#success").hide();
          $("#fail").show();
        }
    });
    $.ajax({
         type: "GET",
         url: "/api/mypay/summary/integral",
         dataType:"json",
         cache:false,
         success: function(data) {
            $("#integral").text(data.data);
         },
         error:function(){
            $("#integral").text("0");
         }
      });
    $.ajax({
         type: "GET",
         url: "/api/user/proCity",
         dataType:"json",
         cache:false,
         success: function(data) {
            var jsonStr = data.data;
            var area_city_code = $.parseJSON(jsonStr);
            if(area_city_code.areaCode == "310000"){
              if($("#SH")) {
                $("#SH").show();
              }
            }else{
              if("#NOSH"){
                $("#NOSH").show();
              }
            }
         },
         error:function(){

         }
    });

  }
}
