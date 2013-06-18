;
/**
* CORE Helper functions
* <pre>
*[Update Log]
*   1.Rayd20100907：增加:invokeBLL。
*   2.補上Rayd增加的Date.Format函數。
*   3.Rayd20100915：增加:pos方法，在Grid.setShortcut方法中使用到。
*   3.Rayd20100915：增加:selectRange方法。
*   4.Rayd20100920：增加:print方法。
*   5.Rayd20101105：增加:cache方法。
*   5.Rayd20101226：增加:為實現起訖日期條件，覆寫$.datepicker._selectDay方法。
*   6.Rayd20110615：增加:notify方法，修改msg，增加調用notify方法的類別：u,更了grid.save成功時調用此方法。
*   7.Rayd20121106: 替换invoke方法，原来方法存在问题，没有使用
*   8.Rayd20121106: 添加getPath方法，取得虚拟目录名称
* [Depends:]
*	1.jquery.ui.core.js
*	2.jquery.ui.widget.js
*   3.jquery.myui.core.js
* </pre>
* @class myui
* @module myui.core
* @namespace myui
*
*/ 
(function($) {
    $.myui = {
        /**
        * 版本
        * @property version
        */
        version: "2.0.20110830"
        /** 
        * 发出Ajax请求，返回JSON资料
        * @method request
        * @param type {string} 请求类型
        * @param data {object} 提送数据，格式为{id:'gv_project',currPage:10}，传送过程中将被URLEncode编码，可能送中文、特殊字符，以下子符有通过测试：【"等于"\'1+1=3&4】eg.{ id: grid.element.attr("id"), page: location.href, data: '"等于"\'1+1=3&4'} //ex. id=gv_bigbug&page=http%3A%2F%2Flocalhost%3A8089%2Fpm%2Flang%2Fmain%2Ftested%2Fgrid.aspx
        * @param url {string} 请求的URL,可选，默認為當前頁面Href
        * @return {string} 返回JSON格式，或非JSON格式，如錯誤信息
        */
        , request: function(type, data, url, retJson) {

            var recData, ret, path, inData = {}, isRetJson = true;
            if (typeof data != 'undefined') {
                inData = data;
            }
            if (typeof url != 'undefined' && url != null) {
                path = url;
            } else {
                path = this.getUrl(location.href);
            }
            if (path.indexOf('rt=') < 0) {
                path += (path.indexOf('?') < 0 ? '?' : '&') + 'rt=' + (type ? type : 'q');
            }
            //this.msg(path, 'a');

            if (typeof retJson != 'undefined') {
                isRetJson = retJson;
            }
            //alert(url + type );
            recData = $.ajax({
                type: "POST",
                url: path,
                async: false,
                data: inData
            }).responseText;
            //this.msg(recData, 'a');

            if (recData == "") {
                //多语言处理待完善
                //alert("服务器没有返回任何资讯！");
                return false;
            }
            if (isRetJson && recData.substr(0, 1) == '{') {
                ret = $.parseJSON(recData);
            } else {
                ret = recData;
            }

            if (ret.msg && ret.msg.result == 'fail') {
                this.msg(ret.msg.message, false, 'w');
            }
            return ret;
        }
        /** 
        * 直接用Ajax调用类别库，有两种方式：
        * 1.固定法：调用当前类中InvokeBLL方法，在aspx.cs中以Case的方式调用特定BLL對象。
        * 2.反射法：动态调用指定类别的指定方法；此方式灵活但耗性能
        * @method invokeBLL
        * @param argName {string}  类名.方法名,必須
        * @param argData {string}  参数，可選
        * @param argUrl {string}   URL，可選
        * @param argCallback {function}   回調函數，用來處理返回結果，可選
        * @return {string} 执行结果
        */
        , invokeBLL: function(/* argName[, argData][, argUrl][, callback] */) {
            var type = 'cust', msg = ''
            , url, paramPrefix = "p_"
            , isAuto = false
            , data = {}, className, methodName, argParams = null, argUrl = null, argCallback = null;

            switch (arguments.length) {
                case 1:
                    className = arguments[0];
                    break;
                case 2:
                    className = arguments[0];
                    if ((jQuery.isArray(arguments[1])) || (typeof arguments[1] == "object") || (typeof arguments[1] == "string")) {
                        argParams = arguments[1];
                    }
                    if (jQuery.isFunction(arguments[1])) {
                        argCallback = arguments[1];
                    }

                    break;
                case 3:
                    className = arguments[0];
                    argParams = arguments[1];

                    if (jQuery.isFunction(arguments[2])) {
                        argCallback = arguments[2];
                    } else if (typeof arguments[2] == 'string') {
                        argUrl = arguments[2];
                    }
                    break;
                case 4:
                    className = arguments[0];
                    argParams = arguments[1];
                    argUrl = typeof arguments[2] == 'string' ? arguments[2] : this.getUrl(location.href);
                    argCallback = jQuery.isFunction(arguments[3]) ? arguments[3] : null;
                    break;
                default:
                    msg = '傳入參數個數有誤，最少1個，最多4個!'
                    break;

            }


            if (argUrl == null) {
                url = this.getUrl(location.href);
            } else {
                url = argUrl;
            }
            //是否传入类名
            if (className.lastIndexOf('.') > 0) {
                methodName = className.substr(className.lastIndexOf('.') + 1);
                className = className.substr(0, className.lastIndexOf('.'));
            } else {
                //methodName = argName;//2012-07-17 BY Rayd
                methodName = className;
                className = '';
            }

            //NONE PARAM
            if (argParams == null) {
                data = { m: methodName };
            } else if (typeof argParams == 'object' && !jQuery.isArray(argParams)) {
                data = argParams;
                data.m = methodName;
                data._class = className;
            } else if (jQuery.isArray(argParams)) {
                //因為用反射，參數的順序一定要與方法參數的順序相同，所以需在前端編好號
                for (var i = 0; i < argParams.length; i++) {
                    data[paramPrefix + i] = argParams[i];                  //eg. p_0, p_1
                }
                data.m = methodName;
                data._class = className;

            }
            //alert("methodName="+data.MethodName + "className=" + data.ClassName);
            return this.request(type, data, url);
        }
        ///** 
        //* 是invokeBLL别名方法：
        //* @method invoke
        //* @param argName {string}  类名.方法名
        //* @param argData {string}  参数
        //* @param argUrl {string}   URL
        // * @return {string} 执行结果
        //*/
        //2012-11-06：取消此方法，此方法存在問題
        //,invoke:function(/* argName[, argData][, argUrl][, callback] */) {
        //    return this.invokeBLL(params);
        //}

        /** 
        * 是invokeBLL别名方法：
        * @method invoke
        * @param methodName {string}  类名.方法名
        * @param params {Array}  参数，为Array或Object
        * @param options {Object} {encrypt：true--资料传送前进行加密 ,
        * decrypt:true--接收到资料，进行解密,
        * wrapper:"<record>或分隔符号如逗号",
        * together:true/false Options信息是否一同传入
        * url:"",async:true同步/false異步,
        * complete:fn_1,
        * beforeSend:fn_2,
        * error:fn_3,
        * success:fn_4}
        * @return {string} 結果
        */
        , invoke: function(s_methodName, o_params, o_options) {
            var xParams = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><_root><_class>{0}</_class><_params>{1}</_params></_root>"
            //, xnode = "<{0}>{1}</{0}>", tmp_xnote = ""
		       , together = true //options参数是否要一起传送
		       , arg_options = ""
		       , arg_args = ""
		       , retData = ""
		       , rr_url = "/" + this.getPath() + "/RR.aspx" //
		       , async = false //異步，Default false
		       , encrypt = false //整包资料传送前进行加密
		       , decrypt = false //接收到整包资料，进行解密
		       , fn_beforeSend = null
		       , fn_complete = null
		       , fn_error = null
		       , fn_success = null
		       , params = []
		       , retdatatype = "json"
		       , myui = this
		       , options = {}
		       , wrapper = "";


            if (typeof o_options != 'undefined') {
                options = o_options;
                if (typeof o_options.rr_url != 'undefined') {
                    rr_url = o_options.rr_url;
                }
                if (typeof o_options.async != 'undefined') {
                    async = o_options.async;
                }
                if (typeof o_options.beforeSend == 'function') {
                    fn_beforeSend = o_options.beforeSend;
                }
                if (typeof o_options.complete == 'function') {
                    fn_complete = o_options.complete;
                }
                if (typeof o_options.error == 'function') {
                    fn_error = o_options.error;
                }
                if (typeof o_options.success == 'function') {
                    fn_success = o_options.success;
                }
                if (typeof o_options.datatype != 'undefined') {
                    retdatatype = o_options.datatype;
                }


            }
            //options 參數一定存在，若沒有則會自動生成(url是固定的值)
            //url
            if (typeof options.url == 'undefined') {
                options['url'] = myui.getUrl(location.href);
            }
            //wrapper
            if (typeof options.wrapper != 'undefined') {
                wrapper = options.wrapper;
            }
            //together
            if (typeof options.together != 'undefined') {
                together = options.together;
            }
            //encypy
            if (typeof options.encrypt != 'undefined') {
                encrypt = options.encrypt;
            }            
            //decrypt
            if (typeof options.decrypt != 'undefined') {
                decrypt = options.decrypt;
            }
            
            //是否传送Options
            if (together) {
                arg_options = myui.packArgs(options, options);
            }
            //沒有參數傳入
            if (o_params != null && typeof o_params != 'undefined') {
                arg_args = myui.packArgs(o_params, options);
            }
            arg_args = arg_args + arg_options;
            xParams = xParams.format(s_methodName, arg_args);
            //alert(new Date().getUTCDate());
            //alert(this.encrypt(this.coding({prefix:"myui",year:"yyyy",week:""}),'mygod.89'));
            //需加密
            if(encrypt){
                xParams = this.encrypt(xParams,this.encrypt(this.coding({prefix:"myui",year:"yyyy",week:""}),'mygod.89'));
            }
            //alert(xParams);
            var ret = $.ajax({

                type: "POST",
                url: rr_url,
                async: async,
                processData: false,
                data: xParams, //xml format
                //data: {fld1:'field1 value',fld2:'field2 value',fld3:'field3 value'},
                //contentType:'application/x-www-form-urlencoded',
                //contentType:'text/xml'

                beforeSend: function(XMLHttpRequest) {
                    //alert(this.data);
                    if (fn_beforeSend != null) fn_beforeSend(XMLHttpRequest);

                },
                complete: function(XMLHttpRequest, textStatus) {
                    //this; // 调用本次AJAX请求时传递的options参数
                    //alert(textStatus);
                    if (fn_complete != null) fn_complete(XMLHttpRequest, textStatus);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    // 通常 textStatus 和 errorThrown 之中
                    // 只有一个会包含信息
                    //this; // 调用本次AJAX请求时传递的options参数
                    if (fn_error != null) fn_error(XMLHttpRequest, textStatus, errorThrown);
                    //alert(errorThrown);
                },

                success: function(ret_data, textStatus) {
                    // data 可能是 xmlDoc, jsonObj, html, text, 等等...
                    //this; // 调用本次AJAX请求时传递的options参数

                    //retData = ret_data;
                    if (fn_success != null) fn_success(ret_data, textStatus);
                    //alert(ret_data);
                }

                //}).responseText;
            }).responseText;
            //后端数返回加密数据包，需解密
            if (typeof o_options != 'undefined' && typeof o_options.decrypt != 'undefined' && o_options.decrypt) {
                ret = this.decrypt(ret,this.encrypt(this.coding({prefix:"myui",year:"yyyy",week:""}),'mygod.89'));
            }
            //alert(ret);
            
            //转换为JSON格式
            if (retdatatype == "json" && typeof ret == 'string' && ret != "" && ret.substr(0, 1) == '{') {
                ret = $.parseJSON(ret);
            }

            return ret;
        }

        /** 
        * 取得配置字段的信息，组成XML格式字符
        * @method packArgs
        * @param o_params {Array} {object} {string} 數組的元素可為string,object，不能為Array
        *           1."rose" or "rose,jack" or 或不傳任何參數;  result:<_arg><![CDATA[<rose/>]]></_arg> or <_arg><![CDATA[<rose/><jack/>]]></_arg> <_arg><![CDATA[]]></_arg>
        *           2.{id:"name",size:"20"} result:<_arg><![CDATA[<name/><birthday/>]]></_arg>
        *           3.["name","age","birthday"] result: <_arg><![CDATA[<name/><birthday/>]]></_arg>
        *           4 [{name:'rose',age:20},{name:'jack',age:22}] result: <_arg><![CDATA[<name>rose</name><age>20</age>]]></_arg>
        * @param o_options {object} or {string} 最外層xml節點名稱或分隔符
        * @return {string} 如xml格式的字串：<fields><name/><age/><birthday/></fields>
        */
        , packArgs: function(o_params, o_options) {
            var xnode = "<{0}>{1}</{0}>", tmp_xnote = ""
		       , args = "<_arg><![CDATA[{0}]]></_arg>"
		       , tmp_args = ""
		       , items
		       , wrapper = ""
		       , params = [];

            if (o_params == null) {
                $.myui.msg('Cant access NULL param', 'w');
                return false;
            }
            
            //wrapper
            if (typeof o_options == 'string') {
                wrapper = o_options;
            }else if (typeof o_options.wrapper!='undefined'){
                wrapper = o_options.wrapper;
            }

            //統一變成數組
            if ($.isArray(o_params)) {
                params = o_params;
            } else {
                params.push(o_params);
            }

            //數組，一個元素代表一個參數
            for (var i = 0; i < params.length; i++) {
                var o = params[i];
                //依參數類型區分處理
                switch (typeof o) {
                    case 'string':
                        //用逗號分隔的多個值
                        if (wrapper.length == 1 && o.indexOf(wrapper) > 0) {
                            items = o.split(wrapper);
                            $.each(items, function(j, item) {
                                tmp_xnote = tmp_xnote + xnode.format(item, "");
                            });
                        } else {
                            //若是子串，直接包裝
                            tmp_xnote = o;
                        }
                        break;
                    case 'object':
                        //數組元素是對象（不能再是數組）
                        if (!$.isArray(o)) {
                            //對象的每一元素
                            for (var name in o) {
                                //只有string與number能被封裝
                                if (typeof name != 'undefined' && typeof o[name] == 'string' || typeof o[name] == 'number' || typeof o[name] == 'boolean') {
                                    //tmp_xnote = tmp_xnote + xnode.format(name, o[name]);
                                    tmp_xnote = tmp_xnote + xnode.format(name, $.myui.safeStr(o[name], true));
                                    /*
                                    if(o_options.encrypt){
                                        tmp_xnote = tmp_xnote + xnode.format(name, $.myui.encrypt(o[name], name));
                                    }else{
                                        tmp_xnote = tmp_xnote + xnode.format(name, $.myui.safeStr(o[name], true));
                                    }
                                    */
                                }
                            }
                        }
                        break;
                    case 'undefined':
                        break;
                    default:
                        break;
                } //end switch
                //每個參數累加

                //"" or ","
                if (wrapper.length <= 1) {
                    //格式：<_arg><![CDATA[<name>jack</name><age>20</age>]]></_arg><_arg>...</_arg>
                    tmp_args = tmp_args + args.format(tmp_xnote);
                } else {
                    //格式:<record><name>jack</name><age>20</age></record><record>...</record>
                    tmp_args = tmp_args + xnode.format(wrapper, tmp_xnote);
                }
                //clear
                tmp_xnote = "";

            }; //end for

            //wrapper
            //if (wrapper != "") {
            //加上<_arg>...</_arg>
            //tmp_args = args.format(tmp_args);;
            //}


            //alert(tmp_args);        
            return tmp_args;
        }


        /** 
        * 取得配置字段的信息，组成XML格式字符
        * @method makeFields
        * @param a_fields {Array} ["name,age,birthday"] or [{id:name,size:"20"},{id:age,size:"20"},{id:birthday,size:"50"}]
        * @param b_isall {bool} 全部信息，包括熟悉
        * @return {string} 如xml格式的字串：<fields><name/><age/><birthday/></fields>
        */
        , makeFields: function(a_fields, b_isall) {
            var fields = ""
            , isall = false;

            if (typeof b_isall != 'undefined') {
                isall = b_isall;
            }
            $.each(a_fields, function(i, o) {
                //fields += "<" + o.id + "/>" + ",";
                //[{id:name,size:"20"},{id:age,size:"20"},{id:birthday,size:"50"}]
                if (typeof o == 'object') {
                    fields += "<" + o.id;
                    //包括属性
                    if (isall) {
                        for (var name in o) {
                            if (name != 'id' && (typeof o[name] == 'string' || typeof o[name] == 'number' || typeof o[name] == 'boolean')) {
                                fields += " " + name + "=\"" + o[name] + "\"";
                            }
                        }
                    }
                    fields += "/>";
                }
                //["name,age,birthday"]
                if (typeof o == 'string') {
                    fields += "<" + o + "/>";
                }
            });
            return fields;
        }

        /** 
        * 取得純URL（不帶任何參數）
        * @method getUrl
        * @param argUrl {string} 可能是帶有【?】或【#】的URL字串，若不傳入，則Default為當前頁面的HREF
        * @return {string} path
        */
        , getPath: function(argUrl) {
            var url = location.href
                , pos = -1, pos2 = 0, path = "";

            if (typeof argUrl != 'undefined') {
                url = argUrl;
            }
            //http://localhost/erp/js/grid.htm

            if (url.indexOf('//') >= 0) {
                //取得//位置
                pos = url.indexOf('//');
                //后面的一个/号
                pos2 = url.indexOf('/', pos + 3);
                pos = url.indexOf('/', pos2 + 1);
                path = url.substr(pos2 + 1, pos - pos2 - 1);
            }
            return path;

        }
        /** 
        * 取得純URL（不帶任何參數）
        * @method getUrl
        * @param argUrl {string} 可能是帶有【?】或【#】的URL字串，若不傳入，則Default為當前頁面的HREF
        * @return {string} url
        */
        , getUrl: function(argUrl) {
            var url = location.href
            , pos = -1;

            if (typeof argUrl != 'undefined') {
                url = argUrl;
            }
            if (url.indexOf('?') >= 0) {
                pos = url.indexOf('?');
            }
            if (url.indexOf('#') >= 0) {
                pos = url.indexOf('#');
            }
            //test.aspx
            if (argUrl.indexOf('/') == -1) {
                pos = location.href.lastIndexOf('/');
                url = location.href.substr(0, pos + 1) + argUrl;
            } else {

                if (pos >= 0) {
                    url = url.substr(0, pos);
                }
            }
            return url;

        }
        /** 
        * 显示信息
        * @method msg
        * @param message {string} 信息,或者待本地化的key
        * @param isLang {bool} 是否需要翻譯，true:需要（default），false:不需要，$.myui.msg('hello') or $.myui.msg('hello','a')；$.myui.msg("hello,{0},I'm{1}",['Jone','Rose']) or $.myui.msg("hello,{0},I'm{1}",['Jone','Rose'],'a') 
        * @param values {array} 動態值
        * @param type {string} 呈现类别 s--status(default);a--alert;w--dirlog window
        */
        , msg: function(message, isLang, values, type) {
            var tp = 's', word, lang = false;
            if (typeof isLang != 'undefined' && typeof isLang == 'boolean') {
                lang = isLang;
            }
            if (typeof type != 'undefined' && typeof type == 'string') {
                tp = type.toLowerCase();
            }
            //alert(word);

            if (lang) {
                if (typeof values != 'undefined' && $.isArray(values)) {
                    word = this.lang(message, values);
                }
                else {
                    word = this.lang(message);
                }
            } else {
                //$.myui.msg("hello,{0},I'm{1}",['Jone','Rose']) or $.myui.msg("hello,{0},I'm{1}",['Jone','Rose'],'a') 
                if ($.isArray(arguments[1])) {
                    word = message.format(arguments[1]);
                }
                if (typeof values != 'undefined' && $.isArray(values)) {
                    word = message.format(values);
                } else {
                    word = message;
                }
            }
            //$.myui.msg('hello') or $.myui.msg('hello','a')
            if (typeof arguments[1] == 'string' && arguments.length == 2) {
                tp = arguments[1];
            }
            ////$.myui.msg("hello,{0},I'm{1}",['Jone','Rose']) or $.myui.msg("hello,{0},I'm{1}",['Jone','Rose'],'a') 
            if (typeof arguments[2] == 'string' && arguments.length == 3) {
                tp = arguments[2];
            }

            switch (tp) {
                case 'a': //alert()
                    alert(word);
                    break;
                case 's': //at window.status
                    window.status = word;
                    break;
                case 'w': //at dirlog win
                    this.win({ title: '', msg: word, message: '' });
                    break;
                case 'n': //noti
                    this.notify(word);
                    break;

                default:
                    window.status = word;
                    break;
            }

        }
        /** 
        * 計算耗時并顯示.
        * @method taken
        * @param title {string} 耗時對象名稱
        * @param oldTime {datetime} 起時
        * @param times {datetime} 止時
        */
        , taken: function(title, oldTime, times) {
            var dtNow = new Date().getTime();
            //alert(oldTime);
            this.msg((title || ' It ') + 'had taken:' + (dtNow - oldTime) / (times || 1) + 'ms');
        }
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
        , win: function(cfg, callback) {
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

            tip = this.safeStr(cfg.msg);
            message = this.safeStr(cfg.message);
            if (typeof cfg.url != 'undefined') {
                url = cfg.url;
            }
            if (typeof cfg.heigth != 'undefined') {
                heigth = cfg.heigth;
            }
            if (typeof cfg.width != 'undefined') {
                width = cfg.width;
            }

            if (typeof cfg.heigth != 'undefined') {
                heigth = cfg.heigth;
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

            } else {
                buttons['OK'] = ok;
            }


            main = '<div id="dialog-message" title="" style="display:none"></div>'
            if (url == '#') {

                tagMessage = '<p id="{0}">'
                    + '<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>'
                    + '<span><pre>{1}</pre></span>'
                    + '</p>'
                    + '<p><pre>{2}</pre>'
                    + '</p>'
                tagMessage = tagMessage.format(id, tip, message);

            } else {
                tagMessage = '<iframe id="{0}" src="{1}" style="width: 100%; height: 100%;" frameborder="0"></iframe>';


                tagMessage = tagMessage.format(cfg.id, url);

            }

            //建立主Dialog

            if ($("#dialog-message").length == 1) {
                $("#dialog-message").remove();
            }
            $("body").append(main);

            if ($("#" + id).length == 0) {
                $("#dialog-message").empty();
                $("#dialog-message").append(tagMessage);
            } else {
                if (url == '#') {
                    //tip
                    $("#" + id).find('span>pre').text(tip);
                    //message
                    $("#" + id).find('p>pre').text(message);
                } else {
                    $("#" + id).find('iframe').attr('src', url);
                }
            }

            // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
            //$("#dialog").dialog("destroy");
            var $dialog = $("#dialog-message").dialog({
                modal: true,
                autoOpen: false,
                closeOnEscape: true,
                width: width,
                //height:heigth,
                //show: 'explode',
                //hide: 'explode',
                buttons: buttons

            });


            //$('#dialog-message').attr("title", cfg.title);
            $dialog.dialog('open');
            $dialog.css({ height: height });

            //sub function
            function ok() {
                var ret = true;

                //invoke callback function
                if (typeof callback == 'function') {
                    callback.apply(this, [ret, parent]);
                }
                $dialog.dialog('close');
            };
            //sub function
            function cancel() {
                var ret = false;
                if (typeof callback == 'function') {
                    callback.apply(this, [ret, parent]);
                }
                $dialog.dialog('close');
            };
        }
        /** 
        * 開啟信息對話框。
        * @method notify
        * @param msg {string} 信息
        * @param elem {object} 附件在的元素，Default為body
        */
		, notify: function(msg, elem) {
		    //var msg = "Selected " + $.trim($(input).text());

		    $('<div style="position: absolute; display: inline-block; font-size: 1.5em; padding: .5em; box-shadow: 1px 1px 1px -1px rgba(0,0,0,0.5);z-index: 1000;"/>').appendTo(document.body).text(msg).addClass("ui-state-error ui-corner-bottom").position({
		        my: "center top",
		        at: "center top",
		        of: window
		    }).show({
		        effect: "blind"
		    }).delay(1000).hide({
		        effect: "blind",
		        duration: "slow"
		    }, function() {
		        $(this).remove();
		    });
		}
        /** 
        * 開资料选取話框。若有cfg.parent和cfg.fields传入，则会自动更新母Grid,您同样可以在CallBack来完成此任务，注意应避免重复执行
        * @method wingrid
        * @param cfg {object} 配置類
        * <pre>
        * [sample code]        
        *   gd_akd.grid('setToolbar', { id: 'testwingrid', name: 'open...', fn: openWin, data: { grid: gd_akd},isAppend:true});
        *   ...
        *        function openWin(event) {
        *            if (gd_akd.grid('currRow') != null) {
        *                    $.myui.wingrid({
        *                       id: 'allkindlist'
        *                        , parent: event.data.grid //母对象
        *                        , append: true //true：返回结果集是添加到母体中；false：更改当前行
        *                        , single: false //true-单选行;false-可多选
        *                        , fields: {qty:'qty', productid:'prod_id'}//productid:子选窗口栏名，'prod_id':父窗口栏名
        *                        , url: 'allkind.aspx'
        *                        , width: 700//宽度
        *                        , height: 400//高度
        *                        , cond: { allkind_pid: '0' }//资料过滤条件
        *                        , readonly: false //只讀
        *                        , isSelect: false //挑選返回
        *                        , isCheckAll: false //默認查詢的結果全選（checked=1）
        *                        , query: false //开启画面后马上执行Query
        *                }, applyData);
        *            } else {
        *                $.myui.msg('noselected',true,'w');
        *            }
        *            //alert(ret);
        *        }
        *   //function  
        *   function applyData(dataRows) {
        *       alert('applyData:here');
        *       gd_akd.grid('applyFrom', dataRows, { note: 'note', allkind_seq: 'allkind_seq' }, true);
        *   }    
        * </pre>
        * 
        * @param callback {function} 回掉函数，返回母体选中的资料行数据，以便其自行对资料进行处理
        */
        , wingrid: function(cfg, callback) {
            var retData = []
            , tip, message
            , winId = 'win-grid'
            , width = 380
            , heigth = 300
            , isReadOnly = true
            , isSingle = true
            , isAppend = false
            , query = false
            , isSelect = true
            , isCheckAll = false
            , tagMessage = '<div id="{0}" title="{1}" style="display:none;width:{3}px;height:{4}px">'
                    + '<div id="{2}" style="width:{5}px;height:{6}px"></div>'
                    + '</div>';

            if (typeof cfg.width != 'undefined') {
                width = cfg.width;
            }
            if (typeof cfg.isSelect != 'undefined') {
                isSelect = cfg.isSelect;
            }
            if (typeof cfg.readonly != 'undefined') {
                isReadOnly = cfg.readonly;
            }

            if (typeof cfg.single != 'undefined') {
                isSingle = cfg.single;
            }
            if (typeof cfg.append != 'undefined') {
                isAppend = cfg.append;
            }

            if (typeof cfg.heigth != 'undefined') {
                heigth = cfg.heigth;
            }
            if (typeof cfg.width != 'undefined') {
                width = cfg.width;
            }
            if (typeof cfg.query != 'undefined') {
                query = cfg.query;
            }
            if (typeof cfg.isCheckAll != 'undefined') {
                isCheckAll = cfg.isCheckAll;
            }

            tagMessage = tagMessage.format(winId, '', cfg.id, width - 20, heigth, width, heigth + 80);


            if ($("#" + winId).length == 1) {

                $("#" + winId).remove();

            }

            $("body").append(tagMessage);
            var gd_list = $("#" + cfg.id);
            gd_list.grid(
                {
                    url: cfg.url
                    , readonly: isReadOnly
                    , single: isSingle
                    , isCheckAll: isCheckAll
                }
            );

            gd_list.grid('setCond', cfg.cond);
            if (isSelect) {
                gd_list.grid('setToolbar', { id: 'seleted', name: this.lang('btn_selected'), fn: selected, data: { grid: gd_list} });
                gd_list.grid('setToolbar', { id: 'cancel', name: this.lang('btn_colse'), fn: cancel, data: { grid: gd_list} });
            }
            //gd_list.grid('focus');
            //gd_list.grid('adjust');
            var $dialog = $("#" + winId).dialog({
                modal: true,
                autoOpen: false,
                closeOnEscape: true,
                width: width

            });

            $dialog.dialog('open');
            if (query) {
                gd_list.grid('query');
            }
            //sub function
            function selected() {
                var ret = {}
                  , rows = gd_list.grid('getRows');

                //没有选行
                if (rows == null || rows.length == 0) {
                    return false;
                }
                //若有cfg.parent和cfg.fields传入，则会自动更新母Grid
                if (typeof cfg.parent == 'object' && typeof cfg.fields == 'object') {
                    //若取名为“_applyFrom”，则不可调用到，可能被widget视为private
                    cfg.parent.grid('applyFrom', rows, cfg.fields, isAppend);

                }
                //invoke callback function
                if (typeof callback == 'function') {
                    callback.apply(this, [rows]);
                }
                $dialog.dialog('close');
            };
            //sub function
            function cancel() {
                $dialog.dialog('close');
            };
        }


        /** 
        * 替换敏感关键字，如下：
        * &lt;  --小于号<
        * &gt;  --大于号>
        * &amp; --和&
        * &apos;--单引号'
        * &quot;--双引号" 
        * @method safeStr
        * @param str {string} 待檢查字串
        * @param isRecovery {bool} 是否將編碼復原，true-將編碼還原(decrypt)；false-將HTML編碼(encrypt)（Default） add 20120718 by rayd
        * @return {string} 替換過的安全字串，若為空或不為字符則返回本身。
        */
        , safeStr: function(str, b_encrypt) {
            var ret = '', encrypt = false; ;
            if (typeof str == 'string' && str != '') {
                ret = str;
            } else {
                return str;
            }
            if (typeof b_encrypt != 'undefined') {
                encrypt = b_encrypt;
            }
            //編碼encrypt
            if (encrypt) {

                if ($.browser.msie) {
                    ret = ret.replace(/&/g, '&amp;');
                    ret = ret.replace(/</g, '&lt;');
                    ret = ret.replace(/>/g, '&gt;');
                    //            ret = ret.replace(/'/g, '&apos;');
                    ret = ret.replace(/"/g, '&quot;');
                } else {
                    //str.replace(/<img.*?src="[^"]*"[^>]*>/ig, function(){i++; return "img" + i;});
                    //ret.replace(/&/g, function(){i++; return ['&amp;','&lt;','&gt;','&apos;','&quot;'][i];});
                    ret = ret.replace(/&/g, '&amp;');
                    ret = ret.replace(/</g, '&lt;');
                    ret = ret.replace(/>/g, '&gt;');
                    ret = ret.replace(/'/g, '&apos;');
                    ret = ret.replace(/"/g, '&quot;');
                }
            } else {//解碼decrypt
                if ($.browser.msie) {
                    ret = ret.replace(/&amp;/g, '&');
                    ret = ret.replace(/&lt;/g, '<');
                    ret = ret.replace(/&gt;/g, '>');
                    //            ret = ret.replace(/'/g, '&apos;');
                    ret = ret.replace(/&quot;/g, '"');
                } else {
                    //str.replace(/<img.*?src="[^"]*"[^>]*>/ig, function(){i++; return "img" + i;});
                    //ret.replace(/&/g, function(){i++; return ['&amp;','&lt;','&gt;','&apos;','&quot;'][i];});
                    ret = ret.replace(/&amp;/g, '&');
                    ret = ret.replace(/&lt;/g, '<');
                    ret = ret.replace(/&gt;/g, '>');
                    ret = ret.replace(/&apos;/g, "'");
                    ret = ret.replace(/&quot;/g, '"');
                }
            }

            return ret;
        }

        /** 
        * 取得或設定Cookie
        * @method cookie
        * @param name {string} cookie名稱
        * @param value {string} cookie值
        * @param options {object} 配置對象
        * <pre>
        * {
        *   expires://期限
        *   ,path://路徑
        *   ,domain://域
        *   secure://加密
        * }        
        * </pre>
        * @return {string} Cookie值，若設定Cookie則無返回值
        */
        , cookie: function(name, value, options) {
            if (typeof value != 'undefined') { // name and value given, set cookie
                options = options || {};
                if (value === null) {
                    value = '';
                    options.expires = -1;
                }
                var expires = '';
                if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
                    var date;
                    if (typeof options.expires == 'number') {
                        date = new Date();
                        date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
                    } else {
                        date = options.expires;
                    }
                    expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
                }
                // CAUTION: Needed to parenthesize options.path and options.domain
                // in the following expressions, otherwise they evaluate to undefined
                // in the packed version for some reason...
                var path = options.path ? '; path=' + (options.path) : '';
                var domain = options.domain ? '; domain=' + (options.domain) : '';
                var secure = options.secure ? '; secure' : '';
                document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
            } else { // only name given, get cookie
                var cookieValue = null;
                if (document.cookie && document.cookie != '') {
                    var cookies = document.cookie.split(';');
                    for (var i = 0; i < cookies.length; i++) {
                        var cookie = jQuery.trim(cookies[i]);
                        // Does this cookie string begin with the name we want?
                        if (cookie.substring(0, name.length + 1) == (name + '=')) {
                            cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                            break;
                        }
                    }
                }
                return cookieValue;
            }
        }
        /** 
        * 取得或設定Cache,HTML5 suport
        * @method cache
        * @param name {string} cache名稱，当值为数字-1，清除全部Cache
        * @param value {string} cache值,当值为数字-1，移除该名称的Cache
        * @param options {object} 配置對象,暫時沒有用到
        * <pre>
        * {
        * }
        * </pre>
        * @return {string} Cache值，若設定Cache則無返回值
        */
        , cache: function(name, value, options) {
            var ret = null;
            options = options || {};
            if (typeof value != 'undefined') { // name and value given, set Cache
                if (value == -1) {
                    localStorage.removeItem(name);
                } else {
                    if (value === null) {
                        value = '';
                    }
                    localStorage.setItem(name, value);
                }
            } else { // only name given, get Cache
                if (name == -1) {
                    localStorage.clear();
                } else {
                    if (localStorage[name]) {
                        ret = localStorage[name];
                    }
                }
                return ret;
            }
        }
        /** 
        * 取得或設定Session,HTML5 suport
        * @method session
        * @param name {string} Session名稱，当值为数字-1，清除全部Session
        * @param value {string} Session值,当值为数字-1，移除该名称的Session
        * @param options {object} 配置對象,暫時沒有用到
        * <pre>
        * {
        * }
        * </pre>
        * @return {string} Session值，若設定Cache則無返回值
        */
        , session: function(name, value, options) {
            var ret = null;
            options = options || {};
            if (typeof value != 'undefined') { // name and value given, set Cache
                if (value == -1) {
                    sessionStorage.removeItem(name);
                } else {
                    if (value === null) {
                        value = '';
                    }
                    sessionStorage.setItem(name, value);
                }
            } else { // only name given, get Cache
                if (name == -1) {
                    sessionStorage.clear();
                } else {
                    if (sessionStorage[name]) {
                        ret = sessionStorage[name];
                    }
                }
                return ret;
            }
        }
        /** 
        * 依Key值找到其对应语系翻译，并组合好后返回。
        * @method lang
        * @param key {string} 待翻譯字，一般為Key，如‘btn_save’
        * @param values {array} 動態值數組
        * @return {string} 翻譯結果
        */
        , lang: function(key, values) {
            var word = key,
            data = { key: key };

            //到母页面中查找翻译
            if (typeof parent.LANG == 'object' && typeof parent.LANG.global != 'undefined' && typeof parent.LANG.global[key] != 'undefined') {
                //alert(parent.LANG[key]);
                word = parent.LANG.global[key].w;
                //在本页面中找
            } else if (typeof LANG_PAGE == 'object' && typeof LANG_PAGE.page != 'undefined' && typeof LANG_PAGE.page[key] != 'undefined') {
                //alert(LANG[key]);
                word = LANG_PAGE.page[key].w;
            } else {//最后到后台找
                //word = this.request('lang', data);
            }
            //动态组合返回内容;
            if (typeof values != 'undefined' && $.isArray(values)) {
                word = word.format(values);
            }

            return word;
        }
        /** 
        * 成批取得语言翻譯包，取得後Cache在頁面，以免多次請求。
        * @method langs
        * @param url {string} url:頁面級的識別ID，與XML的Page id屬性匹配即可；'global'：全局級；'all':全部，整份翻译文件
        * @return {json} 翻譯包
        * [sample code:]
        * <pre>
        * //LANG_PAGE一定要定义,且要定义为全局。
        * var LANG = {};
        * var LANG_PAGE = {};
        *   ...
        *    if($.isEmptyObject(LANG)){
        *        LANG = $.myui.langs('global');
        *    }
        *    if($.isEmptyObject(LANG_PAGE)){
        *        LANG_PAGE = $.myui.langs('default.aspx');
        *    }
        *
        * </pre>
        */
        , langs: function(url) {
            var ret = '';
            var data = { kind: url }, langs, cacheName = 'cache_langs';

            ret = this.request('langs', data);


            /*  
            //Cache langs          
            if ($.myui.cache(cacheName)===null){
            langs = $.myui.request('langs',  { kind: 'all' }, null,false);
            $.myui.cache(cacheName,langs);
            }else{
            langs = $.myui.cache(cacheName);
            }
            //alert(langs);
            
            $.parseJSON(langs);
            */
            return ret;
        }
        /** 
        * 记录LOG信息到后台。
        * @method log
        * @param msg {string} 待Log信息
        */
        , log: function(msg) {
            var data = { message: msg };
            word = this.request('log', data);
        }

        /** 
        * 四舍五入。
        * @method round
        * @param num {number} 待处理数字
        * @param leng {number} 取小数位数，如2293.123为3位
        */
        , round: function(num, len) {
            with (Math) {
                return round(num * pow(10, len)) / pow(10, len);
            }
        }

        /** 
        * 产生GUID码。
        * @method guid
        */
        , guid: function() {
            var ret
            , fn = function() { return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1); };
            ret = (fn() + fn() + "-" + fn() + "-" + fn() + "-" + fn() + "-" + fn() + fn() + fn()).toUpperCase();
            /*
            var guid = "";
            for (var i = 1; i <= 32; i++){
            var n = Math.floor(Math.random()*16.0).toString(16);
            guid +=   n;
            if((i==8)||(i==12)||(i==16)||(i==20))
            guid += "-";
            }
            return guid;                
            */

            return ret;
        }
        /** 
        * 编码
        * @method coding
        * @param o_option {object} 配置 {prefix:"O",year:"",week:"",hour:"",minute:"",second:"",random:2,seq:1}
        * @param s_prefix {string} 前缀
        * @param s_hms {string} 精确到时分秒
        * @param n_length {number} 随机数的位数
        */
        //, coding: function(s_prefix, s_hms, n_length) {
        , coding: function(o_option) {
            var dt = new Date(), ret = "";
            //return app.app_nm + dt.format('yy') + dt.getWeekNumber().toString() + dt.getDay().toString() + dt.getHours().toString() + dt.getMinutes().toString() + dt.getSeconds().toString() + '-' + (Math.random()).toString();
            //ret = s_prefix + dt.format('yy') + dt.getWeekNumber().toString() + dt.getDay().toString() + '-' + Math.round(((1 + Math.random()) * Math.pow(10, n_length))).toString();
            
            if(typeof o_option!='undefined' && o_option!=null){
                for (var name in o_option){
                    switch(name){
                        case "prefix":
                            ret = ret + o_option[name];
                            break;
                        case "year":
                            ret += dt.format(o_option[name]);
                            break;
                        case "week"://一年中的周次
                            ret += dt.getWeekNumber().toString();
                            break;
                        case "days"://一年中的第几天
                            ret += dt.getDayNumber().toString();
                            break;
                        case "day"://当月的日期
                            ret += dt.getDay().toString();
                            break;
                        case "hour":
                            ret += dt.getHours().toString();
                            break;
                        case "minute":
                            ret += dt.getMinutes().toString();
                            break;
                        case "second":
                            ret += dt.getSeconds().toString();
                            break;
                        case "random":
                            ret += Math.round(((1 + Math.random()) * Math.pow(10, o_option[name]))).toString();
                            break;
                        case "seq":
                            ret += this.formatNumber((o_option[name] +1),'000');
                            break;
                        default:
                            if (typeof name=='number'){
                               ret += this.formatNumber(name+1,o_option[name]);
                            }else{
                               ret += o_option[name];
                            }
                            break;    
                    }
                }
            }            
            return ret;
        }
        /**
         * 格式化数字显示方式 
         * 用法
         * formatNumber(12345.999,'#,##0.00');
         * formatNumber(12345.999,'#,##0.##');
         * formatNumber(123,'000000');
         * @param num
         * @param pattern
         */
        ,formatNumber:function(num,pattern){
          var strarr = num?num.toString().split('.'):['0'];
          var fmtarr = pattern?pattern.split('.'):[''];
          var retstr='';

          // 整数部分
          var str = strarr[0];
          var fmt = fmtarr[0];
          var i = str.length-1;  
          var comma = false;
          for(var f=fmt.length-1;f>=0;f--){
            switch(fmt.substr(f,1)){
              case '#':
                if(i>=0 ) retstr = str.substr(i--,1) + retstr;
                break;
              case '0':
                if(i>=0) retstr = str.substr(i--,1) + retstr;
                else retstr = '0' + retstr;
                break;
              case ',':
                comma = true;
                retstr=','+retstr;
                break;
            }
          }
          if(i>=0){
            if(comma){
              var l = str.length;
              for(;i>=0;i--){
                retstr = str.substr(i,1) + retstr;
                if(i>0 && ((l-i)%3)==0) retstr = ',' + retstr; 
              }
            }
            else retstr = str.substr(0,i+1) + retstr;
          }

          retstr = retstr+'.';
          // 处理小数部分
          str=strarr.length>1?strarr[1]:'';
          fmt=fmtarr.length>1?fmtarr[1]:'';
          i=0;
          for(var f=0;f<fmt.length;f++){
            switch(fmt.substr(f,1)){
              case '#':
                if(i<str.length) retstr+=str.substr(i++,1);
                break;
              case '0':
                if(i<str.length) retstr+= str.substr(i++,1);
                else retstr+='0';
                break;
            }
          }
          return retstr.replace(/^,+/,'').replace(/\.$/,'');
        }
        
        /** 
        * 判断数组中的对象元素是否存在。
        * @method inArray
        * @param a_array {Array} 数组，其元素为对象，如[{id:'0001',name:'greg baby'},{id:'0002',name:'sunny baby'}]
        * @param s_key {Object} 数组，其元素为对象，可多个值进行判断（多值暂未实现），如{id:'0001',name:'greg baby'}
        */
        , inArray: function(a_array, s_key) {
            var ret = -1;

            //数组
            $.each(a_array, function(i, o) {
                //对象
                for (var name in o) {
                    //key
                    for (var nm in s_key) {
                        if (name == nm && o[name] == s_key[nm]) {
                            ret = i;
                        }
                    }
                }

            });

            return ret;
        }
        /** 
        * 创建日历对象
        * @method _makeDatepicker
        * @param o_options {Object} 配置参数：{id:"spec_date",format:"yy-mm",month:"2"}
        * id:元素ID
        * isTitle:是否显示在查询区，Grid有效
        * format:返回值格式
        * cond:是否显示在条件栏
        * months:多月显示
        * @param isTitle {bool} 是否為標題欄，指的是條件
        */
        //, makeDatepicker: function(dps, isTitle) {
        , makeDatepicker: function(o_options) {
            var format = 'yy-mm-dd',
            months = 1,
            isShow = true;

            //條件區Default不創建日曆；
            if (typeof o_options.isTitle != 'undefined') {
                isShow = false;
                months = 2;
            }
            //條件區若沒有設定cond=1,則不創建日曆；
            if (o_options != null && typeof o_options.cond != 'undefined' && o_options.cond == '1') {
                isShow = true;
            }
            //
            if (o_options != null && typeof o_options.format != 'undefined') {
                format = o_options.format;

            }
            //
            if (o_options != null && typeof o_options.months != 'undefined' && typeof o_options.isTitle != 'undefined') {
                months = parseInt(o_options.months);
            }
            //alert(o_options.id);
            if (isShow) {
                $('#' + o_options.id).datepicker({
                    //$('#' + dps[m].id).cal({
                    //showAnim: 'fadeIn'
                    dateFormat: format
                 , numberOfMonths: months
                 , changeYear: true
                 , changeMonth: true
                    //,showOn: 'button'
                    //,buttonImage: 'images/calendar.gif'
                    //,buttonImageOnly: true

                });
            }
        }
        /** 
        * 加解密主调方法
        * @method des
        * @param beinetkey {string} 密钥
        * @param message {string} 加密时为待加密的字符串，解密时为待解密的字符串
        * @param encrypt {number} 1：加密；0：解密
        * @param mode {bool} true：CBC mode；false：非CBC mode
        * @param iv {string} 初始化向量
        */        
        ,des:function(beinetkey, message, encrypt, mode, iv, padding) {
            //declaring this locally speeds things up a bit
            var spfunction1 = new Array(0x1010400, 0, 0x10000, 0x1010404, 0x1010004, 0x10404, 0x4, 0x10000, 0x400, 0x1010400, 0x1010404, 0x400, 0x1000404, 0x1010004, 0x1000000, 0x4, 0x404, 0x1000400, 0x1000400, 0x10400, 0x10400, 0x1010000, 0x1010000, 0x1000404, 0x10004, 0x1000004, 0x1000004, 0x10004, 0, 0x404, 0x10404, 0x1000000, 0x10000, 0x1010404, 0x4, 0x1010000, 0x1010400, 0x1000000, 0x1000000, 0x400, 0x1010004, 0x10000, 0x10400, 0x1000004, 0x400, 0x4, 0x1000404, 0x10404, 0x1010404, 0x10004, 0x1010000, 0x1000404, 0x1000004, 0x404, 0x10404, 0x1010400, 0x404, 0x1000400, 0x1000400, 0, 0x10004, 0x10400, 0, 0x1010004);
            var spfunction2 = new Array( - 0x7fef7fe0, -0x7fff8000, 0x8000, 0x108020, 0x100000, 0x20, -0x7fefffe0, -0x7fff7fe0, -0x7fffffe0, -0x7fef7fe0, -0x7fef8000, -0x80000000, -0x7fff8000, 0x100000, 0x20, -0x7fefffe0, 0x108000, 0x100020, -0x7fff7fe0, 0, -0x80000000, 0x8000, 0x108020, -0x7ff00000, 0x100020, -0x7fffffe0, 0, 0x108000, 0x8020, -0x7fef8000, -0x7ff00000, 0x8020, 0, 0x108020, -0x7fefffe0, 0x100000, -0x7fff7fe0, -0x7ff00000, -0x7fef8000, 0x8000, -0x7ff00000, -0x7fff8000, 0x20, -0x7fef7fe0, 0x108020, 0x20, 0x8000, -0x80000000, 0x8020, -0x7fef8000, 0x100000, -0x7fffffe0, 0x100020, -0x7fff7fe0, -0x7fffffe0, 0x100020, 0x108000, 0, -0x7fff8000, 0x8020, -0x80000000, -0x7fefffe0, -0x7fef7fe0, 0x108000);
            var spfunction3 = new Array(0x208, 0x8020200, 0, 0x8020008, 0x8000200, 0, 0x20208, 0x8000200, 0x20008, 0x8000008, 0x8000008, 0x20000, 0x8020208, 0x20008, 0x8020000, 0x208, 0x8000000, 0x8, 0x8020200, 0x200, 0x20200, 0x8020000, 0x8020008, 0x20208, 0x8000208, 0x20200, 0x20000, 0x8000208, 0x8, 0x8020208, 0x200, 0x8000000, 0x8020200, 0x8000000, 0x20008, 0x208, 0x20000, 0x8020200, 0x8000200, 0, 0x200, 0x20008, 0x8020208, 0x8000200, 0x8000008, 0x200, 0, 0x8020008, 0x8000208, 0x20000, 0x8000000, 0x8020208, 0x8, 0x20208, 0x20200, 0x8000008, 0x8020000, 0x8000208, 0x208, 0x8020000, 0x20208, 0x8, 0x8020008, 0x20200);
            var spfunction4 = new Array(0x802001, 0x2081, 0x2081, 0x80, 0x802080, 0x800081, 0x800001, 0x2001, 0, 0x802000, 0x802000, 0x802081, 0x81, 0, 0x800080, 0x800001, 0x1, 0x2000, 0x800000, 0x802001, 0x80, 0x800000, 0x2001, 0x2080, 0x800081, 0x1, 0x2080, 0x800080, 0x2000, 0x802080, 0x802081, 0x81, 0x800080, 0x800001, 0x802000, 0x802081, 0x81, 0, 0, 0x802000, 0x2080, 0x800080, 0x800081, 0x1, 0x802001, 0x2081, 0x2081, 0x80, 0x802081, 0x81, 0x1, 0x2000, 0x800001, 0x2001, 0x802080, 0x800081, 0x2001, 0x2080, 0x800000, 0x802001, 0x80, 0x800000, 0x2000, 0x802080);
            var spfunction5 = new Array(0x100, 0x2080100, 0x2080000, 0x42000100, 0x80000, 0x100, 0x40000000, 0x2080000, 0x40080100, 0x80000, 0x2000100, 0x40080100, 0x42000100, 0x42080000, 0x80100, 0x40000000, 0x2000000, 0x40080000, 0x40080000, 0, 0x40000100, 0x42080100, 0x42080100, 0x2000100, 0x42080000, 0x40000100, 0, 0x42000000, 0x2080100, 0x2000000, 0x42000000, 0x80100, 0x80000, 0x42000100, 0x100, 0x2000000, 0x40000000, 0x2080000, 0x42000100, 0x40080100, 0x2000100, 0x40000000, 0x42080000, 0x2080100, 0x40080100, 0x100, 0x2000000, 0x42080000, 0x42080100, 0x80100, 0x42000000, 0x42080100, 0x2080000, 0, 0x40080000, 0x42000000, 0x80100, 0x2000100, 0x40000100, 0x80000, 0, 0x40080000, 0x2080100, 0x40000100);
            var spfunction6 = new Array(0x20000010, 0x20400000, 0x4000, 0x20404010, 0x20400000, 0x10, 0x20404010, 0x400000, 0x20004000, 0x404010, 0x400000, 0x20000010, 0x400010, 0x20004000, 0x20000000, 0x4010, 0, 0x400010, 0x20004010, 0x4000, 0x404000, 0x20004010, 0x10, 0x20400010, 0x20400010, 0, 0x404010, 0x20404000, 0x4010, 0x404000, 0x20404000, 0x20000000, 0x20004000, 0x10, 0x20400010, 0x404000, 0x20404010, 0x400000, 0x4010, 0x20000010, 0x400000, 0x20004000, 0x20000000, 0x4010, 0x20000010, 0x20404010, 0x404000, 0x20400000, 0x404010, 0x20404000, 0, 0x20400010, 0x10, 0x4000, 0x20400000, 0x404010, 0x4000, 0x400010, 0x20004010, 0, 0x20404000, 0x20000000, 0x400010, 0x20004010);
            var spfunction7 = new Array(0x200000, 0x4200002, 0x4000802, 0, 0x800, 0x4000802, 0x200802, 0x4200800, 0x4200802, 0x200000, 0, 0x4000002, 0x2, 0x4000000, 0x4200002, 0x802, 0x4000800, 0x200802, 0x200002, 0x4000800, 0x4000002, 0x4200000, 0x4200800, 0x200002, 0x4200000, 0x800, 0x802, 0x4200802, 0x200800, 0x2, 0x4000000, 0x200800, 0x4000000, 0x200800, 0x200000, 0x4000802, 0x4000802, 0x4200002, 0x4200002, 0x2, 0x200002, 0x4000000, 0x4000800, 0x200000, 0x4200800, 0x802, 0x200802, 0x4200800, 0x802, 0x4000002, 0x4200802, 0x4200000, 0x200800, 0, 0x2, 0x4200802, 0, 0x200802, 0x4200000, 0x800, 0x4000002, 0x4000800, 0x800, 0x200002);
            var spfunction8 = new Array(0x10001040, 0x1000, 0x40000, 0x10041040, 0x10000000, 0x10001040, 0x40, 0x10000000, 0x40040, 0x10040000, 0x10041040, 0x41000, 0x10041000, 0x41040, 0x1000, 0x40, 0x10040000, 0x10000040, 0x10001000, 0x1040, 0x41000, 0x40040, 0x10040040, 0x10041000, 0x1040, 0, 0, 0x10040040, 0x10000040, 0x10001000, 0x41040, 0x40000, 0x41040, 0x40000, 0x10041000, 0x1000, 0x40, 0x10040040, 0x1000, 0x41040, 0x10001000, 0x40, 0x10000040, 0x10040000, 0x10040040, 0x10000000, 0x40000, 0x10001040, 0, 0x10041040, 0x40040, 0x10000040, 0x10040000, 0x10001000, 0x10001040, 0, 0x10041040, 0x41000, 0x41000, 0x1040, 0x1040, 0x40040, 0x10000000, 0x10041000);

            //create the 16 or 48 subkeys we will need
            var keys = this.des_createKeys(beinetkey);
            var m = 0,
            i, j, temp, temp2, right1, right2, left, right, looping;
            var cbcleft, cbcleft2, cbcright, cbcright2
            var endloop, loopinc;
            var len = message.length;
            var chunk = 0;
            //set up the loops for single and triple des
            var iterations = keys.length == 32 ? 3 : 9; //single or triple des
            if (iterations == 3) {
                looping = encrypt ? new Array(0, 32, 2) : new Array(30, -2, -2);
            } else {
                looping = encrypt ? new Array(0, 32, 2, 62, 30, -2, 64, 96, 2) : new Array(94, 62, -2, 32, 64, 2, 30, -2, -2);
            }

            //message += '\0\0\0\0\0\0\0\0'; //pad the message out with null bytes
              //pad the message depending on the padding parameter
              if (padding == 2) message += "        "; //pad the message with spaces
              else if (padding == 1) {temp = 8-(len%8); message += String.fromCharCode (temp,temp,temp,temp,temp,temp,temp,temp); if (temp==8) len+=8;} //PKCS7 padding
              else if (!padding) message += "\0\0\0\0\0\0\0\0"; //pad the message out with null bytes
            
            //store the result here
            result = '';
            tempresult = '';

            if (mode == 1) { //CBC mode
                cbcleft = (iv.charCodeAt(m++) << 24) | (iv.charCodeAt(m++) << 16) | (iv.charCodeAt(m++) << 8) | iv.charCodeAt(m++);
                cbcright = (iv.charCodeAt(m++) << 24) | (iv.charCodeAt(m++) << 16) | (iv.charCodeAt(m++) << 8) | iv.charCodeAt(m++);
                m = 0;
            }

            //loop through each 64 bit chunk of the message
            while (m < len) {
                if (encrypt) { //加密时按双字节操作
                    left = (message.charCodeAt(m++) << 16) | message.charCodeAt(m++);
                    right = (message.charCodeAt(m++) << 16) | message.charCodeAt(m++);
                } else {
                    left = (message.charCodeAt(m++) << 24) | (message.charCodeAt(m++) << 16) | (message.charCodeAt(m++) << 8) | message.charCodeAt(m++);
                    right = (message.charCodeAt(m++) << 24) | (message.charCodeAt(m++) << 16) | (message.charCodeAt(m++) << 8) | message.charCodeAt(m++);
                }
                //for Cipher Block Chaining mode,xor the message with the previous result
                if (mode == 1) {
                    if (encrypt) {
                        left ^= cbcleft;
                        right ^= cbcright;
                    } else {
                        cbcleft2 = cbcleft;
                        cbcright2 = cbcright;
                        cbcleft = left;
                        cbcright = right;
                    }
                }

                //first each 64 but chunk of the message must be permuted according to IP
                temp = ((left >>> 4) ^ right) & 0x0f0f0f0f;
                right ^= temp;
                left ^= (temp << 4);
                temp = ((left >>> 16) ^ right) & 0x0000ffff;
                right ^= temp;
                left ^= (temp << 16);
                temp = ((right >>> 2) ^ left) & 0x33333333;
                left ^= temp;
                right ^= (temp << 2);
                temp = ((right >>> 8) ^ left) & 0x00ff00ff;
                left ^= temp;
                right ^= (temp << 8);
                temp = ((left >>> 1) ^ right) & 0x55555555;
                right ^= temp;
                left ^= (temp << 1);

                left = ((left << 1) | (left >>> 31));
                right = ((right << 1) | (right >>> 31));

                //do this either 1 or 3 times for each chunk of the message
                for (j = 0; j < iterations; j += 3) {
                    endloop = looping[j + 1];
                    loopinc = looping[j + 2];
                    //now go through and perform the encryption or decryption 
                    for (i = looping[j]; i != endloop; i += loopinc) { //for efficiency
                        right1 = right ^ keys[i];
                        right2 = ((right >>> 4) | (right << 28)) ^ keys[i + 1];
                        //the result is attained by passing these bytes through the S selection functions
                        temp = left;
                        left = right;
                        right = temp ^ (spfunction2[(right1 >>> 24) & 0x3f] | spfunction4[(right1 >>> 16) & 0x3f] | spfunction6[(right1 >>> 8) & 0x3f] | spfunction8[right1 & 0x3f] | spfunction1[(right2 >>> 24) & 0x3f] | spfunction3[(right2 >>> 16) & 0x3f] | spfunction5[(right2 >>> 8) & 0x3f] | spfunction7[right2 & 0x3f]);
                    }
                    temp = left;
                    left = right;
                    right = temp; //unreverse left and right
                } //for either 1 or 3 iterations
                //move then each one bit to the right
                left = ((left >>> 1) | (left << 31));
                right = ((right >>> 1) | (right << 31));

                //now perform IP-1,which is IP in the opposite direction
                temp = ((left >>> 1) ^ right) & 0x55555555;
                right ^= temp;
                left ^= (temp << 1);
                temp = ((right >>> 8) ^ left) & 0x00ff00ff;
                left ^= temp;
                right ^= (temp << 8);
                temp = ((right >>> 2) ^ left) & 0x33333333;
                left ^= temp;
                right ^= (temp << 2);
                temp = ((left >>> 16) ^ right) & 0x0000ffff;
                right ^= temp;
                left ^= (temp << 16);
                temp = ((left >>> 4) ^ right) & 0x0f0f0f0f;
                right ^= temp;
                left ^= (temp << 4);

                //for Cipher Block Chaining mode,xor the message with the previous result
                if (mode == 1) {
                    if (encrypt) {
                        cbcleft = left;
                        cbcright = right;
                    } else {
                        left ^= cbcleft2;
                        right ^= cbcright2;
                    }
                }
                if (encrypt) {
                    tempresult += String.fromCharCode((left >>> 24), ((left >>> 16) & 0xff), ((left >>> 8) & 0xff), (left & 0xff), (right >>> 24), ((right >>> 16) & 0xff), ((right >>> 8) & 0xff), (right & 0xff));
                } else {
                    tempresult += String.fromCharCode(((left >>> 16) & 0xffff), (left & 0xffff), ((right >>> 16) & 0xffff), (right & 0xffff));
                } //解密时输出双字节
                encrypt ? chunk += 16 : chunk += 8;
                if (chunk == 512) {
                    result += tempresult;
                    tempresult = '';
                    chunk = 0;
                }
            } //for every 8 characters,or 64 bits in the message
            //return the result as an array
            return result + tempresult;
        } //end of des
        /** 
        * this takes as input a 64 bit beinetkey(even though only 56 bits are used),as an array of 2 integers,and returns 16 48 bit keys
        * @method des_createKeys
        * @param beinetkey {string} 密钥
        */        
        ,des_createKeys:function(beinetkey) {
            //declaring this locally speeds things up a bit
            pc2bytes0 = new Array(0, 0x4, 0x20000000, 0x20000004, 0x10000, 0x10004, 0x20010000, 0x20010004, 0x200, 0x204, 0x20000200, 0x20000204, 0x10200, 0x10204, 0x20010200, 0x20010204);
            pc2bytes1 = new Array(0, 0x1, 0x100000, 0x100001, 0x4000000, 0x4000001, 0x4100000, 0x4100001, 0x100, 0x101, 0x100100, 0x100101, 0x4000100, 0x4000101, 0x4100100, 0x4100101);
            pc2bytes2 = new Array(0, 0x8, 0x800, 0x808, 0x1000000, 0x1000008, 0x1000800, 0x1000808, 0, 0x8, 0x800, 0x808, 0x1000000, 0x1000008, 0x1000800, 0x1000808);
            pc2bytes3 = new Array(0, 0x200000, 0x8000000, 0x8200000, 0x2000, 0x202000, 0x8002000, 0x8202000, 0x20000, 0x220000, 0x8020000, 0x8220000, 0x22000, 0x222000, 0x8022000, 0x8222000);
            pc2bytes4 = new Array(0, 0x40000, 0x10, 0x40010, 0, 0x40000, 0x10, 0x40010, 0x1000, 0x41000, 0x1010, 0x41010, 0x1000, 0x41000, 0x1010, 0x41010);
            pc2bytes5 = new Array(0, 0x400, 0x20, 0x420, 0, 0x400, 0x20, 0x420, 0x2000000, 0x2000400, 0x2000020, 0x2000420, 0x2000000, 0x2000400, 0x2000020, 0x2000420);
            pc2bytes6 = new Array(0, 0x10000000, 0x80000, 0x10080000, 0x2, 0x10000002, 0x80002, 0x10080002, 0, 0x10000000, 0x80000, 0x10080000, 0x2, 0x10000002, 0x80002, 0x10080002);
            pc2bytes7 = new Array(0, 0x10000, 0x800, 0x10800, 0x20000000, 0x20010000, 0x20000800, 0x20010800, 0x20000, 0x30000, 0x20800, 0x30800, 0x20020000, 0x20030000, 0x20020800, 0x20030800);
            pc2bytes8 = new Array(0, 0x40000, 0, 0x40000, 0x2, 0x40002, 0x2, 0x40002, 0x2000000, 0x2040000, 0x2000000, 0x2040000, 0x2000002, 0x2040002, 0x2000002, 0x2040002);
            pc2bytes9 = new Array(0, 0x10000000, 0x8, 0x10000008, 0, 0x10000000, 0x8, 0x10000008, 0x400, 0x10000400, 0x408, 0x10000408, 0x400, 0x10000400, 0x408, 0x10000408);
            pc2bytes10 = new Array(0, 0x20, 0, 0x20, 0x100000, 0x100020, 0x100000, 0x100020, 0x2000, 0x2020, 0x2000, 0x2020, 0x102000, 0x102020, 0x102000, 0x102020);
            pc2bytes11 = new Array(0, 0x1000000, 0x200, 0x1000200, 0x200000, 0x1200000, 0x200200, 0x1200200, 0x4000000, 0x5000000, 0x4000200, 0x5000200, 0x4200000, 0x5200000, 0x4200200, 0x5200200);
            pc2bytes12 = new Array(0, 0x1000, 0x8000000, 0x8001000, 0x80000, 0x81000, 0x8080000, 0x8081000, 0x10, 0x1010, 0x8000010, 0x8001010, 0x80010, 0x81010, 0x8080010, 0x8081010);
            pc2bytes13 = new Array(0, 0x4, 0x100, 0x104, 0, 0x4, 0x100, 0x104, 0x1, 0x5, 0x101, 0x105, 0x1, 0x5, 0x101, 0x105);

            //how many iterations(1 for des,3 for triple des)
            var iterations = beinetkey.length >= 24 ? 3 : 1;
            //stores the return keys
            var keys = new Array(32 * iterations);
            //now define the left shifts which need to be done
            var shifts = new Array(0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0);
            //other variables
            var lefttemp, righttemp, m = 0,
            n = 0,
            temp;

            for (var j = 0; j < iterations; j++) { //either 1 or 3 iterations
                left = (beinetkey.charCodeAt(m++) << 24) | (beinetkey.charCodeAt(m++) << 16) | (beinetkey.charCodeAt(m++) << 8) | beinetkey.charCodeAt(m++);
                right = (beinetkey.charCodeAt(m++) << 24) | (beinetkey.charCodeAt(m++) << 16) | (beinetkey.charCodeAt(m++) << 8) | beinetkey.charCodeAt(m++);

                temp = ((left >>> 4) ^ right) & 0x0f0f0f0f;
                right ^= temp;
                left ^= (temp << 4);
                temp = ((right >>> -16) ^ left) & 0x0000ffff;
                left ^= temp;
                right ^= (temp << -16);
                temp = ((left >>> 2) ^ right) & 0x33333333;
                right ^= temp;
                left ^= (temp << 2);
                temp = ((right >>> -16) ^ left) & 0x0000ffff;
                left ^= temp;
                right ^= (temp << -16);
                temp = ((left >>> 1) ^ right) & 0x55555555;
                right ^= temp;
                left ^= (temp << 1);
                temp = ((right >>> 8) ^ left) & 0x00ff00ff;
                left ^= temp;
                right ^= (temp << 8);
                temp = ((left >>> 1) ^ right) & 0x55555555;
                right ^= temp;
                left ^= (temp << 1);

                //the right side needs to be shifted and to get the last four bits of the left side
                temp = (left << 8) | ((right >>> 20) & 0x000000f0);
                //left needs to be put upside down
                left = (right << 24) | ((right << 8) & 0xff0000) | ((right >>> 8) & 0xff00) | ((right >>> 24) & 0xf0);
                right = temp;

                //now go through and perform these shifts on the left and right keys
                for (i = 0; i < shifts.length; i++) {
                    //shift the keys either one or two bits to the left
                    if (shifts[i]) {
                        left = (left << 2) | (left >>> 26);
                        right = (right << 2) | (right >>> 26);
                    } else {
                        left = (left << 1) | (left >>> 27);
                        right = (right << 1) | (right >>> 27);
                    }
                    left &= -0xf;
                    right &= -0xf;

                    //now apply PC-2,in such a way that E is easier when encrypting or decrypting
                    //this conversion will look like PC-2 except only the last 6 bits of each byte are used
                    //rather than 48 consecutive bits and the order of lines will be according to 
                    //how the S selection functions will be applied:S2,S4,S6,S8,S1,S3,S5,S7
                    lefttemp = pc2bytes0[left >>> 28] | pc2bytes1[(left >>> 24) & 0xf] | pc2bytes2[(left >>> 20) & 0xf] | pc2bytes3[(left >>> 16) & 0xf] | pc2bytes4[(left >>> 12) & 0xf] | pc2bytes5[(left >>> 8) & 0xf] | pc2bytes6[(left >>> 4) & 0xf];
                    righttemp = pc2bytes7[right >>> 28] | pc2bytes8[(right >>> 24) & 0xf] | pc2bytes9[(right >>> 20) & 0xf] | pc2bytes10[(right >>> 16) & 0xf] | pc2bytes11[(right >>> 12) & 0xf] | pc2bytes12[(right >>> 8) & 0xf] | pc2bytes13[(right >>> 4) & 0xf];
                    temp = ((righttemp >>> 16) ^ lefttemp) & 0x0000ffff;
                    keys[n++] = lefttemp ^ temp;
                    keys[n++] = righttemp ^ (temp << 16);
                }
            } //for each iterations
            //return the keys we've created
            return keys;
        }
        /** 
        * 把字符串转换为16进制字符串,如：a变成61（即10进制的97）；abc变成616263
        * @method stringToHex
        * @param s {string} 報表完整內容
        */
        ,stringToHex:function(s) {
            var r = '';
            var hexes = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');
            for (var i = 0; i < (s.length); i++) {
                r += hexes[s.charCodeAt(i) >> 4] + hexes[s.charCodeAt(i) & 0xf];
            }
            return r;
        }
                
        /** 
        * 16进制字符串转换为字符串,如：61（即10进制的97）变成a；616263变成abc
        * @method HexTostring
        * @param s {string} 
        */
        ,HexTostring:function(s) {
            var r = '';
            for (var i = 0; i < s.length; i += 2) {
                var sxx = parseInt(s.substring(i, i + 2), 16);
                r += String.fromCharCode(sxx);
            }
            return r;
        }
         
        /** 
        * 加密函数
        * @method encrypt
        * @param s {string} 待加密的字符串
        * @param k {string} 密钥
        */        
        ,encrypt:function(s, k) {
            return this.stringToHex(this.des(k, s, 1, 0,"",2));
        }

        /** 
        *解密测试函数
        * @method decrypt
        * @param s {string} 待解密的字符串
        * @param k {string} 密钥        
        */        
        ,decrypt:function(s, k) {
            return this.des(k, this.HexTostring(s), 0, 0,"",2);
        }
        /** 
        * 列印功能。
        * @method print
        * @param argReportHtml {string} 報表完整內容
        */
        , print: function(argReportHtml, config) {
            var cfg = 'width={0},height={1},location=no,menubar=yes,scrollbars=yes'
            , win, w = '800', h = '600', pw, ph;

            if (screen.width == 640) {
                w = '600';
                h = '500';
            }
            else if (screen.width == 800) {
                w = '700';
                h = '600';

            }
            else if (screen.width >= 1024) {
                w = '900';
                h = '700';
            }
            if (typeof config != 'undefined') {
                if (typeof config.width != 'undefined') {
                    w = config.width;
                }
                if (typeof config.height != 'undefined') {
                    h = config.height;
                }

            }
            cfg = cfg.format(w, h);
            win = window.open("", "win", cfg);
            with (win.document) {
                open("text/html", "replace");
                write(argReportHtml);


                pw = documentElement.scrollWidth - 4;
                ph = documentElement.scrollHeight - 4;


                close();
            }
            //移动窗口到中心位置
            win.moveTo((screen.width - pw) / 2, (screen.height - ph) / 2);
        }


    };

})(jQuery);

/** 
* 格式化字符。
* @method format
* @param argReportHtml {string} 報表完整內容
* @return {json} 翻譯包
* [sample code:]
* <pre>
* //LANG_PAGE一定要定义,且要定义为全局。
* var s = 'Hello,{0},{1}!';
* var ret;
*   ...
*   ret = s.format('Jerry','Good Morning');
*   or
*   ret = s.format(['Jerry','Good Morning']);
*   Result:
*   ret = 'Hello,Jerry,Good Morning!'
* </pre>
*/
String.prototype.format = function() {
    var args = arguments;
    if ($.isArray(arguments[0])) {
        args = arguments[0];
    }
    return this.replace(/\{(\d+)\}/g,
                function(m, i) {
                    return args[i];
                });
}


Array.prototype.indexOf = function(o) {
      
        //alert();
        for (var i = 0, len = this.length; i < len; i++) {
            if (this[i] == o) return i;
        }
        return -1;
    }
    
//Array Remove - By John Resig (MIT Licensed)
Array.prototype.remove = function(from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest);
}

