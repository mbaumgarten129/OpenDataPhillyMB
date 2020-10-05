package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.ParkingFileReader;

public class ParkingViolationProcessor {
	private Map<String, HashSet<ParkingViolation>> parkingViolations;
	private PopulationProcessor populationProcessor;
	private Map<String, Double> finesPerCapita = null;
	
	public ParkingViolationProcessor(ParkingFileReader parkingFileReader, PopulationProcessor populationProcessor) {
		this.parkingViolations = parkingFileReader.readAllParkingViolations();
		this.populationProcessor = populationProcessor;
	}
	
	/**
	 * Using memoization to get fines per capita
	 * @return the fines per capita
	 */
	public Map<String, Double> getFinesPerCapita() {
		if (finesPerCapita == null) {
			finesPerCapita = calcFinesPerCapita();
		}
		
		return finesPerCapita;
	}
	
	/**
	 * Calculates the total fines per capita for each zip code
	 * @return A map which maps the zip code to the fines per capita for that zip code
	 */
	private Map<String, Double> calcFinesPerCapita() {
		Map<String, Double> zipCodeFinesPerCapita = new HashMap<String, Double>();
		Map<String, Integer> populations = populationProcessor.getPopulations();
		
		if (populations == null) {
			return zipCodeFinesPerCapita;
		}
		
		
		for (String zip : populations.keySet()) {
			if (!parkingViolations.containsKey(zip)) {
				continue;
			}
			double currZipFines = 0;
			for (ParkingViolation violation : parkingViolations.get(zip)) {
				if (violation.getState().contentEquals("PA")) {
					currZipFines = currZipFines + violation.getFine();
				}
			}
			
			if (populations.get(zip) > 0 && currZipFines > 0) {
				zipCodeFinesPerCapita.put(zip, currZipFines / (double) populations.get(zip));
			}
		}
		
		return zipCodeFinesPerCapita;
	}
	
}
