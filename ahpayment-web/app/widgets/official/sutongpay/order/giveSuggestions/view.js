module.exports = {
  init: function() {



    function isNotOrderNo(unifyId) {
        return unifyId.match(/^[0-9]*$/);
      }

      $("#orderNo").blur(function() {
        if (!isNotOrderNo($("#orderNo").val())) {
          $("#orderNoMsg").html("订单号格式不对");
          return;
        }
      })

      $("#orderNo").focus(function(){
 $("#orderNoMsg").html("");
      });


    //点击提交按钮事件
    $("#modifyBtn").click(function() {
      require("common/extras/checkLogin").checkLogin();
      if ($("#orderNo").val().length == 0) {
        $("#orderNoMsg").html("订单号不能为空");
        $("#contextMsg").html("");
        return;

      }
      if ($("#context").val().length == 0) {
        $("#contextMsg").html("投诉内容不能为空");
        $("#orderNoMsg").html("");
        return;

      }

      $.ajax({
        url: "/api/suggestions/submitsug",
        type: "POST",
        data: {
          orderNo: $("#orderNo").val(),
          context: $("#context").val()
        },
        success: function(data) {
          if (data.data.status == "ok") {
            location.href = "/order/suggestionsSearch"
          } else {
            flushCode();
            alert(data.data.msg);

          }
        },
        error: function(data) {
          flushCode();
          alert("提交投诉失败");
        }
      })
    });


    $("#button").click(function(){

      location.href = "/order/suggestionsSearch"
    });
  }
}