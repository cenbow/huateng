var cmdHelper = require("common/commonjs_helper")
var designEnv = require("design/environment")
var viewUtil = require("common/util")
var styleUtil = require("design/style")
var compManage = require("sys/toolbar/comp/view")

module.exports = {
    attach_edit_handle: function (evt, noMenu) {
        var $t = $(evt.currentTarget);

        if (designEnv.beforeSelectedId != null) {
            this.remove_handler();
        }

        if (!$t.hasClass('page_part')) {
            viewUtil.magic_render($t, 'sys/editHandler/view-with-handler', function () {
                $('#edit-handler .info', $t).html($t.width() + 'x' + $t.height());
            });

            // have mouse click event, different to just trigger click
            if (!noMenu && evt.pageX != undefined) {
                viewUtil.magic_render('body', 'sys/editHandler/toolbar', function () {

                    $('.icon-level-up', '.edit-bar.bar').click(function (evt) {
                        compManage.change_z_index($t.attr('id'), 1);
                        evt.stopPropagation();
                    });
                    $('.icon-level-down', '.edit-bar.bar').click(function (evt) {
                        compManage.change_z_index($t.attr('id'), -1);
                        evt.stopPropagation();
                    });
                    $('.icon-copy', '.edit-bar.bar').click(function (evt) {
                        compManage.clone_widget($t.attr('id'));
                        evt.stopPropagation();
                    });
                    $('.icon-delete', '.edit-bar.bar').click(function (evt) {
                        compManage.del_widget($t.attr('id'));
                        evt.stopPropagation();
                    });
                    // setting menu
                    $('.setting-name', '.edit-bar.bar').click(function () {
                        $t.trigger('dblclick');
                    });

                    // mouse leave edit bar
                    $('.edit-bar.bar').mouseleave(function () {
                        $(this).fadeOut(300, function () {
                            $(this).remove();
                        });
                    });

                    $('.fixed-position').click(function () {
                        $('#position').trigger('click');
                    });

                    $('.show-on-all-page').click(function () {
                        $('#s_o_a_p').trigger('click')
                    });

                    //position : absolute or fixed
                    $('#position', '.edit-bar').change(function () {
                        var position = $(this).prop('checked') ? 'fixed' : 'absolute';
                        $t.css('position', position);
                        styleUtil.save($t.attr('id'), 'position', position);
                    });

                    //show on all page
                    $('#s_o_a_p', '.edit-bar').change(function () {
                        var order = $(this).prop('checked') ? '10' : '';
                        $t.css('order', order);
                        styleUtil.save($t.attr('id'), 'order', order);
                    });

                    //component name
                    $('.edit-bar .comp-name').html($t.data("compPath"));

                    //position : absolute or fixed
                    $('#position', '.edit-bar').attr('checked', $t.css('position') == 'fixed');

                    //show on all page
                    $('#s_o_a_p', '.edit-bar').attr('checked', $t.css('order') == '10');

                    // mouse position with bar
                    $('.edit-bar.bar').css({'left':evt.clientX - 20 + 'px', 'top':evt.clientY - 20 + 'px'});
                });
            }
        } else {
            viewUtil.magic_render($t, 'sys/editHandler/view', function () {
                //component name
                $('#edit-handler .edit-bar .comp-name', $t).html($t.data("data-comp-path"));
            });
        }

        designEnv.beforeSelectedId = $t.attr('id');
        evt.stopPropagation();
        return true;
    },

    remove_handler :function () {
        if ($('#' + designEnv.beforeSelectedId).hasClass("ui-resizable")) {
            $('#' + designEnv.beforeSelectedId).resizable('destroy');
        }
        $('#edit-handler', '#' + designEnv.beforeSelectedId).remove();
        $('.edit-bar.bar').remove();
    },

    show_attach :function (evt, draggable) {
        if ($(draggable).hasClass('modal')) {
            return;
        }
        $(evt.target).css('outline', '2px dashed #35EAFF');
        if (!$(evt.target).hasClass('page_part')) {
            $(evt.target).append('<button id="attach-info" class="btn btn-info btn-small" style="background: #35EAFF">吸附</button>');
        }
    },
    remove_attach :function (evt) {
        $(evt.target).css('outline', '');
        $('#attach-info', $(evt.target)).remove();

    },
    //load style from component's style attribute end.
    mode_switch :function (selector) {
        //active current mode
        var that = this
        $('.js-mode-switch img').each(function () {
            if ($(this).attr('data-mode') == that.trimClass($(selector).attr('class'))) {
                $(this).addClass('active');
                return false;
            }
        });

        //add click event
        $(".js-mode-switch img").click(function () {
            $(".js-mode-switch img.active").removeClass('active');
            $(selector).attr("class", $(this).attr('data-mode'));
            $(this).addClass('active');
            //add mode to dom ,for structure save
            $('input[name="mode"]', $(this).parents('form:eq(0)')).val($(this).attr('data-mode'));
        });
    },
    trimClass :function (class_string) {
        return class_string.replace(/\s?comp-hover/g, '').replace(/\s?ui-.*/g, '');
    },
    show_on_all_page :function () {
        return $('#' + designEnv.beforeSelectedId).css('order') == 10;
    }
}
