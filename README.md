# Akka Example

A simple example of [Akka](https://akka.io/).

### Get Started

```bash
mvn clean package
java -jar target/akka-example-0.0.1.jar
```

There are three types of actor defined:
- Director, who can direct singers and dancers.
- Singer, who can sing.
- Dancer, who can dance.

When this app starts, a director is created first. And then, the director will create two singers
and two dancers as its child actors, and command them to do their staffs.

Check the logging messages which indicates the lifecycle and actions of each actor.

