package org.xpdojo.bank.cdc.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

import static org.springframework.boot.WebApplicationType.SERVLET;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
public class MobileBankingService {

    private static final Logger log = LoggerFactory.getLogger(MobileBankingService.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(MobileBankingService.class)
                .web(SERVLET)
                .run(args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder aBuilder) {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate template = new RestTemplate(factory);
        template.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
        return template;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            logRequest(request, body);
            ClientHttpResponse response = execution.execute(request, body);
            logResponse(response);
            return response;
        }

        private void logRequest(HttpRequest request, byte[] body) throws IOException {
            if (log.isDebugEnabled()) {
                log.debug("=========================== request begin ================================================");
                log.debug("URI         : {}", request.getURI());
                log.debug("Method      : {}", request.getMethod());
                log.debug("Headers     : {}", request.getHeaders());
                log.debug("Request body: {}", new String(body, "UTF-8"));
                log.debug("========================== request end ================================================");
            }
        }

        private void logResponse(ClientHttpResponse response) throws IOException {
            if (log.isDebugEnabled()) {
                log.debug("============================ response begin==========================================");
                log.debug("Status code  : {}", response.getStatusCode());
                log.debug("Status text  : {}", response.getStatusText());
                log.debug("Headers      : {}", response.getHeaders());
                log.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
                log.debug("======================= response end =================================================");
            }
        }
    }
}
