package linky.web;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import jakarta.inject.Singleton;
import linky.links.Name;
import linky.web.links.NotFoundException;

@Produces
@Singleton
public class AlreadyInUseExceptionHandler implements ExceptionHandler<Name.NameAlreadyInUse, HttpResponse<ApiError>> {

    @Override
    public HttpResponse<ApiError> handle(HttpRequest request, Name.NameAlreadyInUse exception) {
        return HttpResponse.status(HttpStatus.CONFLICT)
            .body(new ApiError.Builder(HttpStatus.CONFLICT, exception.getMessage()).build());
    }
}