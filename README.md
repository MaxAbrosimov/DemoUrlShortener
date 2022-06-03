# Url shortener
This is a small service for solving the problem of shortening links. I`m running on JDK 1.8


## Prerequisites

| *Docker*                                              |                            *Cassandra*                             |                     *NodeJs*                      |                                                              *JDK* |
|-------------------------------------------------------|:------------------------------------------------------------------:|:-------------------------------------------------:|-------------------------------------------------------------------:|
| [Install docker](https://docs.docker.com/get-docker/) | [Install cassandra via docker](https://hub.docker.com/_/cassandra) | [Install nodeJs](https://nodejs.org/en/download/) | [Install JDK](https://www.oracle.com/java/technologies/downloads/) |


## Startup 
| *Cassandra*                       |        *API*        |                                    *UI* |
|-----------------------------------|:-------------------:|----------------------------------------:|
| docker run -p 9042:9042 cassandra | mvn spring-boot:run | cd frontend && npm install && npm start |


## API CALL
### Get all existing urls
curl --location --request GET 'localhost:8080/v1/url/list'

###  Shorten url
curl --location --request POST 'http://localhost:8080/v1/url/shortifyUrl' \
--header 'Content-Type: application/json' \
--data-raw '{
"longUrl": "http://google.com"
}'

### Get url by short
curl --location --request POST 'http://localhost:8080/v1/url/getByShort' \
--header 'Content-Type: application/json' \
--data-raw '{
"shortUrl": "4d8bd056"
}' 


## UI
Open localhost:3000

Enter url to field
Press enter or submit button

See a shorted url below the button
Copy created url to the clipboard and past into browser search field
Or click on the url
