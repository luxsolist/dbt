package luxk.jdbt;

public class DBTException extends Exception {
	
	static final long serialVersionUID = 1;
	
	public DBTException() {
		super();
	}

	public DBTException(String arg0) {
		super(arg0);
	}

	public DBTException(String arg0, int line) {
		super(line + ": " + arg0);
	}

	public DBTException(Throwable arg0) {
		super(arg0);
	}

	public DBTException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DBTException(String arg0, Throwable arg1, int line) {
		super(line + ": " + arg0, arg1);
	}
}
