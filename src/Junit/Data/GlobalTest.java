package Junit.Data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Framework.CodeRobot.NOInfo;

public class GlobalTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAppConfig() {
		String exp = "codeset";
		
		String act ="";
		for(int i =0;i<1;i++){
			act = Framework.Data.Global.AppConfig("path");
			System.out.println(act);
		}
		
		//asser
		assertEquals(exp,act);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAppDir() {
		String exp = "/media/lucky/DATA/Apps/ebridge/Workspace-Man/Framework";
		
		String act ="";
		for(int i =0;i<1;i++){
			act = Framework.Data.Global.AppDir();
			System.out.println(act);
		}
		
		//asser
		assertEquals(exp,act);
	}
}
