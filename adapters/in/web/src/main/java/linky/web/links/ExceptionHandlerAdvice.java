package linky.web.links;

import linky.web.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleException(final NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                new ApiError.Builder(
                    HttpStatus.NOT_FOUND, e.getMessage()
                ).build()
            );
    }

}