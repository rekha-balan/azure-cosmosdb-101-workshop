'use strict';

// Chris Joakim, 2018/04/12

const fs = require('fs');
const os = require('os');
const CosmosDocDbUtil = require('./lib/cosmos_docdb_util').CosmosDocDbUtil;

class Main {

    constructor() {
        this.request_urls = [];
        this.current_request_idx = -1;
        this.responses = [];
    }

    execute() {
        if (process.argv.length < 2) {
            console.log('error: too few command-line args provided.');
            process.exit();
        }
        else {
            var funct = process.argv[2];

            switch (funct) {
                case 'get_database_account':
                    this.get_database_account();
                    break;
                case 'list_databases':
                    this.list_databases();
                    break;
                case 'create_collection':
                    this.create_collection();
                    break;
                case 'delete_collection':
                    this.delete_collection();
                    break;
                case 'list_collections':
                    this.list_collections();
                    break;
                case 'create_document':
                    this.create_document();
                    break;
                case 'delete_document':
                    this.delete_document();
                    break;
                case 'query_documents':
                    this.query_documents();
                    break;
                default:
                    console.log('error: unknown function - ' + funct);
            }
        }
    }

    get_database_account() {
        console.log('get_database_account...');
        var util = new CosmosDocDbUtil();
        util.on('get_database_account', (evt_obj) => {
            console.log(JSON.stringify(evt_obj, null, 2));
        });
        util.get_database_account();
    }

    list_databases() {
        console.log('list_databases...');
        var util = new CosmosDocDbUtil();
        util.on('list_databases', (evt_obj) => {
            console.log(JSON.stringify(evt_obj, null, 2));
        });
        util.list_databases();
    }

    create_collection() {
        console.log('create_collection...');
        var dbname = process.argv[3];
        var cname  = process.argv[4];
        var util = new CosmosDocDbUtil();
        util.on('create_collection', (evt_obj) => {
            console.log(JSON.stringify(evt_obj, null, 2));
        });
        util.create_collection(dbname, cname);
    }

    delete_collection() {
        console.log('delete_collection...');
        var dbname = process.argv[3];
        var cname  = process.argv[4];
        var util = new CosmosDocDbUtil();
        util.on('delete_collection', (evt_obj) => {
            console.log(JSON.stringify(evt_obj, null, 2));
        });
        util.delete_collection(dbname, cname);
    }

    list_collections() {
        console.log('list_collections...');
        var dbname = process.argv[3];
        var util = new CosmosDocDbUtil();
        util.on('list_collections', (evt_obj) => {
            console.log(JSON.stringify(evt_obj, null, 2));
        });
        util.list_collections(dbname);
    }

    create_document() {
        console.log('create_document...');
        var dbname = process.argv[3];
        var cname  = process.argv[4];
        var doc = {};
        var now = new Date();
        doc['pk'] = 'test';
        doc['date'] = now;
        doc['epoch'] = now.getTime();
        doc['user'] = process.env.USER;
        doc['java_home'] = process.env.JAVA_HOME;
        console.log(doc);

        var util = new CosmosDocDbUtil();
        util.on('create_document', (evt_obj) => {
            console.log(JSON.stringify(evt_obj, null, 2));
        });
        util.create_document(dbname, cname, doc);
    }

    delete_document() {
        console.log('delete_document...');
        var dbname = process.argv[3];
        var cname  = process.argv[4];
        var doc_id = process.argv[5];
        var opts   = {};

        var util = new CosmosDocDbUtil();
        util.on('delete_document', (evt_obj) => {
            console.log(JSON.stringify(evt_obj, null, 2));
        });
        util.delete_document(dbname, cname, doc_id, opts);
    }

    query_documents() {
        console.log('query_documents...');
        var dbname = process.argv[3];
        var cname  = process.argv[4];
        var user   = process.argv[5];
        var epoch  = Number(process.argv[6]);
        var query_spec = {};
        query_spec['query'] = "SELECT * FROM c WHERE c.user = @user AND c.epoch = @epoch";
        query_spec['parameters'] = [ {name: '@user', value: user}, {name: '@epoch', value: epoch}];
        console.log(JSON.stringify(query_spec, null, 2));

        var util = new CosmosDocDbUtil();
        util.on('query_documents', (evt_obj) => {
            console.log(JSON.stringify(evt_obj, null, 2));
        });
        util.query_documents(dbname, cname, query_spec);
    }
}

new Main().execute();

// node cosmos_main.js get_database_account
// node cosmos_main.js list_databases
// node cosmos_main.js create_collection dev test1
// node cosmos_main.js delete_collection dev test1
// node cosmos_main.js delete_collection dev coll1523524504556
// node cosmos_main.js list_collections dev
// node cosmos_main.js create_document dev test
// node cosmos_main.js delete_document dev test e7b48ee4-0100-2e15-8eb8-fa80b1ee2f5e
// node cosmos_main.js query_documents dev test cjoakim 1523567974845
