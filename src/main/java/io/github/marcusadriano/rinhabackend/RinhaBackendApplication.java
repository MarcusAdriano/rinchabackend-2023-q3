package io.github.marcusadriano.rinhabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RinhaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RinhaBackendApplication.class, args);
    }

}
