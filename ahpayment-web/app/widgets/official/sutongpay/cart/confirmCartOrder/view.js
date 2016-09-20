module.exports = {
	init: function() {
		function CaculateAllAmt() {
			var allAmt = 0;
			$("input[name='cartTotalPrice']").each(function(i) {
				allAmt = allAmt + Math.round(($(this).val())*100)/100;
			});
			$("font[name='allAmt']").html("￥" + (Math.round(allAmt*100)/100).toFixed(2));
		}
		$(function() {
			CaculateAllAmt();
		});
	}
}