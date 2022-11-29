package linky.web.links;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import linky.links.FindLink;
import linky.links.Ip;
import linky.links.Link;
import linky.links.Name;
import linky.links.Url;

@MicronautTest
@ExtendWith(MockitoExtension.class)
class FindLinkEndpointTest {

    @Mock
    @MockBean
    public FindLink useCase;

    @Test
    void existingLinkCanBeFound(RequestSpecification spec) {
        final var expectedName = "test_name";
        given(this.useCase.findBy(any(Name.class), any(Ip.class)))
            .willReturn(existingLink(new Name(expectedName)));

        final var foundLink = spec
            .when().get("/links/{name}", expectedName)
            .then().statusCode(200)
            .extract()
            .body().as(LinkDto.class);

        assertThat(foundLink)
            .returns(expectedName, LinkDto::getName);
    }

    @Test
    void linkNotFound(RequestSpecification spec) {
        final var unknownLinkName = "test_name";
        given(this.useCase.findBy(any(Name.class), any(Ip.class)))
            .willReturn(Optional.empty());

        spec
            .given().contentType(ContentType.JSON)
            .when().get("/links/{name}", unknownLinkName)
            .then().statusCode(404);
    }

    private Optional<Link> existingLink(final Name name) {
        return Optional.of(new Link(name, new Url("test_url")));
    }
}
