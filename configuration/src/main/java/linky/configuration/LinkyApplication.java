package linky.configuration;

import linky.events.EventsConfiguration;
import linky.persistence.PersistenceConfiguration;
import linky.web.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
    DomainConfiguration.class,
    EventsConfiguration.class,
    PersistenceConfiguration.class,
    WebConfiguration.class
})
public class LinkyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkyApplication.class, args);
    }

}
