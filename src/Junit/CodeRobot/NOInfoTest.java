package Junit.CodeRobot;

import static org.junit.Assert.*;

import java.io.Console;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Framework.CodeRobot.NOInfo;

public class NOInfoTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}



	@Test
	public void testFormatDate() {
		int act = 10;
		NOInfo noInfo = new NOInfo();
		String ret = noInfo.formatDate("yyyy-MM-dd");
		//asser
		assertSame(ret.length(),act);
		
	}

	@Test
	public void testMakeCode() {
		//int act = 12;
		int exp = 1;
		NOInfo noInfo = new NOInfo();
		String ret ="";
		for(int i =0;i<1;i++){
			//ret = noInfo.MakeCode("/media/lucky/DATA/Apps/ebridge/Workspace-Man/elearning/WebContent/data/codeset/spec_no.xml", 10);
			ret = noInfo.MakeCode("spec_no", exp);
			System.out.println(ret);
		}
		
		//asser
		assertSame(exp,ret.split(",").length);
		
	}
}
