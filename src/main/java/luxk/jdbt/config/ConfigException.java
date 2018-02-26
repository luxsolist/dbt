package luxk.jdbt.config;

public class ConfigException extends Exception {
	
	static final long serialVersionUID = 0x825481;
	
	public ConfigException(String msg) {
		super(msg);
	}
	
	public ConfigException(Throwable t) {
		super(t);
	}
	
	public ConfigException(String msg, Throwable t) {
		super(msg, t);
	}

}
