module.exports = {
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
