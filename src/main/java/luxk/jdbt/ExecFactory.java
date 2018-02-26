package luxk.jdbt;

public class ExecFactory {
	
	private static final String EXEC_CASE              = "Case";
	private static final String EXEC_DESCRIPTION      = "Description";
	private static final String EXEC_PREWORK           = "Prework";
	private static final String EXEC_POSTWORK          = "Postwork";
	private static final String EXEC_PERFORMANCE_TEST = "PerformanceTest";
	private static final String EXEC_FUNCTIONAL_TEST  = "FunctionalTest";
	private static final String EXEC_SESSION           = "Session";
	private static final String EXEC_THREAD            = "Thread";
	private static final String EXEC_TRANSACTION      = "Transaction";
	private static final String EXEC_EXPR              = "Expr";
	private static final String EXEC_LOOP              = "Loop";
	private static final String EXEC_SQL_EXEC         = "SqlExec";
	private static final String EXEC_SQL_SELECT       = "SqlSelect";
	private static final String EXEC_SQL_COMIT        = "SqlCommit";
	private static final String EXEC_SQL_ROLLBACK     = "SqlRollback";
	private static final String EXEC_EXPECT            = "Expect";
	private static final String EXEC_COMMAND           = "Command";
	private static final String EXEC_WAIT              = "Wait";
	private static final String EXEC_NOTIFY            = "Notify";
	private static final String EXEC_SLEEP             = "Sleep";
	private static final String EXEC_EXTERNAL_CLASS  = "ExternalClass";
	

	public static AbstractExec factory(String type, String name, ExecIF parent,
			MainContext mCtx) throws DBTException {
		
		if(EXEC_CASE.equals(type))
			return new ExecBlockCase(name, parent, mCtx);
		
		if(EXEC_DESCRIPTION.equals(type))
			return new ExecLineDescription(name, parent, mCtx);
		
		if(EXEC_PREWORK.equals(type))
			return new ExecBlockPrework(name, parent, mCtx);
		
		if(EXEC_POSTWORK.equals(type))
			return new ExecBlockPostwork(name, parent, mCtx);
		
		if(EXEC_PERFORMANCE_TEST.equals(type))
			return new ExecBlockPerformanceTest(name, parent, mCtx);

		if(EXEC_FUNCTIONAL_TEST.equals(type))
			return new ExecBlockFunctionalTest(name, parent, mCtx);

		if(EXEC_SESSION.equals(type))
			return new ExecBlockSession(name, parent, mCtx);

		if(EXEC_THREAD.equals(type))
			return new ExecBlockThread(name, parent, mCtx);

		if(EXEC_TRANSACTION.equals(type))
			return new ExecBlockTransaction(name, parent, mCtx);

		if(EXEC_EXPR.equals(type))
			return new ExecLineExpr(name, parent, mCtx);
		
		if(EXEC_LOOP.equals(type))
			return new ExecBlockLoop(name, parent, mCtx);
		
		if(EXEC_SQL_EXEC.equals(type))
			return new ExecBlockSQLExec(name, parent, mCtx);
		
		if(EXEC_SQL_SELECT.equals(type))
			return new ExecBlockSQLSelect(name, parent, mCtx);
		
		if(EXEC_SQL_COMIT.equals(type))
			return new ExecLineSQLCommit(name, parent, mCtx);
		
		if(EXEC_SQL_ROLLBACK.equals(type))
			return new ExecLineSQLRollback(name, parent, mCtx);
		
		if(EXEC_EXPECT.equals(type))
			return new ExecLineExpect(name, parent, mCtx);
		
		if(EXEC_COMMAND.equals(type))
			return new ExecLineCommand(name, parent, mCtx);
		
		if(EXEC_WAIT.equals(type))
			return new ExecLineWait(name, parent, mCtx);
		
		if(EXEC_NOTIFY.equals(type))
			return new ExecLineNotify(name, parent, mCtx);
		
		if(EXEC_SLEEP.equals(type))
			return new ExecLineSleep(name, parent, mCtx);

		if(EXEC_EXTERNAL_CLASS.equals(type))
			return new ExecLineExternalClass(name, parent, mCtx);
		

		throw new DBTException("Invalid processing type " + type);
	}
	
	public static boolean isExec(String type) {
		
		if(EXEC_CASE.equals(type))
			return true;

		if(EXEC_DESCRIPTION.equals(type))
			return true;

		if(EXEC_PREWORK.equals(type))
			return true;
		
		if(EXEC_POSTWORK.equals(type))
			return true;
		
		if(EXEC_PERFORMANCE_TEST.equals(type))
			return true;

		if(EXEC_FUNCTIONAL_TEST.equals(type))
			return true;

		if(EXEC_SESSION.equals(type))
			return true;

		if(EXEC_THREAD.equals(type))
			return true;

		if(EXEC_TRANSACTION.equals(type))
			return true;

		if(EXEC_EXPR.equals(type))
			return true;
		
		if(EXEC_LOOP.equals(type))
			return true;
		
		if(EXEC_SQL_EXEC.equals(type))
			return true;
		
		if(EXEC_SQL_SELECT.equals(type))
			return true;
		
		if(EXEC_SQL_COMIT.equals(type))
			return true;
		
		if(EXEC_SQL_ROLLBACK.equals(type))
			return true;
		
		if(EXEC_EXPECT.equals(type))
			return true;
		
		if(EXEC_COMMAND.equals(type))
			return true;
		
		if(EXEC_WAIT.equals(type))
			return true;
		
		if(EXEC_NOTIFY.equals(type))
			return true;
		
		if(EXEC_SLEEP.equals(type))
			return true;

		if(EXEC_EXTERNAL_CLASS.equals(type))
			return true;

		return false;
	}
}
