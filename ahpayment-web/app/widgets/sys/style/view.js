module.exports = {
    configInit:function () {
        var designEnv = require("design/environment")
        var styleUtil = require("design/style")

        var $t = $('#' + designEnv.beforeSelectedId);
        styleUtil.load_from_dom('#js-style-setting-panel');

        require("design/interactions/mouse_controller").scrollable('#js-style-setting-body');

        $('#js-style-setting-panel #js-change-image').click(function (evt) {
            evt.stopPropagation();
            require("common/image/selector/config").show_change_image(evt, function (image_url) {
                $("#bg-image").attr("src", image_url);
                styleUtil.save_and_enable(designEnv.beforeSelectedId, 'background-image', 'url(' + image_url + ')');
            })
        });

        $('.position', '#js-style-setting-panel .positions').click(function (evt) {
            evt.stopPropagation();
            $('.position', $(this).parents('.positions')).removeClass('selected');
            $(this).addClass('selected');
            styleUtil.save_and_enable(designEnv.beforeSelectedId, 'background-position', $(this).attr('data'));
        });

        $('input[name=background-repeat]', '#js-style-setting-panel .repeat-and-position').click(function (evt) {
            evt.stopPropagation();
            styleUtil.save_and_enable(designEnv.beforeSelectedId, 'background-repeat', $(this).val());
        });
    }
}
