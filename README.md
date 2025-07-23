# 🍽️ Restaurant Finder

A Spring Boot application that helps users find nearby restaurants using Geohashing.

---

## 🚀 Build with

- Love
- Spring Boot
- PostgreSQL
- Flyway DB migration support
- Dockerized with `docker-compose`
- Profile-based config: `postgres`, `h2`
- Geohash-based location indexing and search
- JSON file loader for bulk restaurant data (only for testing purpose)

---

## 🧱 Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker / Docker Compose
- Flyway
- H2 (for testing profile)

---

## 🐳 Running the App via Docker Compose
Make sure you have Docker installed.

### Build the project
```
./gradlew clean build
```

### Run services
```
docker compose up --build
```

### Stop the process
Note that the below command will destroy the datastore as well.
```
docker compose down --volumes
```

## Files to ignore
Please ignore the below files. These are remnants of unfinished thoughts!

```
com/jishnu/restaurantfinder/service/naive/RestaurantNaiveSearchServiceImpl.java
com/jishnu/restaurantfinder/service/hilbert/RestaurantHilbertCurveSearchServiceImpl.java
```

### Testing
Can load a json file with restaurant data during the application startup from a file in the classpath now. I know, but I guess this is enough for testing. I can think of a better approach. At the moment I did not spend much time with it.

The idea is to add the json file like below and change the property - `LOAD_TEST_DATA` in docker-compose.yml
`src/main/resources/data/restaurants_data.json`

Note that we have docker-compose with both our application and database. In an ideal world - we have these running separately. :D

## Technical Decisions

### The algorithm
The naive way is to fetch all the restaurant ids and calculate the Euclidian distance and filter the results where the user is within the restaurant radius. This is very expensive and not scalable.

We use GeoHashing to find the hash of each restaurant coordinates and its radius reach. This information is saved in a separate table called `restaurant_geohash`. 
This is like a lookup table to search for the related geohashes comparing with the user geohash. From that list, we filter by calulating the actual distance from the restaurant to the user - which should not exceed the restaurant radius. 
This extra check is to make sure we do not send wrong results to the clients (I do not trust my creation yet. Needs more testing with loads of data). 

I went with a geo-hashing algorithm instead of in-memory solutions like  a Quad-Tree is because geo-hashing is more scalable. Quad-Trees has to me stored in memory and we have to load the data when the service boots up. 
There could be alternative approaches where we could store the quad-tree in the disk (that supports such datastrucure to be stored as is and queried from) and search it - not worth the effort.

Another algorithm similar to geo-hashing is Hilbert Curve. Which could be a good solution here. Unfortunately I couldnt figure out the right algorithm (convert x,y -> Z) yet to implement - Still thinking about it.
I think Hiberts Curve will perform much better in our case. We can store the 1D value in the database for each restaurant and then, we can do a range query on the user 1D value using the radius.

Both the Geo-Hashing and Hiberts Curve algorithms convert 2D cordinates to 1D value, which is easier and efficient for geo proximity problems. 

There are geo-hashing libraries, redis supports geo-hashing and even postgres has a plugin. But our requirement is not in the context of actual latitude and longiture so I had to improvise and come up with something that suits our needs (which was painful to write btw.).
Maybe the existing libraries supports such ranges other than lat and lan, but did not explore.

### Limits
I have added a set for quadrant limit. The max values x and y can have. You can always modify it. 
`geo.hashing.quad.limit=100000000`


### The datastore
I choose a relational database - PostgreSQL here, because one - postgres is cool. two, I know relational databases more and did not have enough time to explore and implement document database. 
If this is the only requirement of this service, we could move the geohashes to a cache service and maybe store the restaurant details as documents. If we are expecting more relations and other entites then better off with a relational database. I say this with my limited knowledge.

## Improvements
- Instead of using a table in the database to store the geo-hash, we could use a in-memory cache like Redis which would be faster - or maybe keep both.
- I see the geo-hashing custom algoritm I wrote is not performing enough. Still needs improvements, but it works with the little testing I did. 

## Environment tested with
```
~ ❯ java -version                                                                                                                                                                                                                                                                                                   22:52:45
openjdk version "21.0.2" 2024-01-16
OpenJDK Runtime Environment Homebrew (build 21.0.2)
OpenJDK 64-Bit Server VM Homebrew (build 21.0.2, mixed mode, sharing)
```

```
~ ❯ docker --version                                                                                                                                                                                                                                                                                          ✘ 125 22:53:36
Docker version 28.3.2, build 578ccf6
```

```
~/dev/restaurantfinder main +4 !4 ❯ ./gradlew --version                                                                                                                                                                                                                                                             22:53:59

------------------------------------------------------------
Gradle 8.14.3
------------------------------------------------------------

Build time:    2025-07-04 13:15:44 UTC
Revision:      e5ee1df3d88b8ca3a8074787a94f373e3090e1db

Kotlin:        2.0.21
Groovy:        3.0.24
Ant:           Apache Ant(TM) version 1.10.15 compiled on August 25 2024
Launcher JVM:  21.0.2 (Homebrew 21.0.2)
Daemon JVM:    /opt/homebrew/Cellar/openjdk/21.0.2/libexec/openjdk.jdk/Contents/Home (no JDK specified, using current Java home)
OS:            Mac OS X 15.5 aarch64
```

`PostgreSQL 15`

```
~/dev/restaurantfinder main +4 !4 ❯ system_profiler SPHardwareDataType                                                                                                                                                                                                                                              22:54:09
    Hardware Overview:
      Model Name: MacBook Pro
      Model Identifier: MacBookPro17,1
      Chip: Apple M1
      Total Number of Cores: 8 (4 performance and 4 efficiency)
      Memory: 16 GB
      System Firmware Version: 11881.121.1
      OS Loader Version: 11881.121.1
```

