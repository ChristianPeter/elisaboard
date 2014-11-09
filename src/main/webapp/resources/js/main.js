/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function ocScrollToDetail(data) {
    if (data.status === 'success') {         
         $('body').scrollTo('#detailCol');
    }
}

function ocScrollToList(data) {
    if (data.status === 'success') {         
         //$('body').scrollTo('#listCol'); // this won't work...
         $('body').scrollTop(0);
    }
}


$(document).ready(function(){
    $(document).on('click', '.btndelete', function(event){
        event.preventDefault();
        event.stopPropagation();
    });
});


$(function() {
/*!
 * jQuery - .scrollTo()
 *
 *  Author:
 *  Timothy A. Perez
 *
 * Date: OCT 2012
 * Comments: Setting new web standards...
 */
	// .scrollTo - Plugin
	$.fn.scrollTo = function( target, options, callback ){
	  if(typeof options == 'function' && arguments.length == 2){ callback = options; options = target; }
	  var settings = $.extend({
		scrollTarget  : target,
		offsetTop     : 50,
		duration      : 500,
		easing        : 'swing'
	  }, options);
	  return this.each(function(){
		var scrollPane = $(this);
		var scrollTarget = (typeof settings.scrollTarget == "number") ? settings.scrollTarget : $(settings.scrollTarget);
		var scrollY = (typeof scrollTarget == "number") ? scrollTarget : scrollTarget.offset().top + scrollPane.scrollTop() - settings.offsetTop;
		scrollPane.animate({scrollTop : scrollY }, settings.duration, settings.easing, function(){
		  if (typeof callback == 'function') { callback.call(this); }
		});
	  });
	}
	

	// [end] Demo Code //
});