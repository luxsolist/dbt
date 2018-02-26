package luxk.jdbt;

public class WorkerFactory {
	
	public enum WorkerType {
		PERFORMANCE,
		FUNCTIONAL,
	};
	
	private WorkerType wType;
	private MainContext mCtx;
	
	private int thrId;
	
	public WorkerFactory(WorkerType wType, MainContext mCtx) {
		this.wType = wType;
		this.mCtx = mCtx;
		
		this.thrId = 0;
	}
	
	public Worker getWorker(String name) {
		Worker w;
		
		switch(this.wType) {
		case PERFORMANCE:
			w = new Worker(name, mCtx);
			w.setId(this.thrId++);
			break;
		case FUNCTIONAL:
			w = new Worker(name, mCtx);
			w.setId(this.thrId++);
			break;
		default:
			w = null;
		}
		
		return w;
	}
}
