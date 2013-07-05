package Junit.Helper;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Framework.Helper.Tools;
import Framework.Xml.XmlDocModel;

public class ToolsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSafeJSON() {
		//fail("Not yet implemented");
	}

	@Test
	public void testList2Node() {
		//fail("Not yet implemented");
	}

	@Test
	public void testXmlToJSON() {
		int exp = 0;
		XmlDocModel xDoc = new XmlDocModel();
		String filename = "/media/lucky/DATA/Apps/ebridge/Workspace-Man/Framework/data/schema/student.xml";
		
		xDoc.load(filename);
		String s_xml =  "<node><key>value1</key></node>";
		String jsonString = Tools.XmlToJSON(xDoc, "");
		//String jsonString = Tools.XmlToJSON(s_xml, "");
		int act = jsonString.indexOf('[');
		assertEquals(act, exp);
		
	}

}
