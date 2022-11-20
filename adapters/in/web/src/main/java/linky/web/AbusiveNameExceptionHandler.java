package linky.web;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import jakarta.inject.Singleton;
import linky.links.Name;

@Produces
@Singleton
public class AbusiveNameExceptionHandler implements ExceptionHandler<Name.NameIsAbusive, HttpResponse<ApiError>> {

    @Override
    public HttpResponse<ApiError> handle(HttpRequest request, Name.NameIsAbusive exception) {
        return HttpResponse.badRequest(
            new ApiError.Builder(HttpStatus.BAD_REQUEST, exception.getMessage()).build()
        );
    }
}