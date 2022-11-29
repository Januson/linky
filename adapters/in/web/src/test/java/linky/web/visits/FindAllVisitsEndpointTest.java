package linky.web.visits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

import linky.links.Ip;
import linky.links.LinkNotFound;
import linky.links.LinkVisited;
import linky.links.Name;
import linky.visits.Country;
import linky.visits.FindAllVisits;
import linky.visits.Origin;
import linky.web.ApiError;

@MicronautTest
@ExtendWith(MockitoExtension.class)
class FindAllVisitsEndpointTest {

    @Mock
    @MockBean
    public FindAllVisits useCase;

    @Test
    void visitsOfExistingLinkCanBeFound(RequestSpecification spec) {
        final var existingName = "test_name";
        given(this.useCase.allOf(any(Name.class)))
            .willReturn(existingVisits(new Name(existingName)));

        List<VisitDto> visits = spec
            .when().get("/visits/{name}", existingName)
            .then().statusCode(200)
            .extract()
            .body().as(new TypeRef<>() {});

        assertThat(visits)
            .hasSize(3);
    }

    @Test
    void linkMustExistToListItsVisits(RequestSpecification spec) {
        final var unknownLinkName = "test_name";
        given(this.useCase.allOf(any(Name.class)))
            .willThrow(new LinkNotFound(new Name(unknownLinkName)));

        ApiError error = spec
            .when().get("/visits/{name}", unknownLinkName)
            .then().statusCode(404)
            .extract()
            .body().as(ApiError.class);

        assertThat(error.getMessage())
            .isNotBlank();
    }

    @Test
    void linkHasNoVisits(RequestSpecification spec) {
        final var linkName = "test_name";
        given(this.useCase.allOf(any(Name.class)))
            .willReturn(List.of());

        List<VisitDto> visits = spec
            .when().get("/visits/{name}", linkName)
            .then().statusCode(200)
            .extract()
            .body().as(new TypeRef<>() {});

        assertThat(visits)
            .isEmpty();
    }

    private List<LinkVisited> existingVisits(final Name name) {
        return List.of(
            new LinkVisited(name,
                new Origin.Encoded(new Ip("unknown"), new Country("unknown"))),
            new LinkVisited(name, new Origin.Encoded(new Ip("8.8.8.8"), new Country("Canada"))),
            new LinkVisited(name, new Origin.Pending(new Ip("192.168.122.155"))));
    }
}
