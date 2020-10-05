package edu.upenn.cit594.datamanagement;
/**
 * this class reads parking json files and implements the ParkingFileReader interface
 */

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.logging.ActivityLogger;

public class ParkingJsonReader implements ParkingFileReader {
	private String filename;
	
	public ParkingJsonReader(String filename) {
		this.filename = filename;
	}
	
	@Override
	public Map<String, HashSet<ParkingViolation>> readAllParkingViolations() {
		HashMap<String, HashSet<ParkingViolation>> violationMap = new HashMap<>();
        HashSet<ParkingViolation> totalViolationSet = new HashSet<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray violations=(JSONArray) parser.parse(new FileReader(filename));
            ActivityLogger.getInstance().mustLog(filename);
            Iterator iterator = violations.iterator();
            while (iterator.hasNext()) {
                JSONObject vio = (JSONObject) iterator.next();
                // If there is missing data skip
                if (!vio.containsKey("zip_code") || !vio.containsKey("state") || !vio.containsKey("fine")) {
                	continue;
                }
                
                String zipCode = vio.get("zip_code").toString();
                String fine = vio.get("fine").toString();
                String state = vio.get("state").toString();
                if (!ReaderHelpers.isValidZip(zipCode) || !ReaderHelpers.isValidDouble(fine) ||
                		 state.contentEquals("")) {
                	continue;
                }
                
                ParkingViolation newPv = new ParkingViolation(zipCode.substring(0, 5), state, Double.parseDouble(fine));
                totalViolationSet.add(newPv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (ParkingViolation pv : totalViolationSet) {
            HashSet<ParkingViolation> violationByZipSet = new HashSet<>();
            violationMap.put(pv.getZipCode(), violationByZipSet);
        }
        for (String code : violationMap.keySet()) {
            for (ParkingViolation pv : totalViolationSet) {
                if (pv.getZipCode().equals(code)) {
                    violationMap.get(code).add(pv);
                }

            }
        }
        return violationMap;
	}

}
