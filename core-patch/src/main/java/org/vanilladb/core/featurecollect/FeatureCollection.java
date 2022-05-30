package org.vanilladb.core.featurecollect;

public class FeatureCollection{
	int txNum;
	int startTime;
	int readCount;
	int writeCount;
	String queryType;
	int concurrentlyExecutingTxNum;
	int accessedTableNum;
}
