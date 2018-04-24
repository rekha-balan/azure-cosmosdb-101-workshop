# [azure-cosmosdb-101-workshop](WORKSHOP.md)

# CosmosDB Overview

## [Welcome to Azure Cosmos DB](https://docs.microsoft.com/en-us/azure/cosmos-db/introduction)

## Multi-Modal

- **SQL/DocumentDB** - enhanced SQL syntax
- **MongoDB** - use mongo libraries and tools
- **Graph** - Apache TinkerPop & Gremlin
- **Cassandra** - Apache "Cassandra-as-a-Service"
- **Table**

This workshop/repo initially focuses on SQL/DocumentDB and Graph.

## Features

- Massively Scalable
- High Throughput
- Request Units
- Global Replication
- Service Level Agreement - 99.99% Availability
- Service Level Agreement - 10ms & 15ms Performance
- Automated and Complete Indexing
- Tunable Consistency Levels
- Extremely easy to use
- In-browser queries
- In-Database code - Stored Procedures, UDFs, Triggers
- Spatial Queries with GPS Coordinates

## Deeper Dive - Features

### Request Units

- [Request Units](https://docs.microsoft.com/en-us/azure/cosmos-db/request-units)
- [Request Unit Calculator](https://www.documentdb.com/capacityplanner)

### Tunable Consistency Levels

- Five Levels:
  - **Strong**  - Reads are guaranteed to return the most recent version of an item
  - Bounded Staleness
  - **Session Consistent Prefix** - read-your-writes
  - Consistent Prefix
  - **Eventual**

- [Consistency Levels](https://docs.microsoft.com/en-us/azure/cosmos-db/consistency-levels)

### Partitions and Keys

- [Partition Keys](https://docs.microsoft.com/en-us/azure/cosmos-db/partition-data)

### Security

- Encryption in Flight
- [Encryption at Rest](https://docs.microsoft.com/en-us/azure/cosmos-db/database-encryption-at-rest)

## Demo - Provisioning in Azure Portal

## Programming SDKs - SQL/DocumentDB

### C#

### Java

Maven coordinates:
```
<dependency>
    <groupId>com.microsoft.azure</groupId>
    <artifactId>azure-documentdb</artifactId>
    <version>1.16.0</version>
</dependency>
```

### Python

```
pip install pydocumentdb
```

### Node.js

```
npm install azure
npm install documentdb
```

## Links

- [Documentation](https://azure.microsoft.com/en-us/services/cosmos-db/)
- [Documentation](https://docs.microsoft.com/en-us/azure/cosmos-db/)
- [Cheat Sheet](https://docs.microsoft.com/en-us/azure/cosmos-db/query-cheat-sheet)
