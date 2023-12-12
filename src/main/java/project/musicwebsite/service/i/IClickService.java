package project.musicwebsite.service.i;

import project.musicwebsite.entity.Click;
import project.musicwebsite.entity.Song;

import java.util.List;

public interface IClickService {
    Click save(Long userId, Long songId);
    Long getListensBySongId(Long songId);
    List<Song> getAllSongByUserId();
}
