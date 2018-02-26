package luxk.jdbt;

import static org.junit.Assert.*;

import java.net.URL;
import java.sql.DriverManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import luxk.jdbt.mockjdbc.MockDriver;

public class PerformanceTestTest {
	
	private static MockDriver drv;
	
	private static MainContext mCtx;
	private static ExecBlockCase testCase;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		drv = new MockDriver();
		DriverManager.registerDriver(drv);
		
		URL confUrl = PerformanceTestTest.class.getResource("config_v1_test.xml");
		mCtx = new MainContext();
		mCtx.loadConfig(confUrl.getPath());
		
		mCtx.setThreadCount(10);
		mCtx.setDuration(100);
		
		mCtx.setCheckInternal(5);
		
		mCtx.setRunPrework(true);
		mCtx.setRunPostwork(true);
		mCtx.setRunMain(true);
		
		mCtx.setReporter(new DisplayPerformance());
		mCtx.setStarted(true);
		
		URL caseUrl = PerformanceTestTest.class.getResource("performance_v1.case");
		XMLParser parser = new XMLParser();
		long t = System.nanoTime();
		testCase = parser.parseCase(caseUrl.getPath(), mCtx);
		mCtx.writeDebug("Case parsing complete(elapsed " + (System.nanoTime() - t)/1000000 + "ms)");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		DriverManager.deregisterDriver(drv);
	}
	
	@Test
	public void testPrework() throws Exception {
		testCase.runPrework();
	}
	
	@Test
	public void testMain() throws Exception {
		testCase.runMain();
	}
	
	@Test
	public void testPostwork() throws Exception {
		testCase.runPostwork();
	}
}
