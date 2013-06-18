package Framework.Data;

public enum dbType {
	sqlite, sqlserver, oracle, db2, mysql, sybase, Informix, postgresql, access
}

/*
 * Java数据库连接（JDBC）由一组用 Java 编程语言编写的类和接口组成。JDBC 为工具/数据库开发人员提供了一个标准的
 * API，使他们能够用纯Java API
 * 来编写数据库应用程序。然而各个开发商的接口并不完全相同，所以开发环境的变化会带来一定的配置变化。本文主要集合了不同数据库的连接方式。
 * 
 * 一、连接各种数据库方式速查表
 * 
 * 　　下面罗列了各种数据库使用JDBC连接的方式，可以作为一个手册使用。
 * 
 * 　　1、Oracle8/8i/9i数据库（thin模式）
 * 
 * Class.forName("oracle.jdbc.driver.OracleDriver").newInstance(); String
 * url="jdbc:oracle:thin:@localhost:1521:orcl"; //orcl为数据库的SID String
 * user="test"; String password="test"; Connection conn=
 * DriverManager.getConnection(url,user,password);
 * 
 * 　　2、DB2数据库
 * 
 * Class.forName("com.ibm.db2.jdbc.app.DB2Driver ").newInstance(); String
 * url="jdbc:db2://localhost:5000/sample"; //sample为你的数据库名 String user="admin";
 * String password=""; Connection conn=
 * DriverManager.getConnection(url,user,password);
 * 
 * 　　3、Sql Server7.0/2000数据库
 * 
 * Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
 * String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=mydb";
 * //mydb为数据库 String user="sa"; String password=""; Connection conn=
 * DriverManager.getConnection(url,user,password);
 * 
 * 　　4、Sybase数据库
 * 
 * Class.forName("com.sybase.jdbc.SybDriver").newInstance(); String url
 * =" jdbc:sybase:Tds:localhost:5007/myDB";//myDB为你的数据库名 Properties sysProps =
 * System.getProperties(); SysProps.put("user","userid");
 * SysProps.put("password","user_password"); Connection conn=
 * DriverManager.getConnection(url, SysProps);
 * 
 * 　　5、Informix数据库
 * 
 * Class.forName("com.informix.jdbc.IfxDriver").newInstance(); String url =
 * "jdbc:informix-sqli://123.45.67.89:1533/myDB:INFORMIXSERVER=myserver;
 * user=testuser;password=testpassword"; //myDB为数据库名 Connection conn=
 * DriverManager.getConnection(url);
 * 
 * 　　6、MySQL数据库
 * 
 * Class.forName("org.gjt.mm.mysql.Driver").newInstance(); String url =
 * "jdbc:mysql://localhost/myDB?user=soft&password=soft1234&useUnicode=true&characterEncoding=8859_1"
 * //myDB为数据库名 Connection conn= DriverManager.getConnection(url);
 * 
 * 　　7、PostgreSQL数据库
 * 
 * Class.forName("org.postgresql.Driver").newInstance(); String url
 * ="jdbc:postgresql://localhost/myDB" //myDB为数据库名 String user="myuser"; String
 * password="mypassword"; Connection conn=
 * DriverManager.getConnection(url,user,password);
 * 
 * 　　8、access数据库直连用ODBC的
 * 
 * Class.forName("sun.jdbc.odbc.JdbcOdbcDriver") ; String
 * url="jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ="
 * +application.getRealPath("/Data/ReportDemo.mdb"); Connection conn =
 * DriverManager.getConnection(url,"",""); Statement
 * stmtNew=conn.createStatement() ; 9、Sqlite
 * 用"org.sqlite.JDBC"作为JDBC的驱动程序类名。连接JDBC的URL格式为jdbc
 * :sqlite:/path。这里的path为指定到SQLite数据库文件的路径，例如: jdbc:sqlite://dirA/dirB/dbfile
 * jdbc:sqlite://DRIVE:/dirA/dirB/dbfile
 * jdbc:sqlite://COMPUTERNAME/shareA/dirB/dbfile
 */