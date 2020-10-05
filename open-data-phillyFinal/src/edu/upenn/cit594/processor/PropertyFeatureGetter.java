package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public interface PropertyFeatureGetter {
	
	/**
	 * Gets the value for a double field of a property
	 * @param property The property 
	 * @return double with the value of the field
	 */
	public double get(Property property);
	
}
