package org.vanilladb.bench.server.procedure;

import org.vanilladb.core.server.VanillaDb;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureParamHelper;

public class StartRecordFeature extends StoredProcedure<StoredProcedureParamHelper> {
	public StartRecordFeature() {
		super(StoredProcedureParamHelper.newDefaultParamHelper());
	}

	@Override
	protected void prepareKeys() {
		// Do nothing
	}

	@Override
	protected void executeSql() {
		VanillaDb.startRecordFeature();
	}
}