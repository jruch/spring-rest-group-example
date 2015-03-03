A simple example of using Apache CXF and JAX-RS for REST-ful web services. Just a place to hack around, used for some training and discussion on the Kuali Rice team.

# Running the Project

You can launch the application from the command line (if you have Maven installed) or from an IDE.

To launch using maven, run the following:

```
mvn spring-boot:run
```

To run from an IDE, load the project into your IDE and set up a run configuration for the ```com.westbrain.sandbox.jaxrs.Application``` main class.

# Using the API

We will use the command line tool ```curl``` to exercise the API. If you also want to have pretty printing, install the ```jq``` command line tool as well.

* http://curl.haxx.se/
* http://stedolan.github.io/jq/

#### Test the end point (no authentication)

```
curl -D hdr.txt http://localhost:8080/groups/1
cat hdr.txt
```

#### You receive the following JSON response, which indicates you are not authorized to access the resource:

```json
{
  "error": "unauthorized",
  "error_description":"Full authentication is required to access this resource"
}
```

#### In order to access the protected resource, you must first request an access token via the OAuth handshake. Request OAuth authorization:

```sh
curl -X POST -vu clientapp:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=password1&username=username1&grant_type=password&scope=read%20write&client_secret=123456&client_id=clientapp"
```

#### A successful authorization results in the following JSON response (tokens will differ):

```json
{
  "access_token": "ff16372e-38a7-4e29-88c2-1fb92897f558",
  "token_type": "bearer",
  "refresh_token": "f554d386-0b0a-461b-bdb2-292831cecd57",
  "expires_in": 43199,
  "scope": "read write"
}
```

#### Use the `access_token` returned in the previous request to make the authorized request to the protected endpoint:

```sh
curl -D hdr.txt http://localhost:8080/groups/1 -H "Authorization: Bearer 649e4a31-21a8-41a8-b655-c1d90935240c"
```

If the request is successful, you will see the following JSON response (or something similar):

```json
{
    "id":1,
    "name":"aliquet",
    "description":"ac pharetra consequat purus sollicitudin urna ipsum"
}

```

After the specified time period, the `access_token` will expire. Use the `refresh_token` that was returned in the original OAuth authorization to retrieve a new `access_token`:

```sh
curl -X POST -vu clientapp:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=f554d386-0b0a-461b-bdb2-292831cecd57&client_secret=123456&client_id=clientapp"
```



https://spring.io/guides/gs/actuator-service/

http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html