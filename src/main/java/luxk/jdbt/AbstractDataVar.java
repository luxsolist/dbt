package luxk.jdbt;

public abstract class AbstractDataVar extends AbstractData {

	protected String dataType = null;
	
	public void setDataType(String str) {
		this.dataType = str;
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
		throw new DataException(this.name + " unsupport setFormat");
	}
}
