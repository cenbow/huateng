module.exports = {
  init: function(){
    //判断用户是否登录
    require("common/extras/checkLogin").checkLogin();


    	$('#xy li').click(function(){
        $('ul li').removeClass('selected');
        $(this).addClass('selected');
        $("#seln").attr('src',$(this).children().children().attr('src'));
        $("#seln").attr('title',$(this).attr('title'));
        $("#bankType").val($(this).attr('id'))
        $("#bankCode").val($(this).children('input').val())
        $("#cardType").val($(this).parent().children('input').val())
        $("#scardType").html("信用卡");
   })


      $('#cx li').click(function(){
        $('ul li').removeClass('selected');
        $(this).addClass('selected');
        $("#seln").attr('src',$(this).children().children().attr('src'));
        $("#seln").attr('title',$(this).attr('title'));
        $("#bankType").val($(this).attr('id'))
        $("#bankCode").val($(this).children('input').val())
        $("#cardType").val($(this).parent().children('input').val())
        $("#scardType").html("储蓄卡");
   })


    	$("#subBtn").click(function(){
      require("common/extras/checkLogin").checkLogin();
      var bankType=$(".selected").attr('id');
      if(bankType==null && bankType==""){
      	return require("common/extras/popup").alert("", "error", "请选择银行", 5);

      }
      $("#formCard").submit();
  
    });


    }
}