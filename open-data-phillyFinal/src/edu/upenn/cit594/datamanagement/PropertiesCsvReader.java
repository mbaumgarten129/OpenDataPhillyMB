package edu.upenn.cit594.datamanagement;
/**
 * this class reads property  files
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.logging.ActivityLogger;

public class PropertiesCsvReader {
	private String filename;
	
	public PropertiesCsvReader(String filename) {
		this.filename = filename;
	}

	/**
	 * reads property csv file
	 * @return map with zip code key and set of property objects as a value
	 */
	public Map<String, HashSet<Property>> readAllProperties() {
		HashMap<String, HashSet<Property>> propertyMap = new HashMap<>();
        File propertyFile=new File(filename);
        try {
            Scanner fileReader=new Scanner(propertyFile);
            ActivityLogger.getInstance().mustLog(filename);
            String[] labels = fileReader.nextLine().split(",");
            int livableAreaCol=-1;
            int marketValCol=-1;
            int zipCodeCol=-1;
            
            for (int i=0; i<labels.length; i++) {
            	if (labels[i].contentEquals("total_livable_area")) {
            		livableAreaCol = i;
            	} else if (labels[i].contentEquals("zip_code")) {
            		zipCodeCol = i;
            	} else if (labels[i].contentEquals("market_value")) {
            		marketValCol = i;
            	}
            }
            
            if (livableAreaCol == -1 || marketValCol == -1 || zipCodeCol == -1) {
            	fileReader.close();
            	return null;
            }
            
            while(fileReader.hasNextLine()){
                String line=fileReader.nextLine();
                List<String> data = ReaderHelpers.separateByComma(line);
                
                if (!ReaderHelpers.isValidDouble(data.get(livableAreaCol)) || !ReaderHelpers.isValidDouble(data.get(marketValCol)) 
                		|| !ReaderHelpers.isValidZip(data.get(zipCodeCol))) {
                	// Input data was missing or inaccurate so skip this row
                	continue;
                }
                else {
                	double marketVal = Double.parseDouble(data.get(marketValCol));
                	double livableArea = Double.parseDouble(data.get(livableAreaCol));
                	String zipCode = data.get(zipCodeCol).substring(0, 5);
                	Property prop = new Property(marketVal, livableArea, zipCode);
                	HashSet<Property> currZipSet = null;
                	if (propertyMap.containsKey(zipCode)) {
                		currZipSet = propertyMap.get(zipCode);
                		currZipSet.add(prop);
                	} else {
                		currZipSet = new HashSet<>();
                		currZipSet.add(prop);
                	}
                	propertyMap.put(zipCode, currZipSet);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return propertyMap;
	}

	
	
}
