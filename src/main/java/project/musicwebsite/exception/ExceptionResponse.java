package project.musicwebsite.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private HttpStatus status;
    private Date date;
    private String message;
    private String request;

}
