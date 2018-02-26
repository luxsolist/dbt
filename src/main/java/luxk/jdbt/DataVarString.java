package luxk.jdbt;

public class DataVarString extends AbstractDataVar {
	
	private String value;
	
	public DataIF duplicate() throws DataException {
		
		DataVarString data = new DataVarString();
		
		data.setName(this.name);
		data.setType(this.type);
		
		data.setValue(this.value);
		
		return data;
	}

	@Override
	public void setValue(String val) throws DataException {
		this.value = val;
	}

	@Override
	public void setIntValue(int val) throws DataException {
		this.value = Integer.toString(val);
	}
	
	@Override
	public void setLongValue(long val) throws DataException {
		this.value = Long.toString(val);
	}
	
	@Override
	public void setFloatValue(float val) throws DataException {
		this.value = Float.toString(val);
	}
	
	@Override
	public String getValue() throws DataException {
		return this.value;
	}

	@Override
	public int getIntValue() throws DataException {
		int ret;
		
		try {
			ret = Integer.parseInt(this.value);
		} catch(Exception e) {
			throw new DataException(
					this.name + " fail to convert " + this.value + " to int");
		}
		
		return ret;
	}

	@Override
	public long getLongValue() throws DataException {
		long ret;
		
		try {
			ret = Long.parseLong(this.value);
		} catch(Exception e) {
			throw new DataException(
					this.name + " fail to convert " + this.value + " to long");
		}
		
		return ret;
	}

	@Override
	public float getFloatValue() throws DataException {
		float ret;
		
		try {
			ret = Float.parseFloat(this.value);
		} catch(Exception e) {
			throw new DataException(
					this.name + " fail to convert " + this.value +" to float");
		}
		
		return ret;
	}

}
