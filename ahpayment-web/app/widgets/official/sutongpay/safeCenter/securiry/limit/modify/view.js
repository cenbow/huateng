module.exports = {
	init: function() {
		$(function() {
			//初始化表单验证
			$("#riskForm").parsley();
			//刚进入页面的默认值
			$("#txnAmt").val($("#txnAmt2").val());
			$("#txnDayAmt").val($("#txnDayAmt2").val());
			$("#txnMonAmt").val($("#txnMonAmt2").val());
			$(".ui-txtinput02").attr("disabled", true);
		});

		

		// 修改限额
		$("#changeButtn").click(function() {
			if ($("#changeButtn").hasClass('clicked')) {
				return;
			}
			$("#changeButtn").addClass('clicked');
			$(".ui-txtinput02").attr("disabled", false);
			$(this).hide();
			$("#changeButtn").removeClass('clicked');
			$("#subButtn").css("display", "inline-block");
		});


		// 提交
		$("#subButtn").click(function() {
			if ($("#riskForm").parsley("isValid")) {
				var txnA = $("#txnAmt").val();
				var txnD = $("#txnDayAmt").val();
				var txnM = $("#txnMonAmt").val();
				var modFlag = 'false';
				$.ajax({
					url: '/api/security/isModifyRisk',
					type: 'GET',
					dataType: 'json',
					async: false
				})
					.done(function(data) {
						if (data.data.status == "success") {
							if (data.data.isRealName == '1') {
								if (txnA * 100 <= 1000000) {
									if (txnD * 100 <= 1000000) {
										if (txnM * 100 <= 2000000) {
											modFlag = 'true';
										} else {
											require("common/extras/popup").alert("", "error", "初级实名用户,月累计消费限额上限不能超过20000", 5);
											return;
										}
									} else {
										return require("common/extras/popup").alert("", "error", "初级实名用户,日累计消费限额上限不能超过10000", 5);
									}
								} else {
									return require("common/extras/popup").alert("", "error", "初级实名用户,单笔消费限额上限不能超过10000", 5);
								}
							} else if (data.data.isRealName == '4') {
								if (txnA * 100 <= 1000000) {
									if (txnD * 100 <= 1000000) {
										if (txnM * 100 <= 3000000) {
											modFlag = 'true';
										} else {
											return require("common/extras/popup").alert("", "error", "高实名用户,月累计消费限额上限不能超过30000", 5);
										}
									} else {
										return require("common/extras/popup").alert("", "error", "高级实名用户,日累计消费限额上限不能超过10000", 5);
									}
								} else {
									return require("common/extras/popup").alert("", "error", "高级实名用户,单笔消费限额上限不能超过10000", 5);
								}
							} else {
								
									if (txnA * 100 <= 50000) {
										if (txnD * 100 <= 50000) {
											if (txnM * 100 <= 200000) {
												modFlag = 'true';

											} else {
												return require("common/extras/popup").alert("", "error", "非实名用户,月累计消费限额上限不能超过2000", 5);
											}
										} else {
											return require("common/extras/popup").alert("", "error", "非实名用户,日累计消费限额上限不能超过500", 5);
										}
									} else {
										return require("common/extras/popup").alert("", "error", "非实名用户,单笔消费限额上限不能超过500", 5);
									}
								
							}
						} else {
							return require("common/extras/popup").alert("", "error", data.data.msg, 5);
						}

					})
					.fail(function() {
						return require("common/extras/popup").alert("", "error", "查询客户信息失败", 5);
					});

				if (modFlag == 'false') {
					return;
				}

				if ((txnM - 0) >= (txnD - 0)) {
					if ((txnD - 0) >= (txnA - 0)) {
						$.ajax({
							url: "/api/security/modifyRiskParam",
							type: "POST",
							async: false,
							data: {
								txnAmt: $("#txnAmt").val(),
								txnDayAmt: $("#txnDayAmt").val(),
								txnMonAmt: $("#txnMonAmt").val()
							},
							success: function(data) {
								if (data.data.status == "success") {
									$.when(require("common/extras/popup").alert("", "success", "交易限额设置成功。", 5))　　.done(function() {
										location.href = "/safeCenter/home";
									})　　.fail(function() {
										location.href = "/safeCenter/home";
									});

								} else {
									return require("common/extras/popup").alert("", "error", "交易限额设置失败，" + data.data.msg, 5);
								}


							},
							error: function(data) {
								return require("common/extras/popup").alert("", "error", "交易限额设置失败", 5);
							}
						});
					} else {
						return require("common/extras/popup").alert("", "error", "您输入的日累计消费限额必须大于或等于单笔消费限额", 5);
					}
				} else {
					return require("common/extras/popup").alert("", "error", "您输入的月累计消费限额必须大于或等于日累计消费限额", 5);
				}

			} else {
				$("#riskForm").parsley("validate")
			}
		});
		$("#backButtn").click(function() {
			location.href = "/safeCenter/home";
		});


	}
};