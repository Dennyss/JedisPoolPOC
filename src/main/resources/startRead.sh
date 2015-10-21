#!/bin/bash
JAVA_HOME=/tools/jdk1.7.0_40

$JAVA_HOME/bin/java -cp redisPoc.jar:libs/* com.Application retrieve &