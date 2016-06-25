# OSGi-Greeter
A demonstration of OSGI modules, using apache felix

## To Build 
      
      mvn -f Greeter/pom.xml install 
     
      mvn -f EnglishDict/pom.xml package 
     
      mvn -f FrenchDict/pom.xml package 
      
      
## Deploy in container
felix from: http://felix.apache.org/downloads.cgi#framework
At the time of writing the felix framework version was 5.4.0

### Deploy
 1. copy the jars from each target folder in to bundle directory of the felix framework

### run felix
    
    java -jar bin/felix.jar


## Deply in Embedded Felix app
Copy the bundles to EmbeddedFelix/bundle. run Starter.main method from within Embedded felix project


