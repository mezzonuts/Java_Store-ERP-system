$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
$env:PATH="$env:JAVA_HOME\bin;C:\maven\apache-maven-3.9.6\bin;$env:PATH"
Set-Location D:\try_my_project\ERP_JAVA
& "C:\maven\apache-maven-3.9.6\bin\mvn.cmd" clean compile exec:java "-Dexec.mainClass=com.sosha.SoshaApp" "-Dspring.profiles.active=sqlite"
