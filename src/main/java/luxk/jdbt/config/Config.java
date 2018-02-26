package luxk.jdbt.config;

import org.w3c.dom.Element;

public interface Config {
	
	public void addDatabase(Database db);
	public Database getDatabase();
	public Database getDatabase(String dbName);
	
	public void setParam(String name, String value) throws ConfigException;
	public String getParam(String name);
	public boolean getParamBoolean(String name);
	
	public void buildFromXML(Element root) throws ConfigException;

}
