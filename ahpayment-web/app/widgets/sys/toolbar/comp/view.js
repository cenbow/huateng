var viewUtil = require("common/util")
var styleUtil = require("design/style")
var designEnv = require("design/environment")

module.exports = {
    init: function (in_item) {
        var that = this;
        $(document).on('click', '#js-comp-list li', function (evt) {
            that.show_components(evt)
        });

        $(document).on('click', '#js-component-go-back', function (evt) {
            $('#js-add-component-panel').remove();
        });

        $(document).on('click', '#js-comps-list li', function (evt) {
            that.add_component(evt, in_item)
        })
    },

    is_not_page_part: function () {
        return !$("#" + designEnv.beforeSelectId).hasClass('page_part');
    },

    show_components: function (evt) {
        $('#js-add-component-panel').remove();
        viewUtil.magic_render('.toolbar', 'sys/toolbar/comp/comps', '/design/component/list/' + $(evt.currentTarget).attr('data-comp-id'), function () {
            require("design/interactions/mouse_controller").scrollable('#js-add-component-panel .scrollable');
        });
    },

    add_component: function (evt, in_item) {
        if (designEnv.pageData == null) {
            designEnv.pageData = {};
        }
        var url = '/design/widget/' + $('.page_body').attr('id');//page_widget_id
        if (in_item) {
            url = '/design/widget/item/' + $('#itemId').val();  //item_id
        }
        url += '/' + $(evt.currentTarget).attr('data-comp-id');

        viewUtil.create(url, function (data) {
            var target = '.page_body>div:eq(0)';
            var left = 200;
            if (data.data.template_path == "common/container/rowbox") {
                target = '.page_body';
                left = 0;
            }
            if (data.data.template_path == "common/image/image") {
                $("<img/>").attr("src", $('img', data.data.html).attr("src"))
                    .load(function () {
                        $('#' + data.data.id).css({'width': this.width, 'height': this.height });
                        styleUtil.save(data.data.id, 'width', this.width + 'px');
                        styleUtil.save(data.data.id, 'height', this.height + 'px');
                    });
            }

            if (in_item) {
                if ($('.item-desc-root').length == 0) {
                    //第一次添加时，后台会生成item-desc-root ,直接刷新
                    window.location.reload();
                    return;
                } else {
                    target = '.item-desc-root';
                }
            }
            $(target).append(data.data.html);

            //定位
            var scroll_top = $(document).scrollTop();
            var top = scroll_top - scroll_top % 8;
            if (in_item) {
                top -= 500;
            } else {
                top += 40;
            }


            styleUtil.save_and_enable(data.data.id, 'top', top + 'px');
            styleUtil.save_and_enable(data.data.id, 'left', left + 'px');
            styleUtil.save_and_enable(data.data.id, 'position', 'absolute');

            var path = data.data.template_path.replace(/\//g, "_");
            humane.log(data.data.template_name + "已添加.");
            // $('.item-desc-root')如果没有查到，方法内会去掉对容器的限制
            require("design/comp_helper").initComponentAll(path,
                $('#' + data.data.id), $('.item-desc-root'));
            // close panel
            $('.close').trigger('click');
            // choose added component
            $('#' + data.data.id).trigger('click');
        })
    },

    change_z_index: function (id, step) {
        var target = $('#' + id);
        var z = parseInt(target.css('z-index')) + step;
        //only between 2~19
        if (z > 0 && z <= 20) {
            styleUtil.save_and_enable(id, 'z-index', z);
        }
    },

    del_widget: function (widget_id) {
        viewUtil.create('/design/widget/delete/' + widget_id, {children: this._get_children('#' + widget_id)}, {
            success: function () {
                $('#' + widget_id).remove();
                $('.edit-bar.bar').remove();
                delete designEnv.pageData[designEnv.beforeSelectId];
                designEnv.beforeSelectId = null;
                humane.log(widget_id + " 已删除.");
            }
        });
    },

    clone_widget: function (widget_id) {
        $('.page_footer').trigger('click');
        viewUtil.create('/design/widget/clone/' + widget_id, {children: this._get_children('#' + widget_id)}, {
            success: function (data) {
                var $source = $('#' + widget_id);
                var $editBar = $('.edit-bar.bar');
                $('div[id="edit-handler"]', $source).remove();
                $editBar.remove();

                var clone = $source.clone().attr('id', data.data.id).css({
                    'top': $source.position().top + 24,
                    'left': $source.position().left + 56
                });

                clone.removeClass('comp-hover');

                $source.removeClass('comp-hover');

                //oldId,newId,componentPath;oldId,newId,componentPath;...
                var idMappings = data.data.mode.split(';');
                //替换id
                _.each(idMappings, function (id_nid_comp) {
                    var _arrays = id_nid_comp.split(',');
                    $('#' + _arrays[0], clone).attr('id', _arrays[1]);
                });

                //change outer top & left
                styleUtil.save(data.data.id, 'top', $source.position().top + 24);
                styleUtil.save(data.data.id, 'left', $source.position().left + 56);


                $source.after(clone);

                //copy styles and init
                var _comps = '';
                _.each(idMappings, function (id_nid_comp) {
                    var _arrays = id_nid_comp.split(',');
                    //copy all style except parentId
                    var source_style = designEnv.pageData[_arrays[0]];
                    $.each(source_style, function (key, value) {
                        styleUtil.save(_arrays[1], key, value);
                    });
                    // save parentId
                    styleUtil.save(_arrays[1], 'parentId', _arrays[2]);
                    // $('.item-desc-root')如果没有查到，方法内会去掉对容器的限制
                    require("design/comp_helper").init_component_by_path(_arrays[3], $('#' + _arrays[1]), $('.item-desc-root'));
                    _comps += _arrays[3] + ' ';
                });
                humane.log(_comps + "已复制");
                $editBar.remove();
                $('#' + data.data.id).trigger('click');
            },
            error: function () {
            }
        });
    },
    _get_children: function (selector) {
        var children = '';
        var first = true;
        $('.js-comp', $(selector)).each(function () {
            if (first) {
                first = false;
            } else {
                children += ',';
            }
            children += $(this).attr('id');
        });
        return children;
    }
}

module.exports.init()
