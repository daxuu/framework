package Junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Framework.*;
import Framework.CodeRobot.Coder;

public class StudentTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void SaveTest() throws Exception {
		int ret=1;
		Student student = new Student();
		int actual = student.save();
		assertSame(ret,  actual);

		
	}

}
