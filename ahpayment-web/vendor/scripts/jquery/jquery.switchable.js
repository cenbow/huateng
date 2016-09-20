/*
 * Switchable jQuery Plugin
 * @author: Jinpu Hu <jinpu.hu@qunar.com>
 */

// lazyload start
(function($) {

    function _loadImgSrc(img, type) {
        var data_src = img.getAttribute(type);

        if (data_src && img.src != data_src) {
            img.src = data_src;
            img.removeAttribute(type);
        }
    };

    $.extend({
        loadCustomLazyData: function(containers, type) {

            var $imgs;

            $.each(containers, function() {
                switch (type) {
                    case 'data-textarea':
                        // TODO 通过textarea延迟加载内容(图片，网页html，脚本)
                        break;
                    default:
                        if (this.nodeName === 'IMG') { // 本身就是图片
                            $imgs = $(this);
                        } else {
                            $imgs = $(this).find('img');
                        }
                        $imgs.each(function() {
                            _loadImgSrc(this, type);
                        });
                }
            });
        }
    });
})(jQuery);
// lazyload end

(function($) {

    var doc = document;
    var DOT = '.';
    var EVENT_BEFORE_SWITCH = 'beforeSwitch';
    var EVENT_SWITCH = 'switch';
    var EVENT_AFTER_SWITCH = 'afterSwitch';
    var CLASSPREFIX = 'able-switchable-';

    // Namspace able.Switchable
    $.extend({
        able: {
            Switchable: Switchable
        }
    });

    SP = Switchable.prototype;
    Switchable.Plugins = [];

    // Class Switchable
    function Switchable($container, config) {
        var self = this; // Switchable Class Instance
        self.config = $.extend({}, $.fn.switchable.defaults, config || {});
        self.$container = $container;
        self._init();
    };


    // Extend Switchable prototype
    $.extend(SP, {
        _init: function() {
            var self = this,
            config = self.config;

            self.activeIndex = config.activeIndex;

            self.$evtBDObject = $('<div />');

            self._parseStructure();

            if (config.hasTriggers) self._bindTriggers();

            $.each(Switchable.Plugins, function() {
                this._init(self);
            });
        },

        _parseStructure: function() {
            var self = this,
            $container = self.$container,
            config = self.config;

            switch (config.type) {
                case 0:
                    self.$triggers = $container.find(DOT + config.navCls).children();
                    self.$panels = $container.find(DOT + config.contentCls).children();
                    break;

                case 1:
                    self.$triggers = $container.find(DOT + config.triggerCls);
                    self.$panels = $container.find(DOT + config.panelCls);
                    break;
            }
            self.viewLength = self.$panels.length / config.step;
        },

        _bindTriggers: function() {
            var self = this, config = self.config,
            $triggers = self.$triggers, events = config.events;

            $triggers.each(function(index, trigger) {
                if ($.inArray('click', events) !== -1) {
                    $(trigger).click(function(evt) {
                        if (self.activeIndex === index) return self;
                        if (self.switchTimer) clearTimeout(self.switchTimer);
                        self.switchTimer = setTimeout(function() {
                            self.switchTo(index);
                        }, config.delay * 1000);

                        evt.stopPropagation();
                    });
                }

                if ($.inArray('hover', events) !== -1) {
                    $(trigger).hover(function(evt) {
                        if (self.activeIndex === index) return self;
                        if (self.switchTimer) clearTimeout(self.switchTimer);
                        self.switchTimer = setTimeout(function() {
                            self.switchTo(index);
                        }, config.delay * 1000);
                    }, function(evt) {
                        if (self.switchTimer) clearTimeout(self.switchTimer);
                        evt.stopPropagation();
                    });
                }
            });
        },

        beforeSwitch: function(fn) {
            if ($.isFunction(fn)) this.$evtBDObject.bind(EVENT_BEFORE_SWITCH, fn);
        },

        afterSwitch: function(fn) {
            if ($.isFunction(fn)) this.$evtBDObject.bind(EVENT_AFTER_SWITCH, fn);
        },

        switchTo: function(index) {
            var self = this, config = self.config,
            triggers = $.makeArray(self.$triggers), panels = $.makeArray(self.$panels),
            activeIndex = self.activeIndex,
            step = config.step,
            fromIndex = activeIndex * step,
            toIndex = index * step;

            self.$evtBDObject.trigger(EVENT_BEFORE_SWITCH, [index]);

            if (config.hasTriggers) {
                self._switchTrigger(activeIndex > -1 ? triggers[activeIndex] : null, triggers[index]);
            }

            self._switchView(
                panels.slice(fromIndex, fromIndex + step),
                panels.slice(toIndex, toIndex + step),
                index);

            // update activeIndex
            self.activeIndex = index;

            self.$evtBDObject.trigger(EVENT_AFTER_SWITCH, [index]);  //# 是否还未完全实现
        },

        /**
         * 切换到上一视图
         */
        prev: function() {
            var self = this, activeIndex = self.activeIndex;
            self.switchTo(activeIndex > 0 ? activeIndex - 1 : self.viewLength - 1);
        },

        /**
         * 切换到下一视图
         */
        next: function() {
            var self = this, activeIndex = self.activeIndex;
            self.switchTo(activeIndex < self.viewLength - 1 ? activeIndex + 1 : 0/*, FORWARD*/);
        },

        _switchTrigger: function(fromTrigger, toTrigger) {

            var activeTriggerCls = this.config.activeTriggerCls;

            if (fromTrigger) $(fromTrigger).removeClass(activeTriggerCls);
            $(toTrigger).addClass(activeTriggerCls);
        },

        _switchView: function(fromPanels, toPanels, index) {
            // 最简单的切换效果：直接隐藏/显示
            $.each(fromPanels, function() {
                $(this).hide();
            });
            $.each(toPanels, function() {
                $(this).show();
            });
        }
    }); // EOF Switchable prototype extend

    $.fn.switchable = function(config) {
        var $self = this;

        // 维系Switchable对象将来可以访问
        var switchables = $self.data('switchables');
        $self.data('switchables', switchables ? switchables : []);

        return $self.each(function() {
            $self.data('switchables').push(new Switchable($(this), config));
        });
    };

    $.fn.switchable.defaults = {

        /**
         * @cfg Number type
         * 默认为0。

         * type为0，则通过navCls和contentCls来获取triggers和panels；

         * type为1，则通过triggerCls和panelCls来获取triggers和panels；
         */
        type: 0,

        /**
         * @cfg String navCls
         * 默认为able-switchable-nav，通过此类获取触发条件的容器，比如1 2 3 4 5的列表，这个class应该设置到ul或者ol上面，而不是每个触发条件li上面。
         */
        navCls: CLASSPREFIX + 'nav',

        /**
         * @cfg String contentCls
         * 默认为able-switchable-content，通过此类获取显示内容的容器，但不是具体的内容面板。
         */
        contentCls: CLASSPREFIX + 'content',

        /**
         * @cfg String triggerCls
         * 默认为able-switchable-trigger，通过此类获取具体的触发条件，此情况下，一般触发条件不在同一个容器。
         */
        triggerCls: CLASSPREFIX + 'trigger',

        /**
         * @cfg String panelCls
         * 默认为able-switchable-panel，通过此类获取具体的显示内容面板，此情况下，一般内容面板不在同一个容器。
         */
        panelCls: CLASSPREFIX + 'panel',

        /**
         * @cfg Boolean hasTriggers
         * 默认为true，是否有可见的触发条件。
         */
        hasTriggers: true,

        /**
         * @cfg Number activeIndex
         * 默认为0，初始时被激活的索引。
         */
        activeIndex: 0,

        /**
         * @cfg String activeTriggerCls
         * 默认为active，被激活时的css样式名。
         */
        activeTriggerCls: 'active',

        /**
         * @cfg Array events
         * 默认为['click', 'hover']，触发条件事件响应数组，目前支持click和hover。
         */
        events: ['click', 'hover'],

        /**
         * @cfg Number step
         * 默认为1，一次切换的内容面板数。
         */
        step: 1,

        /**
         * @cfg Number delay
         * 默认为0，延迟执行切换的时间间隔。
         */
        delay: 0,

        /**
         * @cfg Array viewSize
         * 一般自动设置，除非自己需要控制显示内容面板的[宽, 高]，如果[680]、[320, 150]。
         */
        viewSize: []
    };
})(jQuery);

