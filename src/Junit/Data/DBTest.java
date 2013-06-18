package Junit.Data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import Framework.Data.DB;

public class DBTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetOracl() {
		int ret=1;
		DB db = new DB();
		int actual = db.getOracl();
		assertSame(ret,  actual);
	}

	@Test
	public void testGetSql() {
		int ret=1;
		DB db = new DB();
		int actual = db.getSql();
		assertSame(ret,  actual);
	}

	@Test
	public void testGetSqlite() {
		int ret=1;
		DB db = new DB();
		int actual = db.getSqlite();
		assertSame(ret,  actual);
	}



}
