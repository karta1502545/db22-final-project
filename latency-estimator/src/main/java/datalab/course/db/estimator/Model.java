package datalab.course.db.estimator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.regression.RandomForest;

public class Model {
	
	public static Model fit(DataFrame trainingSet) {
		RandomForest forest = RandomForest.fit(Formula.lhs(Constants.FIELD_NAME_LATENCY),
				trainingSet, 200, trainingSet.ncols() / 3, 2, 100, 5, 1.0);
		return new Model(forest);
	}
	
	public static Model loadFromFile(File modelFile) {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(modelFile))) {
			RandomForest forest = (RandomForest) in.readObject();
			return new Model(forest);
		} catch (Exception e) {
			throw new RuntimeException("error while reading the model from file: " + modelFile, e);
		}
	}
	
	private RandomForest forest;
	
	private Model(RandomForest forest) {
		this.forest = forest;
	}
	
	public String trainingSummary() {
		return forest.metrics().toString();
	}
	
	public double testMeanAbsoluteError(DataFrame testingSet) {
		DataFrame features = testingSet.drop(Constants.FIELD_NAME_LATENCY);
		double[] labels = testingSet.doubleVector(Constants.FIELD_NAME_LATENCY).array();
		double[] predictions = forest.predict(features);
		
		double sum = 0.0;
		for (int i = 0; i < predictions.length; i++) {
			sum += Math.abs(predictions[i] - labels[i]);
		}
		
		return sum / predictions.length;
	}
	
	public double testMeanRelativeError(DataFrame testingSet) {
		DataFrame features = testingSet.drop(Constants.FIELD_NAME_LATENCY);
		double[] labels = testingSet.doubleVector(Constants.FIELD_NAME_LATENCY).array();
		double[] predictions = forest.predict(features);
		
		double sum = 0.0;
		for (int i = 0; i < predictions.length; i++) {
			double diff = Math.abs(predictions[i] - labels[i]);
			sum += diff / labels[i];
		}
		
		return sum / predictions.length;
	}
	
	public void saveToFile(File savePath) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savePath))) {
			out.writeObject(forest);
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException("error while writing the model to file: " + savePath, e);
		}
	}
}
