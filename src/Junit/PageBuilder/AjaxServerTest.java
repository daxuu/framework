package Junit.PageBuilder;

import static org.junit.Assert.*;

import java.awt.Frame;

import org.apache.bcel.generic.NEW;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.FrameworkField;

import Framework.Data.Global;
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
		String exp = "1";
		AjaxServer ajax = AjaxServer.GetInvoker();
		//String act = (String)ajax.invoke("Framework.logger.PM_LOG", "IsExist", new String[]{"string","string"}, new String[]{"log_id","31b99904-35ab-4d1b-bd5c-743dbb6ecc1a"});
		//String act = (String)ajax.invoke("Framework.logger.PM_LOG", "Test", new String[]{"string"}, new String[]{"Lucky"});
		//AddRow
		Integer act = (Integer)ajax.invoke("Framework.logger.PM_LOG", "AddRow", 
				new String[]{
				"log",
				"pm_log",
				"log_id,log_msg",
				"'" + Framework.CodeRobot.NORobot.Guid() +"','test msg :" + Framework.CodeRobot.NORobot.Guid()+ "'"
				}
		);
		assertEquals(exp, Integer.toString(act));
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

