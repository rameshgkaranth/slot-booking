# Slot-Booking POC
Problem Statement: A slot is a 2 hour window. The available slots are from 9 AM to 6 PM with no slot for 1PM to 2PM. There are 4 vans available. Each van can hold 20 cartons and each carton’s height, width and breadth
are 15”, 30” and 15” respectively. An order has a set of item and each item has a height, width and breadth.

* Build a REST based service to provide the slot information for any order.

* Build a REST based service to book the slot.

Feel free to implement any improvement you see fit for this application


## Build instructions
Gradle has been used as the build tool for this project. Jetty gradle plugin is used to test the change locally.

* To Clean the build directory - <code>./gradlew clean</code>
* To Build/Assemble the project - <code>./gradlew clean build</code>
* To Deploy WAR in Jetty - <code>./gradlew clean jettyRunWar</code>
* In order to debug in IDE run command - <code>export GRADLE_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=8009,server=y,suspend=n"</code>


## Deploying WAR to Heroku
<code>heroku deploy:war <Path to WAR FILE> --app slot-booking</code>


## Herko URL for deployed WAR
https://slot-booking.herokuapp.com/
