Description of the component ( Ex: what it achieves, how it can be used in a portal/as a API, deployed to a mobile phone)
-------------------------------
An app that exposes the API to bridge following :

https requests and mqtt client in the IOT platform.
ws (STOMP and websockets) requests and mqtt client in the IOT platform.

APIs exposed are as below:

https://device-status-webapp.run.covapp.io/devicelog
https://device-status-webapp.run.covapp.io/publish

Build instructions
------------------
Local build:

1.Install maven 
2.Install jdk 1.8
3.Download code
4.In the local directory run "mvn  install"

Build using Jenkins:

1.click on https://build.dtw.covisint.com/view/CISCO/job/CISCO-device-status-webapp
2.click build with parameters: 2.0
3.once built download from : http://nexus.dtw.covisint.com/service/local/artifact/maven/redirect?r=snapshots&g=com.covisint.css.cisco.iot&a=device-status-webapp&v=2.0-SNAPSHOT&e=war

Compiler requirements, JDK version, DB requirements (mysql)
-----------------------------------------------------------
1. jdk 1.8
2. any J2ee webserver. Tested on tomcat 8.0 as it supports websockets and STOMP

Preferred IDEs for the implementation( example: Eclipse, IntelliJ, Netbeans)
-------------------------------------
1. Eclipse Juno and above J2EE version
