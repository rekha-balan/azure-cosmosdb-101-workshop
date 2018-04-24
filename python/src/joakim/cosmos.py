# Chris Joakim, Microsoft, 2018/04/07

import os
import sys

from src.joakim import config

import pydocumentdb.document_client as document_client

# pydocumentdb.errors.HTTPFailure: Status code: 400
# {"code":"BadRequest","message":"Cross partition query is required but disabled. Please set x-ms-documentdb-query-enablecrosspartition to true, specify x-ms-documentdb-partitionkey, or revise your query to avoid this exception.\r\nActivityId: b262caa9-d545-4867-bf9f-e208a94e28de, Microsoft.Azure.Documents.Common/1.21.0.0"}
# pydocumentdb Cross partition query is required but disabled x-ms-documentdb-query-enablecrosspartition


class DocDbUtil(object):

    def __init__(self, enable_cross_partition=False):
        c = config.Config()
        host = c.cosmosdb_uri()
        key  = c.cosmosdb_key()
        self.client = document_client.DocumentClient(host, {'masterKey': key})

        if enable_cross_partition:
            self.client.default_headers['x-ms-documentdb-query-enablecrosspartition'] = True

    def collection_link(self, dbname, cname):
        return 'dbs/' + dbname + '/colls/' + cname;

    def list_databases(self):
        databases = list(self.client.ReadDatabases())
        if not databases:
            print('no databases')
        else:
            for db in databases:
                id = db['id']
                print('database: {}'.format(id))
        return databases

    def insert_document(self, dbname, cname, doc):
        link = self.collection_link(dbname, cname)
        try:
            return self.client.UpsertDocument(link, doc)
        except:
            print("Unexpected error:{}".format(sys.exc_info()[0]))
            raise

    def execute_query(self, dbname, cname, query_spec, enable_cross_partition=False):
        self.set_cross_partition_header(enable_cross_partition)
        link = self.collection_link(dbname, cname)
        print('execute_query; link: {} query_spec: {}'.format(link, query_spec))
        try:
            return list(self.client.QueryDocuments(link, query_spec))
        except:
            print("Unexpected error:{}".format(sys.exc_info()[0]))
            raise

    def set_cross_partition_header(self, boolean):
        header = self.cross_partition_header()
        self.client.default_headers[header] = boolean

    def cross_partition_header(self):
        return 'x-ms-documentdb-query-enablecrosspartition'
