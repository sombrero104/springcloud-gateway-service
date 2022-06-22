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
<br/>

## 필터 추가 
application.yml 파일에서 설정했었던 라우트 정보인 <br/>
spring.cloud.gateway.routes 설정을 아래와 같이 자바 코드로 설정 가능하다. <br/>
~~~
@Configuration
public class FilterConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                                    .addResponseHeader("first-response", "first-response-header"))
                        .uri("http://localhost:8081/"))
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                    .addResponseHeader("second-response", "second-response-header"))
                        .uri("http://localhost:8082/"))
                .build();
    }
}
~~~ 

http://localhost:8000/first-service/message를 호출하면 아래와 같이 출력되는 것을 확인할 수 있다. <br/>

<img src="./images/request_header.png" width="30%" /><br/>
<img src="./images/response_header.png" width="45%" /><br/>

<br/><br/><br/><br/>
