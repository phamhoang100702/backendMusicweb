package project.musicwebsite.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.musicwebsite.entity.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingerDTO extends UserDTO{
    private String bio;
    private String linkSocial;
    private List<User> follower;

}
