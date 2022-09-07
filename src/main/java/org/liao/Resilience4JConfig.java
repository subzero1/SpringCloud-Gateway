package org.liao;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4JConfig {
//    /**
//     * 初始化断路器，读取Resilience4J的yaml配置
//     *
//     * @param circuitBreakerRegistry
//     * @return
//     */
//    @Bean
//    public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(
//            CircuitBreakerRegistry circuitBreakerRegistry) {
//        ReactiveResilience4JCircuitBreakerFactory factory = new ReactiveResilience4JCircuitBreakerFactory();
//
//        //自定义断路器配置
////        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom().slidingWindowSize(100).build();
//
//        //设置断路器默认配置
//        //不修改默认值可以忽略
//        factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                //默认超时规则,默认1s,不使用断路器超时规则可以设置大一点
//                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(6000)).build())
//                //默认断路器规则
////                        .circuitBreakerConfig(circuitBreakerConfig).build())
//                .build());
//
//        //添加自定义拦截器
//        factory.configureCircuitBreakerRegistry(circuitBreakerRegistry);
//        return factory;
//    }


    @Bean
    public ReactiveResilience4JCircuitBreakerFactory defaultCustomizer() {

        //自定义断路器配置
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom() //
                // 滑动窗口的类型为时间窗口
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                // 时间窗口的大小为60秒
                .slidingWindowSize(10)
                // 在单位时间窗口内最少需要5次调用才能开始进行统计计算
                .minimumNumberOfCalls(5)
                // 在单位时间窗口内调用失败率达到50%后会启动断路器
                .failureRateThreshold(50)
                // 允许断路器自动由打开状态转换为半开状态
                .enableAutomaticTransitionFromOpenToHalfOpen()
                // 在半开状态下允许进行正常调用的次数
                .permittedNumberOfCallsInHalfOpenState(5)
                // 断路器打开状态转换为半开状态需要等待60秒
                .waitDurationInOpenState(Duration.ofSeconds(10))
                // 所有异常都当作失败来处理
                .recordExceptions(Throwable.class)
                .build();
        //時間限制器
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(60000))
                .build();

        //速率控制
        RateLimiterConfig rateLimiterConfig=RateLimiterConfig.ofDefaults();

        ReactiveResilience4JCircuitBreakerFactory factory = new ReactiveResilience4JCircuitBreakerFactory();
        factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                //超时规则,默认1s
                .timeLimiterConfig(timeLimiterConfig)
                //设置断路器配置
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
        return factory;
    }

}
