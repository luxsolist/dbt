package luxk.jdbt;

public abstract class AbstractData implements DataIF {

	public static final int DATA_TYPE_NONE = 10;
	public static final int DATA_TYPE_INT = 11;
	public static final int DATA_TYPE_LONG = 12;
	public static final int DATA_TYPE_FLOAT = 13;
	public static final int DATA_TYPE_STRING = 14;
	public static final int DATA_TYPE_DATE = 15;
	
	protected String name = null;
	protected String type = null;
	
	public abstract void setValue(String val) throws DataException;
	public abstract void setIntValue(int val) throws DataException;
	public abstract void setLongValue(long val) throws DataException;
	public abstract void setFloatValue(float val)  throws DataException;
	
	public abstract void setMin(long min) throws DataException;
	public abstract void setMax(long max) throws DataException;
	public abstract void setInc(int inc) throws DataException;
	public abstract void setFormat(String fmt) throws DataException;
	
	public abstract String getValue() throws DataException;
	public abstract int getIntValue() throws DataException;
	public abstract long getLongValue() throws DataException;
	public abstract float getFloatValue() throws DataException;
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type.toUpperCase();
	}
}
