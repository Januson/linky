package linky.web.links;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import linky.links.CreateNewLink;
import linky.links.Link;
import linky.links.Links;
import linky.links.Name;
import linky.links.NewLink;
import linky.links.Url;
//import linky.web.ExceptionHandlerAdvice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest
//@Import({ExceptionHandlerAdvice.class})
@MicronautTest
class CreateNewLinkEndpointTest {

	private CreateNewLink useCase;

	@BeforeEach
	void setUp() {
		this.useCase = mock(CreateNewLink.class);
	}

	@MockBean(CreateNewLink.class)
	CreateNewLink createNewLink() {
		return this.useCase;
	}

	@Test
	void created(RequestSpecification spec) {
		final var newLinkName = new Name("test_name");
		final var request = new CreateNewLinkRequest("www.test.test", null);
		given(this.useCase.create(any(NewLink.class))).willReturn(newLinkName);

//		mockMvc.perform(post("/links").contentType(MediaType.APPLICATION_JSON)
//				.content(asJson(request)).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isCreated())
//				.andExpect(content().string(not(emptyOrNullString())));
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

//
//	@Test
//	void duplicateName() throws Exception {
//		given(this.useCase.create(any(NewLink.class)))
//				.willThrow(new Name.NameAlreadyInUse("test_name"));
//
//		final var request = new CreateNewLinkRequest("www.test.test", "test_name");
//
//		mockMvc.perform(post("/links").contentType(MediaType.APPLICATION_JSON)
//				.content(asJson(request)).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isConflict())
//				.andExpect(content().string(not(emptyOrNullString())));
//	}
//
//	@Test
//	void abusiveName() throws Exception {
//		given(this.useCase.create(any(NewLink.class)))
//				.willThrow(new Name.NameIsAbusive("test_name"));
//
//		final var request = new CreateNewLinkRequest("www.test.test", "test_name");
//
//		mockMvc.perform(post("/links").contentType(MediaType.APPLICATION_JSON)
//				.content(asJson(request)).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isBadRequest())
//				.andExpect(content().string(not(emptyOrNullString())));
//	}
//
//	@Test
//	void malformedUrl() throws Exception {
//		given(this.useCase.create(any(NewLink.class))).willThrow(new Url.MalformedUrl("www/"));
//
//		final var request = new CreateNewLinkRequest("www/", "test_name");
//
//		mockMvc.perform(post("/links").contentType(MediaType.APPLICATION_JSON)
//				.content(asJson(request)).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isBadRequest())
//				.andExpect(content().string(not(emptyOrNullString())));
//	}

	public static String asJson(final CreateNewLinkRequest request) {
		try {
			return new ObjectMapper().writerFor(CreateNewLinkRequest.class)
					.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
