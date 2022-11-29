package linky.web;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import jakarta.inject.Singleton;

@Produces
@Singleton
public class RuntimeExceptionHandler implements ExceptionHandler<RuntimeException, HttpResponse<ApiError>> {

    @Override
    public HttpResponse<ApiError> handle(HttpRequest request, RuntimeException exception) {
        return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            new ApiError.Builder(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()).build()
        );
    }
}
