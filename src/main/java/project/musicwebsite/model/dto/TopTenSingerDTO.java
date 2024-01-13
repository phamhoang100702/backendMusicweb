package project.musicwebsite.model.dto;

import lombok.Data;

@Data
public class TopTenSingerDTO {
    private Long id;
    private String name;
    private String nickName;
    private Long followers;
    private String avatar;
}
