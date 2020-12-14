package linky.web;

import linky.links.CreateNewLink;
import linky.links.FindAllLinks;
import linky.links.FindLink;
import linky.visits.FindAllVisits;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@TestConfiguration
@SpringBootConfiguration
@ComponentScan("linky.web")
@EnableAutoConfiguration
@EnableWebMvc
public class TestConfig {

    @MockBean
    private FindAllLinks findAllLinks;

    @MockBean
    private FindLink findLink;

    @MockBean
    private CreateNewLink createNewLink;

    @MockBean
    private FindAllVisits findAllVisits;

}