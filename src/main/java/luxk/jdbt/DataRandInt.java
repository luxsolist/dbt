package luxk.jdbt;

import java.util.Random;

public class DataRandInt extends AbstractDataRand {

	private Random rand = new Random();
	
	public DataIF duplicate() throws DataException {
		
		DataRandInt data = new DataRandInt();
		
		data.setName(this.name);
		data.setType(this.type);
		
		data.setMin(this.min);
		data.setMax(this.max);
		
		return data;
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
		
		if(this.min >= (long)Integer.MAX_VALUE)
			throw new DataException(this.name + " min value exceed Integer.MAX_VALUE");
		
		int intMax = (this.max > (long)Integer.MAX_VALUE) ?
				Integer.MAX_VALUE : (int)this.max;
		
		int res = this.rand.nextInt(intMax - (int)this.min);
		return res + (int)this.min;
	}

	@Override
	public long getLongValue() throws DataException {
		long res = this.rand.nextLong();
		res = (res < 0 ? -res : res) % (this.max - this.min);
		return res + this.min;
	}

	@Override
	public float getFloatValue() throws DataException {
		return (float)getIntValue();
	}
}
