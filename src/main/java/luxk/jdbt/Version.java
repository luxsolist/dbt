package luxk.jdbt;

public class Version {
	
	private int vers[] = new int[5];
	
	public static Version parseVersion(String str) throws DBTException {
		
		if(str == null || str.isEmpty())
			throw new DBTException("Version string is null");
		
		String tmp[] = str.trim().split("[.]");
		if (tmp == null || tmp.length == 0)
			throw new DBTException("Invalid version format(expected '0000[.0000.0000.0000.00000000]'): " + str);
		
		Version v = null;
		
		switch(tmp.length) {
		case 1:
			v = new Version(Integer.parseInt(tmp[0]));
			break;
		case 2:
			v = new Version(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
			break;
		case 3:
			v = new Version(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
			break;
		case 4:
			v = new Version(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]),
					Integer.parseInt(tmp[3]));
			break;
		case 5:
			v = new Version(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]),
					Integer.parseInt(tmp[3]), Integer.parseInt(tmp[4]));
			break;
		default:
			throw new DBTException("Invalid version format(expected '0000[.0000.0000.0000.00000000]'): " + str);
		}
		
		return v;
	}
	
	public Version(int ver1) {
		this.vers[0] = ver1;
		this.vers[1] = 0;
		this.vers[2] = 0;
		this.vers[3] = 0;
		this.vers[4] = 0;
	}
	
	public Version(int ver1, int ver2) {
		this.vers[0] = ver1;
		this.vers[1] = ver2;
		this.vers[2] = 0;
		this.vers[3] = 0;
		this.vers[4] = 0;
	}

	public Version(int ver1, int ver2, int ver3) {
		this.vers[0] = ver1;
		this.vers[1] = ver2;
		this.vers[2] = ver3;
		this.vers[3] = 0;
		this.vers[4] = 0;
	}

	public Version(int ver1, int ver2, int ver3, int ver4) {
		this.vers[0] = ver1;
		this.vers[1] = ver2;
		this.vers[2] = ver3;
		this.vers[3] = ver4;
		this.vers[4] = 0;
	}
	
	public Version(int ver1, int ver2, int ver3, int ver4, int rev) {
		this.vers[0] = ver1;
		this.vers[1] = ver2;
		this.vers[2] = ver3;
		this.vers[3] = ver4;
		this.vers[4] = rev;
	}
	
	public int getVer1() {
		return vers[0];
	}

	public int getVer2() {
		return vers[1];
	}

	public int getVer3() {
		return vers[2];
	}

	public int getVer4() {
		return vers[3];
	}

	public int getRev() {
		return vers[4];
	}
	
	public int[] getVersionArray() {
		return this.vers;
	}

	public String toString() {
		return String.format("%d.%d.%d.%d.%d", this.vers[0], this.vers[1], this.vers[2], this.vers[3], this.vers[4]);
	}
	
	public int compareTo(Version v) {
		int vArr[] = v.getVersionArray();
		for(int i = 0; i < this.vers.length; i++) {
			if(this.vers[i] > vArr[i]) return 1;
			else if(this.vers[i] < vArr[i]) return -1;
		}
		return 0;
	}
}