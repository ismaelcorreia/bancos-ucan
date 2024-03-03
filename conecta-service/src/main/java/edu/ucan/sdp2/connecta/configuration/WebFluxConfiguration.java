package edu.ucan.sdp2.connecta.configuration;

import edu.ucan.sdp2.connecta.utils.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Configuration
@EnableWebFlux
public class WebFluxConfiguration implements WebFluxConfigurer {

    @Bean
    public TokenCaptureFilter tokenCaptureFilter() {
        return new TokenCaptureFilter();
    }

    public class TokenCaptureFilter implements org.springframework.web.server.WebFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, org.springframework.web.server.WebFilterChain chain) {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            Session.token = headers.getFirst(HttpHeaders.AUTHORIZATION);
            return chain.filter(exchange);
        }
    }
}
