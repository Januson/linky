package linky.web.visits;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jdk.jfr.ContentType;
import linky.links.Ip;
import linky.links.LinkVisited;
import linky.links.Name;
import linky.visits.Country;
import linky.visits.FindAllVisits;
import linky.visits.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

//@SpringBootTest(webEnvironment = RANDOM_PORT)
@WebMvcTest(FindAllVisitsEndpoint.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class FindAllVisitsEndpointTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private FindAllVisits useCase;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    void visitsOfExistingLinkCanBeFoun2d() throws Exception {
        final var existingName = "test_name";
        given(this.useCase.allOf(any(Name.class)))
            .willReturn(existingVisits(new Name(existingName)));

        this.mockMvc.perform(get("/visits/" + existingName)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("visits"));
    }

//    @Test
//    void visitsOfExistingLinkCanBeFound() throws Exception {
//        final var existingName = "test_name";
//        given(this.useCase.allOf(any(Name.class)))
//            .willReturn(existingVisits(new Name(existingName)));
//
//        mockMvc.perform(get("/visits/{name}", existingName)
//            .contentType(MediaType.APPLICATION_JSON)
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(
//                content().string(
//                    not(emptyOrNullString())));
//    }
//
//    @Test
//    void linkMustExistToListItsVisits() throws Exception {
//        final var unknownLinkName = "test_name";
//        given(this.useCase.allOf(any(Name.class)))
//            .willThrow(LinkNotFound.class);
//
//        mockMvc.perform(get("/visits/{name}", unknownLinkName)
//            .contentType(MediaType.APPLICATION_JSON)
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound())
//            .andExpect(
//                content().string(
//                    not(emptyOrNullString())));
//    }
//
//    @Test
//    void whenResourcesAreRetrievedPaged_then200IsReceived(){
//        Response response = RestAssured.get(paths.getFooURL() + "?page=0&size=2");
//
//        assertThat(response.getStatusCode(), is(200));
//    }
//    @Test
//    void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived(){
//        String url = getFooURL() + "?page=" + randomNumeric(5) + "&size=2";
//        Response response = RestAssured.get.get(url);
//
//        assertThat(response.getStatusCode(), is(404));
//    }
//    @Test
//    void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources(){
//        createResource();
//        Response response = RestAssured.get(paths.getFooURL() + "?page=0&size=2");
//
//        assertFalse(response.body().as(List.class).isEmpty());
//    }

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
