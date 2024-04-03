package com.schoolmanagement.poc.config.swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("info.app")
@Getter
@Setter
public class SwaggerConfigProperties {

    private String name;
    private String version;
    private String description;

}