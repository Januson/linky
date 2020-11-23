package linky.web.visits;

import linky.links.Ip;
import linky.links.LinkNotFound;
import linky.links.LinkVisited;
import linky.links.Name;
import linky.visits.Country;
import linky.visits.FindAllVisits;
import linky.visits.Origin;
import linky.web.links.FindLinkEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FindLinkEndpoint.class)
class FindAllVisitsEndpointTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FindAllVisits useCase;

    @Test
    void existingLinkCanBeFound() throws Exception {
        final var linkName = new Name("test_name");
        given(this.useCase.allOf(any(Name.class)))
            .willReturn(existingVisits(linkName));

        mockMvc.perform(get("/visits/{name}", "test_name")
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
        given(this.useCase.allOf(any(Name.class)))
            .willThrow(LinkNotFound.class);

        mockMvc.perform(get("/visits/{name}", unknownLinkName)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(
                content().string(
                    not(emptyOrNullString())));
    }

    private List<LinkVisited> existingVisits(final Name name) {
        return List.of(
            new LinkVisited(name,
                new Origin.Encoded(new Ip("unknown"), new Country("unknown"))),
            new LinkVisited(name,
                new Origin.Encoded(new Ip("8.8.8.8"), new Country("Canada"))),
            new LinkVisited(name,
                new Origin.Pending(new Ip("192.168.122.155")))
        );
    }

}
