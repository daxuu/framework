/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework.Data;

import Framework.Xml.*;
//dom4j包
import Framework.logger.MyLogger;
import java.util.*;
//import javax.swing.text.html.HTMLDocument.*;
import java.sql.SQLException;
//import java.util.*;
import org.dom4j.*;

/**
 *通用的Database對象類，如Table，View等
 * @author Adminjstrator
 */
public class DbObject {

    private String _TableName;
    private XmlDocModel _TableSchema;
    private String _CurrentKeyValue;
    private String _KeyName;
    private String _NextKeyValue;
    private String _DatabaseName;
    private String _DataSourceName;

    public String getCurrentKeyValue() {
        return _CurrentKeyValue;
    }

    public void setCurrentKeyValue(String _CurrentKeyValue) {
        this._CurrentKeyValue = _CurrentKeyValue;
    }

    public String getDataSourceName() {
        return _DataSourceName;
    }

    public void setDataSourceName(String _DataSourceName) {
        this._DataSourceName = _DataSourceName;
    }

    public String getDatabaseName() {
        return _DatabaseName;
    }

    public void setDatabaseName(String _DatabaseName) {
        this._DatabaseName = _DatabaseName;
    }

    public String getKeyName() {
        return _KeyName;
    }


    public String getNextKeyValue() {
        return _NextKeyValue;
    }

    public void setNextKeyValue(String _NextKeyValue) {
        this._NextKeyValue = _NextKeyValue;
    }

    public String getTableName() {
        return _TableName;
    }

    public void setTableName(String _TableName) {
        this._TableName = _TableName;
    }

    public XmlDocModel getTableSchema() {
        XmlDocModel ret = new XmlDocModel();
        String tableName = getDataSourceName();
        String dbName = getDatabaseName();
        String path = "config//" + tableName + ".xml";
        //沒有緩存
        if (ret.load(path) == null) {

        	//remark temp
            //_TableSchema = ret.CreateSchema(tableName, dbName);
        } else {
            _TableSchema = ret;

        }

        return _TableSchema;

    }

    public void setTableSchema(XmlDocModel _TableSchema) {
        this._TableSchema = _TableSchema;
    }

    /// <summary>
    /// 取得資料，指定DB，TABLE，Field,條件，封裝類型
    /// </summary>
    /// <param name="argDbName">资料庫名称</param>
    /// <param name="argDataSourceName">资料来源名称，如TABLE、View名称</param>
    /// <param name="argFields">栏位清单，以","号分隔</param>
    /// <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday = '2000/09/01'</param>
    /// <param name="argIsFlat">將欄位的節點變成屬性</param>
    /// <returns>将资料封装成XmlDocModel返回.</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 指定DB，TABLE，Field,條件，封裝類型
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String cond = String.format("projectid={0}",new String[]{'1'});
    ///		XmlDocModel xProject = Query("CRM","PROJECT","PROJECTID,PROJECTNAME",cond,false);
    ///	</code>
    ///	</example>
    /// </remarks>
    public XmlDocModel Query(String argDbName, String argDataSourceName, String argFields, String argCond, boolean argIsFlat) {
        XmlDocModel ret = DbHelper.Query(argDbName, argDataSourceName, argFields,
                argCond, argIsFlat);
        return ret;
    }

    /// <summary>
    /// 取得資料，指定DB，TABLE，Field,條件
    /// </summary>
    /// <param name="argDbName">资料庫名称</param>
    /// <param name="argDataSourceName">资料来源名称，如TABLE、View名称</param>
    /// <param name="argFields">栏位清单，以","号分隔</param>
    /// <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday = '2000/09/01'</param>
    /// <returns>将资料封装成XmlDocModel返回.</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 重載調用Query,指定DB，TABLE，Field,條件,
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String cond = String.format("projectid={0}",new String[]{'1'});
    ///		XmlDocModel xProject = Query("CRM","PROJECT","PROJECTID,PROJECTNAME",cond);
    ///	</code>
    ///	</example>
    /// </remarks>
    public XmlDocModel Query(String argDbName, String argDataSourceName, String argFields, String argCond) {
        return Query(argDbName, argDataSourceName, argFields, argCond);
    }

