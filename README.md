# UWC SASI SAKAI INTEGRATION MODULE

This is the source code for the UWC JOB SCHEDULER INTEGRATION.

The master branch is the most current stable development release.
The other branches are currently or previously being tested or supported.

## Building
This package can be placed directly in the root of the sakai folder. 

[OPTIONAL] modify the sakai source root pom to include the "uwc_generic" folder as a module. 
```
Build -> Deploy -> Ready to go. 
```
To build Sakai you need Java 1.8. Once you have clone a copy of this repository you can
build it by running (or `./mvnw install` if you don't have Maven installed):
```
mvn install
```
## Running
When you are done, deploy Sakai to Tomcat:
```
mvn clean install sakai:deploy -Dmaven.tomcat.home=/path/to/your/tomcat
```
Now start Tomcat:
```
cd /path/to/your/tomcat/bin
./startup.sh && tail -f ../logs.catalina.out
```
## Updating

Ensure that all dependencies are up to date, located in ALL pom files located in this folder, 

to update beans: 
 
```
Modify components.xml located in -> uwc_generic/pack/src/webapp/WEB-INF/
to meet necessary requirements. 

```


Once Sakai has started up (it usually takes around 30 seconds), open your browser and navigate to [SAKAI_URL]

## Under Development
This plugin is not currently under development. 
