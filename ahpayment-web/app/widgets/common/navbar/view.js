module.exports = {
    init: function() {
        var data = $.parseJSON($(this).attr('data-behavior'));
        if (data != null) {

            //font color
            $('li:not(.active) a', $(this)).css('color', data['font-color']);

            //background-color
            $('li:not(.active)', $(this)).css('background-color', data['background-color']);

            //hover-font-color
            $('li:not(.active)', $(this)).mouseover(function () {
                $('a', this).css('color', data['hover-font-color']);
                $(this).css('background-color', data['hover-background-color']);
            });

            //mouse-leave
            $('li:not(.active)', $(this)).mouseleave(function () {
                $('a', this).css('color', data['font-color']);
                $(this).css('background-color', data['background-color']);
            });

            //active-font-color
            $('li.active a', $(this)).css('color', data['active-font-color']);

            //active-background-color
            $('li.active', $(this)).css('background-color', data['active-background-color']);

            //margin-right
            $('li', $(this)).css('margin-right', data['margin-right']);

            //padding-top
            $('li', $(this)).css('padding-top', data['padding-top']);

            //padding-bottom
            $('li', $(this)).css('padding-bottom', data['padding-bottom']);

            //border-radius
            $('li', $(this)).css('border-radius', data['border-radius']);
        }
    }
}
