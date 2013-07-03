
package Framework.Data;

import java.io.Closeable;
import java.io.Console;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;

import javax.security.auth.login.LoginContext;

import org.apache.log4j.Logger;

/**
 * @author ken
 * @Time 2011-12-10
 * SQL API 学习
 */
public class DbInfo {

	private static Logger log = Logger.getLogger(DbInfo.class);
	private static String  DabaseName = "test";
	

	
	public static String getDabaseName() {
		return DabaseName;
	}

	public static void setDabaseName(String dabaseName) {
		DabaseName = dabaseName;
	}

	/**
	 * 取一个数据库中所有表的信息
	 * @throws SQLException 
	 */
	public static void demoDB(){
		Connection conn = null;
		ResultSet tSet = null;
		ResultSet primaryKey = null;
		ResultSet foreinKey = null;
		ResultSet tableType = null;
		ResultSet rsSet = null;
		ResultSet tableTypes = null;
		try{
			//System.out.println();
			conn = DALHelper.CreateDatabase(getDabaseName());
			System.out.println("######  DatabaseMetaData关于数据库的整体综合信息====");
			java.sql.DatabaseMetaData dbmd = conn.getMetaData();

			System.out.println("数据库产品名: " + dbmd.getDatabaseProductName());
			System.out.println("数据库是否支持事务: " + dbmd.supportsTransactions());
			System.out.println("数据库产品的版本号:"+dbmd.getDatabaseProductVersion());
			System.out.println("数据库的默认事务隔离级别:"+dbmd.getDefaultTransactionIsolation());
			System.out.println("支持批量更新:"+dbmd.supportsBatchUpdates());
			System.out.println("DBMS 的 URL:"+dbmd.getURL());
			System.out.println("数据库的已知的用户名称:"+dbmd.getUserName());
			System.out.println("数据库是否处于只读模式:"+dbmd.isReadOnly());
			System.out.println("数据库是否支持为列提供别名:"+dbmd.supportsColumnAliasing());
			System.out.println("是否支持指定 LIKE 转义子句:"+dbmd.supportsLikeEscapeClause());
			System.out.println("是否为外连接提供受限制的支持:"+dbmd.supportsLimitedOuterJoins());
			System.out.println("是否允许一次打开多个事务:"+dbmd.supportsMultipleTransactions());
			System.out.println("是否支持 EXISTS 表达式中的子查询:"+dbmd.supportsSubqueriesInExists());
			System.out.println("是否支持 IN 表达式中的子查询:"+dbmd.supportsSubqueriesInIns());
			System.out.println("是否支持给定事务隔离级别:"+dbmd.supportsTransactionIsolationLevel(1));
			System.out.println("此数据库是否支持事务:"+dbmd.supportsTransactions());
			System.out.println("此数据库是否支持 SQL UNION:"+dbmd.supportsUnion());
			System.out.println("此数据库是否支持 SQL UNION ALL:"+dbmd.supportsUnionAll());
			System.out.println("此数据库是否为每个表使用一个文件:"+dbmd.usesLocalFilePerTable());
			System.out.println("此数据库是否将表存储在本地文件中:"+dbmd.usesLocalFiles());
			System.out.println("底层数据库的主版本号:"+dbmd.getDatabaseMajorVersion());
			System.out.println("底层数据库的次版本号:"+dbmd.getDatabaseMinorVersion());
			
			System.out.println("JDBC 驱动程序的主版本号:"+dbmd.getJDBCMajorVersion());
			System.out.println("JDBC 驱动程序的次版本号:"+dbmd.getJDBCMinorVersion());
			System.out.println("JDBC 驱动程序的名称:"+dbmd.getDriverName());
			System.out.println("JDBC 驱动程序的 String 形式的版本号:"+dbmd.getDriverVersion());
			
			System.out.println("可以在不带引号的标识符名称中使用的所有“额外”字符:"+dbmd.getExtraNameCharacters());
			System.out.println("用于引用 SQL 标识符的字符串:"+dbmd.getIdentifierQuoteString());
			System.out.println("允许用于类别名称的最大字符数:"+dbmd.getMaxCatalogNameLength());
			System.out.println("允许用于列名称的最大字符数:"+dbmd.getMaxColumnNameLength());
			System.out.println("允许在 GROUP BY 子句中使用的最大列数:"+dbmd.getMaxColumnsInGroupBy());
			System.out.println("允许在 SELECT 列表中使用的最大列数:"+dbmd.getMaxColumnsInSelect());
			System.out.println("允许在表中使用的最大列数:"+dbmd.getMaxColumnsInTable());
			System.out.println("数据库的并发连接的可能最大数:"+dbmd.getMaxConnections());
			System.out.println("允许用于游标名称的最大字符数:"+dbmd.getMaxCursorNameLength());
			System.out.println("在同一时间内可处于开放状态的最大活动语句数:"+dbmd.getMaxStatements());
			
			//获取所有表 new String[]{"TABLE"} 
			//String[] type = {"TABLE","VIEW"}  null
			System.out.println("###### 获取表的信息");
			tSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
			int tableCount=0;
	        while (tSet.next()) {
	        	//oracle 排除系统表
	        	if(tSet.getString("TABLE_NAME").indexOf("PB")==0 || tSet.getString("TABLE_NAME").indexOf('$')>0){
	        		continue;
	        	}
	        	tableCount++;
	            System.out.println(tableCount+"_表类别:"+tSet.getString("TABLE_CAT")+"_表模式:"+tSet.getString("TABLE_SCHEM")
	            		+"_表名称:"+tSet.getString("TABLE_NAME")+"_表类型:"+tSet.getString("TABLE_TYPE")
	            		//+"\n_表的解释性注释:"+tSet.getString("REMARKS")+"_类型的类别:"+tSet.getString("TYPE_CAT")
	            		//+"\n_类型模式:"+tSet.getString("TYPE_SCHEM")+"_类型名称:"+tSet.getString("TYPE_NAME")
	            		//+"\n_有类型表的指定'identifier'列的名称:"+tSet.getString("SELF_REFERENCING_COL_NAME")
	            		//+"\n_指定在 SELF_REFERENCING_COL_NAME 中创建值的方式:"+tSet.getString("REF_GENERATION")
	            		);
	            //2_表类别:MANOR_表模式:PUBLIC_表名称:SYS_RESOURCE_表类型:TABLE
	            String tableName = tSet.getString("TABLE_NAME");
	            String sql = "select * from " + tableName + " where 1>1";
	            rsSet = conn.createStatement().executeQuery(sql);
	    		ResultSetMetaData rsData = rsSet.getMetaData();
	    		for (int i = 1; i <= rsData.getColumnCount(); i++) {
					System.out.println("==列的信息:获取SQL语句的列名:"
							+rsData.getColumnName(i)+":"+rsData.getColumnTypeName(i)+"("+rsData.getPrecision(i)+")"
							+" Type: " + rsData.getColumnType(i)
							+" 大小写敏感: "+rsData.isCaseSensitive(i)
							+" isReadOnly:"+rsData.isReadOnly(i) 
							+" ClassName" +rsData.getColumnClassName(i));
					//==列的信息:获取SQL语句的列名:LIMITLEVER(LIMITLEVER,5,java.lang.Short) 列宽5 大小写敏感true isReadOnly:false
				}
	    		
		        System.out.println("###### 表的主键列信息");
		        //ResultSet primaryKey = dbmd.getPrimaryKeys("MANOR","PUBLIC","SYS_ROLE_RES");
		        primaryKey = dbmd.getPrimaryKeys(null,null,tSet.getString("TABLE_NAME"));
		        while(primaryKey.next()){
		         System.out.println("表名:"+primaryKey.getString("TABLE_NAME")+",列名:"+primaryKey.getString("COLUMN_NAME")
		        		 +" 主键名:"+primaryKey.getString("PK_NAME"));
		         //表名:SYS_ROLE_RES,列名:SYS_RES_ID 主键名:CONSTRAINT_9
		         //表名:SYS_ROLE_RES,列名:SYS_ROLE_ID 主键名:CONSTRAINT_9
		        }
		        
//		        System.out.println("###### 表的外键列信息");
//		        //foreinKey = dbmd.getImportedKeys("MANOR","PUBLIC","SYS_ROLE_RES");
//		        foreinKey = dbmd.getImportedKeys(null,null,tSet.getString("TABLE_NAME"));
//		        while(foreinKey.next()){
//		        	System.out.println("主键名:"+foreinKey.getString("PK_NAME")+",外键名:"+foreinKey.getString("FKCOLUMN_NAME")
//		        			+",主键表名:"+foreinKey.getString("PKTABLE_NAME")+",外键表名:"+foreinKey.getString("FKTABLE_NAME")
//		        			+",外键列名:"+foreinKey.getString("PKCOLUMN_NAME")+",外键序号:"+foreinKey.getString("KEY_SEQ"));
//		        	//主键名:PRIMARY_KEY_95,外键名:SYS_RES_ID,主键表名:SYS_RESOURCE,外键表名:SYS_ROLE_RES,外键列名:ID,外键序号:1
//		        	//主键名:PRIMARY_KEY_A,外键名:SYS_ROLE_ID,主键表名:SYS_ROLE,外键表名:SYS_ROLE_RES,外键列名:ID,外键序号:1
//		        }
	    		
			}
	        tSet.close();
	        
	        System.out.println("###### 获取当前数据库所支持的SQL数据类型");
	        tableType = dbmd.getTypeInfo();
	        while(tableType.next()){
	        	System.out.println("数据类型名:"+tableType.getString(1)
	        		 +",短整型的数:"+tableType.getString(2)
	        		 +",整型的数:"+tableType.getString(3)
	        		 +",最小精度:"+tableType.getString(14)
	        		 +",最大精度:"+tableType.getString(15));
	        	//数据类型名:TIMESTAMP,短整型的数:93,整型的数:23,最小精度:0,最大精度:10
	        	//数据类型名:VARCHAR,短整型的数:12,整型的数:2147483647,最小精度:0,最大精度:0
	        }
	         

	        
	        System.out.println("###### 获取数据库中允许存在的表类型");
	        tableTypes = dbmd.getTableTypes();
	        while(tableTypes.next()){
	        	System.out.println("类型名:"+tableTypes.getString(1));
	        	/** H2
	        	 类型名:SYSTEM TABLE
	        	 类型名:TABLE
	        	 类型名:TABLE LINK
	        	 类型名:VIEW
	        	 */
	        }
	        //此外还可以获取索引等的信息
	        //conn.close();
		}catch(SQLException e1){
			System.out.println(e1.getMessage());
		}finally{
			
			DALHelper.closeDatabase(conn);

			DALHelper.closeReultSet(tSet);
			DALHelper.closeReultSet(primaryKey);
			DALHelper.closeReultSet(foreinKey);
			DALHelper.closeReultSet(tableType);
			DALHelper.closeReultSet(rsSet);
			DALHelper.closeReultSet(tableTypes);
		}
		
		

	}
	
