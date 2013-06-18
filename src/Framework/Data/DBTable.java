package Framework.Data;

import java.util.Iterator;
import java.util.concurrent.locks.Condition;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import Framework.Xml.*;

/**
 * 
 * 
 * 项目名称：Framework  
 * 类名称：DBTable  
 * 类描述：通用資料對象類  
 * @author lucky  
 * 创建时间：Jun 13, 2013 9:31:28 AM  
 * 修改人：lucky
 * 修改时间：Jun 13, 2013 9:31:28 AM  
 * 修改备注：  
 * @version  
 *
 */
public class DBTable {

    String tableName;
    XmlDocModel tableSchema;
    XmlDocModel viewSchema;
    String currentKeyValue;
    String keyName;
    String keyValue;
    String nextKeyValue;
    String databaseName;
    String dataSourceName;
    int dataBaseType;


    /// <summary>
    /// 表名稱
    /// </summary>
	public String getTableName() {
		return tableName;
	}
    public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/// <summary>
    /// 表結構
    /// </summary>
	public XmlDocModel getTableSchema()
    {
		this.tableSchema = DALHelper.GetSchema(this.getDatabaseName(),this.getDataSourceName());
        return this.tableSchema;
    }
    public void setTableSchema(XmlDocModel tableSchema) {
		this.tableSchema = tableSchema;
	}
	

    /// <summary>
    /// 表結構
    /// </summary>
    
	public XmlDocModel getViewSchema()
    {
            this.viewSchema = DALHelper.GetSchema(this.getDataSourceName(),this.getDatabaseName());
            return this.viewSchema;
    }
    public void setViewSchema(XmlDocModel viewSchema) {
		this.viewSchema = viewSchema;
	}
    
    /// <summary>
    /// 表當前Key欄值，對單Key欄表有效
    /// </summary>
    public String getCurrentKeyValue()
    {
            return this.currentKeyValue;
    }
    
    public void getCurrentKeyValue(String tableName) {
		this.tableName = tableName;
	}
    
    /// <summary>
    /// 表欄下一值，對單Key欄表有效，在新增時可直接依次屬性得到Key欄的新值。
    /// </summary>
    public String getNextKeyValue() {
		return nextKeyValue;
	}
	public void setNextKeyValue(String nextKeyValue) {
		this.nextKeyValue = nextKeyValue;
	}
    
    /// <summary>
    /// Database名稱，以此名稱可取得Database鏈接
    /// </summary>
    public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
    

    /// <summary>
    /// 表資料查詢時應用的View名稱，當資料需從多個表中關聯時用，默認為表名稱。
    /// </summary>
    public String getDataSourceName() {
		return dataSourceName;
	}
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

    /// <summary>
    /// 表資料查詢時應用的View名稱，當資料需從多個表中關聯時用，默認為表名稱。
    /// </summary>

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
    public String getKeyValue() {
		return keyValue;
	}
    
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
    /// <summary>
    /// 表Key欄位名稱，對單Key欄表有效
    /// </summary>
    public String getKeyName() {
		return keyName;
		/*
        String ret = "";
        XmlDocModel xData = DALHelper.GetSchema(this.getDatabaseName(), this.getTableName());
        //org.dom4j.Node key = xData.getDocument().getRootElement().selectSingleNode("xconfig");
        
        //XmlNode key = xData.ChildNodes[1].FirstChild.FirstChild;
        if (key.attribute("n")Value == "false")
        {
            ret = key.Name;
        }else
        {
            //throw new Framework.MyException.PFException("该栏可能不是主键：{0}", new String[] { key.Name });
        }
        return ret;
		 */  
	}
    





	
    /// <summary>
    /// Database类别，如SqlServer，Sqlite，Oracle，DB2,MySql等
    /// </summary>
	public  dbType getDatabaseType()
    {
            dbType ret = dbType.sqlserver;
            String provider = Global.AppConfig("log_url").toLowerCase();
            
            if (provider.indexOf("sqlite")>0){
                ret = dbType.sqlite;
            }else if (provider.indexOf("oracle")>0) {
            	ret = dbType.oracle;
			}else if (provider.indexOf("sqlserver")>0) {
				ret = dbType.sqlserver;
			}else if (provider.indexOf("mysql")>0) {
				ret = dbType.mysql;
			}else if (provider.indexOf("db2")>0) {
				ret = dbType.db2;
			}else if (provider.indexOf("sybase")>0) {
				ret = dbType.sybase;
			}else if (provider.indexOf("Informix")>0) {
				ret = dbType.Informix;
			}else if (provider.indexOf("postgresql")>0) {
				ret = dbType.postgresql;
			}else if (provider.indexOf("access")>0) {
				ret = dbType.access;
			}

            return ret;
    }

	/**
	 * 
	 * 功能概述：取得指定key值行的指定字段的值
	 * @param argFieldName 單一字段名稱
	 * @param argKeyValue 查找的KEY值，以此值在表的主鍵欄查找記錄
	 * @return
	 * @author lucky  
	 * 创建时间：Jun 17, 2013 2:41:33 PM  
	 * 修改人：lucky
	 * 修改时间：Jun 17, 2013 2:41:33 PM  
	 * 修改备注：  
	 * @version  
	 *
	 */
    public String GetFields(String argFieldName,String argKeyValue)
    {
        return GetValue(argFieldName,argKeyValue);
    }
    