    /// <summary>
    /// 取得資料（默認為當前的DAL類指定的DB）
    /// </summary>
    /// <param name="argDataSourceName">资料来源名称，如TABLE、View名称</param>
    /// <param name="argFields">栏位清单，以","号分隔</param>
    /// <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday = '2000/09/01'</param>
    /// <param name="argIsFlat">將欄位的節點變成屬性</param>
    /// <returns>将资料封装成XmlDocModel返回.</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 重載調用Query，默認為當前的DAL類指定的DB，若該DAL類沒有指定，則為Config中設定的Default DB
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String cond = String.format("projectid={0}",new String[]{'1'});
    ///		XmlDocModel xProject = Query("PROJECT","PROJECTID,PROJECTNAME",cond,false);
    ///	</code>
    ///	</example>
    /// </remarks>
    public XmlDocModel Query(String argDataSourceName, String argFields, String argCond, boolean argIsFlat) {
        String dbName = this.getDatabaseName();
        MyLogger.Write(dbName);
        return Query(dbName, argDataSourceName, argFields, argCond, argIsFlat);
    }

    /// <summary>
    /// 取得資料，資料對象為當前DAL對象指定名稱
    /// </summary>
    /// <param name="argFields">栏位清单，以","号分隔</param>
    /// <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday = '2000/09/01'</param>
    /// <param name="argIsFlat">將欄位的節點變成屬性</param>
    /// <returns>将资料封装成XML返回.</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 重載調用Query
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String cond = String.format("projectid={0}",new String[]{'1'});
    ///		XmlDocModel xProject = Query("PROJECTID,PROJECTNAME",cond,false);
    ///	</code>
    ///	</example>
    /// </remarks>
    public XmlDocModel Query(String argFields, String argCond, boolean argIsFlat) {

        return this.Query(this.getDataSourceName(), argFields, argCond, argIsFlat);
    }

    /// <summary>
    /// 取得資料，資料對象為當前DAL對象指定名稱，欄資料以節點封裝
    /// </summary>
    /// <param name="argFields">栏位清单，以","号分隔</param>
    /// <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday = '2000/09/01'</param>
    /// <param name="argIsFlat">將欄位的節點變成屬性</param>
    /// <returns>将资料封装成XML返回.</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 重載調用Query
    /// 欄位不封裝成屬性
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String cond = String.format("projectid={0}",new String[]{'1'});
    ///		XmlDocModel xProject = Query("PROJECTID,PROJECTNAME",cond);
    ///	</code>
    ///	</example>
    /// </remarks>
    public XmlDocModel Query(String argFields, String argCond) {
        return this.Query(this.getDataSourceName(), argFields, argCond, false);
    }

    /// <summary>
    /// 取得資料:指定条件,所有栏位
    /// </summary>
    /// <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday = '2000/09/01'</param>
    /// <returns>将资料封装成XML返回.</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 重載調用Query
    ///
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String cond = String.format("projectid={0}",new String[]{'1'});
    ///		XmlDocModel xProject = Query(cond,false);
    ///	</code>
    ///	</example>
    /// </remarks>
    public XmlDocModel Query(String argCond, boolean argIsFlat) {
        return this.Query("*", argCond, argIsFlat);
    }
    /// <summary>
    /// 取得資料:指定条件,所有栏位，欄資料以節點封裝
    /// </summary>
    /// <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday = '2000/09/01'</param>
    /// <returns>将资料封装成XML返回.</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 重載調用Query
    ///
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String cond = String.format("projectid={0}",new String[]{'1'});
    ///		XmlDocModel xProject = Query(cond);
    ///	</code>
    ///	</example>
    /// </remarks>

    public XmlDocModel Query(String argCond) {
        return this.Query(argCond, false);
    }
    /// <summary>
    /// 查询功能:指定条件,所有栏位
    /// </summary>
    /// <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday = '2000/09/01'</param>
    /// <param name="argIsFlat">將欄位的節點變成屬性</param>
    /// <returns>将资料封装成XML返回.</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 1.默认的条件运算符为  '='
    /// 2.若接收到的条件中有'%'或'*'，则用Like
    /// 3.若接收到的条件中有','，则用IN
    /// 4.若接收到的条件中有空格' '，则分成两个条件，并以OR运算
    /// 5.若接收到的条件中有‘&’，则分成两个条件，并以AND运算
    /// 6.若接收到的条件第一个字符为等于（=）、大于（>）、小于（<）、不等于(<>)号，则以其替换默认运算符
    /// 7.若接收到的条件有'~',则用Between
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2009-08-18 Create
    /// Lucky      2009-09-09 增加第7點規格
    /// </para>
    ///
    /// <example>
    ///   <?xml version="1.0" encoding="utf-8" ?>
    ///   <xconfig>
    ///     <cond>
    ///         <project_id />
    ///         <proj_name>PM</proj_name>
    ///         <proj_nm />
    ///     </cond>
    ///     <def>
    ///         <project_id />
    ///         <proj_name>PM</proj_name>
    ///         <proj_nm />
    ///     </def>
    ///     <fixcond>
    ///         <item link="AND">qty=oqty</item>
    ///         <item link="AND">qty&lt0</item>
    ///     </fixcond>
    ///   </xconfig>
    /// </example>
    /// </remarks>

