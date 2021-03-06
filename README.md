# Url shortener
This is a small service for solving the problem of shortening links. 
It`s running on JDK 1.8. 



## MVP flow:
- Service validates provided URL, creates a unique identifier for it and saves it to cassandra tables.
- I chose cassandra because it fits the main task as a key-value storage. Most of cassandra calls will be reads & inserts, without deletes. Which is perfect for cassandra.
- identifier was chosen based by current time, assuming that service is working in one instance.

### What left:
- Add logging
- Add more unit tests to reach 100% coverage


## Prerequisites

| *Docker*                                              |                            *Cassandra*                             |                     *NodeJs*                      |                                                              *JDK* |                                                *Maven* |
|-------------------------------------------------------|:------------------------------------------------------------------:|:-------------------------------------------------:|-------------------------------------------------------------------:|-------------------------------------------------------:|
| [Install docker](https://docs.docker.com/get-docker/) | [Install cassandra via docker](https://hub.docker.com/_/cassandra) | [Install nodeJs](https://nodejs.org/en/download/) | [Install JDK](https://www.oracle.com/java/technologies/downloads/). | [Install maven](https://maven.apache.org/install.html) |

 Please make sure that you are running on java 8. *java -version* If you have another version as a default one run:
 export JAVA_8_HOME=$({path_to_jdk})
export JAVA_HOME=$JAVA_8_HOME


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
"shortUrl": ${shortUrl}"
}' 


## UI
Open localhost:{PORT} (By default its 3000. See .env file in frontend package). Please ensure that UI port is the same as *short-url.prefix* 
in application.properties

Enter url to field
Press enter or submit button

See a shorted url below the button
Copy created url to the clipboard and past into browser search field
Or click on the url

