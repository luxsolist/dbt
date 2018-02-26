package luxk.jdbt;

import java.util.HashMap;

public class FunctionalTest extends PerformanceTest {

	private MainContext buildMainContext(HashMap<String, String> map)
			throws ArgumentException, DBTException {
		MainContext mCtx = new MainContext();
		
		String confPath = getOptionString(map, OPT_CONF, false);
		mCtx.loadConfig(((confPath == null || confPath.isEmpty()) ? CONF_PATH : confPath));
		mCtx.setThreadCount(getOptionInt(map, OPT_SESS, true));
		mCtx.setDuration(getOptionInt(map, OPT_TIME, true));
		
		mCtx.setCheckInternal(0);
		
		mCtx.setRunPrework(true);
		mCtx.setRunPostwork(true);
		mCtx.setRunMain(true);
		
		mCtx.setReporter(new DisplayPerformance());
		mCtx.setStarted(true);
		
		return mCtx;
	}

	private void printUsage() {
		System.out.println(
			"Usage: java -classpath=CLASSPATH FunctionalTest [OPTION] PATH");
		System.out.println("Run case PATH for DB functional test");
		System.out.println("Options");
		System.out.println("  -conf=PATH   path for configuration file(.xml)");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FunctionalTest test = new FunctionalTest();
		HashMap<String, String> map = null;
		MainContext mCtx = null;
		
		try {
			map = test.processArgs(args);
			mCtx = test.buildMainContext(map);
		} catch(ArgumentException e) {
			e.printStackTrace();
			test.printUsage();
			System.exit(-1);
		} catch(DBTException e) {
			e.printStackTrace();
		}
		
		try {
			XMLParser parser = new XMLParser();
			ExecIF exec = parser.parseCase(
					test.getOptionString(map, OPT_CASE, true), mCtx);
			
			if(!(exec instanceof ExecBlockCase))
				throw new DBTException(
					"Invalid class(" + exec.getClass().getName() + ") for " +
				PerformanceTest.class.getName());
			
			exec.prepare();
			
			if(mCtx.isDebug())
				exec.dump();
		
			mCtx.write("run " + exec.getName() + "...");
			exec.run();
			
			if(mCtx.getConfig().getParamBoolean("report.detail"))
				mCtx.getReporter().printDetailResult(exec.getResult(),
						mCtx.getWriteStream());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
