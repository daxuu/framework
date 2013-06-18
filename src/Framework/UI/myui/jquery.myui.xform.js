;
/**
* xform 对象：可用XML+XSLT来动态构建Form
* <pre>
* 功能：
*1.可用XML+XSLT来动态构建Form；
*2.
* [Update Log]
*1.20120713:create by rayd
*2.當模板的結構有異動時（如加Node），需更新到實例中
*3.分頁功能
*4.保存功能
*
* [Depends:]
*	1.jquery.ui.core.js
*	2.jquery.ui.widget.js
*   3.jquery.myui.core.js
*   4.jquery.xslt.js
*
*  [Sample code:]
*  var xf_chgpwd = $('#xf_chgpwd');
*  xf_chgpwd.xform({
*     url: 'account.aspx'
*    ,fixCols:4 //固定左边4栏
*    ,name:'帐号'//grid名称
*    ,buttonText:true//按钮显示图与文
*    ,readonly:true//只读
*    ,single: false //只允许单选行，即checkbox 为只读状态
*    ,condCol: true//显示条件栏
*    ,toolbarItem: ['p', 'q']//工具栏提供的按钮。p:page页次信息；q:query查询；a:add row添加行；r:remove rows移除行；:save保存；e:export导出
*  });
* </pre>
*
* @class combo
* @module myui.combo
* @namespace myui
*/   
(function($) {
    $.widget("myui.xform", {
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
            url: location.href //請求與回應的URL文件名稱
            ,autoSave:false    //資料異動後自動保存   
            ,xslt: ''          //xslt path
            ,xml:''            //xml string
            ,xformat:          //xml 資料規範定義
            {
                root:'xconfig records record'
                ,xfield:''
                ,xtemplate:''
            }
            ,post:{
                method:''
                ,data:{}
                ,url:''
            }            
        }
        /**
        * 版本
        * @property version
        */ 
        ,version: "1.0.0"
        /**
        * xml數據
        * @property version
        */ 
        ,xml:""
        ,id:""
        
        /** 
        * 建立xform對象
        * @method create
        */
        ,create: function() {
            this.id = this.element.attr("id");
            var fnCallback = ((this.options.callback==null||typeof this.options.callback !='function')?jQuery.noop:this.options.callback)
            ,data_context_id = 'xf_'+ this.id
            ,data_id = 'xdata_'+ this.id;
            
            this.xml = this.getXml();
            
            //alert(this.xml.html());
            //$("#"+this.id).xslt(this.xml.html(), this.options.xslt, fnCallback);
            var xform= this;
            $("#"+this.id).xslt(this.xml.html(), this.options.xslt, function(form){
                xform.initial(xform);
                
            });

             $("#"+this.id).append('<span id="toolbar" class="ui-widget-header ui-corner-all"><button id="save">保存</button></span>');
             $( "#save" ).button({
			    text: false,
			    icons: {
				    primary: "ui-icon-seek-start"
			    }
		    });
            //this.initial();
        } //end _create
       /** 
        * 初始化
        * @method initial
        */        
        ,initial:function(p_xform){

            var form = $('#' + p_xform.id);
            var xform = p_xform;
            if(form.children('div').length==1){
                $('input',form).bind('change',{xform:xform},function(event){
                    var values={};
                    values[$(this).attr('name')] = $(this).val();
                    //alert(values[$(this).attr('name')]);
                    event.data.xform.update(values);
                });
                $('textarea',form).bind('change',{xform:xform},function(event){
                    var values={};
                    values[$(this).attr('name')] = $(this).val();
                    event.data.xform.update(values);
                });
                
            }
            
        }
       /** 
        * 初始化
        * @method getXml
        */        
        ,getXml:function(){
            //xconfig records record config_xcontent
            var _notes = this.options.xformat.root.split(' ')
                ,_xml=""
                ,_xml_1 = ""
                ,_xml_2 = "";
                
             _notes.push(this.options.xformat.xfield);
            //substr(38)：remove <?xml version="1.0" encoding="utf-8"?>
            if($(this.options.xml.substr(38)).find('records record').length==0){    
                for (i=0;i<_notes.length;i++){
                    _xml_1 += '<' + _notes[i] + '>';
                    _xml_2 = '</' + _notes[i] + '>' + _xml_2;
                }
                _xml = '<xdata_'+this.id+'>' + _xml_1 + this.options.xformat.xtemplate + _xml_2 + '</xdata_'+this.id+'>';
            }else{
                _xml ='<xdata_'+this.id+'>' + $.myui.safeStr(this.options.xml.substr(38),true) + '</xdata_'+this.id+'>';           
            }
            return  $(_xml);
            
            
        }        
       /** 
        * 更新頁面數據:form.xml
        * @method update
        * @param p_values {Object} 新值對象，格式如：{key_fld_name:'001',fld_name1:'value_01',fld_name2:2.01}。
        */        
        ,update:function(p_values){
            var xfield=this.options.xformat.xfield;
            
            //$('#xdata_inner-center').find("xconfig records record").each(
            //多筆資料
            this.xml.find(this.options.xformat.root).each(function(){
               
                $(this).attr('ischanged','1');
                //alert();
                $(this).find(xfield).each(function(){
                    //欄位
                    for (name in p_values) {
                       $(this).find(name).text(p_values[name]); 
                    }
                })//end each xfield
            });//end each root
             
            //alert(this.xml.html());
            if(this.options.autoSave){
                this.save();
            }
        }
        /** 
        * 保存资料到DB中
        * @method save
        * @param event {Object} 传值对象，在Button上调用时要将form对象用参数传过来，不然this就会是Button本身。
        */        
	    , save: function(event) {
	        var xform = ((typeof event != 'undefined' && typeof event.data.form != 'undefined') ? event.data.xform : this)
	        ,ret = null
	        ,methodName = this.options.post.method
	        ,values = this.options.post.data;
	        //alert(xform.isChanged());
	        //xform.xml.find(this.options.xformat.root).each(function(){
	        //  data[] = $(this).html();
	        //});
            //欄位
            for (name in values) {
               if(values[name] =='' ){
                    values[name] = xform.xml.find(this.options.xformat.root).find(name).html(); 
               }
                //alert(values[name]);	           
            }
            
	        //data.config_xcontent=xform.xml.find(xform.options.xform.xfield).html();
	        //data.configxml_id = xform.xml.find().html();
	        
	        //资料有异动
	        if (xform.isChanged()) {
	            //ret = $.myui.request('s', {}, xform.options.url);
                ret = $.myui.invokeBLL(methodName,values);

	        } else {
	            return false;
	        }

	        if (ret == "" || ret == null) {
	            //$.myui.msg('bl0000',true, 'w');
	            $.myui.msg('bl0000',true, 'n');
	        } else {
	            try {
	                if (ret.msg.result == "fail") {
	                    $.myui.msg(ret.msg.message, 'w');
	                }
	            } catch (e) {
                    if (ret.indexOf("message") != -1) {
	                    ret = ret.substring(ret.indexOf("message") + 10, ret.length);
	                    $.myui.msg(ret, 'w');
	                } else {
	                    $.myui.msg(ret, 'w');
	                }	            
	            }
	        }
	    }
	    /** 
        * Form中的資料是否發生任何改變
        * @method isChanged
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。
        */ 
	    ,isChanged:function(){
	        var ret = false;
	        var xform = ((typeof event != 'undefined' && typeof event.data.form != 'undefined') ? event.data.xform : this);

	        if (xform.xml.find(xform.options.xformat.root).length==1){
	            ret=true;
	        }
	        return ret;
	    }
    }); //end $.widget

})(jQuery);