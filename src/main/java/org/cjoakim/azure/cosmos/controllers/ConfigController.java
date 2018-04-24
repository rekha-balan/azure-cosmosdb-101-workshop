package org.cjoakim.azure.cosmos.controllers;

import org.cjoakim.azure.cosmos.util.KVPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * This is a Spring Boot HTTP Controller class for the /config path.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

@Controller
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    // inject via application.properties
    // @Value("${welcome.message:test}")
    private String message = "Config";

    @RequestMapping("/config")
    public String config(Map<String, Object> model) {

        logger.warn("ConfigController#config");

        ArrayList<KVPair> list = new ArrayList<KVPair>();

        Map<String, String> env = System.getenv();
//        for (String envName : env.keySet()) {
//            list.add(new KVPair(envName, env.get(envName)));
//            //logger.warn(String.format("%s=%s%n", envName, env.get(envName)));
//        }

        for (String envName : pertinentEnvironmentVariableNames()) {
            list.add(new KVPair(envName, env.get(envName)));
        }

        Collections.sort(list);
        model.put("message", this.message);
        model.put("kvpairs", list);

        return "config"; // return the name of the template to render
    }

    private ArrayList<String> pertinentEnvironmentVariableNames() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("AZURE_COSMOSDB_DOCDB_ACCT");
        list.add("AZURE_COSMOSDB_DOCDB_KEY");
        list.add("AZURE_COSMOSDB_DOCDB_DBNAME");
        list.add("AZURE_COSMOSDB_DOCDB_COLLNAME");
        list.add("AZURE_OPENWEATHERMAP_KEY");
        list.add("JAVA_HOME");
        list.add("HOME");
        list.add("USER");

        return list;
    }

}

//    public static String getDocDbAcct() {
//
//        return System.getenv("AZURE_COSMOSDB_DOCDB_ACCT");
//    }
//
//    public static String getDocDbKey() {
//
//        return System.getenv("AZURE_COSMOSDB_DOCDB_KEY");
//    }
//
//    public static String getDocDbDefaultDbName() {
//
//        return System.getenv("AZURE_COSMOSDB_DOCDB_DBNAME");
//    }
//
//    public static String getDocDbDefaultCollName() {
//
//        return System.getenv("AZURE_COSMOSDB_DOCDB_COLLNAME");
//    }
//
//    public static String getOpenWeatherMapKey() {
//
//        return System.getenv("AZURE_OPENWEATHERMAP_KEY");