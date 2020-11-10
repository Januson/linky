package linky.events;

import linky.visits.AddNewVisit;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@SpringBootConfiguration
@ComponentScan("linky.events")
@EnableAutoConfiguration
public class TestConfig {

    @MockBean
    private AddNewVisit addNewVisit;

}