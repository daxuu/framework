
package Framework;

import java.util.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Types;  


public class Student {
	private Integer _id = 1;
	private String _stuNO = "001";
	private String _stuName = "Jack";
	private Integer _stuArg = 10;
	private String _birthday = "2000/1/1";
	private String _stuSex = "M";
	private String _updDay = "2000/1/1";
	
	
	public Integer getId() {
		return _id;
	}
	public void setId(Integer _id) {
		this._id = _id;
	}
	public String getStuNO() {
		return _stuNO;
	}
	public void setStuNO(String _stuNO) {
		this._stuNO = _stuNO;
	}
	public String getStuName() {
		return _stuName;
	}
	public void setStuName(String _stuName) {
		this._stuName = _stuName;
	}
	public Integer getStuArg() {
		return _stuArg;
	}
	public void setStuArg(Integer _stuArg) {
		this._stuArg = _stuArg;
	}
	public String getBirthday() {
		return _birthday;
	}
	public void setBirthday(String _birthday) {
		this._birthday = _birthday;
	}
	public String getStuSex() {
		return _stuSex;
	}
	public void setStuSex(String _stuSex) {
		this._stuSex = _stuSex;
	}
	public String getUpdDay() {
		return _updDay;
	}
	public void setUpdDay(String _updDay) {
		this._updDay = _updDay;
	}
	
	public List GetStudents(){
	    //prepare a selected Student
	    Student selected = new Student();
	    
	    //prepare the example Student List
	    int count = 30;
	    List studentList = (List) new ArrayList();
	    
	    //  self.setVariable("StudentList", StudentList, false);
	
	    for(int j= 0; j < count; ++j) {
	      Student studentx = new Student();
	      if (j == 1) {
	      	selected = studentx;
	      }
	      //setupStudent(studentx, j);
	      ((ArrayList) studentList).add(studentx);
	    }
		return studentList;
	}
	
	
	public int  save() throws Exception {
		//load driver and get a database connetion
		//Note: It is usually better to use connection pool. Please consult
		//the manual of your Web server. Or, refer to the Developer's Guide
		int ret = 0;
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		//Class.forName("oracle.jdbc.OracleDriver");
		//String url = "jdbc:oracle:thin:@172.18.60.114:1521:test"
		//new oracle.jrockit
	    Class.forName("oracle.jdbc.driver.OracleDriver");
        //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        String url = "jdbc:oracle:thin:@172.18.60.114:1521:test";		
		
		Connection con = DriverManager.getConnection(url,"gdi", "gdi");
		PreparedStatement stmt = con.prepareStatement("INSERT INTO student(stu_no,name) values(?, ?)");
		
		//insert what end user entered into database table
		stmt.setString(1, "02");
		stmt.setString(2, "name 02");
		//stmt.setString(3, selected.stuName);
		//stmt.setString(4, selected.stuName);
		//execute the statement
		ret = stmt.executeUpdate();
		//commit
		con.commit();
		//close the jdbc connection
		con.close();
		return ret;
	} 
}
