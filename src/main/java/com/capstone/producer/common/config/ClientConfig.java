package com.capstone.producer.common.config;

import com.capstone.producer.clients.AeroApiClientCaller;
import com.capstone.producer.clients.AviationStackClientCaller;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class that sets up a couple beans. Can be used for other bean set up as well
 */
@Configuration
public class ClientConfig {
    @Bean
    @Qualifier("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name="aviationStackClientCaller")
    public AviationStackClientCaller aviationStackClientCaller(RestTemplate restTemplate, @Value("${aviation.base.url}") String baseUrl, @Value("${aviation.key}") String key) {
        return new AviationStackClientCaller(restTemplate, baseUrl, key);
    }

    @Bean(name="aeroApiClientCaller")
    public AeroApiClientCaller aeroApiClientCaller(RestTemplate restTemplate, @Value("${aero.base.url}") String baseUrl, @Value("${aero.key}") String key) {
        return new AeroApiClientCaller(restTemplate, baseUrl, key);
    }
}
