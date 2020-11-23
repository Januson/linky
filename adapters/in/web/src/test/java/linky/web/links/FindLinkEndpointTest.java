package linky.web.links;

import linky.links.FindLink;
import linky.links.Ip;
import linky.links.Link;
import linky.links.Name;
import linky.links.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FindLinkEndpoint.class)
class FindLinkEndpointTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FindLink findLink;

    @Test
    void existingLinkCanBeFound() throws Exception {
        final var expectedLink = "test_name";
        final var expectedLinkName = new Name(expectedLink);
        given(this.findLink.findBy(any(Name.class), any(Ip.class)))
            .willReturn(existingLink(expectedLinkName));

        mockMvc.perform(MockMvcRequestBuilders.get("/links/{name}", expectedLinkName)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(
                content().string(
                    not(emptyOrNullString())));
    }

    @Test
    void linkNotFound() throws Exception {
        final var unknownLinkName = "test_name";
        given(this.findLink.findBy(any(Name.class), any(Ip.class)))
            .willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/links/{name}", unknownLinkName)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(
                content().string(
                    not(emptyOrNullString())));
    }

    private Optional<Link> existingLink(final Name name) {
        return Optional.of(
            new Link(name, new Url("test_url"))
        );
    }

}