    public XmlDocModel Query(XmlDocModel argCond, boolean argIsFlat) {
        int i = 0, j = 0, k = 0, m = 0;
        String[] words = new String[]{};
        String item;
        String sybol = " = ", word = "", tp;

        StringBuilder sbFields = new StringBuilder();
        StringBuilder sbWhere = new StringBuilder();
        StringBuilder sbOrder = new StringBuilder();
        Document doc = argCond.getDocument();
        //Element xnCond = argCond.ChildNodes[1].FirstChild;
        Element root = doc.getRootElement();
        Element el;
        Element xnCond = root.element("cond");
        // xnCond.ge
        // MyLogger.Write(xnCond.attribute("top").getValue());

        //for sql server
        if (xnCond.attribute("top") != null)//取部分資料
        {
            sbFields.append(" top ").append(xnCond.attribute("top").getValue()).append(" ");
        }
        //for every cond node
        for (Iterator it = xnCond.elementIterator(); it.hasNext();) {
            el = (Element) it.next();
            if (i != 0) {
                sbFields.append(",");
            }
            i++;
            sbFields.append(el.getName());

            if (el.getText() != "") {
                if (j != 0) {
                    //在PCM中可設定 ＡＮＤ或OR
                    if (el.attribute("top") == null) {
                        sbWhere.append(" AND ");
                    } else {
                        sbWhere.append(" ");
                        sbWhere.append(el.attribute("top").getValue());
                        sbWhere.append(" ");
                    }
                }
                j++;
                tp = (el.attribute("type") == null) ? "String" : el.attribute("type").getValue();
                words = el.getText().split(" ");
                sbWhere.append("(");
                m = 0;

                for (int s = 0; s < words.length; s++) {
                    item = words[s];
                    if (m != 0) {
                        sbWhere.append(" OR ");
                    }
                    m++;

                    sbWhere.append("(");
                    word = item.replace("*", "%");
                    if (word.indexOf(',') >= 0)//IN
                    {
                        sbWhere.append(MakeSqlIN(el.getName(), tp, word));
                    } else if (word.indexOf('~') >= 0)//between
                    {
                        sbWhere.append(MakeSqlBETWEEN(el.getName(), tp, word));
                    } else if (word.indexOf('=') == 0)//等于（=）
                    {
                        sybol = " = ";
                        sbWhere.append(el.getName()).append(sybol).append((tp == "String" ? "'" : "")).append(word.substring(1)).append((tp == "String" ? "'" : ""));
                    } else if (word.indexOf('>') == 0)//大于（>）
                    {
                        sybol = " > ";
                        sbWhere.append(el.getName()).append(sybol).append((tp == "String" ? "'" : "")).append(word.substring(1)).append((tp == "String" ? "'" : ""));
                    } else if (word.indexOf('<') == 0)//小于（>）
                    {
                        sybol = " > ";
                        sbWhere.append(el.getName()).append(sybol).append((tp == "String" ? "'" : "")).append(word.substring(1)).append((tp == "String" ? "'" : ""));
                    } else if (word.indexOf('!') == 0)//不等于（<>）
                    {
                        sybol = " <> ";
                        sbWhere.append(el.getName()).append(sybol).append((tp == "String" ? "'" : "")).append(word.substring(1)).append((tp == "String" ? "'" : ""));
                    } else if (word.indexOf("<>") == 0)//不等于（<>）
                    {
                        sybol = " <> ";
                        sbWhere.append(el.getName()).append(sybol).append((tp == "String" ? "'" : "")).append(word.substring(2)).append((tp == "String" ? "'" : ""));
                    } //if (word.indexOf('%') >= 0 && tp == "String")//含%号且为字符型态时
                    else if (tp == "String")//字符型態用LIKE ‘VALUE%'
                    {
                        sybol = " LIKE ";

                        sbWhere.append(el.getName()).append(sybol).append("'").append(word).append("%'");
                    } else {
                        sybol = " = ";
                        sbWhere.append(el.getName()).append(sybol).append((tp == "String" ? "'" : "")).append(word).append((tp == "String" ? "'" : ""));

                    }
                    sbWhere.append(")");
                }
                sbWhere.append(")");



            }
            if (el.attribute("order") != null) {
                if (k == 0) {
                    sbOrder.append(" ORDER BY ");

                } else {
                    sbOrder.append(",");

                }
                k++;
                sbOrder.append(el.getName()).append(" ").append(el.attribute("order").getValue());

            }

        }
        Element xnFixCond = root.element("fixcond");

        //for every FixCond node
        //加上PCM中设定的固定条件
        for (Iterator it = xnFixCond.elementIterator(); it.hasNext();) {
            el = (Element) it.next();

            //沒有條件
            if (sbWhere.length() > 0) {
                sbWhere.append(" ");
                sbWhere.append(el.attribute("link") == null ? "AND" : el.attribute("link").getValue());
                sbWhere.append(" ");

            }
            sbWhere.append(el.getText());
        }



        //沒有條件
        if (sbWhere.length() == 0) {
            sbWhere.append(" 1=1 ");
        }
        if (sbOrder.length() > 0) {
            sbWhere.append(sbOrder.toString());
        }

        //  MyLogger.Write(sbFields.toString());
        // MyLogger.Write(sbWhere.toString());
        return this.Query(sbFields.toString(), sbWhere.toString(), argIsFlat);
        //return null;
    }

