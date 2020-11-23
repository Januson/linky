package linky.jobs;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@SpringBootConfiguration
@ComponentScan("linky.jobs")
@EnableAutoConfiguration
public class TestConfig {

    @Bean
    SpyingEncodePendingVisits encodePendingVisits() {
        return new SpyingEncodePendingVisits();
    }

}