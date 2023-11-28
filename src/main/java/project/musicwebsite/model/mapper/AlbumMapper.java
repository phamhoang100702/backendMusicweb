package project.musicwebsite.model.mapper;

import project.musicwebsite.entity.Album;
import project.musicwebsite.model.dto.AlbumDTO;
import project.musicwebsite.model.dto.SingerDTO;

public class AlbumMapper {

    public static AlbumDTO convert(Album album){
        SingerDTO singerDTO = SingerMapper.singerMapper(album.getSinger());
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setName(album.getName());
        albumDTO.setSinger(singerDTO);
        albumDTO.setPublish(album.getPublish());
        albumDTO.setThumbnail(album.getThumbnail());

        return albumDTO;
    }
}
