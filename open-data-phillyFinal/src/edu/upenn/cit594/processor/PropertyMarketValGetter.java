package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class PropertyMarketValGetter implements PropertyFeatureGetter {

	@Override
	public double get(Property property) {
		return property.getMarketValue();
	}
	
}
