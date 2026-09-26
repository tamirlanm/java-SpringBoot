## Practice 3

### Constructor Injection

MovieServiceImplementation receives MovieStorage through constructor injection.

No field injection or @Autowired is used.

### Conditional Bean

The conditional bean is enabled when:

practice2.environment=development

Run with the development profile:

```bash
./gradlew bootRun --args="--spring.profiles.active=dev"