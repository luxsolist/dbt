package luxk.jdbt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConfigTest.class, MockJDBCTest.class,
	ExecBlockCaseTest.class, ExecBlockPreworkTest.class, ExecBlockPostworkTest.class,
	PerformanceTestTest.class,
})
public class AllTests {

}
