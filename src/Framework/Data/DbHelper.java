/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework.Data;

import Framework.Exception.DALException;
import Framework.Xml.*;
import java.sql.*;
import Framework.logger.*;
import Framework.util.Configer;
//import oracle.toplink.essentials.mappings.converters.Converter;

/**
 *
 * @author Administrator
 * @date 2010/3/29, 下午 02:35:55
 */
public class DbHelper {

    private static final String DEFAULT_DB = "default";

    public static Connection createConnection() throws SQLException {
        return createConnection(DEFAULT_DB);
    }

    public static Connection createConnection(String dbName) throws SQLException {

        String db = Configer.getString(dbName + "_dbName");
        String user = Configer.getString(dbName + "_user");
        String pwd = Configer.getString(dbName + "_pwd");
        String url = Configer.getString(dbName + "_dbUrl");
        String className = Configer.getString(dbName + "_className");

        try {
            Class.forName(className);
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: " + className);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        Connection conn = null;
        if (user == null && pwd == null) {//不需要密碼，如sqlite
            conn = DriverManager.getConnection(url);
        } else {
            conn = DriverManager.getConnection(url, user, pwd);
        }

        return conn;
    }

    public static int ExecuteNonQuery(String argSql) {

        return ExecuteNonQuery(argSql, DEFAULT_DB);
    }

    public static int ExecuteNonQuery(String argSql, String argDbName) {
        int ret = 0;
        Statement st = null;
        Connection conn = null;
        try {
            conn = createConnection(argDbName);
            st = conn.createStatement();
            ret = st.executeUpdate(argSql);
            conn.close();
        } catch (SQLException ex) {
            DALException exDb = new DALException("DB01", getDSN(argSql, false), ex, argSql);
            MyLogger.Write(argDbName + argSql);
            ex.printStackTrace();
            //throw exDb;
        } finally {
            if (conn != null) {
                //conn.close();
            }
        }
        return ret;
    }

    public static XmlDocModel ExecuteQuery(String argSql) {

        return ExecuteQuery(argSql, DEFAULT_DB);
    }

    public static XmlDocModel ExecuteQuery(String argSql, String argDbName) {
        XmlDocModel ret = null;

        Statement st = null;
        ResultSet rs = null;
        String dsn = "";
        Connection conn = null;
        //MyLogger.Write(argSql);
        try {
            conn = createConnection(argDbName);
            st = conn.createStatement();
            rs = st.executeQuery(argSql);
            if (!rs.wasNull()) {
                ret=new XmlDocModel();
                //ret.ToXml(rs);
            }

            // ret.Save(ret.getDocument(),"test.xml");
            //转换成JSON格式字串
            //ret = this.ToJsonString(dataReader);
            //ret = Data.Convert2.DataReaderToXml(dataReader, false);
            //ret = DataToXml(dataReader, argDataSourceName,true);
            rs.close();
            conn.close();
        } catch (SQLException ex) {

            DALException exDb = new DALException("DB00", getDSN(argSql, true), ex, argSql, argDbName);
            //throw exDb;
        } catch (Exception e) {

            DALException exDb = new DALException("DB00", getDSN(argSql, true), e, argSql, argDbName);
            // MyLogger.Write(e.toString());
        } finally {
            if (conn != null) {
                //conn.close();
            }
        }

        return ret;
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
    public static XmlDocModel Query(String argDbName, String argDataSourceName, String argFields, String argCond, boolean argIsFlat) {
        XmlDocModel ret = null;

        Statement st = null;
        ResultSet rs = null;
        String dsn = "";
        Connection conn = null;


        String sql = "SELECT %1$s FROM %2$s WHERE %3$s";
        sql = String.format(sql, argFields, argDataSourceName, argCond);


        try {
//            conn = createConnection(argDbName);
//            st = conn.createStatement();
//            rs = st.executeQuery(sql);
//            ret.ToXml(rs);
            ret = ExecuteQuery(sql, argDbName);
        } catch (Exception ex) {
            DALException exDb = new DALException("DB00", argDataSourceName, ex, sql);
            //throw exDb;
        } finally {
            //若在Config中設定開啟Log功能
            if (Configer.getString("open_log").equals("1")) {
                MyLogger.Write(sql, "", "Message", argDataSourceName);
            }
        }

        return ret;
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
    public static int AddRow(String argDbName, String argTableName, String argFieldList, String argValueList) {
        int ret = 0;
        String sql = "INSERT INTO %1$s(%2$s) VALUES(%3$s)";
        sql = String.format(sql, argTableName, argFieldList, argValueList);
        MyLogger.Write(sql);
        try {
            ret = ExecuteNonQuery(sql, argDbName);
            MyLogger.Write(Integer.toString(ret));
        } catch (Exception ex) {
            DALException exDb =
                    new DALException("DB01", argTableName, ex, sql);
            ex.printStackTrace();

        } finally {
            //若在Config中設定開啟Log功能
            if (Configer.getString("open_log").equals("1")) {
                MyLogger.Write(sql, "", "Message", argTableName);
            }
        }

        return ret;

    }

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
    ///		int count = dalProject.DeleteRow("EQ","PROJECT","PROJECTID='001'",true);
    ///	</code>
    ///	</example>
    /// </remarks>
    public static int DeleteRow(String argDbName, String argTableName, String argWhere, boolean argIsKey) {
        int ret = 0;
        String sql = "DELETE FROM %1$s WHERE %2$s";
        sql = String.format(sql, argTableName, argWhere);
        //MyLogger.Write(sql);
        try {
            ret = ExecuteNonQuery(sql, argDbName);
        } catch (Exception ex) {
            DALException exDb =
                    new DALException("DB03", argTableName, ex, sql);
            //throw exDb;
        } finally {
            //若在Config中設定開啟Log功能
            if (Configer.getString("open_log").equals("1")) {
                MyLogger.Write(sql, "", "Message", argTableName);
            }
        }

        return ret;
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
    public static int UpdateRow(String argDbName, String argTableName, String argSet, String argWhere) {
        int ret = 0;
        String sql = "UPDATE %1$s SET %2$s WHERE %3$s";
        sql = String.format(sql, argTableName, argSet, argWhere);

        try {
            ret = ExecuteNonQuery(sql, argDbName);

        } catch (Exception ex) {
            DALException exDb =
                    new DALException("DB02", argTableName, ex, sql);
            //throw exDb;
        } finally {
            //若在Config中設定開啟Log功能
            if (Configer.getString("open_log").equals("1")) {
                MyLogger.Write(sql, "", "Message", argTableName);
            }
        }
        return ret;
    }

    //從基本的簡單的Sql語句中取得Table Name或View Name:SELECT /UPDATE/INSERT/DELETE
    //select * FROM  PROJECT WHERE....
    //insert into tablename(...) values(...);
    //update tablename set fld1=value,fld2=value2;
    //delete tablename where ...
    //可改用Regex的方式
    private static String getDSN(String sql, boolean isQuery) {
        String ret = null;
        if (isQuery) {
            //select * FROM  PROJECT WHERE....
            int pos1 = sql.toLowerCase().indexOf("from ");
            int pos2 = sql.toLowerCase().indexOf(" ", pos1 + 6);

            if (pos1 > 0 && pos2 > 0) {
                ret = sql.substring(pos1 + 6, pos2 - pos1);
            }
        } else {
            //insert into tablename(...) values(...);
            //update tablename set fld1=value,fld2=value2;
            //delete tablename where ...
            int pos1 = sql.toLowerCase().indexOf("insert into ");
            if (pos1 < 0) {
                pos1 = sql.toLowerCase().indexOf("update ");
            }
            int pos2 = sql.toLowerCase().indexOf(" ", pos1 + 6);
            //處理FROM后連續多個空格現象，如from    project
            //while (argSql.Substring(pos2, 1)==" ")
            //{
            //    pos2 = argSql.toLowerCase().indexOf(" ", pos2+1);
            //}
            if (pos1 > 0 && pos2 > 0) {
                ret = sql.substring(pos1 + 6, pos2 - pos1);
            }
        }
        return ret;
    }
}
