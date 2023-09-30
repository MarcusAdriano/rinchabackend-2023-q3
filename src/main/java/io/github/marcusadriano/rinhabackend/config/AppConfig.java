package io.github.marcusadriano.rinhabackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.marcusadriano.rinhabackend.utils.CustomStringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper mapper() {

        final var simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new CustomStringDeserializer());

        return new ObjectMapper().registerModule(new JavaTimeModule())
                .registerModule(simpleModule);
    }

}
