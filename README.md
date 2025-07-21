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

The idea is to add the json file like below and change the propery - `load.data.file=restaurants_data.json`
`src/main/resources/data/restaurants_data.json`

## Technical Decisions

### The algorithm
The naive way is to fetch all the restaurant ids and calculate the Euclidian distance and filter the results where the user is within the restaurant radius. This is very expensive and not scalable.

We use GeoHashing to find the hash of each restaurant coordinates and its radius reach. This information is saved in a separate table called `restaurant_geohash`. 
This is like a lookup table to search for the related geohashes comparing with the user geohash. From that list, we filter by calulating the actual distance from the restaurant to the user - which should not exceed the restaurant radius. 
This extra check is to make sure we do not send wrong results to the clients (I do not trust my creation yet. Needs more testing with loads of data). 

I went with a geo-hashing algorightm instead of in-memory solutions like  a Quad-Tree is because geo-hashing is more scalable. Quad-Trees has to me stored in memory and we have to load the data when the service boots up. 
There could be alternative approaches where we could store the quad-tree in the disk (that supports such datastrucure to be stored as is and queried from) and search it - not worth the effort.

Another algorithm similar to geo-hashing is Hilbert Curve. Which could be a good solution here. Unfortunately I couldnt figure out the right algorithm (convert x,y -> Z) yet to implement - Still thinking about it.

Both the Geo-Hashing and Hiberts Curve algorithms convert 2D cordinates to 1D value, which is easier and efficient for geo proximity problems. 

There are geo-hashing libraries, redis supports geo-hashing and even postgres has a plugin. But our requirement is not in the context of actual latitude and longiture so I had to improvise and come up with something that suits our needs (which was painful to write btw.).
Maybe the existing libraries supports such ranges other than lat and lan, but did not explore.

### The datastore
I choose a relational database - PostgreSQL here, because one - postgres is cool. two, I know relational databases more and did not have enough time to explore and implement document database. 
If this is the only requirement of this service, we could move the geohashes to a cache service and maybe store the restaurant details as documents. If we are expecting more relations and other entites then better off with a relational database. I say this with my limited knowledge.

## Improvements
- Instead of using a table in the database to store the geo-hash, we could use a in-memory cache like Redis which would be faster.
- I see the geo-hashing custom algoritm I wrote is not performing enough. Still needs improvements, but it works with the little testing I did. 


