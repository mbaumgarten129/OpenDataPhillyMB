package edu.upenn.cit594.data;

/**
 * this class represents a ParkingViolation object that is created from inputs taken from a parking violation file
 */

public class ParkingViolation {
	
    private double fine;
    private String state;
    private String zipCode;
    
    public ParkingViolation(String zipCode, String state, double fine) {
    	this.zipCode = zipCode;
    	this.state = state;
    	this.fine = fine;
    }

    public String getZipCode() {
        return zipCode;
    }
    
    public double getFine() {
    	return fine;
    }
    
    public String getState() {
    	return state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
