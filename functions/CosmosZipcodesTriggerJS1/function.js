{
  "bindings": [
    {
      "type": "cosmosDBTrigger",
      "name": "documents",
      "direction": "in",
      "leaseCollectionName": "leases",
      "connectionStringSetting": "cjoakimcosmosddb_DOCUMENTDB",
      "databaseName": "dev",
      "collectionName": "zipcodes",
      "createLeaseCollectionIfNotExists": true
    },
    {
      "type": "documentDB",
      "name": "outputDocument",
      "databaseName": "dev",
      "collectionName": "xville",
      "createIfNotExists": false,
      "connection": "cjoakimcosmosddb_DOCUMENTDB",
      "direction": "out",
      "partitionKey": "/partition_key"
    }
  ],
  "disabled": false
}
