package project.musicwebsite.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.musicwebsite.entity.Song;
@Data
@NoArgsConstructor
public class ClickDTO {
    private Long times;
    private Song  song;

    public ClickDTO(Long times, Song song) {
        this.times = times;
        this.song = song;
    }
}
