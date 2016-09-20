/**
 * jQuery jslides 1.1.0
 *
 * http://www.cactussoft.cn
 *
 * Copyright (c) 2009 - 2013 Jerry
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */
$(function(){
	var numpic = $('#slides02 li').size()-1;
	var nownow = 0;
	var inout = 0;
	var TT = 0;
	var SPEED = 8000;


	$('#slides02 li').eq(0).siblings('li').css({'display':'none'});


	var ulstart = '<ul id="pagination02">',
		ulcontent = '',
		ulend = '</ul>';
	ADDLI();
	var pagination = $('#pagination02 li');
	var paginationwidth = $('#pagination02').width();
	
	pagination.eq(0).addClass('current')
		
	function ADDLI(){
		//var lilicount = numpic + 1;
		for(var i = 0; i <= numpic; i++){
			ulcontent += '<li>' + '<span>' + (i+1) + '</span>' + '</li>';
		}
		
		$('#slides02').after(ulstart + ulcontent + ulend);	
	}

	pagination.on('click',DOTCHANGE)
	
	function DOTCHANGE(){
		
		var changenow = $(this).index();
		
		$('#slides02 li').eq(nownow).css('z-index','900');
		$('#slides02 li').eq(changenow).css({'z-index':'800'}).show();
		pagination.eq(changenow).addClass('current').siblings('li').removeClass('current');
		$('#slides02 li').eq(nownow).fadeOut(300,function(){$('#slides02 li').eq(changenow).fadeIn(700);});
		nownow = changenow;
	}
	
	pagination.mouseenter(function(){
		inout = 1;
	})
	
	pagination.mouseleave(function(){
		inout = 0;
	})
	
	function GOGO(){
		
		var NN = nownow+1;
		
		if( inout == 1 ){
			} else {
			if(nownow < numpic){
			$('#slides02 li').eq(nownow).css('z-index','900');
			$('#slides02 li').eq(NN).css({'z-index':'800'}).show();
			pagination.eq(NN).addClass('current').siblings('li').removeClass('current');
			$('#slides02 li').eq(nownow).fadeOut(300,function(){$('#slides02 li').eq(NN).fadeIn(700);});
			nownow += 1;

		}else{
			NN = 0;
			$('#slides02 li').eq(nownow).css('z-index','900');
			$('#slides02 li').eq(NN).stop(true,true).css({'z-index':'800'}).show();
			$('#slides02 li').eq(nownow).fadeOut(300,function(){$('#slides02 li').eq(0).fadeIn(700);});
			pagination.eq(NN).addClass('current').siblings('li').removeClass('current');

			nownow=0;

			}
		}
		TT = setTimeout(GOGO, SPEED);
	}
	
	TT = setTimeout(GOGO, SPEED); 

})