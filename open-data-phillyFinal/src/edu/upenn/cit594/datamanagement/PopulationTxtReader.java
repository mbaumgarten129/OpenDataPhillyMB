package edu.upenn.cit594.datamanagement;
/**
 * this class reads population files
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.upenn.cit594.logging.ActivityLogger;

public class PopulationTxtReader {
	private String filename;
	
	public PopulationTxtReader(String filename) {
		this.filename = filename;
	}
	
    /**
     * reads a txt file
     * @return map with with zip code key and population integer value
     */
	public Map<String, Integer> readAllZipCodePopulations() {
		HashMap<String, Integer> zipPopMap= new HashMap<>();
        File popFile=new File(filename);
        try {
            Scanner reader=new Scanner(popFile);
            ActivityLogger.getInstance().mustLog(filename);
            while(reader.hasNextLine()){
                String line=reader.nextLine();
                String[] arrLine=line.split(" ");
                // Check for missing data
                if (arrLine.length != 2) {
                	continue;
                }
                // Check for poorly formatted data
                if (!ReaderHelpers.isValidZip(arrLine[0]) || !ReaderHelpers.isValidInt(arrLine[1])) {
                	continue;
                }
                int pop=Integer.parseInt(arrLine[1]);
                zipPopMap.put(arrLine[0].substring(0, 5), pop);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return zipPopMap;
	}
	
}
