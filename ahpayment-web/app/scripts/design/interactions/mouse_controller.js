var viewUtil = require("common/util")
var designUtil = require("design/util")
var styleUtil = require("design/style")
var designEnv = require("design/environment")
var compManage = require("sys/toolbar/comp/view")

draggable = function (selector, $container) {
    var dragOpts = {
        containment:'document',
        grid:[8, 8],
        addClasses:false,
        opacity:0.8,
        axis:false,
        zIndex:false,

        drag:function () {
            var component_height = $(this).position().top + $(this).height();
            if(!$container){
                $container = $(this).closest('.page_part');
            }
            if (!designUtil.show_on_all_page() && component_height > $container.height()) {
                $container.height(component_height)
            }
        },

        start:function () {
        },

        stop:function () {
            if ($(this).hasClass('js-comp')) {
                styleUtil.save($(this).attr('id'), 'top', $(this).css('top'));
                styleUtil.save($(this).attr('id'), 'left', $(this).css('left'));
                if(!$container){
                    $container = $(this).closest('.page_part');
                }
                viewUtil.elasticHeight($container);
                styleUtil.save($container.attr('id'), 'height',  $container.height() + 'px');
            }
        }
    };

    if ($container) {
        var x1 = $container.offset().left;
        var y1 = $container.offset().top;
        $.each($(selector, $container), function () {
            var x2 = $container.offset().left + $container.width() - $(this).width();
            dragOpts['containment'] = [x1, y1, x2, 10000];
            $(this).draggable(dragOpts)
        });
    } else {
        $(selector).draggable(dragOpts);
    }
};

draggableForRowBox = function (selector) {
    var dragOpts = {
        containment:'document',
        grid:[8, 8],
        addClasses:false,
        opacity:0.8,
        axis:'y',
        zIndex:1,

        drag:function () {
            var component_height = $(this).position().top + $(this).height();

            var page_part = $(this).closest('.page_part');
            if (!designUtil.show_on_all_page() && component_height > page_part.height()) {
                page_part.height(component_height)
            }
        },

        start:function () {
        },

        stop:function () {
            if ($(this).hasClass('js-comp')) {
                styleUtil.save($(this).attr('id'), 'top', $(this).css('top'));
                styleUtil.save($(this).attr('id'), 'left', $(this).css('left'));
                var page_part = $(this).closest('.page_part');
                viewUtil.elasticHeight(page_part);
                styleUtil.save(page_part.attr('id'), 'height',  page_part.height() + 'px');
            }
        }
    };

    $(selector).draggable(dragOpts);
};

droppable = function (selector) {
    var dropOpts = {
        greedy:true,
        tolerance:'pointer',

        drop:function (evt, ui) {
            var d = ui.draggable;
            var id = d.attr('id');
            var inner = $('> div.inner', this);
            if (d.parent()[0] == inner[0] || !d.hasClass('js-comp')) {
                return;
            }
            var o1 = ui.offset,
                o2 = inner.offset();
            if (!d.hasClass('rowbox')) {
                styleUtil.save_and_enable(id, 'top', viewUtil.mod_8(o1.top - o2.top));
                styleUtil.save_and_enable(id, 'left', viewUtil.mod_8(o1.left - o2.left));
                d.appendTo(inner);
            } else {
                if ($(this).hasClass('page_part')) {
                    styleUtil.save_and_enable(id, 'top', viewUtil.mod_8(o1.top - $(this).offset().top));
                    styleUtil.save_and_enable(id, 'left', '0px');
                    d.appendTo($(this));
                } else {
                    styleUtil.save_and_enable(id, 'top', viewUtil.mod_8(o1.top - $(this).closest('.page_part').offset().top));
                    styleUtil.save_and_enable(id, 'left', '0px');
                    d.appendTo($(this).closest('.page_part'));
                }
            }
            styleUtil.save(id, 'parentId', d.closest('.js-comp').attr('id'));
        },

        over:function (evt, ui) {
            var h = ui.helper;
            var d = ui.draggable;
            h.css('z-index', parseInt($(this).css('z-index')) + 1);
            d.data('outable', false);
            designUtil.show_attach(evt, d);
        },

        deactivate:function (evt) {
            designUtil.remove_attach(evt);
        },

        out:function (evt, ui) {
            var d = ui.draggable;

            var inner = $('> div.inner', this);
            if (d.parent()[0] == inner[0]) {
                d.data('outable', true)
            }
            designUtil.remove_attach(evt);
        }
    };

    return $(selector).droppable(dropOpts);
};

resizable = function (evt, handles, containment, noMenu) {
    evt.stopPropagation();
    if (!designUtil.attach_edit_handle(evt, noMenu)) {
      return;
    }
    handles = handles || 'n, e, s, w, se, sw, ne, nw';
    if (handles === "null") {
      return;
    }
    containment = containment || 'document';

    var resizeOpts = {
      handles:handles,
      containment:containment,
      grid:[8, 8],
      resize:function (evt) {
        $('#edit-handler .info').html(evt.target.clientWidth + 'x' + evt.target.clientHeight);
        if ($(this).hasClass('page_part')) {
          var max_component = _.max($('.rowbox, .inner>div', this), function (x) {
            return $(x).position().top
          });
          var max_component_height = $(max_component).position().top + $(max_component).height();
          if ($(this).height() <= max_component_height) {
            $(this).height(max_component_height)
          }
        }
        if ($(this).hasClass('button')) {
          $(this).css("line-height", $(this).height() + 'px')
        }
      },
      stop:function () {
        var container = $(this).closest('.page_part');
        if (handles.indexOf('e') < 0 || handles.indexOf('w') < 0) {
          $(this).css("width", '');
        }
        if(!$(this).hasClass('rowbox')&&!$(this).hasClass('page_part')){
          styleUtil.save($(this).attr('id'),'width',$(this).css('width'));
        }
        styleUtil.save($(this).attr('id'),'height',$(this).css('height'));
        styleUtil.save($(this).attr('id'),'top',$(this).css('top'));
        styleUtil.save($(this).attr('id'),'left',$(this).css('left'));

        if(compManage.is_not_page_part()){
          viewUtil.elasticHeight(container);
          styleUtil.save($(container).attr('id'), 'height', $(container).height() + 'px' );
        }
      }
    };
    $(evt.currentTarget).resizable(resizeOpts);
};

scrollable = function (selector) {
    var scroll_height = $(selector).height() + 20 + 'px';
    $(selector).slimScroll({ height: scroll_height, size: '5px' });
}

module.exports = {
    draggable: draggable,
    draggableForRowBox: draggableForRowBox,
    droppable: droppable,
    resizable: resizable,
    scrollable: scrollable
}
