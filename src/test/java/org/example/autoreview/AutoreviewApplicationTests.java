package org.example.autoreview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("${spring.profiles.active:test}")
@SpringBootTest
class AutoreviewApplicationTests {

    @Test
    void contextLoads() {
    }

}
