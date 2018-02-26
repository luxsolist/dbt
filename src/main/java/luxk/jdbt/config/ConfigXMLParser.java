package luxk.jdbt.config;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ConfigXMLParser {

	public Config parseConfig(String xmlPath) throws Exception {
		
		File file = new File(xmlPath);
		
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = f.newDocumentBuilder();
		Document doc = parser.parse(file);
		
		Element root = doc.getDocumentElement();
		String version = root.getAttribute("version");
		Config c = getConfig(version);
		c.buildFromXML(root);
		
		return c;
		
	}
	
	private Config getConfig(String version) throws Exception {
		@SuppressWarnings({ "rawtypes" })
		Class c = Class.forName("luxk.dbt.config.Config_" + version.trim());
		Config result = (Config)c.newInstance();
		return result;
	}
}
