package org.cjoakim.azure.cosmos;

/**
 * This class returns configuration values specified as environment variables.
 *
 * See https://12factor.net
 * See https://12factor.net/config
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class AppConfig {

    public static String getDocDbAcct() {

        return System.getenv("AZURE_COSMOSDB_DOCDB_ACCT");
    }

    public static String getDocDbKey() {

        return System.getenv("AZURE_COSMOSDB_DOCDB_KEY");
    }

    public static String getDocDbDefaultDbName() {

        return System.getenv("AZURE_COSMOSDB_DOCDB_DBNAME");
    }

    public static String getDocDbDefaultCollName() {

        return System.getenv("AZURE_COSMOSDB_DOCDB_COLLNAME");
    }

    public static String getOpenWeatherMapKey() {

        return System.getenv("AZURE_OPENWEATHERMAP_KEY");
    }

}
