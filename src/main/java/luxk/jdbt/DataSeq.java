package luxk.jdbt;

import java.util.concurrent.atomic.AtomicLong;

public class DataSeq extends AbstractData {
	
	private long max = Long.MAX_VALUE;
	private int inc = 1;
	private String format = null;
	
	AtomicLong aLong = new AtomicLong();
	
	public DataIF duplicate() throws DataException {
		
		DataSeq data = new DataSeq();
		
		data.setName(this.name);
		data.setType(this.type);
		
		data.setMin(this.aLong.get());
		data.setMax(this.max);
		data.setInc(this.inc);
		
		return data;
	}

	@Override
	public void setValue(String val) throws DataException {
		throw new DataException(
				this.name + " unsupport setValue");
	}
	
	@Override
	public void setIntValue(int val) throws DataException {
		throw new DataException(
				this.name + " unsupport setIntValue");
	}
	
	@Override
	public void setLongValue(long val) throws DataException {
		throw new DataException(
				this.name + " unsupport setLongValue");
	}
	
	@Override
	public void setFloatValue(float val) throws DataException {
		throw new DataException(
				this.name + " unsupport setFloatValue");
	}

	@Override
	public void setMin(long val) throws DataException {
		aLong.set(val);
	}

	@Override
	public void setMax(long val) throws DataException {
		this.max = val;
	}

	@Override
	public void setInc(int val) throws DataException {
		this.inc = val;
	}

	@Override
	public void setFormat(String fmt) throws DataException {
		this.format = fmt;
	}

	@Override
	public String getValue() throws DataException {
		String res = null;
		
		if(this.format != null && !"".equals(this.format))
			res = String.format(this.format, getLongValue());
		else
			res = Long.toString(getLongValue());
		
		return res;
	}

	@Override
	public int getIntValue() throws DataException {
		
		long nextVal = aLong.getAndAdd(inc);

		if(nextVal > this.max)
			throw new DataException(
					this.name + " sequence val exceed max " + this.max);

		if(nextVal > Integer.MAX_VALUE)
			throw new DataException(
					this.name + " sequence val exceed Integer.MAX_VALUE");
		
		return (int)nextVal;
	}

	@Override
	public long getLongValue() throws DataException {
		
		long nextVal = aLong.getAndAdd(inc);
		
		if(nextVal > this.max)
			throw new DataException(
					this.name + " sequence val exceed max " + this.max);
		
		return nextVal;
	}

	@Override
	public float getFloatValue() throws DataException {
		return (float)getLongValue();
	}

}
