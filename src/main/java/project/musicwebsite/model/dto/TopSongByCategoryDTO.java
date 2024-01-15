package project.musicwebsite.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.musicwebsite.entity.Category;
import project.musicwebsite.entity.Song;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class TopSongByCategoryDTO {
    private Category category;
    List<Song> songs = new LinkedList<>();
}
