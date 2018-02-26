package luxk.jdbt;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExecBlockCaseTest {
	
	private static MainContext mCtx;
	private static ExecBlockCase execCase;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		URL url = ExecBlockCaseTest.class.getResource("config_v1_test.xml");
		mCtx = new MainContext();
		mCtx.loadConfig(url.getPath());
		execCase = new ExecBlockCase("Test Case", null, mCtx);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testDBVersion() throws Exception {
		
		// ignore null/empty string
		execCase.setDatabaseVersion(null);
		execCase.setDatabaseVersion("");
		
		assertTrue("check to accept all DB versions(after initialized)",
				execCase.checkDatabaseVersion("9999.9999.111111.123242343.123456789"));
		
		execCase.setDatabaseVersion("1234567");
		assertTrue("check to accept DB versions", execCase.checkDatabaseVersion("1234567"));
		assertFalse("check to reject DB versions(false to smaller)", execCase.checkDatabaseVersion("1234566"));
		assertFalse("check to reject DB versions(false to bigger)", execCase.checkDatabaseVersion("1234568"));
		
		execCase.setDatabaseVersion("1.23.45.67");
		assertTrue("check to accept DB versions", execCase.checkDatabaseVersion("1.23.45.67"));
		assertFalse("check to reject DB versions(false to smaller)", execCase.checkDatabaseVersion("1.23.45.65"));
		assertFalse("check to reject DB versions(false to bigger)", execCase.checkDatabaseVersion("1.23.45.68"));
		
		execCase.setDatabaseVersion("1.23-45.67.89.10");
		assertTrue("check to accept DB versions", execCase.checkDatabaseVersion("20.23.45.67"));
		assertFalse("check to reject DB versions(false to smaller)", execCase.checkDatabaseVersion("1.22.45.65"));
		assertFalse("check to reject DB versions(false to bigger)", execCase.checkDatabaseVersion("46"));
		
		execCase.setDatabaseVersion("1.23.45-67.89.10");
		assertTrue("check to accept DB versions", execCase.checkDatabaseVersion("1.23.45.00.00"));
		assertTrue("check to accept DB versions", execCase.checkDatabaseVersion("1.23.45.67"));
		assertTrue("check to accept DB versions", execCase.checkDatabaseVersion("67.89.10.00"));
		assertFalse("check to reject DB versions(false to smaller)",
				execCase.checkDatabaseVersion("1.23.44.99999.9999999"));
		assertFalse("check to reject DB versions(false to bigger)", execCase.checkDatabaseVersion("67.89.10.00.01"));

		execCase.setDatabaseVersion("1.2.3.4.5678-");
		assertTrue("check to accept DB versions", execCase.checkDatabaseVersion("1.2.3.4.5678"));
		assertTrue("check to accept DB versions", execCase.checkDatabaseVersion("1.2.3.4.5679"));
		assertFalse("check to reject DB versions(false to smaller)", execCase.checkDatabaseVersion("1.2.3.4.5677"));

		execCase.setDatabaseVersion("-1.2.3.4.5678");
		assertTrue("check to accept DB versions", execCase.checkDatabaseVersion("1.2.3.4.5678"));
		assertTrue("check to accept DB versions", execCase.checkDatabaseVersion("1.2.3.4.5677"));
		assertFalse("check to reject DB versions(false to bigger)", execCase.checkDatabaseVersion("1.2.3.4.5679"));

		execCase.setDatabaseVersion("1.2.3.4.5678-2.3.4.5.6789");
	}
	
	@Test(expected=DBTException.class)
	public void testDBVersionExceptionDashOnly() throws Exception {
		execCase.setDatabaseVersion("-");
	}

	@Test(expected=DBTException.class)
	public void testDBVersionExceptionTooManyNum1() throws Exception {
		execCase.setDatabaseVersion("1.23.45.67.89.10");
	}

	@Test(expected=DBTException.class)
	public void testDBVersionExceptionTooManyNum2() throws Exception {
		execCase.setDatabaseVersion("-1.23.45.67.89.10");
	}

	@Test(expected=DBTException.class)
	public void testDBVersionExceptionTooManyNum3() throws Exception {
		execCase.setDatabaseVersion("1.23.45.67.89.10-");
	}

	@Test(expected=DBTException.class)
	public void testDBVersionExceptionTooManyNum4() throws Exception {
		execCase.setDatabaseVersion("1.2.3.4.5678-2.3.4.5.6789-3.4.5.6.7890");
	}
	
	@Test(expected=DBTException.class)
	public void testDup() throws Exception {
		execCase.duplicate(null);
	}
	
	@Test(expected=DBTException.class)
	public void testPrepare() throws Exception {
		execCase.prepare();
	}
	
	@Test(expected=DBTException.class)
	public void testRun() throws Exception {
		execCase.run();
	}
	
	@Test(expected=DBTException.class)
	public void testCleanup() throws Exception {
		execCase.cleanup();
	}
}
