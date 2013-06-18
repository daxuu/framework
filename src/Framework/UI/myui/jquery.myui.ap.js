;
/**
* ap 对象,包含AP級別的功能，如Tabs管理
* <pre>
* 功能：
*v1.可上傳文件，並在同一頁面上傳多個文件；
*v2.可移除文件
*x3.可限制文件類型與大小
*v4.有進度條功能
*5.可選擇是保存在DB中還是在Disk中
* [Depends:]
*	1.jquery.ui.core.js
*	2.jquery.ui.widget.js
*   3.jquery.myui.core.js
*   4.jquery.myui.combo.js
*
*  [Sample code:]
*  var gv_account = $('#gv_account');
*  gv_account.grid({
*     url: 'account.aspx'
*    ,fixCols:4 //固定左边4栏
*    ,name:'帐号'//grid名称
*    ,buttonText:true//按钮显示图与文
*    ,readonly:true//只读
*    ,single: false //只允许单选行，即checkbox 为只读状态
*    ,condCol: true//显示条件栏
*    ,toolbarItem: ['p', 'q']//工具栏提供的按钮。p:page页次信息；q:query查询；a:add row添加行；r:remove rows移除行；:save保存；e:export导出
*  });
*
* 
* </pre>
*
* @class form
* @module myui.form
* @namespace myui
*/
(function($) {
    //第一个页签为固定首页，从2开始起跳
    var tabCounter = 2;
    $.myui.ap = {
        /** 
        * 開啟信息對話框。
        * @method win
        * @param cfg {object} 配置類
        * 【eg.】
        * var cfg = {
        * id:'w-001'
        * ,title:''
        * ,msg:''
        * ,message:''
        * ,width:500
        * ,height:200
        * ,parent:null //母對象，如Grid
        * ,buttons:[{id:2,name:'Save',fn:null},{id:1,name:'Cancel',fn:null}]
        * }
        * @param callback {function} 回調函數
        */
        win: function(cfg, fn_callback) {
            var ret = null
            , tip
            , message
            , url = '#'
            , tagMessage
            , width = 380
            , height = 100
            , parent = null
            , main
            , id = '_content'
            , buttons = {}
            , btn = [];

            tip = $.myui.safeStr(cfg.msg);
            message = $.myui.safeStr(cfg.message);
            if (typeof cfg.url != 'undefined') {
                url = cfg.url;
            }
            if (typeof cfg.height != 'undefined') {
                height = cfg.height;
            }
            if (typeof cfg.width != 'undefined') {
                width = cfg.width;
            }

            if (typeof cfg.height != 'undefined') {
                height = cfg.height;
            }
            if (typeof cfg.parent != 'undefined') {
                parent = cfg.parent;
            }
            if (typeof cfg.id != 'undefined') {
                id = cfg.id;
            }
            if (typeof cfg.buttons != 'undefined') {
                btn = cfg.buttons;

                for (var m = 0; m < btn.length; m++) {
                    switch (btn[m].id) {
                        case 3:
                            break;
                        case 2:
                            buttons[btn[m].name] = ok;
                            break;
                        case 1:
                            buttons[btn[m].name] = cancel;
                            break;
                        case 0:
                            //buttons[btn[m].name] = ok;
                            break;
                        default:
                            buttons['OK'] = ok;
                            break;
                    }
                }

            }


            main = '<div id="dialog-message" title="" style="display:none"><iframe id="{0}" name="{0}" src="{1}" style="width: 100%; height: 100%;" frameborder="0"></iframe></div>'

            main = main.format(cfg.id, url);
            //var win = $(main);
            //建立主Dialog

            
            
            // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
            //$("#dialog").dialog("destroy");
            //var $dialog = $("#dialog-message").dialog({
            var $dialog = $(main).win({
                modal: false,
                autoOpen: false,
                closeOnEscape: true,
                width: width,
                height: height,
                resizable: true,
                //show: 'explode',
                //hide: 'explode',
                buttons: buttons,
                title: cfg.title
            });
            
            
            $dialog.win('open');
            $dialog.css({ height: height });
            
            
            /*
		    //dialog options
		    var dialogOptions = {
			    "title" : "dialog@" + new Date().getTime(),
			    "width" : 400,
			    "height" : 300,
			    "modal" : false,
			    "resizable" :true,
			    "close" : function(){}
		    };
		    // dialog-extend options
		    var dialogExtendOptions = {
			    "close" : true,
			    "maximize" : true,
			    "minimize" : true,
			    "dblclick" : 'maximize',
			    "titlebar" : false
		    };            
            
		    // open dialog
		    $("#dialog-message").dialog(dialogOptions).dialogExtend(dialogExtendOptions);
            */

            //sub function
            function ok() {
                var ret = true;
                $dialog.win('close');
                //invoke callback function
                if (typeof fn_callback == 'function') {
                    fn_callback.apply(this, [ret, parent]);
                }

            };
            //sub function
            function cancel() {
                var ret = false;
                $dialog.win('close');
                if (typeof fn_callback == 'function') {
                    fn_callback.apply(this, [ret, parent]);
                }

            };
        }    
    };
    
//    $.widget("myui.ap", {
//        /**
//        * 配置属性对象
//        * <pre>
//        * var cfg = {
//        *     name: '',//grid名称
//        * };
//        * </pre>
//        * @property options
//        */
//        options: {
//            apid: ''
//            , tabsMax: 2 //0代表不限制
//        }
//        /**
//        * 版本
//        * @property version
//        */
//        , version: "1.0.0"
//        /** 
//        * 建立grid對象
//        * @method _create
//        */
//        , _create: function() {
//            this._init();
//        }

//        /** 
//        * 取得grid对象ID
//        * @method _gridId
//        */
//        , _gridId: function() {
//            //return a.title && a.title.replace(/\s/g, '_').replace(/[^A-Za-z0-9\-_:\.]/g, '') ||
//            //this.options.idPrefix + (++gridId);
//            return this.options.idPrefix + (this.element.attr("id"));
//        }

//        /** 
//        * 初始化
//        * @method _init
//        */
//        , _init: function() {

//        }
//        /** 
//        * 添加Tab頁
//        * @method addTab
//        * @param tag_A {Object} A 標籤對象
//        * @param tabs_id {String} 頁簽組的ID
//        * @param o_options {Object} {max:最大開啟的頁簽數，0代表不限制；default為2}
//        */
//		, addTab: function(tag_A, tabs_id, o_options) {
//		    var tabTemplate = "<li><a href='#{href}'>#{label}</a> <span id='#{btn_id}' class='ui-icon ui-icon-close'>Remove Tab</span></li>",
//			    isAppend = false, //是否添加
//		        tabs = $("#" + tabs_id).tabs({
//		            beforeLoad: function(event, ui) {
//		                ui.jqXHR.error(function() {
//		                    ui.panel.html("Couldn't load this tab. We'll try to fix this as soon as possible.If this wouldn't be a demo.");
//		                });
//		            }
//		            //,show: { effect: "blind", duration: 800 }       
//		        }).css({ padding: 0 }),
//			    label = $(tag_A).text(),
//				id = tabs_id + "-" + tabCounter,
//				li = $(tabTemplate.replace(/#\{href\}/g, "#" + id).replace(/#\{label\}/g, label).replace(/#\{btn_id\}/g, "btn-" + id)),
//				url = $(tag_A).attr("href"),
//				tabContentHtml = '';


//		    //	
//		    if (typeof o_options != 'undefined' && typeof o_options.append != 'undefined') {
//		        isAppend = o_options.append;
//		    }

//		    //首字母為#	
//		    if (url.substr(0, 1) == "#") {
//		        url = url.substr(url.indexOf('#') + 1);
//		    } else {

//		        $(tag_A).attr("href", "#" + url);
//		    }

//		    var index = this.getExistTab(url, tabs_id);
//		    //URL没有开启
//		    if (index == 0) {
//		        tabContentHtml = '<iframe  src="' + url + '" style="width:100%;height:100%" frameborder="0"></iframe>';
//		        //第一个为Home
//		        if (tabCounter <= this.options.tabsMax + 1) {
//		            tabs.find(".ui-tabs-nav").append(li);
//		            tabs.append("<div id='" + id + "'>" + tabContentHtml + "</div>");
//		            $('#' + id).css({padding:0});
//		            $('#' + id).find('iframe').css({ height: $('.ui-layout-center').height() - 30 + 'px', 'padding': '0px' })
//		            tabs.tabs("refresh");
//		            index = tabCounter - 1;
//		            tabCounter++;

//		            $("#btn-" + id).bind("click", function() {
//		                var panelId = $(this).closest("li").remove().attr("aria-controls");
//		                $("#" + panelId).remove();
//		                $("#tabs").tabs("refresh");

//		                if (tabCounter > 2) tabCounter--;
//		                //alert(tabCounter);
//		            });

//		        } else {
//		            index = tabCounter - 1;
//		            $('#' + tabs_id + '-' + index).find('iframe').attr('src', url);
//		            $('#' + tabs_id + ' ul li:last-child').find('a').text(label);
//		            tabs.tabs("refresh");
//		        }
//		    }
//		    tabs.tabs("option", "active", index);

//		}
//        /** 
//        * 依照URL取得是否有打開的Tab，若沒有返回null,有則返回Index。
//        * @method getExistTab
//        */
//        , closeTab: function(url, tab_id) {
//            var index = 0;
//            var tabs = $("#" + tab_id + " div iframe");
//            //alert(url + tabs.length);
//            //當有../或..\時，將其替換成空
//            var newUrl = url.replace(/((\.){2}\/)|((\.){2}\\)/g, '');
//            for (var i = 0; i < tabs.length; i++) {
//                if (tabs[i].src.indexOf(newUrl, 0) >= 0) {
//                    index = i;
//                }
//            }
//            return index;
//        }

//        /** 
//        * 依照URL取得是否有打開的Tab，若沒有返回null,有則返回Index。
//        * @method getExistTab
//        */
//        , getExistTab: function(url, tab_id) {
//            var index = 0;
//            var tabs = $("#" + tab_id + " div iframe");
//            //alert(url + tabs.length);
//            //當有../或..\時，將其替換成空
//            var newUrl = url.replace(/((\.){2}\/)|((\.){2}\\)/g, '');
//            for (var i = 0; i < tabs.length; i++) {
//                if (tabs[i].src.indexOf(newUrl, 0) >= 0) {
//                    index = i;
//                }
//            }
//            return index;
//        }

//    });
})(jQuery);