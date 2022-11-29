package linky.web.links;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import linky.links.CreateNewLink;
import linky.links.Name;
import linky.links.NewLink;
import linky.links.Url;
import linky.web.ApiError;

@MicronautTest
@ExtendWith(MockitoExtension.class)
class CreateNewLinkEndpointTest {

    @Mock
    @MockBean
    public CreateNewLink useCase;

    @Test
    void created(RequestSpecification spec) {
        final var newLinkName = new Name("test_name");
        final var request = new CreateNewLinkRequest("www.test.test", null);
        given(this.useCase.create(any(NewLink.class))).willReturn(newLinkName);

        LinkDto createdLink = spec
            .given()
            .contentType(ContentType.JSON)
            .body(asJson(request))
            .when().post("/links")
            .then().statusCode(201)
            .extract()
            .body().as(LinkDto.class);//body(is("Hello World"));

        assertThat(createdLink)
            .returns(request.getUrl(), LinkDto::getLongUrl)
            .returns(newLinkName.toString(), LinkDto::getName);
    }


    @Test
    void duplicateName(RequestSpecification spec) {
        final var request = new CreateNewLinkRequest("www.test.test", "test_name");
        given(this.useCase.create(any(NewLink.class)))
            .willThrow(new Name.NameAlreadyInUse("test_name"));

        ApiError error = spec
            .given()
            .contentType(ContentType.JSON)
            .body(asJson(request))
            .when().post("/links")
            .then().statusCode(409)
            .extract()
            .body().as(ApiError.class);//body(is("Hello World"));

        assertThat(error.getMessage())
            .isNotBlank();
    }

    @Test
    void abusiveName(RequestSpecification spec) {
        final var request = new CreateNewLinkRequest("www.test.test", "test_name");
        given(this.useCase.create(any(NewLink.class)))
            .willThrow(new Name.NameIsAbusive("test_name"));

        ApiError error = spec
            .given()
            .contentType(ContentType.JSON)
            .body(asJson(request))
            .when().post("/links")
            .then().statusCode(400)
            .extract()
            .body().as(ApiError.class);

        assertThat(error.getMessage())
            .isNotBlank();
    }

    @Test
    void malformedUrl(RequestSpecification spec) {
        final var request = new CreateNewLinkRequest("www/", "test_name");
        given(this.useCase.create(any(NewLink.class)))
            .willThrow(new Url.MalformedUrl("www/"));

        ApiError error = spec
            .given()
            .contentType(ContentType.JSON)
            .body(asJson(request))
            .when().post("/links")
            .then().statusCode(400)
            .extract()
            .body().as(ApiError.class);

        assertThat(error.getMessage())
            .isNotBlank();
    }

    public static String asJson(final CreateNewLinkRequest request) {
        try {
            return new ObjectMapper().writerFor(CreateNewLinkRequest.class)
                .writeValueAsString(request);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
