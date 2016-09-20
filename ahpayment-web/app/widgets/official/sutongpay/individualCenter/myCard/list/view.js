module.exports = {
	init: function(){
		require("common/softKeyboard").keyBoardIdSet("dfbKey");
    
    require("common/softKeyboard").formIdSet("unbind");

		$("#del").live("click",function(){

			
			$("#bankCardNo").val($(this).parent().parent().children("li").children().eq(2).text());
			
			$("#payPasswd").val("");
			$("#vcode").val("");
			$("#imgCodeFlag").val("false");
			$("#checkCodMsg").html("")
			$("#codeFlagSpan").css('display','none')

			$("#cover").addClass('popup-disable-cover');
			$("#modal").addClass('show');
			$("#cover").addClass('zheight');
			$("#modal").removeClass('modalh');
			$("#payPwdMsg").html("");
			$("a[name='flushCode']").click();
			require("common/softKeyboard").closeKeyboard_uncheck();
		})

}
}