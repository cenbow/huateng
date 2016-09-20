module.exports = {
  init: function(){
    //判断用户是否登录
    require("common/extras/checkLogin").checkLogin();
    setInterval(ts,1000);
    var s=10;
    function ts(){
    	
    	s=parseInt(s)-1;
    	$("#ts").text(s);
    	if(s==0){
    		window.location.href="/individualCenter";
    		return;
    	}
    	
    }


	

    }
}