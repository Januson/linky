package linky.web;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import org.springframework.http.HttpStatus;

import jakarta.inject.Singleton;
import linky.links.Name;
import linky.web.links.NotFoundException;

@Produces
@Singleton
public class AlreadyInUseExceptionHandler implements ExceptionHandler<Name.NameAlreadyInUse, HttpResponse<ApiError>> {

    @Override
    public HttpResponse<ApiError> handle(HttpRequest request, Name.NameAlreadyInUse exception) {
        return HttpResponse.notFound(
            new ApiError.Builder(HttpStatus.CONFLICT, exception.getMessage()).build()
        );
    }
}