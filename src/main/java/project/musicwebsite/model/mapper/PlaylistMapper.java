package project.musicwebsite.model.mapper;

import project.musicwebsite.entity.Playlist;
import project.musicwebsite.model.dto.PlaylistDTO;
import project.musicwebsite.model.dto.UserDTO;

public class PlaylistMapper {
    public static PlaylistDTO convert(Playlist playlist){
        UserDTO userDTO = UserMapper.convert(playlist.getCreator());
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName(playlist.getName());
        playlistDTO.setUser(userDTO);
        playlistDTO.setStatus(playlist.getStatus());
        return playlistDTO;
    }
}
