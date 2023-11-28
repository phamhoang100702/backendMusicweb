package project.musicwebsite.model.mapper;

import project.musicwebsite.entity.Song;
import project.musicwebsite.model.dto.SongDTO;

import java.util.LinkedList;
import java.util.List;

public class SongMapper {
    public static SongDTO convertSong(Song song){
        SongDTO songDTO1 = new SongDTO();
        songDTO1.setFileSong(song.getFileSong());
        songDTO1.setName(song.getName());
        songDTO1.setStatus(song.getStatus());
        songDTO1.setCategory(song.getCategory());
        songDTO1.setFileLyric(song.getFileLyric());
        songDTO1.setId(song.getId());
        return songDTO1;
    }

    public static List<SongDTO> convertList(List<Song> songList){
        List<SongDTO> songDTOS = new LinkedList<>();
        for(Song song:songList){
            songDTOS.add(convertSong(song));
        }
        return songDTOS;
    }
}
