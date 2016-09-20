module.exports = {
    configInit: function($panel) {
        var mouseController = require("design/interactions/mouse_controller")
        var viewUtil = require("common/util")

        $('#js-image-slider-data-scroll', $panel).sortable();
        mouseController.scrollable("#js-image-slider-data-scroll");

        $('#js-change-images', $panel).click(function (evt) {
            require("common/image/selector/config").show_change_image(evt, function (image_url) {
                $('.image-view', $panel).attr('src', image_url);
                $("#js-image-slider-data-scroll").append("<img alt=\"title1\" src="+ image_url +">");
            });
        });

        $panel.on('click', '.image-list img', function() {
            $('.image-list img.active', $panel).removeClass('active');
            $(this).addClass('active');
            $('.image-view', $panel).attr('src', $(this).attr('src'));
            // write title and link to image-view
            $('input[name=title]', $panel).val($(this).attr('alt'));
            $('input[name=href]', $panel).val($(this).attr('href'));
        });

        $('input', $panel).keyup(function() {
            $(this).change();
        });

        $('input[name=href]', $panel).change(function(){
            $('.image-list img.active', $panel).attr('href', $(this).val());
        });

        $('input[name=title]', $panel).change(function(){
            $('.image-list img.active', $panel).attr('alt', $(this).val());
        });

        $panel.on('dblclick', '.image-list img', function(){
            $(this).remove();
            return false;
        });

        $target = $(this)
        $('button.js-ok', $panel).click(function () {
            var params = [];
            $('.image-list img', $panel).each(function(){
                params.push({src: $(this).attr('src'), title: $(this).attr('alt'), href: $(this).attr('href')});
            });
            viewUtil.update('/design/widget/param/'+ $target.attr("id") ,{data: viewUtil.stringify({images:params})},function(){
                $(panel).remove();
            })
        });

        // default to click first img
        $(".image-list img:first", $panel).trigger('click');
    }
}
