# Fullstack Kotlin demo

Example application with a frontend targeting JS, and a backend targeting the 
JVM, both written in Kotlin.

Android app could work too, theoretically, but it takes some more effort to
get the build to work.

## Build

```
./gradlew build
```

Note: Before building the frontend, the base URL of the backend should be
adapted to match the local environment in 
`full-stack-kotlin-demo-js/src/main/kotlin/org/example/js/Application.kt`.

## Run

### Backend

To get the API running on `http://localhost:8888`:

```
java -jar full-stack-kotlin-demo-jvm/build/libs/full-stack-kotlin-demo-jvm-all.jar
```

An OpenAPI specification can be requested from `http://localhost:8888/swagger.json`
to discover available endpoints.

### Frontend

Host the files located in `full-stack-kotlin-demo-js/build/resources/main` with
a web server.

For a quick standalone web server, run one of the following, depending on
what's available on your machine, within the directory named above:

```
python3 -m http.server
```

```
php -S localhost:8000
```