    /// <summary>
    /// 組裝IN SQL語句片段
    /// </summary>
    /// <param name="argFldName">欄名稱</param>
    /// <param name="argType">欄類型</param>
    /// <param name="argValue">值</param>
    /// <returns>IN SQL語句</returns>
    private String MakeSqlIN(String argFldName, String argType, String argValue) {
        String ret;
        int i = 0;
        StringBuilder sbSQL = new StringBuilder();
        String[] values = argValue.split(",");
        String val;

        sbSQL.append(argFldName).append(" IN ").append("(");

        for (int j = 0; j <= values.length; j++) {
            val = values[j];
            //需處理”,A,B,,C,“等輸入狀況。
            //當不是第一個元素，且有值時
            if (i > 0 && (val != null)) {
                sbSQL.append(",");
            }
            i++;
            //有值
            if (val != null) {
                sbSQL.append((argType == "String" ? "'" : ""));
                sbSQL.append(val);
                sbSQL.append((argType == "String" ? "'" : ""));
            }
        }

        sbSQL.append(")");

        ret = sbSQL.toString();
        return ret;

    }
    /// <summary>
    /// 組裝IN SQL語句片段
    /// </summary>
    /// <param name="argFldName">欄名稱</param>
    /// <param name="argType">欄類型</param>
    /// <param name="argValue">值</param>
    /// <returns>IN SQL語句</returns>

    private String MakeSqlBETWEEN(String argFldName, String argType, String argValue) {
        String ret;
        int i = 0;
        StringBuilder sbSQL = new StringBuilder();
        String[] values = argValue.split("~");
        String val;

        sbSQL.append(argFldName).append(" BETWEEN ");
        if (values.length != 2) {
            //wait...
            //throw new BLLException("bl0010", new String[] { argValue });
        }
        for (int j = 0; j <= values.length; j++) {
            val = values[j];
            if (val != null) {
                //throw new Framework.MyException.BLLException("bl0010", new String[] { argValue });
            }
            //需處理”A~B“等輸入狀況。
            //當不是第一個元素，且有值時
            if (i > 0 && (val != null)) {
                sbSQL.append(" AND ");
            }
            i++;
            //有值
            if (val != null) {
                sbSQL.append((argType == "String" ? "'" : ""));
                sbSQL.append(val);
                sbSQL.append((argType == "String" ? "'" : ""));
            }
        }


        ret = sbSQL.toString();
        return ret;

    }
    /// <summary>
    /// 查询功能:所有栏位所有资料
    /// </summary>
    /// <param name="argIsFlat">將欄位的節點變成屬性</param>
    /// <returns>将资料封装成XML返回.</returns>

