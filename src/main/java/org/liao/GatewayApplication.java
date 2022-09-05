package org.liao;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 初始化断路器，读取Resilience4J的yaml配置
     * @param circuitBreakerRegistry
     * @return
     */
    @Bean
    public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(
            CircuitBreakerRegistry circuitBreakerRegistry) {
        ReactiveResilience4JCircuitBreakerFactory factory = new ReactiveResilience4JCircuitBreakerFactory();

        //自定义断路器配置
//        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom().slidingWindowSize(100).build();

        //设置断路器默认配置
        //不修改默认值可以忽略
        factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                //默认超时规则,默认1s,不使用断路器超时规则可以设置大一点
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(60000)).build())
                //默认断路器规则
//                        .circuitBreakerConfig(circuitBreakerConfig).build())
                .build());

        //添加自定义拦截器
        factory.configureCircuitBreakerRegistry(circuitBreakerRegistry);
        return factory;
    }
}
