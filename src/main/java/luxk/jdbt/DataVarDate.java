package luxk.jdbt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataVarDate extends AbstractDataVar {
	
	private TimeZone timeZone = TimeZone.getDefault();
	
	private String strVal = null;
	private String format = null;
	
	public DataIF duplicate() throws DataException {
		
		DataVarDate data = new DataVarDate();
		
		data.setName(this.name);
		data.setType(this.type);
		
		data.setValue(this.strVal);
		data.setFormat(this.format);
		
		return data;
	}

	@Override
	public void setValue(String val) throws DataException {
		this.strVal = val;
	}
	
	@Override
	public void setIntValue(int val) throws DataException {
		throw new DataException(
				this.name + " fail to convert int " + this.strVal +" to date");
	}
	
	@Override
	public void setLongValue(long val) throws DataException {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(this.format);
		simpleFormat.setTimeZone(this.timeZone);
		
		this.strVal = simpleFormat.format(new Date(val));
	}
	
	@Override
	public void setFloatValue(float val) throws DataException {
		setLongValue((long)val);
	}
	
	@Override
	public void setFormat(String fmt) throws DataException {
		this.format = fmt;
	}

	@Override
	public String getValue() throws DataException {
		SimpleDateFormat simpleFormat = null;
		Date date;
		
		try {
			simpleFormat = new SimpleDateFormat(this.format);
			simpleFormat.setTimeZone(this.timeZone);
			
			date = simpleFormat.parse(this.strVal);
		} catch(Exception e) {
			throw new DataException(
					this.name + " fail to parse " + this.strVal +
					" to date with format " + this.format, e);
		}
		
		return simpleFormat.format(date);
	}

	@Override
	public int getIntValue() throws DataException {
		throw new DataException(
				this.name + " fail to convert date " + this.strVal +" to int");
	}

	@Override
	public long getLongValue() throws DataException {		
		return getDate().getTime();
	}

	@Override
	public float getFloatValue() throws DataException {
		return (float)(getDate().getTime());
	}

	private Date getDate() throws DataException {
		SimpleDateFormat simpleFormat = null;
		Date date;
		
		try {
			simpleFormat = new SimpleDateFormat(this.format);
			simpleFormat.setTimeZone(this.timeZone);
			
			date = simpleFormat.parse(this.strVal);
		} catch(Exception e) {
			throw new DataException(
					this.name + " fail to parse date " + this.strVal +
					" with format " + this.format, e);
		}
		
		return date;
	}
}
