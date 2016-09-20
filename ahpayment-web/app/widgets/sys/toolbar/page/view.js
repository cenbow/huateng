var viewUtil = require("common/util")

openSetting = function (evt) {
  evt.stopPropagation();
  $('div.setting', $(evt.currentTarget).parent().siblings()).addClass('hide');
  var page_id = $(evt.currentTarget).parents('li').attr('data-page-id');
  $('#js-add-page-panel').remove();
  viewUtil.magic_render('.toolbar', 'sys/toolbar/page/edit', '/design/page/edit/' + page_id);
},

newPage = function (evt) {
  evt.stopPropagation();
  $('#js-add-page-panel').remove();
  viewUtil.magic_render('.toolbar', 'sys/toolbar/page/edit');
},

changePage = function (evt) {
  evt.stopPropagation();
  var page_id = $(evt.currentTarget).attr('data-page-id');
  $('#js-page-list>li.active').removeClass('active');
  $(evt.currentTarget).addClass('active');
  viewUtil.fetch('/design/body/' + page_id, function (data) {
    var title = $("span", evt.currentTarget).get(0).innerHTML.replace(/<div.*div>/g, '');
    History.pushState({id: page_id}, title, decodeURIComponent('/design/' + page_id))
  })
},

savePage = function (evt) {
  evt.stopPropagation();
  var page_id = $('#page-form-id').val();
  viewUtil.create('/design/page/pages/' + $('#siteInstanceId').val(), $('#js-page-form').serialize(), function (data) {
    if (page_id === '' || page_id === undefined) {
      $('#js-page-list').append('<li data-page-id="' + data.data['id'] + '">' + data.data['name'] +
        '<div class="setting hide"><div class="icon-setting-small"></div>设置</div>');
    } else {
      $('#js-page-list > li[data-page-id="' + page_id + '"] span').get(0).innerHTML = $('#page-form-name').val();
    }
    $('#js-add-page-panel').remove();
  })

},

deletePage = function (evt) {
  evt.stopPropagation();
  var page_id = $('#page-form-id').val();
  if (page_id != null && page_id != '') {
    viewUtil.del('/design/page/' + page_id, function () {
      $('#js-page-list > li[data-page-id="' + page_id + '"]').remove();
      $('#js-add-page-panel').remove();
    });
  }
}

$(document).on('mouseenter', '#js-page-list li', function() {
  $('div.setting', this).removeClass('hide');
})

$(document).on('mouseleave', '#js-page-list li', function() {
  if ($(this).attr('data-page-id') != $('#page-form-id').val()) {
    $('div.setting', this).addClass('hide');
  }
})

$(document).on('click', '#js-page-list div', function(evt) {
  openSetting(evt)
})

$(document).on('click', '#js-page-list li', function(evt) {
  changePage(evt)
})

$(document).on('click', '#js-new-page', function(evt) {
  newPage(evt)
})

$(document).on('click', '#js-save-page', function(evt) {
  savePage(evt)
})

$(document).on('click', '#js-delete-page', function(evt) {
  deletePage(evt)
})
