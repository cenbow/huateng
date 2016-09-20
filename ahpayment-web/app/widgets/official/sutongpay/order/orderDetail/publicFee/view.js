module.exports = {
	init: function() {

		$("#returnbutton").click(function() {
			$.ajax({
				url: "/api/payment/returnGoods",
				type: "POST",
				data: {
					orderNo: $("input[name='orderNo']").val(),
					count: $("input[name='count']").val(),
					electronicNumber: $("input[name='electronicNumber']").val(),
					refundAmt: $("input[name='orderAmt']").val()
				},
				success: function(data) {
					if (data.data == "000000") {
						window.location.reload() 
					} else {
						alert(data.data);
					}
				},
				error: function(data) {
					alert("退货失败");
				}
			})
		})
	}
}