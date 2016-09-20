$('.ui-helpselect').hover(function(){
      $(this).children('div').show();
      $(this).children('i').addClass("i-hover");
    }, function(){
      $(this).children('div').hide();
      $(this).children('i').removeClass("i-hover");
    });