package com.tboostai_batch.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.tboostai_batch.common.GeneralConstants.*;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder createInternalWebClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT)  // 连接超时 5 秒
                .responseTimeout(Duration.ofMillis(RESPONSE_TIMEOUT))            // 响应超时 30 秒
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(IO_TIMEOUT, TimeUnit.MILLISECONDS)) // 读超时 10 秒
                        .addHandlerLast(new WriteTimeoutHandler(IO_TIMEOUT, TimeUnit.MILLISECONDS)));                 // 写超时 10 秒

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    @Bean
    @Primary
    public WebClient.Builder createExternalWebClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT)  // 连接超时 5 秒
                .responseTimeout(Duration.ofMillis(RESPONSE_TIMEOUT))            // 响应超时 30 秒
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(IO_TIMEOUT, TimeUnit.MILLISECONDS)) // 读超时 10 秒
                        .addHandlerLast(new WriteTimeoutHandler(IO_TIMEOUT, TimeUnit.MILLISECONDS)));                 // 写超时 10 秒

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
