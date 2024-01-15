package project.musicwebsite.model.mapper;

import project.musicwebsite.entity.Album;
import project.musicwebsite.entity.Song;
import project.musicwebsite.model.dto.AlbumDTO;
import project.musicwebsite.model.dto.SingerDTO;
import project.musicwebsite.model.dto.SongDTO;

import java.util.LinkedList;
import java.util.List;

public class AlbumMapper {

    public static AlbumDTO convert(Album album){
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setId(album.getId());
        albumDTO.setName(album.getName());
        albumDTO.setThumbnail(album.getThumbnail());
        return albumDTO;
    }

    public static List<AlbumDTO> convertList(List<Album> songList){
        List<AlbumDTO> albumDTOS = new LinkedList<>();
        for(Album album:songList){
            albumDTOS.add(convert(album));
        }
        return albumDTOS;
    }
}
