module.exports = {
		init: function() {
			$("input[name ='allCheck']").click(function() {
				if ($(this).attr("checked") == "checked") { //全部选中
					var allNum = 0;
					var allAmt = 0;
					$("input[name='cartTotalPrice']").each(function(i) {
						allAmt = allAmt + Math.round(($(this).val())*100)/100;
						allNum = allNum + 1;
					});
					$("font[name='allNum']").html(allNum);
					$("font[name='allAmt']").html("￥" + (Math.round(allAmt*100)/100).toFixed(2));
					$("input[type='checkbox']").attr("checked", "checked");
				} else { //全部取消
					$("font[name='allNum']").html("0");
					$("font[name='allAmt']").html("￥0");
					$("input[type='checkbox']").attr("checked", false);
				}
			});

			$("input[type='text']").live("blur",function(){
  	   	 var number = $(this).val();
  	   	 var cartNo = $(this).data("cartno");
  	   	 var cartSkuPrice = $("#cartSkuPrice_" + cartNo).val();
         if(!number.match(/^\d+(\.\d+)?$/)){
         	$(this).val(1);
         	var ts=Math.round(cartSkuPrice*100)/100;
         	$("#cartTotalPrice_" + cartNo).val((Math.round(ts*100)/100).toFixed(2));
         	$(".sale_" + cartNo).html((Math.round(ts*100)/100).toFixed(2));
         	number=1;
         }else{
         	var goodsNum=parseInt($(this).val());
         	$(this).val(goodsNum);
         	
         	var ts=Math.round(cartSkuPrice*goodsNum*100)/100;
			$("#cartTotalPrice_" + cartNo).val((Math.round(ts*100)/100).toFixed(2));
         	$(".sale_" + cartNo).html((Math.round(ts*100)/100).toFixed(2));
			number=goodsNum;
         }
         $.ajax({
							url: '/api/cart/' + cartNo,
							type: 'PUT',
							data: {
								goods_num: parseInt(number)
							},
							cache: false,
							async: false
						})
						.done(function(data) {
							if (data.data != "000000") {
								require("common/extras/popup").alert("", "error", "购物车修改失败", 5);
							}
						})
        
					var allNum = 0;
					var allAmt = 0;
					$("input[name='cartTotalPrice']").each(function(i) {
						allAmt = allAmt + Math.round(($(this).val())*100)/100;
						allNum = allNum + 1;
					});
					if ($("input[name ='allCheck']").attr("checked") == "checked") {
					$("font[name='allNum']").html(allNum);
					$("font[name='allAmt']").html("￥" + (Math.round(allAmt*100)/100).toFixed(2));
				}
  	   })


			$("input[name ='cartNo']").click(function() {
				CalculateNumForCheckOrUnCheck();
			});

			$(".countsub").click(function() {
				var ids = _.map($(".cartCheck input:checked"), function(check) {
					return $(check).val()
				}).join(',');
				if (ids == "") {
					require("common/extras/popup").alert("", "success", "请选择商品", 2);
				} else {
					window.location.href="/cart/confirmCartOrder?ids="+ids;
				}
			});

			$("#deleteCart").click(function() {
					var ids = _.map($(".cartCheck input:checked"), function(check) {
						return $(check).val()
					}).join(',')
					if (ids == "") {
						require("common/extras/popup").alert("", "success", "请选择商品", 2);
					} else {
						return require("common/extras/popup")
							.confirm("", "warn", "确认删除吗？").done(function() {
								return $.ajax({
									url: "/api/cart/patchDelete",
									type: 'PUT',
									cache: false,
									data: {
										ids: ids
									},
									success: function(data) {
										if (data.data = "000000") {
											return window.location.reload();
										} else {
											require("common/extras/popup").alert("", "error", "删除失败," + data.data, 5);
										}
									}
								})
							})
						}
					});

				$(".del").click(function() {
					var cartNo = $(this).data("cartno");
					var name = $(this).data("name");
					return require("common/extras/popup")
						.confirm("", "warn", "确认删除" + name + "吗？").done(function() {
							return $.ajax({
								url: "/api/cart/" + cartNo,
								type: "DELETE",
								success: function(data) {
									if (data.data = "000000") {
										return window.location.reload();
									} else {
										require("common/extras/popup").alert("", "error", "删除失败," + data.data, 5);
									}
								}
							});
						});
				}); 
				$("img[name ='reduce_sum']").click(function() {
					var cartNo = $(this).data("cartno");
					var num = $("#goods_sum_" + cartNo).val();
					var nowNum = parseInt(num) - 1;
					$.ajax({
							url: '/api/cart/' + cartNo,
							type: 'PUT',
							data: {
								goods_num: nowNum
							},
							cache: false,
							async: false
						})
						.done(function(data) {
							if (data.data != "000000") {
								require("common/extras/popup").alert("", "error", "购物车修改失败", 5);
							}
						})

					$("#goods_sum_" + cartNo).val(nowNum);
					if (nowNum == 1) {
						$("#cunt_" + cartNo).hide();
						$("#cuntover_" + cartNo).show();
					}
					var cartTotalPrice = $("#cartTotalPrice_" + cartNo).val();
					var cartSkuPrice = $("#cartSkuPrice_" + cartNo).val();
					$(".sale_" + cartNo).empty();
					var ts=Math.round(cartTotalPrice*100)/100 - Math.round(cartSkuPrice*100)/100;
					$("#cartTotalPrice_" + cartNo).val((Math.round(ts*100)/100).toFixed(2));
					var tsk=Math.round(cartTotalPrice*100)/100 - Math.round(cartSkuPrice*100)/100;
					$(".sale_" + cartNo).html((Math.round(tsk*100)/100).toFixed(2));
					CalculateAmtForMinusOrPlusNum(cartNo);
				});

				function CalculateNumForCheckOrUnCheck() {
					var allAmt = 0;
					var allNum = 0;
					$("input[name='cartNo']").each(function(i) {
						if ($(this).attr("checked") == "checked") {
							var cartNo = $(this).val();
							var cartTotalPrice = $("#cartTotalPrice_" + cartNo).val();
							allAmt = allAmt +  Math.round(cartTotalPrice*100)/100;
							allNum = allNum + 1;
						}
					});
					$("font[name='allNum']").html(allNum);
					$("font[name='allAmt']").html("￥" + (Math.round(allAmt*100)/100).toFixed(2));
				}

				function CalculateAmtForMinusOrPlusNum(cartNo) {
					var allAmt = 0;
					if ($("#check_" + cartNo).attr("checked") == "checked") {
						$("input[name='cartTotalPrice']").each(function(i) {
							allAmt = allAmt + Math.round(($(this).val())*100)/100;
						});
						$("font[name='allAmt']").html("￥" + (Math.round(allAmt*100)/100).toFixed(2));
					}
				}


				$("img[name ='add_sum']").click(function() {
					var cartNo = $(this).data("cartno");
					var num = $("#goods_sum_" + cartNo).val();
					if(isNaN(num)||""==num||num<=0){
						alert("youww")
						return;
					}
					$.ajax({
							url: '/api/cart/' + cartNo,
							type: 'PUT',
							data: {
								goods_num: parseInt(num) + 1
							},
							cache: false,
							async: false
						})
						.done(function(data) {
							if (data.data != "000000") {
								require("common/extras/popup").alert("", "error", "购物车修改失败", 5);
							}
						})
					if (num == 1) {
						$("#cunt_" + cartNo).show();
						$("#cuntover_" + cartNo).hide();
					}
					$("#goods_sum_" + cartNo).val(parseInt(num) + 1);
					var cartTotalPrice = $("#cartTotalPrice_" + cartNo).val();
					var cartSkuPrice = $("#cartSkuPrice_" + cartNo).val();
					$(".sale_" + cartNo).empty();
					var ts=Math.round(cartTotalPrice*100)/100 + Math.round(cartSkuPrice*100)/100;
					$("#cartTotalPrice_" + cartNo).val((Math.round(ts*100)/100).toFixed(2));
					var tsk=Math.round(cartTotalPrice*100)/100 + Math.round(cartSkuPrice*100)/100;
					$(".sale_" + cartNo).html((Math.round(tsk*100)/100).toFixed(2));
					CalculateAmtForMinusOrPlusNum(cartNo);
				});
			}
		}