    public XmlDocModel Query(boolean argIsFlat) {
        return this.Query("*", "1>1", argIsFlat);
    }

    /// <summary>
    /// 查询功能:所有栏位所有资料
    /// </summary>
    /// <returns>将资料封装成XML返回.</returns>
    public XmlDocModel Query() {
        return this.Query(false);
    }

    /// <summary>
    /// 新增一资料行，通用
    /// </summary>
    /// <param name="argDbName">Data Basec名稱</param>
    /// <param name="argTableName">待新增的TABLE</param>
    /// <param name="argFieldList">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功新增行数</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 指定DB，TABLE，Field,值
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///		int count = dalProject.AddRow("EQ","PROJECT","PROJECTID,PROJECTNAME","'1','test name'");
    ///	</code>
    ///	</example>
    /// </remarks>
    public int AddRow(String argDbName, String argTableName, String argFieldList, String argValueList) {
        int ret = 0;

        ret = DbHelper.AddRow(argDbName, argTableName, argFieldList, argValueList);
        return ret;

    }

    public int AddRow(String argTableName, String argFieldList, String argValueList) {
        return AddRow(this.getDatabaseName(), argTableName, argFieldList, argValueList);
    }
    /// <summary>
    /// 新增一资料行，默认当前TABLE
    /// </summary>
    /// <param name="argFieldList">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功新增行数</returns>

    public int AddRow(String argFieldList, String argValueList) {
        return this.AddRow(this.getTableName(), argFieldList, argValueList);
    }

    /// <summary>
    /// 新增一资料行，默认当前TABLE
    /// </summary>
    /// <param name="argData">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功新增行数</returns>
    public int AddRow(XmlDocModel argData, XmlDocModel argConf) throws SQLException {
        return AddRow(argData);
    }
    /// <summary>
    /// 新增一资料行，默认当前TABLE
    /// </summary>
    /// <param name="argFieldList">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功新增行数</returns>

