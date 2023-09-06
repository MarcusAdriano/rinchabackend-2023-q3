package io.github.marcusadriano.rinhabackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "io.github.marcusadriano.rinhabackend.repository.mongo")
public class MongoDbConfig {
}
