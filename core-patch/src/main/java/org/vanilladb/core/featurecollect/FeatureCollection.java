package org.vanilladb.core.featurecollect;

public class FeatureCollection{
	int txNum;
	int startTime;
	int readCount;
	int writeCount;
	int txnType;
	int concurrentlyExecutingTxNum;
	int accessedTableNum;
}
