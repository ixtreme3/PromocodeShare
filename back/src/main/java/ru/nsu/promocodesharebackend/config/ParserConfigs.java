package ru.nsu.promocodesharebackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:parser.properties")
@ConfigurationProperties(prefix = "parser")
@Getter
@Setter
public class ParserConfigs {
    private String categoriesURL ;
    private String shopsURL ;
}