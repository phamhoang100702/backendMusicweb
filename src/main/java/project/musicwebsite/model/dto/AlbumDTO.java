package project.musicwebsite.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlbumDTO {
    private String name;
    private Date publish;
    private String thumbnail;
    private SingerDTO singer;
}
