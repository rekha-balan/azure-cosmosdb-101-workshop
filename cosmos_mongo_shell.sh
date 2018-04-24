#!/bin/bash

# Use the mongo client program to open a shell window to CosmosDB w/MongoDB API.
# Chris Joakim, Microsoft, 2018/04/12

echo 'Azure CosmosDB/MongoDB...'

mongo $AZURE_COSMOSDB_MONGODB_CONN_STRING

# The value of $AZURE_COSMOSDB_MONGODB_CONN_STRING looks something like this:
# mongodb://cjoakimcosmosmongo:<key>@cjoakimcosmosmongo.documents.azure.com:10255/?ssl=true&replicaSet=globaldb

# Mongo shell example commands:
# > show dbs
# > use tutorial
# > show collections
# > db.stats()
# > db.test.stats()
# > db.test.insert({username: "cjoakim"})
# > db.test.findOne()
# > exit
