package Junit.Data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Framework.Data.DbInfo;

public class DbInfoTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDemoDB() {
		
		DbInfo.setDabaseName("log");
		DbInfo.demoDB();
	}

	@Test
	public void testGetDBParameterMetaData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllDriverMsg() {
		fail("Not yet implemented");
	}

}
