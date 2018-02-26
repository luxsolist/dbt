package luxk.jdbt;

public class DataVarInt extends AbstractDataVar {
	
	private int value;

	public DataIF duplicate() throws DataException {
		
		DataVarInt data = new DataVarInt();
		
		data.setName(this.name);
		data.setType(this.type);
		
		data.setIntValue(this.value);
		
		return data;
	}
	
	@Override
	public void setValue(String val) throws DataException {
		try {
			this.value = Integer.parseInt(val);
		} catch(Exception e) {
			new DBTException(this.name + " fail to set " + val + "to int", e);
		}
	}
	
	@Override
	public void setIntValue(int val) throws DataException {
		this.value = val;
	}
	
	@Override
	public void setLongValue(long val) throws DataException {
		if(val > Integer.MAX_VALUE)
			throw new DataException(this.name + " long value " + val +
					" exceed Integer.MAX_VALUE");
		if(val < Integer.MIN_VALUE)
			throw new DataException(this.name + " long value " + val +
					" exceed Integer.MIN_VALUE");
		
		this.value = (int)val;
	}
	
	@Override
	public void setFloatValue(float val) throws DataException {
		if(val > Integer.MAX_VALUE)
			throw new DataException(this.name + " float value " + val +
					" exceed Integer.MAX_VALUE");
		if(val < Integer.MIN_VALUE)
			throw new DataException(this.name + " float value " + val +
					" exceed Integer.MIN_VALUE");
		
		this.value = (int)val;
	}
	
	@Override
	public String getValue() throws DataException {
		return Integer.toString(this.value);
	}

	@Override
	public int getIntValue() throws DataException {
		return this.value;
	}

	@Override
	public long getLongValue() throws DataException {
		return this.value;
	}

	@Override
	public float getFloatValue() throws DataException {
		return this.value;
	}
}
