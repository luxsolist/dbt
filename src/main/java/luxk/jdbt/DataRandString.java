package luxk.jdbt;

import java.util.Random;

public class DataRandString extends AbstractDataRand {
	
	private static final long MAX_LENGTH = Integer.MAX_VALUE; // max length 2G
	
	private long max = MAX_LENGTH;
	
	private Random rand = new Random();
	
	public DataIF duplicate() throws DataException {
		
		DataRandString data = new DataRandString();
		
		data.setName(this.name);
		data.setType(this.type);
		
		data.setMin(this.min);
		data.setMax(this.max);
		
		return data;
	}

	@Override
	public void setMax(long max) throws DataException {
		if(max < 0)
			throw new DataException(this.name + " max value must be >= 0");
		
		if(max < this.min)
			throw new DataException(
					this.name + " max must be >= min " + this.min);
		
		if(max > MAX_LENGTH)
			throw new DataException(this.name + " max must be < " + MAX_LENGTH);

		this.max = max;
	}

	@Override
	public String getValue() throws DataException {
		
		int maxGenSize = (int)this.max - (int)this.min;
		int genSize = rand.nextInt(maxGenSize);
		
		StringBuffer sBuf = new StringBuffer();
		
		for(int i = 0; i < genSize; i++) {
			sBuf.append((char)(rand.nextInt((byte)'Z' - (byte)'A') + 65));
		}

		String res = null;
		
		if(this.format != null && !"".equals(this.format))
			res = String.format(this.format, sBuf.toString());
		else
			res = sBuf.toString();
		
		return res;
	}

	@Override
	public int getIntValue() throws DataException {
		throw new DataException(this.name + " unsupport getIntValue");
	}

	@Override
	public long getLongValue() throws DataException {
		throw new DataException(this.name + " unsupport getLongValue");
	}

	@Override
	public float getFloatValue() throws DataException {
		throw new DataException(this.name + " unsupport getFloatValue");
	}

}
