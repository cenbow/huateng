module.exports = {
  init: function(){
    //判断用户是否登录
    require("common/extras/checkLogin").checkLogin();

    $("#recharge").click(function(){
      location.href="/api/webGatePay/recharge";
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


  }
}
