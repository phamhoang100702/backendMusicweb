package project.musicwebsite.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaylistDTO {
    private String name;
    private Boolean status;
    private UserDTO user;
}
