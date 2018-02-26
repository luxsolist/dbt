package luxk.jdbt;

public class DataVarLong extends AbstractDataVar {
	
	private long value;
	
	public DataIF duplicate() throws DataException {
		
		DataVarLong data = new DataVarLong();
		
		data.setName(this.name);
		data.setType(this.type);
		
		data.setLongValue(this.value);
		
		return data;
	}

	@Override
	public void setValue(String val) throws DataException {
		try {
			this.value = Long.parseLong(val);
		} catch(Exception e) {
			new DataException(this.name + " fail to set " + val + "to long", e);
		}
	}
	
	@Override
	public void setIntValue(int val) throws DataException {
		this.value = val;
	}
	
	@Override
	public void setLongValue(long val) throws DataException {
		this.value = val;
	}
	
	@Override
	public void setFloatValue(float val) throws DataException {
		this.value = (long)val;
	}

	@Override
	public String getValue() throws DataException {
		return Long.toString(value);
	}

	@Override
	public int getIntValue() throws DataException {
		if (value > (long)Integer.MAX_VALUE)
			throw new DataException(
					this.name + " long value " + this.value +
					"exceed to int max " + Integer.MAX_VALUE);
		
		if (value < (long)Integer.MIN_VALUE)
			throw new DataException(
					this.name + " long value " + this.value +
					"exceed to int min " + Integer.MIN_VALUE);
		
		return (int)value;
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
