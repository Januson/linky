package linky.web.links;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class LinkDto extends RepresentationModel<LinkDto> {

    private final String name;
    private final String longUrl;

    public LinkDto(final String name, final String longUrl) {
        this.name = name;
        this.longUrl = longUrl;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("long_url")
    public String getLongUrl() {
        return longUrl;
    }

}
