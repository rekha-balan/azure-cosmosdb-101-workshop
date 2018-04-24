#!/bin/bash

python main.py list_databases > tmp/list_databases.txt

python main.py query_cosmosdb dev weather most_recent_documents 10 > tmp/most_recent_documents.txt

python main.py query_cosmosdb dev weather most_recent_current_weather 10 > tmp/most_recent_current_weather.txt

python main.py query_cosmosdb dev weather by_postal_code 40464 > tmp/by_postal_code_40464.txt

python main.py query_cosmosdb dev weather by_state KY > tmp/by_state_KY.txt

python main.py query_cosmosdb dev weather by_state NC > tmp/by_state_NC.txt

python main.py query_cosmosdb dev weather by_gps_location 37.565354 -84.951536 1000  > tmp/by_gps_location_ky_1000.txt

python main.py query_cosmosdb dev weather by_gps_location 37.565354 -84.951536 1000000  > tmp/by_gps_location_ky_1000000.txt

echo 'done'
