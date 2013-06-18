package Framework.Data;

import java.awt.List;
import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import Framework.Exception.DALException;
import Framework.Xml.*;
import Framework.logger.MyLogger;
import Framework.util.Configer;

/**
 * 
 * 
 * 项目名称：Framework 类名称：DALHelper 类描述：
 * 
 * @author lucky 创建时间：Jun 13, 2013 10:04:15 AM 修改人：lucky 修改时间：Jun 13, 2013
 *         10:04:15 AM 修改备注：
 * @version
 * 
 */
public class DALHelper {

	// / <summary>
	// /
	// / </summary>
	// / <param name="argDatabaseName">Database名称</param>
	// / <returns>Database对象</returns>

	/**
	 * 
	 * 功能概述：依指定的Database名称建立Database对象
	 * 
	 * @param dbName
	 *            :数据库名称，在app.cfg中设定，注意设定的标签全部使用小写
	 * @return Connection对象
	 * @author lucky 创建时间：Jun 13, 2013 10:31:12 AM 修改人：lucky 修改时间：Jun 13, 2013
	 *         10:31:12 AM 修改备注：
	 * @version
	 * 
	 */
	public static Connection CreateDatabase(String dbName) {
		Connection conn = null;

		// String db = Global.AppConfig(dbName.toLowerCase() + "_db_name");
		String user = Global.AppConfig(dbName.toLowerCase() + "_user");
		String pwd = Global.AppConfig(dbName.toLowerCase() + "_pwd");
		String url = Global.AppConfig(dbName.toLowerCase() + "_url");
		String className = Global.AppConfig(dbName.toLowerCase()
				+ "_class_name");

		try {
			Class.forName(className);
		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: " + className);
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {

		}

		try {
			// 不需要密碼，如sqlite
			if (user == null && pwd == null) {
				conn = DriverManager.getConnection(url);
			} else {
				conn = DriverManager.getConnection(url, user, pwd);
			}

		} catch (SQLException ex) {

			ex.printStackTrace();
		}

		return conn;
	}

	// / <summary>
	// / 依Default的DBName建立Database对象
	// / </summary>
	// / <returns>Database对象</returns>
	public static Connection CreateDatabase() {
		Connection db;
		String dbName = Global.AppConfig("default_db_name");

		db = CreateDatabase(dbName);

		return db;
	}

	/**
	 * 
	 * 功能概述：关闭Database链接
	 * 
	 * @param conn
	 *            Database连接对象
	 * @return 是否成功关闭
	 * @author lucky 创建时间：Jun 13, 2013 3:27:48 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         3:27:48 PM 修改备注：
	 * @version
	 * 
	 */
	private static boolean closeDatabase(Connection conn) {
		boolean ret = false;
		if (conn != null) {
			try {
				conn.close();
				ret = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * 
	 * 功能概述：关闭ResultSet对象
	 * 
	 * @param rs
	 *            ResultSet对象
	 * @return 是否成功关闭
	 * @author lucky 创建时间：Jun 13, 2013 3:30:03 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         3:30:03 PM 修改备注：
	 * @version
	 * 
	 */
	private static boolean closeReultSet(ResultSet rs) {
		boolean ret = false;
		if (rs != null) {
			try {
				rs.close();
				ret = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * 
	 * 功能概述：关闭ResultSet对象
	 * 
	 * @param rs
	 *            ResultSet对象
	 * @return 是否成功关闭
	 * @author lucky 创建时间：Jun 13, 2013 3:30:03 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         3:30:03 PM 修改备注：
	 * @version
	 * 
	 */
	private static boolean closeStatement(Statement st) {
		boolean ret = false;
		if (st != null) {
			try {
				st.close();
				ret = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	// 判斷是否為Primary Key欄
	private static boolean isPrimaryKey(ResultSet rsKeys, String fldName)
			throws SQLException {
		boolean ret = false;
		// int i =1;
		// MyLogger.Write(rsKeys.);
		while (rsKeys.next()) {

			if (rsKeys.getString("column_name").toLowerCase() == fldName
					.toLowerCase()) {
				ret = true;
			}
			// i++;
		}
		return ret;
	}

	// / <summary>
	// /
	// / </summary>
	// / <param name="argDatabaseName"></param>
	// / <param name="argDataSourceName"></param>
	// / <returns></returns>

	/**
	 * 
	 * 功能概述： 取得Table或View的結構。
	 * 
	 * @param argDatabaseName
	 * @param argDataSourceName
	 * @return Xml格式的Schema结构
	 * @author lucky 创建时间：Jun 13, 2013 10:10:43 AM 修改人：lucky 修改时间：Jun 13, 2013
	 *         10:10:43 AM 修改备注：
	 * @version
	 * 
	 */
	public static XmlDocModel GetSchema(String argDatabaseName,
			String argDataSourceName) {
		String path = Global.AppDir() + "/data/schema/"
				+ argDataSourceName.toLowerCase() + ".xml";
		XmlDocModel xmlDoc = new XmlDocModel();
		File file = new File(path);
		if (!file.exists()) {
			xmlDoc = CreateSchema(argDataSourceName, argDatabaseName);
		}
		xmlDoc.load(path);

		return xmlDoc;

	}

	// / <summary>
	// / 取得Table或View的結構。
	// / </summary>
	// / <param name="argDatabaseName">Database Name </param>
	// / <param name="argDataSourceName">DataSource Name</param>
	// / <returns>結構</returns>
	/*
	 * getColumnName:USER_ID getColumnType：1 getColumnTypeName：CHAR
	 * getColumnDisplaySize：10 getCatalogName：
	 * getColumnClassName：java.lang.String getColumnLabel：USER_ID getSchemaName：
	 * getPrecision：10 getScale：0 getTableName： getCatalogName： isReadOnly：false
	 * isWritable：true isDefinitelyWritable：false
	 * 
	 * MyLogger.Write( " getColumnName:" + rsmd.getColumnName(i) +
	 * " getColumnType：" + rsmd.getColumnType(i) + " getColumnTypeName：" +
	 * rsmd.getColumnTypeName(i) + " getColumnDisplaySize：" +
	 * rsmd.getColumnDisplaySize(i) + " getCatalogName：" +
	 * rsmd.getCatalogName(i) + " getColumnClassName：" +
	 * rsmd.getColumnClassName(i) + " getColumnLabel：" + rsmd.getColumnLabel(i)
	 * + " getSchemaName：" + rsmd.getSchemaName(i) + " getPrecision：" +
	 * rsmd.getPrecision(i) + " getScale：" + rsmd.getScale(i) + " getTableName："
	 * + rsmd.getTableName(i) + " getCatalogName：" + rsmd.getCatalogName(i) +
	 * " isReadOnly：" + rsmd.isReadOnly(i) + " isNullable：" + rsmd.isNullable(i)
	 * + " isWritable：" + rsmd.isWritable(i) + " isDefinitelyWritable：" +
	 * rsmd.isDefinitelyWritable(i));
	 */

	/**
	 * 
	 * 功能概述：建立Table或View的結構。
	 * 
	 * @param dsn
	 *            table或view名称
	 * @param dbName
	 *            数据库名称
	 * @return table或view的结构結構
	 * @author lucky 创建时间：Jun 13, 2013 2:18:47 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         2:18:47 PM 修改备注：建立的Schema将保存到硬盘，文件名称为小写
	 * @version
	 * @throws SQLException
	 * 
	 */
	public static XmlDocModel CreateSchema(String dsn, String dbName) {
		/** 建立document对象 */
		Statement st = null;
		ResultSet rs = null;
		ResultSet rsKeys = null;
		Connection conn = null;

		String sql = String.format("select * from %1$s where 1>1", dsn);
		XmlDocModel xSchema = new XmlDocModel();
		// ResultSet rs;
		Document doc = xSchema.getDocument();
		try {

			conn = DALHelper.CreateDatabase(dbName);
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			DatabaseMetaData dbmd = conn.getMetaData();
			rsKeys = dbmd.getPrimaryKeys(null, null, dsn);
			int cols = rsmd.getColumnCount(); // 得到数据集的列数
			Element tableElement = doc.getRootElement().addElement(
					dsn.toLowerCase());
			for (int i = 1; i <= cols; i++) {

				if (rsmd.getColumnName(i) != null) {
					Element colElement = tableElement.addElement(rsmd
							.getColumnName(i).toLowerCase());
					// s="50" d="system.String" n="true" k=""
					colElement
							.addAttribute(
									"s",
									String.format("%1$s",
											rsmd.getColumnDisplaySize(i)));
					colElement.addAttribute("d",
							String.format("%1$s", rsmd.getColumnTypeName(i)));
					colElement.addAttribute("n",
							rsmd.isNullable(i) == 0 ? "false" : "true");
					colElement
							.addAttribute(
									"k",
									(isPrimaryKey(rsKeys, rsmd.getColumnName(i)) ? "true"
											: "false"));
				}
			}

			// conn.close();
		} catch (SQLException exSql) {
			MyLogger.Write(exSql.getMessage());
			exSql.printStackTrace();
		} finally {
			closeReultSet(rsKeys);
			closeReultSet(rs);
			closeStatement(st);
			closeDatabase(conn);

		}
		// MyLogger.Write(doc.asXML());

		// Save(xSchema, "//data//schema//" + dsn + ".xml");
		xSchema.Save(doc, Global.AppDir() + "/data/schema/" + dbName.toLowerCase() + "/" + dsn.toLowerCase()
				+ ".xml");

		return xSchema;
	}

	/**
	 * 
	 * 功能概述：将资料集合转换成XmlDocument
	 * 
	 * @param rs
	 * @return Document对象
	 * @throws SQLException
	 * @author lucky 创建时间：Jun 13, 2013 10:54:24 AM 修改人：lucky 修改时间：Jun 13, 2013
	 *         10:54:24 AM 修改备注：从XmlDomModel类中移过来
	 * @version
	 * 
	 */
	public static XmlDocModel ToXml(ResultSet rs) throws SQLException {
		String _COND = "cond";
		String _RECORDS = "records";
		String _REDORD = "redord";

		XmlDocModel xm = new XmlDocModel();
		/** 建立document对象 */
		Document doc = xm.getDocument();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount(); // 得到数据集的列数
		Element tableElement = null;
		Element colElement;
		// tableElement = doc.getRootElement().addElement(_COND);

		// COND NODE
		// for (int i = 1; i <= cols; i++) {
		// if (rsmd.getColumnName(i) != null) {
		// colElement =
		// tableElement.addElement(rsmd.getColumnName(i).toLowerCase());
		//
		// }
		// }//end for
		// RECORDS
		Element recordsElement = doc.getRootElement().addElement(_RECORDS);
		// every row
		while (rs.next()) {
			tableElement = recordsElement.addElement(_REDORD);
			tableElement.addAttribute("onchanged", "0");
			// every col
			for (int i = 1; i <= cols; i++) {
				if (rsmd.getColumnName(i) != null) {
					colElement = tableElement.addElement(rsmd.getColumnName(i)
							.toLowerCase());

					if (rs.getString(i) != null) {
						colElement.setText(rs.getString(i));
					}
				}
			}// end for
		}// end while
			// MyLogger.Write(doc.asXML());
			// this.setDocument(doc);
		return xm;
	}

	/*
	 * XmlDocModel中有Save方法，直接使用 /// <summary> /// 保存XML文檔到提定目的地 /// </summary>
	 * /// <param name="argXml">待保存的XMLDocument Object</param> /// <param
	 * name="argFileName">文件名稱，可含Path</param> public static void
	 * Save(XmlDocModel argXml, String argFileName) { String path = ""; String
	 * dir = ""; String file = ""; String destFile = "";//保存目的地
	 * 
	 * String fileName = argFileName.Replace("/", "\\");//實現不區分“/”與“\”
	 * 
	 * int posPath = fileName.LastIndexOf("\\");//最後一"\" if (posPath < 0)
	 * posPath = 0;
	 * 
	 * file = fileName.SubString(posPath) + ".xml";
	 * 
	 * 
	 * path = fileName.SubString(0, posPath);
	 * 
	 * //有帶盤符 if (path.IndexOf(":") > 0) { destFile = file; } else { dir =
	 * AppDomain.CurrentDomain.SetupInformation.ApplicationBase +
	 * ((path.IndexOf("\\") == 0) ? "\\" : "") + path; destFile = dir +
	 * ((file.IndexOf("\\") == 0) ? "\\" : "") + file; } //若有指定目錄 if
	 * (!path.Equals("")) { //目錄不存在
	 * 
	 * if (!System.IO.Directory.Exists(dir)) {
	 * System.IO.Directory.CreateDirectory(dir); } } try {
	 * 
	 * argXml.Save(destFile); } catch (System.Exception e) { throw new
	 * Manuan.eBridge.Framework.MyException.PFException("PF0010", e.Message); }
	 * 
	 * }
	 */

	// #region 公共方法:ExecuteNonQuery + ExecuteReader
	public static int ExecuteNonQuery(String argSql) throws DALException {

		return ExecuteNonQuery(argSql, "_DEFAULT_DATABASE");
	}

	/**
	 * 
	 * 功能概述：执行SQL语句，返回成功笔数
	 * 
	 * @param argSql
	 *            SQL语句
	 * @param argDbName
	 *            Dababase
	 * @return 成功笔数
	 * @throws DALException
	 * @author lucky 创建时间：Jun 13, 2013 3:50:10 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         3:50:10 PM 修改备注：
	 * @version
	 * 
	 */
	public static int ExecuteNonQuery(String argSql, String argDbName) {
		int ret = 0;
		Connection db = null;
		Statement st = null;
		ResultSet rs = null;

		if (argDbName.equals("_DEFAULT_DATABASE")) {
			db = CreateDatabase();
		} else {
			db = CreateDatabase(argDbName);
		}

		try {
			st = db.createStatement();
			ret = st.executeUpdate(argSql);
		} catch (Exception ex) {
			/*
			 * String dsn = ""; //select * FROM PROJECT WHERE.... int pos1 =
			 * argSql.toLowerCase().indexOf("insert into "); if (pos1 < 0) {
			 * pos1 = argSql.toLowerCase().indexOf("update "); } int pos2 =
			 * argSql.toLowerCase().indexOf(" ", pos1 + 6);
			 * //處理FROM后連續多個空格現象，如from project //while (argSql.SubString(pos2,
			 * 1)==" ") //{ // pos2 = argSql.ToLower().IndexOf(" ", pos2+1); //}
			 * if (pos1 > 0 && pos2 > 0) { dsn = argSql.subString(pos1 + 6, pos2
			 * - pos1); }
			 */
			DALException exDb = new DALException("DB01", "", ex, argSql);

			// throw exDb;
		} finally {

			closeReultSet(rs);
			closeStatement(st);
			closeDatabase(db);

		}
		return ret;
	}

	public static XmlDocModel ExecuteReader(String argSql, String argDbName) {
		return Query(argSql, argDbName);
	}

	public static XmlDocModel ExecuteReader(String argSql) {

		return Query(argSql, "_DEFAULT_DATABASE");
	}

	public static XmlDocModel Query(String argSql) {

		return Query(argSql, "_DEFAULT_DATABASE");
	}

	// / <summary>
	// /
	// / </summary>
	// / <param name="argSql"></param>
	// / <param name="argDbName"></param>
	// / <returns></returns>

	/**
	 * 
	 * 功能概述：在指定的Database中执行指定SQL语句，返回XmlDocModel数据集合对象
	 * 
	 * @param argSql
	 *            SQL语句
	 * @param argDbName
	 *            Database名称
	 * @return 查找到的结果集合
	 * @author lucky 创建时间：Jun 13, 2013 4:27:55 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         4:27:55 PM 修改备注：
	 * @version
	 * 
	 */
	public static XmlDocModel Query(String argSql, String argDbName) {
		XmlDocModel ret = null;
		Connection db = null;
		Statement st = null;
		ResultSet rs = null;
		// DbCommand dbCommand = db.GetSqlStringCommand(argSql);
		try {
			if (argDbName == "_DEFAULT_DATABASE") {
				db = CreateDatabase();
			} else {
				db = CreateDatabase(argDbName);

			}

			st = db.createStatement();
			rs = st.executeQuery(argSql);
			ret = ToXml(rs);
		} catch (Exception ex) {
			String dsn = getDSN(argSql,true);
			DALException exDb = new DALException("DB01", dsn, ex, argSql);
			// throw exDb;
		} finally {
			closeReultSet(rs);
			closeStatement(st);
			closeDatabase(db);
			// 若在Config中設定開啟Log功能
			if (Global.AppConfig("open_log").equals("1")) {
				MyLogger.Write(argSql, "", "Message", argDbName);
			}
		}

		return ret;
	}

	// #endregion

	// #region Query
	// / <summary>
	// / 取得資料，指定DB，TABLE，Field,條件，封裝類型
	// / </summary>
	// / <param name="argDbName">资料庫名称</param>
	// / <param name="argDataSourceName">资料来源名称，如TABLE、View名称</param>
	// / <param name="argFields">栏位清单，以","号分隔</param>
	// / <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday =
	// '2000/09/01'</param>
	// / <param name="argIsFlat">將欄位的節點變成屬性</param>
	// / <returns>将资料封装成XmlDocModel返回.</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / 指定DB，TABLE，Field,條件，封裝類型
	// / </para>
	// / <para>[異動記錄]
	// / AUTHOR DATE NOTE
	// / ========== ========== ========================================
	// / Rayd 2005-02-11 Create
	// / </para>
	// / <example>
	// / <code>
	// / String cond = String.format("projectid={0}",new String[]{'1'});
	// / XmlDocModel xProject =
	// Query("CRM","PROJECT","PROJECTID,PROJECTNAME",cond,false);
	// / </code>
	// / </example>
	// / </remarks>

	/**
	 * 
	 * 功能概述：在Database中执行指定table/view,fields,条件表达语句
	 * 
	 * @param argDbName
	 *            Database名称
	 * @param argDataSourceName
	 *            table or view名
	 * @param argFields
	 *            字段式，多个用逗号分隔
	 * @param argCond
	 *            条件表达式，不能带WHERE,可以带ORDER BY ，如：LastName = 'Rose' AND Birday =
	 *            '2000/09/01'
	 * @return 查找到的结果集合
	 * @author lucky 创建时间：Jun 13, 2013 4:30:34 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         4:30:34 PM 修改备注：
	 * @version
	 * 
	 */
	public static XmlDocModel Query(String argDbName, String argDataSourceName,
			String argFields, String argCond) {
		String sql = "SELECT %1$s FROM %2$s WHERE %3$s";
		sql = String.format(sql, argFields, argDataSourceName, argCond);
		return Query(sql, argDbName);
	}

	/*
	 * /// <summary> /// 取得資料，以JSON格式返回，指定DB，TABLE，Field,條件，封裝類型 /// </summary>
	 * /// <param name="argDbName">资料庫名称</param> /// <param
	 * name="argDataSourceName">资料来源名称，如TABLE、View名称</param> /// <param
	 * name="argFields">栏位清单，以","号分隔</param> /// <param
	 * name="argCond">条件表达式，如：LastName = 'Rose' AND Birday =
	 * '2000/09/01'</param> /// <param name="argIsFlat">將欄位的節點變成屬性</param> ///
	 * <param
	 * name="argIsText">true:若单独取一个栏位，且只有一笔资料时直接取得返回；false:將结果封装成XML</param> ///
	 * <returns>将资料以文字形体返回.</returns> /// <remarks> /// <para>[規格說明] ///
	 * 指定DB，TABLE，Field,條件，封裝類型 /// </para> /// <para>[異動記錄] /// AUTHOR DATE
	 * NOTE /// ========== ========== ========================================
	 * /// Rayd 2012-11-07 Create /// </para> /// <example> /// <code> ///
	 * String cond = String.format("projectid={0}",new String[]{'1'}); ///
	 * String jsonProject = Query("CRM","PROJECT","PROJECTID,PROJECTNAME",cond);
	 * /// </code> /// </example> /// </remarks> public static String
	 * Query(String argDbName, String argDataSourceName, String argFields,
	 * String argCond) { String ret = null;
	 * 
	 * Database db = DALHelper.CreateDatabase(argDbName);
	 * 
	 * 
	 * String sql = "SELECT {0} FROM {1} WHERE {2}";
	 * 
	 * sql = String.Format(sql, argFields, argDataSourceName, argCond);
	 * 
	 * DbCommand dbCommand = db.GetSqlStringCommand(sql);
	 * dbCommand.CommandTimeout = 6000; try {
	 * 
	 * using (DataSet ds = db.ExecuteDataSet(dbCommand)) { //转换成JSON格式字串 ret =
	 * Convert2.DataSetToJson(ds);
	 * 
	 * } } catch (Exception ex) { MyException.DbAccessException exDb = new
	 * MyException.DbAccessException("DB00", argDataSourceName, ex, sql); throw
	 * exDb; } finally { //若在Config中設定開啟Log功能 if
	 * (Manuan.eBridge.Framework.Data.Global.AppConfig("open_log") == "1") {
	 * Manuan.eBridge.Framework.MyLogger.Write(sql, "", "Message",
	 * argDataSourceName); } }
	 * 
	 * return ret; }
	 */

	/**
	 * 
	 * 功能概述：
	 * 
	 * @param argSql
	 * @return
	 * @author lucky 创建时间：Jun 13, 2013 4:54:54 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         4:54:54 PM 修改备注：
	 * @version
	 * 
	 */
	public static HashMap<String, String> QueryRow(String argSql) {
		return QueryRow(argSql, "_DEFAULT_DATABASE");
	}

	// / <summary>
	// / 取得資料，以DataSet返回，指定DB，TABLE，Field,條件
	// / </summary>
	// / <param name="argDbName">资料庫名称</param>
	// / <param name="argDataSourceName">资料来源名称，如TABLE、View名称</param>
	// / <param name="argFields">栏位清单，以","号分隔</param>
	// / <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday =
	// '2000/09/01'</param>
	// / <returns>DataSet</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / 指定DB，TABLE，Field,條件，封裝類型
	// / </para>
	// / <para>[異動記錄]
	// / AUTHOR DATE NOTE
	// / ========== ========== ========================================
	// / Rayd 2012-11-07 Create
	// / </para>
	// / <example>
	// / <code>
	// / String cond = String.format("projectid={0}",new String[]{'1'});
	// / String jsonProject =
	// Query("CRM","PROJECT","PROJECTID,PROJECTNAME",cond);
	// / </code>
	// / </example>
	// / </remarks>

	/**
	 * 
	 * 功能概述：取得一行数据，依指定DB名称与SQL语句
	 * 
	 * @param argDbName
	 *            ：database名称
	 * @param argSql
	 *            ：SQL语句
	 * @return 符合条件的数据包对象
	 * @author lucky 创建时间：Jun 13, 2013 4:45:33 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         4:45:33 PM 修改备注：
	 * @version
	 * 
	 */
	public static HashMap<String, String> QueryRow(String argSql,
			String argDbName) {
		Connection db = null;
		Statement st = null;
		ResultSet rs = null;
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			if (argDbName == "_DEFAULT_DATABASE") {
				db = CreateDatabase();
			} else {
				db = CreateDatabase(argDbName);
			}
			st = db.createStatement();
			rs = st.executeQuery(argSql);
			map = ResultSet2Map(rs);

		} catch (Exception ex) {
			DALException exDb = new DALException("DB01", "", ex, argSql);
			// throw exDb;
		} finally {
			closeReultSet(rs);
			closeStatement(st);
			closeDatabase(db);
			// 若在Config中設定開啟Log功能
			if (Global.AppConfig("open_log").equals("1")) {
				MyLogger.Write(argSql, "", "Message", argDbName);
			}
		}
		return map;
	}

	// / <summary>
	// / 取得資料，以DataSet返回，指定DB，TABLE，Field,條件
	// / </summary>
	// / <param name="argDbName">资料庫名称</param>
	// / <param name="argDataSourceName">资料来源名称，如TABLE、View名称</param>
	// / <param name="argFields">栏位清单，以","号分隔</param>
	// / <param name="argCond">条件表达式，如：LastName = 'Rose' AND Birday =
	// '2000/09/01'</param>
	// / <returns>DataSet</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / 指定DB，TABLE，Field,條件，封裝類型
	// / </para>
	// / <para>[異動記錄]
	// / AUTHOR DATE NOTE
	// / ========== ========== ========================================
	// / Rayd 2012-11-07 Create
	// / </para>
	// / <example>
	// / <code>
	// / String cond = String.format("projectid={0}",new String[]{'1'});
	// / String jsonProject =
	// Query("CRM","PROJECT","PROJECTID,PROJECTNAME",cond);
	// / </code>
	// / </example>
	// / </remarks>

	/**
	 * 
	 * 功能概述：依DB名、table或view名，字段表达式、条件表达式取得数据
	 * 
	 * @param argDbName
	 *            ：DB名
	 * @param argDataSourceName
	 *            ：table或view名
	 * @param argFields
	 *            ：字段表达式
	 * @param argCond
	 *            条件表达式
	 * @return
	 * @author lucky 创建时间：Jun 13, 2013 4:50:40 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         4:50:40 PM 修改备注：
	 * @version
	 * 
	 */
	public static HashMap<String, String> QueryRow(String argDbName,
			String argDataSourceName, String argFields, String argCond) {

		String sql = "SELECT %1$s FROM %2$s WHERE %3$s";
		sql = String.format(sql, argFields, argDataSourceName, argCond);
		return QueryRow(sql, argDbName);
	}


	/**
	 * 
	 * 功能概述：
	 * 
	 * @param argDbName
	 * @param argTableName 表名
	 * @param argFieldList 字段名称表达式
	 * @param argValueList 新增值表达式
	 * @return
	 * @author lucky 创建时间：Jun 13, 2013 5:28:07 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         5:28:07 PM 修改备注：
	 * @version
	 * 
	 */
	public static int AddRow(String argDbName, String argTableName,	String argFieldList, String argValueList) {
		int ret = 0;
		Connection db = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "INSERT INTO %1$s(%2$s) VALUES( %3$s)";
		sql = String.format(sql, argTableName, argFieldList, argValueList);

		try {
			if (argDbName == "_DEFAULT_DATABASE") {
				db = CreateDatabase();
			} else {
				db = CreateDatabase(argDbName);
			}
			st = db.createStatement();
			ret = st.executeUpdate(sql);

		} catch (Exception ex) {
			DALException exDb = new DALException("DB01", argTableName, ex, sql);
			// throw exDb;
		} finally {
			closeReultSet(rs);
			closeStatement(st);
			closeDatabase(db);
			// 若在Config中設定開啟Log功能
			if (Global.AppConfig("open_log").equals("1")) {
				MyLogger.Write(sql, argTableName, "Message", argDbName);
			}
		}

		return ret;

	}

	/**
	 * 
	 * 功能概述：新增一行数据
	 * 
	 * @param argTableName 表名
	 * @param argFieldList 字段名称表达式
	 * @param argValueList 新增值表达式
	 * @return 增加成功笔数
	 * @author lucky 创建时间：Jun 13, 2013 5:28:07 PM 修改人：lucky 修改时间：Jun 13, 2013
	 *         5:28:07 PM 修改备注：
	 * @version
	 * 
	 */
	public static int AddRow(String argTableName, String argFieldList,
			String argValueList) {
		return AddRow("_DEFAULT_DATABASE", argTableName, argFieldList,
				argValueList);

	}

	// #endregion

	// #region DeleteRow

	// / <summary>
	// / 删除一资料行，依KEY栏
	// / </summary>
	// / <param name="argDbName">DataBase 名稱</param>
	// / <param name="argTableName">栏位清单</param>
	// / <param name="argWhere">值清单</param>
	// / <param name="argIsKey">區分多載用</param>
	// / <returns>成功刪除筆数</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / 指定DB，TABLE，Field,值
	// / </para>
	// / <para>[異動記錄]
	// / AUTHOR DATE NOTE
	// / ========== ========== ========================================
	// / Rayd 2005-02-11 Create
	// / </para>
	// / <example>
	// / <code>
	// / int count =
	// dalProject.DeleteRow("EQ","PROJECT","PROJECTID='001'",true);
	// / </code>
	// / </example>
	// / </remarks>

	/**
	 * 
	 * 功能概述：删除数据，依指定条件 Database名称，表名 
	 * @param argDbName DataBase 名稱
	 * @param argTableName 表名
	 * @param argWhere 条件
	 * @return 被删除的行数
	 * @author lucky  
	 * 创建时间：Jun 17, 2013 11:15:06 AM  
	 * 修改人：lucky
	 * 修改时间：Jun 17, 2013 11:15:06 AM  
	 * 修改备注：  
	 * @version  
	 *
	 */
	public static int DeleteRow(String argDbName, String argTableName, String argWhere) {
		int ret = 0;
		Connection db = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "DELETE FROM %1$s WHERE %2$s";
		sql = String.format(sql, argTableName, argWhere);

		try {
			if (argDbName == "_DEFAULT_DATABASE") {
				db = CreateDatabase();
			} else {
				db = CreateDatabase(argDbName);
			}
			st = db.createStatement();
			ret = st.executeUpdate(sql);

		} catch (Exception ex) {
			DALException exDb = new DALException("DB03", argTableName, ex, sql);
			// throw exDb;
		} finally {
			closeReultSet(rs);
			closeStatement(st);
			closeDatabase(db);
			// 若在Config中設定開啟Log功能
			if (Global.AppConfig("open_log").equals("1")) {
				MyLogger.Write(sql, argTableName, "Message", argDbName);
			}
		}

		return ret;
	}

	/**
	 * 
	 * 功能概述： 删除数据，依指定条件，表名 
	 * @param argTableName 表名
	 * @param argWhere 条件
	 * @return 被删除的行数
	 * @author lucky  
	 * 创建时间：Jun 17, 2013 11:20:57 AM  
	 * 修改人：lucky
	 * 修改时间：Jun 17, 2013 11:20:57 AM  
	 * 修改备注：  
	 * @version  
	 *
	 */
	public static int DeleteRow(String argTableName, String argWhere) {
		return DeleteRow("_DEFAULT_DATABASE", argTableName, argWhere);
	}

	// #region UpdateRow

	// / <summary>
	// / 修改一资料行，通用
	// / </summary>
	// / <param name="argDbName">Data Basec名稱</param>
	// / <param name="argTableName">待新增的TABLE</param>
	// / <param name="argFieldList">栏位清单</param>
	// / <param name="argValueList">值清单</param>
	// / <returns>成功修改行数</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / 指定DB，TABLE，Field,值
	// / </para>
	// / <para>[異動記錄]
	// / AUTHOR DATE NOTE
	// / ========== ========== ========================================
	// / Rayd 2005-02-11 Create
	// / </para>
	// / <example>
	// / <code>
	// / int count =
	// dalProject.UpdateRow("EQ","PROJECT","PROJECTNAME='testname'","projectid='001'");
	// / </code>
	// / </example>
	// / </remarks>
	public static int UpdateRow(String argDbName, String argTableName,
			String argSet, String argWhere) {
		int ret = 0;
		Connection db = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = "UPDATE %1$s SET %2$s WHERE %3$s}";
		sql = String.format(sql, argTableName, argSet, argWhere);
		try {
			if (argDbName == "_DEFAULT_DATABASE") {
				db = CreateDatabase();
			} else {
				db = CreateDatabase(argDbName);
			}
			st = db.createStatement();
			ret = st.executeUpdate(sql);

		} catch (Exception ex) {
			DALException exDb = new DALException("DB02", argTableName, ex, sql);
			// throw exDb;
		} finally {
			closeReultSet(rs);
			closeStatement(st);
			closeDatabase(db);
			// 若在Config中設定開啟Log功能
			if (Global.AppConfig("open_log").equals("1")) {
				MyLogger.Write(sql, argTableName, "Message", argDbName);
			}
		}

		return ret;
	}

	public static int UpdateRow(String argTableName, String argSet,
			String argWhere) {
		return UpdateRow("_DEFAULT_DATABASE", argTableName, argSet, argWhere);
	}

	// #endregion

	// / <summary>
	// / 取得單一欄值
	// / </summary>
	// / <param name="argField">欄名稱</param>
	// / <param name="argCond">條件</param>
	// / <returns>欄值，當沒有找到，返回null</returns>
	
	/**
	 * 
	 * 功能概述：  
	 * @param argDbName
	 * @param argDSN
	 * @param argFields
	 * @param argCond
	 * @return
	 * @author lucky  
	 * 创建时间：Jun 17, 2013 1:55:54 PM  
	 * 修改人：lucky
	 * 修改时间：Jun 17, 2013 1:55:54 PM  
	 * 修改备注：  
	 * @version  
	 *
	 */
	public static String GetValue(String argDbName, String argDSN,
			String argFields, String argCond) {
		String ret = null;
		HashMap<String, String> list = new HashMap<String, String>();

		list = QueryRow(argDbName, argDSN, argFields, argCond);
		if (argFields.indexOf(',') > 0) {
			ret = list.toString();
		} else {
			ret = list.get(argFields.toLowerCase());
		}

		return ret;
	}

	// / <summary>
	// / 取得單一欄值
	// / </summary>
	// / <param name="argField">欄名稱</param>
	// / <param name="argCond">條件</param>
	// / <returns>欄值，當沒有找到，返回null</returns>
	public static String GetValue(String argDbName, String argSql) {
		String ret = null;
		HashMap<String, String> list = new HashMap<String, String>();

		list = QueryRow(argSql, argDbName);

		if (list.size() == 1) {

			Iterator<?> it = list.entrySet().iterator();
			while (it.hasNext()) {
				@SuppressWarnings("unchecked")
				Map.Entry<String,String> testDemo = (Map.Entry<String,String>) it.next();

				ret = testDemo.getValue().toString();

			}

		} else {
			ret = list.toString();
		}

		return ret;
	}

	// / <summary>
	// / 取得某一欄位最大值
	// / </summary>
	// / <param name="argField">欄名稱</param>
	// / <param name="argCond">條件</param>
	// / <returns>欄值，當沒有找到，返回null</returns>
	public static String GetMaxValue(String argDbName, String argDSN,
			String argField, String argCond) {
		String ret = null;
		HashMap<String, String> list = new HashMap<String, String>();

		String fld = String.format("max(%1$s}) as maxvalue", argField);

		list = QueryRow(argDbName, argDSN, fld, argCond);
		ret = list.get("maxvalue");
		list.clear();

		return ret;
	}

	// / <summary>
	// / 取得某一欄位最大值
	// / </summary>
	// / <param name="argField">欄名稱</param>
	// / <param name="argCond">條件</param>
	// / <returns>欄值，當沒有找到，返回null</returns>
	public static String GetMaxValue(String argDbName, String argDSN,
			String argField, String argCondName, String argCondValue) {
		String cond = String
				.format("%1$s} = '%2$s'", argCondName, argCondValue);
		return GetMaxValue(argDbName, argDSN, argField, cond);
	}

	/**
	 * 
	 * 功能概述：取出ResultSet第一行的值
	 * 
	 * @param rs
	 * @author lucky 创建时间：Jun 14, 2013 11:22:08 AM 修改人：lucky 修改时间：Jun 14, 2013
	 *         11:22:08 AM 修改备注：
	 * @version
	 * 
	 */
	private static HashMap<String, String> ResultSet2Map(ResultSet rs) {
		ResultSetMetaData rsmd = null;
		HashMap<String, String> list = new HashMap<String, String>();

		int cols = 0;
		try {
			rsmd = rs.getMetaData();
			cols = rsmd.getColumnCount(); // 得到数据集的列数
			while (rs.next()) {
				for (int i = 1; i < cols + 1; i++) {
					switch (rsmd.getColumnType(i))
					{
					case Types.ARRAY:
						list.put(rsmd.getColumnName(i).toLowerCase(),rs.getArray(i).toString());
						/*
						java.sql.Array values = rs.getArray(i);
						//ary.
						StringBuilder sb= new StringBuilder();
						for（int j=0；i<rs.values;j++){
						    //sb.append(rs.getArray(i)[j]);

						}
						*/
						break;
					case Types.BINARY:
						list.put(rsmd.getColumnName(i).toLowerCase(),Byte.toString(rs.getByte(i)));
						break;
					case Types.VARBINARY:
						break;						
					case Types.BIT:
						//list.put(rsmd.getColumnName(i).toLowerCase(),Byte.toString(rs.getb));
						break;
					case Types.BLOB:
						break;
					case Types.BOOLEAN:
						list.put(rsmd.getColumnName(i).toLowerCase(),Boolean.toString(rs.getBoolean(i)));
						break;
					case Types.CHAR:
						break;
					case Types.CLOB:
						break;
					case Types.DATALINK:
						break;
					case Types.DISTINCT:
						break;
					case Types.JAVA_OBJECT:
						break;
					case Types.LONGVARBINARY:
						break;
					case Types.NCLOB:
						break;
					case Types.NULL:
						list.put(rsmd.getColumnName(i).toLowerCase(),"NULL");						
						break;
					case Types.OTHER:
						break;
					case Types.REF:
						break;
					case Types.ROWID:
						break;
					case Types.SQLXML:
						list.put(rsmd.getColumnName(i).toLowerCase(),rs.getSQLXML(i).toString());
						break;
					case Types.STRUCT:
						//list.put(rsmd.getColumnName(i).toLowerCase(),rs.gets.toString());
						break;
					case Types.DATE:
						list.put(rsmd.getColumnName(i).toLowerCase(),(rs.getDate(i).toString()));
						break;
					case Types.TIME:
						list.put(rsmd.getColumnName(i).toLowerCase(),(rs.getTime(i).toString()));
						break;
					case Types.TIMESTAMP:
						list.put(rsmd.getColumnName(i).toLowerCase(),(rs.getTimestamp(i).toString()));
						break;
						
					case Types.DECIMAL:
						list.put(rsmd.getColumnName(i).toLowerCase(),(rs.getBigDecimal(i).toString()));
						break;
					case Types.NUMERIC:
						list.put(rsmd.getColumnName(i).toLowerCase(),(rs.getBigDecimal(i).toString()));
						break;
					case Types.BIGINT:
						list.put(rsmd.getColumnName(i).toLowerCase(),Integer.toString(rs.getInt(i)));
						break;
					case Types.DOUBLE:
						list.put(rsmd.getColumnName(i).toLowerCase(),Double.toString(rs.getDouble(i)));
						break;
					case Types.FLOAT:
						list.put(rsmd.getColumnName(i).toLowerCase(),Float.toString(rs.getFloat(i)));
						break;
					case Types.SMALLINT:
						list.put(rsmd.getColumnName(i).toLowerCase(),Integer.toString(rs.getInt(i)));
						break;
					case Types.INTEGER:
						list.put(rsmd.getColumnName(i).toLowerCase(),Integer.toString(rs.getInt(i)));
						break;
					case Types.TINYINT:
						list.put(rsmd.getColumnName(i).toLowerCase(),Integer.toString(rs.getInt(i)));
						break;

					case Types.LONGNVARCHAR:
						list.put(rsmd.getColumnName(i).toLowerCase(),rs.getNString(i));
						break;
					case Types.NCHAR:
						list.put(rsmd.getColumnName(i).toLowerCase(),rs.getNString(i));
						break;
					case Types.NVARCHAR:
						list.put(rsmd.getColumnName(i).toLowerCase(),rs.getNString(i));
						break;
					case Types.VARCHAR:
						if (rs.getString(i) != null) {
							list.put(rsmd.getColumnName(i).toLowerCase(),rs.getString(i));
						}
						break;
					case Types.LONGVARCHAR:
						list.put(rsmd.getColumnName(i).toLowerCase(),rs.getString(i));
						break;
					default:
						break;
					}
				}
				//只取第一行
				break;
			}
		} catch (SQLException exSql) {
			MyLogger.Write(exSql.getMessage());
			exSql.printStackTrace();
		} finally {
			rsmd = null;
			closeReultSet(rs);

		}
		return list;
	}
	
    //
    //select * FROM  PROJECT WHERE....
    //insert into tablename(...) values(...);
    //update tablename set fld1=value,fld2=value2;
    //delete tablename where ...
    //可改用Regex的方式
	
	/**
	 * 
	 * 功能概述： 從基本的簡單的Sql語句中取得Table Name或View Name:SELECT /UPDATE/INSERT/DELETE 
	 * @param sql
	 * @param isQuery
	 * @return DSN 
	 * @author lucky  
	 * 创建时间：Jun 17, 2013 11:50:23 AM  
	 * 修改人：lucky
	 * 修改时间：Jun 17, 2013 11:50:23 AM  
	 * 修改备注：可改用Regex的方式  
	 * @version  
	 *
	 */
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
