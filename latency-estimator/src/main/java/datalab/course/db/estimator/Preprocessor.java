package datalab.course.db.estimator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import smile.data.DataFrame;
import smile.data.Tuple;

public class Preprocessor {
	
	public static DataFrame preprocess(DataFrame features, DataFrame latencies, Config config) {
		Stream<Tuple> featStream = features.stream();
		Stream<Tuple> latStream = latencies.stream();
		
		// Filter warm-up transactions in features
		featStream = filterWarmUpTransactions(featStream, config.warmupEndTime());
		
		// Sort
		featStream = sortById(featStream);
		latStream = sortById(latStream);
		
		// Filter warm-up transactions in latencies
		DataFrame[] dfs = leaveMatchedRecords(featStream, latStream);
		
		// Drop id and start time columns
		features = dfs[0].drop(Constants.FIELD_NAME_ID, Constants.FIELD_NAME_START_TIME);
		latencies = dfs[1].drop(Constants.FIELD_NAME_ID);
		
		// Merge two data frames
		return features.merge(latencies);
	}
	
	private static Stream<Tuple> filterWarmUpTransactions(Stream<Tuple> stream, long warmUpEndTime) {
		return stream.filter(tuple ->
			tuple.getLong(Constants.FIELD_NAME_START_TIME) > warmUpEndTime
		);
	}
	
	private static Stream<Tuple> sortById(Stream<Tuple> stream) {
		return stream.sorted(new Comparator<Tuple>() {

			@Override
			public int compare(Tuple t1, Tuple t2) {
				long id1 = t1.getLong(Constants.FIELD_NAME_ID);
				long id2 = t2.getLong(Constants.FIELD_NAME_ID);
				return (int) (id1 - id2);
			}
			
		});
	}
	
	private static DataFrame[] leaveMatchedRecords(Stream<Tuple> featStream, Stream<Tuple> latStream) {
		List<Tuple> newFeatRows = new ArrayList<Tuple>();
		List<Tuple> newLatRows = new ArrayList<Tuple>();
		Iterator<Tuple> featIter = featStream.iterator();
		Iterator<Tuple> latIter = latStream.iterator();
		
		Tuple featTuple = featIter.next();
		Tuple latTuple = latIter.next();
		while (featIter.hasNext() && latIter.hasNext()) {
			long featId = featTuple.getLong(Constants.FIELD_NAME_ID);
			long latId = latTuple.getLong(Constants.FIELD_NAME_ID);
			
			if (featId < latId) {
				featTuple = featIter.next();
			} else if (featId > latId) {
				latTuple = latIter.next();
			} else {
				newFeatRows.add(featTuple);
				newLatRows.add(latTuple);
				featTuple = featIter.next();
				latTuple = latIter.next();
			}
		}
		
		// Create new DataFrame
		DataFrame features = DataFrame.of(newFeatRows);
		DataFrame latencies = DataFrame.of(newLatRows);

		return new DataFrame[] {features, latencies};
	}
}
