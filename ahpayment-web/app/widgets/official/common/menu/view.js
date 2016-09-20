module.exports = {
  init: function() {
  	//点击左右键
    
    $('.ui-myleft-agg .next').click(function(){
      var divleft=$("#ui-myuldiv").css("left");
      var divwidth=-($("#ui-myuldiv ul").length*175-175)+"px"
      if(divleft!=divwidth)
      {
      	$("#ui-myuldiv").animate({ left: '+=-175px' }, 500)
      	$(".ui-Round .current").next("i").addClass('show');
      	$(".ui-Round i").removeClass('current')
      	$(".ui-Round .show").addClass('current').removeClass('show');
      } 
    });
     $('.ui-myleft-agg .prew').click(function(){
     var divleft=$("#ui-myuldiv").css("left");
     if(divleft!="0px")
      {
      $("#ui-myuldiv").animate({ left: '+=175px' }, 300);
      $(".ui-Round .current").prev("i").addClass('show');
      $(".ui-Round i").removeClass('current')
      $(".ui-Round .show").addClass('current').removeClass('show');
      }
     
    });
    //鼠标经过圆点
    $('.ui-Round i').hover(function(){
      var ulname=$(this).attr("name")
      var ulsw=$('#'+ulname).offset().left
      var nowul=$(".ui-Round .current").attr("name")
      var nowsw=$('#'+nowul).offset().left;
      var leftshow=(ulsw-nowsw)+"px"
      $("#ui-myuldiv").animate({ left: '-='+leftshow }, 300);
      $(".ui-Round i").removeClass('current')
      $(this).addClass("current");
    });
  }
}