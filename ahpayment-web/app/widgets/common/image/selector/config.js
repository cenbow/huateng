viewUtil = require("common/util")

okCallback = undefined

_init = function () {
    var that = this;
    var panel = '#js-image-select-panel';
    $('#image-upload-btn').click(function () {
        $('#image-upload').trigger('click');
    });

    $(document).on('click', panel + ' img', function(){
        $('#js-image-select-panel img.active').removeClass('active');
        $(this).addClass('active');
    })

    $('#js-image-choose-ok').click(function () {
        okCallback($('#js-image-select-panel img.active').attr('src'));
        $(panel).remove();
    });

    $(panel + ' button.cancel').click(function () {
        $(panel + ' .js-dialog-close').trigger('click');
    });
},

_choose_image = function () {
    $('#image-upload').fileupload({
        url:'/images/upload',
        dataType:'json',
        done:function (/*e, data*/) {
            $('#image-list').empty();
            viewUtil.magic_render('#image-list', 'common/image/selector/image_list', '/images/user?p=1&size=18', function () {
                $('#image-list>image:first-child').trigger('click');
            });
        }
    });

    var total = $('#image-pagination').attr('data-total');
    _show_pagination(total);
    _init();
},

_show_pagination = function (total) {
    $('#image-pagination').pagination(total, {
        num_display_entries:5, //显示多少个连续的页码,在...之间的连续
        num_edge_entries:2, //一般说来,开头和结尾各留2个页码
        prev_text:'上一页',
        next_text:'下一页',
        items_per_page:18,
        callback:function (page_id) {
            $('#image-list').empty();
            viewUtil.magic_render('#image-list', 'common/image/selector/image_list', '/images/user?p=' + (page_id + 1) + '&size=18');
        }
    });
}

module.exports = {
    show_change_image:function (evt, callback) {
        okCallback = callback;
        var position = $('.toolbar').size() > 0 ? '.toolbar' : 'body';
        viewUtil.magic_render(position, 'common/image/selector/view', '/images/user?p=1&size=18', function ($view) {
            _choose_image();
            $view.draggable();
        })
    }
}
