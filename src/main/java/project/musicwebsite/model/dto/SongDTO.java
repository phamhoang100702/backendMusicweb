package project.musicwebsite.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.musicwebsite.entity.Album;
import project.musicwebsite.entity.Singer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {
    private Long id;
    private String name;
    private boolean status;
    private String fileSong;
    private String fileLyric;
    private String category;
}
