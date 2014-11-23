/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {

    var windowHeight = $(window).height();
    var tabPanelOffset = 0;
    
    $.each($('script[id^=documentData]'), function () {
        var payload = $(this).text();
        var $iframe = $(this).next();
        $iframe.contents().find('html').html(payload);
//            $iframe.css('width', '100%');

//        var height = $iframe.contents().find('html').height();
        tabPanelOffset = $iframe.closest('.tab-content').offset().top;
        $('iframe').height(windowHeight - tabPanelOffset);
    });

    $(window).resize(function () {
        windowHeight = $(this).height();
        $('iframe').height(windowHeight - tabPanelOffset);
    });
});