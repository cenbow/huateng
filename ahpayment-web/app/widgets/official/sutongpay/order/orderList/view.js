module.exports = {
	init: function() {
		initDate();

	        //初始化选中全部
	  	var  feeType=$("#feeType").val();
	  	if (feeType==""){
	   		 $("#F").addClass("sel-inqu");
	  	}else{
	     	$("#"+feeType).addClass("sel-inqu");
  		}

		function initDate() {
			if ($('#startDate').val() == "" && $('#endDate').val() == "") {
				var myDate = new Date();
				var year = myDate.getFullYear();
				var month = myDate.getMonth() + 1;
				if (month < 10) {
					month = "0" + month;
				}
				var firstDay = year + "-" + month + "-" + "01";
				var today = new Date();
				var today_f = formatDate(today);
				$("#startDate").val(firstDay);
				$("#endDate").val(today_f);
			}
		}

		$(".inqu").click(function(){
			$(this).parent().children().removeClass("sel-inqu");
			$(this).addClass("sel-inqu");
			if($(this).attr('id')=='01'||$(this).attr('id')=='02'||$(this).attr('id')=='03'||$(this).attr('id')=='04'||$(this).attr('id')=='05'){
			   $("#feeType").val($(this).attr('id'));
			}else if ($(this).attr('id')=='F'){
			   $("#feeType").val('');
			}
			isselectDate();
			$("#searchform").submit();
		 });

		//格式化日期
		function formatDate(date) {
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			var day = date.getDate();
			var formatedDate = "";
			formatedDate += year + "-";
			if (month >= 10) {
				formatedDate += month + "-";
			} else {
				formatedDate += "0" + month + "-";
			}
			if (day >= 10) {
				formatedDate += day;
			} else {
				formatedDate += "0" + day;
			}
			return formatedDate;
		}
		//当选择日期跟当月，七天相同时，要选中
		function isselectDate() {
			var myDate = new Date();
			var year = myDate.getFullYear();
			var month = myDate.getMonth() + 1;
			if (month < 10) {
				month = "0" + month;
			}
			var firstDay = year + "-" + month + "-" + "01";
			var today = new Date();
			var today_f = formatDate(today);

			if ($("#startDate").val() == firstDay && $("#endDate").val() == today_f) {
				$("#daystype").val("30");
			}

			var before7 = new Date(today.getTime() - (6 * 60 * 60 * 1000 * 24));
			before7 = formatDate(before7);

			if ($("#startDate").val() == before7 && $("#endDate").val() == today_f) {
				$("#daystype").val("7");
			}


		}
	}
}