package luxk.jdbt;

import java.util.HashMap;

public class PerformanceTest {
	
	protected static final String OPT_SESS = "sess";
	protected static final String OPT_TIME = "time";
	protected static final String OPT_DISP = "disp";
	protected static final String OPT_CONF = "conf";
	protected static final String OPT_PRE  = "pre";
	protected static final String OPT_POST = "post";
	protected static final String OPT_MAIN = "main";
	protected static final String OPT_CASE = "case path";
	
	protected static final String CONF_PATH = "conf/config.xml";
	
	protected HashMap<String, String> processArgs(String[] args)
			throws ArgumentException {
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].startsWith("-")) {
				String tmp[] = args[i].split("=");
				
				if(tmp.length != 2)
					throw new ArgumentException("invalid option " + args[i]);
				
				setOptionString(map, tmp[0].substring(1), tmp[1]);
			} else {
				setOptionString(map, OPT_CASE, args[i]);
			}
		}
		return map;
	}
	
	protected void setOptionString(HashMap<String, String> map, String key,
			String value) throws ArgumentException {
		String exist = map.get(key.toLowerCase());
		if(exist != null)
			throw new ArgumentException("duplicated option " + key);
		
		map.put(key.toLowerCase(), value);
	}
	
	protected String getOptionString(HashMap<String, String> map, String key,
			boolean mandatory) throws ArgumentException {
		String val = map.get(key);
		
		if(val == null || "".equals(val)) {
			if(mandatory)
				throw new ArgumentException("option " + key + " not specifiied");
			else
				return null;
		}
		
		return val;
	}

	protected boolean getOptionBoolean(HashMap<String, String> map, String key,
			boolean mandatory, boolean defaultVal) throws ArgumentException {
		String val = map.get(key);
		
		if(val == null || "".equals(val)) {
			if(mandatory)
				throw new ArgumentException("option " + key + " not specifiied");
			else
				return defaultVal;
		}
		
		return UtilString.isPositiveOption(val);
	}

	protected int getOptionInt(HashMap<String, String> map, String key,
			boolean mandatory) throws ArgumentException {
		String val = map.get(key);
		
		if(val == null || "".equals(val)) {
			if(mandatory)
				throw new ArgumentException("option " + key + " not specifiied");
			else
				return Integer.MIN_VALUE;
		}
		
		int intVal;
		try {
			intVal = Integer.parseInt(val);
		} catch(Exception e) {
			throw new ArgumentException(
					"invalid number value '" + val + "' for option " + key);
		}
		
		return intVal;
	}

	private MainContext buildMainContext(HashMap<String, String> map)
			throws ArgumentException, DBTException {
		MainContext mCtx = new MainContext();
		
		String confPath = getOptionString(map, OPT_CONF, false);
		mCtx.loadConfig(((confPath == null || confPath.isEmpty()) ? CONF_PATH : confPath));
		mCtx.setThreadCount(getOptionInt(map, OPT_SESS, true));
		mCtx.setDuration(getOptionInt(map, OPT_TIME, true));
		
		int iVal = getOptionInt(map, OPT_DISP, false);
		if(iVal != Integer.MIN_VALUE)
			mCtx.setCheckInternal(iVal);
		
		mCtx.setRunPrework(getOptionBoolean(map, OPT_PRE, false, true));
		mCtx.setRunPostwork(getOptionBoolean(map, OPT_POST, false, true));
		mCtx.setRunMain(getOptionBoolean(map, OPT_MAIN, false, true));
		
		mCtx.setReporter(new DisplayPerformance());
		mCtx.setStarted(true);
		
		return mCtx;
	}
	
	private void printUsage() {
		System.out.println(
			"Usage: java -classpath=CLASSPATH PerformanceTest [OPTION] PATH");
		System.out.println("Run case PATH for DB performance test");
		System.out.println("Options");
		System.out.println("  -sess=NUM   session count");
		System.out.println("  -time=NUM   test time(sec)");
		System.out.println("  -disp=NUM   report TPS periodically(sec)");
		System.out.println("  -conf=PATH  path for configuration file(.xml)");
		System.out.println("  -pre=[Y|n]  run prework(default Y)");
		System.out.println("  -post[Y|n]  run postwork(default Y)");
		System.out.println("  -main[Y|n]  run main(default Y)");
	}

	public static void main(String[] args) {
		
		PerformanceTest test = new PerformanceTest();
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
			ExecBlockCase testCase = parser.parseCase(
					test.getOptionString(map, OPT_CASE, true), mCtx);
			
			mCtx.setProgName(testCase.getName());
			mCtx.write("run prework for " + testCase.getName());
			testCase.runPrework();
			
			if(mCtx.isDebug())
				testCase.dump();
		
			mCtx.write("run main for " + testCase.getName());
			testCase.runMain();
			
			mCtx.write("run postwork for " + testCase.getName());
			testCase.runPostwork();
			
			if(mCtx.getConfig().getParamBoolean("report.detail"))
				mCtx.getReporter().printDetailResult(testCase.getResult(),
						mCtx.getWriteStream());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