    /**
     * 
     * 功能概述： 取得指定key值行的指定字段的值 
     * @param argFieldName 單一字段名稱
     * @param argKeyValue 查找的KEY值，以此值在表的主鍵欄查找記錄
     * @return 
     * @author lucky  
     * 创建时间：Jun 17, 2013 2:44:31 PM  
     * 修改人：lucky
     * 修改时间：Jun 17, 2013 2:44:31 PM  
     * 修改备注：  
     * @version  
     *
     */
    public String GetValue(String argFieldName,String argKeyValue)
    {
        String ret = null;
        String  cond = this.getKeyName() + "= '" + argKeyValue + "'"; 
        ret = DALHelper.GetValue(this.getDatabaseName(),this.getTableName(),argFieldName, cond);
        return ret;

    }    
    /*

    //#region Query

    /// <summary>
    /// 傳條件或Sql語句進行查詢
    /// </summary>
    /// <param name="s_cond">條件資料包，xml 結構</param>
    /// <returns>将资料封装成JSON格式返回.</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 调用Query重载
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Rayd      2012-11-07  Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String cond = String.format("<projectid>{0}</projectid>",new String[]{"1"});
    ///		XmlDocModel xProject = Query(cond);
    ///	</code>
    ///	</example>
    /// </remarks>
    public  DataSet Query(String s_condorsql)
    {
        String dbName = this.getDatabaseName();
        String dataSourceName = this.DataSourceName;
        DataSet ret = null;

        //直接傳SQL
        if (s_condorsql.ToLower().IndexOf("select ") == 0)
        {
            ret = ExecuteDataSet(dbName, s_condorsql);
        }
        else
        {
            //invoke Query
            //ret = this.Query("*", s_condorsql);
            dbName = this.getDatabaseName();
            ret = Query(dbName, this.DataSourceName, "*", s_condorsql);

        }
        return ret;
    }




    /// <summary>
    /// 取得資料，Database+Sql 或 Fields+Conds（xml）
    /// </summary>
    /// <param name="s_arg1">DataBase or Fields</param>
    /// <param name="s_arg1">SQL語句 or 條件</param>
    /// <returns>查詢結果資料</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 1. Database Base Name + SQL s_arg1
    /// 2. Fields+Conds（xml）
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Rayd      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String cond = String.format("projectid={0}",new String[]{"1"});
    ///		XmlDocModel xProject = Query("PROJECTID,PROJECTNAME",cond);
    ///	</code>
    ///	</example>
    /// </remarks>
    public  DataSet Query(String s_arg1, String s_arg2)
    {
        DataSet ret = null;
        String sql="",dbName=this.getDatabaseName();
        //第二個參數傳入的是Sql:arg1=DbName,arg2=sql
        if (s_arg2.Trim().ToLower().IndexOf("select ") == 0)
        {
            //database 
            dbName = s_arg1;
            sql = s_arg2;
            ret = ExecuteDataSet(dbName, sql);
        }
        else //传fileds and conds
        {
            dbName = this.getDatabaseName();
            ret = Query(dbName, this.DataSourceName, s_arg1, s_arg2);
        }

        
        return ret;
    }
	

    
    /// <summary>
    /// 指定数据源Datasource Name(Table or View),字段fields(xml format),条件conds(xml format)
    /// </summary>
    /// <param name="s_dsn">指定数据源Datasource Name(Table or View)</param>
    /// <param name="s_fields">字段fields(xml format)</param>
    /// <param name="s_conds">条件conds(xml format)</param>
    /// <returns></returns>
    public  DataSet Query(String s_dsn, String xs_fields, String xs_conds)
    {
        return Query(this.getDatabaseName(), s_dsn, xs_fields, xs_conds);
    }


    public  DataSet Query(String s_dbname, String s_dsn, String s_fields, String s_conds)
    {
        DataSet ret = null;
        String sql = "SELECT {0} FROM {1} WHERE {2}"; ;

        //为xml格式字段与条件参数
        if (s_conds.Trim().IndexOf('<') == 0 && s_conds.Trim().IndexOf("</") > 0)
        {
            XmlDocModel xField = new XmlDocModel("<field>" + s_fields + "</field>");
            XmlDocModel xCond = new XmlDocModel("<cond>" + s_conds + "</cond>");
            String[] fw = MakeFieldAndWhere(xField, xCond);
            sql = String.Format(sql, fw[0], this.DataSourceName, fw[1]);
        }
        else
        {
            sql = String.Format(sql, s_fields, this.DataSourceName, s_conds);
        }
        ret = ExecuteDataSet(s_dbname, sql);

        return ret;

    }
*/
    
    //#region QueryXml
    
    
    public  XmlDocModel QueryXml(String s_condorsql)
    {
        XmlDocModel ret = null;
        ret = DALHelper.Query(s_condorsql);
        return ret;
    }

    public  XmlDocModel QueryXml(String s_fields, String s_cond)
    {
        XmlDocModel ret = null;
        ret = DALHelper.Query(this.getDatabaseName(), this.getDataSourceName(), s_fields, s_cond);
        return ret;
    }


    //#endregion

    //#region QueryJson
    public  String QueryJson(String s_condorsql)
    {
        String ret = null;
        XmlDocModel xData = QueryXml(s_condorsql);
        
        ret = JsonHelper.xmltoJson(xData.toString());
        
        return ret;
    }

    public  String QueryJson(String s_fields, String s_cond)
    {
        String ret = null;

        XmlDocModel xData = QueryXml(s_fields, s_cond);
        
        ret  = JsonHelper.xmltoJson(xData.toString());

        return ret;
    }
    /*
    public  String QueryJson(String argDbName, String argDataSourceName, String argSql)
    {
        String ret = null;

        DataSet ds = Query(argDbName, argDataSourceName, argSql);
        ret = Data.JsonHelper.DataSetToJson(ds);
        

        return ret;
    }
*/
    
    //#endregion

    //#endregion

    //#region AddRow
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
    /// Rayd      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///		int count = dalProject.AddRow("EQ","PROJECT","PROJECTID,PROJECTNAME","'1','test name'");
    ///	</code>
    ///	</example>
    /// </remarks>
    public  int AddRow(String argDbName, String argTableName, String argFieldList, String argValueList)
    {
        int ret = 0;

        ret = DALHelper.AddRow(argDbName, argTableName, argFieldList, argValueList);
        return ret;

    }

    public  int AddRow(String argTableName, String argFieldList, String argValueList)
    {
        return AddRow(this.getDatabaseName(), argTableName, argFieldList, argValueList);
    }
    /// <summary>
    /// 新增一资料行，默认当前TABLE
    /// </summary>
    /// <param name="argFieldList">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功新增行数</returns>
    public  int AddRow(String argFieldList, String argValueList)
    {
        return this.AddRow(this.getTableName(), argFieldList, argValueList);
    }

