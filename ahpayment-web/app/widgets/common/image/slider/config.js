module.exports = {
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
