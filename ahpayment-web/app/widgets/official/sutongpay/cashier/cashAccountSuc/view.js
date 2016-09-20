module.exports = {
  init: function() {

$(".ui-button ui-button-morange").click(function(){
		var productNo=$("input[name='productNo']").val();
		var orderAmount=$("input[name='orderAmount']").val();
		var orderSeq = $("input[name='orderSeq']").val();

		$.ajax({
			url: '/api/lockUser/lockCustomer',
			type: 'POST',
			dataType: 'json',
			data: {productNo: productNo,orderAmount: orderAmount,orderSeq:orderSeq}
		})
		.done(function(data) {
			console.log(data.data);
			window.location="../cashier/lockinfo"+ $.query.empty().set("recodeNo", data.data)
			.set("productNo",productNo).set("orderSeq",orderSeq).toString();		
		})
		.fail(function(data) {
			console.log(data.data);
			require("common/extras/popup").alert("", "error", "账户锁定失败", 5).fail;
		});		
	});


  }
}


