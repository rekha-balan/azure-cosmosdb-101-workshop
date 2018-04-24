# [azure-cosmosdb-101-workshop](WORKSHOP.md)

# Client Terminal Interfaces

- **Mongo Shell**
- Windows PowerShell
- Azure Resource Manager
- **Azure CLI - Command Line Interface**

---

## Mongo Shell

### Environment Variables

Best Practice:

[The Twelve Factor App](https://12factor.net)

```
env | grep AZURE_COSMOSDB_MONGODB_CONN_STRING
```

### Connect

```
mongo $AZURE_COSMOSDB_MONGODB_CONN_STRING
```

### Demo

```
./cosmos_mongo_shell.sh
```

```
> show dbs
> use dev
> show collections
> db.stats()
> db.test.stats()
> db.test.insert({username: "cjoakim"})
> db.test.findOne()
> exit
```

---

## PowerShell

[Samples](https://docs.microsoft.com/en-us/azure/cosmos-db/powershell-samples)

---

## Azure Resource Manager

[Overview](https://docs.microsoft.com/en-us/azure/azure-resource-manager/resource-group-overview)

---

## The Azure CLI - Command Line Interface

- Platform neutral scripting interface to most of Azure
  - Linux, Windows, macOS
  - Integrate it into your own on-prem scripts

[Azure CLI 2.0](https://docs.microsoft.com/en-us/cli/azure/?view=azure-cli-latest)

```
$ az

     /\
    /  \    _____   _ _  ___ _
   / /\ \  |_  / | | | \'__/ _\
  / ____ \  / /| |_| | | |  __/
 /_/    \_\/___|\__,_|_|  \___|


Welcome to the cool new Azure CLI!

Here are the base commands:

    account          : Manage Azure subscription information.
    acr              : Manage Azure Container Registries.
    acs              : Manage Azure Container Services.
    ad               : Manage Azure Active Directory Graph entities needed for Role Based Access
                       Control.
    advisor          : Manage Azure Advisor.
    aks              : Manage Azure Kubernetes Services.
    appservice       : Manage App Service plans.
    backup           : Commands to manage Azure Backups.
    batch            : Manage Azure Batch.
    batchai          : Batch AI.
    billing          : Manage Azure Billing.
    cdn              : Manage Azure Content Delivery Networks (CDNs).
    cloud            : Manage registered Azure clouds.
    cognitiveservices: Manage Azure Cognitive Services accounts.
    configure        : Display and manage the Azure CLI 2.0 configuration. This command is
                       interactive.
    consumption      : Manage consumption of Azure resources.
    container        : Manage Azure Container Instances.
    cosmosdb         : Manage Azure Cosmos DB database accounts.
    disk             : Manage Azure Managed Disks.
    dla              : (PREVIEW) Manage Data Lake Analytics accounts, jobs, and catalogs.
    dls              : (PREVIEW) Manage Data Lake Store accounts and filesystems.
    eventgrid        : Manage Azure Event Grid topics and subscriptions.
    eventhubs        : Manage Azure Event Hubs namespaces, eventhubs, consumergroups and geo
                       recovery configurations - Alias.
    extension        : Manage and update CLI extensions.
    feature          : Manage resource provider features.
    feedback         : Loving or hating the CLI?  Let us know!
    find             : Find Azure CLI commands.
    functionapp      : Manage function apps.
    group            : Manage resource groups and template deployments.
    identity         : Managed Service Identities.
    image            : Manage custom virtual machine images.
    interactive      : Start interactive mode.
    iot              : (PREVIEW) Manage Internet of Things (IoT) assets.
    keyvault         : Safeguard and maintain control of keys, secrets, and certificates.
    lab              : Manage Azure DevTest Labs.
    lock             : Manage Azure locks.
    login            : Log in to Azure.
    logout           : Log out to remove access to Azure subscriptions.
    managedapp       : Manage template solutions provided and maintained by Independent Software
                       Vendors (ISVs).
    monitor          : Manage the Azure Monitor Service.
    mysql            : Manage Azure Database for MySQL servers.
    network          : Manage Azure Network resources.
    policy           : Manage resource policies.
    postgres         : Manage Azure Database for PostgreSQL servers.
    provider         : Manage resource providers.
    redis            : Access to a secure, dedicated Redis cache for your Azure applications.
    reservations     : Manage Azure Reservations.
    resource         : Manage Azure resources.
    role             : Manage user roles for access control with Azure Active Directory and service
                       principals.
    servicebus       : Manage Azure Service Bus namespaces, queues, topics, subscriptions, rules and
                       geo-disaster recovery configuration alias.
    sf               : Manage and administer Azure Service Fabric clusters.
    snapshot         : Manage point-in-time copies of managed disks, native blobs, or other
                       snapshots.
    sql              : Manage Azure SQL Databases and Data Warehouses.
    storage          : Manage Azure Cloud Storage resources.
    tag              : Manage resource tags.
    vm               : Provision Linux or Windows virtual machines.
    vmss             : Manage groupings of virtual machines in an Azure Virtual Machine Scale Set
                       (VMSS).
    webapp           : Manage web apps.
```

Help Screens:
```
az cosmosdb --help
az cosmosdb create --help
```

List Azure Cosmos DB database accounts:
```
az cosmosdb list
```
