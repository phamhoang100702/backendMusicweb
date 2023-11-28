package project.musicwebsite.model.mapper;

import project.musicwebsite.entity.User;
import project.musicwebsite.model.dto.UserDTO;

import java.util.LinkedList;
import java.util.List;

public class UserMapper {
    public static UserDTO convert(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static List<UserDTO> convertList(List<User> list){
        List<UserDTO> userDTOS = new LinkedList<>();
        for(User user:list){
            userDTOS.add(convert(user));
        }
        return userDTOS;
    }
}
