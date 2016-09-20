module.exports = {
  init: function() {
  	$(function(){
	// 页面浮动面板
	$("#floatPanel a.arrow").eq(0).click(function(){
		$("html,body").animate({scrollTop :0}, 300);
		return false;
	});
	$("#floatPanel a.arrow").eq(1).click(function(){
		$("html,body").animate({scrollTop : $(document).height()}, 300);
		return false;
	});

	$("#customer").hover(function() {
		document.getElementById("customer").src="/assets/sutongpay/rightools_over-01.jpg";
	}, function() {
		document.getElementById("customer").src="/assets/sutongpay/rightools-01.jpg";
	});
	$("#cart").hover(function() {
		document.getElementById("cart").src="/assets/sutongpay/rightools_over-02.jpg";
	}, function() {
		document.getElementById("cart").src="/assets/sutongpay/rightools-02.jpg";
	});

	var panel = $(".popPanel");	
	var w = panel.outerWidth();
	
	$(".qrcode").hover(function(){
		panel.css("width","0px").show();
		panel.animate({"width" : w + "px"},300);
	},function(){
		panel.animate({"width" : "0px"},300);
	});
	
});
  }
}