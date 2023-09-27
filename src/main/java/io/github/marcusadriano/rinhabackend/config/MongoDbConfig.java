package io.github.marcusadriano.rinhabackend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDbConfig {

    @Bean
    public MongoClient writeConcernResolver(@Value("${spring.data.mongodb.uri:mongodb://localhost:27017}") final String mongoUri) {
        return MongoClients.create(mongoUri);
    }

}
