package datalab.course.db.estimator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import smile.data.DataFrame;
import smile.data.type.DataType;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;
import smile.io.CSV;

public class DataLoader {
	
	public static DataFrame loadCsvAsDataFrame(Path path) {
		DataFrame df = null;
		try {
			StructType schema = inferSchema(path);
			CSVFormat.Builder builder = CSVFormat.Builder.create();
			builder.setHeader(); // Make it infer the header names from the first row
			CSV csv = new CSV(builder.build());
			csv.schema(schema);
			df = csv.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return df;
	}
	
	private static CSVParser newCsvParser(Path path) throws IOException {
		return CSVParser.parse(path, StandardCharsets.UTF_8, CSVFormat.DEFAULT);
	}
	
	private static boolean isHeader(CSVRecord record) {
		return record.get(0).trim().equals(Constants.FIELD_NAME_ID);
	}
	
	private static StructType inferSchema(Path path) throws IOException {
		// Find the header and the first row of values
		CSVRecord header = null;
		CSVRecord values = null;
		try (CSVParser csv = newCsvParser(path)) {
            for (CSVRecord record : csv) {
            	if (isHeader(record))
            		header = record;
            	else
            		values = record;
            	
            	if (header != null && values != null)
            		break;
            }
        }
		
		// Infer the schema
		String[] names = new String[header.size()];
        DataType[] types = new DataType[header.size()];
        for (int i = 0; i < names.length; i++) {
        	String fieldName = header.get(i).trim();
            names[i] = fieldName;

    		// 'Transaction ID' and 'Start Time' are always LongType
    		// Otherwise, treat the rest as Double values.
            if (fieldName.equals(Constants.FIELD_NAME_ID) ||
            		fieldName.equals(Constants.FIELD_NAME_START_TIME))
            	types[i] = DataTypes.LongType;
            else
            	types[i] = DataTypes.DoubleType;
        }

		// Build the schema
        StructField[] fields = new StructField[names.length];
        for (int i = 0; i < fields.length; i++)
            fields[i] = new StructField(names[i], types[i]);
        return DataTypes.struct(fields);
	}

}
