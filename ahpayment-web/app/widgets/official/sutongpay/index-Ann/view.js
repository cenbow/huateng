
module.exports = {
  init: function() { 
 //手动关闭
   $('#close-ann').click(function(){
      $('#js-anndiv').hide();

    });
//10秒后消失 
$(document).ready(function(){  
 setInterval(hideAnn, 10000); //设置时间
});
function hideAnn() {  
	  $("#js-anndiv").hide();
	}    
  }
}
