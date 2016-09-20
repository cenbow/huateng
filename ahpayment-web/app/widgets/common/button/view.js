module.exports = {
  init: function() {
    var data = $.parseJSON($(this).attr('data-behavior')) || {};

    // init status
    if (data != null) {
      $('.content', this).text(data.content);
      $(this).css('color', data['text']);
      $(this).css('font-size', data['fontsize'] + 'px')
      $(this).css('line-height', $(this).height() + 'px')
      $(this).css('background-color', data['bg']);
    }

    // default status
    $(this).mouseleave(function() {
      $(this).css('color', data['text']);
      $(this).css('background-color', data['bg'])
    });

    // hover status
    $(this).mouseover(function() {
      $(this).css('color', data['text-hover']);
      $(this).css('background-color', data['bg-hover'])
    })

    // click event
    $(this).click(function() {
      if(data['href']!=null){
        window.location = data['href']
      }
    });
  }
};
