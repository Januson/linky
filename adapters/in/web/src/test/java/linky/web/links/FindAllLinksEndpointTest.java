package linky.web.links;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.specification.RequestSpecification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import linky.links.FindAllLinks;
import linky.links.Link;
import linky.links.Name;
import linky.links.Url;

@MicronautTest
@ExtendWith(MockitoExtension.class)
class FindAllLinksEndpointTest {

    @Mock
    @MockBean
    public FindAllLinks useCase;

    @Test
    void existingLinksCanBeFound(RequestSpecification spec) {
        given(useCase.all()).willReturn(links());

        List<LinkDto> visits = spec
            .when().get("/links")
            .then().statusCode(200)
            .extract()
            .body().as(new TypeRef<>() {});

        assertThat(visits)
            .hasSize(3);
    }

    @Test
    void noLinksAvailable(RequestSpecification spec) {
        given(useCase.all()).willReturn(List.of());

        List<LinkDto> visits = spec
            .when().get("/links")
            .then().statusCode(200)
            .extract()
            .body().as(new TypeRef<>() {});

        assertThat(visits)
            .isEmpty();
    }

    private List<Link> links() {
        return List.of(
            new Link(new Name("test_name_1"), new Url("test_url_1")),
            new Link(new Name("test_name_2"), new Url("test_url_2")),
            new Link(new Name("test_name_3"), new Url("test_url_3")));
    }
}
