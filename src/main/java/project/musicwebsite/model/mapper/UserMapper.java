package project.musicwebsite.model.mapper;

import project.musicwebsite.entity.User;
import project.musicwebsite.model.dto.UserDTO;

public class UserMapper {
    public static UserDTO convert(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
