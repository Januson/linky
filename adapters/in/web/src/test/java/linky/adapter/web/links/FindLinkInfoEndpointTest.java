package linky.adapter.web.links;

import linky.adapter.web.SpringIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringIntegrationTest
public class FindLinkInfoEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void existingLinkCanBeFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/links/link_id")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(
                content().string(
                    not(emptyOrNullString())));
    }

    @Test
    public void shouldReturn404WhenLinkNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/links/missing_link_id")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

}
