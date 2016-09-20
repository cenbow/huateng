module.exports = {
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
