package org.cjoakim.azure.cosmos.util;

import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.cjoakim.azure.cosmos.dao.PostalCodeDao;

/**
 * Simple class used to generate sample documents for the InsertController.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class SampleDocGenerator {

    public SampleDocGenerator() {

        super();
    }

    public String newDoc() {
        try {
            Date now = new Date();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("pk", "place");
            map.put("date", now.toString());
            map.put("epoch", now.getTime());
            map.put("postalCode", PostalCodeDao.random());
            map.put("note", "");

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public static void main(String[] args) {

        SampleDocGenerator gen = new SampleDocGenerator();
        System.out.println(gen.newDoc());
    }
}