Date.prototype.format=function(formatter){
        if (!formatter || formatter == "") {
            formatter = "yyyy-MM-dd";
        }
        var year = this.getFullYear().toString();
        var month = (this.getMonth() + 1).toString();
        var day = this.getDate().toString();
        var yearMarker = formatter.replace(/[^y|Y]/g, '');
        if (yearMarker.length > 1) {
            if (yearMarker.length == 2) {
                year = year.substring(2, 4);
            }
        } else {
            year = "";
        }
        var monthMarker = formatter.replace(/[^m|M]/g, '');
        if (monthMarker.length > 1) {
            if (month.length == 1) {
                month = "0" + month;
            }
        }
        else {
            month = "";
        }
        var dayMarker = formatter.replace(/[^d]/g, '');
        if (dayMarker.length > 1) {
            if (day.length == 1) {
                day = "0" + day;
            }
        } else {
            day = "";
        }
        return formatter.replace(yearMarker, year).replace(monthMarker, month).replace(dayMarker, day);
}

/*用相对不规则的字符串来创建日期对象,不规则的含义为:顺序包含年月日三个数值串,有间隔*/
String.prototype.createDate = function() {
    var regThree = /^\D*(\d{2,4})\D+(\d{1,2})\D+(\d{1,2})\D*$/;
    var regSix = /^\D*(\d{2,4})\D+(\d{1,2})\D+(\d{1,2})\D+(\d{1,2})\D+(\d{1,2})\D+(\d{1,2})\D*$/;
    var str = "";
    var date = null;
    if (regThree.test(this)) {
        str = this.replace(/\s/g, "").replace(regThree, "$1/$2/$3");
        date = new Date(str);
    }
    else if (regSix.test(this)) {
        str = this.match(regSix);
        date = new Date(str[1], str[2] - 1, str[3], str[4], str[5], str[6]);
    }
    if (isNaN(date)) return new Date();
    else return date;
}

