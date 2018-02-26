package luxk.jdbt;

public class DataException extends Exception {

	static final long serialVersionUID = 1;
	
	public DataException() {
		super();
	}

	public DataException(String arg0) {
		super(arg0);
	}

	public DataException(Throwable arg0) {
		super(arg0);
	}

	public DataException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
