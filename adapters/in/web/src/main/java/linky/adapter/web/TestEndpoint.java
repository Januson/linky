package linky.adapter.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class TestEndpoint {

    @GetMapping("/hello")
    public String greet() {
        return "Hello, World!";
    }

}
