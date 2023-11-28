package project.musicwebsite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseObject {
    private String status;
    private String message;
    private Object object;
}
