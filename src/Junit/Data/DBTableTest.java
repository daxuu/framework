package Junit.Data;

import static org.junit.Assert.*;

import org.dom4j.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Framework.Data.DBTable;
import Framework.Xml.XmlDocModel;

public class DBTableTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetTableSchema() {
		String exp = "xconfig";
		XmlDocModel tbSchema = new XmlDocModel();
		
		DBTable tb = new DBTable();
		tb.setDatabaseName("test");
		tb.setTableName("STUDENT");
		tb.setDataSourceName("STUDENT");
		tbSchema = tb.getTableSchema();
		Document doc =tbSchema.getDocument();
		String act = doc.selectSingleNode(exp).getName();
		
		assertEquals(exp, act);
	}

	@Test
	public void testGetViewSchema() {
		//fail("Not yet implemented");
	}

}
