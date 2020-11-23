package linky.web.links;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import linky.links.CreateNewLink;
import linky.links.Name;
import linky.links.NewLink;
import linky.links.Url;
import linky.web.ExceptionHandlerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@Import({ExceptionHandlerAdvice.class})
class CreateNewLinkEndpointTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CreateNewLink createNewLink;

    @BeforeEach
    void setUp() {
    }

    @Test
    void created() throws Exception {
        final var newLinkName = new Name("test_name");
        given(this.createNewLink.create(any(NewLink.class)))
            .willReturn(newLinkName);

        final var request = new CreateNewLinkRequest("www.test.test", null);

        mockMvc.perform(post("/links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJson(request))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(
                content().string(
                    not(emptyOrNullString())));
    }

    public static String asJson(final CreateNewLinkRequest request) {
        try {
            return new ObjectMapper()
                .writerFor(CreateNewLinkRequest.class)
                .writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void duplicateName() throws Exception {
        given(this.createNewLink.create(any(NewLink.class)))
            .willThrow(new Name.NameAlreadyInUse("test_name"));

        final var request = new CreateNewLinkRequest("www.test.test", "test_name");

        mockMvc.perform(post("/links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJson(request))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andExpect(
                content().string(
                    not(emptyOrNullString())));
    }

    @Test
    void abusiveName() throws Exception {
        given(this.createNewLink.create(any(NewLink.class)))
            .willThrow(new Name.NameIsAbusive("test_name"));

        final var request = new CreateNewLinkRequest("www.test.test", "test_name");

        mockMvc.perform(post("/links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJson(request))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(
                content().string(
                    not(emptyOrNullString())));
    }

    @Test
    void malformedUrl() throws Exception {
        given(this.createNewLink.create(any(NewLink.class)))
            .willThrow(new Url.MalformedUrl("www/"));

        final var request = new CreateNewLinkRequest("www/", "test_name");

        mockMvc.perform(post("/links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJson(request))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(
                content().string(
                    not(emptyOrNullString())));
    }

}
