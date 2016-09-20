module.exports = {
  init: function() {
  	
  	    
  	   $("input[name ='button']").click(function(){
  	   	     var itemId = $(this).data("id");
             window.location.href="/payment/reChargeCard/detail?itemId="+itemId;
  	   	});
  }
}