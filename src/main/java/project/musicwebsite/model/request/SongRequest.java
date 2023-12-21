package project.musicwebsite.model.request;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SongRequest {
    public Long id;
    public String name;
    public Boolean status;
    public String fileSound;
    public String fileLyric;
    public String avatar;
    public List<SingerSongRequest> singerSongRequestList;
}
