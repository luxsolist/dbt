package luxk.jdbt;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import luxk.jdbt.mockjdbc.MockConnection;
import luxk.jdbt.mockjdbc.MockDriver;
import luxk.jdbt.mockjdbc.MockResultSet;
import luxk.jdbt.mockjdbc.MockStatement;

public class MockJDBCTest {
	
	private static MockDriver drv;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		drv = new MockDriver();
		DriverManager.registerDriver(drv);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		DriverManager.deregisterDriver(drv);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMockJDBC() throws Exception {
		Connection con = DriverManager.getConnection("");
		assertTrue("check is MockConnection", con instanceof MockConnection);
		
		Statement stmt1 = con.createStatement();
		assertTrue("check is MockStatement", stmt1 instanceof MockStatement);
		
		int ret = stmt1.executeUpdate("xxx");
		assertEquals("checking return of MockStatement.executeUpdate", 10, ret);
		
		ResultSet rs = stmt1.executeQuery("sql");
		assertTrue("check is MockResultSet", rs instanceof MockResultSet);
		
		int rowCnt = 0;
		while(rs.next()) {
			rowCnt++;
			for(int i = 0; i < 30; i++) {
				assertEquals("check is valid result column(index)",
						String.format("MockResultSet.getString(int %d)", i), rs.getString(i));
			}
			assertEquals("check is valid result column(name)",
					"MockResultSet.getString(String xXx)", rs.getString("xXx"));
		}
		assertEquals("check result row count", 23, rowCnt);
		
		rs.close();
		stmt1.close();
		con.close();
	}

}