/*
* 功能:根据输入表达式返回日期字符串
* 参数:dateFmt:字符串,由以下结构组成 
* yy:长写年,YY:短写年mm:数字月,MM:英文月,dd:日,hh:时,
* mi:分,ss秒,ms:毫秒,we:汉字星期,WE:英文星期.
* isFmtWithZero : 是否用0进行格式化,true or false
*/
Date.prototype.parseString = function(dateFmt, isFmtWithZero) {
    dateFmt = (dateFmt == null ? "yy-mm-dd" : dateFmt);
    isFmtWithZero = (isFmtWithZero == null ? true : isFmtWithZero);
    if (typeof (dateFmt) != "string")
        throw (new Error(-1, 'parseString()方法需要字符串类型参数!'));
    var weekArr = [["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
["SUN", "MON", "TUR", "WED", "THU", "FRI", "SAT"]];
    var monthArr = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];
    var str = dateFmt;
    str = str.replace(/yy/g, this.getFullYear());
    str = str.replace(/YY/g, this.getYear());
    str = str.replace(/mm/g, (this.getMonth() + 1).toString().fmtWithZero(isFmtWithZero));
    str = str.replace(/MM/g, monthArr[this.getMonth()]);
    str = str.replace(/dd/g, this.getDate().toString().fmtWithZero(isFmtWithZero));
    str = str.replace(/hh/g, this.getHours().toString().fmtWithZero(isFmtWithZero));
    str = str.replace(/mi/g, this.getMinutes().toString().fmtWithZero(isFmtWithZero));
    str = str.replace(/ss/g, this.getSeconds().toString().fmtWithZero(isFmtWithZero));
    str = str.replace(/ms/g, this.getMilliseconds().toString().fmtWithZeroD(isFmtWithZero));
    str = str.replace(/we/g, weekArr[0][this.getDay()]);
    str = str.replace(/WE/g, weekArr[1][this.getDay()]);
    return str;
}

