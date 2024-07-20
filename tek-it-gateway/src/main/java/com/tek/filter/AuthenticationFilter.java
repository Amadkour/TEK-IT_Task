package com.tek.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tek.filter.RouteValidator;
import com.tek.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> implements Ordered {

    private final JwtUtil jwtUtil;
    private final RouteValidator validator;

    public AuthenticationFilter(JwtUtil jwtUtil, RouteValidator validator) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.validator = validator;
    }

    public static class Config {
        // Put the configuration properties
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, "Missing authorization header", HttpStatus.BAD_REQUEST);
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    return onError(exchange, "Unauthorized access to application", HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("error", true);
        responseMap.put("success", false);
        responseMap.put("message", err);

        try {
            ObjectMapper mapper = new ObjectMapper();
            byte[] responseBytes = mapper.writeValueAsBytes(responseMap);
            response.getHeaders().add("Content-Type", "application/json");
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBytes)));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public int getOrder() {
        return -1; // Ensure that this filter is executed before other filters
    }
}
