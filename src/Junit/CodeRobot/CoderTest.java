package Junit.CodeRobot;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Framework.CodeRobot.Coder;

public class CoderTest {
	@SuppressWarnings("unused")
	//private Coder coder;
	@Before
	public void setUp() throws Exception {
		//super.setUp();
		//coder = new Framework.CodeRobot.Coder();
	}

	@After
	public void tearDown() throws Exception {
	}



	/**
	 * Test method for {@link Framework.CodeRobot.Coder#getGUID()}.
	 */
	@Test
	public void testGetGUID() {
		String actual="ced7dacc-67b1-4803-af11-0da3520d29c0";
		String ret = Coder.getCoder().getGUID();
		assertSame(ret.length(),  actual.length());
		//fail("Not yet implemented");
	}

}