    public int AddRow(XmlDocModel argData) throws SQLException {

        int i = 0;

        try {
            List<org.dom4j.Element> list = argData.getDocument().getRootElement().selectNodes("records/record");

            for (int j = 0; j < list.size(); j++) {
                Element node = (Element) list.get(j);
                i = AddRow(node);
            }
        } catch (SQLException exSQL) {
            exSQL.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return i;
    }

    public int AddRow(Element argData) throws SQLException {

        int i = 0;
        String tp = "", iskey = "", size = "", defValue = "", val = "";
        StringBuilder sbFields = new StringBuilder();
        StringBuilder sbValues = new StringBuilder();

        Element elSchema, elParam;

        Element fldParam = argData;

        XmlDocModel schema = this.getTableSchema();
        //xconfig
        Element rootSchema = schema.getDocument().getRootElement();
        //find table name node
        Element fldSchdma = rootSchema.element(this.getTableName().toLowerCase());
        //MyLogger.Write(fldSchdma.asXML());
        //for every cond node
        for (Iterator it = fldSchdma.elementIterator(); it.hasNext();) {
            elSchema = (Element) it.next();
            //Element xdCol = argConf.SelectSingleNode("col[@id='" + el.getName() + "']");
            elParam = fldParam.element(elSchema.getName());
            //已有值
            if (elParam != null) {
                val = elParam.getText().replace("'", "''");

                //一欄不需加","
                if (i > 0) {
                    sbFields.append(",");
                    sbValues.append(",");
                }
                i++;

                sbFields.append(elSchema.getName());

                tp = (elSchema.attribute("d") == null) ? "0" : elSchema.attribute("d").getValue();
                iskey = (elSchema.attribute("n") == null) ? "0" : elSchema.attribute("d").getValue() == "false" ? "1" : "0";
                defValue = (elSchema.attribute("k") == null) ? "0" : elSchema.attribute("k").getValue();
                size = (elSchema.attribute("s") == null) ? "0" : elSchema.attribute("s").getValue();
                //temp
                switch (Integer.parseInt(tp)) {
                    case 0://"system.String":

                        if (val == "") {
                            sbValues.append("null");
                        } else {
                            sbValues.append("'");
                            sbValues.append(val);//update 2009-09-14 by lucky
                            sbValues.append("'");
                        }

                        break;
                    case 1://"system.decimal":
                        sbValues.append(elParam.getText().trim() == "" ? "null" : elParam.getText().trim());

                        break;
                    case 2://"system.datetime":
                        sbValues.append((elParam.getText().trim() == "") ? "getdate()" : elParam.getText().trim());
                        break;
                    case 3://"blob":
                        sbValues.append(elParam.getText().trim() == "" ? "null" : elParam.getText().trim());
                        break;
                    default:
                        if (val == "") {
                            sbValues.append("null");
                        } else {
                            sbValues.append("'");
                            sbValues.append(val);//update 2009-09-14 by lucky
                            sbValues.append("'");
                        }
                        break;
                }


            } else {
                //設定PCM
                //SetPCM();
            }
        }


        return this.AddRow(this.getTableName(), sbFields.toString(), sbValues.toString());
    }
    /// <summary>
    /// 新增一资料行，默认当前TABLE
    /// </summary>
    /// <param name="argFieldList">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功新增行数</returns>
//    public int AddRow(StringDictionary argData)
//    {
//
//    Element xnData = Convert2.ToElement(argData, this.TableSchema.ChildNodes[1].FirstChild);
//    return this.AddRow(xnData);
//    }

    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
    /// <param name="argDbName">DataBase 名稱</param>
    /// <param name="argTableName">栏位清单</param>
    /// <param name="argWhere">值清单</param>
    /// <param name="argIsKey">區分多載用</param>
    /// <returns>成功刪除筆数</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 指定DB，TABLE，Field,值
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///		int count = dalProject.AddRow("EQ","PROJECT","PROJECTID,PROJECTNAME","'1','test name'");
    ///	</code>
    ///	</example>
    /// </remarks>
    public int DeleteRow(String argDbName, String argTableName, String argWhere, boolean argIsKey) {
        int ret = 0;
        ret = DbHelper.DeleteRow(argDbName, argTableName, argWhere, argIsKey);
        return ret;
    }

    public int DeleteRow(String argTableName, String argWhere, boolean argIsKey) {
        return DeleteRow(this.getDatabaseName(), argTableName, argWhere, argIsKey);
    }
    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>

    public int DeleteRow(String argWhere, boolean argKey) {
        return this.DeleteRow(this.getTableName(), argWhere, argKey);
    }

    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
    public int DeleteRow(String argFieldName, String argFieldValue) {
        String where = String.format("{0} = {1}", argFieldName, argFieldValue);
        return this.DeleteRow(this.getTableName(), where, true);
    }

    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
//    public int DeleteRow(StringDictionary argData)
//    {
//
//    Element xnData = Convert2.ToElement(argData, this.TableSchema.ChildNodes[1].FirstChild);
//    return this.DeleteRow(xnData);
//    }
    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
    public int DeleteRow(String argKeyFieldValue) {
        String where = String.format("%1$s = %2$s", this.getKeyName(), argKeyFieldValue);
        return this.DeleteRow(this.getTableName(), where, true);
    }

    public int DeleteRow(XmlDocModel argData) throws SQLException {

        int i = 0;

        try {
            List<org.dom4j.Element> list = argData.getDocument().getRootElement().selectNodes("records/record");

            for (int j = 0; j < list.size(); j++) {
                Element node = (Element) list.get(j);
                i = DeleteRow(node);
            }
        } catch (SQLException exSQL) {
            exSQL.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return i;
    }

    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
    public int DeleteRow(Element argData, Element argConf) throws SQLException {
        int j = 0, i = 0;
        String tp = "", iskey = "";
        String firstName = null;
        String firstValue = null;

        Element elSchema, elParam;
        StringBuilder sbWhere = new StringBuilder();
        XmlDocModel schema = this.getTableSchema();
        //xconfig
        Element rootSchema = schema.getDocument().getRootElement();
        //find table name node
        Element fldSchdma = rootSchema.element(this.getTableName().toLowerCase());

        for (Iterator it = argData.elementIterator(); it.hasNext();) {
            elParam = (Element) it.next();
            if (i == 0) {
                firstName = elParam.getName();
                firstValue = elParam.getText();
            }
            i++;

            elSchema = elParam.element(elParam.getName());
            //傳過來的條件欄在Schema中存在
            if (elSchema != null) {


                tp = (elSchema.attribute("d") == null) ? "0" : elSchema.attributeValue("d");
                iskey = (elSchema.attribute("k") == null) ? "0" : (elSchema.attributeValue("k") == "false") ? "1" : "0";
                if (iskey == "1") {
                    if (j > 0) {
                        sbWhere.append(" and ");
                    }
                    sbWhere.append(elParam.getName()).append("='").append(elParam.getText()).append("'");
                    j++;
                }
            } else {
                //設定PCM
                //SetPCM();
            }
        }
        //當沒有產生條件時，default為第一個欄位為KEY的條件
        if (sbWhere.length() == 0) {
            sbWhere.append(firstName).append("='").append(firstValue).append("'");
        }

        return this.DeleteRow(sbWhere.toString(), false);
    }
    // <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>

    public int DeleteRow(Element argData) throws SQLException {
        return this.DeleteRow(argData, null);
    }

    /// <summary>
    /// 修改一资料行，通用
    /// </summary>
    /// <param name="argDbName">Data Basec名稱</param>
    /// <param name="argTableName">待新增的TABLE</param>
    /// <param name="argFieldList">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功修改行数</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 指定DB，TABLE，Field,值
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///		int count = dalProject.UpdateRow("EQ","PROJECT","PROJECTNAME='testname'","projectid='001'");
    ///	</code>
    ///	</example>
    /// </remarks>
    public int UpdateRow(String argDbName, String argTableName, String argSet, String argWhere) {
        int ret = 0;
        ret = DbHelper.UpdateRow(argDbName, argTableName, argSet, argWhere);
        return ret;
    }

    public int UpdateRow(String argTableName, String argSet, String argWhere) {
        return UpdateRow(this.getDatabaseName(), argTableName, argSet, argWhere);
    }

    /// <summary>
    /// 修改一资料行依KEY栏
    /// </summary>
    public int UpdateRow(String argSet, String argWhere) {
        return this.UpdateRow(this.getTableName(), argSet, argWhere);
    }

    /// <summary>
    /// 修改一资料行依KEY栏
    /// </summary>
    public int UpdateRow(Element argData, Element argConf) throws SQLException {

        return this.UpdateRow(argData);
    }

    public int UpdateRow(XmlDocModel argData) throws SQLException {

        int i = 0;

        try {
            List<org.dom4j.Element> list = argData.getDocument().getRootElement().selectNodes("records/record");

            for (int j = 0; j < list.size(); j++) {
                Element node = (Element) list.get(j);
                i = UpdateRow(node);
            }
        } catch (SQLException exSQL) {
            exSQL.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return i;
    }
    /// <summary>
    /// 修改一资料行,依KEY栏
    /// </summary>
    /// <param name="argData"></param>
    /// <returns></returns>

    public int UpdateRow(Element argData) throws SQLException {
        int i = 0, j = 0, m = 0;
        String tp = "", iskey = "", size = "", defValue = "", val = "";
        //String xpath = "[@ischanged='2']";
        String firstName = "";
        String firstValue = "";

        StringBuilder sbSet = new StringBuilder();
        StringBuilder sbWhere = new StringBuilder();
        Element elSchema, elParam;

        Element fldParam = argData;

        XmlDocModel schema = this.getTableSchema();
        //xconfig
        Element rootSchema = schema.getDocument().getRootElement();
        //find table name node
        Element fldSchdma = rootSchema.element(this.getTableName().toLowerCase());

        //for every cond node
        for (Iterator it = fldSchdma.elementIterator(); it.hasNext();) {

            //Element xdCol = argData.SelectSingleNode(el.getName());
            elSchema = (Element) it.next();
            //Element xdCol = argConf.SelectSingleNode("col[@id='" + el.getName() + "']");
            elParam = fldParam.element(elSchema.getName());
            //已有設定
            if (elParam != null) {
                if (m == 0) {
                    firstName = fldParam.getName();
                    firstValue = fldParam.getText();
                    m++;
                }
                //sbFields.append(el.getName());
                //sbSet.append(el.getName()).append("=").append("value").append(",");
                tp = (elSchema.attribute("d") == null) ? "0" : elSchema.attribute("d").getValue();
                iskey = (elSchema.attribute("n") == null) ? "0" : elSchema.attribute("n").getValue().equals("false") ? "1" : "0";
                defValue = (elSchema.attribute("k") == null) ? "0" : elSchema.attribute("k").getValue();
                size = (elSchema.attribute("s") == null) ? "0" : elSchema.attribute("s").getValue();

                if (iskey.equals("1")) {
                    if (j > 0) {
                        sbWhere.append(" and ");
                    }
                    sbWhere.append(elSchema.getName()).append("='").append(elParam.getText()).append("'");
                    j++;
                } else {
                    //值被改變
                    if (elParam.attribute("ischanged") != null && elParam.attribute("ischanged").getValue().equals("2")) {
                        val = elParam.getText().replace("'", "''");
                        //第一次不需加","
                        if (i == 0) {
                        } else {
                            sbSet.append(",");
                        }
                        i++;

                        sbSet.append(elSchema.getName()).append("=");

                        //temp
                        switch (Integer.parseInt(tp)) {
                            case 0://"system.String":

                                sbSet.append("'");
                                sbSet.append(val);
                                sbSet.append("'");
                                break;
                            case 1://"system.decimal":
                                sbSet.append(elParam.getText().trim().equals("") ? "null" : elParam.getText().trim());

                                break;
                            case 2://"system.datetime":
                                sbSet.append(elParam.getText().trim().equals("") ? "getdate()" : elParam.getText().trim());
                                break;
                            case 3://"blob":
                                sbSet.append(elParam.getText().trim().equals("") ? "null" : elParam.getText().trim());
                                break;
                            default:
                                sbSet.append("'");
                                sbSet.append(val);
                                sbSet.append("'");

                                break;
                        }
                    }
                }
            } else {
                //設定PCM
                //SetPCM();
            }
        }
        //當沒有產生條件時，default為第一個欄位為KEY的條件
        //MyLogger.Write(sbSet.toString());

        if (sbWhere.length() == 0) {
            sbWhere.append(firstName).append("='").append(firstValue).append("'");
        }

        return this.UpdateRow(this.getTableName(), sbSet.toString(), sbWhere.toString());
        //return 0;
    }

/// <summary>
/// 新增一资料行，默认当前TABLE
/// </summary>
/// <param name="argFieldList">栏位清单</param>
/// <param name="argValueList">值清单</param>
/// <returns>成功新增行数</returns>
//    public int UpdateRow(StringDictionary argData)
//    {
//
//    Element xnData = Convert2.ToElement(argData, this.TableSchema.ChildNodes[1].FirstChild);
//    return this.UpdateRow(xnData);
//    }
    public boolean IsExist(String argField, String argValue) {
        boolean ret = false;
        //int count = 0;
        String sql = "SELECT {1} FROM {0} WHERE {1} = '{2}'";
        sql = String.format(sql, this.getTableName(), argField, argValue);
        XmlDocModel xData = DbHelper.ExecuteQuery(sql);

        if (xData!=null) {
            ret = true;
        }
        return ret;

    }
    
    /// <summary>
    /// 判斷是否存在數據
    /// </summary>
    /// <param name="Cond">條件</param>
    /// <param name="retDate" 返回數據>
    /// <returns>欄值，當沒有找到，返回null</returns>
//    public boolean IsExist(String Cond,out XmlDocModel retDate)
//    {
//    boolean ret = false;
//    //int count = 0;
//    String sql = "SELECT * FROM {0} WHERE {1}";
//    sql = String.format(sql, this.getTableName(), Cond);
//    retDate = DbHelper.ExecuteQuery(sql);
//
//    if (retDate.ChildNodes[1].FirstChild.HasChildNodes)
//    {
//    ret = true;
//    }
//    return ret;
//
//    }

    /*
    /// <summary>
    /// 取得單一欄值
    /// </summary>
    /// <param name="argField">欄名稱</param>
    /// <param name="argCond">條件</param>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public String GetValue(String argField, String argCond)
    {
    return  DbHelper.GetValue(this.DatabaseName, this.getDataSourceName(), argField, argCond);
    }

    /// <summary>
    /// 取得某一欄位最大值
    /// </summary>
    /// <param name="argField">欄名稱</param>
    /// <param name="argCond">條件</param>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public String GetMaxValue(String argField, String argCond)
    {
    return DbHelper.GetMaxValue(this.DatabaseName, this.getDataSourceName(), argField, argCond);
    }
    /// <summary>
    /// 取得某一欄位最大值
    /// </summary>
    /// <param name="argField">欄名稱</param>
    /// <param name="argCond">條件</param>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public String GetMaxValue(String argField, String argCondName,
    String argCondValue)
    {
    return DbHelper.GetMaxValue(this.DatabaseName, this.getDataSourceName(), argField,argCondName,argCondValue);
    }
     *
     * */
}

