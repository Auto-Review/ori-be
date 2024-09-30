package org.example.autoreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AutoreviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoreviewApplication.class, args);
    }

}
