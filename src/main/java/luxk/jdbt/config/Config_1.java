package luxk.jdbt.config;

import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import luxk.jdbt.UtilString;

public class Config_1 implements Config {
	
	HashMap<String, Database> dbs = new HashMap<String, Database>();
	HashMap<String, String> params = new HashMap<String, String>();
	
	public Config_1() {
		// create default TestProps
		try {
			setParam("log.debug", "n");
			setParam("log.testError", "n");
			setParam("report.detail", "n");
			setParam("test.duration", "60s");
			setParam("test.session", "10");
		} catch (ConfigException e) {
			// do nothing
		}
	}
	
	public void addDatabase(Database db) {
		this.dbs.put(db.getName(), db);
	}
	
	public Database getDatabase() {
		return this.dbs.get("default");
	}
	
	public Database getDatabase(String dbName) {
		return this.dbs.get(dbName);
	}
	
	public void setParam(String name, String value) throws ConfigException {
		if(name == null || name.isEmpty())
			throw new ConfigException("param name is null");
		
		this.params.remove(name);
		this.params.put(name, value);
	}
	
	public String getParam(String name) {
		return this.params.get(name);
	}
	
	public boolean getParamBoolean(String name) {
		return UtilString.isPositiveOption(getParam(name));
	}
	
	public void buildFromXML(Element root) throws ConfigException {
		
		// parse database section
		NodeList dbs = root.getElementsByTagName("Database");
		if(dbs.getLength() == 0) {
			throw new ConfigException("Database configuration not found");
		}
		
		for(int i = 0; i < dbs.getLength(); i++) {
			Element e = (Element)(dbs.item(i));
			String name = e.getAttribute("name");
			Database db = new Database(name);
			db.buildFromXML(e);
			this.dbs.put(name, db);
		}
				
		// parse userparam section
		NodeList uparams = root.getElementsByTagName("Params");
		for(int i = 0; i < uparams.getLength(); i++) {
			Element e = (Element)(uparams.item(i));
			NodeList params = e.getElementsByTagName("Param");
			for(int j = 0; j < params.getLength(); j++) {
				Element ee = (Element)(params.item(j));
				String n = ee.getAttribute("name");
				String v = ee.getAttribute("value");
				setParam(n, v);
			}
		}
		
	}


}