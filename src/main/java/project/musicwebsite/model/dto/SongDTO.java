package project.musicwebsite.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.musicwebsite.entity.Album;
import project.musicwebsite.entity.Category;
import project.musicwebsite.entity.Singer;

import java.util.List;
import java.util.ListIterator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {
    private Long id;
    private String name;
    private int status;
    private String fileSong;
    private String fileLyric;
    private List<Category> categories;
}
