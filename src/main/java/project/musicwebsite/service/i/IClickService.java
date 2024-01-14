package project.musicwebsite.service.i;

import project.musicwebsite.entity.Click;
import project.musicwebsite.entity.Song;
import project.musicwebsite.model.dto.ChartDTO;
import project.musicwebsite.model.dto.ClickDTO;

import java.sql.SQLException;
import java.util.List;

public interface IClickService {
    Click save(Click click);
    List<Song> getListensBySongId(Long songId);
    List<Song> getAllSongByUserId();

    List<ClickDTO> countListensInWeek();

    List<ClickDTO> countListensInDay() throws SQLException;

    List<ClickDTO> countListensInMonth();

    List<ClickDTO> countListens();

    Long countListensBySong(Song song);

    Long count();


}
