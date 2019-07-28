/*
* infinite方法调用
* */
+function ($) {
    "use strict";
    var getOffset = function (container) {
        var tagName = container[0].tagName.toUpperCase();
        var scrollTop;
        if (tagName === 'BODY' || tagName === 'HTML') {
            scrollTop = container.scrollTop() || $(window).scrollTop()
        } else {
            scrollTop = container.scrollTop()
        }
        var offset = container[0].scrollHeight - ($(window).height() + scrollTop);
        //console.log(offset)
        return offset
    };

    var Infinite = function(el, distance) {
        this.container = $(el);
        this.container.data("infinite", this);
        this.distance = distance || 50;
        this.attachEvents();
    };

    Infinite.prototype.scroll = function() {
        var container = this.container;
        this._check();
    };

    Infinite.prototype.attachEvents = function(off) {
        var el = this.container;
        var scrollContainer = (el[0].tagName.toUpperCase() === "BODY" ? $(document) : el);
        scrollContainer[off ? "off" : "on"]("scroll", $.proxy(this.scroll, this));
    };
    Infinite.prototype.detachEvents = function(off) {
        this.attachEvents(true);
    };
    Infinite.prototype._check = function() {
        var offset = getOffset(this.container);
        if(Math.abs(offset) <= this.distance) {
            this.container.trigger("infinite");
        }
    };

    var infinite = function(el) {
        attachEvents(el);
    };

    $.fn.infinite = function(distance) {
        return this.each(function() {
            new Infinite(this, distance);
        });
    };
    $.fn.destroyInfinite = function() {
        return this.each(function() {
            var infinite = $(this).data("infinite");
            if(infinite && infinite.detachEvents) infinite.detachEvents();
        });
    }

}($);