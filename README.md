<br/>

# Spring Cloud Gateway 
<br/>

## Spring Cloud Gateway 서비스 설정 
~~~
server:
  port: 8000

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://localhost:8761/eureka
spring:
  application:
    name: gateway-service
  cloud:
    gateway:
      routes:
        - id: first-service
          uri: http://localhost:8081/
          predicates:
            - Path=/first-service/**
              # http://localhost:8000/first-service/welcome 을 호출하면
              # 위 uri에 Path값을 붙인 http://localhost:8081/first-service/welcome 을 호출하게 된다. 
        - id: second-service
          uri: http://localhost:8082/
          predicates:
            - Path=/second-service/**
              # http://localhost:8000/second-service/welcome 을 호출하면
              # 위 uri에 Path값을 붙인 http://localhost:8082/second-service/welcome 을 호출하게 된다. 
~~~

<br/><br/><br/><br/>
