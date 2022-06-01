package org.vanilladb.bench;

public enum ControlTransactionType {

	START_PROFILING(1001), STOP_PROFILING(1002), START_RECORD_FEATURE(1003);
	
	private int pid;
	
	ControlTransactionType(int pid) {
		this.pid = pid;
	}
	
	public static ControlTransactionType fromProcedureId(int pid) {
		switch (pid) {
		case 1001:
			return START_PROFILING;
		case 1002:
			return STOP_PROFILING;
		case 1003:
			return START_RECORD_FEATURE;
		}

		return null;
	}
	
	public int getProcedureId() {
		return pid;
	}
}
