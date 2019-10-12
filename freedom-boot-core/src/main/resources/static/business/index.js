$(function() {

    settingManager.loadData();

    var $mainnav = $("#mainnav-menu");
    $("[data-url]").on("click", function () {
        $mainnav.find("li").removeClass("active-link");
        $(this).parents("li").addClass("active-link");
    });

    $("#tab-container").tab({
        homeUrl: "/home/dashboard.html",
        homeName: "主页",
        bottom: 175,
        tabCallback: function (url, tab) {
            $mainnav.find("li").removeClass("active-link");
            $("a[data-url][data-url='"+url+"']").parents("li").addClass("active-link");
        }
    });

    $(".logout-btn").on("click", function() {
        layer.confirm("确定要注销吗?", function(index){
            $.ajax({
                type: "POST",
                url: "/core/login/logout.json",
                dataType: "json",
                success: function(resp) {
                    if (resp.success) {
                        window.parent.location.href = "/";
                    }
                }
            });
        });
    });

});

var settingManager = {
    loadData: function() {
        settingManager.loadTheme();
        settingManager.loadLayout();
        settingManager.loadCollapsedMode();
        settingManager.loadProfile();
        settingManager.loadFloat();
        settingManager.loadDarkTheme();
    },
    loadTheme: function() {
        var themeStyle = localStorage.getItem("freedom.index.themeStyle");
        if (themeStyle) {
            var $theme = "<link id='theme' href='"+ themeStyle +"' rel='stylesheet'/>";
            $("head").append($theme);
        }
    },
    loadLayout: function() {
        var layout = localStorage.getItem("freedom.index.layout");
        if (layout) {
            var body = ($("#freedom-set-icon"), $("#freedom-set-btngo"), $("#container"));
            body.addClass(layout);
        }
    },
    loadCollapsedMode: function() {
        var mode = localStorage.getItem("freedom.index.nav");
        var body = $("#container");
        body.removeClass("mainnav-lg mainnav-sm");
        if (mode) {
            body.addClass(mode);
        }
    },
    loadProfile: function() {
        var show = localStorage.getItem("freedom.index.profile");
        var $mainnav = $("#mainnav-profile");
        if (show) {
            $mainnav.addClass("hidden");
        } else {
            $mainnav.removeClass("hidden");
        }
    },
    loadFloat: function() {
        var float = localStorage.getItem("freedom.index.float");
        var body = $("#container");
        body.removeClass("aside-float");
        if (float) {
            body.addClass("aside-float");
        }
        $(window).trigger("resize");
    },
    loadDarkTheme: function () {
        var darkTheme = localStorage.getItem("freedom.index.darkTheme");
        var body = $("#container");
        body.removeClass("aside-bright");
        if (darkTheme) {
            body.addClass(darkTheme);
        }
    }
}