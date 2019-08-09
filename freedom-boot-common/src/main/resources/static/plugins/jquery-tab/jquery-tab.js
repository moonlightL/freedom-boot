;(function($){

  "use strict"

  var defaultSetting = {
    homeUrl: "",
    homeName: "主页",
    bottom: 135,
    tabCallback: null
  }

  $.fn.tab = function(options) {
      defaultSetting = $.extend(defaultSetting, options || {});
      var that = this;
      init(that);
  }

  function init(obj) {
      initTabContainer(obj);
      bindEvent();
  }

  function initTabContainer(obj) {
    var format_url = defaultSetting.homeUrl.replace(/\./g, '_').replace(/\//g, '_').replace(/:/g, '_').replace(/\?/g, '_').replace(/,/g, '_').replace(/=/g, '_').replace(/&/g, '_');
    var arr = [];
    arr.push("<div class='tab-container-menu'>");
    arr.push("<ul id='tabs' class='tabs'>")
    arr.push("<li class='tabs-item active' id='tab_"+format_url+"' data-close='false' data-url='"+defaultSetting.homeUrl+"'><a href='javascript:;' class='waves-effect waves-light'>"+defaultSetting.homeName+"</a></li>");
    arr.push("</div>");
    arr.push("<div class='tab-container-content' id='tab-container-content'>");
    arr.push("<div id='iframe_tab_"+format_url+"' class='iframe active'>");
    arr.push("<iframe class='tab-iframe' src='"+defaultSetting.homeUrl+"' frameborder='0' width='100%' onload='changeFrameHeight(this)'></iframe>");
    arr.push("</div>");
    arr.push("</div>");

    $(obj).append(arr.join(""));
  }

  function bindEvent() {
      Waves.displayEffect();

      // 点击标签
     $(document).on("click",".tabs li", function() {
        // 切换 tab
        $(".tabs-item").removeClass("active");
        $(this).addClass("active");
        if (typeof defaultSetting.tabCallback == "function") {
            var url = $(this).data("url");
            defaultSetting.tabCallback(url, this);
        }

        // 切换 iframe
        $(".iframe").removeClass("active");
        $("#iframe_" + $(this).attr("id")).addClass("active");

        // 检测是否需要滑动
        checkScroll($(this));
     });

     // 打开标签
     $("a[data-url]").on("click", function() {
        TabManager.openTab(this);
     });
  }

  function checkScroll(tab) {
    var marginLeft = $("#tabs").css("marginLeft").replace("px", "");
    // 滚动到可视区域:在左侧
    if (tab.position().left < marginLeft) {
      var left = $("#tabs").scrollLeft() + tab.position().left - marginLeft;
      $("#tabs").animate({scrollLeft: left}, 200);
    }
    // 滚动到可视区域:在右侧
    if((tab.position().left + tab.width() - marginLeft) > document.getElementById("tabs").clientWidth) {
      var left = $("#tabs").scrollLeft() + ((tab.position().left + tab.width() - marginLeft) - document.getElementById("tabs").clientWidth);
      $("#tabs").animate({scrollLeft: left}, 200);
    }
  }

  window.changeFrameHeight = function(iframe) {
    iframe.height =  document.documentElement.clientHeight - defaultSetting.bottom;
  }

  window.onresize = function() {
      $(".tab-iframe").height(document.documentElement.clientHeight - defaultSetting.bottom);
  }

  var TabManager = {
      openTab: function(obj) {
        // 取消激活
        $(".tabs-item").removeClass("active");
        $(".iframe").removeClass("active");

        var tabName = $(obj).text();
        var url = $(obj).data("url");
        var format_url = url.replace(/\./g, '_').replace(/\//g, '_').replace(/:/g, '_').replace(/\?/g, '_').replace(/,/g, '_').replace(/=/g, '_').replace(/&/g, '_');
        // 如果标签已存在，重新激活
        if ($("#tab_" + format_url).length > 0) {
          $("#tab_" + format_url).trigger("click");
        } else {
          // 创建 tab
          var tab = "<li class='tabs-item active' id='tab_"+format_url+"' data-close='true' data-url='"+ url +"'><a href='javascript:;' class='waves-effect waves-light'>"+tabName+"</a></li>";
          $("#tabs").append(tab);

          // 创建 iframe
          var iframe = "<div id='iframe_tab_"+format_url+"' class='iframe active'><iframe class='tab-iframe' src='"+url+"' frameborder='0' width='100%' onload='changeFrameHeight(this)'></iframe></div>";
          $("#tab-container-content").append(iframe);

          // 检测是否需要滑动
           checkScroll($("#tab_" + format_url));
        }
      },
      closeTab: function(item) {

          if (!item.data("close")) {
            return false;
          }

          // 如果移除的是激活的标签，则激活前一个标签
          if (item.hasClass("active")) {
            item.prev().trigger("click");
          }

          $("#iframe_" + item.attr("id")).remove();
          item.remove();
      }
  };

  var menu = new BootstrapMenu(".tabs li", {
      fetchElementData: function(item) {
         return item;
      },
      actions: {
        close: {
          name: "关闭",
          iconClass: "zmdi zmdi-close",
          onClick: function(item) {
            TabManager.closeTab(item);
          }
        },
        closeOther: {
          name: "关闭其他",
         iconClass: 'zmdi zmdi-arrow-split',
          onClick: function(item) {
              var index = item.attr("id");
              $(".tabs li").each(function() {
                  if($(this).attr("id") != index) {
                    TabManager.closeTab($(this));
                  }
              });
          }
        },
        closeAll: {
          name: "关闭全部",
          iconClass: 'zmdi zmdi-swap',
          onClick: function() {
            $(".tabs li").each(function() {
                  TabManager.closeTab($(this));
              });
          }
        },
        closeRight: {
          name: "关闭右侧所有",
          iconClass: 'zmdi zmdi-arrow-right',
          onClick: function(item) {
            var index = item.attr("id");
            $($('.tabs li').toArray().reverse()).each(function() {
              if ($(this).attr("id") != index) {
                TabManager.closeTab($(this));
              } else {
                return false;
              }
            });
          }
        },
        closeLeft: {
          name: "关闭左侧所有",
          iconClass: 'zmdi zmdi-arrow-left',
          onClick: function(item) {
            var index = item.attr("id");
            $('.tabs li').each(function() {
              if ($(this).attr("id") != index) {
                TabManager.closeTab($(this));
              } else {
                return false;
              }
            });
          }
        },
        refresh: {
          name: "刷新",
          iconClass: 'zmdi zmdi-refresh',
          onClick: function(item) {
            var index = item.attr("id");
            var $iframe = $('#iframe_' + index).find('iframe');
            $iframe.attr('src', $iframe.attr('src'));
          }
        }
      }
  });

})(jQuery)

