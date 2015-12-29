#!/bin/sh
APP_HOME=/export/home/aranjalk/raspberry
PATH=/a01/software/jdk1.7:$PATH
export PATH 
export APP_HOME
 
CLASSPATH=${APP_HOME}/RaspberryServices-0.0.1-SNAPSHOT.jar
for dd in ${APP_HOME}/lib/*.jar ${APP_HOME}/lib/*.zip; do
        CLASSPATH=${CLASSPATH}:${dd}
done
CLASSPATH=${CLASSPATH}:.
export CLASSPATH
echo CLASSPATH is ${CLASSPATH}
JAVA_RUN=/a01/software/jdk1.7/bin/java
export JAVA_RUN

${JAVA_RUN} -cp $CLASSPATH com/covisint/idm/services/scheduler/PiRunner

