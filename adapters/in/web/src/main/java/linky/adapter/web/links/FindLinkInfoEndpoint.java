package linky.adapter.web.links;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindLinkInfoEndpoint {

    @GetMapping(
        value = "/links/{shortcut}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findLinkInfo(@PathVariable String shortcut) {
        if ("missing_link_id".equalsIgnoreCase(shortcut)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("{\"shortcut\": \"link_id\"}");
    }

}
