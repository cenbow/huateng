  //日期输入框获得焦点后，移除，防止粘贴
  $(".ui-timeinput").focus(function(){
   $(this).blur();
   });

     function get_time() {
      return new Date().getTime();
    }
//确保结束日期大于等于开始日期
   $( "#startDate" ).datepicker({
      defaultDate: "+1w",
      numberOfMonths: 1,
      maxDate: new Date(),

      onClose: function( selectedDate ) {
       $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
       var arr=selectedDate.split("-");
       var crrent=new Date(arr[0],arr[1]-1,arr[2]);
       //最大90天
       crrent.setTime(crrent.getTime()+(90*3600*1000*24));
       if (crrent.getTime()>get_time()){
        crrent.setTime(get_time());
       }


        $( "#endDate" ).datepicker( "option", "maxDate", crrent );
      }
    });
    $( "#endDate" ).datepicker({
      defaultDate: "+1w",
      numberOfMonths: 1,
      maxDate: new Date(),

      onClose: function( selectedDate ) {
        $( "#startDate" ).datepicker( "option", "maxDate", selectedDate );
         var arr=selectedDate.split("-");
       var crrent=new Date(arr[0],arr[1]-1,arr[2]);
       //最大90天
       crrent.setTime(crrent.getTime()-(90*3600*1000*24));
        $( "#startDate" ).datepicker( "option", "minDate", crrent);
      }
    });

    
//当选择日期跟当月，七天相同时，要选中
 function isselectDate(){
  var myDate = new Date();
  var year = myDate.getFullYear();
  var month = myDate.getMonth()+1;
    if (month<10){
        month = "0"+month;
    }
  var firstDay = year+"-"+month+"-"+"01";
  var today = new Date();
  var today_f = formatDate(today);

  if ($("#startDate").val()==firstDay &&  $("#endDate").val()==today_f){
    $("#daystype").val("30");
  }
 
  var before7 = new Date(today.getTime() - (6*60*60*1000*24));
  before7 = formatDate(before7);

  if ($("#startDate").val()==before7 &&  $("#endDate").val()==today_f){
    $("#daystype").val("7");
  }

  
    }



//all submit button init
$(".portal-button-submit").each(function(){
	$btnSubmit = $(this)
	$btnSubmit.on("click",function(){
		$this = $(this)
    isselectDate();
		$this.parents("form:first").submit();
	});
});


//all captcha init
$(".portal-captcha").each(function(){
    $input = $(this)
    codebtn_id = $input.attr("data-btn")
    codeimg_id = $input.attr("data-img")
    $img = $(codeimg_id)
    $btn = $(codebtn_id)
    captcha = require("/common/extras/captcha")
    captcha.captcha($input,$img,$btn)
})

//all datepicker init
$(".portal-datepicker").each(function(){
	$datepicker = $(this)
	$datepicker.datepicker( $.datepicker.regional[ "zh-CN" ] )
  $("#ui-datepicker-div").wrap('<div class="origin"></div>')
})





//pagination init
pageSize = 10
$("#js-list-pagination").pagination($("#js-list-total").text() || $("#js-list-total").val(), {
  num_display_entries: 5,
  num_edge_entries: 2,
  current_page: $.query.get('startIndex') ? $.query.get('startIndex') - 1 : 0,
  prev_text: "上一页",
  next_text: "下一页",
  items_per_page: pageSize,
  link_to: "javascript:void(0)",
  callback: function(page_id) {
    window.location.search = $.query.set('startIndex', parseInt(page_id) + 1).set('pageSize', pageSize).toString();
    return false;
  }
});

//格式化日期
function formatDate(date){
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  var formatedDate = "";
  formatedDate += year + "-";
  if(month >= 10 ){
    formatedDate += month + "-";
  }
  else{
    formatedDate += "0" + month + "-";
  }
  if(day >= 10 ){
    formatedDate += day ;
  }
  else{
    formatedDate += "0" + day ;
  }
  return formatedDate;
}

//last7 day button init
$(".portal-button-last7").click(function(){
  var today = new Date();
  var before7 = new Date(today.getTime() - (6*60*60*1000*24));
  var today_f = formatDate(today);
  var before7_f = formatDate(before7);
  $("#startDate").val(before7_f);
  $("#endDate").val(today_f);
  $("#daystype").val("7");

    $this = $(this);
    $this.parents("form:first").submit();

});



//curmonth init
$(".portal-button-curmonth").click(function(){
  var myDate = new Date();
  var year = myDate.getFullYear();
  var month = myDate.getMonth()+1;
    if (month<10){
        month = "0"+month;
    }
  var firstDay = year+"-"+month+"-"+"01";
  var today = new Date();
  var today_f = formatDate(today);
  $("#startDate").val(firstDay);
  $("#endDate").val(today_f);
   $("#daystype").val("30");


    $this = $(this);
    $this.parents("form:first").submit();


});



//button css refrence
$('.bz-anniu01').hover(function(){
    $(this).css("background-position","0 -392px");
    $(this).children("span").css("background-position","right -540px");
  },function(){
    $(this).css("background-position","0 -355px");
    $(this).children("span").css("background-position","right -503px");
  });

  $(".b-an").hover(function(){
  $(this).css("background-position","0 -741px")
  $(this).children("span").css("background-position","right -861px")

},function(){
  $(this).css("background-position","0 -711px")
  $(this).children("span").css("background-position","right -831px")
});

   $('.bz-anniu01-b').hover(function(){
  $(this).css("background-position","0 -1659px")
  $(this).children("span").css("background-position","right -1710px")

},function(){
  $(this).css("background-position","0 -1512px")
  $(this).children("span").css("background-position","right -1563px")
});

   $('.bz-anniu01-h25').hover(function(){
  $(this).css("background-position","0 -681px")
  $(this).children("span").css("background-position","right -801px")

},function(){
  $(this).css("background-position","0 -651px")
  $(this).children("span").css("background-position","right -771px")
});

   $(".b-an02").hover(function(){
  $(this).css("background-position","0 -1782px")
  $(this).children("span").css("background-position","right -1847px")

},function(){
  $(this).css("background-position","0 -1756px")
  $(this).children("span").css("background-position","right -1821px")
});
