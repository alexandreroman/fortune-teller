# Fortune Teller

Fortune Teller is a demo project written in [Kotlin](https://kotlin-lang.org),
showcasing cloud-native technologies such as [Spring Boot](https://spring.io/projects/spring-boot)
and [Spring Cloud](https://cloud.spring.io).

Thanks to [Spring Cloud Services for PCF](https://docs.pivotal.io/spring-cloud-services),
this app is leveraging key technologies for microservices:
 - [Netflix Eureka](https://github.com/Netflix/eureka): a service registry for locating
  microservices;
 - [Netflix Ribbon](https://github.com/Netflix/ribbon): a client-side load balancer to
 distribute load among microservices;
 - [Netflix Hystrix](https://github.com/Netflix/Hystrix): a latency and fault tolerance
 library;
 - [Open Feign](https://github.com/OpenFeign/feign): a REST client library that allows
 you to consume HTTP APIs with minimal overhead.


This app is made of two main components:
 - `fortune-service`: a microservice connected to a database, serving fortunes;
 - `fortune-ui`: a simple HTML frontend, rendering fortunes to users.

## How to use it?

### Run locally

Compile this project using Maven and JDK 8:
```shell
$ ./mvnw clean package
```

When running locally, this app requires an Eureka server:
```shell
$ java -jar eureka-server/target/eureka-server.jar
```

You also need to run a [RabbitMQ server](https://www.rabbitmq.com).
The easiest way is to use the [official RabbitMQ Docker image](https://hub.docker.com/_/rabbitmq):
```shell
$ docker run --hostname rabbit --rm -p "5672:5672/tcp" rabbitmq:3
```

You are now ready to start the two main components:
```shell
$ java -jar fortune-service/target/fortune-service.jar
$ java -jar fortune-ui/target/fortune-ui.jar
```

Go to `http://localhost:8080`:
<img src="https://i.imgur.com/ghv2Qkw.png"/>

You may want to start more processes. All you need is to set a different port for each process:
```shell
$ PORT=1234 java -jar fortune-service/target/fortune-service.jar
$ PORT=5678 java -jar fortune-service/target/fortune-service.jar
```

Thanks to Eureka, these new processes are automatically registered, and consumers like
`fortune-ui` seamlessly access these services.

### Deploy to Pivotal Cloud Foundry

Prior to deploying this app to Pivotal Cloud Foundry, you need to create required services:
```shell
$ cf create-service p-circuit-breaker-dashboard standard circuit-breaker
$ cf create-service p-service-registry standard service-registry
```

Monitor service creation (it can take some time):
```shell
$ cf services
name               service                       plan       bound apps   last operation
circuit-breaker    p-circuit-breaker-dashboard   standard                create succeeded
service-registry   p-service-registry            standard                create succeeded
```

Now it's time to deploy the app to PCF:
```shell
$ cf push
```

### Deploy to Pivotal Web Services

In case you want to deploy to [Pivotal Web Services](https://run.pivotal.io)
(a public instance of PCF for testing purposes), you should use a different manifest
when pushing the app:
```shell
$ cf push -f manifest-pws.yml
```

## Contribute

Contributions are always welcome!

Feel free to open issues & send PR.

## License

Copyright &copy; 2018 Pivotal Software, Inc.

This project is licensed under the [Apache Software License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).