/*将一位数字格式化成两位,如: 9 to 09*/
String.prototype.fmtWithZero = function(isFmtWithZero) {
    if (isFmtWithZero)
        return (this < 10 ? "0" + this : this);
    else return this;
}
String.prototype.fmtWithZeroD = function(isFmtWithZero) {
    if (isFmtWithZero)
        return (this < 10 ? "00" + this : (this < 100 ? "0" + this : this));
    else return this;
}

/* 功能 : 返回与某日期相距N天(N个24小时)的日期
* 参数 : num number类型 可以为正负整数或者浮点数,默认为1;
* type 0(秒) or 1(天),默认为秒
* 返回 : 新的PowerDate类型
*/
Date.prototype.dateAfter = function(num, type) {
    num = (num == null ? 1 : num);
    if (typeof (num) != "number") throw new Error(-1, "dateAfterDays(num,type)的num参数为数值类型.");
    type = (type == null ? 0 : type);
    var arr = [1000, 86400000];
    var dd = this.valueOf();
    dd += num * arr[type];
    return new Date(dd);
}

//判断是否是闰年,返回true 或者 false
Date.prototype.isLeapYear = function() {
    var year = this.getFullYear();
    return (0 == year % 4 && ((year % 100 != 0) || (year % 400 == 0)));
}


