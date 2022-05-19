package datalab.course.db.estimator;

import java.io.File;

import com.moandjiezana.toml.Toml;

public class Config {
	
	private long warmupEndTime;
	
	public static Config load(File file) {
		Toml toml = new Toml().read(file);
		
		Config config = new Config();
		config.warmupEndTime = toml.getTable("general").getLong("warm_up_end_time").longValue();
		
		return config;
	}
	
	public long warmupEndTime() {
		return warmupEndTime;
	}
}
