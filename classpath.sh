#!/bin/bash

# This script calculates the maven CLASSPATH and produces the
# 'classpath' file, which can be sourced from a bash shell script.
#
# Chris Joakim, Microsoft, 2018/03/31

mvn dependency:tree > doc/mvn_dependency_tree.txt

mvn dependency:build-classpath -Dmdep.outputFile=doc/classpath.txt

python3 classpath.py classpath > classpath

python3 classpath.py jar_contents > jar_contents.sh

echo 'done, see the "classpath" file'
