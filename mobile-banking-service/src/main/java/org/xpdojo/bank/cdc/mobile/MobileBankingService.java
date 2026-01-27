package org.xpdojo.bank.cdc.mobile;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

import static org.springframework.boot.WebApplicationType.SERVLET;

@EnableDiscoveryClient
@SpringBootApplication
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
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My API")
                        .version("1.0")
                        .description("API Description"));
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
