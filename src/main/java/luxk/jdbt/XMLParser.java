package luxk.jdbt;

import java.io.File;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import luxk.jdbt.UtilBean;

public class XMLParser {
	
	public ExecBlockCase parseCase(String xmlPath, MainContext mCtx)
			throws Exception {

		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		File f = new File(xmlPath);
		DefaultHandler dh = new CaseParserHandler();
		((CaseParserHandler)dh).setMainContext(mCtx);
		parser.parse(f, dh);

		return ((CaseParserHandler)dh).getResult();
	}
}

class CaseParserHandler extends DefaultHandler {
	
	private MainContext mCtx = null;
	private Stack<AbstractExec> stack = new Stack<AbstractExec>();
	private ExecIF exec = null;
	
	private int seq = 0;
	
	public void setDocumentLocator(Locator locator) {
		if(this.mCtx.isDebug())
			this.mCtx.writeDebug("line " + locator.getLineNumber());
		if(this.mCtx.isDebug())
			this.mCtx.writeDebug(" colume " + locator.getColumnNumber());
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		try {
			if(this.mCtx.isDebug())
				this.mCtx.writeDebug("startElement qName: " + qName);
			
			if(DataFactory.isVar(qName)) {
				buildVar(qName, atts);
			} else if(DataFactory.isRand(qName)) {
				buildRand(qName, atts);
			} else if(DataFactory.isSeq(qName)) {
				buildSeq(qName, atts);
			} else if(DataFactory.isTime(qName)) {
				buildTime(qName, atts);
			} else if("sync".equalsIgnoreCase(qName)) {
				buildSync(qName, atts);
			} else {
				buildExec(qName, atts);
			}
		} catch(Exception e) {
			throw new SAXException(e.getMessage(), e);
		}
	}

	public void endElement(String uri, String localName, String qName) {
		if(this.mCtx.isDebug())
			this.mCtx.writeDebug("endElement qName: " + qName);
		
		if(ExecFactory.isExec(qName))
			this.exec = this.stack.pop();
	}
	
	public void characters(char[] chars, int start, int length) {
		String str = new String(chars, start, length).trim();
		
		if(str != null && !"".equals(str)) {
		
			if(this.mCtx.isDebug())
				this.mCtx.writeDebug(
						"characters(" + start + ", " + length + "): " +
								str.trim());
			
			AbstractExec exec = this.stack.peek();
			if(exec instanceof ExecBlockSQLExec) {
				((ExecBlockSQLExec)exec).setText(str);
			} else if(exec instanceof ExecBlockSQLSelect) {
				((ExecBlockSQLSelect)exec).setText(str);
			} else if(exec instanceof ExecLineExpect) {
				((ExecLineExpect)exec).setText(str);
			} else if(exec instanceof ExecLineDescription) {
				((ExecLineDescription)exec).setDescription(str);
			} else {
				if(this.mCtx.isDebug())
					this.mCtx.writeDebug("ignore characters("
						+ exec.getClass().getName() + "): " + str.trim());
			}
		}
	}
	
	public void setMainContext(MainContext mCtx) {
		this.mCtx = mCtx;
	}
	
	public ExecBlockCase getResult() throws Exception {
		
		if(!(this.exec instanceof ExecBlockCase))
			throw new Exception("case root type(" + this.exec.getClass().getSimpleName() + " is not ExecBlockCase");
		
		return (ExecBlockCase)this.exec;
	}

	private void buildVar(String qName, Attributes atts) throws Exception {
		
		String dataType = atts.getValue("dataType");
		AbstractData data = DataFactory.factoryVar(dataType);
		
		int len = atts.getLength();
		for(int i = 0; i < len; i++) {
			if(this.mCtx.isDebug())
				this.mCtx.writeDebug("  Attr #" + i + " key:" +
						atts.getQName(i) + ", val:" + atts.getValue(i));
			UtilBean.setProperty(data, atts.getQName(i),
					atts.getValue(i));
		}
		
		AbstractExec exec = this.stack.peek();
		exec.addData(data);
	}
	
	private void buildRand(String qName, Attributes atts) throws Exception {
		
		String dataType = atts.getValue("dataType");
		AbstractData data = DataFactory.factoryRand(dataType);
		
		int len = atts.getLength();
		for(int i = 0; i < len; i++) {
			if(this.mCtx.isDebug())
				this.mCtx.writeDebug("  Attr #" + i + " key:" +
						atts.getQName(i) + ", val:" + atts.getValue(i));
			UtilBean.setProperty(data, atts.getQName(i),
					atts.getValue(i));
		}
		
		AbstractExec exec = this.stack.peek();
		exec.addData(data);
	}
	
	private void buildSeq(String qName, Attributes atts) throws Exception {
		
		AbstractData data = new DataSeq();
		data.setType("SEQ");
		
		int len = atts.getLength();
		for(int i = 0; i < len; i++) {
			if(this.mCtx.isDebug())
				this.mCtx.writeDebug("  Attr #" + i + " key:" + 
						atts.getQName(i) + ", val:" + atts.getValue(i));
			UtilBean.setProperty(data, atts.getQName(i),
					atts.getValue(i));
		}				
		
		AbstractExec exec = this.stack.peek();
		exec.addData(data);
	}
	
	private void buildTime(String qName, Attributes atts) throws Exception {

		AbstractData data = new DataTime();
		data.setType("TIME");
		
		int len = atts.getLength();
		for(int i = 0; i < len; i++) {
			if(this.mCtx.isDebug())
				this.mCtx.writeDebug("  Attr #" + i + " key:" +
						atts.getQName(i) + ", val:" + atts.getValue(i));
			UtilBean.setProperty(data, atts.getQName(i),
					atts.getValue(i));
		}				
		
		AbstractExec exec = this.stack.peek();
		exec.addData(data);
	}

	private void buildSync(String qName, Attributes atts) throws Exception {
		
	}

	private AbstractExec buildExec(String qName, Attributes atts) 
			throws Exception {
		
		ExecIF parent = this.stack.isEmpty() ? null : this.stack.peek();
		
		AbstractExec exec = ExecFactory.factory(qName, qName + "_" + this.seq++,
				parent, this.mCtx);
		
		int len = atts.getLength();
		for(int i = 0; i < len; i++) {
			if(this.mCtx.isDebug())
				this.mCtx.writeDebug("  Attr #" + i + " key:" +
						atts.getQName(i) + ", val:" + atts.getValue(i));
			UtilBean.setProperty(exec, atts.getQName(i),
					atts.getValue(i));
		}
		
		if(qName.equals("Case")) {
			if(parent != null)
				throw new Exception("Parent " + parent.getClass().getName() + " exists. 'Case' must be root");
			
		} else {
			if(parent == null)
				throw new Exception(qName + "'s parent not exists");
			parent.addChild(exec);
		}

		this.stack.push(exec);
		
		return exec;
	}
}
