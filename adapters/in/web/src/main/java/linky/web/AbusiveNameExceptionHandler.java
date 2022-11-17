package linky.web;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import org.springframework.http.HttpStatus;

import jakarta.inject.Singleton;
import linky.links.Name;

@Produces
@Singleton
public class AbusiveNameExceptionHandler implements ExceptionHandler<Name.NameIsAbusive, HttpResponse<ApiError>> {

    @Override
    public HttpResponse<ApiError> handle(HttpRequest request, Name.NameIsAbusive exception) {
        return HttpResponse.notFound(
            new ApiError.Builder(HttpStatus.BAD_REQUEST, exception.getMessage()).build()
        );
    }
}