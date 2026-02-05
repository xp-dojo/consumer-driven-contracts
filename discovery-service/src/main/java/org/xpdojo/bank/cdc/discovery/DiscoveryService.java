package org.xpdojo.bank.cdc.discovery;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

import static org.springframework.boot.WebApplicationType.SERVLET;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryService {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryService.class)
                .web(SERVLET)
                .run(args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My API")
                        .version("1.0")
                        .description("API Description"));
    }
}

