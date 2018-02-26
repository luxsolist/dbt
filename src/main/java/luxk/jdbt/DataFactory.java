package luxk.jdbt;

public class DataFactory {
	
	private static int str2DataType(String dataType) {
		if("int".equalsIgnoreCase(dataType))
			return AbstractData.DATA_TYPE_INT;
		if("long".equalsIgnoreCase(dataType) ||
				"number".equalsIgnoreCase(dataType))
			return AbstractData.DATA_TYPE_LONG;
		if("float".equalsIgnoreCase(dataType))
			return AbstractData.DATA_TYPE_FLOAT;
		if("string".equalsIgnoreCase(dataType))
			return AbstractData.DATA_TYPE_STRING;
		if("date".equalsIgnoreCase(dataType))
			return AbstractData.DATA_TYPE_DATE;
		
		return -1;		
	}
	
	public static AbstractDataVar factoryVar(String dataType)
			throws DBTException {
		
		int typeInt = str2DataType(dataType);
		AbstractDataVar res = null;
		
		switch(typeInt) {
		case AbstractData.DATA_TYPE_INT:
			res = new DataVarInt();
			break;
		case AbstractData.DATA_TYPE_LONG:
			res = new DataVarLong();
			break;
		case AbstractData.DATA_TYPE_FLOAT:
			res = new DataVarFloat();
			break;
		case AbstractData.DATA_TYPE_STRING:
			res = new DataVarString();
			break;
		case AbstractData.DATA_TYPE_DATE:
			res = new DataVarDate();
			break;
		default:
			throw new DBTException("invalid var type "+ dataType);
		}
		
		res.setType("VAR");
		return res;
	}
	
	public static AbstractDataRand factoryRand(String dataType)
			throws DBTException {
		
		int typeInt = str2DataType(dataType);
		AbstractDataRand res = null;
		
		switch(typeInt) {
		case AbstractData.DATA_TYPE_INT:
		case AbstractData.DATA_TYPE_LONG:
			res = new DataRandInt();
			break;
		case AbstractData.DATA_TYPE_STRING:
			res = new DataRandString();
			break;
		default:
			throw new DBTException("invalid random type "+ dataType);
		}
		
		res.setType("RAND");
		
		return res;
	}

	public static boolean isVar(String str) {
		if ("Var".equals(str) || "Variable".equals(str))
			return true;
		
		return false;	
	}

	public static boolean isRand(String str) {
		if ("Rand".equals(str) || "Random".equals(str))
			return true;
		
		return false;	
	}
	
	public static boolean isSeq(String str) {
		if ("Seq".equals(str) || "Sequence".equals(str))
			return true;
		
		return false;			
	}
	
	public static boolean isTime(String str) {
		if ("Time".equals(str))
			return true;
		
		return false;			
	}
	
	public static boolean isData(String name) {
		if (isVar(name) || isRand(name) || isSeq(name) || isTime(name))
			return true;
		
		return false;
	}
}
