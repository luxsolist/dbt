package luxk.jdbt;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExecBlockPostworkTest {

	private static MainContext mCtx;
	private static ExecBlockPostwork exec;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		URL url = ExecBlockPostworkTest.class.getResource("config_v1_test.xml");
		mCtx = new MainContext();
		mCtx.loadConfig(url.getPath());

		exec = new ExecBlockPostwork("Test Postwork", null, mCtx);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test(expected=DBTException.class)
	public void testDup() throws Exception {
		exec.duplicate(null);
	}
	
	@Test
	public void testPrepare() throws Exception {
		exec.prepare();
	}
	
	@Test
	public void testRun() throws Exception {
		exec.run();
	}
	
	@Test
	public void testCleanup() throws Exception {
		exec.cleanup();
	}
}
