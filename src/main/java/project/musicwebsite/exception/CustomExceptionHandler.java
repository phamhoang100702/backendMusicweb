package project.musicwebsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice

public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerNotFoundException(NotFoundException ex, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionResponse(HttpStatus.NOT_FOUND, new Date(),
                        ex.getMessage(), webRequest.getDescription(false))
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionResponse> handleConflictException(ConflictException ex, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ExceptionResponse(HttpStatus.CONFLICT, new Date(),
                        ex.getMessage(), webRequest.getDescription(false))
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(BadRequestException ex, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponse(HttpStatus.BAD_REQUEST, new Date(),
                        ex.getMessage(), webRequest.getDescription(false))
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> hanldeAllException(Exception ex, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, new Date(),
                        ex.getMessage(), webRequest.getDescription(false))
        );
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ExceptionResponse> handleNoContent(Exception ex, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ExceptionResponse(HttpStatus.NO_CONTENT, new Date(),
                        ex.getMessage(), webRequest.getDescription(false))
        );
    }

    @ExceptionHandler(FileUploadIoException.class)
    public ResponseEntity<ExceptionResponse> handleFileException(Exception ex, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponse(HttpStatus.BAD_REQUEST, new Date(),
                        ex.getMessage(), webRequest.getDescription(false))
    );
    }
}
