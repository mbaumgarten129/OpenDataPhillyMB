package edu.upenn.cit594.processor;

import java.util.Map;

import edu.upenn.cit594.datamanagement.PopulationTxtReader;

public class PopulationProcessor {
	private Map<String, Integer> populations;
	private Integer totalPopulation = null;
	
	public PopulationProcessor(PopulationTxtReader populationTxtReader) {
		this.populations = populationTxtReader.readAllZipCodePopulations();
	}
	
	/**
	 * Using memoization to get total population
	 * @return the total population
	 */
	public int getTotalPopulation() {
		if (totalPopulation == null) {
			totalPopulation = calcTotalPopulation();
		}
		
		return totalPopulation;
	}
	
	/**
	 * Calculates total population in all of the zip codes combined
	 * @return the total population
	 */
	private int calcTotalPopulation() {
		int totalPop = 0;
		for (String zip : populations.keySet()) {
			totalPop = totalPop + populations.get(zip);
		}
		return totalPop;
	}
	
	public Map<String, Integer> getPopulations() {
		return this.populations;
	}
}
