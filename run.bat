@echo off
cd /d D:\try_my_project\ERP_JAVA
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
mvn -DskipTests clean compile exec:java "-Dexec.mainClass=com.sosha.SoshaApp" "-Dspring.profiles.active=sqlite"
pause
