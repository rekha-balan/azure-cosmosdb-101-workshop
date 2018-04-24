#!/bin/bash

# Chris Joakim, Microsoft, 2018/04/09

# mvn clean compile

source classpath

#echo $CP

#java -cp $CP org.cjoakim.azure.cosmos.dao.PostalCodeDao

#java -cp $CP org.cjoakim.azure.cosmos.dao.WeatherDao

#java -cp $CP org.cjoakim.azure.cosmos.dao.DocumentDbDao

#java -cp $CP org.cjoakim.azure.cosmos.WeatherDaemon 10 5000

java -cp $CP org.cjoakim.azure.cosmos.util.MapDataUtil


echo 'done'
