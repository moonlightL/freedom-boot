$(document).ready(function() {
    var $imgHolder 	= $('#freedom-bg-list');
    var $bgBtn 		= $imgHolder.find('.chg-bg');
    var $target 	= $('#bg-overlay');

    var url = localStorage.getItem("loginBgUrl") || "/assets/picture/bg-img-1.jpg";
    $target.css("background-image","url("+ url +")");
    var num = url.substring(url.lastIndexOf("-") + 1).split(".")[0];
    $($bgBtn[num -1]).addClass('active');

    $bgBtn.on('click', function(e){
        e.preventDefault();
        e.stopPropagation();

        var $el = $(this);
        if ($el.hasClass('active') || $imgHolder.hasClass('disabled'))return;
        if ($el.hasClass('bg-trans')) {
            $target.css('background-image','none').removeClass('bg-img');
            $imgHolder.removeClass('disabled');
            $bgBtn.removeClass('active');
            $el.addClass('active');

            return;
        }

        $imgHolder.addClass('disabled');
        var url = $el.attr('src').replace('/thumbs','');

        localStorage.setItem("loginBgUrl", url);

        $('<img/>').attr('src' , url).load(function(){
            $target.css('background-image', 'url("' + url + '")').addClass('bg-img');
            $imgHolder.removeClass('disabled');
            $bgBtn.removeClass('active');
            $el.addClass('active');

            $(this).remove();
        })

    });


});
