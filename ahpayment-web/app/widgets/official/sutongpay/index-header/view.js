module.exports = {
  init: function() {
    $('.ui-helpselect', this).hover(function() {
      $(this).children('div').show();
      $(this).children('i').addClass("i-hover");

    }, function() {
      $(this).children('div').hide();
      $(this).children('i').removeClass("i-hover");
    });

    // 导航条二级菜单
    $('.ui-headernav-li').hover(function() {
      $(this).children('.ui-headnavPop').show();
      $(this).addClass('headernavhover')
    }, function() {
      $(this).children('.ui-headnavPop').hide();
      $(this).removeClass('headernavhover')
    });
  }
}