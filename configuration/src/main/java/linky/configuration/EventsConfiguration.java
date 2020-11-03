package linky.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("linky.adapter.events")
public class EventsConfiguration {

}
