package org.vanilladb.core.featurecollect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class FeatureMap {
	private static ConcurrentHashMap<Integer, FeatureCollection> featureMap;
    public FeatureMap() {
        featureMap = new ConcurrentHashMap<Integer, FeatureCollection>();
        System.out.println("Hello from initFeatureMap");

        // FeatureCollection temp1 = new FeatureCollection();
        // temp1.txNum = 10;
        // featureMap.put((Integer)(int)1, temp1);
        // FeatureCollection temp2 = new FeatureCollection();
        // temp2.txNum = 9;
        // featureMap.put((Integer)(int)2, temp2);
        // for(FeatureCollection element : featureMap.values())
        //     System.out.println("txNum = " + element.txNum);
    }
    public ConcurrentHashMap<Integer, FeatureCollection> getFeatureMap() {
		return featureMap;
	}
    public void setCpuUsage(double cpuUsage, int txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.cpuUsage = cpuUsage;
    }

    public void setNumberOfQueuingTx(int numberOfQueuingTx, int txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.numberOfQueuingTx = numberOfQueuingTx;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void setReadRecordSize(int readRecordSize, int txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.readRecordSize = readRecordSize;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void setWriteRecordSize(int writeRecordSize, int txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.writeRecordSize = writeRecordSize;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void setMemoryUsage(float memoryUsage, int txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.memoryUsage = memoryUsage;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void setLatency(long latency, int txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.latency = latency;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void setStartTime(long startTime, int txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.startTime = startTime;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void setTxNum(int txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.txNum = txNum;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void setActiveTxCount(int activeTxCount, long txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.concurrentlyExecutingTxNum = activeTxCount;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void setTxnType(int txnType, long txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.txnType = txnType;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void setReadCount(int readCount, long txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.readCount = readCount;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void setWriteCount(int writeCount, long txNum) {
        FeatureCollection temp = featureMap.getOrDefault((Integer)(int)txNum, new FeatureCollection());
        temp.writeCount = writeCount;
        featureMap.put((Integer)(int)txNum, temp);
    }
    public void outputFeatureMapAsCSV() {
        String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

        PrintWriter pw = null;
        //output feature.csv
        try {
            pw = new PrintWriter(new File("feature.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder buff = new StringBuilder();
        buff.append("Transaction ID, Start Time, readCount, writeCount, queryType, concurrentlyExecutingTxNum, readRecordSize, writeRecordSize, numberOfQueuingTx, memoryUsage, cpuUsage").append(LINE_SEPARATOR);
        // System.out.println(featureMap.values());
        for (FeatureCollection txFeature : featureMap.values()) {
            buff.append(txFeature.txNum).append(", ")
                .append(txFeature.startTime).append(", ")
                .append(txFeature.readCount).append(", ")
                .append(txFeature.writeCount).append(", ")
                .append(txFeature.txnType).append(", ")
                .append(txFeature.concurrentlyExecutingTxNum).append(", ")
                .append(txFeature.readRecordSize).append(", ")
                .append(txFeature.writeRecordSize).append(", ")
                .append(txFeature.numberOfQueuingTx).append(", ")
                .append(txFeature.memoryUsage).append(", ")
                .append(txFeature.cpuUsage)
                .append(LINE_SEPARATOR);
        }
        pw.write(buff.toString());
        pw.close();

        //output latency.csv
        try {
            pw = new PrintWriter(new File("latency.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder buff1 = new StringBuilder();
        buff1.append("Transaction ID, Latency").append(LINE_SEPARATOR);
        // System.out.println(featureMap.values());
        for (FeatureCollection txFeature : featureMap.values()) {
            buff1.append(txFeature.txNum).append(", ")
                .append(txFeature.latency)
                .append(LINE_SEPARATOR);
        }
        pw.write(buff1.toString());
        pw.close();

        //output total.csv
        try {
            pw = new PrintWriter(new File("total.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder buff2 = new StringBuilder();
        buff2.append("Transaction ID, Latency, Start Time, readCount, writeCount, queryType, concurrentlyExecutingTxNum, readRecordSize, writeRecordSize, numberOfQueuingTx, memoryUsage, cpuUsage").append(LINE_SEPARATOR);
        // System.out.println(featureMap.values());
        for (FeatureCollection txFeature : featureMap.values()) {
            buff2.append(txFeature.txNum).append(", ")
                .append(txFeature.latency).append(", ")
                .append(txFeature.startTime).append(", ")
                .append(txFeature.readCount).append(", ")
                .append(txFeature.writeCount).append(", ")
                .append(txFeature.txnType).append(", ")
                .append(txFeature.concurrentlyExecutingTxNum).append(", ")
                .append(txFeature.readRecordSize).append(", ")
                .append(txFeature.writeRecordSize).append(", ")
                .append(txFeature.numberOfQueuingTx).append(", ")
                .append(txFeature.memoryUsage).append(", ")
                .append(txFeature.cpuUsage)
                .append(LINE_SEPARATOR);
        }       
        pw.write(buff2.toString());
        pw.close();

        System.out.println("Features of each transaction are successfully written in csv!");
    }
}