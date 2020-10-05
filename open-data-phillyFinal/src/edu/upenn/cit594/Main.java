package edu.upenn.cit594;

import java.io.File;

import edu.upenn.cit594.datamanagement.ParkingCsvReader;
import edu.upenn.cit594.datamanagement.ParkingFileReader;
import edu.upenn.cit594.datamanagement.ParkingJsonReader;
import edu.upenn.cit594.datamanagement.PopulationTxtReader;
import edu.upenn.cit594.datamanagement.PropertiesCsvReader;
import edu.upenn.cit594.logging.ActivityLogger;
import edu.upenn.cit594.processor.ParkingViolationProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyProcessor;
import edu.upenn.cit594.ui.UserInteraction;

public class Main {
	
	/**
	 * main method that executes the program
	 * @param args array of runtime arguments
	 */
	public static void main(String[] args) {
		
		if (args.length != 5) {
			System.out.println("Sorry, you entered the incorrect number of arguments");
			System.exit(0);
		}
		
		ActivityLogger.setFilename(args[4]);
		ActivityLogger.getInstance().mustLog(args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " " + args[4]);
		
		if (!(args[0].contentEquals("json") || 
				args[0].contentEquals("csv"))) {
			System.out.println("Must type either json or csv for format of parking violations file.");
			System.exit(0);
		}
		File parkingViolationsFile = new File(args[1]);
		if (!parkingViolationsFile.canRead()) {
			System.out.println("Cannot read the file " + args[1]);
			System.exit(0);
		}
		File propertiesFile = new File(args[2]);
		if (!propertiesFile.canRead()) {
			System.out.println("Cannot read the file " + args[2]);
			System.exit(0);
		}
		File populationFile = new File(args[3]);
		if (!populationFile.canRead()) {
			System.out.println("Cannot read the file " + args[3]);
			System.exit(0);
		}
		
		
		
		ParkingFileReader parkingReader;
		
		
		if (args[0].contentEquals("json")) {
			parkingReader = new ParkingJsonReader(args[1]);
		} else {
			parkingReader = new ParkingCsvReader(args[1]);
		}
		
		PropertiesCsvReader propertiesReader = new PropertiesCsvReader(args[2]);
		PopulationTxtReader populationReader = new PopulationTxtReader(args[3]);
		
		PopulationProcessor populationProcessor = new PopulationProcessor(populationReader);
		ParkingViolationProcessor parkingViolationProcessor = new ParkingViolationProcessor(parkingReader, populationProcessor);
		PropertyProcessor propertyProcessor = new PropertyProcessor(propertiesReader, populationProcessor, parkingViolationProcessor);
		
		UserInteraction ui = new UserInteraction(populationProcessor, parkingViolationProcessor, propertyProcessor);
		ui.run();
	}
}
