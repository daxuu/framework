package Junit.Data;

import static org.junit.Assert.*;

import java.sql.SQLClientInfoException;

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
}
