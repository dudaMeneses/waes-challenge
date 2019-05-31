# WAES challenge

## Testing

### Unit Tests
For unit testing I'm isolating the layers I have to really test only that unit.
- For controllers I'm using spring-test framework ``WebMvcTest`` and the tests I proceed are pretty much if the exceptions I expect are returning the messages I set and if the result I'm returning is the JSON I want;
- For services I'm using ``Mockito`` framework only in order to mock repository layer and using ``Spy`` to emulate real class usage;
- For repository layer I'm using ``DBRider`` to load data in my in-memory embedded DB and  ``DataJpaTest`` to mock everything;
- In all unit tests, also, ``Hamcrest`` is used to assert the data and figure out if the result are as expected.

### Integration Tests

## Code

### Architecture
I'm using Context Bound Pattern which it is quite good for microservices which we have a well defined scope. Also the package organization is separated by layer once spring-boot many times works better this way, like if necessary define packages to scan.  

### SpringBoot
Following the idea of easily startable, auto-configurable and full of useful resources applications I choose to use spring-boot because it gives me an end-to-end option to create services and in case it runs in container it will be possible to scale easily.

### Documentation
 


## Future Improvements