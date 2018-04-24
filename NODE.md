# [azure-cosmosdb-101-workshop](WORKSHOP.md)

# CosmosDB with Node.js

See the code in this GitHub reporitory in the **nodejs** directory.

```
cd nodejs/
npm install

node cosmos_main.js get_database_account
node cosmos_main.js list_databases
node cosmos_main.js create_collection dev test22
node cosmos_main.js delete_collection dev test22
node cosmos_main.js create_collection dev test23
node cosmos_main.js list_collections dev
node cosmos_main.js create_document dev test23
node cosmos_main.js delete_document dev test23 e7b48ee4-0100-2e15-8eb8-fa80b1ee2f5e
node cosmos_main.js query_documents dev test23 cjoakim 1523567974845
```

See the CosmosDB in Azure Portal

## Pertinent Code

- [cosmos_main.js](https://github.com/cjoakim/azure-cosmosdb-101-workshop/blob/master/nodejs/cosmos_main.js)
- [cosmos_docdb_util.py](https://github.com/cjoakim/azure-cosmosdb-101-workshop/blob/master/nodejs/lib/cosmos_docdb_util.js)

## npm libraries

[azure at npm](https://www.npmjs.com/package/azure)

[documentdb at npm](https://www.npmjs.com/package/documentdb)

[azu-js at npm](https://www.npmjs.com/package/azu-js)