	/**
	 * PreparedStatement 信息
	 * ResultSetMetaData 信息
	 * @throws SQLException
	 */
	public static void getDBParameterMetaData() {
		Connection conn = null;
		ResultSet rs = null;
		try{
			//Connection conn = JDBCUtil.getConnection(); //id,name
			conn = DALHelper.CreateDatabase(getDabaseName());
			PreparedStatement pre = conn.prepareStatement("SELECT * FROM SYS_APPTYPE where id = ?");
			pre.setInt(1, 3);
			java.sql.ParameterMetaData pmd = pre.getParameterMetaData();
			System.out.println("参数的个数:"+pmd.getParameterCount());
			System.out.println("获取指定参数的 SQL 类型:"+pmd.getParameterType(1));
			System.out.println("culomn的参数类型:"+pmd.getParameterTypeName(1));
			System.out.println("Java 类的完全限定名称:"+pmd.getParameterClassName(1));
			System.out.println("获取指定参数的模式:"+pmd.getParameterMode(1));
			System.out.println("获取指定参数的指定列大小:"+pmd.getPrecision(1));
			System.out.println("获取指定参数的小数点右边的位数:"+pmd.getScale(1));
			System.out.println("是否允许在指定参数中使用 null 值:"+pmd.isNullable(1));
			System.out.println("指定参数的值是否可以是带符号的数字:"+pmd.isSigned(1));
			
			//获取结果集元数据
			rs = pre.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString(1)+"___"+rs.getString(2));
			}
			rs.close();
		}catch(SQLException e1){
			System.out.println(e1.getMessage());
		}finally{
			DALHelper.closeDatabase(conn);
			DALHelper.closeReultSet(rs);
		}

	}

	/**
	 * 获取所有Driver信息
	 */
	public static void getAllDriverMsg(){
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while(drivers.hasMoreElements()) {
			Driver d = drivers.nextElement();
		    System.out.println(d.getClass().getName()+"_"+d.getMajorVersion());
		}

	}
}

