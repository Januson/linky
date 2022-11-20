package linky.web;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import jakarta.inject.Singleton;
import linky.links.LinkNotFound;
import linky.links.Url;

@Produces
@Singleton
public class LinkNotFoundExceptionHandler implements ExceptionHandler<LinkNotFound, HttpResponse<ApiError>> {

    @Override
    public HttpResponse<ApiError> handle(HttpRequest request, LinkNotFound exception) {
        return HttpResponse.status(HttpStatus.CONFLICT).body(
            new ApiError.Builder(HttpStatus.CONFLICT, exception.getMessage()).build()
        );
    }
}