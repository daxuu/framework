package Junit.PageBuilder;

import static org.junit.Assert.*;

import java.awt.Frame;

import org.apache.bcel.generic.NEW;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.FrameworkField;

import Framework.PageBuilder.AjaxServer;
import Framework.logger.PM_LOG;

public class AjaxServerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInvoker() {
		AjaxServer exp = null;
		
		AjaxServer act = AjaxServer.GetInvoker();
		
		assertNotNull(act);
	}

	@Test
	public void testInvoke() {
		//String exp = "PM.BLL.PM_PROJECT,PM.BLL:Save";
		String exp = "Hello,Lucky";
		AjaxServer ajax = AjaxServer.GetInvoker();
		//String act = (String)ajax.invoke("Framework.logger.PM_LOG", "GetValue", new String[]{"string","string","string"}, new String[]{"log_id","31b99904-35ab-4d1b-bd5c-743dbb6ecc1a","log_msg"});
		String act = (String)ajax.invoke("Framework.logger.PM_LOG", "Test", new String[]{"string"}, new String[]{"Lucky"});
		assertEquals(exp, act);
	}

	@Test
	public void testGetMethodClass() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetMethodObject() {
		//fail("Not yet implemented");
	}

}

