# WAES challenge
This project is part of hiring process for WAES Scalable Application Developer job.

## How to Run
### Dependencies
- Java 8
- Maven

### Running application
Once you have Maven and Java 8 installed locally you just need to run ``mvn clean spring-boot:run`` to test it. 
After this, you can proceed with tests and verify services documentation in the follow URL:
- SWAGGER: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Testing
### Unit Tests
For unit testing I'm isolating the layers I have to really test only that unit.
- For controllers I'm using spring-test framework ``WebMvcTest`` and the tests I proceed are pretty much if the exceptions I expect are returning the messages I set and if the result I'm returning is the JSON I want;
- For services I'm using ``Mockito`` framework only in order to mock repository layer and using ``Spy`` to emulate real class usage;
- For repository layer I'm using ``DBRider`` to load data in my in-memory embedded DB and  ``DataJpaTest`` to mock everything;
- In all unit tests, also, ``Hamcrest`` is used to assert the data and figure out if the result are as expected.

### Integration Tests
For integration tests I'm using ``SpringBootTest`` and proceeding with different test cases that certify all layers integrating properly, and not the logic of one or another, so, basically, I'm testing what goes at least beyond the controller layer.

## Code
I believe in clean code and SOLID concepts as the main guideline to code, so I tried in my whole code follow this. Because of that, you will not see comments everywhere once the code should be sufficiently self explanatory. The only comments I certified to insert were related to decisions I made or public/class javadocs.

### Architecture
I'm using Context Bound Pattern which it is quite good for microservices because we have a well defined scope and if something is out of scope it represents, possibly, a point of integration. Also the package organization is separated by layer once spring-boot many times works better this way, like if necessary define packages to scan.  

### SpringBoot
Following the idea of easily startable, auto-configurable and full of useful resources applications I choose to use spring-boot because it gives me an end-to-end option to create services and in case it runs in container it will be possible to scale easily.

### Database
I opted to a in-memory embedded H2 database because it is easy to work with, I can have a control about the ID that is passed to my services (be sure that it will be not null and not repeatable inside the application) and also I can work with transactions to lock some instructions when saving on it. 

### Documentation
For service documentation I used ``Swagger``, then I can add description to the services exposed and information that can be passed to it. 
For the other layers I feel that it is important to have at least public methods and classes with javadoc, then we can have a brief of the functionality without the necessity to read its code.

## Future Improvements
##### Docker
Host it in containers make it easily to share same resource and infrastructure configuration.
##### Use a real DB
In case it scale, the information will be only inside the in-memory local container, not shared with other containers for the same application.
##### Configuration outside application
For both security and configuration reason, it is better to have all environment related configuration outside application code.
##### Merge left and right endpoints into the same service
Both implementation do basically the same, but for the opposite direction. It could be a second path parameter. 
##### Implement contracts
In case someone use this API in the future it's better to provide contracts to help on their development.


