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
* @class combo
* @module myui.combo
* @namespace myui
*/   
(function($) {
    $.widget("myui.combo", {
        options: {
            id: 'editor'
            , url: location.href //default url is current page url
            , fieldName: ''
            , source: []
            , data: null
            , width: '50px'
            , minLength: 0
            , delay: 0
            //,maxRows: 12
            , prefix: '' //Combo父畫面輸入框的ID前綴，指定以便Combo選中返回后找到更新的其他欄值
            , config: null //当前Combo栏信息，含Combo的配置信息，來自PCM中
            , parent: null //母页面对象，需要更新母对象的值。
            , button: false //是否需在Input后显示ComboBox图示
            , isCond: true //true:为条件区;false：资料区
            , notmatch: 'data'//当自行输入的值不匹配MenuItem时的处理机制：'all'：全部清空，含资料区与条件区；'data'：只清空资料区（defalut）；'cond'：'只清空条件区'；'none'：全部不清空。

        }
        /**
        * 版本
        * @property version
        */
        , version: "1.0.0"
        /** 
        * 建立COMBO對象
        * @method _create
        */
        , _create: function() {
            var self = this;
            //var select = this.element;
            //alert(self.options.id);
            select = this.element.show(),
            wrapper = this.wrapper = $("<span id='ui-combobox-" + this.options.id + "'>")
						.addClass("ui-combobox")
						.insertAfter(select);

            var input = this.element
                                .click(function(event) {
                                    event.stopPropagation();
                                })
                                .appendTo(wrapper)
            //                    .appendTo(this.options.button ? wrapper : '')
					.autocomplete({
					    source: function(request, response) {
					        var ret = self.options.config.combo.source;
					        var inputdata = this._value();
					        var records = [];
					        //单笔资料或无资料
					        if (typeof ret.xconfig == 'undefined' || ret.xconfig.records == null || typeof ret.xconfig.records.record.length == 'undefined') {
					            if (typeof ret.xconfig != 'undefined' && ret.xconfig.records != null) {
					                records.push(ret.xconfig.records.record);
					            }
					        } else {//多笔资料
					            records = ret.xconfig.records.record;
					        }
					        var fields = self.fields()
					        response($.map(records, function(item) {
					            var items = { id: '', label: '', value: '' };
					            var i = j = 0;
					            //var fields = self.fields();
					            //loop ervey fields with set from pcm
					            for (var m = 0; m < fields.length; m++) {
					                items.id = m;
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
					            if ($.trim(inputdata) != "") {
					                if (items.label.indexOf(inputdata, 0) != -1) {
					                    return items;
					                }
					            }
					            else {
					                return items;
					            }
					        }), fields, inputdata)//end response


					    }, //end source
					    delay: this.options.delay,
					    change: function(event, ui) {
					        var noMath = self.options.notmatch;
					        var data = {};
					        //自行输入的值不匹配时，即没有从Menu中选取时
					        if (ui.item != null) {


					            if (typeof self.options.config.combo.nomatch != 'undefined'
					            && typeof self.options.config.combo.nomatch.empty != 'undefined') {
					                noMath = self.options.config.combo.nomatch.empty;
					            }
					            //條件欄
					            if (self.options.isCond) {
					                self.setValue(event, ui, $(this).val());
					            } else {
					                $(this).val("");
					                self.setValue(event, ui, $(this).val());
					            }


					            return false;
					        }


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
					        $(this).removeClass("ui-corner-top").addClass("ui-corner-all");
					    }
					})//end autocomplete define
					.addClass("ui-widget ui-widget-content ui-corner-left")
					.css({ width: this.options.button ? this.options.width - 30 + 'px' : this.options.width }); //end input define
            if (this.options.button) {
                wrappera = this.wrappera = $("<a>")
					.attr("tabIndex", -1)
					.attr("title", "Show All Items")
					.attr("iscond", this.options.isCond)
					.tooltip()
					.appendTo(wrapper)
					.button({
					    icons: {
					        primary: "ui-icon-triangle-1-s"
					    },
					    text: false
					})
					.removeClass("ui-corner-all")
					.addClass("ui-corner-right ui-combobox-toggle")

					.click(function(event) {
					    //					        if (input.autocomplete("widget").is(":visible")) {
					    //					            input.autocomplete("close");
					    //					            //removeIfInvalid(input);
					    //					            return;
					    //					        }
					    $(this).blur(function(event) {
					        input.blur();
					    });
					    var secre = input.val();
					    input.autocomplete("search", secre == "" ? " " : secre);
					    //					    input.autocomplete("search", " " );
					    //					    input.focus();

					    if (event.target.parentElement.attributes.iscond.value == 'false') {
					        event.stopPropagation();
					    }
					    //					     if (a.stopPropagation) a.stopPropagation();
					    //					     else a.cancelBubble = true;
					});
            }
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
        , setValue: function(event, ui, value) {
            var self = this
            , fields = []
            , values = {}
            , val = ''
            , isEmpty = false;

            fields = self.fields();
            for (var m = 0; m < fields.length; m++) {

                if (typeof fields[m].comit != 'undefined'
			        && fields[m].comit == '1') {
                    if (ui.item != null) {
                        //如是條件且設定cond=
                        if (self.options.isCond) {
                            if (typeof fields[m].cond == 'undefined' || fields[m].cond == '1') {
                                val = ui.item[fields[m].name];
                                values[fields[m].name] = val;
                            }
                        } else {
                            val = ui.item[fields[m].name];
                            values[fields[m].name] = val;
                        }
                    } else {
                        //條件欄，清空所有Comit栏的值
                        if (self.options.isCond && typeof fields[m].label != 'undefined' && fields[m].label == '1') {
                            values[fields[m].name] = value;
                        } else {
                            values[fields[m].name] = '';
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
            //            this.wrappera.remove();
            //            this.wrapper.remove();

        }

        , destroy: function() {
            //            alert("");
            //            this.wrapper.remove();
            //				//this.element.his();
            $.Widget.prototype.destroy.call(this);
        }
    }); //end $.widget

})(jQuery);
