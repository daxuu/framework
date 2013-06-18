;
/**
* grid 对象
* <pre>
* 功能：
*v1.可固定表头；
*v2.可升降排序，可设定某栏是否可排序
*x3.可调整栏宽
*v4.每一栏可设定条件输入栏，可设定
*x5.可设定多栏，跨栏
*v6.可固定栏，并可设定前面几栏
*v7.可接受数据（JSON/ARRAY）并自动产生Grid对象
*v8.可对原HTML的Table Grid化，即可
*v9.可选多行，
*v10.可在Grid单元格中双击（或单击，可设定）编辑，编辑形态有input/datepicker/checkbox/combox/number/textarea/treeview
*x11.可显示图形栏/并可设定是否显示在内容前还是单独的一栏
*v12.可翻页，设定每页笔数、跳页/显示总笔数
*v13.支持键盘：Page：翻页；上下移格或移行，最后一行往下为下页/最前一行往上为上页/最后一格往下为第一格，最前一格往前为最后一格
*v14.可设定每栏是否隐藏
*v15.可改变每单元格的内容或属性，如红色字体/加粗/或删除标记
*x16.有图标标记功能，如紧急/待处理/完成
*v17.对与可编辑栏，可设定数据校验，并准确提示
*x18.可选栏（×）
*vx19.可添加、插入、移除、拖动行，添加时并定位到新行/
*v20.可查询/保存，并可设定动作，如通过Ajax提交或取得数据。
*v21.有状态区，可设定在上面（TOP）或下面（BOTTOM），或者上下同时存在。
*x22.可调整行高。
*v23.可对行动态附加事件，提供给开发应用扩充，如在选中行后，在明细区显示明细数据。
*v24.可对单元格动态附加事件，提供给开发应用扩充，如数量改变后，金额应相应改变。
*v25.可加入子层，并结构化显示
*v26.Toolbar可添加Item，含button，input等物件
*x27.可能动态设定表头
*v28.可设定自动保存机制，-1为不自动保存，0--马上保存，n(>0)--为没隔n秒保存一次。
*x29.rowselected事件应实现在100ms内延迟触发。 以防止在快速换行时，不需要触发
*v30.汇总数字栏
*v31.加Button Group
* [Update Log]
*1.点CheckALL会触发SetCond事件 OK
*2.日期假空问题 OK
*3.Click同一行时，会多次触发selectedrow事件
*4.批量翻译功能 OK
*5.checkbox对齐问题
*6.wingrid对齐问题
*7.Rayd20100906：checkBox对齐问题修复
*8.Rayd20100907：applyFrom，增加對null值的過濾條件。
*9.Rayd20100913：getValue，修复对CheckBox取不到值问题。
*10.Rayd20100913：editor，增加当Editor失去焦距时可自动保存功能，调整Adjust方法。
*11.Rayd20100915：公开_bind方法，更改_bind为bind。
*11.Rayd20100915：修复getValue方法：解决弹出窗口返回出错BUG。
*12.Rayd20100915：调整shortcut，移动行与格不需按alt键。
*13.Rayd20100915：更该编辑栏Default为值选中（Select）状态。
*14.Rayd20101011：解決資料未保存排序時會出現【objec object】錯誤值狀況當資料發送改變時，會有ischanged屬性。在Sort時可能會出現此狀況
*15.Rayd20101011：增加按資料設定樣式顯示資料功能，如預期工作未完成，的資料應以紅色顯示。
*16.Rayd20101020：增加rowcheck event。
*17.Rayd20101021：增加動態統計欄功能：可多欄統計，CheckBox選中範圍統計。
*18.Rayd20101104：優化性能。添加HTML5的本地存储功能（已取消，测试功能不稳定）
*19.Rayd20101118：修復兩次Update Bug。
*20.Rayd20101226：新增複製行功能：copyRow。
*21.Rayd20110119：优化AddRow性能，解决批量添加行时（如从子窗口挑选多笔资料进来）速度慢问题：a.减少请求次数;b.增加skip选项，可设定添加时间是否自动跳到新行（当有明细时）。
*22.Rayd20110125：优化Query性能：前端移除isChanged检查；Query加带条件对象参数，pcm.cs配合调整；
*23.Rayd20110126：解決combox用快捷鍵時會跳到資料區問題；
*24.Rayd20110302：增加可設定條件區是否使用日厲功能，default為不使用，在PCM中設定可使用；增加條件欄Combox帶多個值設定，
*25.Rayd20110309：初始化或沒有資料時，也需有橫向滾動條，增加全部匯總功能；
*26.Rayd20110310：Sum在IE/Chrome中累計不準問題。調整橫向滾動對齊問題。增加調整欄寬功能，連續點擊同一行編輯行高會逐漸擴大
*27.？：增加自定義條件功能
*28.？：增加快捷鍵翻到最後一頁實現自動翻頁，並自動定焦功能
*29.？：增加自動信息提示功能
*30.Rayd20110830:增加options.pcm，options.config為Client配置config,並增加可以在Client中自行配置Columns與Config功能
*31.Rayd20121125: 增加后端资料没有Key值，将自动新增，用在给用户分配菜单权限时，将菜单资料全部列出，单选取后会自动新增
*32.Rayd20121212: 增加传输数据加密/接收数据解密功能
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
* @class grid
* @module myui.grid
* @namespace myui
*/ 
(function($) {
    var gridId = 0,
    //ajaxUrl = '',
	listId = 0,
    /*
    p:page
    q:query
    a:add row
    r:remove row
    s:save
    e:export
    */
    defItem = ['p', 'q', 'a', 'c', 'r', 's', 'e'],
    checkWidth = 36//行首CheckBox的宽度,最小36px方可显示Scroll箭头
    , open_class = 'ui-icon-triangle-1-e', close_class = 'ui-icon-triangle-1-s'
    , TABLE_STYLE = ' border="0" cellpadding="1"  cellspacing="1" ';

    $.widget("myui.gridview", {
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
        *     columns [{name:'#ID',type:'string',length:20,}]
        *     cloumns 元素说明：
        *       id：栏位，与资料源中的fieldName相同，均为小写 ,必须
        *       name：栏名称，显示在画面上，若没有则以id取代
        *       type：string-字符型
        *       sort：asc-从小到大排列；desc-从大到小排列
        *       size：设定的栏宽，单位px            
        *       iskey：0-不为KEY栏；1-为KEY栏 Default="0"
        *       size:宽度
        *       hidden:"1"
        *       defaultValue:
        *           $LAST:
        *           $GUID
        *           $TODAY:Client端的當前日期，格式為yyyy-mm-dd
        *           $COND:條件中設定的值，可用到一些hidden欄
        *           $SEQ:序號，
        *           自定義Function：function(id,fields,values){return id + "-" + values[fields[1].id]}
        *           若以上項目還不可適應需求，可用自定義Function
        *       checkbox:"Y" 
        *       uncheckbox:"N"
        *       format:ex.function(values){return value.proj_name + values.proj_no}
        *       align:"right/left/center"
        *       readonly:"0"-不可编辑；"1"-可编辑(Default)
        *       filter:1-可查询栏，0-不可查询
        *       adsc:1-可按栏切换升降排序，0-不可自行排序。
        *       min:"0"
        *       max:"10"
        *       unique:"1/0":是否唯一限制
        *       encrypt:true/false:传输的该栏数据加密处理，保存到DB是密文，在Bind时再解密显示到画面，调用$.myui.encrypt('value','column.id')加密；$.myui.dncypt('value','column.id')解密
        *     ----COLUMNS OPTIONS END  
        *     editorTrigger: 'click',
        *     buttonText: false, //图形按钮是否同时显示文字
        *     readonly: false, //优先级比PCM高
        *     single: false //只允许单选行，即checkbox 为只读状态
        *     isCheckAll:false //默认全选，即在资料从服务器取过来后，为选中状态，汇总（Sum）时用到
        *     encrypt:true/false:传送到服务器的数据包加密处理，保护传输过程，会降低性能，慎用
        *     decrypt:true/false:输出的数据包需解密处理，保护传输过程，会降低性能，慎用
        * };
        * </pre>
        * @property options
        */
        options: {
            name: '',
            url: '', //default url is current page url
            width: 0,
            widthLeft: 20,
            widthRight: 200,
            height: 0,
            fixCols: 1, //凍結欄，0,1,2...從凍結到第幾欄，default为1，即Key栏，与CheckBox同在
            pageSize: 10, //default 10 records every page
            pageInfo: { size: 10, curr: 1, pages: 0, records: 0 },
            config: {},
            cond: {}, //资料查询条件
            columns: [],
            editSelect: true, //编辑框Default为选中值状态
            appendLast: true, //true:addRow在最后（Default）；false:在最前面
            toolbarSimple: false, //只有翻页按钮
            toolbarItem: [], //工具栏提供的按钮
            toolbarTop: true, //toolbar of top
            toolbarBottom: false, //toolbar of bottom
            idPrefix: 'myui-grid-',
            condCol: true, //显示条件栏
            editorTrigger: 'click',
            buttonText: false, //图形按钮是否同时显示文字
            autoSelected: true, //自动选中第一行
            readonly: false, //优先级比PCM高
            copy: false, //开启Copy功能，default=false
            skip: true, //当添加资料时，自动跳到设定行（如最后一行，或最前一行），参考appendLast
            single: false, //只允许单选行，即checkbox 为只读状态
            lrow: "<tr><td></td></tr>", //Left行HTML模板
            rrow: "<tr><td></td></tr>", //Right行HTML模板
            isCheckAll: false, //在资料从服务器取过来后，为全部选中状态，汇总（Sum）时用到
            encrypt: false, //整个数据包加密传送
            decrypt: false, //整个数据包接收后解密
            isCheckAll: false, //在资料从服务器取过来后，为全部选中状态，汇总（Sum）时用到
            ajax: 'AjaxHandler.ashx' //ajax通道，AjaxHandler为全能型反射通道
        }
        /**
        * 版本
        * @property version {string}
        */
        , version: "2.0.20110830"
        /** 
        * 建立grid對象
        * @method _create
        */
        , _create: function() {
            //clear CData
            this.CData = [];
            this.ID = this._gridId();
            //var t1 = new Date();
            if (this.options.name == '') {
                this.options.name = this.element.attr("id");
            }
            if (this.options.url == '') {
                this.options.url = $.myui.getUrl(location.href);
            }
            this._sumFields = [];
            this._tabify(true);
            //$.myui.taken(this._gridId() + '，_create ', t1);
            this.setShortCut();

            this.adjust();
            this.focus();

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
        * Grid 识别身份ID
        * @property ID {string} 
        */
        , ID: ""

        /**
        * Grid Msg內容對象（取消此屬性2011-01-16）
        * @property Msg {Object} 
        */
        //,Msg:null

        /**
        * 左边头区域对象，含title与Condiction 
        * @property LeftHeader {jQuery Object}
        */
        , LeftHeader: "1.0.0"

        /**
        * 右边资料区域对象，含title与Condiction 
        * @property RightHeader {jQuery Object}
        */
        , RightHeader: "1.0.0"
        /**
        * 左边资料区域对象
        * @property LeftData {jQuery Object}
        */
        , LeftData: "1.0.0"

        /**
        * 右边资料区域对象
        * @property RightData {jQuery Object}
        */
        , RightData: "1.0.0"
        /**
        * 异动资料
        * @property CData {jQuery Object}
        */
        , CData: []
        /** 
        * 自动调整宽、高，若沒有指定值，以父容器寬度、高度为標準
        * @method adjust
        */
        , adjust: function(weight, height) {
            //alert(this.element.parent().width());
            //var w = this.element.parent().width() - 17  //Scroll

            var w = (typeof weight == 'undefined') ? this.element.parent().width() : outerWeight
            , h = (typeof height == 'undefined') ? this.element.parent().height() : height
            , h_top = 0
            , h_bottom = 0
            , h_title = 0;


            if (this.options.width > 0) {
                w = this.options.width;
            }
            if (this.options.height > 0) {
                h = this.options.height;
            }
            if (w == 0 || h == 0) {
                return;
            }
            //调整right body起始位置
            //$('#' + this._gridId() + '-body-right').css({ left: $('#' + this._gridId() + '-body-left').width() + 9 });
            //调整Title行高
            //this._adjustTitle();

            if (this.options.toolbarTop) {

                this.element.find('#' + this._gridId() + '-tb-top').css({ width: w + 'px' });
                h_top = this.element.find('#' + this._gridId() + '-tb-top').height();
            }
            if (this.options.toolbarBottom) {
                this.element.find('#' + this._gridId() + '-tb-bottom').css({ width: w + 'px' });
                h_bottom = this.element.find('#' + this._gridId() + '-tb-bottom').height();

            }
            h_title = this.element.find('#' + this._gridId() + '-title-left').height() + 4;

            //alert("width:" +this.element.parent().html() + " result w=" +(w ));
            //+10 ？
            this.element.find('#' + this._gridId() + '-body-right').css({ width: (w - this.options.widthLeft) + 'px' });
            this.element.find('#' + this._gridId() + '-data-right').css({ height: (h - h_top - h_bottom - h_title) + 'px' });
            this.element.find('#' + this._gridId() + '-data-left').css({ height: (h - h_top - h_bottom - h_title) + 'px' });


        }
        /** 
        * 得到焦距
        * @method focus
        */
        , focus: function() {
            //gd_data_r.focus();
            if ($('#' + this._gridId() + '-tb-top-c').length == 1) {
                $('#' + this._gridId() + '-tb-top-c').find('input').first().focus();
            } else if ($('#' + this._gridId() + '-tb-top-c').length == 1) {
                $('#' + this._gridId() + '-tb-bottom-c').find('input').first().focus();
            } else if ($('#' + this._gridId() + '-title-left').length == 1) {
                $('#' + this._gridId() + '-title-left').find('input').first().focus();
            }

        }

        /** 
        * 得到当前区域，暂时未用
        * @method currArea
        */
        , currArea: function(focusObj) {
            var ret = focusObj,
            id = document.activeElement.id;

            //while (focusObj.nodeName == 'div') {
            ret = ret.parent();
            //alert(ret.element.nodeName);
            //}
            return ret;

        }
        /** 
        * 设定快捷键
        * @method setShortCut
        */
        , setShortCut: function() {
            var grid = this;
            grid.element.bind("keydown.grid", function(event) {
                var keyCode = $.ui.keyCode;
                //在條件區讓快捷鍵失效
                if (document.activeElement.id.indexOf('-cond-') > 1) {
                    return;
                }
                //
                //alert(event.keyCode);
                switch (event.keyCode) {
                    case 112: //f1
                        grid.query();
                        event.preventDefault();
                        break;
                    case 113: //f2
                        grid.addRow();
                        event.preventDefault();
                        break;
                    case 114: //f3
                        grid.removeRow();
                        event.preventDefault();
                        break;
                    case 115: //f4
                        grid.save();
                        event.preventDefault();
                        break;
                    case 116: //f5
                        grid.exportData();
                        event.preventDefault();
                        break;
                    case keyCode.PAGE_UP:
                        grid.toPage('prev');
                        event.preventDefault();
                        break;
                    case keyCode.PAGE_DOWN:
                        grid.toPage('next');
                        event.preventDefault();
                        break;
                    case 36: //home
                        if (event.altKey) {
                            grid.toPage('first');
                            event.preventDefault();
                        }
                        break;
                    case 35: //end
                        if (event.altKey) {
                            grid.toPage('last');
                            event.preventDefault();
                        }
                        break;
                    case keyCode.UP:
                        //not is combo
                        if (grid.currCell() != null && grid.currCell().find('input').length == 1 && !(grid.currCell().find('input').hasClass("ui-autocomplete-input"))) {
                            grid.skipRow(-1);
                            event.preventDefault();

                        }
                        /* 
                        if (event.altKey) {
                        grid.skipRow(-1);
                           
                        //                            setTimeout(function(){
                        //                            grid.skipRow(-1);
                        //                            }, 100);
                            
                        event.preventDefault();
                        }
                        */
                        break;
                    case keyCode.DOWN:
                        if (grid.currCell() != null && grid.currCell().find('input').length == 1 && !(grid.currCell().find('input').hasClass("ui-autocomplete-input"))) {
                            grid.skipRow(1);
                            event.preventDefault();
                        }
                        /*
                        if (event.altKey) {
                        grid.skipRow(1);
                            
                        //                            setTimeout(function() {
                        //                            grid.skipRow(1);
                        //                            }, 100);
                        //                            
                        event.preventDefault();
                        }
                        */
                        break;
                    case keyCode.ENTER:

                        if (event.altKey) {
                            grid.skipCell(-1);

                        } else {
                            grid.skipCell(1);
                        }
                        event.preventDefault();

                        break;
                    case keyCode.RIGHT:
                        if (grid.currCell() != null && grid.currCell().find('input').length == 1 && grid.currCell().find('input').val().length == grid.currCell().find('input').pos()) {
                            grid.skipCell(1);
                            event.preventDefault();
                        }
                        /*
                        if (event.altKey) {
                        grid.skipCell(1);
                        event.preventDefault();
                        }
                        */
                        break;
                    case keyCode.LEFT:
                        if (grid.currCell().find('input').pos() == 0) {
                            grid.skipCell(-1);
                            event.preventDefault();
                        }
                        /*
                        if (event.altKey) {
                        grid.skipCell(-1);
                        event.preventDefault();
                        }
                        */
                        break;

                    default:

                        break;
                }
            })
        }
        /** 
        * 跳至并选中下一单元格，或上一单元格
        * @method skipCell
        * @param index {number} 1--下一单元格，-1--上一单元格
        */
        , skipCell: function(index) {
            var rowIndex, colIndex, nextTD,
            grid = this,
            currRow,
            currCell,
            lr = -1, i = -1, pos;

            //没有选中行
            if (this.currRow() == null && grid.currRow() != null) {
                grid.selectedRows(0);
                //left只有Check与KEY栏
                if (grid.currRow()[0].children().length < 3) {
                    lr = 1;
                    i = 0
                } else {
                    lr = 0;
                    i = 1
                }
                pos = this._nextPos(lr, i);
                lr = pos.lr;
                i = pos.x;


                $(grid.currRow()[lr].children()[i]).trigger(this.options.editorTrigger);
                return;
            }

            //没有单元格被选中
            if (this.currCell() == null) {

                lr = 1;
                i = 0;
            } else {
                lr = grid._currColIndexLeft > 0 ? 0 : 1;
                i = (lr == 0 ? grid._currColIndexLeft + index : (grid._currColIndexRight >= 0 ? grid._currColIndexRight + index : 0));
                //Right
                pos = this._nextPos(lr, i);
                lr = pos.lr;
                i = pos.x;
            }
            //$.myui.msg('i=' + i + ' leftIndex=' + grid._currColIndexLeft + ' RightIndex=' + grid._currColIndexRight + ' lr=' + lr);
            if (grid.currRow() == null) {
                return;
            } else {
                currCell = $(grid.currRow()[lr].children()[i]);
            }

            while (currCell.is(':hidden')
            //|| currCell.attr('readonly') == '1'--当全部是Readonly时，会引起无限循环
            || currCell.find('input[type="checkbox"]').length == 1) {
                i += index;
                //alert('Left or Right:' + lr + ' is hidden:' + currCell.is(':hidden') + ' readonly=' + currCell.attr('readonly') + ' i=' + i);

                pos = this._nextPos(lr, i);
                lr = pos.lr;
                i = pos.x;
                currCell = $(grid.currRow()[lr].children()[i]);
            }
            //if (this.currCell().data('old') != this.currCell().find('input').val()) {

            if (this.currCell() != null && this.currCell().find('input').length == 1) {
                this.currCell().find('input').trigger('change');
            }
            //}
            //checkbox
            if (this.currCell() != null && currCell.find('input[type="checkbox"]').length == 1) {
                currCell.find('input').focus();
                currCell.trigger(this.options.editorTrigger);
            } else {
                currCell.trigger(this.options.editorTrigger);
            }
        }
        /** 
        * 找到下一个位置
        * @method _nextPos
        * @param lr {number} left & right lr:0--left ;1--right
        * @param i {number} 横向位置
        * @return {Object} 左右区与x横向位置，eg.{ lr: lr, x: i };
        */
        , _nextPos: function(lr, i) {
            var grid = this;
            //right
            if (lr == 1) {
                //last cell
                if (i >= grid.currRow()[lr].children().length) {
                    //当Left只有CheckBox与Hidden的KEY时（<3）,不能跳到Left
                    if (grid.currRow()[0].children().length < 3) {
                        i = 0;
                    } else {
                        i = 1;
                        //切换Left Right Data Area
                        lr = 0;
                    }
                    grid.skipRow(1); //grid.currRow()发生改变。
                }
                //first cell
                if (i < 0) {
                    //当Left只有CheckBox与Hidden的KEY时（<3）,不能跳到Left
                    if (grid.currRow()[0].children().length < 3) {
                        i = grid.currRow()[lr].children().length - 1;
                        grid.skipRow(-1); //grid.currRow()发生改变。
                    } else {
                        lr = 0; //切换Left
                        i = grid.currRow()[lr].children().length - 1;
                    }
                }
            } else {//Left :lr==0
                //last cell
                if (lr == 0 && i >= grid.currRow()[lr].children().length) {
                    i = 0;
                    //切换Left Right Data Area
                    lr = 1;
                }
                //first cell
                if (lr == 0 && i <= 1) {
                    if (grid.currRow()[1].children().length > 0) {
                        lr = 1; //切换Left

                        i = grid.currRow()[1].children().length - 1;
                        grid.skipRow(-1); //grid.currRow()发生改变。
                    } else {

                        i = grid.currRow()[0].children().length - 1;
                    }
                }
            }

            return { lr: lr, x: i };
        }
        /** 
        * 跳至并选中下一行，或上一行
        * @method skipRow
        * @param index {number} 1--下一行，-1--上一行 ,0--第一行，9--最后一行
        * @param isEditor {bool} true--除跳行外，若原有编辑框，同时开启编辑；false--不需要开启编辑功能。
        */
        , skipRow: function(index, isEditor) {
            var grid = this,
            m = index,
            lr = -1, i = -1,
            edit = true;
            //grid._currRowIndex += index;
            //alert(grid.rows());
            if (typeof isEditor != 'undefined') {
                edit = isEditor;
            }
            switch (index) {
                case -1:
                case 1:
                    m = grid._currRowIndex + index;
                    if (m < 0) { m = grid.rows() - 1 };
                    if (m >= grid.rows()) { m = 0 };

                    break;
                case 0:
                    m = 0;
                    break;
                case 9:
                    //start from 0 
                    m = grid.rows() - 1;
                default:
                    break;
            }
            //以下代码待完善：不要触发RowSelected事件，不利于性能，当RowSelected有bind时。
            grid.selectedRows(m);
            if (grid.currRow() != null) {
                while (grid.currRow()[0].attr('style').indexOf('display: none') > -1) {
                    if (index == 1 || index == -1) {
                        m = m + index;
                    } else if (index == 0) {
                        m = m + 1;
                    } else if (index == 9) {
                        m = m - 1;
                    }
                    //alert(m);
                    grid.selectedRows(m);
                }

            }

            //alert(m);
            //原行有编辑功能，且设定可开启
            if (edit && $('#editor').length == 1) {
                lr = grid._currColIndexLeft > 0 ? 0 : 1;
                i = grid._currColIndexLeft > 0 ? grid._currColIndexLeft : grid._currColIndexRight;
                $('#editor').trigger('change');
                //alert($(this).attr('name'));
                grid.currRow()[lr].children().eq(i).trigger(this.options.editorTrigger);
            }
        }

        /** 
        * 添加一行
        * @method addRow
        * @param event {Object} eg.{grid:gv_sample,values:{fldName1:'value1',fldName2:'value2'}}
        */
	, addRow: function(event) {
	    var data, val = {},
        grid = ((typeof event != 'undefined' && typeof event.data != 'undefined' && typeof event.data.grid != 'undefined') ? event.data.grid : this),
        size = parseInt(grid.options.pageInfo.size),
        curr = parseInt(grid.options.pageInfo.curr),
        records = parseInt(grid.options.pageInfo.records) + 1,
        idx = 0,
        lastRow = null,
        keyValue = "",
	    rowData = { "xconfig": { "records": { "record": {}} }, "info": { "count": "1"} }; ;

	    idx = grid.rows() - 1;
	    lastRow = grid.row(idx);

	    //每欄
	    $.each(grid.options.columns, function(i, o) {
	        rowData.xconfig.records.record[o.id] = "";
	        //key
	        if (typeof o.iskey != 'undefined' && o.iskey == 1) {
	            keyValue = $.myui.guid();
	            rowData.xconfig.records.record[o.id] = keyValue;
	        }
	        if (typeof o.defaultValue == 'string') {
	            switch (o.defaultValue) {
	                case "$GUID":
	                    rowData.xconfig.records.record[o.id] = $.myui.guid();
	                    break;
	                case "$LAST":
	                    if (lastRow != null) {
	                        rowData.xconfig.records.record[o.id] = lastRow[o.id];
	                    }
	                    break;
	                case "$COND":
	                    if (typeof grid.options.cond[o.id] == 'string') {
	                        rowData.xconfig.records.record[o.id] = grid.options.cond[o.id];
	                    }
	                    break;
	                case "$TODAY":
	                    rowData.xconfig.records.record[o.id] = new Date().format();
	                    break;
	                case "$SEQ":
	                    rowData.xconfig.records.record[o.id] = records;
	                    break;

	                default: //
	                    rowData.xconfig.records.record[o.id] = o.defaultValue;
	                    break;
	            }
	        }
	        //調用function來取得Default值；
	        if (typeof o.defaultValue == 'function') {
	            rowData.xconfig.records.record[o.id] = o.defaultValue.call(grid, o.id, grid.options.columns, rowData.xconfig.records.record);
	        }


	    });
	    grid.bind(rowData, true);

	    grid.setValue(rowData.xconfig.records.record, keyValue, "add");

	    //alert(records);
	    grid._setPage(
	    {
	        records: records
	        , pages: records % size == 0 ? records / size : Math.floor(records / size) + 1

	    }
	    );
	    //	    if(grid.options.appendLast){
	    //			//to last row at current page
	    //			grid.skipRow(9);
	    //	    }else{
	    //			//to first row
	    //			grid.skipRow(0);
	    //	    }
	    //	    if (typeof event != 'undefined' && typeof event.values != 'undefined') {

	    //	        for (name in event.values) {
	    //	            //alert(event.values[name]);
	    //	            val[name] = event.values[name];
	    //	            grid.setValue(val);
	    //	            val = {};
	    //	        }
	    //grid.setValue(event.values);
	    //	    }

	    return this;
	}
        /** 
        * 添加一行
        * @method addRow
        * @param event {Object} eg.{grid:gv_sample,values:{fldName1:'value1',fldName2:'value2'}}
        */
	, insertRow: function(pos) {
	    this.addRow();
	}
        /** 
        * 设定Default值
        * @method setDefault
        * @param values {Object} 值对象,eg.{proj_id:'00120',proj_name:'Test 1'},当values._parttime='1'时，将会将values中的所有栏，同时设定为条件
        */
	, setDefault: function(values) {
	    var tp = 'upddef',
	        data = { kind: 'def', fld: '', value: '', rowid: '' };
	    //alert(this._gridId());
	    for (var name in values) {
	        data.fld = name;
	        data.value = values[name];
	        //$.myui.request(tp, data, this.options.url);
	    };
	    this.setValue(values);
	    //$.myui.request(tp, values, this.options.url);
	    //return this;
	}
        /** 
        * 同setDefault
        * @method setDef
        */
	, setDef: function(values, isCond) {
	    this.setDefault(values);
	}
        /** 
        * 取得本頁被選中行（是Checked，不是Selected)
        * @method getCheckedRows
        * @param event {Object} eg.{grid:gv_sample,values:{fldName1:'value1',fldName2:'value2'}}
        *   
        */
	, getCheckedRows: function(event) {
	    var grid = ((typeof event != 'undefined' && typeof event.data != 'undefined' && typeof event.data.grid != 'undefined') ? event.data.grid : this)
	    , rows = [], row
	    , count = grid.rows();

	    for (var i = 0; i < count; i++) {
	        if (grid.currRow(i)[0].find('td[name="cell0"]').find('span>input').attr('checked')) {
	            row = grid.currRow(i);
	            rows.push(row);
	        }
	    }
	    return rows;
	}
        /** 
        * 复制单前行,若要开启此功能，设定options.copy=true
        * @method copyRow
        * @param event {Object} eg.{grid:gv_sample,values:{fldName1:'value1',fldName2:'value2'}}
        *   
        */
	, copyRow: function(event) {
	    var grid = ((typeof event != 'undefined' && typeof event.data != 'undefined' && typeof event.data.grid != 'undefined') ? event.data.grid : this)
	    , row = grid.currRow()
	    , rows = grid.getCheckedRows(event)//收集選中行。 
	    , data = {}, name, value;



	    //若沒有Check中行，
	    if (rows.length == 0) {
	        row = grid.currRow();
	        if (row != null) {
	            rows.push(grid.currRow());
	        }
	    }
	    //複製每一行
	    for (var j = 0; j < rows.length; j++) {
	        row = rows[j];
	        for (var m = 0; m < row.length; m++) {
	            row_lr = row[m];

	            for (var n = 0; n < row_lr.children().length; n++) {
	                name = row_lr.children().eq(n).attr('name');
	                if (row_lr.children().eq(n).find('input').length == 1) {
	                    value = row_lr.children().eq(n).find('input').attr('value');
	                } else {
	                    value = row_lr.children().eq(n).find('span').text();
	                }
	                //key value,KEY栏不需要COPY
	                if (name == 'cell0') {
	                    key = row_lr.children().eq(n).attr('val');
	                }
	                if (typeof name == 'string' && name != 'cell0' && value != key) {
	                    data[name] = value;
	                }
	            }
	        }
	        //添加一行
	        grid.addRow();
	        //复制当行所有值到新行
	        grid.setValue(data);
	    }

	}
        /** 
        * 设定查找资料的条件
        * @method setCond
        * @param values {Object} 值对象,eg.{proj_id:'00120',proj_name:'Test 1'},当values._parttime='1'时，将会将values中的所有栏，同时设定为Default
        */
	    , setCond: function(values) {
	        var cond_id;

	        for (var name in values) {
	            //保存到Options.cond中
	            this.options.cond[name] = values[name];
	            //将条件值Show到画面
	            cond_id = '#' + this.element.attr("id") + '-cond-' + name;
	            if ($(cond_id).length > 0) {
	                if ($(cond_id).val() != values[name]) $(cond_id).val(values[name]);
	            }
	        }
	        //alert(values);
	        return this;
	    }
        /** 
        * 取得符合条件的资料
        * @method query
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。或为条件对象，如：gv_testreport.grid('query',{ app_id: appId,_parttime:'1' })
        * @return {JSON} 返回JSON形态的资料包。
        */
	, query: function(event) {
	    var cond = {},
	    pageInfo,
	    pageCurr = 1, //当前页次
	    pagePages,
	    grid = ((typeof event != 'undefined' && typeof event.data != 'undefined' && typeof event.data.grid != 'undefined') ? event.data.grid : this),
	    pageSize = grid.options.pageSize, //每页笔数
	    data,
	    fields = '',
        ret = true;
	    /*
	    sendData = {
	    size: pageSize
	    , __chk_all: (grid.options.isCheckAll ? '1' : '0')
	    },
	    
	    */

	    //cond条件对象
	    if (typeof event === 'object' && typeof event.data === 'undefined') {
	        var tmp_conds = [];
	        if ($.isArray(event)) {
	            tmp_conds = event;
	        } else {
	            tmp_conds.push(event);
	        }
	        $.each(tmp_conds, function(i, o) {
	            for (var name in o) {
	                cond[name] = o[name];
	            }
	            grid.setCond(cond);
	        });

	    }

	    if (typeof grid.options.cond === 'object') {
	        for (var name in grid.options.cond) {

	            cond[name] = grid.options.cond[name];
	        }
	    }

	    //fields
	    //fld ="<{0} />"
	    //fields = grid._getColumnXml();
	    fields = $.myui.makeFields(grid.options.columns);

	    if (grid.isChanged() && window.confirm($.myui.lang('cfm_save'))) {
	        grid.save();
	        return false;
	    }
	    //data = $.myui.invoke(grid.options.className + ".Query", event, { myui_args: { id: grid._gridId(), fields: fields, size: grid.options.pageSize} });
	    /*data = $.myui.invoke(grid.options.className + ".Query", cond,
	    { myui_args: {
	    id: grid._gridId()
	    , type: "query"
	    , fields: fields
	    , size: grid.options.pageSize
	    , curr: 0
	    , url: grid.options.url
	    }
	    });
	    */
	    data = $.myui.invoke(grid.options.className + ".Query", [cond, fields], {
	        id: grid._gridId()
            , type: "query"
	        , encrypt: grid.options.encrypt
	        , decrypt: grid.options.decrypt
            , size: grid.options.pageSize
            , curr: 0
            , url: grid.options.url
	    });
	    if (typeof data == 'object') {
	        grid.bind(data);

	        //page info
	        pagePages = (data.info.count % pageSize) == 0 ? (data.info.count / pageSize) : Math.floor(data.info.count / pageSize) + 1;

	        //set page info of toolbar
	        pageInfo = { records: data.info.count, size: pageSize, curr: pageCurr, pages: pagePages };
	        grid._setPage(pageInfo);
	        //更新匯總值
	        if (grid.options.isCheckAll) {
	            for (var name in data.info) {
	                if (name != 'count') {
	                    grid.updateSum(name, data.info[name]);
	                }
	            }
	        }
	    } else if (typeof data == 'string' && data.indexOf('changed') > 1) {
	        var msg = $.myui.lang('ischanged', [grid.options.name]);
	        var save = $.myui.lang('btn_save');
	        var calcel = $.myui.lang('btn_nosave');
	        $.myui.win({ title: '', msg: msg, message: '', parent: grid, buttons: [{ id: 2, name: save, fn: null }, { id: 1, name: calcel, fn: null}] }, grid._autoDo);
	        ret = false;

	    } else {
	        $.myui.msg(data, 'w');
	    }

	    //默認全選時不需清除
	    if (!grid.options.isCheckAll) {
	        //clear sum info
	        grid.sum();
	    }
	    //grid.focus();
	    return ret;
	}
        /** 
        * 取得配置字段的信息，组成XML格式字符
        * @method _getColumnXml
        */
    , _getColumnXml: function() {
        var fields = "";
        $.each(this.options.columns, function(i, o) {
            //fields += "<" + o.id + "/>" + ",";
            fields += "<" + o.id;
            for (var name in o) {
                if (name != 'id' && (typeof o[name] == 'string' || typeof o[name] == 'number')) {
                    fields += " " + name + "=\"" + o[name] + "\"";
                }
            }
            fields += "/>";
        });
        return fields;
    }
        /** 
        * 自保存或放棄，在发现变更时调用
        * @method _autoDo
        * @param isSave {bool} 保存与否，true:保存，false:不保存
        * @param grid {Object} Grid对象。
        */
	, _autoDo: function(isSave, grid) {
	    if (isSave) {
	        //$.myui.request('s',{},grid.options.url);
	        grid.save();
	    } else {
	        $.myui.request('cancel', {}, grid.options.url);
	        grid.query();
	    }
	}
        /** 
        * 跳页，eg. grid.toPage('first');
        * @method toPage
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。
        */
	, toPage: function(event) {
	    var table, tr = '', td = '', record, mainId, sendData,
	        pageInfo, type, obj,
	        grid = ((typeof event != 'undefined' && typeof event.data != 'undefined' && typeof event.data.grid != 'undefined') ? event.data.grid : this),
	        el = (typeof event == 'string') ? event : event.data.obj,
	        reqType = 'page', data;

	    pageInfo = grid.options.pageInfo;
	    type = (typeof el == 'string') ? el : el.attr('id').substr(el.attr('id').lastIndexOf('-') + 1)
	    switch (type) {
	        case 'first':
	            pageInfo.curr = 1;
	            //reqType = type.substr(0, 1);
	            break;
	        case 'prev':
	            //reqType = type.substr(0, 1);
	            pageInfo.curr -= 1;
	            if (pageInfo.curr < 1) pageInfo.curr = 1;
	            break;
	        case 'next':
	            //reqType = type.substr(0, 1);
	            pageInfo.curr += 1;
	            if (pageInfo.curr > pageInfo.pages) pageInfo.curr = pageInfo.pages;
	            break;
	        case 'last':
	            //reqType = type.substr(0, 1)
	            pageInfo.curr = pageInfo.pages;
	            break;
	        case 'curr':
	            //reqType = 'data';
	            //alert(pageInfo.curr);
	            //pageInfo.curr = el.val();

	            break;
	        case 'size':
	            //alert(pageInfo.size);
	            //reqType = 'data';

	            //pageInfo.size = el.val();
	            break;

	        default:
	            break;

	    }
	    //alert(pageInfo.curr);
	    grid._setPage(pageInfo);

	    //请求资料
	    sendData = {
	        curr: pageInfo.curr
	        , size: pageInfo.size
	    };
	    //var data = $.myui.request(reqType, sendData, grid.options.url);
	    if (grid.isChanged() && window.confirm($.myui.lang('cfm_save'))) {
	        grid.save();
	        return false;
	    }
	    /*
	    data = $.myui.invoke(grid.options.className + ".Query", {},
	    { myui_args: {
	    id: grid._gridId()
	    , type: reqType
	    , size: grid.options.pageSize
	    , curr: pageInfo.curr
	    , url: grid.options.url
	    }
	    });
	    */
	    //分頁時，條件與欄位可為空
	    data = $.myui.invoke(grid.options.className + ".Query", ['', ''], {
	        id: grid._gridId()
            , type: 'page'
            , size: grid.options.pageSize
            , curr: pageInfo.curr
            , url: grid.options.url
	    });
	    grid.bind(data);
	    grid.focus();


	    //return this;
	    return true;
	}
        /** 
        * 删除一行
        * @method removeRow
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。
        */
	, removeRow: function(event) {
	    var grid = ((typeof event != 'undefined' && typeof event.data.grid != 'undefined') ? event.data.grid : this),
	    inData = {
	        size: grid.options.pageInfo.size,
	        curr: grid.options.pageInfo.curr
	    },
	    data = {},
	    rows,
	    rows_r,
	    n = -1, posPrev = -1,
	    size = parseInt(grid.options.pageInfo.size),
        curr = parseInt(grid.options.pageInfo.curr),
        records = parseInt(grid.options.pageInfo.records);

	    if (window.confirm($.myui.lang('cfm_del'))) {
	        //data = $.myui.request('r', inData, grid.options.url);
	        rows = $('#' + grid._gridId() + '-data-left tbody tr');
	        for (var m = 0; m < rows.length; m++) {
	            //alert($(rows[m]).children().first().find('input').attr('checked'));
	            if ($(rows[m]).children().first().find('input').attr('checked') == "checked") {
	                //alert(data[$(rows[m]).children().first().attr('name')]);
	                grid.setValue(data, $(rows[m]).children().first().attr('val'), "del");
	                n = rows[m].rowIndex;

	                rows_r = $('#' + grid._gridId() + '-data-right tbody tr');
	                $(rows[m]).remove();
	                $(rows_r[n]).remove();

	                records = parseInt(grid.options.pageInfo.records) - 1;
	                grid._setPage(
                    {
                        records: records
                        , pages: records % size == 0 ? records / size : Math.floor(records / size) + 1
                    });
	                grid._currRowIndex = -1;
	            }
	        } //end for
	        posPrev = n;
	        grid.skipRow(0);
	    }
	    //return this;
	}
        /** 
        * 保存资料到DB中
        * @method save
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。
        */
	, save: function(event) {
	    var grid = ((typeof event != 'undefined' && typeof event.data.grid != 'undefined') ? event.data.grid : this),
	    ret = null, fieds, data;
	    //资料有异动
	    if (grid.isChanged()) {
	        //fields = grid._getColumnXml();
	        fields = $.myui.makeFields(grid.options.columns, true);
	        /*
	        ret = $.myui.invoke(grid.options.className + ".Save", grid.CData,
	        {
	        paramtype: "xml"//以单一Xml格式传送数据：CData
	        , myui_args: {
	        id: grid._gridId()
	        , fields: fields
	        , url: grid.options.url
	        }
	        });
	        */
	        //將需保存的資料當作一個參數，用<record></record>包裝起來
	        data = $.myui.packArgs(grid.CData, 'record');
	        //alert(data);
	        ret = $.myui.invoke(grid.options.className + ".Save", [data, fields],
            {
                 id: grid._gridId()
                //, fields: fields
                , url: grid.options.url
            });

	    } else {
	        return false;
	    }
	    //6个0代表成功
	    if (ret.code == "000000") {
	        $.myui.msg(ret.msg, false, 'n');
	        //clear CData of client
	        grid.CData = [];
	    } else {
	        if (typeof ret == "string") {
	            $.myui.msg(ret, 'w');
	        } else {
	            $.myui.msg(ret.msg, 'w');
	        }
	    }
	    /*
	    if (ret == "" || ret == null) {
	    //$.myui.msg('bl0000',true, 'w');
	    $.myui.msg('bl0000', true, 'n');
	    //clear CData of client
	    grid.CData = [];
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
	    */

	}
        /** 
        * 清除资料区、或条件区资料
        * @method clear
        * @param kind {number} 0--条件；1.资料（default）
        */
	, clear: function(kind) {
	    var tp = 1
	    , title_r = '#' + this._gridId() + '-title-right', conds_r
	    , title_l = '#' + this._gridId() + '-title-left', conds_l
	    , data_r = '#' + this._gridId() + '-data-right'
	    , data_l = '#' + this._gridId() + '-data-left';

	    if (typeof kind != 'undefined') {
	        tp = kind;
	    }
	    switch (tp) {
	        case 0:
	            conds_l = $(title_l).find('input');
	            conds_r = $(title_r).find('input');
	            for (var m = 0; m < conds_r.length; m++) {
	                //readonly 栏的条件是Default的条件，不可清除
	                if (conds_r.attr('type') == 'text' && conds_r.attr('readonly') != '1') {
	                    conds_r.val('');
	                    conds_r.trigger('change');
	                }
	            }

	            break;
	        case 1:
	            $(data_l).children().remove(); //clear all child node
	            $(data_r).children().remove(); //clear all child node
	            this._addBlank();
	            break;
	        default:
	            break;
	    }

	    return this;
	}
        /** 
        * 资料是否发生改变，含增删改
        * @method isChanged
        * @return {bool} true:发生更改；false:未更改
        */
    , isChanged: function() {
        var ret = false;
        //var chg = $.myui.request('chg', {}, this.options.url);
        //this.CData.length > 0
        //alert(chg);
        ret = (this.CData.length > 0 ? true : false);
        return ret;
    }

        /** 
        * 找到某行或某些行
        * @method findRow
        * @param name {string} 栏名，若只有一个参数，则有为Key值，栏名自动以Key栏名代替；如findRow('001901');
        * @param value {string} 可选，栏值
        * @return {array} 含左右两区的资料行对象的数组
        */
	, findRow: function(name, value) {
	    var ret = null
            , cols = this.options.columns
	        , rows_l = $('#' + this._gridId() + '-data-left').find('tbody').children()
	        , rows_r = $('#' + this._gridId() + '-data-right').find('tbody').children()
            , fld = name
            , val = value;
	    switch (arguments.length) {
	        case 0: //若没有传入任何参数，则相对于currRow
	            break;
	        case 1: //
	            //loop columns
	            for (var m = 0; m < cols.length; m++) {
	                //为key栏且ch传入key value时
	                if (typeof cols[m].iskey != 'undefined' && cols[m].iskey == '1') {
	                    fld = cols[m].id;
	                    //end loop
	                    break;
	                }
	            }
	            val = name;
	            break;
	        case 2: //
	            fld = name;
	            val = value;
	            break;
	    }
	    //以Key值查找
	    var row = rows_l.find("td[val='" + val + "']");
	    if (row.length == 1) {
	        var i = row.parent().index();
	        //alert(i + val);    
	        ret = [];
	        ret.push(rows_l.eq(i));
	        ret.push(rows_r.eq(i));

	    }
	    /*
	    //
	    for (var n = 0; n < rows_l.length; n++) {
	    ret = [];
	    ret.push(rows_l.eq(n));
	    ret.push(rows_r.eq(n));
	    //alert('FLD:' + this.getValue(fld,ret)  + 'VAL:' + val);
	    if (this.getValue(fld, ret) == val) {
	    break;
	    } else {
	    ret = null;
	    }

	    }
	    */
	    //若没有找到，则返回null
	    return ret;
	}



        /** 
        * 取得选中行，包括当前行与Checked=true的行
        * @method getRows
        * @return {array} 含左右两区的资料行对象的数组，若没有选中行返回null；<br>
        * 数组元素为JSON格式,如：[{prod_id:'001',prod_name:'test Name'},{},...]
        */
	, getRows: function() {
	    var rows = []
	    , row = {}
	    , grid = this
	    , data;

	    data = $.myui.request('getrows', {}, this.options.url);

	    //若正常返回，并且有资料
	    if (typeof data == 'object' && data.xconfig.records != null) {
	        if ($.isArray(data.xconfig.records.record)) {
	            rows = data.xconfig.records.record;
	        } else {
	            rows.push(data.xconfig.records.record);
	        }
	    } else if (grid.currRow() != null) {
	        for (var m = 0; m < grid.currRow().length; m++) {
	            for (var n = 0; n < grid.currRow()[m].children().length; n++) {
	                //TD有name属性
	                //避免重复name
	                if (typeof grid.currRow()[m].children().eq(n).attr('name') != 'undefined'
	                    && typeof row[grid.currRow()[m].children().eq(n).attr('name')] == 'undefined') {
	                    row[grid.currRow()[m].children().eq(n).attr('name')] = grid.getValue(grid.currRow()[m].children().eq(n).attr('name'));
	                }
	            }
	        }
	        rows.push(row);
	    }
	    //alert(rows);
	    if (rows.length == 0) {
	        return null;
	    } else {
	        return rows;
	    }
	}
        /** 
        * 更改选择行或条件行指定栏位的值，可成批更改多个栏位值
        * @method setValue
        * @param values {Object} 『栏：值』格式的对象，如：{proj_id:'00120',proj_name:'Test 1'}
        * @param id {string} 可选，行识别ID,若没有传入，则以当前行为更新行 ;add--增 upd--改 del--删
        */
	, setValue: function(values, id, s_chgtype) {
	    var data = {}
	    , row = this.currRow()
	    , rowid, keyName, keyInfo = {}
	    , chgType = "2"//update 
	    , grid = this
	    , idx = -1;

	    if (typeof id == 'string') {
	        //alert(id);
	        row = this.findRow(id);
	    }

	    //
	    if (typeof s_chgtype == 'string') {
	        switch (s_chgtype) {
	            case "add":
	                chgType = "1";
	                break;
	            case "upd":
	                chgType = "2";
	                break;
	            case "del":
	                chgType = "3";
	                break;
	        }
	    }

	    for (var m = 0; m < row.length; m++) {
	        if (typeof $(row[m]).children().first().attr('val') != 'undefined') {
	            //data.rowid = $(row[m]).children().first().attr('val');
	            rowid = $(row[m]).children().first().attr('val');
	            keyName = $(row[m]).children().first().attr('fld');
	            keyInfo[keyName] = rowid;
	            break;
	        }
	    }
	    //没有找到待更新行，直接返回
	    if (row == null) {
	        return false;
	    };

	    idx = $.myui.inArray(grid.CData, keyInfo);
	    //not finded
	    if (idx == -1) {
	        data[keyName] = rowid;

	        data["myui_chg"] = chgType;
	    } else {//fineded

	        data = grid.CData[idx];
	        //新增未存行，若咨询删除
	        if (chgType == "3") {
	            if (data["myui_chg"] == "1") {
	                //delete item
	                data["myui_chg"] = "0";
	            }
	            if (data["myui_chg"] == "2") {
	                data["myui_chg"] = chgType; // 2 --delete
	            }
	        }
	    }
	    //刪除時只需要Key欄值無需其他欄位
	    if (chgType != "3") {
	        for (var name in values) {
	            //data[name] = (values[name] == null ? '' : $.myui.safeStr(values[name], true));
	            data[name] = (values[name] == null ? '' : values[name]);
	            //upd mark
	            //alert(data[name]);
	            //data.value = $.myui.safeStr(values[name]);

	            //更新画面上的值
	            //左右两区
	            for (var m = 0; m <= 1; m++) {
	                if (row[m].find('td[name="' + name + '"]').length == 1) {
	                    //当为编辑状态，有editor编辑控件
	                    if (row[m].find('td[name="' + name + '"]').find('#editor').length == 1) {
	                        row[m].find('td[name="' + name + '"]').find('#editor').val(data[name]);
	                    }
	                    //排除CheckBox
	                    if (row[m].find('td[name="' + name + '"]').find('span').find('input').length == 0) {
	                        row[m].find('td[name="' + name + '"]').find('span').text(data[name]);
	                    }
	                }
	            }

	        };
	    }
	    //若无此行资料异动记录
	    if (idx == -1) {
	        grid.CData.push(data);
	    }

	    //alert(this.author.length);172.18.60.77

	}
        /** 
        * 更改选择行或条件行指定栏位的值，可成批更改多个栏位值
        * @method setValue
        * @param config {Object} 一个或多个栏的样式设定对象，如：{status_name:{background-color:'red'},note:'green'}
        *
        * @param id {string} 可选，行识别ID,若没有传入，则以当前行为更新行
        */
	, setStyle: function(config, id) {
	    var cfgFields = config
	    , data = {}
	    , row = this.currRow()
	    , rowid;

	    if (typeof id == 'undefined') {
	        for (var m = 0; m < row.length; m++) {
	            if (typeof $(row[m]).children().first().attr('val') != 'undefined') {
	                data.rowid = $(row[m]).children().first().attr('val');
	                rowid = $(row[m]).children().first().attr('val');
	                break;
	            }
	        }
	    } else {
	        rowid = id;
	        row = this.findRow(rowid);
	    }
	    //没有找到待更新行，直接返回
	    if (row == null) {
	        return false;
	    };

	    for (var name in cfgFields) {
	        data.fld = name;
	        data.style = cfgFields[name];
	        //当传入为字串时，认为其为字体颜色属性
	        if (typeof data.style == 'string') {
	            data.style = { color: cfgFields[name] };
	        }
	        //左右两区
	        for (var m = 0; m <= 1; m++) {
	            if (row[m].find('td[name="' + name + '"]').length == 1) {
	                //排除CheckBox
	                if (row[m].find('td[name="' + name + '"]').find('input').length == 0) {

	                    row[m].find('td[name="' + name + '"]').find('span').css(data.style);
	                }
	            }

	        }
	    };
	}

        /** 
        * 取得指定行是否被选中。true/false
        * @method rowChecked
        * @param index {number} 当前页行数，从0起。
        *
        */
	, rowChecked: function(index) {
	    var ret = false;
	    if (typeof index == 'number') {

	        ret = this.currRow(index)[0].find('td[name="cell0"]').find('input[type="checkbox"]').attr('checked');
	    } else {
	        ret = this.currRow()[0].find('td[name="cell0"]').find('input[type="checkbox"]').attr('checked');
	    }

	    return ret;
	}
        /** 
        * 取得指定行,与currRow同义
        * @method row
        * @param index {number} 当前页行数，从0起。
        *
        */
	, row: function(index) {
	    var row = this.currRow(index)
	    , row_lr, data = {}, row_td;

	    if (row != null && $.isArray(row)) {
	        //row = rows[j];
	        for (var m = 0; m < row.length; m++) {
	            row_lr = row[m];

	            for (var n = 0; n < row_lr.children().length; n++) {
	                row_td = row_lr.children().eq(n);
	                name = row_td.attr('name');
	                //key field
	                if (name == "cell0") {
	                    name = row_td.attr('fld');
	                    value = row_td.attr('val');
	                    data[name] = value;
	                } else {
	                    //存在input 
	                    if (row_td.find('input').length == 1) {
	                        value = row_td.find('input').attr('value');
	                    } else {
	                        value = row_td.find('span').text();
	                    }
	                    data[name] = value;
	                }
	            }
	        }
	    }
	    return data;
	}

        /** 
        * 取得指定行值
        * @method getData
        * @param index {number} 当前页行数，从0起。
        *
        */
	, getData: function(index) {

	    return this.row(index);
	}
        /** 
        * 取消此方法（2011-01-16）
        * 設定當前Grid的提示信息
        * @method setMsg
        *
            
        , getMsg: function() {
        return this.msg;
        //var msg_id = this._gridId() + '-tb-top-msg';
        //return $('#'+msg_id).text();
        }*/
        //需要累計欄集合
    , _sumFields: []
        //左右：Left Right
        //,_lr:[]

        /** 
        * 設定當前Grid的提示信息
        * @method sum
        * @param fields {Array} or {Object} 待統計欄ID，可同時多個。
        * @param checkInfo {Object} 當前CheckBox狀態對象，含checkInfo.rowid:識別行ID；checkInfo.all:1--全選；0--非全選；checkInfo.value:true--選中；false--沒有選中。
        *
        */
	, sum: function(checkInfo) {
	    var fld, val = 0, val_total = 0,
	    isClear = (typeof checkInfo === 'undefined' || checkInfo == '' || checkInfo === 0),
	    isCheckAll = (typeof checkInfo != 'undefined' && typeof checkInfo.rowid === 'undefined'),
	    isCheck,
	    row_count = this.rows()
	    , row_checked
	    , ret = null
        , cols = this.options.columns
        , lr = ['right']
        , title
        , sum_id
        , title_id = this._gridId() + '-title-';

	    if (this.options.fixCols > 1) {
	        lr = ['right', 'left'];
	    }

	    //'myui-grid-gv_testreport-title-right'
	    //alert(this._sumFields.length);
	    //若

	    if (this._sumFields.length === 0) {
	        for (var m = 0; m < cols.length; m++) {
	            if (typeof cols[m].sum != 'undefined' && cols[m].sum == '1') {
	                this._sumFields.push({ id: cols[m].id, len: (typeof cols[m].len != 'undefined' && cols[m].len != '') ? cols[m].len : 2 });
	            }
	        }

	    }

	    //alert($('#' + this.element.attr("id") + '-chk').attr('status'));
	    //LEFT AND RIGHT
	    for (var m = 0; m < lr.length; m++) {
	        //EVERY SUM FIELD
	        for (var i = 0; i < this._sumFields.length; i++) {
	            val_total = 0;
	            val = 0;
	            fld = this._sumFields[i].id;

	            title = $('#' + title_id + lr[m]).find('tr.ui-widget-header>td[name="' + fld + '"]');
	            //alert('title:' + '#' + title_id + lr[m] + 'fieldname:' + fld);
	            sum_id = title_id + '-' + fld + '-sum';
	            //Clear
	            if (isClear && title.find('#' + sum_id).length == 1) {
	                title.find('#' + sum_id).text('');
	                //break;
	            }

	            //在Title中找到统计栏
	            if (!(isClear) && title.length == 1) {
	                //不存在
	                if (title.find('#' + sum_id).length == 0) {
	                    title.append('(<span id="' + sum_id + '" class="ui-state-highlight">' + val + '</span>)');
	                } else {//已经存在
	                    val_total = title.find('#' + sum_id).text();
	                    val_total = (val_total == '' || isNaN(val_total)) ? 0 : parseFloat(val_total);
	                }
	                //全选或全取消
	                if (isCheckAll) {
	                    //当页每行
	                    for (var j = 0; j < row_count; j++) {
	                        isCheck = $('#' + this._gridId() + '-data-left table>tbody').children().eq(j).find('td[name="cell0"]>span>input').attr('checked');

	                        //当本页资料区的CheckBox有部分选中时，未Checked的栏才参加累计
	                        if (checkInfo.value) {
	                            if (!isCheck) {
	                                val = this.getValue(fld, j);
	                                //为数字
	                                if (val != '' && !isNaN(val)) {
	                                    val = parseFloat(val);
	                                    val_total = val_total + val;
	                                }
	                            }
	                        } else if ((!checkInfo.value)) {
	                            if (isCheck) {
	                                val = this.getValue(fld, j);
	                                //为数字
	                                if (val != '' && !isNaN(val)) {
	                                    val = parseFloat(val);
	                                    val_total = val_total - val;
	                                }
	                            }
	                        }
	                    } //end for
	                    title.find('#' + sum_id).text($.myui.round(val_total, this._sumFields[i].len));

	                } else {//在资料区单选行
	                    val = this.getValue(fld);
	                    //alert('fieldName:' + fld + 'sum_id:' + sum_id + 'val:' + val +  '  fined:  ' + title.find('#'+sum_id).length);
	                    //为数字
	                    if (val != '' && !isNaN(val)) {
	                        val = parseFloat(val);
	                        //不存在
	                        if (title.find('#' + sum_id).length == 0) {
	                            title.append('(<span id="' + sum_id + '" class="ui-state-highlight">' + val + '</span>)');
	                        } else {//已经存在
	                            val_total = title.find('#' + sum_id).text();
	                            val_total = (val_total == '' || isNaN(val_total)) ? 0 : parseFloat(val_total);

	                            val = val_total + (checkInfo.value ? val : (val * -1));

	                            title.find('#' + sum_id).text($.myui.round(val, this._sumFields[i].len));
	                        }
	                    }
	                }
	            }
	        }
	    }
	}
        /** 
        * 更新Sum值。
        * @method changeSum
        * @param fld {String} 欄名稱
        * @param val {String} 當前值
        */
	, updateSum: function(fld, val) {
	    var lr = (this.options.fixCols > 1 ? ['right', 'left'] : ['right'])
        , title
        , title_id = this._gridId() + '-title-'
        , sum_id = title_id + '-' + fld + '-sum'
        , fldConfig = this.getColConf(fld)
        , len = (typeof fldConfig.len != 'undefined' && fldConfig.len != '') ? fldConfig.len : 2;


	    //title.find('#'+sum_id).text($.myui.round(val_total,this._sumFields[i].len)); 
	    //LEFT AND RIGHT
	    for (var m = 0; m < lr.length; m++) {
	        title = $('#' + title_id + lr[m]).find('tr.ui-widget-header>td[name="' + fld + '"]');
	        if (title.find('#' + sum_id).length == 0) {
	            title.append('(<span id="' + sum_id + '" class="ui-state-highlight">' + $.myui.round(val, len) + '</span>)');
	        } else {//已经存在
	            title.find('#' + sum_id).text($.myui.round(val, len));
	        }
	    }
	}
        /** 
        * 改变Title。
        * @method changeTitle
        * @param cfg {Array} 配置对象，格式如：
        * <pre>
        * {
        *   color_id5:false ,
        *   color_id6:false ,
        *   color_id7:false ,
        *   color_id8:false
        * }
        * </pre>
        */
	, changeTitle: function(cfg) {
	    for (var m = 0; m < this.options.columns.length; m++) {
	        for (var name in cfg) {
	            //找到栏位
	            if (this.options.columns[m].id == name) {
	                switch (typeof cfg[name]) {

	                    case 'boolean':
	                        this.options.columns[name].hidden = cfg[name];
	                        break;
	                    case 'string':
	                        this.options.columns[name].name = cfg[name]
	                        break;
	                    case 'object':
	                        if (typeof cfg[name].name == 'string') {
	                            this.options.columns[name].name = cfg[name].name;
	                        }
	                        if (typeof cfg[name].hidden == 'boolean') {
	                            this.options.columns[name].hidden = cfg[name].hidden;
	                        }
	                        break;
	                    default:
	                        break;

	                }

	            }
	        }
	    }
	}

        /**
        * 当前行序数
        * @property _currRowIndex，defalut=-1
        */
	, _currRowIndex: -1

        /**
        * 当前列序数
        * @property _currColIndex，defalut=-1
        */
	, _currColIndex: -1

        /**
        * 左区当前列序数
        * @property _currColIndexLeft，defalut=-1
        */
	, _currColIndexLeft: -1


        /**
        * 右区当前列序数
        * @property _currColIndexRight，defalut=-1
        */
	, _currColIndexRight: -1

        /** 
        * 本页的资料行数
        * @method rows
        * @return {number} 行数
        */
	, rows: function() {
	    var ret = 0, data_l = '#' + this._gridId() + '-data-left';
	    ret = $(data_l).find('tbody').children().length;
	    return ret;
	}
        /** 
        * 本页的资料行数,与rows同
        * @method pageCount
        * @return {number} 行数
        */
	, pageCount: function() {
	    return this.rows();
	}
        /** 
        * 资料栏数，含Left与Right两区的总和，与PCM设定的COL节点数一致。
        * @method cols
        * @return {number} 栏数
        */
	, cols: function() {
	    return this.options.columns.length;
	}
        /** 
        * 当前定位行(背景被改变，有Left与Right俩个)。
        * @method currRow
        * @param rowIndex {number} 指定行数，从0开始
        * @return {Array} 当前左右区的TR对象，当没有当前行被聚焦时，返回null
        */
	, currRow: function(rowIndex) {
	    var ret = [], i = 0;
	    if (typeof rowIndex == 'number') {
	        i = rowIndex;
	    } else {//undefined or not number
	        i = this._currRowIndex;
	    }
	    if (this._currRowIndex > -1) {
	        ret.push($('#' + this._gridId() + '-data-left').find('tbody').children().eq(i));
	        ret.push($('#' + this._gridId() + '-data-right').find('tbody').children().eq(i));

	    } else {
	        ret = null;
	    }
	    return ret;
	}
        /** 
        * 左区或右某一选中TD对象。
        * @method currCell
        * @return {Array} TD jquery对象，当没有时返回null
        */
	, currCell: function() {
	    var ret = null;
	    if (this.currRow() != null) {
	        if (this._currColIndexLeft > -1) {
	            ret = this.currRow()[0].children().eq(this._currColIndexLeft);
	        }
	        if (this._currColIndexRight > -1) {
	            ret = this.currRow()[1].children().eq(this._currColIndexRight);
	        }
	    }

	    return ret;
	}
        /** 
        * 选中某行（多行功能暂未提供）
        * @method selectedRows
        * @param rows {number} [1,4,5]or 1
        */
	, selectedRows: function(rows) {
	    var grid = this
	    , data_l, data_r
	    , gridId = grid._gridId();

	    //data_l = grid.element.find('#' + gridId + '-data-left');
	    data_r = grid.element.find('#' + gridId + '-data-right');

	    if ($.isArray(rows)) {

	    } else if (typeof rows == 'number') {
	        //alert('row=' + rows + ' tr length=' + data_r.find('tbody').children().length);
	        //alert(data_r.find('tbody').children().eq(rows).html());
	        data_r.find('tbody').children().eq(rows).trigger(this.options.editorTrigger);
	        //alert(data_r.find('tbody').children().eq(rows).html());
	    }
	}
        /** 
        * 取得指定栏的值
        * @method getValue
        * @param item {String or Number or Array} 可为栏识别ID，如project_id,也可为序,如5表示第5个,
        * 也可一次取多個值，如['prog_id','prog_url']，返回一個類:{prog_id:'0100',prog_url:'test.aspx'}
        * @param row {number} 指定行,若没有指定，则为当前行
        * @return {String} 值
        */
	, getValue: function(item, row) {
	    var val = {}, ret = null,
	    cell, fields = [],
	    nm, currCol,
	    rows_lr = this.currRow();

	    if (typeof row == 'number') {
	        rows_lr = this.currRow(row);
	    } else if (typeof row == 'object') {
	        rows_lr = row;
	    }


	    //$.myui.msg(this.currRow().length, 'a');
	    if ($.isArray(item)) {
	        fields = item;
	    } else {
	        fields.push(item);
	    }

	    for (var m = 0; m < rows_lr.length; m++) {
	        //循環每一欄

	        for (var n = 0; n < fields.length; n++) {
	            if (typeof fields[n] == 'number') {
	                nm = this.options.columns[fields[n]].id;
	            } else {
	                nm = fields[n];
	            }
	            cell = $(rows_lr[m]).find('[name=' + nm + ']');
	            //alert(cell.length);

	            //if (cell.length == 1 && nm != 'cell0') {
	            if (cell.length == 1) {
	                //ret = (cell.find('span').length == 0) ? cell.find('input').val() : cell.find('span').text();
	                if (cell.find('input').length == 1) {
	                    if (cell.find('input[type="text"]').length == 1) {
	                        ret = cell.find('input').val();

	                    } else if (cell.attr('val') == null && cell.find('input[type="checkbox"]').length == 1) {//data area checkbox
	                        currCol = this.getColConf(nm);
	                        if (cell.find('input').attr('checked')) {
	                            ret = currCol.checkbox.checked;
	                        } else {
	                            ret = currCol.checkbox.unchecked;

	                        }
	                    } else if (cell.attr('val') != null && cell.find('input[type="checkbox"]').length == 1) {//row head checkbox
	                        ret = cell.find('input').attr('checked');
	                    }
	                } else {
	                    ret = cell.find('span').text();
	                }

	                val[nm] = ret;
	                //break;
	            } //end if

	        } //end for n	        
	    } //end for m
	    return $.isArray(item) ? val : ret;
	}

        /** 
        * 取得指定栏的條件值（gv_testreport-cond-big_kind）
        * @method getCond
        * @param item {String or Number} 可为栏识别ID，如project_id,也可为序,如5表示第5个
        * @param row {number} 指定行,若没有指定，则为当前行
        * @return {String} 值
        */
	, getCond: function(item) {
	    var ret = null,
	    id_prefix_fld = this.element.attr("id") + '-cond-',
	    id_prefix_cond = 'myui-grid-' + this.element.attr("id") + '-title-',
	    rows_lr = ['left', 'right'];

	    //myui-grid-gv_testreport-title-right
	    if (typeof item == 'number') {
	        nm = this.options.columns[item].id;
	    } else {
	        nm = item;
	    }

	    for (var m = 0; m < rows_lr.length; m++) {
	        cell = $('#' + id_prefix_cond + rows_lr[m]).find('tr[name="cond"]').find('td[name=' + nm + ']');
	        if (cell.length == 1 && nm != 'cell0') {

	            if (cell.find('input').length == 1) {
	                if (cell.find('input[type="text"]').length == 1) {
	                    ret = cell.find('input').val();
	                } else if (cell.find('input[type="checkbox"]').length == 1) {
	                    currCol = this.getColConf(nm);
	                    if (cell.find('input').attr('checked')) {
	                        ret = currCol.checkbox.checked;
	                    } else {
	                        ret = currCol.checkbox.unchecked;

	                    }
	                }
	            } else {
	                ret = cell.find('span').text();
	            }
	            break;
	        }
	    }
	    return ret;
	}

        /** 
        * 导出Grid的资料成EXCEL，含所有页次的资料
        * @method exportData
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。
        * @return {String} 值
        */
	, exportData: function(event) {
	    //alert(grid.options.url);
	    var grid = ((typeof event != 'undefined' && typeof event.data.grid != 'undefined') ? event.data.grid : this);
	    //data = $.myui.request('print', {}, grid.options.url);

	    if (grid.isChanged() && window.confirm($.myui.lang('cfm_save'))) {
	        grid.save();
	        return false;
	    }
	    //fields = grid._getColumnXml();
	    fields = $.myui.makeFields(grid.options.columns);
	    data = $.myui.invoke(grid.options.className + ".Query", {},
            { myui_args: {
                id: grid._gridId()
                , fields: fields
                , type: "export"
                , url: grid.options.url
            }
            });

	    //alert($.myui.getPath()+data);
	    window.open("/" + $.myui.getPath() + data);
	}

        /** 
        * 设定工具栏
        * @method setToolbar
        * @param option {Object} 配置参数对象。 
        * <pre>
        * [sample code]        
        *   gd_akd.grid('setToolbar', { id: 'testwingrid', name: 'open...', fn: openWin, data: { grid: gd_akd},isAppend:true});
        *   ...
        *        function openWin(event) {
        *            if (gd_akd.grid('currRow') != null) {
        *                    $.myui.wingrid({
        *                       id: 'allkindlist'
        *                        , parent: event.data.grid
        *                        , append: true
        *                        , single: false
        *                        , fields: { note: 'note', allkind_seq: 'allkind_seq' }
        *                        , url: 'allkind.aspx'
        *                        , width: 700
        *                        , cond: { allkind_pid: '0' }
        *                        , query: false
        *                }, applyData);
        *            } else {
        *                $.myui.msg('noselected',true,'w');
        *            }
        *            //alert(ret);
        *        }
        * </pre>
        */
    , setToolbar: function(option) {
        var el
        , toolbars = this._getToolbar()
        , evenName = 'click'
	    , item
        , itemId;

        if (option.evenName) {
            evenName = option.evenName;
        }
        //可能有俩个Toolbar
        for (var i = 0; i < toolbars.length; i++) {
            itemId = this._gridId() + '-tb-' + toolbars[i] + '-' + option.id;
            el = $('#' + itemId);
            //alert(itemId);

            if (el.length > 0) {
                if (option.name) {
                    el.button('option', 'label', option.name);
                }
                if (typeof option.fn === 'function') {
                    //default覆盖原有事件,设定isAppend=true，重复叠加事件
                    if ((typeof option.isAppend == 'undefined') || (!option.isAppend)) {
                        el.unbind(evenName);
                    }
                    el.bind(evenName, option.data, option.fn);
                }
            } else {//Toobar中不存在该Item，则添加
                item = '<button id="' + itemId + '">' + option.name + '</button>';
                $('#' + this._gridId() + '-tb-' + toolbars[i] + '-c').append(item);
                el = $('#' + itemId).button();
                if (typeof option.fn === 'function') {
                    //default覆盖原有事件,设定isAppend=true，重复叠加事件
                    if ((typeof option.isAppend == 'undefined') || (!option.isAppend)) {
                        el.unbind(evenName);
                    }
                    el.bind(evenName, option.data, option.fn);
                } //end if
            } //end if
        } //end for
    } //end func

        /** 
        * 更新资料到本Grid对象，可能是来自WinGrid弹出资料选取框
        * @method applyFrom
        * @param fromRows {Object} 多行资料对象。[{proj_id:'a001',qty:100},{proj_id:'a002',qty:201}]
        * @param selfRow {Object} Grid当前行资料包对象，eg.{proj_id:'project_id',qty:'qty'}
        * @param append {bool} true-添加，；false-更新当前行
        */
    , applyFrom: function(fromRows, selfRow, append) {
        var ret = {}
        , rows = fromRows
        , cols, isAppend = false;


        if (typeof append != 'undefined') {
            isAppend = append;
        }
        //rows
        for (var m = 0; m < rows.length; m++) {
            cols = rows[m];
            for (var name in cols) {
                //不存在,避免重复;匹配传入的selfRow
                if (typeof ret[name] == 'undefined' && typeof selfRow[name] == 'string' && cols[name] != null) {
                    //赋值
                    ret[selfRow[name]] = cols[name];
                }
            }
            if (isAppend) {
                this.addRow({ values: ret });
            } else {
                if (!$.isEmptyObject(ret)) {
                    this.setValue(ret);
                }
            }
            //clear
            ret = {};
        }
    }
        /** 
        * 判斷指定資料是否已經存在，可多欄資料一起判斷，如姓名為“rayd",群組ID為：“001”
        * @method isExist
        * @param argData {Object} 多行资料对象。{account_name:'rayd',role_id:'001'}
        * @param argIsAll {bool} true-比較全部資料，需要到後台比對；false-只比較本頁資料，不需到後端(Default)
        */
    , isExist: function(argData, argIsAll) {
        var isAll = false
        , ret = false
        data;

        if (typeof argIsAll == 'bool') {

            isAll = argIsAll;
        }
        //
        for (var name in argData) {

        }

    }
        /**
        * Grid的當前資料集合，就全部資料而言，僅包含當前頁資料。
        * @property records {Array}
        */
    , records: []
        /** 
        * 效验输入资料
        * @method _checkInput
        * @param nm {String} 栏名
        * @param val {String} 栏值
        * @return {bool} true:效验通过；false:效验不成功
        */
    , _checkInput: function(nm, val) {
        var ret = false,
        conf,
        expNum = /^[0-9.+-]*$/, //数字判断，含"+-."
        expStr = /^[a-zA-Z]*$/, //字母判断
        expDate = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/,     //日期：2010-01-31
        max = '', min = '',
        regExp;
        //依栏名称取得其PCM设定Config
        for (var m = 0; m < this.options.columns.length; m++) {
            if (this.options.columns[m].id == nm) {
                conf = this.options.columns[m];
                break;
            }
        }
        //在PCM中设定的regExp校验表达式
        if (typeof conf.exp != 'undefined') {
            regExp = new RegExp(conf.exp, "i");
            if (!(regExp.test(val))) {
                $.myui.msg('data_invald', true, null, 'w');
                return false;
            } else {
                ret = true; //继续检查
            }
        }
        if (typeof conf.max != 'undefined') {
            max = conf.max;
        } else {
            max = conf.size;
        }
        //必输栏
        if (typeof conf.min != 'undefined') {
            min = conf.min;
        } else {
            min = '0';
        }
        //^.{m,n}$
        expStr = '^.{' + min + ',' + max + '}$';
        regExp = new RegExp(expStr, "i");
        if (!(regExp.test(val))) {

            $.myui.msg('noempty', true, [min, max], 'w');

            return false;
        } else {
            ret = true;  //继续检查
        }

        if (typeof conf.type != 'undefined' && conf.type == 'number') {

            if (!(expNum.test(val))) {

                $.myui.msg('mustnumber', true, null, 'w');
                return false;
            } else {
                ret = true;  //继续检查
            }
        }
        //日期格式检查
        if (typeof conf.type != 'undefined' && conf.type == 'date') {

            if (!(expDate.test(val))) {
                $.myui.msg('mustdate', true, null, 'w');
                return false;
            } else {
                ret = true;  //继续检查
            }
        }
        //alert(ret);
        return ret;
    }
        /** 
        * 绑定资料到grid对象
        * <pre>
        * [SampleCode:自定義資料banding]
        * ...
        * var data=$.parseJSON('{ "xconfig": {"records": {"record": {"style":"color:red","app_id": "287dffc3-adbf-442d-a465-4af1216ec501", "big_date": "2010-10-08", "big_end": null, "big_end_pre": {"style": "background-color:red", "value": "2010-10-11" }, "big_id": "a8258121-7757-4381-bae2-138ab268e8d3", "big_kind": "BUG", "big_start": null, "big_start_pre": null, "big_title": "C", "big_who_apply": "Lucky", "big_who_incharger": "向澤安", "file_id": null, "ischanged": "0", "note": null, "prog_id": "08201b46-e5ba-4add-8c8e-c3323e1aee8c", "prog_name": "權限分配" } } },"info":{"count":"1"}}');
        * gv_testreport.grid('bind',data);
        *
        * [SampleCode:自定義資料binding]
        * ...
        * var data=$.parseJSON('{ "xconfig": {"records": {"record": {"style":"color:red","app_id": "287dffc3-adbf-442d-a465-4af1216ec501", "big_date": "2010-10-08", "big_end": null, "big_end_pre": {"style": "background-color:red", "value": "2010-10-11" }, "big_id": "a8258121-7757-4381-bae2-138ab268e8d3", "big_kind": "BUG", "big_start": null, "big_start_pre": null, "big_title": "C", "big_who_apply": "Lucky", "big_who_incharger": "向澤安", "file_id": null, "ischanged": "0", "note": null, "prog_id": "08201b46-e5ba-4add-8c8e-c3323e1aee8c", "prog_name": "權限分配" } } },"info":{"count":"1"}}');
        * gv_testreport.grid('bind',data);//注意這段："big_end_pre": {"style": "background-color:red", "value": "2010-10-11" }
        * ...
        * </pre>
        * @method bind
        * @param data {JSON} 资料
        * @param argIsAppend {bool} true：追加；false:清空后重新绑定
        */
    , bind: function(data, argIsAppend) {
        var table, tr_l = '', tr_r = '', td = '', td_l = '', td_r = '', tdChk = '', record, mainId, sendData,
	        pageInfo,
	        pageCurr = 1, //当前页次
	        pageSize = this.options.config.pagesize, //每页笔数
	        pageRecords = 0, //总行数
	        pagePages = 0, //总页数
	        w = '100px',
	        records = [],
	        isHidden = false, display,
	        grid = this,
	        isAppend = false,
	        objTD,
	        start = 0, end = 0,
	        data_r = '#' + this._gridId() + '-data-right',
	        data_l = '#' + this._gridId() + '-data-left',
	        isAllChecked = true, checked = '',
	        align = 'left',
	        val = '',
	        mk_new_row = '0',
            rows_l,
            rows_r,
            h_lr,
            css = '', tdStyle = '', trStyle = '', className = '',
            read = '0',
            groupby = '', pid = '0', prow_l, prow_r, showRow = ''
        emptykey = "0";



        var t1 = new Date();
        if (typeof argIsAppend != 'undefined') {
            isAppend = argIsAppend;
        }
        mainId = this._gridId() + '-body';

        //$.myui.msg(data.xconfig.records != null);
        if (isAppend) {
            mk_new_row = '1';
        } else {
            grid._currRowIndex = -1;
            this.clear();
        }

        if (data && data.xconfig && data.xconfig.records) {
            //not is array
            if (typeof data.xconfig.records.record.length == 'undefined') {
                records.push(data.xconfig.records.record);
            } else {//is array
                records = data.xconfig.records.record;
            }
            this.records = records;

            //若有資料，且有空行，則移除空行（blank row）
            if (records.length > 0) {
                if ($('#' + this._gridId() + '-blank-l').length == 1) {
                    $('#' + this._gridId() + '-blank-l').remove();
                }
                if ($('#' + this._gridId() + '-blank-r').length == 1) {
                    $('#' + this._gridId() + '-blank-r').remove();
                }
            }
            //loop for ervery row
            for (var m = 0; m < records.length; m++) {
                record = records[m];
                if (typeof record['style'] != 'undefined') {
                    trStyle = record['style'];
                } else {
                    trStyle = '';
                }
                //every col
                for (var i = 0; i < this.options.columns.length; i++) {
                    isHidden = this._isHidden(this.options.columns[i]);
                    if (isHidden) {
                        display = 'none';
                    } else {
                        display = '';
                    }
                    if (typeof this.options.columns[i].size != 'undefined') {
                        w = this.options.columns[i].size;
                        w = ((w + '').indexOf('px') < 0) ? (w + 'px') : w;

                    }
                    if (typeof this.options.columns[i].type != 'undefined' && this.options.columns[i].type == 'number') {
                        align = 'right';
                    } else {
                        align = 'left';
                    }
                    //readonly of options
                    if ((typeof this.options.columns[i].readonly != 'undefined' && this.options.columns[i].readonly == '1')
                    //|| (typeof this.options.config.readonly != 'undefined' && this.options.config.readonly == '1')
                        || (this.options.readonly)
                        ) {
                        //新添加的行设定为Readonly，exception的除外
                        if (mk_new_row == '1') {
                            if (typeof this.options.columns[i].exception != 'undefined' && this.options.columns[i].exception == '1') {
                                //alert(this.options.columns[i].exception == '1');
                                read = '0';
                            } else {
                                read = '1';
                            }
                        } else {
                            read = '1';
                        }
                    } else {
                        read = (typeof this.options.columns[i].readonly != 'undefined' && this.options.columns[i].readonly == '1') ? "1" : "0";
                    }
                    /*
                    //group
                    if (typeof this.options.columns[i].group != 'undefined') {
                    groupby = this.options.columns[i].group;
                    //eg. record['allkind_pid']
                    pid = record[groupby];

                        //td
                    var prow = $(data_l).find('tbody>tr>td[val="' + pid + '"]');
                    //找到父行，ID 
                    if (prow.length == 1) {
                    //td->tr
                    prow_l = prow.parent();
                    showRow = 'none';
                    }
                    }
                    */
                    //val = $.myui.safeStr((record[this.options.columns[i].id] == null ? '' : record[this.options.columns[i].id]));
                    if (record[this.options.columns[i].id] == null) {
                        if (typeof this.options.columns[i].iskey != 'undefined' && this.options.columns[i].iskey == '1' || this.options.columns[i].iskey == 1) {
                            val = $.myui.guid();
                            record[this.options.columns[i].id] = val;
                            emptykey = "1";
                        } else {
                            emptykey = "0";
                            val = '';
                        }
                        //} else if (typeof record[this.options.columns[i].id] == 'object') {//當資料發送改變時，會有ischanged屬性。在Sort時可能會出現此狀況
                        //    val = $.myui.safeStr(record[this.options.columns[i].id].value);
                        //    tdStyle = (typeof record[this.options.columns[i].id].style != 'undefined' ? record[this.options.columns[i].id].style : '');
                    } else if (typeof record[this.options.columns[i].id] == 'string') {
                        if(typeof this.options.columns[i].encrypt!='undefined' && this.options.columns[i].encrypt){
                            val = $.myui.decrypt(record[this.options.columns[i].id], this.options.columns[i].id);
                        }else{
                            val = $.myui.safeStr(record[this.options.columns[i].id], true);
                        }
                        //val = record[this.options.columns[i].id];
                    }
                    //checkbox
                    if (typeof this.options.columns[i].checkbox != 'undefined') {
                        align = 'center';
                        if (val == this.options.columns[i].checkbox) {
                            /*mark rayd 2012-11-24
                            if (typeof this.options.columns[i].checkbox.checked.css != 'undefined') {
                            css = this.options.columns[i].checkbox.checked.css;
                            } else {
                            css = ''
                            };
                            */
                            checked = 'checked';
                        } else {
                            /*
                            if (typeof this.options.columns[i].checkbox.unchecked.css != 'undefined') {
                            css = this.options.columns[i].checkbox.unchecked.css;
                            } else {
                            css = ''
                            };
                            */
                            checked = '';
                        }
                        val = '<input type="checkbox" ' + checked + '/>';
                    }
                    //file
                    if (typeof this.options.columns[i].file != 'undefined') {
                        val = '<a href="' + record[this.options.columns[i].id] + '" target ="_blank"><span class="ui-icon ui-icon-circle-arrow-s"></span></a>';
                    }
                    //format 格式函数
                    if (typeof this.options.columns[i].format == 'function') {

                        val = this.options.columns[i].format.call(grid, record);
                    }

                    //                    if (typeof this.options.columns[i].textarea != 'undefined') {
                    //                        val = '<textarea name = "global_textarea">'+record[this.options.columns[i].id]+'</textarea>';
                    //                    }
                    //        //textarea
                    //        $(data_r).find('td[name="global_textarea"]').resizable({
                    //		    handles: "se"
                    //	    });
                    /*
                    用数组并不比直接用子串相加性能更佳
                    var tableBuffer = new StringBuffer(); 
                    tableBuffer.append('<td name="');
                    tableBuffer.append(this.options.columns[i].id);
                    tableBuffer.append('" readonly="');
                    tableBuffer.append(read);
                    tableBuffer.append('" id="');
                    tableBuffer.append(this._gridId());
                    tableBuffer.append('-');
                    tableBuffer.append(this.options.columns[i].id);
                    tableBuffer.append('" style="width:');
                    tableBuffer.append(w);
                    tableBuffer.append(';display:');
                    tableBuffer.append(display);
                    tableBuffer.append(';text-align:');
                    tableBuffer.append(align);
                    tableBuffer.append(';');
                    tableBuffer.append(tdStyle);
                    tableBuffer.append('"  ><span name="val">');
                    tableBuffer.append(val);
                    tableBuffer.append('</span></td>');
                    */
                    td = '<td name="' + this.options.columns[i].id + '" ro="' + read + '" id="' + this._gridId() + '-' + m + '-' + this.options.columns[i].id + '" style="width:' + w + ';display:' + display + ';text-align:' + align + ';' + tdStyle + '"  ><span name="val">' + val + '</span></td>';
                    tdStyle = '';

                    //区分左边固定栏与右边不固定栏两部份，最少有一个固定栏--CHECKBOX
                    if (i >= this.options.fixCols) {//data right
                        td_r += td;
                        //td_r += tableBuffer.toString();

                    } else {//data left
                        if (typeof this.options.columns[i].iskey != 'undefined' && (this.options.columns[i].iskey == '1' || this.options.columns[i].iskey == 1)) {
                            //没有Key值时，自动赋值
                            if (val == "") {
                                val = $.myui.guid();
                                record[this.options.columns[i].id] = val;
                                //val = keyValue;
                            }
                            tdChk = '<td style="width:' + checkWidth + 'px" name="cell0"'; //'><span><input type="checkbox"/></span></td>';
                            tdChk += ' fld="' + this.options.columns[i].id + '" val="' + val + '"';
                            if (typeof record['checked'] != 'undefined' && record['checked'] == '1') {
                                //tdChk += '><span><input type="checkbox" checked /></span></td>';
                                checked = 'checked';
                            } else {
                                //tdChk += '><span><input type="checkbox"/></span></td>';
                                checked = '';
                                isAllChecked = false;
                            }
                            //tdChk += '><span><input type="checkbox" ' + checked + ' ' + (this.options.single ? 'disabled' : '') + ' style="width:20px;height;20px"/></span></td><td><span class="ui-icon ui-icon-flag"></span></td>';
                            //2012-11-12 不要CheckBox,顯示行號，多選用其他方法
                            tdChk += '><span><input type="checkbox" ' + checked + ' ' + (this.options.single ? 'disabled' : '') + ' style="width:20px;height;20px"/></span></td>';
                            //tdChk += '><span class="ui-icon ui-icon-grip-dotted-vertical">' + (m + 1) + '</span></td>';


                        }
                        td_l += td;
                        //td_l += tableBuffer.toString();
                    }

                } //end td loop

                td_l = tdChk + td_l;
                if (m % 2 == 0) {
                    className = 'ui-selectee';
                } else {
                    className = '';
                }
                //var tr_r = new StringBuffer();
                //var tr_l = new StringBuffer();
                //tr_r.append('<tr pid="' + pid + '" new="' + mk_new_row + '" class="ui-widget-content '+className+'" style="height:21px;display:' + showRow + ';'+trStyle+'">' + td_r + '</tr>');
                //tr_l.append('<tr pid="' + pid + '"  new="' + mk_new_row + '"  class="ui-widget-content '+className+'" style="height:21px;display:' + showRow + ';'+trStyle+'">' + td_l + '</tr>');

                tr_r = '<tr pid="' + pid + '" new="' + mk_new_row + '" class="ui-widget-content ' + className + '" style="height:22px;display:' + showRow + ';' + trStyle + '" emptykey="' + emptykey + '">' + td_r + '</tr>';
                tr_l = '<tr pid="' + pid + '"  new="' + mk_new_row + '"  class="ui-widget-content ' + className + '" style="height:22px;display:' + showRow + ';' + trStyle + '" emptykey="' + emptykey + '">' + td_l + '</tr>';
                //alert(tr_r);
                //alert(tr_l);
                //若有上层：需添加到其父层下。达到分组的目的
                if (prow_l != null) {
                    //根据Left row 的位置找到Right Row
                    prow_r = $(data_r).find('tbody').children().eq(prow_l.index());

                    if (prow_l.children().eq(1).find('span.ui-icon').length == 0) {
                        $('<span class="ui-icon ' + open_class + '" style="float: left; margin: 0.4em 0.2em 0 0; cursor: pointer;"></span>')
                        .appendTo(prow_l.children().eq(1))
                        .bind('click', { prow_l: prow_l, prow_r: prow_r, pid: pid }, function(event) {
                            event.data.prow_l.parent().find('tr[pid="' + event.data.pid + '"]').toggle();
                            event.data.prow_r.parent().find('tr[pid="' + event.data.pid + '"]').toggle();
                            //切换图标
                            if ($(this).hasClass(close_class)) {
                                $(this).removeClass(close_class);
                                $(this).addClass(open_class);
                            } else {
                                $(this).removeClass(open_class);
                                $(this).addClass(close_class);

                            }
                        });
                    }

                    //
                    prow_l.after(tr_l);
                    prow_r.after(tr_r);
                    //prow_l.after(tr_l.toString());
                    //prow_r.after(tr_r.toString());

                } else {

                    if (grid.options.appendLast || $(data_l).find('tbody').children().length == 0) {
                        //appdnd tr to last
                        $(data_l).find('tbody').append(tr_l);
                        $(data_r).find('tbody').append(tr_r);

                        //$(data_l).find('tbody').append(tr_l.toString());
                        //$(data_r).find('tbody').append(tr_r.toString());


                    } else {
                        $(data_l).find('tbody').children().eq(0).before(tr_l);
                        $(data_r).find('tbody').children().eq(0).before(tr_r);

                        //$(data_l).find('tbody').children().eq(0).before(tr_l.toString());
                        //$(data_r).find('tbody').children().eq(0).before(tr_r.toString());

                    }
                }


                //init var
                tr_l = tr_r = '';
                td_l = '';
                td_r = '';
                tdChk = '';
                prow_l = null;
                prow_r = null;
                showRow = '';

            } //end tr loop
            //here 108'
            //更改Title栏的CheckBox状态。
            $('#' + this.element.attr('id') + '-chk').attr('checked', isAllChecked);
            //调整资料行高于列宽。
            this._adjustRowCol();
            //here 185'


            //bind event to every tr :left and right
            //改用bind,若用live来附加事件会出现在翻页后重复附加，翻一次多附加一次。
            //left
            $(data_l + ' tbody tr[new="' + mk_new_row + '"]').unbind('click');
            $(data_l + ' tbody tr[new="' + mk_new_row + '"]')
            .bind('click', function(event) {
                //alert('$(this).index()=' + $(this).index() + 'grid._currRowIndex=' + grid._currRowIndex);
                //过滤重复Click同一行
                if ($(this).index() != grid._currRowIndex) {
                    //清除上一被选行 ui-state-hover
                    //ui-state-default ui-state-highlight
                    $(data_r + ' tbody').children().eq(grid._currRowIndex).removeClass('ui-state-default ui-state-highlight');
                    $(data_l + ' tbody').children().eq(grid._currRowIndex).removeClass('ui-state-default ui-state-highlight');
                    //改变当前行Class
                    $(this).addClass('ui-state-default ui-state-highlight');
                    $(data_r + ' tbody').children().eq($(this).index()).addClass('ui-state-default ui-state-highlight');
                    grid._currRowIndex = $(this).index();
                    grid._rowselected(event, { index: grid._currRowIndex, rowdata: grid.row(), gridid: grid.ID });
                    /*
                    setTimeout(function() {
                    grid._rowselected(event);
                    }, 300);
                    */
                }

            });
            //right
            $(data_r + ' tbody tr[new="' + mk_new_row + '"]').unbind('click');
            $(data_r + ' tbody tr[new="' + mk_new_row + '"]').
            bind('click', function(event) {
                //alert('$(this).index()=' + $(this).index() + 'grid._currRowIndex=' + grid._currRowIndex);
                //过滤重复Click同一行
                if ($(this).index() != grid._currRowIndex) {
                    //清除上一被选行Class
                    $(data_r + ' tbody').children().eq(grid._currRowIndex).removeClass('ui-state-default ui-state-highlight');
                    $(data_l + ' tbody').children().eq(grid._currRowIndex).removeClass('ui-state-default ui-state-highlight');
                    //改变当前行Class
                    $(this).addClass('ui-state-default ui-state-highlight');
                    $(data_l + ' tbody').children().eq($(this).index()).addClass('ui-state-default ui-state-highlight');
                    grid._currRowIndex = $(this).index();
                    grid._rowselected(event, { index: grid._currRowIndex, rowdata: grid.row(), gridid: grid.ID });
                    /*
                    setTimeout(function() {
                    grid._rowselected(event);
                    }, 300);
                    */
                }
            });
            //here 191'

            //bind event to every td，CheckBox除外
            //left data
            objTD = $(data_l + ' tbody tr[new="' + mk_new_row + '"]').find('td[ro="0"]');
            objTD.unbind(grid.options.editorTrigger);
            objTD.bind(grid.options.editorTrigger, { grid: grid, lr: 'left' }, this._editor);

            //left data
            //objTD = $(data_r + ' tbody td[name!="cell0"]');
            //alert($(data_r + ' tbody tr[new="0"]').find('td[readonly="0"]').length);
            objTD = $(data_r + ' tbody tr[new="' + mk_new_row + '"]').find('td[ro="0"]');
            //alert(objTD.length);
            objTD.unbind(grid.options.editorTrigger);
            objTD.bind(grid.options.editorTrigger, { grid: grid, lr: 'right' }, this._editor);
            //here 265'
            //$.myui.taken(this._gridId() + '，bind data ', t1);

            //所有资料区的CheckBox，不含Title条件
            var firstTD = $(data_l + ' tbody tr[new="' + mk_new_row + '"]').find('td[name="cell0"]');
            //alert(firstTD.length);
            firstTD.unbind('change');
            firstTD.bind('change', { grid: grid, kind: 'check' }, this._selectRow);
            //firstTD.unbind('click');
            //firstTD.bind('click', { grid: grid, kind: 'check' }, this._selectRow);

        }

        if (isAppend) {
            if (grid.options.appendLast) {
                //选取最后一行
                if (grid.options.skip) this.skipRow(9, false);
                //Scroll跳到最後 
                $(data_l).find('table>tbody>tr').last().find('td').first().find('input').focus();

            } else {
                //选取第一行

                $(data_l).find('table>tbody>tr').first().find('td').first().find('input').focus();
                //解決當在最前面添加一行時，選中行不能正確定位問題。
                if (grid.rows() > 1) {
                    grid._currRowIndex += 1;
                }
                if (grid.options.skip) this.skipRow(0, false);
            }
        } else {
            if (grid.options.autoSelected) {
                //选取第一行
                this.skipRow(0, false);
            }
        }

        //here 286'
        $.myui.taken(this._gridId() + '，bind data ', t1);


    }
        /** 
        * row select event,只有在改变当前行时才触发，同一行重复Click不会触发该事件
        *  此事件是注册在data_right/data_left的TR中，为了避免同一行Click不同栏时重复触发。
        *  调用时，注意前面加'grid'并全部用小写，eg. grid.bind('gridrowselected',{},function(event,eventdata){alert(eventdata.rowdata.spec_id + eventdata.gridid + event.index);});
        * @event rowselected
        * @param event {Object} 父事件的参数
        * @param data {Object} 传出的数据参数：rowdata ;gridid;index
        */
    , _rowselected: function(event, data) {
        //alert("rowselected trigger");
        this._trigger("rowselected", event, data);
    }
        /** 
        * 调整行高、列宽
        * @method _adjustRowCol
        */
    , _adjustRowCol: function() {
        var rows_l, rows_r
        , cols_data_l, cols_data_r, cols_title_l, cols_title_r
        , cols_title_cond_r
        , data_r = '#' + this._gridId() + '-data-right'
	    , data_l = '#' + this._gridId() + '-data-left'
	    , title_l = '#' + this._gridId() + '-title-left'
	    , title_r = '#' + this._gridId() + '-title-right';

        //调整Title行高
        this._adjustTitle();

        //调整TR的高度,以大者作为左右两行的高度。
        rows_l = $(data_l + ' tbody tr');
        rows_r = $(data_r + ' tbody tr');
        h_lr = 0;
        for (var m = 0; m < rows_l.length; m++) {
            //alert('left outerHeight=' + $(rows_l[m]).outerHeight() + ' right outerHeight=' + $(rows_r[m]).outerHeight() + 'left innerHeight=' + $(rows_l[m]).innerHeight() + ' right innerHeight=' + $(rows_r[m]).innerHeight() + 'left height=' + $(rows_l[m]).height() + ' right height=' + $(rows_r[m]).height());
            if (rows_l.eq(m).height() < rows_r.eq(m).height()) {
                h_lr = rows_r.eq(m).height();
                //alert($(rows_l[m]).length);
                rows_l.eq(m).css({ height: h_lr + 'px' });
                //alert($(rows_l[m]).height());
            } else if (rows_l.eq(m).height() > rows_r.eq(m).height()) {
                h_lr = rows_l.eq(m).height();
                rows_r.eq(m).css({ height: h_lr + 'px' });
            }

        }
        /*加了Table的 layout:fix樣式後，寬度已經固定，不用調整。
        //调整每一栏宽，以大者为宽
        cols_data_r = $(data_r + '>table>tbody>tr').first().children();
        cols_title_r = $(title_r + '>table>tbody>tr').first().children();
        cols_title_cond_r = $(title_r + '>table>tbody>tr').last().children();
        for (var n = 0; n < cols_data_r.length; n++) {
        //alert('name:' + $(cols_title_r[n]).attr('name') + ' <br>title Width:' + $(cols_title_r[n]).width() + ' data width:' + $(cols_data_r[n]).width());
        //不为Hidden栏，且当资料栏因资料装载后栏宽变大时
        if (cols_title_r.eq(n).find(':hidden').length == 0 ) {
        //alert('name:' + cols_title_r.eq(n).attr('name') + ' <br>title Width:' + cols_title_r.eq(n).width() + ' data width:' + cols_data_r.eq(n).width());
        if(cols_title_r.eq(n).width() < cols_data_r.eq(n).width()){
        //title
        cols_title_r.eq(n).css({ width: cols_data_r.eq(n).width() + 'px' });
        //cond
        cols_title_cond_r.eq(n).css({ width: cols_data_r.eq(n).width() + 'px' });
        }
                 
        //調整資料欄寬，效果待確定
        //else if(cols_title_r.eq(n).width() > cols_data_r.eq(n).width()){
        //   //alert(cols_data_r.eq(n).attr('name'));
        //   $(data_r + '>table>tbody').find('td[name="'+cols_data_r.eq(n).attr('name')+'"]').css({width:cols_title_r.eq(n).width() + 'px'});
        //}
                 
        //alert('name:' + $(cols_title_r[n]).attr('name') + ' <br>title Width:' + $(cols_title_r[n]).width() + ' data width:' + $(cols_data_r[n]).width());
                
        }
        }
        */

    }
        /** 
        * 调整Title高度宽度
        * @method _adjustTitle
        */
    , _adjustTitle: function() {
        var title_l = '#' + this._gridId() + '-title-left'
	    , title_r = '#' + this._gridId() + '-title-right'
        , rows_l = $(title_l + ' tbody tr')
        , rows_r = $(title_r + ' tbody tr')
        , h_lr = 0;

        //调整Title行高
        for (var m = 0; m < rows_l.length; m++) {
            //alert('left height=' + $(rows_l[m]).height() + ' right height=' + $(rows_r[m]).height());
            if ($(rows_l[m]).height() < $(rows_r[m]).height()) {
                h_lr = $(rows_r[m]).height();
                //alert($(rows_l[m]).length);
                $(rows_l[m]).css({ height: h_lr + 'px' });
                //alert($(rows_l[m]).height());
            } else if ($(rows_l[m]).height() > $(rows_r[m]).height()) {
                h_lr = $(rows_l[m]).height();
                $(rows_r[m]).css({ height: h_lr + 'px' });
            }

        }

    }
        /** 
        * checkbox选取一行
        * @method _selectRow
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。
        */
    , _selectRow: function(event) {

        var inData = {
            value: (typeof $(this).attr('checked') != 'undefined') ? $(this).attr('checked') : $(this).find('input').attr('checked')
            , rowid: $(this).attr('val')
        },
        row_count = event.data.grid.rows(),
        data_id = $('#' + event.data.grid._gridId() + '-data-left'),
        checked_count = 0;

        if (typeof ($(this).find('input').attr('checked')) != "undefined") {
            $(this).find('input').attr('checked', true);
        } else {
            $(this).find('input').attr('checked', false);
        }
        checked_count = data_id.find('table>tbody td[name="cell0"]>span>input[type="checkbox"][checked]').length;
        //alert(checked_count);
        /*

        
        
        var icon = $(this).find("span");
        if (icon.hasClass("ui-icon-check")) {
        icon.removeClass("ui-icon-check");
        icon.addClass("ui-icon-grip-dotted-vertical");
        //icon.addClass("ui-icon-radio-off");
        } else {
        icon.removeClass("ui-icon-grip-dotted-vertical");
        //icon.removeClass("ui-icon-radio-off");
        icon.addClass("ui-icon-check");

        }
        */


        //自动影响CheckBox ALL的值
        if (checked_count == row_count) {//全选
            $('#' + event.data.grid.element.attr("id") + '-chk').attr('checked', true);
        } else if (checked_count == 0) {//全部为选
            //eg.gv_testreport-chk
            $('#' + event.data.grid.element.attr("id") + '-chk').attr('checked', false);
        } else if (checked_count > 0) {//部分选中
            $('#' + event.data.grid.element.attr("id") + '-chk').attr('checked', false);
        }

        //$.myui.request('chk', inData, event.data.grid.options.url);
        //
        event.data.grid.sum(inData);
        //trigger event
        event.data.grid._rowcheck(event, { all: 0, rowid: inData.rowid, value: inData.value });
    }

        /** 
        * row select event,在改变当前行的CheckBox时触发，包括Checked和UnChecked
        *  
        *  调用时，注意前面加'grid'并全部用小写，eg. grid.bind('gridrowrowcheck',{},function(event){alert(event.data.all);alert(event.data.rowid);});
        * @event rowcheck
        * @param event {Object} 父事件的参数
        * @param data {Object} 參數，rowid:行識別ID；all:為全選;value:當前CheckBox的值； 如：『all:0,rowid:'asdefd-e99a73s-099as8e-0ad9e',value:true』
        */
    , _rowcheck: function(event, data) {

        this._trigger("rowcheck", event, data);
    }

        /** 
        * 选中本页所有行
        * @method _checkAll
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。
        */
    , _checkAll: function(event) {
        var inData = {
            value: (typeof $(this).attr('checked') != 'undefined') ? $(this).attr('checked') : $(this).find('input').attr('checked'),
            size: event.data.grid.options.pageInfo.size,
            page: event.data.grid.options.pageInfo.curr
        }
        //位置要在资料区Check/Uncheck之前
        event.data.grid.sum(inData);

        //$.myui.request('chkall', inData, event.data.grid.options.url);
        event.data.grid._selectedPage(inData.value);

        //trigger event
        event.data.grid._rowcheck(event, { all: 1, rowid: 1, value: inData.value });

    }
        /** 
        * 选取/取消选取整页全部资料行,checkBox状态变化
        * @method _selectedPage
        * @param val {bool} true选取;false:取消选取
        */
    , _selectedPage: function(val) {
        if (val) {
            //$('#' + this._gridId() + '-data-left tbody td[name="cell0"]').find('input[checked=false]').data('old',false);
            $('#' + this._gridId() + '-data-left tbody td[name="cell0"]').find('input').attr('checked', true);
        } else {
            //$('#' + this._gridId() + '-data-left tbody td[name="cell0"]').find('input[checked=true]').data('old',true);
            $('#' + this._gridId() + '-data-left tbody td[name="cell0"]').find('input').attr('checked', false);
        }

    }
        /** 
        * 建立编辑栏
        * @method _editor
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。
        */
    , _editor: function(event) {
        var tdObj = $(this),
        prevTD = null,
        cols = event.data.grid.options.columns,
        currCol,
        editor = '<input id = "editor" type="text" class="text ui-widget-content ui-corner-all" style="overflow: visible;" value="{0}">',
        text = '<span>',
        grid = event.data.grid,
        //left or right
        lr = event.data.lr,
        w = tdObj.innerWidth() - 2,
        h = tdObj.innerHeight() - 6;

        //alert('w:'+w + 'h:' + h);
        editor = editor.format($.myui.safeStr(tdObj.find('span[name="val"]').text()));
        //editor = '<input id = "editor" type="text" class="text ui-widget-content ui-corner-all" style="overflow: visible; height: 16px;width:100%" value="' + $.myui.safeStr(tdObj.find('span[name="val"]').text()) + '">',

        //alert('span innerText:' + tdObj.find('span[name="val"]').text() + '/n' +editor);
        //重复Click 同一Cell
        if ($(this.parent).index() == grid._currRowIndex &&
            ((lr == 'left' && tdObj.index() == grid._currColIndexLeft) || (lr == 'right' && tdObj.index() == grid._currColIndexRight))
        ) {
            return;
        }
        //alert(tdObj.attr('name'));
        //find Current Column config
        currCol = grid.getColConf(tdObj.attr('name'));

        //可能是首欄，cell0
        if (currCol == null) {
            return;
        }
        //Readonly
        if (tdObj.attr('ro') == '1') {
            //event.preventDefault();
            grid._currColIndex = tdObj.index();
            if (lr == 'left') {
                grid._currColIndexLeft = tdObj.index();
                grid._currColIndexRight = -1;
            }
            if (lr == 'right') {
                grid._currColIndexRight = tdObj.index();
                grid._currColIndexLeft = -1;
            }

            return;
        }
        //如果是CheckBox
        if (typeof currCol.checkbox != 'undefined') {
            tdObj.find('input').unbind('change');
            tdObj.find('input').bind('change', { grid: grid }, function(event) {
                var val = {};
                val[tdObj.attr('name')] = $(this).attr('checked') ? currCol.checkbox : currCol.uncheckbox;
                //event.data.grid.setValue(val);
                /**/
                var tr = $(this).parent().parent().parent();
                var left_data = $('#' + event.data.grid._gridId() + '-data-left');
                var i = tr.index();
                var key = left_data.find('tbody>tr').eq(i).find('td[name="cell0"]').attr('val');
                if (tr.attr('emptykey') == "1") {
                    val = event.data.grid.row();
                    val[tdObj.attr('name')] = $(this).attr('checked') ? currCol.checkbox : currCol.uncheckbox;
                    event.data.grid.setValue(val, key, "add");
                    tr.attr('emptykey', '0');
                } else {
                    event.data.grid.setValue(val);

                }

            });
            return;
        }

        if (tdObj.find('span').length > 0) {
            tdObj.data('old', tdObj.find('span[name="val"]').text());

            //当没有错误栏存在，或重新校验成功时，强制清除
            if ($('#editor').length == 0 || $('#editor.ui-state-error').length == 0
               || event.data.grid._checkInput($('#editor').parent().attr('name'), $('#editor').val())) {
                //alert('beforeEditor is null:' + (event.data.grid._beforeEditor == null));
                //首次
                if (event.data.grid._beforeEditor == null) {
                    prevTD = tdObj;

                } else {
                    prevTD = event.data.grid._beforeEditor;
                }
                if ($('#editor').length == 1) {
                    if (prevTD.find('span[name="val"]').length == 1) {
                        //prevTD.find('span[name="val"]').text($.myui.safeStr($('#editor').val()));
                        prevTD.find('span[name="val"]').text($('#editor').val());
                        //alert(prevTD.html());
                        $('#editor').parent().append(prevTD.html());
                        //IE在當用Mouse Click到下一Cell時，不觸發Change事件（ENTER可）
                        //在change中有新舊值對比，所以此處不比較。
                        if ($.browser.msie) {
                            $('#editor').trigger('change');
                        }
                        var parentel = $('#editor').parent();
                        //此時移除
                        var oldvalue = $('#editor').val();
                        $('#editor').remove();
                        if (parentel[0].className == 'ui-combobox') {
                            parentel.parent().append('<span name="val">' + oldvalue + '</span>');
                            parentel.remove();
                        }
                    }
                }
                //注意：必須用clone，不然是传址的，会被改变。保存原值得目的就不能达到。
                event.data.grid._beforeEditor = tdObj.clone(true);

                tdObj.empty();
                tdObj.append(editor);

                $('#editor').css({ width: w, height: h });


            } else {//存在未通過資料校验栏
                event.preventDefault();
                return;
            }


            if (typeof currCol.date != 'undefined') {
                event.data.grid._makeDatepicker([{ id: 'editor', config: currCol}]);
            }
            if (typeof currCol.combo != 'undefined') {
                var comboFields = [{ id: 'editor', width: currCol.size, colName: currCol.id, config: currCol, isCond: false}];
                event.data.grid._makeCombo(comboFields);
            }

            $('#editor').unbind('change');
            $('#editor').bind('change', { grid: event.data.grid }, function(event) {
                //alert('editor:changed');
                //alert('row id='+ key + '/n old=' + $(this).parent().data('old') + ' new=' + $(this).val());
                if (typeof currCol.combo == 'undefined' && $('#editor').parent().data('old') != $(this).val()) {
                    var tr = $(this).parent().parent();
                    var left_data = $('#' + event.data.grid._gridId() + '-data-left');
                    var i = tr.index();
                    var key = left_data.find('tbody>tr').eq(i).find('td[name="cell0"]').attr('val');

                    var val = {};
                    val[tdObj.attr('name')] = $(this).val();
                    //校检资料通过
                    if (event.data.grid._checkInput(tdObj.attr('name'), $(this).val())) {
                        //當用快捷鍵操作時，會引起畫面資料更新不準確，原因是change發生的時間會比移動行更遲。而用鼠標操作時不會。
                        //所以加了key
                        //event.data.grid.setValue(val);
                        //alert(key);
                        if (tr.attr('emptykey') == "1") {
                            event.data.grid.setValue(event.data.grid.row(), key, "add");
                            tr.attr('emptykey', '0');
                        } else {
                            event.data.grid.setValue(val, key);
                        }
                        //trigger cellchanged event
                        event.data.grid._cellchanged(event, { name: tdObj.attr('name'), value: $(this).val() });

                    } else {
                        $(this).addClass('ui-state-error');
                        //$(this).trigger('click');
                        event.preventDefault();

                        return false;
                    }
                }

            });
            $('#editor').bind('blur', { grid: event.data.grid }, function(event) {
                //alert('trigger blur');
                if ($.browser.msie) {
                    $('#editor').trigger('change');
                }

            });
            //bind editor dblclick event
            $('#editor').bind('dblclick', { grid: event.data.grid }, function(event) {
                event.data.grid._celldblclick(event, { name: tdObj.attr('name'), value: $(this).val(), data: frm.getData() });
            });
            /*
            IE不能及時得到焦點
            */
            if ($.browser.msie) {
                setTimeout(function() {
                    $('#editor').focus();
                }, 100);
            } else {
                $('#editor').focus();
            }
            if (grid.options.editSelect) {
                $('#editor').select();
            }

            grid._currColIndex = tdObj.index();
            if (lr == 'left') {
                grid._currColIndexLeft = tdObj.index();
                grid._currColIndexRight = -1;
            }
            if (lr == 'right') {
                grid._currColIndexRight = tdObj.index();
                grid._currColIndexLeft = -1;
            }

        }
    }
        /** 
        * TD變成Editor前
        * @property _beforeEditor {jQuery.object} defalut = null
        */
    , _beforeEditor: null
        /** 
        * 单元格双击时触发事件，可bind在某Cell,注意前面加'grid'并全部用小写,如： grid.bind('gridcelldblclick',{},function(event){alert(event.data);});
        * @event celldblclick
        * @param event {Object} 父事件的参数
        * @param data {Object} 传过来的参数，格式如：『name:'projectid',value:'0101001'』
        */
    , _celldblclick: function(event, data) {
        //alert('_celldblclick'+data.name+data.value);
        this._trigger("celldblclick", event, data);
    }

        /** 
        * cell changed event:只有在值被改变时才触发
        * 调用时，注意前面加'grid'并全部用小写，eg. grid.bind('gridcellchanged',{},function(event,data){alert(data.name + data.value);});
        * @event cellchanged
        * @param event {Object} 父事件的参数
        * @param data {Object} 传过来的参数，格式如：『name:'projectid',value:'0101001'』
        */
    , _cellchanged: function(event, data) {
        //alert('_cellchanged'+data.name+data.value);
        this._trigger("cellchanged", event, data);
    }
        /** 
        * 更改页次信息
        * @method _setPage
        * @param pageInfo {Object} 页次对象。如：{size:20,pages:2,curr:1,records:100}
        */
    , _setPage: function(pageInfo) {
        var toolbars = this._getToolbar(),
        tbId;

        //可能有俩个Toolbar
        for (var i = 0; i < toolbars.length; i++) {
            tbId = '#' + this._gridId() + '-tb-' + toolbars[i];
            //size
            if (typeof pageInfo.size != 'undefined') {
                $(tbId + '-size').val(pageInfo.size);
            } else {
                pageInfo.size = this.options.pageInfo.size;
            }
            //pages
            if (typeof pageInfo.pages != 'undefined') {
                $(tbId + '-pages').val(pageInfo.pages);
            } else {
                pageInfo.pages = this.options.pageInfo.pages;
            }
            //records
            if (typeof pageInfo.records != 'undefined') {
                $(tbId + '-records').val(pageInfo.records);
            } else {
                pageInfo.records = this.options.pageInfo.records;
            }

            //curr
            if (typeof pageInfo.curr != 'undefined') {
                $(tbId + '-curr').val(pageInfo.curr);
            } else {
                pageInfo.curr = this.options.pageInfo.curr;
            }
            //第一页时，不可前翻
            if (pageInfo.curr == 1) {
                $(tbId + '-first').button("disable");
                $(tbId + '-prev').button("disable");

                $(tbId + '-next').button("enable");
                $(tbId + '-last').button("enable");

            } else if (pageInfo.curr == pageInfo.pages) {//最后一页时，不可后翻
                $(tbId + '-first').button("enable");
                $(tbId + '-prev').button("enable");

                $(tbId + '-next').button("disable");
                $(tbId + '-last').button("disable");

            } else {//否则，可前可后
                $(tbId + '-first').button("enable");
                $(tbId + '-prev').button("enable");

                $(tbId + '-next').button("enable");
                $(tbId + '-last').button("enable");

            }

            if (pageInfo.pages == 1 || pageInfo.records == 0 || pageInfo.pages == '' || pageInfo.records == '') {//只有一页时，不可前翻、后翻
                $(tbId + '-first').button("disable");
                $(tbId + '-prev').button("disable");

                $(tbId + '-next').button("disable");
                $(tbId + '-last').button("disable");
            }

            //保存PageInfo
            this.options.pageInfo = pageInfo;
            //alert(this.options.pageInfo.records);
        }


    }
        /** 
        * 取得Toolbar对象数组
        * @method _getToolbar
        * @return {Array} top and bottom
        */
    , _getToolbar: function() {
        var ret = [];
        if (this.options.toolbarTop) {
            ret.push("top");
        }
        if (this.options.toolbarBottom) {
            ret.push("bottom");
        }
        return ret;
    }


        /** 
        * 初始化grid 对象
        * @method _tabify
        */
        , _tabify: function() {
            var self = this
            , o = this.options
            , gd_main, gd_toolbar_t, gd_toolbar_b, gd_title_l, gd_title_r, gd_body, gd_body_l, gd_body_r, gd_data_l, gd_data_r
            , gd_id = self._gridId(), pcmCookieName = 'cache_pcm_' + gd_id
            , tmpId
            , w = '100%'
            , h = '200px'
            , firstRow
            , retPcm
            , t1 = new Date();

            //沒有設定config,為PCM模式
            //if (this.options.config == null) {
            //2011-09-15:add》 pcm.elements.element.cfg，為什麼以前是OK的？
            //this.options.pcm = $.myui.request('c', {}, this.options.url);
            //this.options.pcm = $.myui.request('c', {}, this.options.url);
            //this.options.config = this.options.pcm.pcm.elements.element.cfg;
            ///this.options.columns = this.options.pcm.pcm.elements.element.col;
            //} else {//為前端設定config模式
            //若沒有設定columns，則
            if (this.options.columns.length == 0) {
                $.myui.msg('沒有設定columns屬性', 'n');
            }
            //}

            /*
            alert($.myui.cookie(pcmCookieName));
            if($.myui.cookie(pcmCookieName)===null){
            if (this.options.config == null) {
            retPcm = $.myui.request('c', {}, this.options.url,false);
            alert(retPcm);
            this.options.config = $.parseJSON(retPcm);
            $.myui.cookie(pcmCookieName,retPcm);
            }
                
            }else{
            retPcm =$.myui.cookie(pcmCookieName);
            this.options.config = $.parseJSON(retPcm);
            }
            
            //HTML5 suport   
            if ($.myui.cache(pcmCookieName)===null){
            retPcm = $.myui.request('c', {}, this.options.url,false);
            $.myui.cache(pcmCookieName,retPcm);
            }else{
            retPcm = $.myui.cache(pcmCookieName);
                
            }
            this.options.config = $.parseJSON(retPcm);  
            */
            //var gd_id = this.id;
            //create div main
            self.element.append('<div id="' + gd_id + '" style="display: block; z-index: 1; outline-color: -moz-use-text-color; outline-style: none; outline-width: 0px;"></div>');
            gd_main = self.element.children();

            //self._makeSortImage(gd_id,gd_main);

            //create div top toolbar
            tmpId = gd_id + '-tb-top';
            gd_main.append('<div id="' + gd_id + '-tb-top" style="width:100%"></div>');
            if (o.toolbarTop) {
                gd_toolbar_t = gd_main.children('#' + tmpId);
                this._makeToolbar(gd_toolbar_t, 'top', self);
                //this._handleToolbar(tmpId);
            } //end if

            //body ===================================================
            tmpId = gd_id + '-body';
            gd_main.append('<div id="' + tmpId + '"></div>');
            gd_body = gd_main.children('#' + tmpId);
            //body left
            tmpId = gd_id + '-body-left';
            gd_body.append('<div id="' + tmpId + '"  style="position:absolute;"></div>');
            //gd_body.append('<div id="' + tmpId + '"  style="position:relative;float:left"></div>');
            gd_body_l = gd_body.children('#' + tmpId);
            //title left
            tmpId = gd_id + '-title-left';
            gd_body_l.append('<div id="' + tmpId + '" class="ui-widget ui-widget-content" style="overflow: hidden; position: relative;"></div>');
            gd_title_l = gd_body_l.children('#' + tmpId);

            //data left
            tmpId = gd_id + '-data-left';
            gd_body_l.append('<div id="' + tmpId + '"  class="myui-grid-body ui-widget-content" style="overflow-x: scroll; overflow-y: hidden;"></div>');
            gd_data_l = gd_body_l.children('#' + tmpId);
            gd_data_l.scroll(function() {
                //同步Title的Scroll
                gd_title_l.scrollLeft(gd_data_l.scrollLeft());
                gd_data_r.scrollTop(gd_data_l.scrollTop());
            });
            //            //data bottom left
            //            tmpId = gd_id + '-databottom-left';
            //            gd_body_l.append('<div id="' + tmpId + '" class="ui-widget ui-widget-content" style="overflow: hidden; position: relative;"></div>');


            //body right
            tmpId = gd_id + '-body-right';
            gd_body.append('<div id="' + tmpId + '"  style="position:absolute;"></div>');
            //gd_body.append('<div id="' + tmpId + '"  style="position:relative;float:left"></div>');
            gd_body_r = gd_body.children('#' + tmpId);


            //title right
            tmpId = gd_id + '-title-right';
            gd_body_r.append('<div id="' + tmpId + '" class="ui-widget ui-widget-content" style="overflow: hidden;  position: relative;"></div>');
            gd_title_r = gd_body_r.children('#' + tmpId);


            //data right
            tmpId = gd_id + '-data-right';
            gd_body_r.append('<div id="' + tmpId + '" class="myui-grid-body ui-widget-content" style="overflow: scroll; "></div>');
            //alert($('#'+tmpId).length);
            //$('#'+tmpId).blur( function () { alert("Lost focus"); } );
            //$('#'+tmpId).focus( function () { alert("focus"); } );

            gd_data_r = gd_body_r.children('#' + tmpId);
            gd_data_r.scroll(function() {
                //同步Title的Scroll
                gd_title_r.scrollLeft(gd_data_r.scrollLeft());
                gd_data_l.scrollTop(gd_data_r.scrollTop());

            });

            //            //data bottom right
            //            tmpId = gd_id + '-databottom-right';
            //            gd_body_r.append('<div id="' + tmpId + '" class="ui-widget ui-widget-content" style="overflow: hidden;  position: relative;"></div>');

            //make title include left and right
            this._makeTitle();
            //使在沒有資料時（含初始化完），出現橫向滾動條
            this._addBlank();
            //设定Left Body属性
            gd_body_l.css({ width: this.options.widthLeft });


            //end body ===================================================

            //create div bottom toolbar
            tmpId = gd_id + '-tb-bottom';
            gd_main.append('<div id="' + tmpId + '" style="width:100%"></div>');
            if (o.toolbarBottom) {
                gd_toolbar_b = gd_main.children('#' + tmpId);
                this._makeToolbar(gd_toolbar_b, 'bottom', self);
                //this._handleToolbar(tmpId);
            }
            if (typeof o.config.width != 'undefined') {
                w = o.config.width;
            }

            //设定Right Body属性
            self.element.css({ width: '100%' });
            if (o.toolbarTop) {
                gd_toolbar_t.css({ width: w });
            }
            if (o.toolbarBottom) {
                gd_toolbar_b.css({ width: w });
            }

            //w = (o.pcm.pcm.elements.element.cfg.width - this.options.widthLeft).toString();
            //自适应宽度
            //w = (self.element.parent().width()-20 - this.options.widthLeft).toString();
            //gd_body_r.css({ width: w + 'px' });
            //alert(o.pcm.pcm.elements.element.cfg.height);
            if (typeof o.config.height != 'undefined') {
                h = o.config.height;
                h = (h + ''.indexOf('px') < 0) ? h + 'px' : h;
            }

            gd_data_l.css({ height: h }); //?is gd_data_r or gd_body
            gd_data_r.css({ height: h }); //?is gd_data_r or gd_body
            //alert(gd_body_l.attr('id') + gd_body_l.width());
            //alert(gd_body_r.attr('id') + gd_body_r.width());
            //调整right body起始位置
            //gd_body_r.css({ left: this.options.widthLeft + 9 });
            gd_body_r.css({ left: this.options.widthLeft }); 

            $.myui.taken(self._gridId() + ' init ', t1);
            return true;

        }

        /** 
        * 构建Title，含条件或多行Title
        * @method _makeTitle
        */
        , _makeTitle: function() {
            var titleHtml_end = '</tbody></table>',
            gridId = this.element.attr("id"), //gv_bigbug
            titleHtml = '<table ' + TABLE_STYLE, // style=""><tbody>',   
            tdTitle_l = '', tdTitle_r = '',
            tdCond_l = '', tdCond_r = '',
            w = '100px', w_l = 0, w_r = 0,
            objTD,
            comboFields = [],
            dateFields = [],
            titleId, condId,
            isHidden = false,
            display, allowFilter,
            grid = this,
            chk, type = 'text',
            emptyCell, checked = '', unchecked = '',
            start = 0,
            end = 0,
            firstCell = 0,
            data_r = '#' + this._gridId() + '-data-right',
	        data_l = '#' + this._gridId() + '-data-left',
            title_r = '#' + this._gridId() + '-title-right',
	        title_l = '#' + this._gridId() + '-title-left',
	        titleRows = 1, tmpl_l, tmpl_r;

            tdCond_l = tdCond_r = '<tr name="cond" style="height:22px;display:' + (this.options.condCol ? '' : 'none') + '">';
            if (typeof this.options.config.rows != 'undefined') {
                titleRows = parseInt(this.options.config.rows);
            }
            //loop title rows
            for (var rows = 0; rows < titleRows; rows++) {
                tdTitle_l = tdTitle_r = '<tr style="height: 22px;" class="ui-widget-header" >';
                tmpl_l = '<tr id = "' + this._gridId() + '-blank-l" style="height: auto;">';
                tmpl_r = '<tr id = "' + this._gridId() + '-blank-r" style="height: auto;">';

                //loop columns
                for (var i = 0; i < this.options.columns.length; i++) {

                    isHidden = this._isHidden(this.options.columns[i]);
                    titleId = gridId + '-title-' + this.options.columns[i].id;
                    condId = gridId + '-cond-' + this.options.columns[i].id;
                    if (typeof this.options.columns[i].size != 'undefined') {
                        w = this.options.columns[i].size;
                        w = ((w + '').indexOf('px') < 0) ? (w + 'px') : w;
                    }
                    if (isHidden) {
                        display = 'none';
                    } else {
                        display = '';
                    }
                    //是否可输入过滤条件
                    if (typeof this.options.columns[i].filter != 'undefined' && this.options.columns[i].filter == '0') {
                        allowFilter = false;
                    } else {
                        allowFilter = true;
                    }

                    //区分左边固定栏与右边不固定栏两部份，最少有一个固定栏--CHECKBOX
                    if (i >= grid.options.fixCols) {//data right

                        tdTitle_r += '<td name = "' + this.options.columns[i].id + '" style="width:' + w + ';display:' + display + ';cursor:pointer"><span id="' + gridId + '-' + this.options.columns[i].id + '">' + this.options.columns[i].name + '</span></td>';
                        tmpl_r += '<th style="height: 0px; width: ' + w + ';"></th>';
                        //condiction TD
                        //combo
                        if (typeof this.options.columns[i].combo == 'object') {
                            type = 'text';
                            //w = parseInt(w.substr(0, w.lastIndexOf('px'))) - 0 + 'px';//留comboBox的图标位置，此功能暂时取消

                            comboFields.push({ id: condId, width: w, colName: this.options.columns[i].id, config: this.options.columns[i], prefix: gridId + '-cond-', isCond: true });
                            //date
                        } else if (typeof this.options.columns[i].date == 'object') {
                            type = 'text';
                            dateFields.push({ id: condId, config: this.options.columns[i] });
                            //checkbox
                        } else if (typeof this.options.columns[i].checkbox == 'object') {
                            type = 'checkbox';
                            checked = this.options.columns[i].checkbox.checked;
                            unchecked = this.options.columns[i].checkbox.unchecked;
                            //other:comm input
                        } else {
                            type = 'text';
                        }
                            
                        //条件始终只有一行
                        if (rows == 0) {
                            tdCond_r += '<td style="width:' + w + ';display:' + display +
                            '" nowarp="nowarp"  name="' + this.options.columns[i].id +
                            '"><input  chk="' + checked + '" unchk="' + unchecked + '" type = "' + type + '" id="' + condId + '" '
                             + (allowFilter ? '' : 'disabled') +
                            ' class="text ui-widget-content ui-corner-all" style="overflow: visible;width:' + this.options.columns[i].size + 'px"></td>';
                        }
                        checked = unchecked = '';
                        if (!isHidden) {
                            w_r += parseInt(w.substr(0, w.lastIndexOf('px')));
                        }

                    } else {//left area
                        //首栏加上checkbox
                        if (firstCell == 0) {
                            w_l = checkWidth;
                            chk = '<td style="width:' + w_l + 'px" name="cell0"><span><input id="' + this.element.attr("id") + '-chk" type="checkbox" name="checkall" ' + (this.options.single ? 'disabled' : '') + '/></span></td>';
                            //emptyCell = '<td name="cell0" noWrap="true"><span name="cond_hidden" class="ui-icon ' + open_class + '" style="cursor:pointer"></span><td><span name="cond_clear" class="ui-icon ui-icon-trash" style="cursor:pointer"></span></td></td>';
                            //emptyCell = '<td name="cell0" noWrap="true"><span name="cond_hidden" class="ui-icon ' + open_class + '" style="cursor:pointer"></span></td><td><span class="ui-icon ui-icon-flag"></span></td>';
                            emptyCell = '<td name="cell0" noWrap="true"><span name="cond_hidden" class="ui-icon ' + open_class + '" style="cursor:pointer"></span></td>';
                            //emptyCell = '<td name="cell0" noWrap="true"><div name="cond_hidden" style="cursor:pointer;width:100%">O</div></td>';
                            tdTitle_l += emptyCell;
                            tmpl_l += '<th style="height: 0px; width: ' + checkWidth + 'px;"></th>';

                            //条件始终只有一行
                            if (rows == 0) {

                                tdCond_l += chk;
                            }
                            firstCell++;
                        }

                        tdTitle_l += '<td name = "' + this.options.columns[i].id + '" style="width:' + w + ';display:' + display + ';cursor:pointer"><span id="' + gridId + '-' + this.options.columns[i].id + '">' + this.options.columns[i].name + '</span></td>';
                        tmpl_l += '<th style="height: 0px; width: ' + w + ';"></th>';

                        //combo
                        if (typeof this.options.columns[i].combo == 'object') {
                            //w = parseInt(w.substr(0, w.lastIndexOf('px'))) - 0 + 'px';//留comboBox的图标位置，此功能暂时取消
                            type = 'text';
                            comboFields.push({ id: condId, width: w, colName: this.options.columns[i].id, config: this.options.columns[i], prefix: gridId + '-cond-', isCond: true });
                            //date
                        } else if (typeof this.options.columns[i].date == 'object') {
                            type = 'text';
                            dateFields.push({ id: condId, config: this.options.columns[i] });
                            //checkbox    
                        } else if (typeof this.options.columns[i].checkbox == 'object') {
                            type = 'checkbox';
                            checked = this.options.columns[i].checkbox.checked;
                            unchecked = this.options.columns[i].checkbox.unchecked;

                            //other:input
                        } else {
                            type = 'text';
                        }
                        if (!isHidden) {
                            w_l += parseInt(w.substr(0, w.lastIndexOf('px')));
                        }
                        //条件始终只有一行
                        if (rows == 0) {
                            tdCond_l += '<td style="width:' + w + ';display:' + display +
                            '"  name="' + this.options.columns[i].id +
                            '"><input  chk="' + checked + '" unchk="' + unchecked + '" type="' + type + '" id="' + condId + '" '
                             + (allowFilter ? '' : 'disabled') +
                            ' class="text ui-widget-content ui-corner-all" style="overflow: visible;width:100%"></td>';
                        }
                        checked = unchecked = '';
                    }

                } //end columns

                //保存Left right Width值
                this.options.widthLeft = parseInt(w_l) < 36 ? 36 : w_l;
                this.options.widthRight = w_r;
                tdTitle_l += '</tr>';
                tdTitle_r += '</tr>';
                tmpl_l += '</tr>';
                tmpl_r += '</tr>';

                if ($(title_l).find('table').length == 0) {
                    $(title_l).append(titleHtml + ' style="width:' + (parseInt(w_l) + 0) + 'px;table-layout: fixed;margin-right: 0px; padding-right: 0px;"><tbody>' + titleHtml_end);
                }
                if ($(title_r).find('table').length == 0) {
                    $(title_r).append(titleHtml + ' style="width:' + (parseInt(w_r) + 0) + 'px;table-layout: fixed;margin-right: 20px; padding-right: 20px;"><tbody>' + titleHtml_end);
                }
                $(title_l).find('tbody').append(tdTitle_l);
                $(title_r).find('tbody').append(tdTitle_r);

                this.options.lrow = tmpl_l;
                this.options.rrow = tmpl_r;
                //init
                tdTitle_l = '';
                tdTitle_r = '';
                tmpl_l = '';
                tmpl_r = '';

                firstCell = 0;
            } //end title rows
            tdCond_l += '</tr>';
            tdCond_r += '</tr>';
            $(title_l).find('tbody').append(tdCond_l);
            $(title_r).find('tbody').append(tdCond_r);

            this._makeCombo(comboFields);
            this._makeDatepicker(dateFields, true);

            //第一行选取栏CheckBox:(cell0：SelectAllRow的标志，区分一般的CheckBox)
            $(title_l).find('tbody td[name="cell0"]').find('input[type="checkbox"]').bind('change', { grid: grid }, this._checkAll);
            //text chaged
            $(title_l).find('input[type="text"]').bind('change', { grid: grid, kind: '' }, function(event) {
                var values = {};
                values[$(this).parent().attr('name')] = $(this).val();
                event.data.grid.setCond(values);

            });
            //Data CheckBox
            $(title_l).find('tbody td[name!="cell0"]').find('input[type="checkbox"]').bind('change', { grid: grid, kind: '' }, function(event) {
                var values = {};
                values[$(this).parent().attr('name')] = $(this).attr('checked') ? $(this).attr('chk') : $(this).attr('unchk');
                event.data.grid.setCond(values);
            });

            //text change
            $(title_r).find('input[type="text"]').bind('change', { grid: grid, kind: '' }, function(event) {
                var values = {};
                values[$(this).parent().get(0).tagName == "SPAN" ? $(this).parent().parent().attr('name') : $(this).parent().attr('name')] = $(this).val();
                event.data.grid.setCond(values);
            });
            $(title_r).find('input[type="checkbox"]').bind('change', { grid: grid, kind: '' }, function(event) {
                var values = {};
                values[$(this).parent().attr('name')] = $(this).attr('checked') ? $(this).attr('chk') : $(this).attr('unchk');
                event.data.grid.setCond(values);
            });

            //sort,排除第一栏CheckBox
            $(title_r).find('tbody>tr.ui-widget-header>td[name!="cell0"]').bind('click', { grid: this }, this.sort);
            $(title_l).find('tbody>tr.ui-widget-header>td[name!="cell0"]').bind('click', { grid: this }, this.sort);
            //展开条件栏
            //alert($(title_l).find('tbody>tr.ui-widget-header>td[name ="cell0"]>span[id="cond_hidden"]').length);
            $(title_l).find('tbody>tr.ui-widget-header>td[name ="cell0"]>span[name="cond_hidden"]').bind('click', { grid: this }, function(event) {
                $('#' + grid._gridId() + '-body tr[name="cond"]').toggle();
                //切换图标
                if ($(this).hasClass(open_class)) {
                    $(this).removeClass(open_class);
                    $(this).addClass(close_class);
                } else {
                    $(this).removeClass(close_class);
                    $(this).addClass(open_class);

                }
            });
            $(title_l).find('tbody>tr.ui-widget-header>td>span[name="cond_clear"]').bind('click', { grid: this }, function(event) {
                event.data.grid.clear(0);
            });
        }
        /** 
        * 追加空行，使在無資料時，出現滾動條
        * @method _addBlank
        */
        , _addBlank: function() {
            var gd_data_r, gd_data_l
            gd_data_l = $('#' + this._gridId() + '-data-left');
            gd_data_r = $('#' + this._gridId() + '-data-right');
            gd_data_r.append('<table style="width:' + this.options.widthRight + 'px;table-layout: fixed;" cellspacing="1" cellpadding="1"><tbody>' + this.options.rrow + '</tbody></table>');
            gd_data_l.append('<table style="width:' + this.options.widthLeft + 'px;table-layout: fixed;"  cellspacing="1" cellpadding="1"><tbody>' + this.options.lrow + '</tbody></table>');
        }
        /** 
        * 排序
        * @method sort
        * @param event {Object} 传值对象，在Button上调用时要将grid对象用参数传过来，不然this就会是Button本身。
        */
        , sort: function(event) {
            var grid = event.data.grid,
            sorttype = "asc",
            datatype = "string",
            rt = 'sort', allowADSC,
            ret = '',
            id = $(this).attr('name'),
            col = grid.getColConf(id),
            sortimage_id = '#sort-image-' + grid._gridId();

            //是否可让User自行升降排序
            if (typeof col.adsc != 'undefined' && col.adsc == '0') {
                allowADSC = false;
            } else {
                allowADSC = true;
            }
            if (allowADSC) {
                $(this).css({ cursor: 'hand' });

                //如果不存在當前TD,则添加
                if ($(this).find(sortimage_id).length == 0) {
                    //先移除别栏的
                    if ($(sortimage_id).length == 1) {
                        $(sortimage_id).remove();
                    }
                    $(this).append(grid._makeSortImage(grid._gridId()));
                }


                //切换图标
                //desc
                if ($(sortimage_id).hasClass('ui-icon ui-icon-carat-1-n')) {
                    $(sortimage_id).removeClass('ui-icon ui-icon-carat-1-n');
                    $(sortimage_id).addClass('ui-icon ui-icon-carat-1-s');
                    sorttype = 'desc';
                } else {//asc
                    $(sortimage_id).removeClass('ui-icon ui-icon-carat-1-s');
                    $(sortimage_id).addClass('ui-icon ui-icon-carat-1-n');
                    sorttype = 'asc';
                }
                //ret = $.myui.request(rt, data, grid.options.url);
                if (grid.isChanged() && window.confirm($.myui.lang('cfm_save'))) {
                    grid.save();
                    return false;
                }
                //dataType
                $.each(grid.options.columns, function(i, o) {
                    if (o.id == id) {
                        if (typeof o.type == "string") {
                            datatype = o.type;
                        }
                    }
                });
                /*
                ret = $.myui.invoke(grid.options.className + ".Query", {},
                { myui_args: {
                id: grid._gridId()
                , type: rt
                , sortType: sorttype
                , size: grid.options.pageSize
                , curr: grid.options.pageInfo.curr
                , url: grid.options.url
                , dataType: datatype
                , fldname: id
                }
                });
                */
                //query有兩個參數：conds and fields,Sort不需要，但需傳空值
                ret = $.myui.invoke(grid.options.className + ".Query", ['', ''], {
                    id: grid._gridId()
                    , type: "sort"
                    , sortType: sorttype
                    , size: grid.options.pageSize
                    , curr: grid.options.pageInfo.curr
                    , url: grid.options.url
                    , dataType: datatype
                    , fldname: id
	                , encrypt: grid.options.encrypt
	                , decrypt: grid.options.decrypt
                    
                });
                grid.bind(ret);
            }
        }
        /** 
        * 判断是否隐藏栏
        * @method _isHidden
        * @param col {String} 栏名
        * @return {bool} true:是隐藏栏；false:不为隐藏栏
        */
        , _isHidden: function(col) {
            var ret = false;
            if ((typeof col.hidden != 'undefined' && col.hidden == '1')
            || (typeof col.iskey != 'undefined' && col.iskey == '1' && col.show != '1')) {
                ret = true;
            }
            return ret;
        }
        /** 
        * 創建一排序時用到的圖片，可移動。
        * @method _makeSortImage
        */
        , _makeSortImage: function(gridId) {
            var spanImage = '<span id="sort-image-' + gridId + '" class="ui-icon ui-icon-carat-1-n" style="float: left; cursor: pointer;"></span>';
            //alert(spanImage);
            //parent.append(spanImage);
            return spanImage;
        }
        /** 
        * 创建ComboBox对象
        * @method _makeCombo
        * @param comboes {Array} 多个ComboBox
        */
        , _makeCombo: function(comboes) {
            for (var m = 0; m < comboes.length; m++) {

                $('#' + comboes[m].id).combo({
                    id: comboes[m].id,
                    fieldName: comboes[m].colName,
                    parent: this,
                    config: comboes[m].config,
                    prefix: comboes[m].prefix,
                    width: comboes[m].config.size,
                    source: comboes[m].config.combo.source,
                    isCond: comboes[m].isCond,
                    button: comboes[m].config.combo.button
                });
            }

        }
        
        /** 
        * 创建日历对象
        * @method _makeDatepicker
        * @param dps {Array} 多个日历参数
        * @param isTitle {bool} 是否為標題欄，指的是條件
        */
        , _makeDatepicker: function(dps, isTitle) {
            var format = 'yy-mm-dd',
            months = 1,
            isShow = true;



            for (var m = 0; m < dps.length; m++) {
                months = 1;
                //條件區Default不創建日曆；
                if (typeof isTitle != 'undefined') {
                    isShow = false;
                    months = 2;
                }
                //條件區若沒有設定cond=1,則不創建日曆；
                if (dps[m].config.date != null && typeof dps[m].config.date.cond != 'undefined' && dps[m].config.date.cond == '1') {
                    isShow = true;
                }
                //
                if (dps[m].config.date != null && typeof dps[m].config.date.format != 'undefined') {
                    format = dps[m].config.date.format;

                }
                //
                if (dps[m].config.date != null && typeof dps[m].config.date.months != 'undefined' && typeof isTitle != 'undefined') {
                    months = parseInt(dps[m].config.date.months);
                }
                //
                if (isShow) {
                    $('#' + dps[m].id).datepicker({
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
        }
        /** 
        * 从PCM取得栏的配置类
        * @method getColConf
        * @param fieldName {String} 栏识别名称，如proj_id
        * @return {Object} 指定栏的配置信息，若没有找到，返回null
        */
        , getColConf: function(fieldName) {
            var ret = null
            , cols = this.options.columns;
            //find Current Column
            for (var m = 0; m < cols.length; m++) {
                if (cols[m].id == fieldName) {
                    ret = cols[m];
                    break;
                }
            }

            return ret;
        }

        /** 
        * 创建工具栏
        * @method _makeToolbar
        * @param elem {Element} 附件对象
        * @param grid {Object} 当前grid对象
        */
        , _makeToolbar: function(elem, topbottom, grid) {
            var gridId = elem.attr('id')
            //, tbTemp = '<div id="' + gridId + '-c" class="ui-widget-header ui-corner-'+ tb+'" style="padding: 1px 1px;"></div>'
            , tbTemp = '<div id="' + gridId + '-c" class="ui-accordion-header ui-state-active ui-corner-' + topbottom + '" style="padding: 1px 1px;"></div>'
	        , tb, item, obj = null
            , show = "";
            elem.append(tbTemp);
            tb = $("#" + gridId + '-c');

            if (grid.options.toolbarItem.length === 0) grid.options.toolbarItem = defItem;
            show = grid.options.toolbarSimple ? 'none' : '';
            for (var i = 0; i < grid.options.toolbarItem.length; i++) {

                switch (grid.options.toolbarItem[i]) {
                    case 'p': //page
                        /*
                        item = '<button id="' + gridId + '-first">go to the first page</button>'
                        + '<button id="' + gridId + '-prev">prev page</button>'
                        + '<label  style="display:'+show +'">'+$.myui.lang('page_left')+ $.myui.lang('page_from')+'</label>'
                        + '<input  style="width:20px;display:'+show +'" type="text" id="' + gridId + '-curr" class="text ui-widget-content" />'
                        + '<label  style="display:'+show +'">'+ $.myui.lang('page_pages')+'/'+$.myui.lang('page_total')+'</label>'
                        + '<input  style="width:20px;display:'+show +'" type="text" id="' + gridId + '-pages" readonly="readonly" class="text ui-widget-content" />'
                        + '<label  style="display:'+show +'">'+$.myui.lang('page_pages') + $.myui.lang('page_right')+'-'+$.myui.lang('page_left')+$.myui.lang('page_every')+$.myui.lang('page_pages')+'</label>'
                        + '<input  style="width:20px;display:'+show +'" type="text" id="' + gridId + '-size" class="text ui-widget-content" />'
                        + '<label  style="display:'+show +'">'+$.myui.lang('page_record')+'/'+$.myui.lang('page_total')+'</label>'
                        + '<input  style="width:20px;display:'+show +'" type="text" id="' + gridId + '-records" readonly="readonly" class="text ui-widget-content" />'
                        + '<label  style="display:'+show +'">'+$.myui.lang('page_record')+$.myui.lang('page_right')+'</label>'
                        + '<button id="' + gridId + '-next">next page</button>'
                        + '<button id="' + gridId + '-last">go to the last page</button>';
                        //+ '<label id="' + gridId + '-msg" class="ui-state-highlight"></label>';
                        */


                        item = '<button id="' + gridId + '-first">go to the first page</button>'
		                 + '<button id="' + gridId + '-prev">prev page</button>'
		                 + '<label  style="display:' + show + '"> &#31532; </label>'
		                 + '<input  style="width:20px;display:' + show + '" type="text" id="' + gridId + '-curr" class="text ui-widget-content ui-corner-all" />'
		                 + '<label  style="display:' + show + '"> &#39029; / &#20849; </label>'
		                 + '<input  style="width:20px;display:' + show + '" type="text" id="' + gridId + '-pages" readonly="readonly" class="text ui-widget-content ui-corner-all" />'
		                 + '<label  style="display:' + show + '"> &#39029;-&#27599;&#39029;</label>'
		                 + '<input  style="width:20px;display:' + show + '" type="text" id="' + gridId + '-size" class="text ui-widget-content ui-corner-all" />'
		                 + '<label  style="display:' + show + '"> &#31508;/&#20849; </label>'
		                 + '<input  style="width:20px;display:' + show + '" type="text" id="' + gridId + '-records" readonly="readonly" class="text ui-widget-content ui-corner-all" />'
		                 + '<label  style="display:' + show + '"> &#31508;</label>'
    	                 + '<button id="' + gridId + '-next">next page</button>'
		                 + '<button id="' + gridId + '-last">go to the last page</button>';

                        //這種寫法並未提速
                        //item = '<button id="{0}-first">go to the first page</button><button id="{0}-prev">prev page</button><label  style="display:{1}">第</label><input  style="width:20px;display:{1}" type="text" id="{0}-curr" class="text ui-widget-content ui-corner-all" /><label  style="display:{1}">頁/共</label><input  style="width:20px;display:{1}" type="text" id="{0}-pages" readonly="readonly" class="text ui-widget-content ui-corner-all" /><label  style="display:{1}">頁-每頁</label><input  style="width:20px;display:{1}" type="text" id="{0}-size" class="text ui-widget-content ui-corner-all" /><label  style="display:{1}">筆/共</label><input  style="width:20px;display:{1}" type="text" id="{0}-records" readonly="readonly" class="text ui-widget-content ui-corner-all" /><label  style="display:{1}">筆</label><button id="{0}-next">next page</button><button id="{0}-last">go to the last page</button><label id="{0}-msg" class="ui-state-highlight"></label>';
                        //item = item.format(gridId,show);
                        //append     
                        tb.append(item);
                        //set button

                        //first
                        obj = $('#' + gridId + '-first');
                        obj.button({
                            text: false,
                            disabled: true,
                            icons: {
                                primary: 'ui-icon-seek-start'
                            }
                        });
                        grid.setToolbar({ id: 'first', fn: grid.toPage, data: { grid: grid, obj: obj }, evenName: 'click' });

                        //prev
                        obj = $('#' + gridId + '-prev');
                        obj.button({
                            text: false,
                            disabled: true,
                            icons: {
                                primary: 'ui-icon-seek-prev'
                            }
                        });
                        grid.setToolbar({ id: 'prev', fn: grid.toPage, data: { grid: grid, obj: obj }, evenName: 'click' });

                        //next
                        obj = $('#' + gridId + '-next');
                        obj.button({
                            text: false,
                            disabled: true,
                            icons: {
                                primary: 'ui-icon-seek-next'
                            }
                        });
                        grid.setToolbar({ id: 'next', fn: grid.toPage, data: { grid: grid, obj: obj }, evenName: 'click' });

                        //last
                        obj = $('#' + gridId + '-last');
                        obj.button({
                            text: false,
                            disabled: true,
                            icons: {
                                primary: 'ui-icon-seek-end'
                            }
                        });
                        grid.setToolbar({ id: 'last', fn: grid.toPage, data: { grid: grid, obj: obj }, evenName: 'click' });

                        //pages
                        obj = $('#' + gridId + '-pages');
                        obj.bind('dblclick', { grid: grid }, function(event) {
                            var data = $.myui.request('xcond', {}, event.data.grid.options.url);
                            $.myui.win({ title: 'Cond', msg: 'xCond', message: data });
                        });
                        //records
                        obj = $('#' + gridId + '-records');
                        obj.bind('dblclick', { grid: grid }, function(event) {
                            var data = $.myui.request('xdata', {}, event.data.grid.options.url);
                            $.myui.win({ title: 'Data', msg: 'xData', message: data });
                        });
                        //size change
                        obj = $('#' + gridId + '-size').bind('change', { grid: grid },
                            function(event) {
                                //alert(event.data.grid.options.pageInfo.records);
                                grid._setPage({
                                    size: $(this).val()
                                    , pages: (event.data.grid.options.pageInfo.records % $(this).val() == 0) ? (event.data.grid.options.pageInfo.records / $(this).val()) : (Math.floor(event.data.grid.options.pageInfo.records / $(this).val()) + 1)
                                    , curr: 1
                                });
                                event.data.grid.toPage('size');

                            });
                        //curr change
                        obj = $('#' + gridId + '-curr').bind('change', { grid: grid },
                            function(event) {
                                //alert(event.data.grid);
                                grid._setPage({ curr: $(this).val() });
                                event.data.grid.toPage('curr');

                            });
                        break;
                    case 'q': //query

                        item = '<button id="' + gridId + '-query">' + $.myui.lang('btn_query') + '</button>';
                        tb.append(item);
                        obj = $('#' + gridId + '-query');
                        obj.button(
                            {
                                text: grid.options.buttonText,
                                disabled: false,
                                icons: {
                                    primary: 'ui-icon-search'
                                }
                            }
                        );
                        grid.setToolbar({ id: 'query', fn: grid.query, data: { grid: grid }, evenName: 'click' });

                        break;
                    case 'a': //add row
                        if (
                        (typeof grid.options.config.readonly == 'undefined' || grid.options.config.readonly == '0')
                        && (!this.options.readonly)
                        ) {
                            item = '<button id="' + gridId + '-add">' + $.myui.lang('btn_add') + '</button>';
                            tb.append(item);
                            obj = $('#' + gridId + '-add');
                            obj.button(
                            {
                                text: grid.options.buttonText,
                                disabled: false,
                                icons: {
                                    primary: 'ui-icon-plusthick '
                                }
                            }
                            );
                            grid.setToolbar({ id: 'add', fn: grid.addRow, data: { grid: grid }, evenName: 'click' });
                        }
                        break;
                    case 'c': //copy row
                        if (this.options.copy) {
                            item = '<button id="' + gridId + '-copy">' + $.myui.lang('btn_copy') + '</button>';
                            tb.append(item);
                            obj = $('#' + gridId + '-copy');
                            obj.button(
                            {
                                text: grid.options.buttonText,
                                disabled: false,
                                icons: {
                                    primary: 'ui-icon-copy'
                                }
                            }
                            );
                            grid.setToolbar({ id: 'copy', fn: grid.copyRow, data: { grid: grid }, evenName: 'click' });
                        }
                        break;

                    case 'r': //remove row
                        if (
                        (typeof grid.options.config.readonly == 'undefined' || grid.options.config.readonly == '0')
                        && (!this.options.readonly)
                        ) {

                            item = '<button id="' + gridId + '-remove">' + $.myui.lang('btn_remove') + '</button>';
                            tb.append(item);
                            obj = $('#' + gridId + '-remove')
                            obj.button(
                            {
                                text: grid.options.buttonText,
                                disabled: false,
                                icons: {
                                    // primary: 'ui-icon-trash'
                                    primary: 'ui-icon-minusthick'
                                }
                            }

                            );
                            grid.setToolbar({ id: 'remove', fn: grid.removeRow, data: { grid: grid }, evenName: 'click' });
                        }
                        break;
                    case 's': //save data
                        if (
                        (typeof grid.options.config.readonly == 'undefined' || grid.options.config.readonly == '0')
                        && (!this.options.readonly)
                        ) {

                            item = '<button id="' + gridId + '-save">' + $.myui.lang('btn_save') + '</button>';
                            tb.append(item);
                            obj = $('#' + gridId + '-save');
                            obj.button(
                            {
                                text: grid.options.buttonText,
                                disabled: false,
                                icons: {
                                    primary: 'ui-icon-disk'
                                }
                            }


                            );
                            grid.setToolbar({ id: 'save', fn: grid.save, data: { grid: grid }, evenName: 'click' });

                        }
                        break;
                    case 'e': //export data to excel


                        item = '<button id="' + gridId + '-export">' + $.myui.lang('btn_export') + '</button>';
                        tb.append(item);
                        obj = $('#' + gridId + '-export');
                        obj.button(
                            {
                                text: grid.options.buttonText,
                                disabled: false,
                                icons: {
                                    primary: 'ui-icon-extlink'
                                }
                            }

                        );
                        grid.setToolbar({ id: 'export', fn: grid.exportData, data: { grid: grid }, evenName: 'click' });

                        break;

                    default: //other
                        break;
                } //end switch
            } //end for

        } //_makeToolbar

    });
})(jQuery);