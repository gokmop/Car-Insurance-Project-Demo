package com.safetycar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static com.safetycar.services.MailServiceImpl.APP_URL;
import static com.safetycar.util.Constants.ConfigConstants.APPLICATION_PROPERTIES;
import static com.safetycar.util.Constants.ConfigConstants.CONTROLLERS_REST;

@Configuration
@PropertySource(APPLICATION_PROPERTIES)
public class SwaggerConfiguration {

    private static final String API_EMAIL = "spring.mail.username";
    private final String apiEmail;

    public SwaggerConfiguration(Environment environment) {
        this.apiEmail = environment.getProperty(API_EMAIL);
    }

    @Bean
    public Docket swaggerConfigurationDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage(CONTROLLERS_REST))
                .build()
                .apiInfo(apiDetails());
    }


    private ApiInfo apiDetails() {
        return new ApiInfo(
                "SafetyCar API Documentation",
                "Api for calculating price for Insurance, creating Users, " +
                        "creating Insurance Policies, and moderate them.",
                "1.0",
                "http://localhost:9090/",
                new springfox.documentation.service.Contact("Georgi Shutov and Kamen Hristov",
                        "https://gitlab.com/g.h.shutov/safety-car-project",
                        apiEmail),
                "API License",
                APP_URL,
                Collections.emptyList());

    }
}
