function checkLogin(){
  $.ajax({
      type:"GET",
      url:"/api/user/islogin",
      dataType:"json",
      data:{
        target:location.href
      },
      cache:false,
      success:function(data){
        if (data.data.status == "fail") {
          location.href=data.data.target;
        }
      }
    })
};
module.exports = {
  checkLogin: checkLogin
};