module.exports = {
	init: function() {
	 // $(function(){
			$('.ui-ModifySelection').hover(function(){
				$(this).addClass("ui-ModifySelection-hover");
			
			},
			function(){
				$(this).removeClass("ui-ModifySelection-hover");
			});
		//	});
     function resetPwd(way,id){
       $("#"+id).addClass('clicked');
       $.ajax({
		   	url: '/api/security/isModifyOrResetPwd',
		   	type: 'GET',
		   	data: {way: way},
		   	async: false,
		   	cache:false
	    })
	   .done(function(data) {
	         $("#"+id).removeClass('clicked');
		   	 if(data.data.status=="success"){
		   	   if(way=='1'){
		   	     location.href = "/safeCenter/paypwd/reset/question"; 
		   	   }else if(way=='2'){
		   	     location.href = "/safeCenter/paypwd/reset/sms"; 
		   	   }else if(way=='3'){
		   	     location.href = "/safeCenter/paypwd/reset/idno"; 
		   	   }
	           
		   	 }else{
		   	   $("#"+id).removeClass('clicked');
               return require("common/extras/popup").alert("", "error", data.data.msg, 5);
		   	 }
	   })
	   .fail(function(data) {
	        $("#"+id).removeClass('clicked');
            return require("common/extras/popup").alert("", "error", "连接服务异常,请稍后再试", 5);
            
	   });

     }

     $("#questionCon").click(function() {
         location.href = "/safeCenter/paypwd/reset/question"; 
	   
     });
     $("#smsCon").click(function() {
        if($("#smsCon").hasClass('clicked')){
          return;
        }
     	resetPwd("2","smsCon");
     });
     $("#idCon").click(function() {
        if($("#idCon").hasClass('clicked')){
          return;
        }
     	resetPwd("3","idCon");
     });

	}
};