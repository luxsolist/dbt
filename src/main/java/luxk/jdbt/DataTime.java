package luxk.jdbt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DataTime extends AbstractData {
	
	private TimeZone timeZone = TimeZone.getDefault();
	private Locale locale = Locale.getDefault();
	
	private String format = "yyyy-MM-dd HH:mm:ss:SSS";
	
	public DataIF duplicate() throws DataException {
		
		DataTime data = new DataTime();
		
		data.setName(this.name);
		data.setType(this.type);
		
		data.setFormat(this.format);
		
		return data;
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
	public void setMin(long min) throws DataException {
		throw new DataException(this.name + " unsupport setMin");
	}

	@Override
	public void setMax(long max) throws DataException {
		throw new DataException(this.name + " unsupport setMax");
	}

	@Override
	public void setInc(int inc) throws DataException {
		throw new DataException(this.name + " unsupport setInc");
	}

	@Override
	public void setFormat(String fmt) throws DataException {
		this.format = fmt;
	}

	@Override
	public String getValue() throws DataException {
		
		SimpleDateFormat simpleFormat = new SimpleDateFormat(this.format);
		simpleFormat.setTimeZone(this.timeZone);
		
		Calendar cal = Calendar.getInstance(this.timeZone, this.locale);
			
		return simpleFormat.format(cal.getTime());
	}

	@Override
	public int getIntValue() throws DataException {
		throw new DataException(this.name + " unsupport setMin");
	}

	@Override
	public long getLongValue() throws DataException {
		Calendar cal = Calendar.getInstance(this.timeZone, this.locale);
		return cal.getTimeInMillis();
	}

	@Override
	public float getFloatValue() throws DataException {
		return (float)getLongValue();
	}
}
