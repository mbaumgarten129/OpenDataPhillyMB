package edu.upenn.cit594.datamanagement;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.upenn.cit594.data.ParkingViolation;

public interface ParkingFileReader {

	/**
	 * method to read parking files
	 * @return map with zip code string key, set of parking violations value
	 */
	Map<String, HashSet<ParkingViolation>> readAllParkingViolations();
	
}
