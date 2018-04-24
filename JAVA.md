# [azure-cosmosdb-101-workshop](WORKSHOP.md)

# CosmosDB with Java

- What we'll do:
  - Use the **weather** collection in CosmosDB
  - Run the **Weather Daemon** Java program on the DSVM
  - Daemon program gets weather reports from **api.openweathermap.org** and persists to **CosmosDB**
  - Run the **Spring Boot** Java web app locally
  - Web app uses **Bootstrap** and **D3.js**, and reads **CosmosDB**

  - Visit web app with a browser:
    - See the environment variable configuration
    - Insert, then Update, some random US ZipCode documents
    - Query the CosmosDB collection
    - See the **latest weather reports** with D3.js map of the US

# DSVM

```
./build.sh

./classpath.sh

cat weather_daemon.sh

./weather_daemon.sh 40 5000
```

# Localhost

```
./web.sh
```

# Browser

```
http://localhost:8080/
```

Note: could have deployed this to an Azure Web App, or Docker Container Instance, or Kuberentes cluster.

### Example Document

```
    {
        "zip": "28786",
        "apiEpoch": 1523183884377,
        "postalCode": {
            "valid": true,
            "cityName": "Waynesville",
            "stateAbbrv": "NC",
            "latitude": 35.45009,
            "postalCd": "28786",
            "countryCd": "US",
            "location": {
                "coordinates": [
                    -82.978773,
                    35.45009
                ],
                "type": "Point"
            },
            "id": "12003",
            "longitude": -82.978773
        },
        "apiResponse": {
            "dt": 1523182500,
            "coord": {
                "lon": -82.98,
                "lat": 35.45
            },
            "visibility": 16093,
            "weather": [
                {
                    "icon": "04n",
                    "description": "overcast clouds",
                    "main": "Clouds",
                    "id": 804
                }
            ],
            "name": "Hazelwood",
            "cod": 200,
            "main": {
                "temp": 31.87,
                "temp_min": 30.2,
                "humidity": 80,
                "pressure": 1013,
                "temp_max": 33.8
            },
            "clouds": {
                "all": 90
            },
            "id": 4470416,
            "sys": {
                "country": "US",
                "sunrise": 1523185690,
                "sunset": 1523231975,
                "id": 1800,
                "type": 1,
                "message": 0.246
            },
            "base": "stations",
            "wind": {
                "deg": 347.009,
                "speed": 4.16
            }
        },
        "id": "28786:current",
        "pk": "28786",
        "apiDate": "8 Apr 2018 10:38:04 GMT",
        "apiType": "current",
        "_rid": "jypUAPIvVQCFAAAAAAAAAA==",
        "_self": "dbs/jypUAA==/colls/jypUAPIvVQA=/docs/jypUAPIvVQCFAAAAAAAAAA==/",
        "_etag": "\"00007a15-0000-0000-0000-5ac9f10c0000\"",
        "_attachments": "attachments/",
        "_ts": 1523183884
    }

```

```
{
  "pk": "28036",
  "type": "thing",
  "version": "0.0.1",
  "private": "yes",
  "description": "Some sample document"
}
```

### Example Queries

Notice the access of **nested** attributes like **c.postalCode.location**

**ST_DISTANCE** is a Spatial SQL built-in function

[Geo Spatial](https://docs.microsoft.com/en-us/azure/cosmos-db/geospatial)

Lowes campus: 35.540790, -80.853326  (Bing it)

```
SELECT COUNT(1) FROM c

SELECT TOP 10 c.postalCode.cityName from c

SELECT * from c where c.postalCode.stateAbbrv = "NC"

SELECT * from c where c.pk = 'place' order by c.epoch

SELECT * FROM c WHERE ST_DISTANCE(c.postalCode.location, {'type': 'Point', 'coordinates': [-80.853326, 35.540790] }) < 1000

SELECT * FROM c WHERE ST_DISTANCE(
        c.postalCode.location,
        {'type': 'Point', 'coordinates': [-82.978773, 35.45009] })
        <= 1000

SELECT c.zip, c.apiEpoch, c.postalCode FROM c WHERE ST_DISTANCE(
        c.postalCode.location,
        {'type': 'Point', 'coordinates': [-82.978773, 35.45009] })
        <= 1000

SELECT * FROM c WHERE c.pk = 'place' AND ST_DISTANCE(
        c.postalCode.location,
        {'type': 'Point', 'coordinates': [-73.9757, 40.7545] })
        <= 1000
```
# Code

### pom.xml

Maven coordinates:
```
...
    <dependency>
        <groupId>com.microsoft.azure</groupId>
        <artifactId>azure-documentdb</artifactId>
        <version>1.16.0</version>
    </dependency>
...

```

[Maven Central](http://search.maven.org/#artifactdetails%7Ccom.microsoft.azure%7Cazure-documentdb%7C1.16.0%7Cjar)


### org.cjoakim.azure.cosmos.dao.WeatherDao

invokes http://api.openweathermap.org/data/2.5

### org.cjoakim.azure.cosmos.dao.DocumentDbDao

CosmosDB CRUD operations

```
import com.microsoft.azure.documentdb.*;
...

    public DocumentDbDao() {

        try {
            this.client = new DocumentClient(
                    serviceEndpoint(),
                    AppConfig.getDocDbKey(),
                    new ConnectionPolicy(),
                    ConsistencyLevel.Session);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Document insertDocument(String dbName, String collName, Object obj) throws DocumentClientException {

        String collLink = this.collLink(dbName, collName);
        return this.client.createDocument(collLink, obj, new RequestOptions(), false).getResource();
    }

...
```

## Links

- [DocumentDB Java SDK](https://github.com/Azure/azure-documentdb-java)
- [Java in Azure](https://azure.microsoft.com/en-us/develop/java/)
