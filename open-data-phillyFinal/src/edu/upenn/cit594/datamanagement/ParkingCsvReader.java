package edu.upenn.cit594.datamanagement;
/**
 * this class reads parking csv files and implements the ParkingFileReader interface
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.logging.ActivityLogger;

public class ParkingCsvReader implements ParkingFileReader {
	private String filename;
	
	public ParkingCsvReader(String filename) {
		this.filename = filename;
	}
	
	
	@Override
	public Map<String, HashSet<ParkingViolation>> readAllParkingViolations() {
		HashMap<String, HashSet<ParkingViolation>> violationMap = new HashMap<>();
        HashSet<ParkingViolation> totalViolationSet = new HashSet<>();
        File parkFile = new File(filename);
        try {
            Scanner fileReader = new Scanner(parkFile);
            ActivityLogger.getInstance().mustLog(filename);
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                List<String> info = ReaderHelpers.separateByComma(line);
                
                //ignoring poorly formatted lines
                if (info.size() != 7) {
                	continue; 
                }
                
                if (!ReaderHelpers.isValidDouble(info.get(1)) || !ReaderHelpers.isValidZip(info.get(6)) || info.get(4).contentEquals("")) {
                	continue;
                }
                ParkingViolation newPV = new ParkingViolation(info.get(6).substring(0,5), info.get(4), Double.parseDouble(info.get(1))); 	
                totalViolationSet.add(newPV);

            }
            fileReader.close();
        } catch (FileNotFoundException e) {
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
