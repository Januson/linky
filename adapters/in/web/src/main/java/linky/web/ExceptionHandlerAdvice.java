package linky.web;

import linky.links.LinkNotFound;
import linky.links.Name;
import linky.links.Url;
import linky.web.links.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(final NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                new ApiError.Builder(
                    HttpStatus.NOT_FOUND, e.getMessage()
                ).build()
            );
    }

    @ExceptionHandler(Name.NameAlreadyInUse.class)
    public ResponseEntity<ApiError> handleNameConflict(final Name.NameAlreadyInUse e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(
                new ApiError.Builder(
                    HttpStatus.CONFLICT, e.getMessage()
                ).build()
            );
    }

    @ExceptionHandler(Name.NameIsAbusive.class)
    public ResponseEntity<ApiError> handleAbusiveName(final Name.NameIsAbusive e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                new ApiError.Builder(
                    HttpStatus.BAD_REQUEST, e.getMessage()
                ).build()
            );
    }

    @ExceptionHandler(Url.MalformedUrl.class)
    public ResponseEntity<ApiError> handleMalformedUrl(final Url.MalformedUrl e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                new ApiError.Builder(
                    HttpStatus.BAD_REQUEST, e.getMessage()
                ).build()
            );
    }

    @ExceptionHandler(LinkNotFound.class)
    public ResponseEntity<ApiError> handleLinkNotFound(final LinkNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                new ApiError.Builder(
                    HttpStatus.NOT_FOUND, e.getMessage()
                ).build()
            );
    }

}