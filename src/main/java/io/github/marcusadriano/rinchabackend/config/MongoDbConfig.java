package io.github.marcusadriano.rinchabackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "io.github.marcusadriano.rinchabackend.repository")
public class MongoDbConfig {
}
