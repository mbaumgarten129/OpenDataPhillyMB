package edu.upenn.cit594.data;

/**
 * this class represents a PropertyObject that is created from inputs taken from a property file
 */

public class Property {

    private double marketValue;
    private double totalLivableArea;
    private String zipCode;


    public Property(double marketValue, double totalLivableArea, String zipCode) {
        this.marketValue = marketValue;
        this.totalLivableArea = totalLivableArea;
        this.zipCode = zipCode;
    }


    public double getMarketValue() {
        return marketValue;
    }

    public void setMarket_value(int market_value) {
        this.marketValue = market_value;
    }

    public double getTotalLivableArea() {
        return totalLivableArea;
    }

    public void setTotalLivableArea(int total_livable_area) {
        this.totalLivableArea = total_livable_area;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
