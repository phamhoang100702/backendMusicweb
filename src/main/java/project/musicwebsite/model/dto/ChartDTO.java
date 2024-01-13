package project.musicwebsite.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ChartDTO {
    Date date;
    Long times;
    String type;
}
