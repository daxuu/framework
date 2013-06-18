;
/**
* COMBOBOX 对象
* [Update Log]
*1.20110112:修正清空時值不能提交問題：setValue方法
*2.20110126:取消nomatch設定，條件區不清除名稱/其他需清除；資料區全部清除，當然comit需為1
*
* <pre>
* [Depends:]
*	1.jquery.ui.core.js
*	2.jquery.ui.widget.js
*   3.jquery.myui.core.js
* </pre>
* @class combobox
* @module myui.combobox
* @namespace myui
*/   
(function($) {
    $.widget("myui.combobox", {
        /**
        * 配置属性对象
        * @property options {Object}
        * <pre>
        * [sample code]        
        *  var cfg = {
        *     id: 'editor'
        *     ,url: location.href //default url is current page url
        *     ,source: []
        *     ,width: '50px'
        *     ,minLength: 0
        *     ,delay: 0
        *     ,prefix: '' //Combo父畫面輸入框的ID前綴，指定以便Combo選中返回后找到更新的其他欄值
        *     ,config: null //当前Combo栏信息，含Combo的配置信息，來自PCM中
        *     ,parent: null //母页面对象，需要更新母对象的值。
        *     ,button: false //是否需在Input后显示ComboBox图示
        *     ,isCond: true //true:为条件区;false：资料区
        *     ,notmatch: 'data'//当自行输入的值不匹配MenuItem时的处理机制：'all'：全部清空，含资料区与条件区；'data'：只清空资料区（defalut）；'cond'：'只清空条件区'；'none'：全部不清空。
        * 
        * </pre>
        * 
        */ 
        options: {
            id: 'editor'
            ,url: location.href //default url is current page url
            ,fieldName:''
            ,source: []
            ,width: '50px'
            ,minLength: 0
            ,delay: 0
            //,maxRows: 12
            ,prefix: '' //Combo父畫面輸入框的ID前綴，指定以便Combo選中返回后找到更新的其他欄值
            ,config: null //当前Combo栏信息，含Combo的配置信息，來自PCM中
            ,parent: null //母页面对象，需要更新母对象的值。
            ,button: false //是否需在Input后显示ComboBox图示
            ,isCond: true //true:为条件区;false：资料区
            ,notmatch: 'data'//当自行输入的值不匹配MenuItem时的处理机制：'all'：全部清空，含资料区与条件区；'data'：只清空资料区（defalut）；'cond'：'只清空条件区'；'none'：全部不清空。

        }
        /**
        * 版本
        * @property version
        */ 
        ,version: "1.0.0"        
        /** 
        * 建立COMBO對象
        * @method _create
        */
        ,_create: function() {
            var self = this;
            //var select = this.element;
            //alert(self.options.id);
            var input = $("#" + self.options.id)
					.autocomplete({
					    source: function(request, response) {
					        //alert(request.term);
					        //alert(self.options.url);
					        var inData = {
					            word: request.term,
					            fld: self.options.config.id, //母页面Combo栏的ID
					            uid: self.options.parent.element.attr("id"), //母页面识别ID，eg.'gv_bigbug'
					            url: $.myui.getUrl(self.options.parent.options.url),
					            keyValue: ''
					        };
					        //alert(inData.url);
					        var ret = $.myui.request('g', inData, '../' + self.options.config.combo.path + '.aspx');
					        var records = [];
					        //单笔资料或无资料
					        if (typeof ret.xconfig == 'undefined' || ret.xconfig.records==null || typeof ret.xconfig.records.record.length == 'undefined') {
					            if(typeof ret.xconfig != 'undefined' && ret.xconfig.records!=null){
					                records.push(ret.xconfig.records.record);
					            }
					        } else {//多笔资料
					            records = ret.xconfig.records.record;
					        }
					        response($.map(records, function(item) {
					            var items = {id:'', label: '', value: '' };
					            var i = j = 0;
					            var fields = self.fields();
					            //loop ervey fields with set from pcm
					            for (var m = 0; m < fields.length; m++) {
					                items.id=m;
					                //at menu show item
					                if (typeof fields[m].label != 'undefined'
					                            && fields[m].label == '1') {
					                    if (i > 0) {
					                        items.label += ',';
					                    }
					                    items.label += item[fields[m].value];
					                    i++;
					                }
					                if ((typeof fields[m].val != 'undefined')
					                            && (fields[m].val == '1')) {
					                    if (j > 0) {
					                        items.value += ',';
					                    }
					                    items.value += item[fields[m].value];

					                    j++;
					                }

					                items[fields[m].name] = item[fields[m].value];
					            }
					            return items;
					        }))//end response


					    }, //end source
					    delay: this.options.delay,
					    change: function(event, ui) {
					        var noMath = self.options.notmatch;
                            var data = {};
					        //自行输入的值不匹配时，即没有从Menu中选取时
					        if (!ui.item) {
					        

					            if (typeof self.options.config.combo.nomatch != 'undefined' 
					            && typeof self.options.config.combo.nomatch.empty != 'undefined') {
					                noMath = self.options.config.combo.nomatch.empty;
					            }
					            //條件欄
                                if (self.options.isCond) {
                                    //data[self.options.fieldName] = $(this).val();
			                        //self.setValue(event, ui,false,$(this).val());
			                        self.setValue(event, ui,$(this).val());
			                    }else{
			                        $(this).val("");
			                        self.setValue(event, ui,$(this).val());
			                    }
                                
                                /*
					            // remove invalid value, as it didn't match anything
					            switch (noMath) {
					                case 'all'://全部清空，含资料区与条件区
					                    $(this).val("");
					                    self.setValue(event, ui,true);
					                    break;
					                case 'data'://资料区清空；条件区清空设定Commit栏值（以PCM 中commit="1"为清空对象，多为hidden的ID值）
					                    //条件区
					                    if (self.options.isCond) {
					                        data[self.options.fieldName] = $(this).val();
					                        self.setValue(event, ui,data);
					                    } else {//资料区
					                        $(this).val("");
					                        self.setValue(event, ui,true);
					                        
					                    }
					                    break;
					                case 'cond'://资料区保留;条件区清空
					                    //条件区
					                    if (self.options.isCond) {
                                            $(this).val("");					                    
					                        self.setValue(event, ui,true);
					                    } else {//资料区
					                        //$(this).trigger('change');
					                        //alert('name:'+ self.options.fieldName+ ' val:' + $(this).val());
					                        data[self.options.fieldName] = $(this).val();
					                        self.setValue(event, ui,data);
					                    }
					                
					                    break;
					                case 'none'://资料区与条件区均不清除，画面上的值也不清空，且可能更新值到后台
					                        data[self.options.fieldName] = $(this).val();
					                        self.setValue(event, ui,data);
					                
					                    break;
					                default:
					                    break;
					            }
                                */
					            return false;
					        }
					        /*
					        select.val(ui.item.id);
					        self._trigger("selected", event, {
					            item: select.find("[value='" + ui.item.id + "']")
					        });
					        */

					    },
					    minLength: this.options.minLength,
					    select: function(event, ui) {

					        self.setValue(event, ui);
					    },
					    open: function() {
					        $(this).removeClass("ui-corner-all").addClass("ui-corner-top");
					    },
					    close: function() {
					        $(this).removeClass("ui-corner-top").addClass("ui-corner-all");
					    }
					})//end autocomplete define
					.addClass("ui-widget ui-widget-content ui-corner-left")
					.css({ width: this.options.button ? (parseInt(this.options.width.substr(0, this.options.width.lastIndexOf('px'))) - 50) + 'px' : this.options.width }); //end input define

            //make button		
            if (this.options.button) {
                $("<button>&nbsp;</button>")
				.attr("tabIndex", -1)
				.attr("title", "Show All Items")
				.insertAfter(input)
				.button({
				    icons: {
				        primary: "ui-icon-triangle-1-s"
				    },
				    text: false
				}).removeClass("ui-corner-all")
				.addClass("ui-corner-right ui-button-icon")
				.click(function() {
				    // close if already visible
				    if (input.autocomplete("widget").is(":visible")) {
				        input.autocomplete("close");
				        return;
				    }
				    // pass empty string as value to search for, displaying all results
				    input.autocomplete("search", "");
				    input.focus();
				});
            } //end if button


        } //end _create

        /** 
        * 取得Combo设定的fld_value栏
        * @method fields
        * @return {Array} 设定的栏对象，可能有多个
        */
        , fields: function() {
            var self = this, fields = [];

            if (typeof self.options.config.combo.fld_value.length == 'undefined') {
                fields.push(self.options.config.combo.fld_value);
            } else {
                fields = self.options.config.combo.fld_value;
            }
            return fields;
        }
        /** 
        * 更新母体对象的对应栏值
        * @method setValue
        * @param event {object} 配置類
        * @param ui {object} 配置類
        * @param argIsEmpty {bool} 是否清空值
        */        
        , setValue: function(event, ui,value) {
            var self = this
            , fields = []
            , values = {}
            , val = ''
            , isEmpty = false;
            
            fields = self.fields();
            /*
            if(typeof value === 'string'){
                val = value;
            }
            取消此功能 2011-1-25
            else if(typeof argIsEmpty == 'object'){
                values = argIsEmpty;
                if (self.options.isCond) {
                    self.options.parent.setCond(values);
                } else {
                    //alert(values[fields[m].name]);
                    self.options.parent.setValue(values);
                }   
                return ;             
            }
            */
            
            for (var m = 0; m < fields.length; m++) {
            
                if (typeof fields[m].comit != 'undefined'
			        && fields[m].comit == '1') {
                    if (ui.item != null) {
                        //如是條件且設定cond=
                        if(self.options.isCond){
                            if(typeof fields[m].cond == 'undefined' || fields[m].cond == '1'){
                                val = ui.item[fields[m].name];
                                values[fields[m].name] = val;
                            }
                        }else{
                            val = ui.item[fields[m].name];
                            values[fields[m].name] = val;
                        }
                    }else{
                        //條件欄，清空所有Comit栏的值
                        if(self.options.isCond && typeof fields[m].label != 'undefined' && fields[m].label == '1')
			            {
                            values[fields[m].name]=value;
                        }else{
                            values[fields[m].name]='';
                        }
                    }
                }
            }
            //alert(values);
            if (self.options.isCond) {
                self.options.parent.setCond(values);
            } else {
                //alert(values[fields[m].name]);
                self.options.parent.setValue(values);
            }
            
        }
    }); //end $.widget

})(jQuery);