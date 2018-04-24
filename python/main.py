"""
Usage:
    python main.py list_databases
    python main.py query_cosmosdb dev weather most_recent_documents 1
    python main.py query_cosmosdb dev weather most_recent_current_weather 1
    python main.py query_cosmosdb dev weather by_id 70643:current
    python main.py query_cosmosdb dev weather by_postal_code 46626
    python main.py query_cosmosdb dev weather by_state NC
    python main.py query_cosmosdb dev weather by_gps_location 41.6833 -86.2503 10000
    python main.py query_cosmosdb dev weather by_gps_location 37.565354 -84.951536 1000    # Parksville KY
    python main.py query_cosmosdb dev weather by_gps_location 37.565354 -84.951536 1000000
    python main.py query_cosmosdb dev weather map_query
    python main.py post_zipcodes_to_function 100
Options:
    -h --help     Show this screen.
    --version     Show version.
"""
# See https://docs.microsoft.com/en-us/azure/cosmos-db/sql-api-sql-query
# Chris Joakim, Microsoft, 2018/04/15

import collections
import csv
import json
import os
import random
import sys
import time
import urllib

import arrow
import requests

from docopt import docopt

from src.joakim import config
from src.joakim import cosmos
from src.joakim import fs

VERSION='April 2018'

def print_options(msg):
    print(msg)
    arguments = docopt(__doc__, version=VERSION)
    print(arguments)

def build_param_dict(name, value):
    param_dict = dict()
    param_dict['name'] = name
    param_dict['value'] = value
    return param_dict

if __name__ == "__main__":

    if len(sys.argv) > 1:
        start_time = time.time()
        func = sys.argv[1].lower()

        if func == 'adhoc':
            pass

        elif func == 'list_databases':
            util = cosmos.DocDbUtil()
            dbs = util.list_databases()
            print(dbs)

        elif func == 'query_cosmosdb':
            dbname = sys.argv[2].lower()
            cname  = sys.argv[3].lower()
            qid    = sys.argv[4].lower()
            results = None

            if qid == 'most_recent_documents':
                count = sys.argv[5]
                query_spec = dict()
                sql = 'SELECT TOP {} * FROM c order by c.apiEpoch desc'.format(count)
                query_spec['query'] = sql
                query_spec['parameters'] = []
                results = cosmos.DocDbUtil().execute_query(dbname, cname, query_spec, True)

            elif qid == 'most_recent_current_weather':
                count = sys.argv[5]
                sql = "SELECT TOP {} c.postalCode.postalCd, c.postalCode.cityName, c.postalCode.stateAbbrv, c.apiEpoch FROM c where c.apiType = 'current' order by c.apiEpoch desc".format(count)
                query_spec = dict()
                query_spec['query'] = sql
                query_spec['parameters'] = []
                results = cosmos.DocDbUtil().execute_query(dbname, cname, query_spec, True)

            elif qid == 'by_id':
                idParam = build_param_dict('@id', sys.argv[5])
                query_spec = dict()
                query_spec['query'] = "SELECT * FROM c WHERE c.id = @id"
                query_spec['parameters'] = [ idParam ]
                results = cosmos.DocDbUtil().execute_query(dbname, cname, query_spec, True)

            elif qid == 'by_postal_code':
                postalCdParam = build_param_dict('@postalCd', sys.argv[5])
                query_spec = dict()
                query_spec['query'] = "SELECT * FROM c WHERE c.postalCode.postalCd = @postalCd order by c.apiEpoch desc"
                query_spec['parameters'] = [ postalCdParam ]
                results = cosmos.DocDbUtil().execute_query(dbname, cname, query_spec, True)

            elif qid == 'by_state':
                postalCdParam = build_param_dict('@stateAbbrv', sys.argv[5])
                query_spec = dict()
                query_spec['query'] = "SELECT * FROM c WHERE c.postalCode.stateAbbrv = @stateAbbrv order by c.apiEpoch desc"
                query_spec['parameters'] = [ postalCdParam ]
                results = cosmos.DocDbUtil().execute_query(dbname, cname, query_spec, True)

            elif qid == 'by_gps_location':
                latitude  = sys.argv[5]
                longitude = sys.argv[6]
                meters    = sys.argv[7]
                coords = '[{}, {}]'.format(longitude, latitude)
                sql = 'SELECT * FROM c WHERE ST_DISTANCE(c.postalCode.location, {}"type": "Point", "coordinates": {} {}) {} {}'.format('{', coords, '}', '<', meters)
                print(sql)
                query_spec = dict()
                query_spec['query'] = sql
                query_spec['parameters'] = []
                results = cosmos.DocDbUtil().execute_query(dbname, cname, query_spec, True)

            elif qid == 'map_query':
                # xxx = sys.argv[5]
                sql = 'SELECT TOP 20 c.postalCode.cityName, c.postalCode.stateAbbrv, c.postalCode.postalCd, c.postalCode.latitude, c.postalCode.longitude, c.apiEpoch FROM c WHERE c.apiEpoch > {} ORDER BY c.apiEpoch DESC'.format(15)
                print(sql)
                query_spec = dict()
                query_spec['query'] = sql
                query_spec['parameters'] = []
                results = cosmos.DocDbUtil().execute_query(dbname, cname, query_spec, True)

            if results:
                jstr = json.dumps(results, sort_keys=False, indent=2)
                print(jstr)
                print('---')
                print('query: {}'.format(json.dumps(query_spec, sort_keys=False, indent=2)))
                print('count: {}'.format(len(results)))

        elif func == 'post_zipcodes_to_function':
            max, count = int(sys.argv[2].lower()), 0
            url = config.Config().zipcode_function_url()
            print("max: {}".format(max))
            print("url: {}".format(url))
            zip_codes = fs.FSUtil().read_json_file('data/nc_zipcodes.json')

            while count < max:
                count = count + 1
                zip_code = random.choice(zip_codes)
                print("count: {} zip_code: {}".format(count, zip_code))
                r = requests.post(url, json=zip_code)
                print(r)
                time.sleep(0.5)

        else:
            print_options('invalid function')
    else:
        print_options('no function given on command-line')
