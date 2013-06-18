;
/**
* file
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
    var noop = function() { return true; };
    var frameCount = 0;


    $.widget("myui.file", {
        /**
        * 配置属性对象
        * <pre>
        * var cfg = {
        *     name: '',//grid名称
        *     url: '', //default url is current page url
        *     fixCols: 1, //凍結欄，0,1,2...從凍結到第幾欄，default为1，即Key栏，与CheckBox同在
        *     pageSize: 10, //default 10 records every page
        *     toolbarItem: [],//工具栏提供的按钮
        *     toolbarTop: true, //toolbar of top
        *     idPrefix: 'myui-grid-',
        *     condCol: true,//显示条件栏
        *     editorTrigger: 'click',
        *     buttonText: false, //图形按钮是否同时显示文字
        *     readonly: false, //优先级比PCM高
        *     single: false //只允许单选行，即checkbox 为只读状态
        * };
        * </pre>
        * @property options
        */
        options: {
            url: "/" + $.myui.getPath() + "/Upload.aspx",
            path: 'data',
            fileName: 'filedata',
            dataType: 'json',
            params: {}, //待传入的参数，若值以#开头，则为动态值，如{data_id:"#fm_spec_spec_id"}
            fileType:"*.*", //允许选取的文件类别,*为不限制
            maxSize:0, //允许选取的文件容量,单位：KB
            counts:0 //允许数量 0--不限制，>0：限制文件个数
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
            var ofile = this;
            var frameName = 'upload_frame_' + (frameCount++);
            var iframe = $('<iframe style="position:absolute;top:-9999px" />').attr('name', frameName);
            var form = $('<form method="post" style="display:none;" enctype="multipart/form-data" />').attr('name', 'form_' + frameName);
            var btn = $('<input type="button" value="添加文件">').attr("id","btn_" + ofile.element.attr("id"))
            var formHtml ="";
            
            
            
            btn.button({
                    icons: {primary: "ui-icon-folder-open"},
                    text:true
                });
            
            form.attr("target", frameName).attr('action', ofile.options.url).attr('id', "file_" + ofile.element.attr("id"));
            //设定ID
            ofile.options.params["id"] = form.attr("id");
            ofile.options.params["path"] = ofile.options.path;
            ofile.options.params["maxSize"] = ofile.options.maxSize.toString();
            
            // form中增加数据域
            formHtml = '<input type="file" name="' + ofile.options.fileName + '">';
            for (var key in ofile.options.params) {
                //定值
                if (ofile.options.params[key].substr(0, 1) != "#") {
                    formHtml += '<input type="hidden" name="' + key + '" value="' + ofile.options.params[key] + '">';
                }
            }
            form.append(formHtml);

            $('#' + ofile.element.attr("id")).append(btn);
            $('#' + ofile.element.attr("id")).append(iframe);
            $('#' + ofile.element.attr("id")).append(form);
            //iframe.appendTo("body");
            //form.appendTo("body");

            // iframe 在提交完成之后
            iframe.load(function(event) {
                var contents = $(this).contents().get(0);
                //style="white-space:nowrap;"
                var fileItem = "<li><a href='{0}'>{1}</a></li>";
                var ret = $(contents).find('p');
                if (ret.length == 1) {
                    ret =ret.text();
                    //转换为JSON格式
                    if (ret != "" && ret.substr(0, 1) == '{') {
                        ret = $.parseJSON(ret);
                        if(ret.code=="000000"){
                            fileItem = fileItem.format(ret.url,ret.filename);
                            //$('#' + ofile.element.attr("id")).append(fileItem);
                            ofile.bind(fileItem,true);
                            //success event
                            ofile._success(event, ret);
                        }
                    }
                    //complite event
                    ofile._complite(event, ret);
                }

                /*
                setTimeout(function() {
                iframe.remove();
                form.remove();
                }, 5000);
                */
            });

            // 文件框
            var fileInput = $('input[type=file][name=' + this.options.fileName + ']', form);
            fileInput.attr('id','if_'+ofile.element.attr("id"));
            //fileInput.attr('accept','accept=image/gif, image/x-ms-bmp, image/bmp');
            
            fileInput.change(function() {
                var file_ext="";
                var file_size=0;
                var pass=true,img;

                //.substr(obj.value.lastIndexOf(".")).toLowerCase(); 
                //alert(/.jpg|.png$/.test(fileInput.val()));
                //判断文件类型
                if(ofile.options.fileType!="*" && ofile.options.fileType!="*.*"){
                    file_ext = fileInput.val().substr(fileInput.val().lastIndexOf(".")).toLowerCase(); 
                    //判断文件类型
                    if(ofile.options.fileType.indexOf(file_ext)<0){
                        pass=false;
                        alert('此文件类型不允许上传！');
                    }
                }
                //文件大小判断

                if(pass && ofile.options.maxSize>0 && typeof document.getElementById('if_'+ofile.element.attr("id")).files!='undefined'){
                    file_size = document.getElementById('if_'+ofile.element.attr("id")).files[0].size;
                    if(file_size>ofile.options.maxSize*1024*1024){
                        pass = false;
                        alert('文件超过规定大小！不能上传！');
                    }
                }
 
                
                //检查通过
                if(pass){
                    form.submit();
                }
            });
            btn.unbind( "click" );
            btn.bind('click', {}, function() {
                formHtml = "";
                if (typeof ofile.options.params != 'undefined') {
                    for (var key in ofile.options.params) {
                        //若存在则更新其值
                        var oParam = $('#file_' + ofile.element.attr("id") + ' input[name="' + key + '"]');
                        if (oParam.length == 1) {
                            oParam.val($(ofile.options.params[key]).val());
                        } else {
                            //Client配置的params参数，含“#”的动态值
                            if (ofile.options.params[key].substr(0, 1) == "#" && $(ofile.options.params[key]).length == 1) {
                                formHtml += '<input type="hidden" name="' + key + '" value="' + $(ofile.options.params[key]).val() + '">';
                            }
                        }
                    }
                    //alert(formHtml);
                    form.append(formHtml);
                }
                //触发file upload button
                fileInput.click();

            });
        }
        /** 
        * 触发
        * @method refresh
        */
        , click: function(s_data_id) {
            var ofile = this;
            
            $("#btn_" + ofile.element.attr("id")).click();

        }
        /** 
        * 取得并刷新显示该附件主题已经存在的文件清单
        * @method refresh
        */
        , refresh: function(s_data_id) {
            var ofile = this
            ,data ;
            
	        data = $.myui.invoke("PM.BLL.PM_FILES.GetFiles", [s_data_id], {
	            id: ofile.element.attr("id")
                , url: ofile.options.url
                ,together:false
	        });
	        
	        ofile.bind(data,false);
        
        }
        /** 
        * 取得并刷新显示该附件主题已经存在的文件清单
        * @method bind
        */
        , bind: function(o_data,b_append) {
            var ofile = this
            ,data = o_data
            //style="white-space:nowrap;"
            //white-space: -moz-pre-wrap;
            ,fileItem = "<li><a href='{0}'>{1}</a>  <span name='{3}' >删除</span></li>"
            ,fileItem_tmp=""
            ,ul_tmp="<ul>"
            ,fileList
            ,records=[];
            
            
            fileList = $('#' + ofile.element.attr("id")).find('ul');
            //不为添加模式
            if(fileList.length==1 && !b_append){
                fileList.remove();
            }
            
            //
            if(typeof o_data=='object'){
                if (data && data.xconfig && data.xconfig.records) {
                    //not is array
                    if (typeof data.xconfig.records.record.length == 'undefined') {
                        records.push(data.xconfig.records.record);
                    } else {//is array
                        records = data.xconfig.records.record;
                    }
                }
                
                //loop for ervery row
                for (var m = 0; m < records.length; m++) {
                    
                    fileItem_tmp += fileItem.format(records[m].file_path,records[m].file_name,records[m].files_id);
                }
            }
            
            //在UL添加li
            if(fileList.length==1 && b_append){
                   fileList.append(o_data);
            }else{
                ul_tmp += fileItem_tmp + "</ul>";
                //alert(ul_tmp);
                $('#' + ofile.element.attr("id")).append(ul_tmp);      
            }
            //
            $('ul li span').button({
			    icons: {
				    primary: "ui-icon-trush"
			    },
			    text: true
		    }).click(function( event ) {
				alert('delete');
			});
        
        }        
        /** 
        *  upload complite event,当文件上传完成
        *  调用时，注意前面加'file'并全部用小写，eg. file.bind('filecomplate',{},function(event,eventdata){alert(eventdata.code + eventdata.filename + eventdata.url+ eventdata.fileid);});
        * @event complite
        * @param event {Object} 父事件的参数
        * @param data {Object} 传出的数据参数：返回值对象
        */
        , _complite: function(event, data) {
            this._trigger("complite", event, data);
        }
        /** 
        *  upload success event,当文件上传完成
        *  调用时，注意前面加'file'并全部用小写，eg. file.bind('filesuccess',{},function(event,eventdata){alert(eventdata.code + eventdata.filename + eventdata.url + eventdata.fileid);});
        * @event success
        * @param event {Object} 父事件的参数
        * @param data {Object} 传出的数据参数：返回值对象
        */
        , _success: function(event, data) {
            this._trigger("success", event, data);
        }        

    });
})(jQuery);