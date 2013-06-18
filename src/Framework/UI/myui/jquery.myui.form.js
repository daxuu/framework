;

(function($) {
    var gridId = 0,
    ajaxUrl = '',
	listId = 0,
    /*
    p:page
    q:query
    a:add row
    r:remove row
    s:save
    e:export
    */
    defItem = ['p', 'q', 'a', 'r', 's', 'e'],
    checkWidth = 36//行首CheckBox的宽度,最小36px方可显示Scroll箭头
    , open_class = 'ui-icon-triangle-1-e', close_class = 'ui-icon-triangle-1-s';

    $.widget("myui.form", {
        //config
        options: {
            name: ''
            , url: '' //default url is current page url
            , checker: []//输入资料校验对象集合
            , columns: [] //资料输入栏集合
            , buttons: [] //动作集合
            , className: ""  //数据接收类别
            , labelWidth:80  //label width
            , inputWidth:120 //input width
            , nonepic:"../../images/none.jpg"
            
        }
        //初始化
        , _create: function() {
            //clear CData
            this.CData = {};
            this.formid = this.element.attr("id");
            this.format();
            //this.format();


        }

        /**
        * 版本
        * @property version {string}
        */
        , version: '1.0.20121119'

        /**
        * form id
        * @property formid {string}
        */
        , formid: ''

        /**
        * Changed Data
        * @property version {Object} 
        */
        , CData: {}

        //取得ID      
        , getId: function() {

            return this.element.attr("id");
        }
        

        /** 
        * 保存资料到DB中
        * @method save
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。
        */
	    , save: function(event) {
	        var form = ((typeof event != 'undefined' && typeof event.data.form != 'undefined') ? event.data.form : this),
	        ret = null, data, fields;
	        //资料有异动
	        if (form.isChanged()) {
	            data = $.myui.packArgs(form.CData, 'record');
	            fields = $.myui.makeFields(form.options.columns,true);
	            ret = $.myui.invoke(form.options.className + ".Save", [data, fields],
                {
                    id: form.formid
                  , url: form.options.url
                });

	        } else {
	            return false;
	        }

	        //6个0代表成功
	        if (ret.code == "000000") {
	            $.myui.msg(ret.msg, false, 'n');
	            //clear CData of client
	            form.CData = {};
	        } else {
	            if (typeof ret == "string") {
	                $.myui.msg(ret, 'w');
	            } else {
	                $.myui.msg(ret.msg, 'w');
	            }
	        }

	    }

        /** 
        * 绑定资料到画面中
        * @method bind
        * @param o_data {Object} 值对象。ex. fm_proj.form("bind",{proj_id:"27F94E0B-076C-5060-4628-EA2F8F2FC644",proj_no:"P201008a55-test"});
        */
	    , bind: function(o_data) {
	        var form = this,
	        ret = null, elm;
	        //form资料有异动
	        if (!form.isChanged()) {
	            for (var name in o_data) {
	                elm = $("#" + form.formid + "_" + name);
	                if (elm.length == 1) {
	                    //checkbox
	                    if(elm.attr("type")!='undefined' && elm.attr("type")=='checkbox'){
	                        //checked
	                        if(elm.attr("checkbox")==o_data[name]){
	                            elm.attr("checked","true");
	                        }else{
	                            elm.removeAttr("checked");
	                        }
	                    }else if(elm.is('img')){
	                        elm.attr('src',o_data[name]==""?form.options.nonepic:o_data[name]);
	                    }else{
	                        elm.val(o_data[name]);
	                    }
	                }
	            }
	        } else {
                $.myui.msg(form.options.name + ":资料有异动，请先保存或取消！", false, 'n');
	            return false;
	        }
	    }
        /** 
        * 绑定资料到画面中
        * @method add
        * @param o_data {Object} 值对象。ex. fm_proj.form("add",{proj_id:"27F94E0B-076C-5060-4628-EA2F8F2FC644",proj_no:"P201008a55-test"});
        */
	    , add: function(o_data) {
	        var form = this,
	        ret = null,
	        data = {},
	        elm;
	        //form资料有异动
	        if (!form.isChanged()) {
	            if (typeof o_data == "undefined") {
	                //每欄
	                $.each(form.options.columns, function(i, o) {
	                    data[o.id] = "";
	                    //key
	                    if (typeof o.iskey != 'undefined' && (o.iskey == "1" || o.iskey == 1)) {
	                        keyValue = $.myui.guid();
	                        data[o.id] = keyValue;
	                    }
	                    if (typeof o.defaultValue == 'string') {
	                        switch (o.defaultValue) {
	                            case "$GUID":
	                                data[o.id] = $.myui.guid();
	                                break;
	                            case "$LAST":
	                                if (typeof form.getData()[o.id] != "undefined") {
	                                    data[o.id] = form.getData()[o.id];
	                                }
	                                break;
	                            default:
	                                data[o.id] = o.defaultValue;
	                                break;
	                        }
	                    }
	                    //調用function來取得Default值；
	                    if (typeof o.defaultValue == 'function') {
	                        data.record[o.id] = o.defaultValue.call(form, o.id, form.options.columns, form.getData());
	                    }


	                });
	            } else {
	                data = o_data;
	            }

	            form.setValue(data, 'add');
	        } else {
                $.myui.msg(form.options.name + ":资料有异动，请先保存或取消！", false, 'n');

	            return false;
	        }
	    }
        /** 
        * 绑定资料到画面中
        * @method remove
        * @param o_data {Object} 值对象。ex. fm_proj.form("add",{proj_id:"27F94E0B-076C-5060-4628-EA2F8F2FC644",proj_no:"P201008a55-test"});
        */
	    , remove: function(o_data) {
	        var form = this,
	        ret = null, elm;
	        //form资料有异动
	        if (!form.isChanged()) {
	            if (window.confirm($.myui.lang('cfm_del'))) {

	                form.setValue(o_data, 'remove');

	            }
	        } else {

	            return false;
	        }
	    }

        /** 
        * 绑定资料到画面中
        * @method remove
        * @param o_data {Object} 值对象。ex. fm_proj.form("add",{proj_id:"27F94E0B-076C-5060-4628-EA2F8F2FC644",proj_no:"P201008a55-test"});
        */
	    , cancel: function(o_data) {
	        var form = this,
	        ret = null, elm;
	        
	        form.CData ={};

	    }
        /** 
        * 判断资料是否有异动
        * @method isChanged
        */
	    , isChanged: function() {
	        return !$.isEmptyObject(this.CData);
	    }

        //提示
        , tips: function(msg) {
            var tips = $('#' + this.getId() + '-tip');

            if (tips.length == 0) {
                tips = $('#' + this.getId() + ' >p');
            }
            tips.text(msg)
			    .addClass('ui-state-error');

            setTimeout(function() {
                tips.removeClass('ui-state-error', 1500);
            }, 500);
        }

        /** 
        * 格式化:栏位集合,Options與Form Input元素相結合的結果
        * @method format
        */
        , format: function() {

            var fld, idx, opt
            , frm = this
            , inputs = $('#' + frm.formid + ' input,textarea,img')
            , cls = 'text ui-widget-content ui-corner-all'
            , oInput, oButton, oButtons, oFieldSet
            , labelWidth = 85;

            //options labelWidth
            if (typeof frm.options.labelWidth != 'undefined') {
                labelWidth = frm.options.labelWidth;
            }

            //找到輸入對象，將其附加到columns中；
            for (var i = 0; i < inputs.length; i++) {
                fld = inputs.eq(i);
                opt = {};
                opt["id"] = fld.attr("name");
                //是否存在配置对象
                idx = $.myui.inArray(frm.options.columns, { id: fld.attr("name") });
                
                //iskey
                if (typeof fld.attr("iskey") == "string") {
                    opt["iskey"] = fld.attr("iskey");
                }
                opt["type"] = (typeof fld.attr("datatype") == "undefined") ? "string" : fld.attr("datatype");
                //opt["size"] = (typeof fld.attr("size") == "undefined") ? frm.options.inputWidth : fld.attr("size");
                opt["hidden"] = (typeof fld.attr("type") == "undefined") ? "0" : "1";
                //size
                if (typeof fld.attr("size") == "string") {
                    opt["size"] = fld.attr("size");
                }else{
                    //在Config中设定了Size
                    if(idx>=0 && typeof frm.options.columns[idx]["size"]!='undefined'){
                        opt["size"] = frm.options.columns[idx]["size"];
                    }else{
                        opt["size"] =frm.options.inputWidth;
                    }
                }
                //devault value
                if (typeof fld.attr("defaultvalue") == "string") {
                    opt["defaultValue"] = fld.attr("defaultvalue");
                }
                //min
                if (typeof fld.attr("min") == "string") {
                    opt["min"] = fld.attr("min");
                }
                //date
                if (typeof fld.attr("date") == "string") {
                    if(fld.attr("date") != ""){
                        opt["date"] = {format:fld.attr("date")};
                    }else{
                        opt["date"] = {};
                    }
                }
                //checkbox
                if (typeof fld.attr("type") !='undefined' && fld.attr("type") =='checkbox') {
                    if(typeof fld.attr("checkbox") == "string"){
                        opt["checkbox"] = fld.attr("checkbox");
                    }else{
                        opt["checkbox"] = "Y";
                    }
                    if(typeof fld.attr("uncheckbox") == "string"){
                        opt["uncheckbox"] = fld.attr("uncheckbox");
                    }else{
                        opt["uncheckbox"] = "N";
                    }                    
                }

                                
                

                //not find
                if (idx < 0) {
                    frm.options.columns.push(opt);
                } else {
                    //copy options to cloumns
                    for (var name in opt) {
                        frm.options.columns[idx][name] = opt[name];
                    }
                }
            }

            oButtons = $("#" + frm.formid).find("button");
            for (var i = 0; i < oButtons.length; i++) {
                fld = oButtons.eq(i);
                opt = {};
                opt["id"] = fld.attr("name");
                opt["text"] = (typeof fld.text() != "") ? "Button" : fld.text();
                opt["icon"] = (typeof fld.attr("icon") == "undefined") ? "" : fld.attr("icon");
                idx = $.myui.inArray(frm.options.buttons, { id: fld.attr("name") });

                //not find
                if (idx < 0) {
                    frm.options.buttons.push(opt);
                } else {
                    //copy options to buttons
                    for (var name in opt) {
                        frm.options.buttons[idx][name] = opt[name];
                    }
                }

            }

            //format
            //columns
            $.each(frm.options.columns, function(i, o) {
                oInput = $("#" + frm.formid).find("input[name=" + o.id + "],textarea[name=" + o.id + "],img[name=" +o.id+ "]");
                if (oInput.length == 1) {
                    oInput.attr("id", frm.formid + "_" + o.id);
                    oInput.addClass(cls);
                    oInput.data('old', oInput.val());
                    if(typeof o.date != 'undefined'){
                        o.date["id"] = oInput.attr("id");
                        $.myui.makeDatepicker(o.date); 
                    }
                    //alert(oInput.get(0).tagName); 
                    //排除textarea  oInput.is('input')也可判断
                    if(typeof o.size != 'undefined' && oInput.is('input')){
                        //checkbox width要减去5px，不然对不齐
                        if(typeof oInput.attr("type") != 'undefined' && oInput.attr("type")=="checkbox"){
                            oInput.css({"width":(o.size-5)+"px","display":"inline-block","height":"20px"});
                        }else{
                            oInput.css({"width":o.size+"px","display":"inline-block","height":"20px"});
                        }
                    }
                    //checkbox
                    if(typeof oInput.attr("type") != 'undefined' && oInput.attr("type")=="checkbox"){
                        if(typeof oInput.attr("checkbox")=='undefined') oInput.attr("checkbox","Y");
                        if(typeof oInput.attr("uncheckbox")=='undefined') oInput.attr("uncheckbox","N");
                    }
                    oInput.bind('change', function(event) {
                        //與初始值對比
                        //if($(this).data('old') != $(this).val()){
                        //alert('old value:' + $(this).data('old') + 'new value:' + $(this).val());
                        //frm._changed(event,{id:$(this).attr('id'),value:$(this).val(),old:$(this).data('old')});
                        //}
                        var data = {},value;
                        //checkbox
                        if($(this).attr("type")!='undefined' && $(this).attr("type")=='checkbox'){
                            if($(this).attr('checked')=='checked'){
                                value =  o.checkbox;
                            }else{
                                value =  o.uncheckbox;
                            }
                        }else{
                            value =  $(this).val();
                        }
                        //alert(value);
                        
                        data[$(this).attr('name')] = value;
                        //set value
                        frm.setValue(data);
                        //changed event
                        frm._changed(event, { name: $(this).attr('name'), value: value ,rowdata:frm.getData()});
                    });
                }
                //label
                var olabel = $("#" + frm.formid).find("label[for=" + o.id + "]");
                if (olabel.length == 1) {
                    olabel.css({ width: labelWidth, display: 'inline-block' });
                    olabel.text(olabel.text() + ":");
                }
                //fieldset
                var oFieldSet = $("#" + frm.formid).find("fieldset");
                if (oFieldSet.length > 0) {
                    oFieldSet.css({ "line-height": "35px" });
                }

            }); //end each columns
            //button
            $.each(frm.options.buttons, function(i, o) {
                oButton = $("#" + frm.formid).find("button[name=" + o.id + "]");

                if (oButton.length == 1) {
                    oButton.attr("id", frm.formid + "_" + o.id);
                    //alert(o.text);
                    switch (o.id) {
                        case "save":
                            o["icon"] = "ui-icon-disk";
                            break;
                        case "add":
                            o["icon"] = "ui-icon-plusthick";
                            break;
                        case "remove":
                            o["icon"] = "ui-icon-trash";
                            break;
                        case "cancel":
                            o["icon"] = "ui-icon-arrowreturnthick-1-w";
                            break;
                        default:
                            if (typeof o.icon == "undefined") {
                                o["icon"] = "";
                            }

                            break;
                    }
                    oButton.bind('click', function(event) {
                        //default save/add/cancel
                        switch (o.id) {
                            case "save":
                                if (typeof o.fn == "function") {
                                    o.fn.call(null, frm);
                                } else {
                                    frm.save();
                                }
                                break;
                            case "add":
                                if (typeof o.fn == "function") {
                                    o.fn.call(null, frm);
                                } else {
                                    frm.add();
                                }
                                break;
                            case "remove":
                                if (typeof o.fn == "function") {
                                    o.fn.call(null, frm);
                                } else {
                                    frm.remove();
                                }
                                break;
                            case "cancel":
                                if (typeof o.fn == "function") {
                                    o.fn.call(null, frm);
                                } else {
                                    frm.cancel();
                                }
                                break;
                            default:
                                if (typeof o.fn == "function") {
                                    o.fn.call(null, frm);
                                }
                                break;
                        }
                    });
                    oButton.button({
                        text: o.text,
                        icons: {
                            primary: o.icon
                        }
                    })
                }
            }); //end each buttons            

        }


        /** 
        * 取得值集合
        * @method data
        */
        , getData: function() {
            var ret = {}, form = this, eml;

            $.each(form.options.columns, function(i, o) {
                eml = $("#" + form.formid + "_" + o.id);
                //alert(eml.length);
                if (eml.length == 1) {
                    ret[o.id] = eml.val();
                }

            });
            return ret;
        }



        //数据校验,含空值/类型/长度/正则表达式
        , check: function() {
            var ret = false
            , obj
            , fldName
            , msg
            , chkFields = this.options.checker
            , max
            , min;

            for (var i = 0; i < chkFields.length; i++) {

                fldName = $('label[for=' + chkFields[i].id + ']').text();
                //alert($('label[for=' + chkFields[i].id + ']').text());
                max = chkFields[i].max;
                min = chkFields[i].min;
                msg = '栏位:' + fldName + '输入值的长度应介于' + chkFields[i].min + ' 与 ' + chkFields[i].max + '间.';

                if (typeof chkFields[i].id == 'string') {
                    obj = $('#' + chkFields[i].id);
                } else {
                    obj = chkFields[i].id;
                }


                /*regex check
                if (!(regexp.test(o.val()))) {
                o.addClass('ui-state-error');
                updateTips(n);
                return false;
                } else {
                return true;
                }
                */
                //alert($('label[for=' + n + ']').text());
                obj.removeClass("ui-state-error");
                if (obj.val().length > max || obj.val().length < min) {

                    obj.addClass('ui-state-error');
                    if (min == max) {
                        msg = '栏位"' + fldName + '"输入值的长度应为' + min + '.';
                    }
                    this.tips(msg);
                    return false;
                } else {
                    ret = true;
                }

            }
            return ret;
        }

        /** 
        * 设置字段值
        * @method setValue
        * @param values {Object} 『栏：值』格式的对象，如：{proj_id:'00120',proj_name:'test'}
        * @param type {string}  add/update/remove/cancel
        */
	    , setValue: function(values, s_type) {
	        var form = this;
	        //找Key值
	        $.each(form.options.columns, function(i, o) {
	            if (typeof o.iskey != "undefined" && (o.iskey == "1" || o.iskey == 1)) {
	                form.CData[o.id] = $("#" + form.formid + "_" + o.id).val();
	            }

	        });
	        if (typeof s_type == "undefined") {
	            //当新增后的修改，type不能变成"2"
	            if (typeof form.CData["myui_chg"] == "undefined") {
	                form.CData["myui_chg"] = "2";
	            }
	        } else {
	            //type
	            switch (s_type) {
	                case "add":
	                    form.CData["myui_chg"] = "1";
	                    break;
	                case "update":
	                    form.CData["myui_chg"] = "2";
	                    break;
	                case "remove":
	                    form.CData["myui_chg"] = "3";
	                    break;
	                default:
	                    break;
	            }
	        }
	        //删除时，只需要Key值
	        if (form.CData["myui_chg"] != "3") {
	            //多个值
	            for (var name in values) {
	                //更新到画面
	                $("#" + form.formid + "_" + name).val(values[name]);
	                //save new value to CData
	                form.CData[name] = values[name];

	            };
	        }
	    }
        /** 
        * 取得配置信息
        * @method getOptions
        * @return {object} 整个options 对象
        */
	    , getOptions: function() {
            return this.options;
	    }

        /** 
        * 某栏值发生异动时触发
        *  调用时，注意前面加'form'并全部用小写，eg. form.bind('formchanged',{},function(event,data){alert(data.name + "=" + data.value +" Other= " + rowdata.other_fld_name);});
        * @event changed
        * @param event {Object} 父事件的参数
        * @param data {Object} 传出的参数值：{name:"currFldName",value:"00120",rowdata:{id:"001",name:"rose"}}
        */
    , _changed: function(event,data) {
        //alert("rowselected trigger");
        this._trigger("changed", event, data);
    }


    });
})(jQuery);