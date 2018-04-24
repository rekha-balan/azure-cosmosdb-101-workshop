# [azure-cosmosdb-101-workshop](WORKSHOP.md)

# Azure Functions

- **Serverless logic**
- **Event-Driven**
- **Multiple Programming Languages**
- The 1.x runtime is GA, 2.x is in preview
- C#, JavaScript/Node.js, F#, Java... Python, TypeScript, etc

[Overview](https://azure.microsoft.com/en-us/services/functions/)

[Programming Languages](https://docs.microsoft.com/en-us/azure/azure-functions/supported-languages)

Create a Function in Azure Portal - Service Bus geo-ml

# CosmosDB with Azure Functions

See the code in this GitHub reporitory in the **functions** directory.

- What we'll do:
  - Delete and Recreate the **zipcodes** and **xville** collections in Azure Portal
  - Use a HTTP client to HTTP POST to an Azure Function which persists to the CosmosDB **zipcodes** collection
  - Another Azure Function will be triggered by the CosmosDB **upserts** to **zipcodes**
  - Cities with "ville" (i.e. - Mooresville, Huntersville, etc) will be added to the **xville** collection


## See the code in the functions/ directory


## See the code in the Azure Portal Functions App


## Steps to Execute

```

cd python

# create python 3 virtualenv per requirements.in
./venv.sh

# activate the virtualenv
source bin/activate

# see the environment variable with the Azure Function URL
env | grep AZURE_FUNCTION
AZURE_FUNCTION_URL_ZIPCODE=https://cjoakim-cosmosdb-101-workshop-adhoc.azurewebsites.net/api/ZipCodeHttpTriggerJS1?code=5I9uooaRDSld0UwLdiV7g26jbE57ZT0xLOGtZXMj3rJIa8rdxgmTZQ==

# Use the Python 'requests' lib to HTTP POST 10 documents to the first Azure Function
python main.py post_zipcodes_to_function 10

```