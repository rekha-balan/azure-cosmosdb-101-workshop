
# Class Config returns configuration values specified as environment variables.
# Chris Joakim, Microsoft, 2018/04/15

import os


class Config(object):

    def __init__(self):
        pass

    def bing_api_key(self):
        return os.environ['BING_API_KEY']

    def cosmosdb_uri(self):
        # example: https://cjoakimcosmosddb.documents.azure.com:443/
        return os.environ['AZURE_COSMOSDB_DOCDB_URI']

    def cosmosdb_key(self):
        return os.environ['AZURE_COSMOSDB_DOCDB_KEY']

    def zipcode_function_url(self):
        return os.environ['AZURE_FUNCTION_URL_ZIPCODE']
