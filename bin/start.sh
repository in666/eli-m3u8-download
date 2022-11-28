#!/bin/sh

nohup /usr/java/jdk1.8.0_171/bin/java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=17002 -jar eli-m3u8-download-1.0.0.jar > eli-m3u8-download-1.0.0.out 2>&1 &

exit 0
