this.require.define({"design/comp_helper":function(exports, require, module){(function() {
  var cmdHelper, designUtil, initComponentAll, initComponentConfig, initComponentView, mouseController, openConfig;

  designUtil = require("design/util");

  mouseController = require("design/interactions/mouse_controller");

  cmdHelper = require("common/commonjs_helper");

  initComponentAll = function(path, $comp, $container) {
    initComponentView(path + "/view", $comp);
    return initComponentConfig(path + "/config", $comp, $container);
  };

  initComponentView = function(path, $comp) {
    var comp, _ref;
    if (cmdHelper.hasModule(path)) {
      comp = require(path);
      return (_ref = comp.init) != null ? _ref.call($(this)) : void 0;
    }
  };

  initComponentConfig = function(path, $comp, $container) {
    var comp, settings, _ref;
    if (cmdHelper.hasModule(path)) {
      comp = require(path);
      if ((_ref = comp.init) != null) {
        _ref.call($comp, $container);
      }
      settings = {
        draggable: true,
        resizable: true,
        resizableHandles: null,
        resizableContainer: null,
        openConfig: true,
        configPath: path,
        loadConfig: true,
        configInitParams: null
      };
      if (comp.settings) {
        $.extend(settings, comp.settings);
      }
      if (settings.draggable) {
        mouseController.draggable($comp);
      }
      if (settings.resizable) {
        $comp.click(function(evt) {
          return mouseController.resizable(evt, settings.resizableHandles, settings.resizableContainer);
        });
      }
      if (settings.openConfig) {
        return $comp.dblclick(function(evt) {
          return openConfig(evt, settings.configPath, settings.loadConfig, settings.configInitParams);
        });
      }
    }
  };

  openConfig = function(evt, configPath, loadConfig, configInitParams) {
    var url;
    evt.stopPropagation();
    $(designEnv.beforeConfigDialog).remove();
    $("#js-image-select-panel").remove();
    if (loadConfig) {
      url = "/design/widget/param/" + designEnv.beforeSelectedId;
    }
    return viewUtil.magic_render(".toolbar", configPath, url, function($configDialog, data) {
      var model, widget;
      designEnv.beforeConfigDialog = $configDialog;
      if (cmdHelper.hasModule(configPath)) {
        widget = require(configPath);
        model = void 0;
        if (widget.model) {
          if (!widget.models) {
            widget.models = {};
          }
          if (!widget.models[designEnv.beforeSelectedId]) {
            widget.models[designEnv.beforeSelectedId] = $.extend(true, {}, widget.model);
          }
          data = data || {};
          model = $.extend(true, widget.models[designEnv.beforeSelectedId], data["data"]);
          rivets.bind($configDialog, model);
          $(".modal-body", $configDialog).slimScroll({
            height: $configDialog.height() - 60 + "px"
          });
          $(".js-submit", $configDialog).click(function(evt) {
            viewUtil.update("/design/widget/param/" + designEnv.beforeSelectedId, {
              data: JSON.stringify({
                data: model
              })
            });
            return $configDialog.remove();
          });
        }
        if (widget.configInit) {
          widget.configInit.call($("#" + designEnv.beforeSelectedId), $configDialog, model, configInitParams);
        }
      }
      return mouseController.draggable($configDialog);
    });
  };

  module.exports = {
    initComponentAll: initComponentAll,
    initComponentView: initComponentView,
    initComponentConfig: initComponentConfig,
    openConfig: openConfig
  };

}).call(this);
;}});
this.require.define({"design/environment":function(exports, require, module){(function() {
  module.exports = {
    beforeSelectId: void 0,
    beforeCopiedId: void 0,
    pageData: void 0,
    globalSettingChanged: void 0
  };

}).call(this);
;}});
this.require.define({"design/extras/collapse":function(exports, require, module){(function() {
  $(document).on('show', '#style-accordion', function(evt) {
    $('.accordion-heading .flag', $(evt.target).parent()).removeClass('icon-flag-close');
    return $('.accordion-heading .flag', $(evt.target).parent()).addClass('icon-flag-open');
  });

  $(document).on('hide', '#style-accordion', function(evt) {
    $('.accordion-heading .flag', $(evt.target).parent()).removeClass('icon-flag-open');
    return $('.accordion-heading .flag', $(evt.target).parent()).addClass('icon-flag-close');
  });

}).call(this);
;}});
this.require.define({"design/init":function(exports, require, module){(function() {
  $(function() {
    var cmdHelper, compHelper, designEnv, designUtil, initHelper, initialize, requireAll;
    require("common/init");
    designEnv = require("design/environment");
    designUtil = require("design/util");
    initHelper = require("common/initialize_helper");
    compHelper = require("design/comp_helper");
    cmdHelper = require("common/commonjs_helper");
    requireAll = cmdHelper.requireByRegex;
    requireAll(/^design\/extras\//);
    require("design/interactions/key_controller");
    $(document).on("click", ".app-modal .close, .cancel", function() {
      if ($(this).hasClass("js-close-self")) {
        return $(this).parents(".app-modal").remove();
      } else {
        return $(".app-modal").hide();
      }
    });
    $(document).on("click", ".modal .close, .cancel", function() {
      return $(this).parents(".modal").remove();
    });
    $(document).on("click", "#js-change-style", function(evt) {
      return compHelper.openConfig(evt, "sys/style/view");
    });
    $(document).on("click", "#js-change-style-no-bg", function(evt) {
      return compHelper.openConfig(evt, "sys/style/no_bg");
    });
    $(document).on("click", "a", function(evt) {
      return evt.preventDefault();
    });
    initialize = function() {
      $(".js-comp").each(function() {
        var path;
        path = $(this).data("compPath") + "/config";
        return compHelper.initComponentConfig(path, $(this));
      });
      designEnv.pageData = $.parseJSON($('#pageData').val()) || {};
      _.each($.parseJSON($('#headFootData').val()), function(v, k) {
        return designEnv.pageData[k] = v;
      });
      initHelper.initializeAll();
      return $(".page a, .page button").click(function(evt) {
        return evt.preventDefault();
      });
    };
    History.Adapter.bind(window, 'statechange', function() {
      var State;
      State = History.getState();
      return $.get('/design/body/' + State.data['id'], function(data) {
        return $('.page_body').hide('slide', {
          direction: 'left'
        }, function() {
          $(this).remove();
          $('.page_header').after(data).show('slide', {
            direction: 'right'
          });
          return initialize();
        });
      });
    });
    initialize();
    return _.each(['4bar', 'comp', 'design', 'page'], function(component) {
      return require("sys/toolbar/" + component + "/view");
    });
  });

}).call(this);
;}});
this.require.define({"design/interactions/key_controller":function(exports, require, module){styleUtil = require("design/style")
designEnv = require("design/environment")
compManage = require("sys/toolbar/comp/view")

key('up, down, left, right, shift+up, shift+down, shift+left, shift+right', 'control', function(event, handler){
  var dom = document.getElementById(designEnv.beforeSelectedId);
  if ($(dom) == undefined || $(dom).hasClass('page_part')){
    return false;
  };
  var increment = 8
  if (key.shift) {
    increment = 1
  }
  if (handler.shortcut.indexOf("up") != -1) {
    dom.style.top = dom.offsetTop - increment + "px";
    styleUtil.save(designEnv.beforeSelectedId,'top',dom.style.top);
  } else if(handler.shortcut.indexOf("down") != -1) {
    dom.style.top = dom.offsetTop + increment + "px";
    styleUtil.save(designEnv.beforeSelectedId,'top',dom.style.top);
  } else if(handler.shortcut.indexOf("left") != -1) {
    dom.style.left = dom.offsetLeft - increment + "px";
    styleUtil.save(designEnv.beforeSelectedId,'left',dom.style.left);
  } else {
    dom.style.left = dom.offsetLeft + increment + "px";
    styleUtil.save(designEnv.beforeSelectedId,'left',dom.style.left);
  }
  return false;
});

key('⌘+c, ctrl+c', 'control', function(event, handler){
  if(compManage.is_not_page_part()){
    designEnv.beforeCopiedId = designEnv.beforeSelectedId;
  }
  return false;
})

key('⌘+v, ctrl+v', 'control', function(event, handler){
  if(compManage.is_not_page_part()){
    compManage.clone_widget(designEnv.beforeCopiedId);
  }
  return false;
})

key('⌘+s, ctrl+s', 'control', function(event, handler){
  $('#js-page-structure-save').trigger('click');
  return false;
})

key('⇧+-, ⇧+=', 'control', function(event, handler){
  if(compManage.is_not_page_part()){
    if (handler.shortcut == "⇧+-"){
      compManage.change_z_index(designEnv.beforeSelectedId, -1);
    }else {
      compManage.change_z_index(designEnv.beforeSelectedId, 1);
    }
  }
  return false;
})

key('⌘+o, ctrl+o', 'control', function(event, handler){
  $('.comp-menu').trigger('click');
  return false;
})

key('backspace, del, delete', 'control', function(event, handler){
  if(compManage.is_not_page_part()){
    compManage.del_widget(designEnv.beforeSelectedId);
  }
  return false;
})

key('esc, escape', 'control', function(event, handler){
  $('.cancel, .close').trigger('click');
  return false;
})

key.setScope('control')
;}});
this.require.define({"design/interactions/mouse_controller":function(exports, require, module){var viewUtil = require("common/util")
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
;}});
this.require.define({"design/style":function(exports, require, module){designEnv = require("design/environment")
viewUtil = require("common/util")

_collect_and_enable2css = function (argument, callback) {
    var _style = '';
    var key = $(argument).attr("id");
    //collection all field in same parent and set to css
    _.each(_.sortBy($('input[type="text"], input[type="number"], input[type="hidden"]', argument), function (input) {
        return $(input).attr('name');
    }), function (input) {
        _style += $(input).val() + ((isNaN(parseInt($(input).val())) || $(input).hasClass('no-px')) ? ' ' : 'px ');
    });
    _style = $.trim(_style);
    if (key.indexOf("background-color") >= 0) {
        _style = _style.split(' ');
        var rgb = viewUtil.hex2Object(_style[0]);
        _style = 'rgba(' + rgb.r + ',' + rgb.g + ',' + rgb.b + ',' + (_style[1] ? (_style[1].replace(' ', '') / 10).toFixed(1) : 1) + ')';
    }
    if (callback) {
        callback(_style);
    } else {
        save_and_enable(designEnv.beforeSelectedId, key, _style);
    }
}

load_from_dom = function (panel) {
    this.load_value_to_argument('box-shadow', false);
    this.load_value_to_argument('border-radius', false);
    this.load_value_to_argument('border', false);
    this.load_value_to_argument('background-color', false);

    if ($('#background-color', panel).size() >= 0) {
        var obj = designEnv.pageData[designEnv.beforeSelectedId];
        if (obj) {
            //background-image
            if (obj['background-image']) {
                $('#bg-image', panel).attr('src', obj['background-image'].replace('url(', '').replace(')', ''));
            }
            //background-repeat
            $('input[type=radio]', $('#background-repeat', panel)).each(function () {
                if (obj['background-repeat'] == $(this).val()) {
                    $(this).attr('checked', 'checked');
                }
            });
            //background-position
            $('.position', $('#background-position', panel)).each(function () {
                if ($(this).attr('data') == obj['background-position']) {
                    $(this).addClass('selected');
                }
            });
        }
    }
    $('.js-arguments', panel).each(function () { //initialize slider color_picker and spinner
        init_slider(this);
        init_color_picker(this);
        init_spinner(this);
    });
}

load_value_to_argument = function (key, data) {
    var value = data || designEnv.pageData[designEnv.beforeSelectedId];
    if (value) {
        value = value[key];
    } else {
        return;
    }
    if (value) {
        if (key.indexOf('background-color') >= 0) {
            //background-color
            var bg_color = viewUtil.rgb2Object(value);
            if (bg_color) {
                $('.bg-color', $('#' + key)).val(bg_color.color);
                $('.bg-transparent', $('#' + key)).val(bg_color.transparent ? bg_color.transparent : 10);
            }
        } else {
            value = value.split(' ');
            if (value != null && value != 'none') {   //all input
                $('input[type="text"],input[type="hidden"]', $('#' + key)).each(function () {
                    var _val = value.length == 1 ? value[0] : value[parseInt($(this).attr("name"))];
                    if (_val) {
                        $(this).val(_val.replace('px', ''));
                    }
                });
            }
        }
    }
}

init_slider = function (argument, callback) {
    $('.js-slider', argument).each(function () {
        var $field = $('input[type="text"]', $(this).parents('.row-fluid:eq(0)'));
        var value = $field.val();
        $(this).slider({
            range:"min",
            //set value to slider
            value:value,
            min:parseInt($field.attr('data-min')),
            max:parseInt($field.attr('data-max')),
            slide:function (event, ui) {
                $field.val(ui.value); //change field value
                _collect_and_enable2css(argument, callback);
            }
        })
    })
}

init_color_picker = function (argument, callback) {
    $(".js-color-picker", argument).minicolors({
        letterCase:'uppercase',
        change:function (/*hex, rgb*/) {
            _collect_and_enable2css(argument, callback);
        }
    })
}

init_spinner = function (argument, callback) {
    $('.number', argument).each(function () {
        var $field = $('input[type="text"]', $(this).parents('.row-fluid:eq(0)'));
        $(this).spinner({
            min:parseInt($field.attr('data-min')),
            max:parseInt($field.attr('data-max')),
            spin:function () {
                if ($('#link_all').prop('checked')) { //others
                    $('.number', '#border-radius').val($(this).val());
                }
                _collect_and_enable2css(argument, callback);
            },
            stop:function () {
                if ($('#link_all').prop('checked')) { //others
                    $('.number', '#border-radius').val($(this).val());
                }
                //change slider
                $(".js-slider", $(this).parents('.row-fluid:eq(0)')).slider("value", $(this).val());

                _collect_and_enable2css(argument, callback);
            }
        });
    })
}

save_and_enable = function (id, key, style) {
    this.save(id, key, style);
    $('#' + id).css(key, style); //enable to css
    if ($('#' + id).hasClass('image') && key == "border-radius") {
        $('img', $('#' + id)).css(key, style);
    }
}

save = function (id, key, style) {
    if (designEnv.pageData[id] == undefined) {
        designEnv.pageData[id] = {};
    }
    designEnv.pageData[id][key] = style;
}

module.exports = {
  load_from_dom: load_from_dom,
  load_value_to_argument: load_value_to_argument,
  init_slider: init_slider,
  init_spinner: init_spinner,
  save_and_enable: save_and_enable,
  save: save
}
;}});
this.require.define({"design/util":function(exports, require, module){var cmdHelper = require("common/commonjs_helper")
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
;}});
this.require.define({"common/button/config":function(exports, require, module){module.exports = {
    settings: {
        loadConfig: false
    },

    init: function() {
        // only init needed configuration
        var data = $.parseJSON($(this).attr('data-behavior'));
        if (data != null) {
            $('.content', this).text(data.content);
            $(this).css('color', data['text']);
            $(this).css('font-size', data['fontsize'] + 'px')
            $(this).css('line-height', $(this).height() + 'px')
            $(this).css('background-color', data['bg']);
        }
    },
    configInit: function() {
        var viewUtil = require("common/util")
        var $t = $(this);

        viewUtil.setFormWithJSON('#js-button-settings-form', $.parseJSON($t.attr('data-behavior')));

        $(".js-color-picker", '#js-button-settings-form').minicolors({
            letterCase:'uppercase'
        });

        $('#js-button-ok').click(function () {

            var json = viewUtil.serializeFormJSON($('#js-button-settings-form'));

            $('.content', $t).text(json['content']);
            $('.content', $t).attr('data-href', json['href']);
            $t.css('font-size', json['fontsize'] + "px");
            $t.css('line-height', $t.height() + "px");
            $t.css('color', json['text']);
            $t.css('background-color', json['bg']);

            var data = viewUtil.stringify(json);
            $t.attr('data-behavior', data);
            viewUtil.update('/design/widget/behavior/' + $t.attr("id"), {data:data}, function () {
                $('#js-button-setting-panel').remove();
            });
        })
    }
}
;}});
this.require.define({"common/container/box/config":function(exports, require, module){module.exports = {
    settings: {
        configPath: "sys/style/view",
        loadConfig: false
    },

    init: function() {
        var mouseController = require("design/interactions/mouse_controller")
        mouseController.droppable(this);
    }
}
;}});
this.require.define({"common/container/rowbox/config":function(exports, require, module){module.exports = {
    settings: {
        resizableHandles: "s,n",
        configPath: "sys/style/view",
        loadConfig: false
    }
}
;}});
this.require.define({"common/grid/config":function(exports, require, module){module.exports = {
    configInit: function() {
        var viewUtil = require("common/util")
        var styleUtil = require("design/style")

        var targetId = $(this).attr("id")
        $("#js-grid-ok").click(function (evt) {
            evt.preventDefault();
            //m X n
            var params = {
                'image':imageUrl,
                'link':$("#js-image-setting-form input[name='link']").val()
            };
            viewUtil.update('/design/widget/param/' + targetId, {data:viewUtil.stringify(params)}, function () {
                $("<img/>").attr("src", imageUrl)
                    .load(function () {
                        $("img", "#" + targetId).attr("src", imageUrl);
                        $("#" + targetId).css({'width':this.width, 'height':this.height })
                        styleUtil.save(targetId, 'width', this.width + 'px');
                        styleUtil.save(targetId, 'height', this.height + 'px');
                    });
                $('#js-image-config-panel').remove();
            });
        })
    }
}
;}});
this.require.define({"common/image/image/config":function(exports, require, module){_save_change = function () {
    viewUtil = require("common/util")
    styleUtil = require("design/style")
    designEnv = require("design/environment")
    var imageUrl = $("#param-image-url").attr("src");
    var params = {
        'image':imageUrl,
        'link':$("#js-image-setting-form input[name='link']").val()
    };
    viewUtil.update('/design/widget/param/' + designEnv.beforeSelectedId, {data: viewUtil.stringify(params)}, function () {
        $("<img/>").attr("src", imageUrl)
            .load(function () {
                $("img", "#" + designEnv.beforeSelectedId).attr("src", imageUrl);
                $("#" + designEnv.beforeSelectedId).css({'width':this.width, 'height':this.height })
                styleUtil.save(designEnv.beforeSelectedId, 'width', this.width + 'px');
                styleUtil.save(designEnv.beforeSelectedId, 'height', this.height + 'px');
            });
        $('#js-image-config-panel').remove();
    });
}

module.exports = {
    configInit: function($panel) {
        $('#js-change-image', $panel).click(function (evt) {
            require("common/image/selector/config").show_change_image(evt, function (image_url) {
                $("#param-image-url").attr("src", image_url);
            })
        });
        $("#js-image-ok", $panel).click(function (evt) {
            evt.preventDefault();
            _save_change();
        })
    }
}
;}});
this.require.define({"common/image/selector/config":function(exports, require, module){viewUtil = require("common/util")

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
;}});
this.require.define({"common/image/slider/config":function(exports, require, module){module.exports = {
    settings: {
        configPath: "common/image/slider",
        loadConfig: false
    },

    configInit: function($panel) {
        var viewUtil = require("common/util")
        var designUtil = require("design/util")
        var styleUtil = require("design/style")

        designUtil.mode_switch($(this))
        $('#js-change-image', $panel).click(function(evt) {
            require("design/comp_helper").openConfig(evt, 'common/image/slider/data_config', true)
        })
        //load data form 'data-behavior' and set form
        viewUtil.setFormWithJSON($panel, $.parseJSON($(this).attr('data-behavior')));

        styleUtil.init_slider($panel);

        $target = $(this)
        $('#js-ok').click(function () {
            var form_json_data = viewUtil.serializeFormJSON($('#js-image-slider-config-form'));
            form_json_data = viewUtil.stringify(form_json_data);
            $target.attr('data-behavior', form_json_data);
            viewUtil.update('/design/widget/behavior/' + $target.attr("id"), {data:form_json_data}, function () {
                $panel.remove();
            });
        })
    }
}
;}});
this.require.define({"common/line/h/config":function(exports, require, module){(function() {
  module.exports = {
    settings: {
      resizableHandles: "e,w",
      loadConfig: false
    },
    configInit: function($panel) {
      var $t, styleUtil;
      styleUtil = require("design/style");
      $t = $(this);
      styleUtil.load_value_to_argument("border-top");
      return $(".js-arguments", $panel).each(function() {
        styleUtil.init_slider(this);
        return styleUtil.init_color_picker(this);
      });
    }
  };

}).call(this);
;}});
this.require.define({"common/line/v/config":function(exports, require, module){(function() {
  module.exports = {
    settings: {
      resizableHandles: "s,n",
      loadConfig: false
    },
    configInit: function($panel) {
      var $t, styleUtil;
      styleUtil = require("design/style");
      $t = $(this);
      styleUtil.load_value_to_argument("border-left");
      return $(".js-arguments", $panel).each(function() {
        styleUtil.init_slider(this);
        return styleUtil.init_color_picker(this);
      });
    }
  };

}).call(this);
;}});
this.require.define({"common/media/video/config":function(exports, require, module){(function() {
  module.exports = {
    settings: {
      loadConfig: false
    },
    configInit: function($panel) {
      var $target, viewUtil;
      viewUtil = require("common/util");
      $target = $(this);
      $("#js-video-src").val($("iframe", $target).attr("src"));
      return $("#js-video-ok").click(function(evt) {
        var video_source;
        video_source = $("#js-video-src").val();
        return viewUtil.update("/design/widget/param/" + $target.attr("id"), {
          data: viewUtil.stringify({
            src: video_source
          })
        }, function() {
          $("iframe", $target).attr("src", video_source);
          return $panel.remove();
        });
      });
    }
  };

}).call(this);
;}});
this.require.define({"common/navbar/config":function(exports, require, module){module.exports = {
    settings: {
        loadConfig: false
    },

    configInit: function() {
        var viewUtil = require("common/util")
        var styleUtil = require("design/style")

        var container = $(this);
        var _DATA = $.parseJSON($(container).attr('data-behavior')) || {};
        _.each(['font-size', 'font-color', 'background-color', 'hover-font-color', 'hover-background-color',
            'active-font-color', 'active-background-color', 'margin-right', 'padding-top', 'padding-bottom',
            'border-radius'], function (key) {
            styleUtil.load_value_to_argument(key, _DATA);
        });
        //load font-size
        styleUtil.load_value_to_argument('font-size');

        //font-size 这个值统一处理掉
        styleUtil.init_slider('#font-size');
        styleUtil.init_spinner('#font-size');

        //default-font-color
        styleUtil.init_color_picker('#font-color', function (value) {
            $('li:not(.active) a', container).css('color', value);
            _DATA['font-color'] = value;
        });
        //default-background-color
        styleUtil.init_color_picker('#background-color', function (value) {
            $('li:not(.active)', container).css('background-color', value);
            _DATA['background-color'] = value;
        });
        styleUtil.init_slider('#background-color', function (value) {
            $('li:not(.active)', container).css('background-color', value);
            _DATA['background-color'] = value;
        });
        styleUtil.init_spinner('#background-color', function (value) {
            $('li:not(.active)', container).css('background-color', value);
            _DATA['background-color'] = value;
        });

        //hover-font-color
        styleUtil.init_color_picker('#hover-font-color', function (value) {
            _DATA['hover-font-color'] = value;
        });
        //hover-background-color
        styleUtil.init_color_picker('#hover-background-color', function (value) {
            _DATA['hover-background-color'] = value;
        });
        styleUtil.init_slider('#hover-background-color', function (value) {
            _DATA['hover-background-color'] = value;
        });
        styleUtil.init_spinner('#hover-background-color', function (value) {
            _DATA['hover-background-color'] = value;
        });

        //active-font-color
        styleUtil.init_color_picker('#active-font-color', function (value) {
            _DATA['active-font-color'] = value;
        });
        //active-background-color
        styleUtil.init_color_picker('#active-background-color', function (value) {
            _DATA['active-background-color'] = value;
        });
        styleUtil.init_slider('#active-background-color', function (value) {
            _DATA['active-background-color'] = value;
        });
        styleUtil.init_spinner('#active-background-color', function (value) {
            _DATA['active-background-color'] = value;
        });

        //margin-right
        styleUtil.init_slider('#margin-right', function (value) {
            $('li', container).css('margin-right', value);
            _DATA['margin-right'] = value;
        });
        styleUtil.init_spinner('#margin-right', function (value) {
            $('li', container).css('margin-right', value);
            _DATA['margin-right'] = value;
        });

        //padding-top
        styleUtil.init_slider('#padding-top', function (value) {
            $('li', container).css('padding-top', value);
            _DATA['padding-top'] = value;
        });
        styleUtil.init_spinner('#padding-top', function (value) {
            $('li', container).css('padding-top', value);
            _DATA['padding-top'] = value;
        });

        //padding-bottom
        styleUtil.init_slider('#padding-bottom', function (value) {
            $('li', container).css('padding-bottom', value);
            _DATA['padding-bottom'] = value;
        });
        styleUtil.init_spinner('#padding-bottom', function (value) {
            $('li', container).css('padding-bottom', value);
            _DATA['padding-bottom'] = value;
        });
        //border-radius
        styleUtil.init_spinner('#border-radius', function (value) {
            $('li', container).css('border-radius', value);
            _DATA['border-radius'] = value;
        });
        $('#js-navbar-ok').click(function () {
            var data = viewUtil.stringify(_DATA);
            $(container).attr('data-behavior', data);
            viewUtil.update('/design/widget/behavior/' + $(container).attr("id"), {data:data}, function () {
                $('#js-navbar-setting-panel').remove();
            });
        });
    }
}
;}});
this.require.define({"common/page/body/config":function(exports, require, module){(function() {
  module.exports = {
    settings: {
      draggable: false,
      resizable: false,
      openConfig: false
    },
    init: function() {
      var designEnv;
      designEnv = require("design/environment");
      require("design/interactions/mouse_controller").droppable($(this));
      return $(this).click(function() {
        if (designEnv.beforeSelectedId) {
          require("design/util").remove_handler();
          return designEnv.beforeSelectedId = null;
        }
      });
    }
  };

}).call(this);
;}});
this.require.define({"common/page/footer/config":function(exports, require, module){(function() {
  module.exports = {
    settings: {
      resizableHandles: "s",
      resizableContainer: "parent",
      configPath: "sys/style/view",
      loadConfig: false
    },
    init: function() {
      var designEnv;
      designEnv = require("design/environment");
      return require("design/interactions/mouse_controller").droppable($(this));
    }
  };

}).call(this);
;}});
this.require.define({"common/page/header/config":function(exports, require, module){(function() {
  module.exports = {
    settings: {
      resizableHandles: "s",
      resizableContainer: "parent",
      configPath: "sys/style/view",
      loadConfig: false
    },
    init: function() {
      var designEnv;
      designEnv = require("design/environment");
      return require("design/interactions/mouse_controller").droppable($(this));
    }
  };

}).call(this);
;}});
this.require.define({"common/text/config":function(exports, require, module){(function() {
  var designEnv, designUtil, mouseController, viewUtil, _editable, _save;

  viewUtil = require("common/util");

  designUtil = require("design/util");

  designEnv = require("design/environment");

  mouseController = require("design/interactions/mouse_controller");

  _save = function(target, callback) {
    return viewUtil.update('/design/widget/param/' + $(target).attr("id"), {
      data: JSON.stringify({
        value: $(".text-content", target).html()
      })
    }, callback);
  };

  _editable = function(target) {
    var $text, command, controlCommand, customCallback, dropDownCommand, forceBlur, id, makeCommand, makeQuery, normalCommand, state, that, _forceBlur;
    that = this;
    id = $(target).attr("id");
    $text = $(".text-content", target);
    _forceBlur = false;
    $text.click(function(evt) {
      if ($("#js-text-toolbar").length === 1) {
        return evt.stopPropagation();
      }
    });
    $text.mousedown(function(evt) {
      if ($("#js-text-toolbar").length === 1) {
        return evt.stopPropagation();
      }
    });
    makeCommand = function(command, param) {
      return function(userParam) {
        var p;
        p = param != null ? param : userParam;
        if (_.isFunction(p)) {
          p = p();
        }
        return document.execCommand(command, false, p);
      };
    };
    makeQuery = function(command) {
      return function() {
        var value;
        value = document.queryCommandValue(command);
        return value === true || value === "true";
      };
    };
    command = {
      bold: makeCommand("bold"),
      italic: makeCommand("italic"),
      strike: makeCommand("strikethrough"),
      underline: makeCommand("underline"),
      sub: makeCommand("subscript"),
      sup: makeCommand("superscript"),
      justifyfull: makeCommand("justifyfull"),
      justifycenter: makeCommand("justifycenter"),
      justifyleft: makeCommand("justifyleft"),
      justifyright: makeCommand("justifyright")
    };
    state = {
      bold: makeQuery("bold"),
      italic: makeQuery("italic"),
      strike: makeQuery("strikethrough"),
      underline: makeQuery("underline"),
      sub: makeQuery("subscript"),
      sup: makeQuery("superscript"),
      justifyfull: makeQuery("justifyfull"),
      justifycenter: makeQuery("justifycenter"),
      justifyleft: makeQuery("justifyleft"),
      justifyright: makeQuery("justifyright")
    };
    normalCommand = {
      link: makeCommand("createlink", function() {
        return prompt("请输入链接内容");
      }),
      unlink: makeCommand("unlink")
    };
    dropDownCommand = {
      font: makeCommand("fontname"),
      size: makeCommand("fontsize"),
      color: makeCommand("forecolor")
    };
    customCallback = {
      size: function(command, param) {
        command(1);
        return $text.html($text.html().replace(/font\-size: x-small;/g, "font-size: " + param + ";"));
      }
    };
    controlCommand = {
      unformat: makeCommand("removeformat"),
      undo: makeCommand("undo"),
      redo: makeCommand("redo"),
      ok: function() {
        return forceBlur();
      },
      html: function() {
        var $textArea, htmlStr;
        if ($("textarea", target).length > 0) {
          return;
        }
        htmlStr = $.trim($text.html());
        $textArea = $("<textarea style='width:95%;height:90%;background-color:white'>" + htmlStr + "</textarea>");
        $text.hide();
        $text.after($textArea);
        $("#js-text-toolbar button").prop("disabled", true);
        $("#js-text-toolbar button[name=html]").html("ok").prop("disabled", false);
        $textArea.focus();
        return $textArea.blur(function() {
          htmlStr = $.trim($textArea.val());
          $textArea.remove();
          $text.html(htmlStr).show();
          return forceBlur();
        });
      }
    };
    $(target).dblclick(function(evt) {
      evt.stopPropagation();
      if ($text.prop("isContentEditable")) {
        return;
      }
      $(designEnv.beforeConfigDialog).remove();
      designUtil.remove_handler();
      designEnv.beforeSelectedId = null;
      $(target).css("cursor", "default");
      $text.prop("contenteditable", true);
      $text.css("cursor", "text");
      $text.focus();
      return viewUtil.get_template("common/text/toolbar", function(template) {
        var $toolbar, updateState;
        $(target).append(template({}));
        $toolbar = $("#js-text-toolbar", target);
        updateState = function() {
          if ($toolbar.length === 0) {
            return;
          }
          return _(state).each(function(v, k) {
            if (v()) {
              return $toolbar.find(".js-toggle-group button[name=" + k + "]").addClass("active");
            } else {
              return $toolbar.find(".js-toggle-group button[name=" + k + "]").removeClass("active");
            }
          });
        };
        $(target).mouseup(updateState);
        $(target).keyup(updateState);
        _(command).each(function(v, k) {
          return $toolbar.find(".js-toggle-group button[name=" + k + "]").click(function() {
            v();
            return updateState();
          });
        });
        _(normalCommand).each(function(v, k) {
          return $toolbar.find(".js-normal-group button[name=" + k + "]").click(v);
        });
        _(dropDownCommand).each(function(v, k) {
          var $btn;
          $btn = $toolbar.find(".js-dropdown-group button[name=" + k + "]");
          $btn.click(function() {
            $(this).siblings().removeClass("active");
            $(this).toggleClass("active");
            $(this).siblings().find(".dropdown-menu").hide();
            return $(this).find(".dropdown-menu").toggle();
          });
          $btn.find(".dropdown-menu").click(function(evt) {
            return evt.stopPropagation();
          });
          return $btn.find(".dropdown-menu a").click(function(evt) {
            var customValue;
            evt.stopPropagation();
            if ($(this).hasClass("js-custom")) {
              customValue = prompt($(this).data("value"));
              if (customCallback[k]) {
                return customCallback[k](v, customValue);
              } else {
                return v(customValue);
              }
            } else {
              return v($(this).data("value"));
            }
          });
        });
        return _(controlCommand).each(function(v, k) {
          return $toolbar.find(".js-control-group button[name=" + k + "]").click(v);
        });
      });
    });
    $text.keydown(function(evt) {
      return evt.stopPropagation();
    });
    $text.keyup(function(evt) {
      evt.stopPropagation();
      if (evt.which === 27) {
        return forceBlur();
      }
    });
    $text.blur(function(evt) {
      var $toolbar, force;
      force = _forceBlur;
      _forceBlur = false;
      $toolbar = $("#js-text-toolbar", target);
      if (!force && $toolbar.length === 1 && $toolbar.has(":hover").length > 0) {
        $(this).focus();
        return true;
      }
      $(target).css("cursor", "");
      $text.prop("contenteditable", false);
      $text.css("cursor", "");
      $toolbar.remove();
      return _save(target);
    });
    return forceBlur = function() {
      _forceBlur = true;
      return $text.blur();
    };
  };

  module.exports = {
    settings: {
      resizable: false,
      openConfig: false
    },
    init: function() {
      document.execCommand("styleWithCSS", false, true);
      $(this).click(function(evt) {
        if ($("#js-text-toolbar").length === 1) {
          return evt.stopPropagation();
        } else {
          return mouseController.resizable(evt);
        }
      });
      return _editable(this);
    }
  };

}).call(this);
;}});
this.require.define({"official/session/login_box/config":function(exports, require, module){(function() {
  module.exports = {
    settings: {
      openConfig: false
    }
  };

}).call(this);
;}});
this.require.define({"official/session/menu/config":function(exports, require, module){(function() {
  module.exports = {
    settings: {
      openConfig: false
    }
  };

}).call(this);
;}});
