;
/**
* selecter 对象,含多選CheckBox，單選Radio功能
* <pre>
* 功能：
*v1.CheckBox；
*v2.Radio
*x3.
*v4.
*5.
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
* @module myui.selecter
* @namespace myui
*/
(function($) {
    //第一个叶签为固定首页，从2开始起跳
    var tabCounter = 2;

    $.widget("myui.selecter", {
        /**
        * 配置属性对象
        * <pre>
        * var cfg = {
        *     name: '',//grid名称
        * };
        * </pre>
        * @property options
        */
        options: {
            apid: ''
            , tabsMax: 2 //0代表不限制
        }
        /**
        * 版本
        * @property version
        */
        , version: "1.0.0"
        /** 
        * 建立grid對象
        * @method _create
        */
        , _create: function() {
            this._init();
        }

        /** 
        * 取得grid对象ID
        * @method _gridId
        */
        , _gridId: function() {
            //return a.title && a.title.replace(/\s/g, '_').replace(/[^A-Za-z0-9\-_:\.]/g, '') ||
            //this.options.idPrefix + (++gridId);
            return this.options.idPrefix + (this.element.attr("id"));
        }

        /** 
        * 初始化
        * @method _init
        */
        , _init: function() {
            var selecterTmpl = '<span class="ui-icon"></span>';
            
        }
        /** 
        * 添加Tab頁
        * @method addTab
        * @param tag_A {Object} A 標籤對象
        * @param tabs_id {String} 頁簽組的ID
        * @param o_options {Object} {max:最大開啟的頁簽數，0代表不限制；default為2}
        */
		, addTab: function(tag_A, tabs_id, o_options) {
		    var tabTemplate = "<li><a href='#{href}'>#{label}</a> <span id='#{btn_id}' class='ui-icon ui-icon-close'>Remove Tab</span></li>",
			    isAppend = false, //是否添加
		        tabs = $("#" + tabs_id).tabs({
		            beforeLoad: function(event, ui) {
		                ui.jqXHR.error(function() {
		                    ui.panel.html("Couldn't load this tab. We'll try to fix this as soon as possible.If this wouldn't be a demo.");
		                });
		            }
		            //,show: { effect: "blind", duration: 800 }       
		        }),
			    label = $(tag_A).text(),
				id = tabs_id + "-" + tabCounter,
				li = $(tabTemplate.replace(/#\{href\}/g, "#" + id).replace(/#\{label\}/g, label).replace(/#\{btn_id\}/g, "btn-" + id)),
				url = $(tag_A).attr("href"),
				tabContentHtml = '';


		    //	
		    if (typeof o_options != 'undefined' && typeof o_options.append != 'undefined') {
		        isAppend = o_options.append;
		    }

		    //首字母為#	
		    if (url.substr(0, 1) == "#") {
		        url = url.substr(url.indexOf('#') + 1);
		    } else {

		        $(tag_A).attr("href", "#" + url);
		    }

		    var index = this.getExistTab(url, tabs_id);
		    //URL没有开启
		    if (index == 0) {
		        tabContentHtml = '<iframe  src="' + url + '" style="width:100%;height:100%" frameborder="0"></iframe>';
		        //第一个为Home
		        if (tabCounter <= this.options.tabsMax + 1) {
		            tabs.find(".ui-tabs-nav").append(li);
		            tabs.append("<div id='" + id + "'>" + tabContentHtml + "</div>");
		            $('#' + id).find('iframe').css({ height: $('.ui-layout-center').height() - 65 + 'px', 'padding': '0px' })
		            tabs.tabs("refresh");
		            index = tabCounter - 1;
		            tabCounter++;

		            $("#btn-"+id).bind("click", function() {
		                var panelId = $(this).closest("li").remove().attr("aria-controls");
		                $("#" + panelId).remove();
		                $("#tabs").tabs("refresh");

		                if (tabCounter > 2) tabCounter--;
		                //alert(tabCounter);
		            });		            
 	            
		        } else {
		            index = tabCounter - 1;
		            $('#' + tabs_id + '-' + index).find('iframe').attr('src', url);
		            $('#' + tabs_id + ' ul li:last-child').find('a').text(label);
		            tabs.tabs("refresh");
		        }
		    }
		    tabs.tabs("option", "active", index);

		}
		/** 
		* 依照URL取得是否有打開的Tab，若沒有返回null,有則返回Index。
		* @method getExistTab
		*/
        , closeTab: function(url, tab_id) {
            var index = 0;
            var tabs = $("#" + tab_id + " div iframe");
            //alert(url + tabs.length);
            //當有../或..\時，將其替換成空
            var newUrl = url.replace(/((\.){2}\/)|((\.){2}\\)/g, '');
            for (var i = 0; i < tabs.length; i++) {
                if (tabs[i].src.indexOf(newUrl, 0) >= 0) {
                    index = i;
                }
            }
            return index;
        }        

        /** 
        * 依照URL取得是否有打開的Tab，若沒有返回null,有則返回Index。
        * @method getExistTab
        */
        , getExistTab: function(url, tab_id) {
            var index = 0;
            var tabs = $("#" + tab_id + " div iframe");
            //alert(url + tabs.length);
            //當有../或..\時，將其替換成空
            var newUrl = url.replace(/((\.){2}\/)|((\.){2}\\)/g, '');
            for (var i = 0; i < tabs.length; i++) {
                if (tabs[i].src.indexOf(newUrl, 0) >= 0) {
                    index = i;
                }
            }
            return index;
        }

    });
})(jQuery);