module.exports = {
  init: function() {

  	//非实名不能转账
    $("a[name='transfer']").click(function(){
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