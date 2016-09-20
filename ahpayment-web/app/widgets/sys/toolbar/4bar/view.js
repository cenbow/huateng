var viewUtil = require("common/util")
var styleUtil = require("design/style")
var designEnv = require("design/environment")
var designUtil = require("design/util")

viewUtil.magic_render('.toolbar', 'sys/toolbar/page/view', '/design/page/pages/' + $('#siteInstanceId').val(), function () {
    require("design/interactions/mouse_controller").scrollable('#js-page-panel .scrollable');
});
viewUtil.magic_render('.toolbar', 'sys/toolbar/comp/view', '/design/component/category/2');
viewUtil.magic_render('.toolbar', 'sys/toolbar/design/view');

$('.logo', '.top-bar-container').click(function () {
    window.location = "/i";
});
$('.toolbar .toolbar-menu li').click(function () {
    $('.toolbar .app-modal').hide();
    var name = $(this).attr('class').split(' ')[0].replace('menu', 'panel');
    $('#js-' + name).show();
});

$(document).on('click', '.toolbar .menu-icon', function() {
    $(this).parent().hide();
    $('#js-add-page-panel').hide();
});

$('#js-page-structure-save').click(function () {
    //add behavior and mode
    $('.js-comp').each(function () {
        if ($(this).attr('mode') === 'true') {
            styleUtil.save($(this).attr('id'), 'mode', designUtil.trimClass($(this).attr('class')));
        }
    });

    $.ajax({
        type: "PUT",
        url: '/design/page/structure/' + $('#siteInstanceId').val() + '/' + $('#pageId').val(),
        data: {structureJson:JSON.stringify(designEnv.pageData)}
    }).done(function(){
        humane.log("页面已保存.");
    })

    if (designEnv.globalSettingChanged) {
        $.ajax({
            type: "PUT",
            url: '/design/page/global/' + $('#siteInstanceId').val(),
            data: {style:$('#body').attr('style')}
        }).done(function(){
            humane.log("全局配置已保存.");
        })
        designEnv.globalSettingChanged = false;
    }
});

$('#js-site-deploy').click(function () {
    var siteId = $("#siteId").val();

    $.ajax({
        type:"GET",
        url:"/api/site/isTemplate?siteId=" + siteId
    }).done(function (msg) {
        var type = "";
        if (msg.data === "1") {
            type = "template";
        } else {
            type = "site"
        }
        viewUtil.get_template('official-site-publish_' + type + '/' + siteId, function (template) {
            var content = $(template({})).hide().fadeIn();
            $('.toolbar').append(content);
            // TODO init.call(self)
            require("official/site/publish_" + type + "/view").init();
        }, true);
    });
});
