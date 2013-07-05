package Junit.Data;

import static org.junit.Assert.*;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Framework.Data.DALHelper;
import Framework.Exception.DALException;

public class DALHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSchema() {
		//fail("Not yet implemented");
	}

	@Test
	public void testToXml() {
		String expString ="";
		//DALHelper.ToXml(rs);
	}
	@Test
	public void testExecuteNonQuery() {
		int exp =  2;
		
		String sql = "update MS_ALLKIND set DATAER='18221' where ALLKIND_ID ='0' OR ALLKIND_ID ='1'";
		
		int act =DALHelper.ExecuteNonQuery(sql,"eq");
		
		assertSame(exp, act);
		//DALHelper.ToXml(rs);
	}
	
	@Test
	public void testGetValue() {
		String exp =  "否";
		
		String sql = "select allkind_name from MS_ALLKIND where allkind_id='0'";
		
		String act =DALHelper.GetValue("eq", sql);
		
		assertEquals(exp, act);
		//DALHelper.ToXml(rs);
	}
	
	
	@Test
	public void testGetPrimaryKey() {
		String exp = "log_id";
		ArrayList<String> ret = DALHelper.getPrimaryKey("pm_log", "log");
		//DALHelper.getPrimaryKey("STUDENT", "test");
		//DALHelper.getPrimaryKey("pfs_usermessage", "pfs");
		
		assertTrue(ret.contains(exp));
	
		//DALHelper.ToXml(rs);
	}
	
	@Test
	public void testQueryJSON() {
		int exp =  0;
		//String sqlString  = "select * from pm_log";
		//String sqlString  = "select * from pfs_userm where user_id=181241";
		String sqlString  = "select * from student";
		String ret = DALHelper.QueryJson(sqlString, "test");
		
		sqlString  = "select * from student";
		ret = DALHelper.QueryJson(sqlString, "test",1,10);
		
		assertEquals(exp, ret.indexOf('{'));
	}
}