// autoplay start
(function($) {
    var Switchable = $.able.Switchable;

    $.extend($.fn.switchable.defaults, {

        /**
         * @cfg Boolean autoplay
         * 默认为false，不自动播放。
         */
        autoplay: false,

        /**
         * @cfg Number interval
         * 默认为3，自动播放间隔时间。
         */
        interval: 3,

        /**
         * @cfg Boolean pauseOnHover
         * 默认为true，鼠标悬停在容器上是否暂停自动播放
         */
        pauseOnHover: true
    });

    Switchable.Plugins.push({
        name: 'autoplay',

        _init: function(host) {
            var config = host.config;
            if (!config.autoplay) return;

            // 鼠标悬停，停止自动播放
            if (config.pauseOnHover) {

                host.$container.hover(function(evt) {
                    host.paused = true;
                }, function(evt) {
                    // because target can be child of evt set container
                    if (evt.currentTarget !== evt.target && host.$container.has(evt.target).length === 0) return;

                    // setTimeout interval 是为了确保自动播放的间隔不会小于间隔时间
                    setTimeout(function() {
                        host.paused = false;
                    }, config.interval * 1000);
                });
            }

            // 设置自动播放
            host.autoplayTimer = setInterval(function() {
                if (host.paused) return;
                host.switchTo(host.activeIndex < host.viewLength - 1 ? host.activeIndex + 1 : 0);
            }, config.interval * 1000, true);
        }
    });
})(jQuery);
// autoplay end

