package luxk.jdbt.config;

import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Database {
	
	private String name;
	private String driver = null;
	private String url = null;
	private String user = null;
	private String password = null;
	private String version = null;
	private int[] reconnectOnErrors = new int[0];
	
	private HashMap<String, String> props = new HashMap<String, String>();
	
	public Database(String name) {
		this.name = name;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		if(driver != null && !driver.isEmpty())
			this.driver = driver;
	}
	
	public String getName() {
		return this.name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if(url != null && !url.isEmpty())
			this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		if(user != null && !user.isEmpty())
			this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password != null && !password.isEmpty())
			this.password = password;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		if(version != null && !version.isEmpty())
			this.version = version;
	}

	public int[] getReconnectOnErrors() {
		return reconnectOnErrors;
	}

	public void setReconnectOnErrors(String val) {
		if(val == null || val.isEmpty())
			return;
		
		String[] codes = val.split(",");
		if(codes != null && codes.length > 0) {
			this.reconnectOnErrors = new int[codes.length];
			for(int i = 0; i < codes.length; i++) {
				this.reconnectOnErrors[i] = Integer.parseInt(codes[i]);
			}
		}
	}
	
	public boolean isReconnectableError(int errCode) {
		for(int e: this.reconnectOnErrors) {
			if(e == errCode)
				return true;
		}
		
		return false;
	}

	public String getProp(String propName) {
		return this.props.get(propName);
	}

	public void setProp(String propName, String propValue) {
		if(propName != null && !propName.isEmpty())
			this.props.put(propName, propValue);
	}
	
	public void buildFromXML(Element e) throws ConfigException {
		setDriver(e.getAttribute("driver"));
		setUrl(e.getAttribute("url"));
		setUser(e.getAttribute("user"));
		setPassword(e.getAttribute("password"));
		setVersion(e.getAttribute("version"));
		setReconnectOnErrors(e.getAttribute("reconnectOnErrors"));
		
		NodeList pl = e.getElementsByTagName("Prop");
		for(int i = 0; i < pl.getLength(); i++) {
			Element p = (Element)pl.item(i);
			String n = p.getAttribute("name");
			String v = p.getAttribute("value");
			setProp(n, v);
		}
	}
}
