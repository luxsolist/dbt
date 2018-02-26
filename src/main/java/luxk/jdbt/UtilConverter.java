package luxk.jdbt;

public final class UtilConverter {
	
	private static final Units[] TIME_UNITS = {
		new Units('m', 1),                           // msec
		new Units('s', 1000),                        // sec
		new Units('M', 60 * 1000),                   // minute
		new Units('h', 60 * 60 * 1000),              // hour
		new Units('d', 24 * 60 * 60 * 1000),         // day
		new Units('y', 365 * 24 * 60 * 60 * 1000),   // year
	};
	
	private static final Units[] BYTE_UNITS = {
		new Units('b', 1),                                        // byte
		new Units('k', 1024),                                     // Kilo
		new Units('m', 1024 * 1024),                              // Mega
		new Units('g', 1024 * 1024 * 1024),                       // Giga
		new Units('t', 1024 * 1024 * 1024 * 1024),                // Tera
		new Units('e', 1024 * 1024 * 1024 * 1024 * 1024),         // Exa
		new Units('p', 1024 * 1024 * 1024 * 1024 * 1024 * 1024),  // Peta
	};
	
	public static long parseTimeMilli(String str) {
		
		if(str == null || "".equals(str))
			return 0;
		
		int len = str.length();
		char last = str.charAt(len-1);

		// 마지막 문자가 숫자로 끝나는 경우
		if(last >= '0' && last <= '9')
			return Long.parseLong(str);
		
		// 마지막 문자가 숫자가 아닌데 1글자밖에 없는 경우. 즉, 단위만 있는 경우
		if(len <= 2)
			return 0;

		long val = Long.parseLong(str.substring(0, len-1));
		
		for(int i = 0; i < TIME_UNITS.length; i++) {
			if(TIME_UNITS[i].equals(last))
				return val * TIME_UNITS[i].getUnitVal();
		}
		
		return -1;
	}
	
	public static long parseByte(String str) {

		if(str == null || "".equals(str))
			return 0;
		
		int len = str.length();
		char last = str.charAt(len-1);

		// 마지막 문자가 숫자로 끝나는 경우
		if(last >= '0' && last <= '9')
			return Long.parseLong(str);
		
		// 마지막 문자가 숫자가 아닌데 1글자밖에 없는 경우. 즉, 단위만 있는 경우
		if(len <= 2)
			return 0;

		long val = Long.parseLong(str.substring(0, len-1));
		
		for(int i = 0; i < BYTE_UNITS.length; i++) {
			if(BYTE_UNITS[i].equals(last))
				return val * BYTE_UNITS[i].getUnitVal();
		}
		
		return -1;
	}

}

class Units {
	private char unitChr = 0;
	private long unitVal = 0;
	
	public Units(char chr, long val) {
		this.unitChr = chr;
		this.unitVal = val;
	}
	
	public char getUnitChr() {
		return this.unitChr;
	}
	
	public String getUnitStr() {
		return Character.toString(this.unitChr);
	}
	
	public boolean equals(char chr) {
		return (this.unitChr == chr);
	}
	
	public long getUnitVal() {
		return this.unitVal;
	}
}