package me.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        /**
            [Custom Pre Filter]
            이 프로젝트에서는 Netty 서버를 사용하고 있는데
            Netty는 비동기 방식 서버이기 때문에
            톰캣과 같이 ServletHttpRequest, ServletHttpResponse가 아닌
            ServerHttpRequest, ServerHttpResponse를 사용한다.
         **/
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom Pre Filter: request id -> {}", request.getId());

            /**
                [Custom Post Filter]
                Mono: WebFlux 에서 사용하는 단일 데이터 타입. 인터페이스 형태의 단일 데이터 타입 하나로 반환.
             **/
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Custom Post Filter: response status -> {}", response.getStatusCode());
            }));
        });
    }

    public static class Config {
        // Put the configuration properties
    }

}
