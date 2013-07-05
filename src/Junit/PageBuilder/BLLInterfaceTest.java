package Junit.PageBuilder;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Framework.Data.BLLInterface;
import Framework.Xml.XmlDocModel;
import Framework.logger.PM_LOG;

public class BLLInterfaceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExport() {
		String exp = "";
		BLLInterface bll = new BLLInterface();
		PM_LOG log = new PM_LOG();
		String sql =  "select * from pm_log";
		XmlDocModel x_data = log.QueryXml(sql); 
		String act = bll.Export(x_data, "pm_log", "<log_msg/><log_obj/><log_content/><datatime/>");
		
		assertEquals(exp, act);
		
	}

}
