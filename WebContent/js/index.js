/**
 * Created by eduask on 2016/11/1.
 */
function initHeight(){
    var winHeight=$(window).height();
    var bodyHeight=winHeight-$('.nav').css('height').substring(0,$('.nav').css('height').length-2)-$('.foot').css('height').substring(0,$('.foot').css('height').length-2);
    console.info(bodyHeight);
    $('.body').css('height',bodyHeight+'px');
};
$(function(){
    initHeight();

    $(window).resize(function(){

       initHeight();

    });

    $('.nav').find('div:eq(2)').bind('mouseover',function(){

        $('#setting').slideDown(500);

    });

    $('.nav').find('div:eq(2)').bind('mouseleave',function(){

        $('#setting').slideUp(500);

    });

    $('#setting').find('div:eq(1)').bind('mouseover',function(){

        $('.section2').slideDown(500);

    });

    $('#setting').find('div:eq(1)').bind('mouseleave',function(){

        $('.section2').slideUp(500);

    });

    $('.topmenus-big:gt(0)').bind('click',function(){
        $('.secondmenus-big').css('display','none');
        $(this).next('.secondmenus-big').css('display','block');
    });

});