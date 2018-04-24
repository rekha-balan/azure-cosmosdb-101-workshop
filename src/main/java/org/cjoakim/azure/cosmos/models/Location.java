package org.cjoakim.azure.cosmos.models;

/**
 * This is a POJO class representing a GPS location in GeoJSON format.
 * See http://geojson.org
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class Location {

    // Instance variables:
    private String type = "Point";
    private Double[] coordinates = new Double[2];


    public Location() {

        this(0.0, 0.0);
    }

    public Location(double latitude, double longitude) {

        super();

        coordinates[0] = longitude;
        coordinates[1] = latitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }
}
