package luxk.jdbt;

public abstract class AbstractDataRand extends AbstractData {
	
	protected String dataType = null;
	protected long min = 0;
	protected long max = Long.MAX_VALUE;
	protected String format = null;
	
	public void setDataType(String str) {
		this.dataType = str;
	}

	@Override
	public void setMin(long min) throws DataException {
		if(min < 0)
			throw new DataException(this.name + " min value must be >= 0");
		
		if(min > this.max)
			throw new DataException(
					this.name + " min must be <= max " + this.max);
		
		this.min = min;
	}

	@Override
	public void setMax(long max) throws DataException {
		if(max < 0)
			throw new DataException(this.name + " max value must be >= 0");
		
		if(max < this.min)
			throw new DataException(
					this.name + " max must be >= min " + this.min);

		this.max = max;
	}

	@Override
	public void setValue(String val) throws DataException {
		throw new DataException(this.name + " unsupport setValue");
	}
	
	@Override
	public void setIntValue(int val) throws DataException {
		throw new DataException(this.name + " unsupport setIntValue");
	}
	
	@Override
	public void setLongValue(long val) throws DataException {
		throw new DataException(this.name + " unsupport setLongValue");
	}
	
	@Override
	public void setFloatValue(float val) throws DataException {
		throw new DataException(this.name + " unsupport setFloatValue");
	}

	@Override
	public void setInc(int inc) throws DataException {
		throw new DataException(this.name + " unsupport setInc");
	}

	@Override
	public void setFormat(String fmt) throws DataException {
		this.format = fmt;
	}	
}
