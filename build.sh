#!/usr/bin/env bash
mvn package
if [ $? -eq 0 ]; then
    echo "mvn package successful"
    rm /Library/Tomcat/webapps/khaneBeDoosh.war
    cp target/khaneBeDoosh.war /Users/hoda/Library/Tomcat/webapps
    echo "cp"
fi