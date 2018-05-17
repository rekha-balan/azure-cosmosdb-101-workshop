# [azure-cosmosdb-101-workshop](WORKSHOP.md)

# CosmosDB with Python

## Demo on a Data Science Virtual Machine (DSVM)

[DSVMs](https://azure.microsoft.com/en-us/services/virtual-machines/data-science-virtual-machines/)

[Anaconda](https://www.anaconda.com/what-is-anaconda/)

- What we'll do:
  - Delete and Recreate the **perf** collection in Azure Portal
  - Load the Database with 430+ Connecticut zip codes
  - Query each document
  - Generate Query Performance Stats

[GitHub Repo](https://github.com/cjoakim/azure-cosmosdb-perf)

## Pertinent Code

[main.py](https://github.com/cjoakim/azure-cosmosdb-perf/blob/master/main.py)
[cosmos.py](https://github.com/cjoakim/azure-cosmosdb-perf/blob/master/src/joakim/cosmos.py)

## Create the perf collection

- dev database
- partition key "/partition_key"
- 10000 RUs

## Load the Database Collection

Create and activate the Anaconda virtual environment:
```
./conda_env.sh
source activate perf
```

```
python main.py load_db 0.1 > tmp/load_db.txt
```

## Query by Id

```
python main.py query_db by_id 62e78915-fbf8-4882-8163-d67442aab66e
```

## Query Specs - SQL value substitution

```
{
  'query': 'SELECT * FROM c WHERE c.id = @id',
  'parameters': [{'value': '62e78915-fbf8-4882-8163-d67442aab66e', 'name': '@id'}]
}
```

## Performance Testing

```
python main.py query_db all_by_postal_cd data/clients/queries.json
python main.py perf_report data/clients/queries.json
```

## Spatial Queries

[GeoJSON](http://geojson.org) format

The loaded documents look like this:
```
  ...
  {
    "location": {
      "type": "Point",
      "coordinates": [
        -73.34415,
        41.137996
      ]
    },
    "postal_cd": "06880",
    "country_cd": "US",
    "city_name": "Westport",
    "state_abbrv": "CT",
    "latitude": 41.137996,
    "longitude": -73.34415
  },
  ...
```

Query:
```
SELECT * FROM root WHERE ST_DISTANCE(root.location, {'type': 'Point', 'coordinates': [-73.34415, 41.137996] }) < 1000
```