    /// <summary>
    /// 新增一资料行，默认当前TABLE
    /// </summary>
    /// <param name="argData">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功新增行数</returns>
    public  int AddRow(Node argData, Node argConf)
    {
        return AddRow(argData);
    }
    /// <summary>
    /// 新增一资料行，默认当前TABLE
    /// </summary>
    /// <param name="argFieldList">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功新增行数</returns>
    public  int AddRow(org.dom4j.Node argData)
    {

        int i = 0;
        String tp = "", iskey = "", size = "", defValue = "", val = "";
        StringBuilder sbFields = new StringBuilder();
        StringBuilder sbValues = new StringBuilder();
        Element el;
        
		Document doc = this.getTableSchema().getDocument();
		Element root = doc.getRootElement();
		


		for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
        //this.TableSchema
        //foreach (XmlNode xRow in argData.ChildNodes)
        //foreach (XmlNode xRow in this.getTableSchema().ChildNodes[1].FirstChild.ChildNodes)
        //{
			el = (Element) it.next();
            //XmlNode xdCol = argConf.SelectSingleNode("col[@id='" + xRow.Name + "']");
            Node xdCol = argData.SelectSingleNode(xRow.Name.ToLower());

            //已有值
            if (xdCol != null)
            {
                val = xdCol.InnerText.Replace("'", "''");

                //一欄不需加","
                if (i > 0)
                {
                    sbFields.Append(",");
                    sbValues.Append(",");
                }
                i++;

                sbFields.Append(xRow.Name);

                tp = (xRow.Attributes["d"] == null) ? "system.String" : xRow.Attributes["d"].Value;
                iskey = (xRow.Attributes["n"] == null) ? "0" : (xRow.Attributes["n"].Value == "false") ? "1" : "0";
                defValue = (xRow.Attributes["k"] == null) ? "0" : xRow.Attributes["k"].Value;
                size = (xRow.Attributes["s"] == null) ? "0" : xRow.Attributes["s"].Value;
                //temp
                switch (tp.ToLower())
                {
                    case "system.String":

                        if (val == "")
                        {
                            sbValues.Append("null");
                        }
                        else
                        {
                            sbValues.Append("'");
                            sbValues.Append(val);//update 2009-09-14 by lucky
                            sbValues.Append("'");
                        }

                        break;
                    case "system.decimal":
                        sbValues.Append(xdCol.InnerText.Trim() == String.Empty ? "null" : xdCol.InnerText.Trim());

                        break;
                    case "system.datetime":
                        switch (this.DatabaseType)
                        {
                            case (int)dbType.sqlserver:
                                sbValues.Append(String.IsNullOrEmpty(xdCol.InnerText.Trim()) ? "getdate()" : "'"+xdCol.InnerText.Trim()+"'");
                                break;

                            case (int)dbType.sqlite:
                                sbValues.Append(String.IsNullOrEmpty(xdCol.InnerText.Trim()) ? "CURRENT_TIMESTAMP" : "'" + xdCol.InnerText.Trim() + "'");

                                break;
                        }

                        break;
                    case "blob":
                        sbValues.Append(xdCol.InnerText.Trim() == String.Empty ? "null" : xdCol.InnerText.Trim());
                        break;
                    default:
                        if (val == "")
                        {
                            sbValues.Append("null");
                        }
                        else
                        {
                            sbValues.Append("'");
                            sbValues.Append(val);//update 2009-09-14 by lucky
                            sbValues.Append("'");
                        }
                        break;
                }


            }
            else
            {
                //設定PCM
                //SetPCM();
            }

        }
        
        if (sbFields.ToString().IndexOf("adder") == -1 
            &&this.TableSchema.ChildNodes[1].FirstChild.SelectSingleNode("adder")!=null)
        {
            sbFields.Append(" , ADDER");
            sbValues.Append(" ,'" + Manuan.eBridge.Framework.Data.Global.AccountID + "'");
        }
        return this.AddRow(this.getTableName(), sbFields.ToString(), sbValues.ToString());
    }
    /// <summary>
    /// 新增一资料行，默认当前TABLE
    /// </summary>
    /// <param name="argFieldList">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功新增行数</returns>
    public  int AddRow(StringDictionary argData)
    {

        XmlNode xnData = Convert2.ToXmlNode(argData, this.TableSchema.ChildNodes[1].FirstChild);
        return this.AddRow(xnData);
    }

    //#endregion
