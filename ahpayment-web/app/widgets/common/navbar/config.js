module.exports = {
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
