package org.cjoakim.azure.cosmos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.documentdb.Document;
import org.cjoakim.azure.cosmos.dao.DocumentDbDao;
import org.cjoakim.azure.cosmos.dao.PostalCodeDao;
import org.cjoakim.azure.cosmos.dao.WeatherDao;
import org.cjoakim.azure.cosmos.models.PostalCode;
import org.cjoakim.azure.cosmos.models.WeatherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Instances of this class are used to invoke the 3rd party Weather Service via HTTP
 * and class WeatherDao, and persist the responses to CosmosDB via class DocumentDbDao.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class WeatherDaemon {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDaemon.class);

    // org.cjoakim.azure.cosmos.WeatherDaemon 1 5000

    public static void main(String[] args) throws Exception {

        boolean continueToProcess = true;
        int  loopCount   = 0;
        int  maxCount    = 0;
        long loopSleepMs = 10000;
        long pauseMs     = 2000;
        String dbName    = AppConfig.getDocDbDefaultDbName();
        String collName  = AppConfig.getDocDbDefaultCollName();
        DocumentDbDao docDbDao = new DocumentDbDao();

        if (args.length > 1) {
            maxCount = Integer.parseInt(args[0]);
            loopSleepMs = Long.parseLong(args[1]);
            logger.warn(String.format("maxCount: %s", maxCount));
            logger.warn(String.format("sleepMs:  %s", loopSleepMs));
            PostalCodeDao.init();
        }
        else {
            continueToProcess = false;
            logger.error("Invalid program args; maxCount and sleepMs required");
        }

        while (continueToProcess) {
            loopCount++;
            Thread.sleep(loopSleepMs);

            PostalCode pc = PostalCodeDao.random();
            ObjectMapper mapper = new ObjectMapper();

            // Get the current weather
            WeatherInfo winfo = new WeatherInfo(pc);
            winfo.setApiType("current");
            winfo.setApiResponseBody(WeatherDao.getWeatherForLocation(pc.getLatitude(), pc.getLongitude()));
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(winfo);
            logger.warn(String.format("json:  %s", json));
            Document doc = docDbDao.upsertDocument(dbName, collName, winfo);
            logger.warn(String.format("doc:  %s", doc));

            Thread.sleep(pauseMs);  // be nice to the server and pause between requests

            // Get the forecast
            winfo = new WeatherInfo(pc);
            winfo.setApiType("forecast");
            winfo.setApiResponseBody(WeatherDao.getForecastForLocation(pc.getLatitude(), pc.getLongitude()));
            doc = docDbDao.upsertDocument(dbName, collName, winfo);
            logger.warn(String.format("doc:  %s", doc));

            if (loopCount >= maxCount) {
                continueToProcess = false;
                System.exit(0);
            }
        }
    }
}
