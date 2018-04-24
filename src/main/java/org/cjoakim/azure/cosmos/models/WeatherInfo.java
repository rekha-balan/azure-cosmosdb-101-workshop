package org.cjoakim.azure.cosmos.models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

/**
 * POJO class representing aggregated data - a PostalCode at
 * a snapshot timetamp, and its weather API response data.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class WeatherInfo {

    // Instance variables:
    private String     id;
    private String     pk;
    private String     zip;
    private PostalCode postalCode;
    private String     apiDate;
    private long       apiEpoch;
    private String     apiType;
    private Object     apiResponse;

    public WeatherInfo() {

        super();
    }

    public WeatherInfo(PostalCode pc) {

        super();

        Date now = new Date();
        this.postalCode = pc;
        this.pk  = this.postalCode.getPostalCd();
        this.zip = this.postalCode.getPostalCd();
        this.id  = "" + this.zip + ":x";
        this.apiDate  = now.toGMTString();
        this.apiEpoch = now.getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }

    public String getApiDate() {
        return apiDate;
    }

    public long getApiEpoch() {
        return apiEpoch;
    }

    public void setApiDate(String apiDate) {
        this.apiDate = apiDate;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
        this.id = "" + this.zip + ":" + apiType;
    }

    public Object getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(Object obj) {
        this.apiResponse = obj;
    }

    public void setApiResponseBody(String apiRespBody) {

        //this.id = "" + this.zip + ":" + apiType;

        if (apiRespBody != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                JsonNode jsonNodeRoot = mapper.readTree(apiRespBody);
                this.apiResponse = jsonNodeRoot;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