// effect start
(function($) {

    var Effects;

    var DISPLAY = 'display';
    var BLOCK = 'block';

    var OPACITY = 'opacity';

    var ZINDEX = 'z-index';
    var POSITION = 'position';
    var RELATIVE = 'relative';
    var ABSOLUTE = 'absolute';

    var SCROLLX = 'scrollx';
    var SCROLLY = 'scrolly';

    var NONE = 'none';
    var FADE = 'fade';
    var CLOSING = 'closing';
    var LINER = 'liner';
    var SWING = 'swing';

    var Switchable = $.able.Switchable;

    $.extend($.fn.switchable.defaults, {
        /**
         * @cfg String effect
         * 默认为none，即只是显示隐藏，目前支持的特效为scrollx、scrolly、fade或者自己直接传入特效函数。
         */
        effect: 'none',

        /**
         * @cfg Number duration
         * 默认为.5，动画的时长。
         */
        duration: 0.5,

        /**
         * @cfg String easing
         * 默认为liner，即线性的。
         */
        easing: LINER,

        /**
         * @cfg Boolean circle
         * 默认为false，如果设置为true，最后一帧到第一帧切换的时候会更加平滑。
         */
        circle: false
    });

    /**
     * 定义效果集
     */
    Switchable.Effects = {

        // 最朴素的显示/隐藏效果
        none: function(fromPanels, toPanels, callback) {
            $.each(fromPanels, function() {
                $(this).hide();
            });
            $.each(toPanels, function() {
                $(this).fadeIn();
            });
            callback();
        },

        // 互相靠拢效果
        closing: function(fromPanels, toPanels, callback) {
            // fromPanels
            $('.left-panel', $(fromPanels)).animate({left:"-=100px"}, 100, "swing");
            $('.right-panel', $(fromPanels)).animate({left:"+=100px"}, 100, "swing");
            $(fromPanels).hide();
            // toPanels
            $(toPanels).show();
            $('.left-panel', $(toPanels)).animate({opacity: 1, left:"+=100px"}, 1600, "swing");
            $('.right-panel', $(toPanels)).animate({opacity: 1, left:"-=100px"}, 1600, "swing");
            callback();
        },

        // 淡隐淡现效果
        fade: function(fromPanels, toPanels, callback) {
            if(fromPanels.length !== 1) {
                return; //fade effect only supports step == 1.
            }
            var self = this, config = self.config,
                fromPanel = fromPanels[0], toPanel = toPanels[0];
            if (self.$anim) self.$anim.clearQueue();

            // 首先显示下一张
            $(toPanel).css(OPACITY, 1);

            // 动画切换
            self.$anim = $(fromPanel).animate({
                opacity: 0,
                duration: config.duration,
                easing: config.easing
            }, function() {
                self.$anim = null; // free

                // 切换 z-index
                $(toPanel).css(ZINDEX, 9);
                $(fromPanel).css(ZINDEX, 1);
                callback();
            });
        },

        // 水平/垂直滚动效果
        scroll: function(fromPanels, toPanels, callback, index) {
            var self = this, config = self.config,
                isX = config.effect === SCROLLX,
                diff = self.viewSize[isX ? 0 : 1] * index,
                attributes = {};

            var $first, _diff;
            if (config.circle && index == 0 && self.activeIndex == self.viewLength-1) {
                $first = $(toPanels);
                if (!self.$container.data('switchables-circle_appended')) {
                    $first.parent().append( $first.clone() );
                    self.$container.data('switchables-circle_appended', true);
                    if (isX)
                        $first.parent().css('width', self.viewSize[0] * (self.viewLength +1) + 'px');
                }
                _diff = diff;
                diff = self.viewSize[isX ? 0 : 1] * self.viewLength;
            }

            attributes[isX ? 'left' : 'top'] = -diff;
            $.extend(attributes, {
                duration: config.duration,
                easing: config.easing
            });

            if (self.$anim) self.$anim.clearQueue();

            self.$anim = self.$panels.parent().animate(attributes, function() {
                if ($first) {
                //  $first.parent().children(':gt('+ (self.viewLength-1) +')').remove();  //# IE8 下会有显示问题，若先设置 top 再删除多余元素
                    $first.parent().css( isX ? 'left' : 'top', _diff);
                    $first = null;
                }
                self.$anim = null; // free
                callback();
            });
        }

    };
    Effects = Switchable.Effects;
    Effects[SCROLLX] = Effects[SCROLLY] = Effects.scroll;

    Switchable.Plugins.push({
        name: 'effect',

        /**
         * 根据 effect, 调整初始状态
         */
        _init: function(host) {
            var config = host.config,
                effect = config.effect,
                $panels = host.$panels,
                step = config.step,
                activeIndex = host.activeIndex,
                fromIndex = activeIndex * step,
                toIndex = fromIndex + step - 1,
                panelLength = $panels.length;

            // 1. 获取高宽
            host.viewSize = [
                config.viewSize[0] || $panels.outerWidth() * step,
                config.viewSize[1] || $panels.outerHeight()* step
            ];

            $panels.first().animate({left:"+=200px"}, 1600, "swing");
            // 注：所有 panel 的尺寸应该相同
            //    最好指定第一个 panel 的 width 和 height，因为 Safari 下，图片未加载时，读取的 offsetHeight 等值会不对

            // 2. 初始化 panels 样式
            if (effect !== NONE) { // effect = scrollx, scrolly, fade
                // 这些特效需要将 panels 都显示出来
                $panels.css(DISPLAY, BLOCK);

                switch (effect) {

                    // 如果是滚动效果
                    case SCROLLX:
                    case SCROLLY:
                        // 设置定位信息，为滚动效果做铺垫
                        $panels.parent().css('position', ABSOLUTE);
                        $panels.parent().parent().css('position', RELATIVE); // 注：content 的父级不一定是 container

                        // 水平排列
                        if (effect === SCROLLX) {
                            $panels.css('float', 'left');

                            // 设置最大宽度，以保证有空间让 panels 水平排布
                            $panels.parent().css('width', host.viewSize[0] * host.viewLength + 'px');
                        }
                        break;

                    // 如果是透明效果，则初始化透明
                    case FADE:
                        $panels.each(function(index) {
                            $(this).css({
                                opacity: (index >= fromIndex && index <= toIndex) ? 1 : 0,
                                position: ABSOLUTE,
                                zIndex: (index >= fromIndex && index <= toIndex) ? 9 : 1
                            });
                        });
                        break;

                    case CLOSING:
                        $panels.css(DISPLAY, NONE);
                        $panels.first().css(DISPLAY, BLOCK);
                        $('.left-panel', $panels.first()).animate({left:"+=100px"}, 1600, "swing");
                        $('.right-panel', $panels.first()).animate({left:"-=100px"}, 1600, "swing");
                        break;
                }
            }

            // 3. 在 CSS 里，需要给 container 设定高宽和 overflow: hidden
            //    nav 的 cls 由 CSS 指定
        }
    });

    /**
     * 覆盖切换方法
     */
    $.extend(Switchable.prototype, {
        /**
         * 切换视图
         */
        _switchView: function(fromPanels, toPanels, index) {
            var self = this, config = self.config,
                effect = config.effect,
                fn = $.isFunction(effect) ? effect : Effects[effect];

            fn.call(self, fromPanels, toPanels, function() {}, index);
        }
    });
})(jQuery);
// effect end

