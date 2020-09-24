package linky.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("linky.adapter.web")
public class LinkyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkyApplication.class, args);
    }

}
