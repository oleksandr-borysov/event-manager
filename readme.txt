What is done:
BE test task option 2
Design and implement a backend API (RESTFUL or GraphQL) to serve web or mobile client to:
- Upload events: (timestamp, device_id, event_type, payload can by any key-value pairs)
- Query raw events by device_id, event_type and time range
   - The possible queries are not clearly defined intentionally, please make your own judgement
Bonus: explain how to scale it to millions of devices
Choose any language and database

Used: RESTful, JSON, Java, Spring, JPA, SQL DB

Build project:
> mvn package

Run using command line from project root directory:
$> mvn spring-boot:run
or
$> java -jar target/event-manager-1.0.0.jar –spring.profiles.active=local

Run in Eclipse:
Run->Run Configurations->Arguments->VM Arguments
Add -Dspring.profiles.active=local

Event JSON data type format:
    {
        "timestamp": "yyyy-MM-ddTHH:mm:ss",
        "deviceId": "Any string",
        "eventType": "Any string",
        "payload": [
            {
                "key": "Any string",
                "value": "Any string"
            },
				...
            {
                "key": "Any string",
                "value": "Any string"
            }
        ]
    }

For testing can be used events data stub in JSON format located in:
{project-root}/src/test/resources/test-data-stub.json

For testing was used the Postman REST client application.

Add event:
POST http://localhost:8080/eventmanager/event/add
Payload example:
{	"timestamp":"2017-09-17T12:55:15",	"deviceId":"d1",	"eventType":"error",	"payload": [{"key":"p1","value":"v1"},{"key":"p2","value":"v2"},{"key":"p3","value":"v3"}]}
Execute from command line:
curl -H "Content-Type: application/json" -X POST -d '{	"timestamp":"2017-09-17T12:55:15",	"deviceId":"d1",	"eventType":"error",	"payload": [{"key":"p1","value":"v1"},{"key":"p2","value":"v2"},{"key":"p3","value":"v3"}]}' http://localhost:8080/eventmanager/event/add

Add set of events:
POST http://localhost:8080/eventmanager/event/add_all
Payload example:
[
{	"timestamp":"2017-09-17T12:55:15",	"deviceId":"d1",	"eventType":"error",	"payload": [{"key":"p1","value":"v1"},{"key":"p2","value":"v2"},{"key":"p3","value":"v3"}]
},
...
{	"timestamp":"2017-09-19T14:55:15",	"deviceId":"d2",	"eventType":"warning",	"payload": [{"key":"p1","value":"v1"},{"key":"p2","value":"v2"},{"key":"p3","value":"v3"}]
}
]

Find event using custom filter
GET http://localhost:8080/eventmanager/event?start_time={yyyy-MM-ddTDD:mm:ss}&end_time={yyyy-MM-ddTDD:mm:ss}&device_id={string value}&event_type={string value}
Parameters after '?' is optional and can appear in any sequence.
Execute from command line:
GET http://localhost:8080/eventmanager/event?start_time=2017-09-18T12:56:00&end_time=2017-09-18T14:56:00&device_id=d2&event_type=warning
GET http://localhost:8080/eventmanager/event?device_id=d2&event_type=warning
GET http://localhost:8080/eventmanager/event?start_time=2017-09-18T14:55:00&device_id=d2

Find all events
GET http://localhost:8080/eventmanager/event/all
or
GET http://localhost:8080/eventmanager/event
Execute from command line:
curl -X GET http://localhost:8080/eventmanager/event/all
or
curl -X GET http://localhost:8080/eventmanager/event

Find by device_id
GET http://localhost:8080/eventmanager/event/device_id={string value}
Execute from command line:
curl -X GET http://localhost:8080/eventmanager/event/device_id=d1

Find by event_type
GET http://localhost:8080/eventmanager/event/event_type={string value}
Execute from command line:
curl -X GET http://localhost:8080/eventmanager/event/event_type=error

Find by device_id event_type
GET http://localhost:8080/eventmanager/event/device_id={string value}/event_type={string value}
Execute from command line:
curl -X GET http://localhost:8080/eventmanager/event/device_id=d1/event_type=error

Find by time range
GET http://localhost:8080/eventmanager/event/start_time={yyyy-MM-ddTDD:mm:ss}/end_time={yyyy-MM-ddTDD:mm:ss}
curl -X GET http://localhost:8080/eventmanager/event/start_time=2017-09-17T12:00:00/end_time=2017-09-19T15:00:00

Find by device_id, event_type, time range
GET http://localhost:8080/eventmanager/event/device_id={string value}/event_type={string value}/start_time={yyyy-MM-ddTDD:mm:ss}/end_time={yyyy-MM-ddTDD:mm:ss}
curl -X GET http://localhost:8080/eventmanager/event/device_id=d1/event_type=error/start_time=2017-09-17T12:00:00/end_time=2017-09-19T15:00:00

Delete all:
POST http://localhost:8080/eventmanager/event/delete_all


What not implemented: data validation, proper error handling