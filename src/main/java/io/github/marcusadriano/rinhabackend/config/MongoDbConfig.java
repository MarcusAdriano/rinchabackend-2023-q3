package io.github.marcusadriano.rinhabackend.config;

import com.mongodb.WriteConcern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.WriteConcernResolver;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "io.github.marcusadriano.rinhabackend.repository.mongo")
public class MongoDbConfig {

    @Bean
    public WriteConcernResolver writeConcernResolver() {
        return action -> WriteConcern.UNACKNOWLEDGED;
    }

}
