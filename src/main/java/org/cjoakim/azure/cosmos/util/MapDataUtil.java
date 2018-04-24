package org.cjoakim.azure.cosmos.util;

import com.microsoft.azure.documentdb.Document;
import com.microsoft.azure.documentdb.FeedResponse;
import org.cjoakim.azure.cosmos.AppConfig;
import org.cjoakim.azure.cosmos.dao.DocumentDbDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Utility class used to invoke the DocumentDbDao for D3 Map Data,
 * while caching the results for concurrent access.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class MapDataUtil {

    // Constants:
    private static final long FIVE_SECONDS = 5000;
    private static final Logger logger = LoggerFactory.getLogger(MapDataUtil.class);

    // Class variables:
    private static long cacheTime = 0;
    private static ArrayList<Document> cacheDocuments = null;
    private static HashMap<String, String> stateNamesMap = initUsStatesMap();

    private static HashMap<String, String> initUsStatesMap() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("AL", "Alabama");
        map.put("AK", "Alaska");
        map.put("AZ", "Arizona");
        map.put("AR", "Arkansas");
        map.put("CA", "California");
        map.put("CO", "Colorado");
        map.put("CT", "Connecticut");
        map.put("DE", "Delaware");
        map.put("DC", "District of Columbia");
        map.put("FL", "Florida");
        map.put("GA", "Georgia");
        map.put("HI", "Hawaii");
        map.put("ID", "Idaho");
        map.put("IL", "Illinois");
        map.put("IN", "Indiana");
        map.put("IA", "Iowa");
        map.put("KS", "Kansas");
        map.put("KY", "Kentucky");
        map.put("LA", "Louisiana");
        map.put("ME", "Maine");
        map.put("MT", "Montana");
        map.put("NE", "Nebraska");
        map.put("NV", "Nevada");
        map.put("NH", "New Hampshire");
        map.put("NJ", "New Jersey");
        map.put("NM", "New Mexico");
        map.put("NY", "New York");
        map.put("NC", "North Carolina");
        map.put("ND", "North Dakota");
        map.put("OH", "Ohio");
        map.put("OK", "Oklahoma");
        map.put("OR", "Oregon");
        map.put("MD", "Maryland");
        map.put("MA", "Massachusetts");
        map.put("MI", "Michigan");
        map.put("MN", "Minnesota");
        map.put("MS", "Mississippi");
        map.put("MO", "Missouri");
        map.put("PA", "Pennsylvania");
        map.put("PR", "Puerto Rico");
        map.put("RI", "Rhode Island");
        map.put("SC", "South Carolina");
        map.put("SD", "South Dakota");
        map.put("TN", "Tennessee");
        map.put("TX", "Texas");
        map.put("UT", "Utah");
        map.put("VT", "Vermont");
        map.put("VA", "Virginia");
        map.put("WA", "Washington");
        map.put("WV", "West Virginia");
        map.put("WI", "Wisconsin");
        map.put("WY", "Wyoming");
        return map;
    }

    /**
     * Default constructor.  Do not use; use the static class-methods instead.
     */
    private MapDataUtil() {

        super();
    }

    public static synchronized ArrayList<String> getMapStatesCsv() {

        ArrayList<String> lines = new ArrayList<String>();
        lines.add("state,flag");

        HashMap<String, String> map = new HashMap<String, String>();

        ArrayList<Document> documents = getMapData();
        for (int i = 0; i < documents.size(); i++) {
            Document doc = documents.get(i);
            String stateAbbrv = doc.getString("stateAbbrv");
            String stateName  = stateNamesMap.get("" + stateAbbrv);
            if (stateName != null) {
                map.put(stateName, stateAbbrv);
            }
        }
        for (String key : map.keySet() ) {
            lines.add("" + key + ",1");
        }
        return lines;
    }

    public static synchronized ArrayList<String> getMapLatestCsv() {

        ArrayList<String> lines = new ArrayList<String>();
        lines.add("weight,place,lat,lon");

        ArrayList<Document> documents = getMapData();
        for (int i = 0; i < documents.size(); i++) {
            if (i < 2) {
                // add two lines like this: "4,NYC,40.71455,-74.007124"
                Document doc = documents.get(i);
                String weight = "2";
                if (i < 1) {
                    weight = "4";
                }
                String city  = doc.getString("cityName");
                String state = doc.getString("stateAbbrv");
                String lat   = "" + doc.getDouble("latitude");
                String lng   = "" + doc.getDouble("longitude");
                String line  = String.format("%s,%s %s,%s,%s", weight, city, state, lat, lng);
                lines.add(line);
            }
        }
        return lines;
    }

    public static String randomState() {

        ArrayList<String> keyList = new ArrayList(stateNamesMap.keySet());
        Random r = new Random();
        int index = r.nextInt(keyList.size());
        return keyList.get(index);
    }

    private static ArrayList<Document> getMapData() {

        if (expired(cacheTime)) {
            logger.warn("getMapData - refreshing cache");
            String dbName = AppConfig.getDocDbDefaultDbName();
            String collName = AppConfig.getDocDbDefaultCollName();
            logger.warn(String.format("getMapData: %s  db: %s  coll: %s", AppConfig.getDocDbAcct(), dbName, collName));

            DocumentDbDao dao = new DocumentDbDao();
            long epoch = 1;
            String sql = String.format("SELECT TOP 24 c.postalCode.cityName, c.postalCode.stateAbbrv, c.postalCode.postalCd, c.postalCode.latitude, c.postalCode.longitude, c.apiEpoch, c.apiType FROM c WHERE c.apiType = 'current' AND c.apiEpoch > %s ORDER BY c.apiEpoch DESC", epoch);
            FeedResponse<Document> documents = dao.queryAsDocuments(dbName, collName, sql);
            cacheTime = System.currentTimeMillis();

            ArrayList<Document> newCache = new ArrayList<Document>();

            for (Document doc : documents.getQueryIterable()) {
                newCache.add(doc);  // doc is an instance of com.microsoft.azure.documentdb.Document
            }
            cacheTime = System.currentTimeMillis();
            cacheDocuments = newCache;
        }
        else {
            logger.warn("getMapData - using cache");
        }
        return cacheDocuments;
    }

    private static boolean expired(long cacheTime) {

        if (cacheTime < (System.currentTimeMillis() - FIVE_SECONDS)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {

        try {
            MapDataUtil mdUtil = new MapDataUtil();

            logger.warn("MapDataUtil.getMapData()");
            ArrayList<Document> documents = MapDataUtil.getMapData();
            for (int i = 0; i < documents.size(); i++) {
                Document doc = documents.get(i);
                logger.warn("---");
                logger.warn("cityName:   " + doc.getString("cityName"));
                logger.warn("stateAbbrv: " + doc.getString("stateAbbrv"));
                logger.warn("latitude:   " + doc.getDouble("latitude"));
                logger.warn("longitude:  " + doc.getDouble("longitude"));
                logger.warn("apiType:    " + doc.getString("apiType"));
                logger.warn("apiEpoch:   " + doc.getLong("apiEpoch"));
            }

            logger.warn("MapDataUtil.getMapStatesCsv()");
            ArrayList<String> lines = MapDataUtil.getMapStatesCsv();
            for (int i = 0; i < lines.size(); i++) {
                logger.warn(lines.get(i));
            }

            logger.warn("MapDataUtil.getMapLatestCsv()");
            lines = MapDataUtil.getMapLatestCsv();
            for (int i = 0; i < lines.size(); i++) {
                logger.warn(lines.get(i));
            }

            for (int i = 0; i < 10; i++) {
                logger.warn(MapDataUtil.randomState());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.exit(0);
        }
    }

}
