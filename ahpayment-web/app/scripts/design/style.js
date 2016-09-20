designEnv = require("design/environment")
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
