function menu (option) {
	var lk = $(option.lk),
		bar = $(option.bar) ;
	lk.hover(function () {
		bar.animate({marginTop : '-103px'},500,"easeOutCirc")
	},function () {
		bar.animate({marginTop : '0px'},500,"easeOutCirc")
	});
}

menu({lk: '.sub-ipone',bar : '.sub-bar'});
menu({lk: '.sub-android',bar : '.sub-bar2'});
menu({lk: '.sub-wp',bar : '.sub-bar3'});
