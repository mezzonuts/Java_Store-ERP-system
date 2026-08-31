@echo off
cd /d D:\try_my_project\ERP_JAVA
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot
set PATH=%JAVA_HOME%\bin;C:\maven\apache-maven-3.9.6\bin;%PATH%
echo Running Sosha POS...
call mvn -DskipTests clean compile exec:java "-Dexec.mainClass=com.sosha.SoshaApp" "-Dspring.profiles.active=sqlite"
pause
