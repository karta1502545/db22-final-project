package datalab.course.db.estimator;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import smile.data.DataFrame;

@Command(name = "latency-estimator", mixinStandardHelpOptions = true, version = "0.0.1",
		 description = "A latency estimator for VanillaCore transactions")
public class Estimator {
	private static Logger logger = Logger.getLogger(Estimator.class.getName());
	
	public static void main(String[] args) {
		int exitCode = new CommandLine(new Estimator())
				.setSubcommandsCaseInsensitive(true)
				.execute(args);
		System.exit(exitCode);
	}
	
	@Option(names = {"-c", "--config"}, required = false, defaultValue = "config.toml")
	File configFile;
	
	@Command(name = "train", mixinStandardHelpOptions = true)
	public int train(
			@Parameters(paramLabel = "FEATURE_FILE", description = "path to the feature file") File featureFile,
			@Parameters(paramLabel = "LATENCY_FILE", description = "path to the latency file") File latencyFile,
			@Parameters(paramLabel = "MODEL_SAVE_FILE", description = "output path for saving the model") File modelSaveFile
		) {
		
		// Load the configurations
		Config config = Config.load(configFile);
		
		// Load and process the data set
		DataFrame dataSet = loadAndProcessDataSet(featureFile, latencyFile, config);
		
		// Fit the model
		Model model = Model.fit(dataSet);
		
		// Report the results
		double trainingMae = model.testMeanAbsoluteError(dataSet);
		double trainingMre = model.testMeanRelativeError(dataSet);

		System.out.println("===== Result Summary =====");
		System.out.println(String.format("Training MAE: %.2f", trainingMae));
		System.out.println(String.format("Training MRE: %.2f%%", trainingMre * 100));
		
		// Save the model
		model.saveToFile(modelSaveFile);

		if (logger.isLoggable(Level.INFO))
			logger.info("Training completed. The model is saved to '" + modelSaveFile + "'");
		
		return 0;
	}
	
	@Command(name = "test", mixinStandardHelpOptions = true)
	public int test(
			@Parameters(paramLabel = "FEATURE_FILE", description = "path to the feature file") File featureFile,
			@Parameters(paramLabel = "LATENCY_FILE", description = "path to the latency file") File latencyFile,
			@Parameters(paramLabel = "MODEL_SAVE_FILE", description = "path to the pre-trained model") File modelFile
		) {
		
		// Load the configurations
		Config config = Config.load(configFile);
		
		// Load and process the data set
		DataFrame dataSet = loadAndProcessDataSet(featureFile, latencyFile, config);
		
		if (logger.isLoggable(Level.INFO))
			logger.info("Loading the model and testing...");
		
		// Load the model
		Model model = Model.loadFromFile(modelFile);
		
		// Report the results
		double testingMae = model.testMeanAbsoluteError(dataSet);
		double testingMre = model.testMeanRelativeError(dataSet);

		System.out.println("===== Result Summary =====");
		System.out.println(String.format("Testing MAE: %.2f", testingMae));
		System.out.println(String.format("Testing MRE: %.2f%%", testingMre * 100));

		if (logger.isLoggable(Level.INFO))
			logger.info("Testing completed.");
		
		return 0;
	}
	
	private static DataFrame loadAndProcessDataSet(File featureFile, File latencyFile, Config config) {
		// Load the data set
		if (logger.isLoggable(Level.INFO))
			logger.info("Loading data set...");

		DataFrame featureDataFrame = DataLoader.loadCsvAsDataFrame(featureFile.toPath());
		DataFrame latencyDataFrame = DataLoader.loadCsvAsDataFrame(latencyFile.toPath());
		
		if (logger.isLoggable(Level.INFO))
			logger.info("All data are loaded.");
		
		// Data Pre-process
		if (logger.isLoggable(Level.INFO))
			logger.info("Pre-processing data...");
		
		DataFrame dataSet = Preprocessor.preprocess(featureDataFrame, latencyDataFrame, config);

		if (logger.isLoggable(Level.INFO))
			logger.info("Pre-processing finished.");
		
		return dataSet;
	}
}
