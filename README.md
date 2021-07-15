# Datastream project using Spring Boot framework

Creating a service to process data coming from Meraki devices.

## Import the project Datastream into IDE

  - File -> Import -> Existing Maven Projects -> next -> browse -> select Datastream folder -> Select Folder -> Finish
  
## Set username and password for the project to connect to mysql
  
  - Open Datastream project on IDE -> under src/main/resources, open db.properties -> replace YOUR_USERNAME and YOUR_PASSWORD with yours

## Create mySQL database 

  - create database datastreamdb

## Run the application

  - Open Datastream project on IDE -> open src/main/java -> open package com.meraki -> right click on DatastreamApplication.java -> find Run As -> click on Java Application and wait for the application complete booting up.
  
## Feed data input to the application

  - Open Postman -> set url: localhost:8080/datastream -> choose POST method -> select Body -> select raw and JSON -> paste the first record in:
    {
      "device_id":1,
      "value":1,
      "timestamp":1611741600
    }
  - click send
  - Continue feeding one record at a time
  - {
        "device_id":1,
        "value":2,
        "timestamp":1611741601
    }

    {
        "device_id":2,
        "value":1,
        "timestamp":1611741602
    }

    {
        "device_id":1,
        "value":6,
        "timestamp":1611741660
    }

    {
        "device_id":2,
        "value":3,
        "timestamp":1611741659
    }

    {
        "device_id":1,
        "value":9,
        "timestamp":1611741930
    }
    
 ## Get Data Display
 
  - Either use postman or a browser
  - On postman -> set url: localhost:8080/datastream -> select GET method -> click send
  - On browser -> set url: localhost:8080/datastream
  
