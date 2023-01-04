$webappName = "dev-api"
if ($args[0] -eq "--as-root" -or $args[1] -eq "--as-root") {
	$webappName = "ROOT"
}

Remove-Item C:\Users\Guido\.rsp\redhat-community-server-connector\runtimes\installations\tomcat-10.0.8_1\apache-tomcat-10.0.8\webapps\$webappName.war

if ($args[0] -eq "--no-build" -or $args[1] -eq "--no-build") {
	Start-Sleep -Seconds 10
	(Get-Item C:\Users\Guido\VSCode\SwEPr-Modulhandbuch\modulhandbuch-backend\target\$webappName.war).LastWriteTime = (Get-Date)
} else {
	& "c:\Users\Guido\VSCode\SwEPr-Modulhandbuch\modulhandbuch-backend\mvnw.cmd" clean package spring-boot:repackage -f "c:\Users\Guido\VSCode\SwEPr-Modulhandbuch\modulhandbuch-backend\pom.xml"
	& 'C:\Program Files\jdk-16\bin\java.exe' -jar C:\Users\Guido\.rsp\redhat-community-server-connector\runtimes\installations\tomcat-10.0.8_1\apache-tomcat-10.0.8\lib\jakartaee-migration-1.0.0-shaded.jar C:\Users\Guido\VSCode\SwEPr-Modulhandbuch\modulhandbuch-backend\target\modulhandbuch-backend.war C:\Users\Guido\VSCode\SwEPr-Modulhandbuch\modulhandbuch-backend\target\$webappName.war
}
Copy-Item C:\Users\Guido\VSCode\SwEPr-Modulhandbuch\modulhandbuch-backend\target\$webappName.war -Destination C:\Users\Guido\.rsp\redhat-community-server-connector\runtimes\installations\tomcat-10.0.8_1\apache-tomcat-10.0.8\webapps\$webappName.war
