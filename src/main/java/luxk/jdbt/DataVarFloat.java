package luxk.jdbt;

public class DataVarFloat extends AbstractDataVar {
	
	private float value;

	public DataIF duplicate() throws DataException {
		
		DataVarFloat data = new DataVarFloat();
		
		data.setName(this.name);
		data.setType(this.type);
		
		data.setFloatValue(this.value);
		
		return data;
	}
	
	@Override
	public void setValue(String val) throws DataException {
		try {
			this.value = Float.parseFloat(val);
		} catch(Exception e) {
			new DataException(
					this.name + " fail to set " + val + " to float", e);
		}
	}
	
	@Override
	public void setIntValue(int val) throws DataException {
		this.value = (float)val;
	}
	
	@Override
	public void setLongValue(long val) throws DataException {
		this.value = (float)val;
	}
	
	@Override
	public void setFloatValue(float val) throws DataException {
		this.value = val;
	}

	@Override
	public String getValue() throws DataException {
		return Float.toString(this.value);
	}

	@Override
	public int getIntValue() throws DataException {
		return (int)this.value;
	}

	@Override
	public long getLongValue() throws DataException {
		return (long)this.value;
	}

	@Override
	public float getFloatValue() throws DataException {
		return this.value;
	}

}
