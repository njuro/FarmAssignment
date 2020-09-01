# FarmAssignment

Interview assignment for CleverFarm.

## Task
Full text of assignment is [available here](src/main/resources/static/assignment.pdf).

## Setup

### Requirements
1. `PostgreSQL` database with `PostGis` extension installed
    - Local / Remote ([Amazon RDS](https://aws.amazon.com/rds/postgresql/)) / Docker container ([postgis/postgis](https://registry.hub.docker.com/r/postgis/postgis/))
2. `Maven` for building application
3. `JRE` for running application
4. (Optional) `Docker` for running integration tests

### Deployment steps
1. Clone this repository and access the application directory
    - `git clone https://gitlab.com/njuro/farm-assignment.git FarmAssignment`
    - `cd FarmAssignment`
2. Open file `src/main/resources/application.properties` and edit following properties to match your database:
    - `spring.datasource.url` - database connection url (includes name of the database)
    - `spring.datasource.username` - database user
    - `spring.datasource.password` - password for database user
3. Build the application with Maven
    - `mvn clean install`
    - (Optional) skip tests by adding `-DskipTests` parameter
4. Launch application
    - `java -jar target/*.jar`

### After deploy
1. Open your browser and head to `http://localhost:8081`
2. You will be redirected to `/login` page and log in
    - Username: `user`
    - Password: `password`
3. You will be redirected to `/swagger-ui/index.html` where you can try different API endpoints. Sample farms are inserted to database at first launch
4. Alternatively you can import [endpoint collection](src/main/resources/static/FarmAssignment.postman_collection.json) into Postman
5. To log out head to `/logout` page

## Notes
- Several sample farms are inserted to database at first launch
- Field borders are represented as [WKT](https://www.wikiwand.com/en/Well-known_text_representation_of_geometry)
- To generate WKT from map area you can use this utility - [https://arthur-e.github.io/Wicket/sandbox-gmaps3.html](https://arthur-e.github.io/Wicket/sandbox-gmaps3.html)
- Each farm belongs to one country (represented by [ISO3 code](https://www.wikiwand.com/en/ISO_3166-1_alpha-3)). Fields of this farm must be located entirely within that country and cannot overlap with other fields