//返回该月天数
Date.prototype.getDaysOfMonth = function() {
    return (new Date(this.getFullYear(), this.getMonth() + 1, 0)).getDate();
}

//转换成大写日期(中文)
Date.prototype.getChinaDate = function() {
    var year = this.getFullYear().toString();
    var month = this.getMonth() + 1;
    var day = this.getDate();
    var arrNum = ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"];
    var strTmp = "";
    for (var i = 0, j = year.length; i < j; i++) {
        strTmp += arrNum[year.charAt(i)];
    }
    strTmp += "年";
    strTmp += arrNum[month] + "月";
    if (day < 10)
        strTmp += arrNum[day];
    else if (day < 20)
        strTmp += "十" + arrNum[day - 10];
    else if (day < 30)
        strTmp += "二十" + arrNum[day - 20];
    else
        strTmp += "三十" + arrNum[day - 30];
    strTmp += "日";
    return strTmp;
}

//日期比较函数,参数date:为Date类型,如this日期晚于参数:1,相等:0 早于: -1
Date.prototype.dateCompare = function(date) {
    if (typeof (date) != "object" || !(/Date/.test(date.constructor)))
        throw new Error(-1, "dateCompare(date)的date参数为Date类型.");
    var d = this.getTime() - date.getTime();
    return d > 0 ? 1 : (d == 0 ? 0 : -1);
}

