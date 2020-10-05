package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;
import java.util.List;

/**
 * this class contains helper methods that assist in parsing the data from the files
 */

public class ReaderHelpers {

	/**
	 * checks if string can be parsed to double
	 * @param s the string
	 * @return true if the string can be parsed to double.  false otherwise
	 */
	public static boolean isValidDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * checks if string can be parsed to an integer
	 * @param s the string
	 * @return true if the string can be parsed to an integer. false otherwise
	 */
	public static boolean isValidInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * checks if string is a valid zip code
	 * @param s the string
	 * @return true if the string is a valid zip code
	 */
	public static boolean isValidZip(String s) {
		try {
			s = s.substring(0, 5);
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * helps parse data with commas in quotes
	 * @param s s
	 * @return data list
	 */
	public static List<String> separateByComma(String s) {
		List<String> allData = new ArrayList<String>();
		boolean shouldSplit = true;
		StringBuilder currData = new StringBuilder();
		char[] csv = s.toCharArray();
		for (char c : csv) {
			if (c == ',') {
				if (shouldSplit) {
					allData.add(currData.toString());
					currData.setLength(0);
				} else {
					currData.append(c);
				}
			}
			else if (c == '"') {
				shouldSplit = !shouldSplit;
			}
			else {
				currData.append(c);
			}
		}
		if (!currData.toString().trim().contentEquals("")) {
			allData.add(currData.toString());
		}
		
		return allData;
	}

}
