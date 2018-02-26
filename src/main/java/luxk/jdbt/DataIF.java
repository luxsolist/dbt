package luxk.jdbt;

public interface DataIF {
	
	public void setName(String name);
	public String getName();
	public DataIF duplicate() throws DataException;
	
	public void setValue(String val) throws DataException;
	public void setIntValue(int val) throws DataException;
	public void setLongValue(long val) throws DataException;
	public void setFloatValue(float val)  throws DataException;
	
	public String getValue() throws DataException;
	public int getIntValue() throws DataException;
	public long getLongValue() throws DataException;
	public float getFloatValue() throws DataException;
}