/*功能:返回两日期之差
*参数:pd PowerDate对象
* type: 返回类别标识.yy:年,mm:月,ww:周,dd:日,hh:小时,mi:分,ss:秒,ms:毫秒
* intOrFloat :返回整型还是浮点型值 0:整型,不等于0:浮点型
* output : 输出提示,如:时间差为#周!
*/
Date.prototype.calDateDistance = function(date, type, intOrFloat, output) {
    if (typeof (date) != "object" || !(/Date/.test(date.constructor)))
        throw new Error(-1, "calDateDistance(date,type,intOrFloat)的date参数为Date类型.");
    type = (type == null ? 'dd' : type);
    if (!((new RegExp(type + ",", "g")).test("yy,mm,ww,dd,hh,mi,ss,ms,")))
        throw new Error(-1, "calDateDistance(pd,type,intOrFloat,output)的type参数为非法.");
    var iof = (intOrFloat == null ? 0 : intOrFloat);
    var miSecMain = this.valueOf();
    var miSecSub = date.valueOf();
    var num = 0;
    switch (type) {
        case "yy": num = this.getFullYear() - date.getFullYear(); break;
        case "mm": num = (this.getFullYear() - date.getFullYear()) * 12 + this.getMonth() - date.getMonth(); break;
        case "ww": num = ((miSecMain - miSecSub) / 7 / 86400000).fmtRtnVal(iof); break;
        case "dd": num = ((miSecMain - miSecSub) / 86400000).fmtRtnVal(iof); break;
        case "hh": num = ((miSecMain - miSecSub) / 3600000).fmtRtnVal(iof); break;
        case "mi": num = ((miSecMain - miSecSub) / 60000).fmtRtnVal(iof); break;
        case "ss": num = ((miSecMain - miSecSub) / 1000).fmtRtnVal(iof); break;
        case "ms": num = (miSecMain - miSecSub); break;
        default: break;
    }
    if (output)
        return output.replace(/#/g, " " + num + " ");
    else return num;
}

//返回整数或者两位小数的浮点数
Number.prototype.fmtRtnVal = function(intOrFloat) {
    return (intOrFloat == 0 ? Math.floor(this) : parseInt(this * 100) / 100);
}

//根据当前日期所在年和周数返回周一的日期
Date.prototype.RtnMonByWeekNum = function(weekNum) {
    if (typeof (weekNum) != "number")
        throw new Error(-1, "RtnByWeekNum(weekNum)的参数是数字类型.");
    var date = new Date(this.getFullYear(), 0, 1);
    var week = date.getDay();
    week = (week == 0 ? 7 : week);
    return date.dateAfter(weekNum * 7 - week - 7 + 7, 1);
}

//根据当前日期所在年和周数返回周日的日期
Date.prototype.RtnSunByWeekNum = function(weekNum) {
    if (typeof (weekNum) != "number")
        throw new Error(-1, "RtnByWeekNum(weekNum)的参数是数字类型.");
    var date = new Date(this.getFullYear(), 0, 1);
    var week = date.getDay();
    week = (week == 0 ? 7 : week);
    return date.dateAfter(weekNum * 7 - week - 2 + 7, 1);
}

//通过当前时间计算当前周数
Date.prototype.getWeekNumber = function() {
    var d = new Date(this.getFullYear(), this.getMonth(), this.getDate(), 0, 0, 0);
    var DoW = d.getDay();
    d.setDate(d.getDate() - (DoW + 6) % 7 + 3); // Nearest Thu
    var ms = d.valueOf(); // GMT
    d.setMonth(0);
    d.setDate(4); // Thu in Week 1
    return Math.round((ms - d.valueOf()) / (7 * 864e5)) + 1;
}
//计算当前日期为今年的第几天
Date.prototype.getDayNumber = function() {
     var now = new Date();
     var firstDay = new Date(now.getFullYear(),0,1);  
    //计算当前时间与本年第一天的时差(返回一串数值，代表两个日期相差的毫秒数)
     var dateDiff = now - firstDay; 
     //一天的毫秒数
     var msPerDay = 1000 * 60 * 60 * 24; 
     //计算天数
     var diffDays = Math.ceil(dateDiff/ msPerDay);
     
     return diffDays;
}

/**
 * @author 全冠清
 */
$.fn.extend({
	pos:function( value ){
		var elem = this[0];
			if (elem&&(elem.tagName=="TEXTAREA"||elem.type.toLowerCase()=="text")) {
			   if($.browser.msie){
					   var rng;
					   if(elem.tagName == "TEXTAREA"){ 
						    rng = event.srcElement.createTextRange();
						    rng.moveToPoint(event.x,event.y);
					   }else{ 
					    	rng = document.selection.createRange();
					   }
					   if( value === undefined ){
					   	 rng.moveStart("character",-event.srcElement.value.length);
					     return  rng.text.length;
					   }else if(typeof value === "number" ){
					   	 var index=this.position();
						 index>value?( rng.moveEnd("character",value-index)):(rng.moveStart("character",value-index))
						 rng.select();
					   }
				}else{
					if( value === undefined ){
					   	 return elem.selectionStart;
					   }else if(typeof value === "number" ){
					   	 elem.selectionEnd = value;
       			         elem.selectionStart = value;
					   }
				}
			}else{
				if( value === undefined )
				   return undefined;
			}
	}
})

$.fn.selectRange = function(start, end){
    return this.each(function(){
        if (this.setSelectionRange) {
            this.focus();
            this.setSelectionRange(start, end);
        }
        else 
            if (this.createTextRange) {
                var range = this.createTextRange();
                range.collapse(true);
                range.moveEnd('character', end);
                range.moveStart('character', start);
                range.select();
            }
    });
};
 

 
 
//继承ui.datepicker，覆盖 _selectDay方法
//提供日期範圍功能，即在同一日曆控件可選起訖日期作為查詢條件
$.extend($.datepicker, {

    /* Action for selecting a day. */
    _selectDay: function(id, month, year, td) {
        var target = $(id);
		
        if ($(td).hasClass(this._unselectableClass) || this._isDisabledDatepicker(target[0])) {
	        return;
        }
        var inst = this._getInst(target[0]);
		
        inst.selectedDay = inst.currentDay = $('a', td).html();
        inst.selectedMonth = inst.currentMonth = month;
        inst.selectedYear = inst.currentYear = year;
        if(this._get(inst, 'numberOfMonths')>1)
            if(target.data("between_"+id)=="" || typeof target.data("between_"+id)=='undefined'){
                target.data("between_"+id,this._formatDate(inst,
	                inst.currentDay, inst.currentMonth, inst.currentYear)+"~")
            }else{
                var start=(typeof target.data("between_"+id)=='undefined'?'':target.data("between_"+id));
                this._selectDate(id, start+this._formatDate(inst,
	                inst.currentDay, inst.currentMonth, inst.currentYear));
                target.data("between_"+id,"");
			
            }else{
                this._selectDate(id, this._formatDate(inst,
	                inst.currentDay, inst.currentMonth, inst.currentYear));
            }	
    }

});



function StringBuffer() {      
    this.__strings__ = new Array;      
}      
     
StringBuffer.prototype.append = function (str) {      
    this.__strings__.push(str);      
}      
     
StringBuffer.prototype.toString = function() {      
    return this.__strings__.join("");      
}      
