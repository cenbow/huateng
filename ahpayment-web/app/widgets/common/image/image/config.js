_save_change = function () {
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
