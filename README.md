# CapstoneProducer

## About
Producer App connecting to Apache Kafka broker  
This application functions as a producer to our Kafka broker, it is a GET request that takes in a message 
and pushes it to the Kafka topic that the consumer is listening to: the URL to the producer is:
http://34.198.166.4/message/{message}  
Where '{message}' is whatever you please.  
If you receive a 500 error it is likely that our AWS instance that is running the broker is stopped, as it costs 
a decent amount to keep running 24/7, so we shut it down when not developing or testing.

## Release Notes
Milestone 2:  
In this version our producer is mapped as a GET request that takes in a message String and pushes that string onto our broker's topic. Producer application can be run using its associated docker container:  
https://hub.docker.com/r/ewagner2802/flighttrackerproducer  

## Milestone 1:
Milestone was environment setup(Docker/Kafka).

## Milestone 2: 
In this version mapping is baseurl/flighticao/{flighticao}. Where the flight icao is the flight you wish to collect data on. The producer will then send the message
received from aviation stack api to a new topic to be used by consumer application.

## Milestone 3: 
In milestone 3 we implemented a function that will search the API for flights with live flight data and will connect to the broker.

## Milestone 4: 
In milestone 4 we integrated job scheduling for updating tracked flights. We also integrated a websocket that communicates with the front end in order for the 
producer to keep track of flights the user wants to see.

## Milestone 5: 
General bug fixes and code clean up.
