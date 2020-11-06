package linky.web.links;

import linky.links.CreateNewLink;
import linky.links.FindLink;
import linky.links.Link;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CreateNewLinkEndpoint.class)
public class CreateNewLinkEndpointTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CreateNewLink createNewLink;

    @Test
    public void existingLinkCanBeFound() throws Exception {
        final var expectedLink = "test_name";
        final var expectedLinkName = new Link.Name(expectedLink);
        given(this.findLink.findBy(expectedLinkName))
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
    public void linkNotFound() throws Exception {
        final var unknownLinkName = "test_name";
        given(this.findLink.findBy(any(Link.Name.class)))
            .willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/links/{name}", unknownLinkName)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(
                content().string(
                    not(emptyOrNullString())));
    }

    private Optional<Link> existingLink(final Link.Name name) {
        return Optional.of(
            new Link(name, new Link.Url("test_url"))
        );
    }

}
