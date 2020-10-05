package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.PropertiesCsvReader;

public class PropertyProcessor {
	private Map<String, HashSet<Property>> properties; 
	private PopulationProcessor populationProcessor;
	private ParkingViolationProcessor parkingViolationProcessor;
	private Map<String, Integer> averageMarketValue = new HashMap<String, Integer>();
	private Map<String, Integer> averageLivableArea = new HashMap<String, Integer>();
	private Map<String, Integer> marketValuePerCapita = new HashMap<String, Integer>();
	private Integer propertValueZipWithMostFinesPerCapita = null;
	
	public PropertyProcessor(PropertiesCsvReader propertiesCsvReader, PopulationProcessor populationProcessor, 
			ParkingViolationProcessor parkingViolationProcessor) {
		this.properties = propertiesCsvReader.readAllProperties();
		this.populationProcessor = populationProcessor;
		this.parkingViolationProcessor = parkingViolationProcessor;
	}
	
	/**
	 * Uses memoization to get the average market value for a zip code
	 * @param zip the zip code
	 * @return the average market value for a zip code
	 */
	public int getAverageMarketValue(String zip) {
		if (zip == null) {
			return 0;
		}
		if (!averageMarketValue.containsKey(zip)) {
			averageMarketValue.put(zip, calcAverageMarketValue(zip));
		}
		return averageMarketValue.get(zip);
	}
	
	/**
	 * Calculates the average market value in a specific zip code
	 * @param zip the zip code
	 * @return the average
	 */
	private int calcAverageMarketValue(String zip) {
		return calcAveragePropertyFeature(zip, new PropertyMarketValGetter());
	}
	
	/**
	 * Uses memoization to get the average livable area for a zip code
	 * @param zip the zip code
	 * @return the average livable area for a zip code
	 */
	public int getAverageLivableArea(String zip) {
		if (zip == null) {
			return 0;
		}
		if (!averageLivableArea.containsKey(zip)) {
			averageLivableArea.put(zip, calcAverageLivableArea(zip));
		}
		return averageLivableArea.get(zip);
	}
	
	/**
	 * Calculates the average livable area in a specific zipcode 
	 * @param zip the zip code
	 * @return the average
	 */
	private int calcAverageLivableArea(String zip) {
		return calcAveragePropertyFeature(zip, new PropertyLivableAreaGetter());
	}
	
	/**
	 * Calculates the average of a property feature for a specific zipcode
	 * @param zip the zip code
	 * @param propertyFeatureGetter tells which feature to get the average for
	 * @return the average
	 */
	private int calcAveragePropertyFeature(String zip, PropertyFeatureGetter propertyFeatureGetter) {
		if (!properties.containsKey(zip)) {
			return 0;
		}
		double total = 0;
		int count = 0;
		for (Property property : properties.get(zip)) {
			total = total + propertyFeatureGetter.get(property);
			count++;
		}
		if (total == 0.0) {
			return 0;
		} else {
			return (int) (total / count);
		}
	}
	
	/**
	 * Uses memoization to get residential market val per capita for a zip code
	 * @param zip the zip code
	 * @return the market value per capita
	 */
	public int getResidentialMarketValPerCapita(String zip) {
		if (zip == null) {
			return 0;
		}
		if (!marketValuePerCapita.containsKey(zip)) {
			marketValuePerCapita.put(zip, calcResidentialMarketValPerCapita(zip));
		}
		return marketValuePerCapita.get(zip);
	}
	
	/**
	 * Calculates the residential market value per capita for a specific zipcode
	 * @param zip the zip code
	 * @return the residential market value per capita
	 */
	private int calcResidentialMarketValPerCapita(String zip) {
		Map<String, Integer> populations = populationProcessor.getPopulations();
		if (populations == null) {
			return 0;
		}
		if (!properties.containsKey(zip) || !populations.containsKey(zip)) {
			return 0;
		}
		double total = 0;
		for (Property property : properties.get(zip)) {
			total = total + property.getMarketValue();
		}
		return (int) (total / populations.get(zip));
	}
	
	/**
	 * Uses memoization to get the average property value in the zip code with the msot fines per capita
	 * @return the average property value in the zip code with the most fines per capita
	 */
	public int getAvePropValInZipMostFinesPerCapita() {
		if (propertValueZipWithMostFinesPerCapita == null) {
			propertValueZipWithMostFinesPerCapita = calcAvePropValInZipMostFinesPerCapita();
		}
		
		return propertValueZipWithMostFinesPerCapita;
	}
	
	/**
	 * Calculates the average property value in the zip code with the most fines per capita
	 * @return the average property value in the zip code with the most fines per capita
	 */
	private int calcAvePropValInZipMostFinesPerCapita() {
		Map<String, Double> finesPerCapita = parkingViolationProcessor.getFinesPerCapita();
		String maxZip = null;
		Double maxFinesPerCapita = 0.0;
		
		for (String zip : finesPerCapita.keySet()) {
			if (finesPerCapita.get(zip) > maxFinesPerCapita) {
				maxFinesPerCapita = finesPerCapita.get(zip);
				maxZip = zip;
			}
		}
		
		return getAverageMarketValue(maxZip);
	}
}
