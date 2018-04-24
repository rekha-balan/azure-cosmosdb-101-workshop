package org.cjoakim.azure.cosmos.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is a POJO class representing postal codes loaded from the
 * postal_codes_us.csv file on the classpath.
 *
 * Example:
 * id,postal_cd,country_cd,city_name,state_abbrv,latitude,longitude
 * 11455,28036,US,Davidson,NC,35.4833060000,-80.7978540000
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class PostalCode {

    // Instance variables:
    private String   id;
    private String   postalCd;
    private String   countryCd;
    private String   cityName;
    private String   stateAbbrv;
    private double   latitude  = Double.MAX_VALUE * -1;
    private double   longitude = Double.MAX_VALUE * -1;
    private Location location  = null; // GeoJSON


    public PostalCode() {

        this("");
    }

    public PostalCode(String csvLine) {
        super();

        if (csvLine != null) {
            try {
                String[] tokens = csvLine.split(",");
                if (tokens.length == 7) {
                    this.id         = tokens[0].trim();
                    this.postalCd   = tokens[1].trim();
                    this.countryCd  = tokens[2].trim();
                    this.cityName   = tokens[3].trim();
                    this.stateAbbrv = tokens[4].trim();
                    this.latitude   = Double.parseDouble(tokens[5].trim());
                    this.longitude  = Double.parseDouble(tokens[6].trim());
                    this.location   = new Location(this.latitude, this.longitude);
                }
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isValid() {

        if (this.postalCd == null) {
            return false;
        }
        if (this.cityName == null) {
            return false;
        }
        if (Math.abs(this.latitude) > 90) {
            return false;
        }
        if (Math.abs(this.longitude) > 180) {
            return false;
        }
        return true;
    }

    public String asJson() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public String getId() {
        return id;
    }

    public String getPostalCd() {
        return postalCd;
    }

    public String getCountryCd() {
        return countryCd;
    }

    public String getCityName() {
        return cityName;
    }

    public String getStateAbbrv() {
        return stateAbbrv;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