/*
    //#region DeleteRow


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
    /// Rayd      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///		int count = dalProject.AddRow("EQ","PROJECT","PROJECTID,PROJECTNAME","'1','test name'");
    ///	</code>
    ///	</example>
    /// </remarks>
    public  int DeleteRow(String argDbName, String argTableName, String argWhere, bool argIsKey)
    {
        int ret = 0;
        ret = DALHelper.DeleteRow(argDbName, argTableName, argWhere, argIsKey);
        return ret;
    }

    public int DeleteRow(String argTableName, String argWhere, bool argIsKey)
    {
        return DeleteRow(this.getDatabaseName(), argTableName, argWhere, argIsKey);
    }
    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
    public  int DeleteRow(String argWhere, bool argKey)
    {
        return this.DeleteRow(this.getTableName(), argWhere, argKey);
    }

    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
    public  int DeleteRow(String argFieldName, String argFieldValue)
    {
        String where = String.Format("{0} = {1}", argFieldName, argFieldValue);
        return this.DeleteRow(this.getTableName(), where, true);
    }

    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
    public  int DeleteRow(StringDictionary argData)
    {

        XmlNode xnData = Convert2.ToXmlNode(argData, this.TableSchema.ChildNodes[1].FirstChild);
        return this.DeleteRow(xnData);
    }
    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
    public  int DeleteRow(String argKeyFieldValue)
    {
        String where = String.Format("{0} = {1}", this.KeyName, argKeyFieldValue);
        return this.DeleteRow(this.getTableName(), where, true);
    }

    /// <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
    public  int DeleteRow(XmlNode argData, XmlNode argConf)
    {
        int j = 0;
        String tp = "", iskey = "";
        String firstName = argData.FirstChild.Name;
        String firstValue = argData.FirstChild.InnerText;

        StringBuilder sbWhere = new StringBuilder();

        foreach (XmlNode xRow in argData.ChildNodes)
        {

            XmlNode xdCol = argData.SelectSingleNode(xRow.Name);
            //已有設定
            if (xdCol != null)
            {
                tp = (xRow.Attributes["d"] == null) ? "system.String" : xRow.Attributes["d"].Value;
                iskey = (xRow.Attributes["n"] == null) ? "0" : (xRow.Attributes["n"].Value == "false") ? "1" : "0";
                if (iskey == "1")
                {
                    if (j > 0)
                    {
                        sbWhere.Append(" and ");
                    }
                    sbWhere.Append(xdCol.Name).Append("='").Append(xdCol.InnerText).Append("'");
                    j++;
                }
            }
            else
            {
                //設定PCM
                //SetPCM();
            }
        }
        //當沒有產生條件時，default為第一個欄位為KEY的條件
        if (sbWhere.Length == 0)
        {
            sbWhere.Append(firstName).Append("='").Append(firstValue).Append("'");
        }

        return this.DeleteRow(sbWhere.ToString(), false);
    }
    // <summary>
    /// 删除一资料行，依KEY栏
    /// </summary>
    public  int DeleteRow(XmlNode argData)
    {
        return this.DeleteRow(argData, null);
    }
    //#endregion

    //#region UpdateRow

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
    /// Rayd      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///		int count = dalProject.UpdateRow("EQ","PROJECT","PROJECTNAME='testname'","projectid='001'");
    ///	</code>
    ///	</example>
    /// </remarks>
    public  int UpdateRow(String argDbName, String argTableName, String argSet, String argWhere)
    {
        int ret = 0;
        ret = DALHelper.UpdateRow(argDbName, argTableName, argSet, argWhere);
        return ret;
    }

    public  int UpdateRow(String argTableName, String argSet, String argWhere)
    {
        return UpdateRow(this.getDatabaseName(), argTableName, argSet, argWhere);
    }

    /// <summary>
    /// 修改一资料行依KEY栏
    /// </summary>
    public  int UpdateRow(String argSet, String argWhere)
    {
        return this.UpdateRow(this.getTableName(), argSet, argWhere);
    }

    /// <summary>
    /// 修改一资料行依KEY栏
    /// </summary>
    public  int UpdateRow(XmlNode argData, XmlNode argConf)
    {

        return this.UpdateRow(argData);
    }

    /// <summary>
    /// 修改一资料行,依KEY栏
    /// </summary>
    /// <param name="argData"></param>
    /// <returns></returns>
    public  int UpdateRow(XmlNode argData)
    {
        int i = 0, j = 0;
        String tp = "", iskey = "", size = "", defValue = "", val = "";
        //String xpath = "[@ischanged='2']";
        String firstName = argData.FirstChild.Name;
        String firstValue = argData.FirstChild.InnerText;

        StringBuilder sbSet = new StringBuilder();
        StringBuilder sbWhere = new StringBuilder();
        //XmlNodeList changeds = argData.SelectNodes(xpath);
        //foreach (XmlNode xRow in argData.ChildNodes)
        foreach (XmlNode xRow in this.TableSchema.ChildNodes[1].FirstChild.ChildNodes)
        {
            XmlNode xdCol = argData.SelectSingleNode(xRow.Name);

            //已有設定
            if (xdCol != null)
            {
                //sbFields.Append(xRow.Name);
                //sbSet.Append(xRow.Name).Append("=").Append("value").Append(",");

                tp = (xRow.Attributes["d"] == null) ? "system.String" : xRow.Attributes["d"].Value;
                iskey = (xRow.Attributes["n"] == null) ? "0" : (xRow.Attributes["n"].Value == "false") ? "1" : "0";
                defValue = (xRow.Attributes["k"] == null) ? "0" : xRow.Attributes["k"].Value;
                size = (xRow.Attributes["s"] == null) ? "0" : xRow.Attributes["s"].Value;
                if (iskey == "1")
                {
                    if (j > 0)
                    {
                        sbWhere.Append(" and ");
                    }
                    sbWhere.Append(xdCol.Name).Append("='").Append(xdCol.InnerText).Append("'");
                    j++;
                }
                else
                {
                    //值被改變
                    //if (xdCol.Attributes["ischanged"] != null && xdCol.Attributes["ischanged"].Value == "2")
                    //{
                        val = xdCol.InnerText.Replace("'", "''");
                        //第一次不需加","
                        if (i == 0)
                        {

                        }
                        else
                        {
                            sbSet.Append(",");
                        }
                        i++;

                        sbSet.Append(xRow.Name).Append("=");

                        //temp
                        switch (tp.ToLower())
                        {
                            case "system.String":

                                sbSet.Append("'");
                                sbSet.Append(val);
                                sbSet.Append("'");
                                break;
                            case "system.decimal":
                                sbSet.Append(xdCol.InnerText.Trim() == String.Empty ? "null" : xdCol.InnerText.Trim());

                                break;
                            case "system.datetime":
                                switch (this.DatabaseType)
                                {
                                    case (int)dbType.sqlserver:
                                        sbSet.Append(String.IsNullOrEmpty(xdCol.InnerText.Trim()) ? "getdate()" : "'" + xdCol.InnerText.Trim() +"'");
                                        break;

                                    case (int)dbType.sqlite:
                                        sbSet.Append(String.IsNullOrEmpty(xdCol.InnerText.Trim()) ? "CURRENT_TIMESTAMP" : "'" + xdCol.InnerText.Trim() + "'");

                                        break;
                                }
                                break;
                            case "blob":
                                sbSet.Append(xdCol.InnerText.Trim() == String.Empty ? "null" : xdCol.InnerText.Trim());
                                break;
                            default:
                                sbSet.Append("'");
                                sbSet.Append(val);
                                sbSet.Append("'");

                                break;
                        }
                }
            }
            else
            {
                //設定PCM
                //SetPCM();
            }
        }
        //當沒有產生條件時，default為第一個欄位為KEY的條件
        if (sbWhere.Length == 0)
        {
            sbWhere.Append(firstName).Append("='").Append(firstValue).Append("'");
        }
        if (!sbSet.ToString().Equals(""))
        {
            //有updater栏
            if (this.TableSchema.ChildNodes[1].FirstChild.SelectSingleNode("updater") != null 
                && sbSet.ToString().IndexOf("updater") == -1)
            {
                sbSet.Append(" , UPDATER = '" + Manuan.eBridge.Framework.Data.Global.AccountID + "'");

            }
        }
        else
        {
            return 0;
        }
        return this.UpdateRow(this.getTableName(), sbSet.ToString(), sbWhere.ToString());
    }

    /// <summary>
    /// 新增一资料行，默认当前TABLE
    /// </summary>
    /// <param name="argFieldList">栏位清单</param>
    /// <param name="argValueList">值清单</param>
    /// <returns>成功新增行数</returns>
    public  int UpdateRow(StringDictionary argData)
    {

        XmlNode xnData = Convert2.ToXmlNode(argData, this.TableSchema.ChildNodes[1].FirstChild);
        return this.UpdateRow(xnData);
    }
    //#endregion

    

    //#region Private FUNCTION


    /// <summary>
    /// 組裝Sql語句的Fields與Where段
    /// </summary>
    /// <param name="argCond">條件資料包</param>
    /// <returns>欄位(fields)與條件(where)</returns>
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
    /// Rayd      2009-08-18 Create
    /// Rayd      2009-09-09 增加第7點規格
    /// Rayd      2012-11-07 將產生SQl的fields與where子句獨立成MakeFieldAndWhere，以便共用
    /// </para>
    /// 
    /// <example>
    /// argCond:
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
    private String[] MakeFieldAndWhere(XmlDocModel x_field, XmlDocModel argCond)
    {
        String[] ret = new String[] { "*", "1=1"};
        int i = 0, j = 0, k = 0, m = 0, n = 0;
        String[] words = new String[] { };
        String sybol = " = ", word = "", tp;

        StringBuilder sbFields = new StringBuilder();
        StringBuilder sbWhere = new StringBuilder();
        StringBuilder sbOrder = new StringBuilder();
        StringBuilder sbFixWhere = new StringBuilder();
        XmlNode xnCond = argCond.ChildNodes[1].FirstChild;
        //if (xnCond.Attributes["top"] != null)
        //{
        //    sbFields.Append(" top ")
        //        .Append(xnCond.Attributes["top"].Value)
        //        .Append(" ");
        //}

        //where
        foreach (XmlNode xRow in xnCond.ChildNodes)
        {
            if (!String.IsNullOrEmpty(xRow.InnerText))
            {
                if (j != 0)
                {
                    //在PCM中可設定 AND或OR
                    if (xRow.Attributes["link"] == null)
                    {
                        sbWhere.Append(" AND ");
                    }
                    else
                    {
                        sbWhere.Append(" ");
                        sbWhere.Append(xRow.Attributes["link"].Value);
                        sbWhere.Append(" ");
                    }
                }
                j++;
                tp = (xRow.Attributes["type"] == null) ? "String" : xRow.Attributes["type"].Value;

                //支持多个关键字，用 | 好分隔
                words = xRow.InnerText.Split('|');
                sbWhere.Append("(");
                m = 0;
                foreach (String item in words)
                {
                    if (m != 0)
                    {
                        sbWhere.Append(" OR ");
                    }
                    m++;

                    sbWhere.Append("(");
                    word = item.Replace("*", "%").Trim();
                    if (word.IndexOf(',') >= 0)//IN
                    {
                        sbWhere.Append(MakeSqlIN(xRow.Name, tp, word));
                    }
                    else if (word.IndexOf('~') >= 0)//between
                    {
                        sbWhere.Append(MakeSqlBETWEEN(xRow.Name, tp, word));
                    }
                    else if (word.IndexOf('=') == 0)//等于（=）
                    {
                        sybol = " = ";
                        sbWhere.Append(xRow.Name)
                        .Append(sybol)
                        .Append((tp == "String" ? "'" : ""))
                        .Append(word.Substring(1))
                        .Append((tp == "String" ? "'" : ""));
                    }
                    else if (word.IndexOf('>') == 0)//大于（>）
                    {
                        sybol = " > ";
                        sbWhere.Append(xRow.Name)
                        .Append(sybol)
                        .Append((tp == "String" ? "'" : ""))
                        .Append(word.Substring(1))
                        .Append((tp == "String" ? "'" : ""));
                    }
                    else if (word.IndexOf('<') == 0)//小于（>）
                    {
                        sybol = " < ";
                        sbWhere.Append(xRow.Name)
                        .Append(sybol)
                        .Append((tp == "String" ? "'" : ""))
                        .Append(word.Substring(1))
                        .Append((tp == "String" ? "'" : ""));
                    }
                    else if (word.IndexOf('!') == 0)//不等于（<>）
                    {
                        sybol = " <> ";
                        sbWhere.Append(xRow.Name)
                        .Append(sybol)
                        .Append((tp == "String" ? "'" : ""))
                        .Append(word.Substring(1))
                        .Append((tp == "String" ? "'" : ""));
                    }
                    else if (word.IndexOf("<>") == 0)//不等于（<>）
                    {
                        sybol = " <> ";
                        sbWhere.Append(xRow.Name)
                        .Append(sybol)
                        .Append((tp == "String" ? "'" : ""))
                        .Append(word.Substring(2))
                        .Append((tp == "String" ? "'" : ""));
                    }

                    //if (word.IndexOf('%') >= 0 && tp == "String")//含%号且为字符型态时
                    else if (tp == "String")//字符型態用LIKE'value%'
                    {
                        sybol = " LIKE ";

                        sbWhere.Append(xRow.Name)
                        .Append(sybol)
                        .Append("'")
                        .Append(word)
                        .Append("%'");
                    }
                    else if (tp == "date")//字符型態用LIKE'value%'
                    {
                        sybol = " LIKE ";

                        sbWhere.Append(xRow.Name)
                        .Append(sybol)
                        .Append("'")
                        .Append(word)
                        .Append("%'");
                    }

                    else
                    {
                        sybol = " = ";
                        sbWhere.Append(xRow.Name)
                        .Append(sybol)
                        .Append((tp == "String" ? "'" : ""))
                        .Append(word)
                        .Append((tp == "String" ? "'" : ""));

                    }
                    sbWhere.Append(")");
                }
                sbWhere.Append(")");
            }
        }

        i = 0;
        //fields、order by、top
        //有子节点传入，可以排除 *
        //if (x_field.ChildNodes[1].FirstChild.HasChildNodes)
        if (x_field.SelectSingleNode("xconfig/field") != null && x_field.SelectSingleNode("xconfig/field").HasChildNodes && x_field.SelectSingleNode("xconfig/field").ChildNodes[0].Name!="#text")
        {
            //foreach (XmlNode xRow in x_field.ChildNodes[1].FirstChild.ChildNodes)
            foreach (XmlNode xRow in x_field.SelectSingleNode("xconfig/field").ChildNodes)
            {
                if (i != 0)
                {
                    sbFields.Append(",");
                }
                i++;
                sbFields.Append(xRow.Name);

                if (xRow.Attributes["sort"] != null)
                {
                    if (k == 0)
                    {
                        sbOrder.Append(" ORDER BY ");

                    }
                    else
                    {
                        sbOrder.Append(",");

                    }
                    k++;
                    sbOrder.Append(xRow.Name)
                        .Append(" ")
                        .Append(xRow.Attributes["sort"].Value);

                }
            }
        }
        else
        {
            sbFields.Append(" * ");
        }
        //Framework.MyLogger.Write(sbFixWhere.ToString());
        //沒有條件
        if (sbWhere.Length == 0)
        {
            sbWhere.Append(" 1=1 ");
        }
        if (sbOrder.Length > 0)
        {
            sbWhere.Append(sbOrder.ToString());
        }

        //设定了取前面几笔功能
        //sqlserver:SELECT top 100 （在最前）
        //sqlite:limit 100 (在最后)
        if (xnCond.Attributes["top"] != null)
        {
            switch (this.DatabaseType)
            {
                case (int)dbType.sqlserver:

                    //sbFields.Append(" top ")
                    //    .Append(xnCond.Attributes["top"].Value)
                    //    .Append(" ");

                    //"SELECT TOP 10"
                    sbFields.Insert(0, " TOP " + xnCond.Attributes["top"].Value + " ");

                    break;

                case (int)dbType.sqlite:
                    sbWhere.Append(" limit ")
                        .Append(xnCond.Attributes["top"].Value)
                        .Append(" ");

                    break;
            }
        }
        if (sbFields.Length > 0)
        {
            ret[0] = sbFields.ToString();
        }
        if (sbWhere.Length > 0)
        {
            ret[1] = sbWhere.ToString();
        }
        
        return ret;
    }


    /// <summary>
    /// 組裝IN SQL語句片段
    /// </summary>
    /// <param name="argFldName">欄名稱</param>
    /// <param name="argType">欄類型</param>
    /// <param name="argValue">值</param>
    /// <returns>IN SQL語句</returns>
    private String MakeSqlIN(String argFldName, String argType, String argValue)
    {
        String ret;
        int i = 0;
        StringBuilder sbSQL = new StringBuilder();
        String[] values = argValue.Split(',');

        sbSQL.Append(argFldName)
            .Append(" IN ")
            .Append("(");

        foreach (String val in values)
        {
            //需處理”,A,B,,C,“等輸入狀況。
            //當不是第一個元素，且有值時
            if (i > 0 && (!String.IsNullOrEmpty(val)))
            {
                sbSQL.Append(",");
            }
            i++;
            //有值
            if (!String.IsNullOrEmpty(val))
            {
                sbSQL.Append((argType == "String" ? "'" : ""));
                sbSQL.Append(val);
                sbSQL.Append((argType == "String" ? "'" : ""));
            }
        }

        sbSQL.Append(")");

        ret = sbSQL.ToString();
        return ret;

    }


    /// <summary>
    /// 組裝IN SQL語句片段
    /// </summary>
    /// <param name="argFldName">欄名稱</param>
    /// <param name="argType">欄類型</param>
    /// <param name="argValue">值</param>
    /// <returns>IN SQL語句</returns>
    private String MakeSqlBETWEEN(String argFldName, String argType, String argValue)
    {
        String ret;
        int i = 0;
        StringBuilder sbSQL = new StringBuilder();
        String[] values = argValue.Split('~');

        sbSQL.Append(argFldName)
            .Append(" BETWEEN ");
        if (values.Length != 2)
        {
            throw new Manuan.eBridge.Framework.MyException.BLLException("bl0010", new String[] { argValue });
        }
        foreach (String val in values)
        {
            if (String.IsNullOrEmpty(val))
            {
                throw new Manuan.eBridge.Framework.MyException.BLLException("bl0010", new String[] { argValue });
            }
            //需處理”A~B“等輸入狀況。
            //當不是第一個元素，且有值時
            if (i > 0 && (!String.IsNullOrEmpty(val)))
            {
                sbSQL.Append(" AND ");
            }
            i++;
            //有值
            if (!String.IsNullOrEmpty(val))
            {
                sbSQL.Append((argType == "String" ? "'" : ""));
                sbSQL.Append(val);
                sbSQL.Append((argType == "String" ? "'" : ""));
            }
        }


        ret = sbSQL.ToString();
        return ret;

    }
    //#endregion

    //#region PUBLIC Function
    public bool IsExist(String s_where , String s_retfields, out XmlDocModel x_ret)
    {
        bool ret = false;
        String sql = "select {0} from {1} where {2}";
        sql = String.Format(sql, new String[] { s_retfields,this.DataSourceName,s_where });
        DataSet ds = ExecuteDataSet(this.getDatabaseName(), sql);
        
        x_ret = Helper.Tools.DataSetToXML(ds);

        if (ds != null && ds.Tables.Count != 0 && ds.Tables[0].Rows.Count > 0)
        {
            ret = true;
        }

        return ret;

    }
    public bool IsExist(String argSql,out DataSet ds_ret)
    {
        bool ret = false;
        DataSet ds = null;
        Database db;
        String dbName=this.getDatabaseName();

        //int count = 0;    
        String sql = "SELECT '*' FROM {0} WHERE {1}";

        if (argSql.ToLower().IndexOf("select") < 0)
        {
            sql = String.Format(sql, new String[] { this.getTableName(), argSql });
        }else{
            sql = argSql;
        }
        ds = ExecuteDataSet(this.getDatabaseName(), sql);
        if (ds != null && ds.Tables.Count>0 && ds.Tables[0].Rows.Count > 0)
        {
            ret = true;
        }
        ds_ret = ds;
        return ret;

    }

    public bool IsExist(String s_sqlorwhere)
    {
        bool ret = false;
        DataSet ds = null;
        
        return IsExist(s_sqlorwhere,out ds);

    }
    /// <summary>
    /// 判断指定栏位值是否存在
    /// </summary>
    /// <param name="argField">栏位名称，多个用 + 分隔，其个数要与值的个数相同，+号两边一定需要一个空格</param>
    /// <param name="argValue">值，多个用 + 分隔，其个数要与栏名的个数相同，+号两边一定需要一个空格</param>
    /// <returns>true--存在，false--不存在</returns>
    public bool IsExist(String argField, String argValue)
    {
        bool ret = false;
        //int count = 0;
        String sql = "SELECT {0} FROM {1} WHERE {2} ";
        //+号两边一定需要一个空格
        String[] saFields = argField.Split('+');
        String[] saValues = argValue.Split('+');
        String s_fields="", s_conds="";

        for (int i=0;i<saValues.Length;i++)
        {
            s_conds += "<" + saFields[i] + ">=" + saValues[i] + "</" + saFields[i] + ">";
            s_fields += "<" + saFields[i] + "/>";
        }

        XmlDocModel xField = new XmlDocModel("<field>" + s_fields + "</field>");
        XmlDocModel xCond = new XmlDocModel("<cond>" + s_conds + "</cond>");
        String[] fw = MakeFieldAndWhere(xField, xCond);
        sql = String.Format(sql, fw[0], this.DataSourceName, fw[1]);

        /remark*
        XmlDocModel xData = DALHelper.ExecuteReader(sql,this.getDatabaseName() );
        if (xData.ChildNodes[1].FirstChild.HasChildNodes)
        {
            ret = true;
        }
        *remark/

        return IsExist(sql);

    }
    public bool IsExist(StringDictionary argCond)
    {
        bool ret = false;
        //int count =0;
        ////int count = 0;
        //StringBuilder sbSql = new StringBuilder();
        //for (count = 0;count<argCond.Count;count++)
        //{
        //    //sbSql.Append(cond.
        //}
        //String sql = "SELECT {1} FROM {0} WHERE {1} = '{2}'";
        //sql = String.Format(sql, new String[] { this.getTableName(), argField, argValue });
        //XmlDocModel xData = DALHelper.ExecuteReader(sql);

        //if (xData.ChildNodes[1].FirstChild.HasChildNodes)
        //{
        //    ret = true;
        //}
        return ret;

    }
    /// <summary>
    /// 判斷是否存在數據，并返回指定栏位值
    /// </summary>
    /// <param name="Cond">條件</param>
    /// <param name="retDate" 返回數據>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public bool IsExist(String s_sqlwhere, out XmlDocModel x_ret)
    {
        DataSet ds = null;
        XmlDocument xRet = new XmlDocument();
        bool ret = false;
        ret = IsExist(s_sqlwhere, out ds);

        x_ret = Helper.Tools.DataSetToXML(ds);
        return ret;

    }


    /// <summary>
    /// 判斷是否存在數據，并返回指定栏位值
    /// </summary>
    /// <param name="Cond">條件</param>
    /// <param name="retDate" 返回數據>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public bool IsExist(String s_where, String s_retfld, out String s_ret)
    {
        DataSet ds = null;
        //XmlDocument xRet = new XmlDocument();
        bool ret = false;
        String sql = "SELECT {0} FROM {1} WHERE {2} ";

        sql = String.Format(sql, s_retfld, this.DataSourceName, s_where);

        ret = IsExist(sql, out ds);
        s_ret = ""; 

        if (ret)
        {
            s_ret = ds.Tables[0].Rows[0][s_retfld].ToString();
        }
        return ret;

    }
    /// <summary>
    /// 判斷是否存在數據，并返回指定栏位值
    /// </summary>
    /// <param name="Cond">條件</param>
    /// <param name="retDate" 返回數據>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public bool IsExist(String s_field,String s_value,String s_retfld, out String s_ret)
    {
        DataSet ds = null;
        bool ret = false;
        String sql = "SELECT {0} FROM {1} WHERE {2} ";

        XmlDocModel xField = new XmlDocModel("<field><" + s_retfld + "/></field>");
        XmlDocModel xCond = new XmlDocModel("<cond><" + s_field +">" + s_value + "</" + s_field+ "></cond>");
        String[] fw = MakeFieldAndWhere(xField, xCond);
        sql = String.Format(sql, fw[0], this.DataSourceName, fw[1]);


        ret = IsExist(sql, out ds);
        s_ret = ""; 

        if (ret)
        {
            s_ret = ds.Tables[0].Rows[0][s_retfld].ToString();
        }
        return ret;

    }
    /remark*
    /// <summary>
    /// 判斷是否存在數據
    /// </summary>
    /// <param name="Cond">條件</param>
    /// <param name="retDate" 返回數據>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public bool IsExist(String argDatasources,String Cond, out XmlDocModel retDate)
    {
        bool ret = false;
        //int count = 0;
        String sql = "SELECT * FROM {0} WHERE {1}";
        sql = String.Format(sql, new String[] { argDatasources, Cond });
        retDate = DALHelper.ExecuteReader(sql, this.getDatabaseName());

        if (retDate.ChildNodes[1].FirstChild.HasChildNodes)
        {
            ret = true;
        }
        return ret;

    }
    /// <summary>
    /// 判斷是否存在數據
    /// </summary>
    /// <param name="Cond">條件</param>
    /// <param name="argIsView">以其View作为查找对象</param>
    /// <param name="retDate" 返回數據>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public bool IsExist(String Cond, bool argIsView)
    {
        bool ret = false;
        //int count = 0;
        String sql = "SELECT * FROM {0} WHERE {1}";
        sql = String.Format(sql, new String[] { argIsView ? this.DataSourceName : this.getTableName(), Cond });
        XmlDocModel retDate = DALHelper.ExecuteReader(sql, this.getDatabaseName());

        if (!retDate.IsEmpty)
        {
            ret = true;
        }
        return ret;

    }
      *remark/

    public String GetValue(String s_sql)
    {
        String ret = null;
        String sql = s_sql;

        DataSet ds = null;
        String dbName = this.getDatabaseName();

        ds = ExecuteDataSet(dbName, s_sql);
        if (ds != null && ds.Tables.Count != 0 && ds.Tables[0].Rows.Count >= 0)
        {
            ret = ds.Tables[0].Rows[0][0].ToString();

        }
        return ret;

    }

    /// <summary>
    /// 取得單一欄值
    /// </summary>
    /// <param name="s_keyfld">Key欄名稱</param>
    /// <param name="s_keyvalue">Key值</param>
    /// <param name="s_retfld">返回欄，可以多個欄位，用逗號分隔</param>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public String GetValue(String s_keyfld, String s_keyvalue, String s_retfld)
    {
        String ret = null;
        String sql = "select {0} from {1} where {2} = '{3}'";

        String dbName = this.getDatabaseName();

        sql = String.Format(sql, new String[] { 
            s_retfld,
            this.getTableName(),
            s_keyfld,
            s_keyvalue
        });

        ret = GetValue(sql);
        return ret;
        //return DALHelper.GetValue(this.getDatabaseName(), this.DataSourceName, argField, argCond);

    }

    /// <summary>
    /// 取得單一欄值
    /// </summary>
    /// <param name="s_keyfld">Key欄名稱</param>
    /// <param name="s_keyvalue">Key值</param>
    /// <param name="s_retfld">返回欄</param>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public String GetValue(String s_keyvalue, String s_retfld)
    {
        String ret = null;
        String sql = "select {0} from {1} where {2} = '{3}'";

        String dbName = this.getDatabaseName();
        //foreach (XmlNode fld in this.TableSchema.SelectSingleNode("xconfig/records/" + this.getTableName().ToLower()).ChildNodes)
        //{

        //}

        sql = String.Format(sql, new String[] { 
            s_retfld,
            this.getTableName(),
            this.KeyName,
            s_keyvalue
        });

        ret = GetValue(sql);
        return ret;
        //return DALHelper.GetValue(this.getDatabaseName(), this.DataSourceName, argField, argCond);

    }

    /// <summary>
    /// 取得單一欄值
    /// </summary>
    /// <param name="s_keyfld">Key欄名稱</param>
    /// <param name="s_keyvalue">Key值</param>
    /// <param name="s_retfld">返回欄</param>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public XmlDocModel GetValues(String s_sql)
    {
        XmlDocModel ret = new XmlDocModel();
        String sql = s_sql;

        DataSet ds = null;
        String dbName = this.getDatabaseName();

        ds = ExecuteDataSet(dbName, s_sql);

        ret = Tools.DataSetToXML(ds); 

        return ret;

    }

    /// <summary>
    /// 取得單一欄值
    /// </summary>
    /// <param name="s_keyfld">Key欄名稱</param>
    /// <param name="s_keyvalue">Key值</param>
    /// <param name="s_retfld">返回欄，可以多個欄位，用逗號分隔</param>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public XmlDocModel GetValues(String s_keyfld, String s_keyvalue, String s_retfld)
    {
        XmlDocModel ret = null;
        String sql = "select {0} from {1} where {2} = '{3}'";

        String dbName = this.getDatabaseName();

        sql = String.Format(sql, new String[] { 
            s_retfld,
            this.getTableName(),
            s_keyfld,
            s_keyvalue
        });

        ret = GetValues(sql);
        return ret;
        //return DALHelper.GetValue(this.getDatabaseName(), this.DataSourceName, argField, argCond);

    }

    /// <summary>
    /// 取得單一欄值
    /// </summary>
    /// <param name="s_keyfld">Key欄名稱</param>
    /// <param name="s_keyvalue">Key值</param>
    /// <param name="s_retfld">返回欄</param>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public XmlDocModel GetValues(String s_keyvalue, String s_retfld)
    {
        XmlDocModel ret = null;
        String sql = "select {0} from {1} where {2} = '{3}'";

        String dbName = this.getDatabaseName();
        foreach (XmlNode fld in this.TableSchema.SelectSingleNode("xconfig/records/" + this.getTableName().ToLower()).ChildNodes)
        {
            
        }

        sql = String.Format(sql, new String[] { 
            s_retfld,
            this.getTableName(),
            this.KeyName,
            s_keyvalue
        });

        ret = GetValues(sql);
        return ret;
        //return DALHelper.GetValue(this.getDatabaseName(), this.DataSourceName, argField, argCond);

    }

    /// <summary>
    /// 取得某一欄位最大值
    /// </summary>
    /// <param name="argField">欄名稱</param>
    /// <param name="argCond">條件</param>
    /// <returns>欄值，當沒有找到，返回null</returns>
    public String GetMaxValue(String argField, String argCond)
    {
        return DALHelper.GetMaxValue(this.getDatabaseName(), this.DataSourceName, argField, argCond);
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
        return DALHelper.GetMaxValue(this.getDatabaseName(), this.DataSourceName, argField, argCondName, argCondValue);
    }
    //#endregion


            /// <summary>
    /// 執行SELECT語句返回DataSet
    /// </summary>
    /// <param name="s_dbName"></param>
    /// <param name="s_dsn"></param>
    /// <param name="s_sql"></param>
    /// <returns>DataSet</returns>
    private DataSet ExecuteDataSet(String s_dbName,String s_sql)
    {
        DataSet ret = null;
        Database db;

        //execute
        db = DALHelper.CreateDatabase(s_dbName);
        if (s_sql != "" && s_sql.ToLower().Trim().IndexOf("select")==0)
        {

            DbCommand dbCommand = db.GetSqlStringCommand(s_sql);
            //dbCommand.CommandTimeout = 6000;
            try
            {
                ret = db.ExecuteDataSet(dbCommand);

            }
            catch (Exception ex)
            {
                MyException.DbAccessException exDb =
                    new MyException.DbAccessException("DB00", s_dbName, ex, s_sql);
                throw exDb;
            }
            finally
            {
                //若在Config中設定開啟Log功能
                if (Manuan.eBridge.Framework.Data.Global.AppConfig("open_log") == "1")
                {
                    Manuan.eBridge.Framework.MyLogger.Write(s_sql, "", "Message", s_dbName);
                }
            }
        }
        return ret;
    }
*/
}