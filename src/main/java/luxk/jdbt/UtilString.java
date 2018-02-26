package luxk.jdbt;

public class UtilString {
	
	private static final String[] positiveOpt = 
		{ "true", "yes", "y", "on", "ok", "1" };

	public static boolean isPositiveOption(String val) {
		if(val == null || "".equals(val))
			return false;
		
		for(String e: positiveOpt) {
			if(e.equalsIgnoreCase(val))
				return true;
		}
		return false;
	}
}
