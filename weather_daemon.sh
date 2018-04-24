#!/bin/bash

# nohup ./weather_daemon.sh <maxCount> <loopSleepMs> &
# nohup ./weather_daemon.sh 100 5000 &
#
# Chris Joakim, Microsoft, 2018/04/23

# mvn clean compile

source classpath

echo $1
echo $2

java -cp $CP org.cjoakim.azure.cosmos.WeatherDaemon $1 $2 > tmp/weather_daemon.txt

echo 'done'
