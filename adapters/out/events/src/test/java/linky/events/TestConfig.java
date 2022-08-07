package linky.events;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@SpringBootConfiguration
@ComponentScan("linky.events")
@EnableAutoConfiguration
public class TestConfig {

	@Bean
	SpyingAddNewVisit addNewVisit() {
		return new SpyingAddNewVisit();
	}
}
