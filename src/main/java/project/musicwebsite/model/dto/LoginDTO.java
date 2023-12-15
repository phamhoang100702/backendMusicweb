package project.musicwebsite.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginDTO {
    private String token;
}
