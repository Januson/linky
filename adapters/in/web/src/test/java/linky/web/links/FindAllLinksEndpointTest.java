package linky.web.links;

import linky.links.FindAllLinks;
import linky.links.Link;
import linky.links.Name;
import linky.links.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FindAllLinksEndpoint.class)
class FindAllLinksEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindAllLinks useCase;

    @Test
    void existingLinksCAnBeFound() throws Exception {
        given(useCase.all()).willReturn(links());

        mockMvc.perform(get("/links")
            .header("Content-Type", "application/json"))
            .andExpect(status().isOk());

        then(useCase).should().all();
    }

    private List<Link> links() {
        return List.of(
            new Link(new Name("test_name_1"), new Url("test_url_1")),
            new Link(new Name("test_name_2"), new Url("test_url_2")),
            new Link(new Name("test_name_3"), new Url("test_url_3"))
        );
    }

}