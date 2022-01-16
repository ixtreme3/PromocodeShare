package ru.nsu.promocodesharebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.nsu.promocodesharebackend.config.ParserConfigs;

@EnableScheduling
@EnableConfigurationProperties(ParserConfigs.class)
@SpringBootApplication
public class PromocodeShareBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromocodeShareBackendApplication.class, args);
    }

}