// lazyload-ext start
(function($) {
    var DATA_TEXTAREA = 'data-textarea', DATA_IMG = 'data-img',
        EVENT_BEFORE_SWITCH = 'beforeSwitch';

    $.extend($.fn.switchable.defaults, {
        /**
         * @cfg Boolean lazyload
         * 默认为false，即不延迟加载。
         */
        lazyload: false,

        /**
         * @cfg String lazyDataType
         * 默认为data-img，目前支持图片延迟加载，将来支持文本数据和脚步延迟加载。
         */
        lazyDataType: DATA_IMG // or DATA_TEXTAREA

    });

    var Switchable = $.able.Switchable;

    Switchable.Plugins.push({
        name: 'lazyload',

        _init: function(host) {
            var config = host.config;
            if (!config.lazyload) return;

            var panels = $.makeArray(host.$panels);
            var type = config.lazyDataType;

            host.$evtBDObject.bind(EVENT_BEFORE_SWITCH, loadLazyData);

            /**
             * 加载延迟数据
             */
            function loadLazyData(evt, index) {
                var step = config.step,
                    begin = index * step,
                    end = begin + step;

                $.loadCustomLazyData(panels.slice(begin, end), type);

                if (isAllDone()) {
                    host.$evtBDObject.unbind(EVENT_BEFORE_SWITCH, loadLazyData);
                }
            }

            /**
             * 是否都已加载完成
             */
            function isAllDone() {
                var $imgs, isDone = true;

                if (type === DATA_IMG) {

                    $imgs = panels[0].nodeName == 'IMG' ? host.$panels : host.$panels.find('img');

                    $imgs.each(function() {
                        if (this.getAttribute(type)) {
                            isDone = false;
                            return false;
                        }

                    });
                }
                // TODO textarea

                return isDone;
            }
        }
    });

})(jQuery);
// lazyload-ext end
