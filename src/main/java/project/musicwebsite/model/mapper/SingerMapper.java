package project.musicwebsite.model.mapper;

import project.musicwebsite.entity.Singer;
import project.musicwebsite.model.dto.SingerDTO;

public class SingerMapper {

    public static SingerDTO singerMapper(Singer BSinger){
        SingerDTO singerDTO = new SingerDTO();
        singerDTO.setName(BSinger.getName());
        singerDTO.setEmail(BSinger.getEmail());
        singerDTO.setBio(BSinger.getBio());
        singerDTO.setLinkSocial(BSinger.getSocialMediaLink());
//        singerDTO.setFollower(BSinger.getFollowers());
        return singerDTO;
    }
}
