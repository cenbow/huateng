module.exports = {
    init: function() {
        var behavior = $(this).attr('data-behavior');
        var handles;
        if (behavior != undefined && behavior != "") {
            handles = $.parseJSON(behavior);
            var switchable = $(this).switchable(handles)
            //add mode
            $(this).addClass(handles['mode']);

            $('.control .left', $(this)).click(function () {
                switchable.data('switchables')[0].prev()
            });
            $('.control .right', $(this)).click(function () {
                switchable.data('switchables')[0].next()
            });
        }
    }
}
