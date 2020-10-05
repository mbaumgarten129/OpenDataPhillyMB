package edu.upenn.cit594.ui;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import edu.upenn.cit594.logging.ActivityLogger;
import edu.upenn.cit594.processor.ParkingViolationProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyProcessor;

public class UserInteraction {
	private PopulationProcessor populationProcessor;
	private ParkingViolationProcessor parkingViolationProcessor;
	private PropertyProcessor propertyProcessor;
	
	public UserInteraction(PopulationProcessor populationProcessor, ParkingViolationProcessor parkingViolationProcessor, 
			PropertyProcessor propertyProcessor) {
		this.populationProcessor = populationProcessor;
		this.parkingViolationProcessor = parkingViolationProcessor;
		this.propertyProcessor = propertyProcessor;
	}
	
	/**
	 * runs the userinteraction component of the project
	 */
	public void run() {
		String input = null;
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("Select calculation to be made:");
			System.out.println("0: exit program");
			System.out.println("1: total population of all zip codes");
			System.out.println("2: parking fines per capita all zip codes");
			System.out.println("3: average market value for residences in specific zip code");
			System.out.println("4: average livable area for residences in specific zip code");
			System.out.println("5: residential market value per capita for specific zip code");
			System.out.println("6: average property value in zip code that has the most fines per capita");
			
			input = scanner.nextLine();
			//log time and selection
			ActivityLogger.getInstance().mustLog(input);
			
			if (input.contentEquals("0")) {
				scanner.close();
				return;
			} else if (input.contentEquals("1")) {
				System.out.println(populationProcessor.getTotalPopulation());
			} else if (input.contentEquals("2")) {
				Map<String, Double> finesPerCapita = parkingViolationProcessor.getFinesPerCapita();
				int[] sortedKeys = sortMapKeysAscending(finesPerCapita);
				for (int i=0; i<sortedKeys.length; i++) {
					System.out.printf("%d %.4f%n", sortedKeys[i], ((int) (finesPerCapita.get(Integer.toString(sortedKeys[i])) * 10000)) / 10000.0);
				}
			} else if (input.contentEquals("3")) {
				System.out.println("Please enter a zip code:");
				input = scanner.nextLine();
				//log time and zip code
				ActivityLogger.getInstance().mustLog(input);
				System.out.println(propertyProcessor.getAverageMarketValue(input));
			} else if (input.contentEquals("4")) {
				System.out.println("Please enter a zip code:");
				input = scanner.nextLine();
				//log time and zip code
				ActivityLogger.getInstance().mustLog(input);
				System.out.println(propertyProcessor.getAverageLivableArea(input));
			} else if (input.contentEquals("5")) {
				System.out.println("Please enter a zip code:");
				input = scanner.nextLine();
				//log time and zip code
				ActivityLogger.getInstance().mustLog(input);
				System.out.println(propertyProcessor.getResidentialMarketValPerCapita(input));
			} else if (input.contentEquals("6")) {
				System.out.println(propertyProcessor.getAvePropValInZipMostFinesPerCapita());
			} else {
				System.out.println("You need to enter a message between 0 and 6");
				System.exit(0);
			}
			
		}
	}
	
	/**
	 * sorts the keys of a map in ascending order with string integers as its keys and Double as its values.  
	 * @param map A map which that maps strings to doubles
	 * @return An integer array with the keys in sorted order
	 */
	private int[] sortMapKeysAscending(Map<String, Double> map) {
		int[] keys = new int[map.size()];
		int counter = 0;
		for (String s : map.keySet()) {
			keys[counter] = Integer.parseInt(s);
			counter++;
		}
		Arrays.sort(keys);
		return keys;
	}
}
