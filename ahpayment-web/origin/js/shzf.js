// JavaScript Document
// 城市弹出部分
$(function(){
	$('.xiala-img').hover(function(){
		$(this).css({"background-image":"url(../images/inside/xiala-img02.jpg)"});

	},function(){
		$(this).css("background-image","url(../images/inside/xiala-img01.jpg)");
	});
});
$(function(){
	$('.city-close').hover(function(){
		$(this).css({"background-image":"url(../images/inside/city-closeimg02.jpg)"});

	},function(){
		$(this).css("background-image","url(../images/inside/city-closeimg01.jpg)");
	});
});

$(".city-close").click(function(){
  $("#city-tanchu01").hide();
});

$(function(){
	$('.city-list span').hover(function(){
		$(this).css({"background-color":"#EFEFEF"});

	},function(){
		$(this).css("background-color","#ffffff");
	});
});
$(document).ready(function(){

$("#hot-li").click(function(){
  $(".city-main div").hide();
  $("#hot-city").show();
  $(".city-bt01 ul li").removeClass("city-checked");
  $(".city-bt01 ul li").addClass("city-noch");
  $("#hot-li").addClass("city-checked");
});
$("#ag-li").click(function(){
  $(".city-main div").hide();
  $("#A-G").show();
  $(".city-bt01 ul li").removeClass("city-checked");
  $(".city-bt01 ul li").addClass("city-noch");
  $("#ag-li").addClass("city-checked");
});
$("#hl-li").click(function(){
  $(".city-main div").hide();
  $("#H-L").show();
  $(".city-bt01 ul li").removeClass("city-checked");
  $(".city-bt01 ul li").addClass("city-noch");
  $("#hl-li").addClass("city-checked");
});
$("#mt-li").click(function(){
  $(".city-main div").hide();
  $("#M-T").show();
  $(".city-bt01 ul li").removeClass("city-checked");
  $(".city-bt01 ul li").addClass("city-noch");
  $("#mt-li").addClass("city-checked");
});
$("#wz-li").click(function(){
  $(".city-main div").hide();
  $("#W-Z").show();
  $(".city-bt01 ul li").removeClass("city-checked");
  $(".city-bt01 ul li").addClass("city-noch");
  $("#wz-li").addClass("city-checked");
});
});
$(function(){
	$('.city-bt01 span').hover(function(){
		$(this).css({"text-decoration":"underline"});

	},function(){
		$(this).css("text-decoration","none");
	});
});
$(function(){
	$('.life-zfdiv .ture').hover(function(){
		$(this).css({"background-image":"url(../images/inside/life-zfbg01.jpg)"});

	},function(){
		$(this).css("background-image","none");
	});
});

// 左侧栏目变化
$(function(){
	$('.left-main li').hover(function(){
		$(this).css("background-color","#F3F3F3");

	},function(){
		$(this).css("background-color","#FFFFFF");
	});
});
// 改变缴费地区
$(function(){
	$(".paylbgpostion").hover(function(){
		$(this).css("background-position","bottom");

	},function(){
		$(this).css("background-position","top");
	});
});



$(document).ready(function(){
	$("#life-cl01").click(function(){
		$(".lifetcdiv").hide()
		$(".paylbgpostion").css("background-image","url(../images/sdm/jflb01.jpg)");
		$("#life-tc01").show()
		$(this).css("background-image","url(../images/sdm/jflb02.jpg)");
	});
});
$(document).ready(function(){
 $("#life-tc01 ul li").click(function(){
 var aa = $(this).text();
 $("#jf01").text(aa);
 $("#life-tc01").hide();
 $("#life-cl01").css("background-image","url(../images/sdm/jflb01.jpg)");
  });
});


$(document).ready(function(){
	$("#life-cl02").click(function(){
		$(".lifetcdiv").hide()
		$(".paylbgpostion").css("background-image","url(../images/sdm/jflb01.jpg)");
		$("#life-tc02").show()
		$(this).css("background-image","url(../images/sdm/jflb02.jpg)");
	});
});
$(document).ready(function(){
 $("#life-tc02 ul li").click(function(){
 var aa = $(this).text();
 $("#jf02").text(aa);
 $("#life-tc02").hide();
 $("#life-cl02").css("background-image","url(../images/sdm/jflb01.jpg)");
  });
});



$(document).ready(function(){
	$("#life-cl03").click(function(){
		$(".lifetcdiv").hide()
		$(".paylbgpostion").css("background-image","url(../images/sdm/jflb01.jpg)");
		$("#life-tc03").show()
		$(this).css("background-image","url(../images/sdm/jflb02.jpg)");
	});
});
$(document).ready(function(){
 $("#life-tc03 ul li").click(function(){
 var aa = $(this).text();
 $("#jf03").text(aa);
 $("#life-tc03").hide();
 $("#life-cl03").css("background-image","url(../images/sdm/jflb01.jpg)");
  });
});

$(function(){
 $(document).bind("click",function(e){
  var target  = $(e.target);
  if(target.closest(".paylbgpostion").length == 0){
       $(".lifetcdiv").hide();
	   $(".paylbgpostion").css("background-image","url(../images/sdm/jflb01.jpg)");

  }
 })
})




// 弹出DIV
$(document).ready(function(){
 $("#tcan01").click(function(){

 $("#tanchu01").show();
  });
  $(".close").click(function(){

 $("#tanchu01").hide();
  });
});