package linky.web.links;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class CreateNewLinkRequest {

    private final String url;
    private final String name;

    @JsonCreator
    CreateNewLinkRequest(
        @JsonProperty(value = "url", required = true) final String url,
        @JsonProperty(value = "name") final String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public String getName() {
        return this.name;
    }

}
    