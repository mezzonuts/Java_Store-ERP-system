@echo off
cd /d D:\try_my_project\ERP_JAVA
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot
set PATH=%JAVA_HOME%\bin;C:\maven\apache-maven-3.9.6\bin;%PATH%
echo Running Sosha POS with cmd...
echo Note: If using PowerShell, run: cmd /c "D:\try_my_project\ERP_JAVA\run.bat"
call mvn -DskipTests clean compile exec:java "-Dexec.mainClass=com.sosha.SoshaApp" "-Dspring.profiles.active=sqlite"
pause
