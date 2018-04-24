'use strict';

const events = require('events');
const util   = require('util');

const DocumentDBClient = require('documentdb').DocumentClient;
const DocumentBase = require('documentdb').DocumentBase;

// This utility class contains functions for invoking Azure CosmosDB/DocumentDB.
// Chris Joakim, 2018/04/12

class CosmosDocDbUtil extends events.EventEmitter {

    constructor() {
        super();
        this.dbname = process.env.AZURE_COSMOSDB_DOCDB_DBNAME;
        var uri     = process.env.AZURE_COSMOSDB_DOCDB_URI;
        var key     = process.env.AZURE_COSMOSDB_DOCDB_KEY;
        this.client = new DocumentDBClient(uri, { masterKey: key });
    }

    db_link(dbname) {
        return 'dbs/' + dbname;
    }

    coll_link(dbname, cname) {
        return this.db_link(dbname) + '/colls/' + cname;
    }

    get_database_account() {
        var start_epoch = (new Date).getTime();
        this.client.getDatabaseAccount((err, db_acct, headers) => {
            var finish_epoch = (new Date).getTime();
            var evt_obj = {};
            evt_obj['type']    = 'CosmosDocDbUtil:get_database_account';
            evt_obj['err']     = err;
            evt_obj['db_acct'] = db_acct;
            evt_obj['headers'] = headers;
            evt_obj['start_epoch']  = start_epoch;
            evt_obj['finish_epoch'] = finish_epoch;
            evt_obj['elapsed_time'] = finish_epoch - start_epoch;
            this.emit('get_database_account', evt_obj);
        });
    }

    list_databases() {
        var start_epoch = (new Date).getTime();
        this.client.readDatabases().toArray((err, dbs) => {
            var finish_epoch = (new Date).getTime();
            var evt_obj = {};
            evt_obj['type'] = 'CosmosDocDbUtil:list_databases';
            evt_obj['err']  = err;
            evt_obj['dbs']  = dbs;
            evt_obj['start_epoch']  = start_epoch;
            evt_obj['finish_epoch'] = finish_epoch;
            evt_obj['elapsed_time'] = finish_epoch - start_epoch;
            this.emit('list_databases', evt_obj);
        });
    }

    create_collection(dbname, cname) {
        var dblink = 'dbs/' + dbname;
        var collspec = { id: cname };
        var start_epoch = (new Date).getTime();
        this.client.createCollection(dblink, collspec, (err, created) => {
            var finish_epoch = (new Date).getTime();
            var evt_obj = {};
            evt_obj['type']    = 'CosmosDocDbUtil:create_collection';
            evt_obj['dbname']  = dbname;
            evt_obj['cname']   = cname;
            evt_obj['created'] = created;
            evt_obj['error']   = err;
            evt_obj['start_epoch']  = start_epoch;
            evt_obj['finish_epoch'] = finish_epoch;
            evt_obj['elapsed_time'] = finish_epoch - start_epoch;
            this.emit('create_collection', evt_obj);
        });
    }

    delete_collection(dbname, cname) {
        var colllink = 'dbs/' + dbname + '/colls/' + cname;
        var start_epoch = (new Date).getTime();
        this.client.deleteCollection(colllink, (err) => {
            var finish_epoch = (new Date).getTime();
            var evt_obj = {};
            evt_obj['type']    = 'CosmosDocDbUtil:delete_collection';
            evt_obj['dbname']  = dbname;
            evt_obj['cname']   = cname;
            evt_obj['error']   = err;
            evt_obj['start_epoch']  = start_epoch;
            evt_obj['finish_epoch'] = finish_epoch;
            evt_obj['elapsed_time'] = finish_epoch - start_epoch;
            this.emit('delete_collection', evt_obj);
        });
    }

    list_collections(dbname) {
        var dblink = 'dbs/' + dbname;
        var start_epoch = (new Date).getTime();
        this.client.readCollections(dblink).toArray((err, collections) => {
            var finish_epoch = (new Date).getTime();
            var evt_obj = {};
            evt_obj['type'] = 'CosmosDocDbUtil:list_collections';
            evt_obj['err']  = err;
            evt_obj['dbname'] = dbname;
            evt_obj['collections']  = collections;
            evt_obj['start_epoch']  = start_epoch;
            evt_obj['finish_epoch'] = finish_epoch;
            evt_obj['elapsed_time'] = finish_epoch - start_epoch;
            this.emit('list_collections', evt_obj);
        });
    }

    create_document(dbname, cname, doc) {
        var colllink = 'dbs/' + dbname + '/colls/' + cname;
        var start_epoch = (new Date).getTime();
        this.client.createDocument(colllink, doc, (err, new_doc) => {
            var finish_epoch = (new Date).getTime();
            var evt_obj = {};
            evt_obj['type']   = 'CosmosDocDbUtil:create_document';
            evt_obj['dbname'] = dbname;
            evt_obj['cname']  = cname;
            evt_obj['doc']    = new_doc;
            evt_obj['error']  = err;
            evt_obj['start_epoch'] = start_epoch;
            evt_obj['finish_epoch'] = finish_epoch;
            evt_obj['elapsed_time'] = finish_epoch - start_epoch;
            this.emit('create_document', evt_obj);
        });
    }

    query_documents(dbname, cname, query_spec) {
        var start_epoch = (new Date).getTime();
        var link = this.coll_link(dbname, cname);
        this.client.queryDocuments(link, query_spec).toArray((err, results) => {
            var finish_epoch = (new Date).getTime();
            var evt_obj = {};
            evt_obj['type'] = 'CosmosDocDbUtil:query_documents';
            evt_obj['coll_link'] = link;
            evt_obj['query_spec'] = query_spec;
            evt_obj['err']     = err;
            evt_obj['results'] = results;
            evt_obj['start_epoch'] = start_epoch;
            evt_obj['finish_epoch'] = finish_epoch;
            evt_obj['elapsed_time'] = finish_epoch - start_epoch;
            this.emit('query_documents', evt_obj);
        });
    }

    delete_document(dbname, cname, doc_id, options) {
        var doclink = 'dbs/' + dbname + '/colls/' + cname + '/docs/' + doc_id;
        var start_epoch = (new Date).getTime();
        this.client.deleteDocument(doclink, options, (err) => {
            var finish_epoch = (new Date).getTime();
            var evt_obj = {};
            evt_obj['type']    = 'CosmosDocDbUtil:delete_document';
            evt_obj['dbname']  = dbname;
            evt_obj['cname']   = cname;
            evt_obj['doc_id']  = doc_id;
            evt_obj['doclink'] = doclink;
            evt_obj['error']   = err;
            evt_obj['start_epoch'] = start_epoch;
            evt_obj['finish_epoch'] = finish_epoch;
            evt_obj['elapsed_time'] = finish_epoch - start_epoch;
            this.emit('delete_document', evt_obj);
        });
    }

}

module.exports.CosmosDocDbUtil = CosmosDocDbUtil;
