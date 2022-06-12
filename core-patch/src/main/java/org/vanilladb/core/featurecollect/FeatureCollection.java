package org.vanilladb.core.featurecollect;

public class FeatureCollection{
	int txNum;
	long latency;
	public long startTime;
	int readCount;
	int writeCount;
	int txnType;
	int concurrentlyExecutingTxNum;
	float memoryUsage;
	int readRecordSize;
	int writeRecordSize;
	double cpuUsage;
	int numberOfQueuingTx;
}
