package com.capstone.producer.kafka;

import com.capstone.producer.clients.AviationStackClientCaller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean(name="aviationStackClientCaller")
    public AviationStackClientCaller aviationStackClientCaller(RestTemplate restTemplate, @Value("${aviation.base.url}") String baseUrl, @Value("${aviation.key}") String key) {
        return new AviationStackClientCaller(restTemplate, baseUrl, key);
    }
}
